package com.evisible.os.timing.quartz.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evisible.os.business.service.ITDataSourceConfigService;
import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.timing.quartz.dao.TTriggerConfigMapper;
import com.evisible.os.timing.quartz.entity.TTriggerConfig;
import com.evisible.os.timing.quartz.entity.TTriggerConfigExample;
import com.evisible.os.timing.quartz.job.JobFactory;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;
import com.evisible.os.timing.quartz.util.SchedulerSingleton;

/**
 * 
 * <p>
 * 定时设置Service
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月16日
 */
@Service("triggerConfigService")
public class TriggerConfigService extends MybatisGenerator<TTriggerConfigMapper> implements ITriggerConfigService {

	@Autowired
	ITDataSourceConfigService tDataSourceConfigService;
	private static Logger log = LoggerFactory.getLogger(TriggerConfigService.class);

	public TriggerConfigService() {
		super(TTriggerConfigMapper.class);
	}

	/**
	 * 插入新的TriggerConfig数据
	 */
	@Override
	public boolean addTriggerConfig(TTriggerConfig record) {
		try {
			User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
			record.setUuid(StringConvert.getUUIDString());
			record.setCreatedate(new Date());
			record.setCreator(user.getuName());
			record.setCreatorId(user.getUuid());
			this.getDao().insertSelective(record);
			return true;
		} catch (Exception e) {
			log.error("------------插入TriggerConfig出错------------");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TTriggerConfig> isDataSourceTriggerConfigered(String dataSourceId) {
		TTriggerConfigExample example = new TTriggerConfigExample();
		example.or().andDatasourceIdEqualTo(dataSourceId);
		List<TTriggerConfig> list = this.getDao().selectByExample(example);
		return list;
	}

	@Override
	public boolean updateTriggerConfig(TTriggerConfig record) {
		try {
			// 更新t_trigger_config 中相应的数据
			this.getDao().updateByPrimaryKey(record);
			// 更新quartz相应job设置
			setJobTrigger(record);
			return true;
		} catch (Exception e) {
			log.error("------------更新TriggerConfig出错------------");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 获取t_trigger_config中当前可用的triggers
	 */
	@Override
	public List<TTriggerConfig> getAvailableTriggers() {
		// cron表达式不为空以及is_available字段为1的记录为有效trigger
		try {
			TTriggerConfigExample example = new TTriggerConfigExample();
			example.or().andIsAvailableEqualTo(true);
			example.or().andQuartzCornIsNotNull();
			return this.getDao().selectByExample(example);
		} catch (Exception e) {
			log.error("------------获取可用Trigger出错------------");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 系统启动时将可用的trigger加入Quartz执行的Schedule中
	 */
	@Override
	public boolean addJobsToSchedule() {
		List<TTriggerConfig> availableTriggers = getAvailableTriggers();
		for (TTriggerConfig config : availableTriggers) {
			try {
				addJobTrigger(config);
			} catch (ClassNotFoundException e) {
				log.error("------------加入定时任务出错------------");
				e.printStackTrace();
				return false;
			} catch (ParseException e) {
				log.error("------------加入定时任务出错------------");
				e.printStackTrace();
				return false;
			} catch (SchedulerException e) {
				log.error("------------加入定时任务出错------------");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据datasourceId设置trigger的可用性
	 */
	@Override
	public boolean setTriggerAvailableByDataSourceId(String dataSourceId, boolean isAvailable) {
		try {
			TTriggerConfigExample example = new TTriggerConfigExample();
			TTriggerConfig record = new TTriggerConfig();
			record.setDatasourceId(dataSourceId);
			record.setIsAvailable(isAvailable);
			example.or().andDatasourceIdEqualTo(dataSourceId);
			this.getDao().updateByExampleSelective(record, example);
			return true;
		} catch (Exception e) {
			log.error("------------设置Trigger可用性出错------------");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 将相应dataSourceId对应的trigger取消， 从quartz工作池中取消， 并从t_trigger_config 中删除
	 */
	@Override
	public boolean cancelTriggerByDataSourceId(String dataSourceId) {
		// 根据dataSourceId获取trigger的uuid
		try {
			TTriggerConfigExample example = new TTriggerConfigExample();
			example.or().andDatasourceIdEqualTo(dataSourceId);
			List<TTriggerConfig> list = this.getDao().selectByExample(example);
			if (list.size() > 0) {
				String triggerUuid = this.getDao().selectByExample(example).get(0).getUuid();
				// 将job立即从quartz中取消
				if (!calcelJobTrigger(triggerUuid)) {
					return false;
				}
				// 将任务从当前线程池删除之后， 将配置表中信息一并删除
				this.getDao().deleteByPrimaryKey(triggerUuid);
				return true;
			}

		} catch (Exception e) {
			log.error("------------取消定时设定出错------------");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * 立即修改当前job设置并生效
	 * </p>
	 * 
	 * @author JiangWanDong
	 * @throws SchedulerException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public void setJobTrigger(TTriggerConfig record) throws SchedulerException, ClassNotFoundException, ParseException {
		Scheduler sched = SchedulerSingleton.getScheduler();
		CronTrigger trigger = (CronTrigger) sched.getTrigger(record.getUuid(), "GATHER");
		if (trigger != null) {
			calcelJobTrigger(record.getUuid());
			addJobTrigger(record);

		}
	}

	/**
	 * 
	 * <p>
	 * 立即从quartz任务池删除job设置并生效
	 * </p>
	 * 
	 * @author JiangWanDong
	 */
	public boolean calcelJobTrigger(String jobName) {
		Scheduler sched = SchedulerSingleton.getScheduler();
		try {
			sched.pauseTrigger(jobName, "GATHER");
		} catch (SchedulerException e) {
			log.error("------------取消定时设定出错------------");
			e.printStackTrace();
			return false;
		}
		try {
			sched.unscheduleJob(jobName, "GATHER");
		} catch (SchedulerException e) {
			log.error("------------取消定时设定出错------------");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * 向Quartz中添加一个job并生效
	 * </p>
	 * 
	 * @author JiangWanDong
	 * @throws ParseException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void addJobTrigger(TTriggerConfig config) throws ParseException, SchedulerException, ClassNotFoundException {
		Scheduler sched = SchedulerSingleton.getScheduler();
		// 创建Job时，
		// 默认以trigger_config中的uuid和uuid作为job和trigger的名称,以数据源的名称作为默认的分组
		JobDetail jobDetail;
		jobDetail = new JobDetail(config.getUuid(), "GATHER", JobFactory.createJob(config.getClazz()).getClass());
		jobDetail.getJobDataMap().put("triggerConfig", config);
		Trigger trigger;
		trigger = new CronTrigger(config.getUuid(), "GATHER", config.getQuartzCorn());
		sched.scheduleJob(jobDetail, trigger);
		if (!sched.isShutdown()) {
			sched.start();
		}

	}

}
