package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;

/**
 * 
 * <p>由Quartz触发的解析BaseData的Job</p>
 * @author JiangWanDong
 * @Date   2018年1月17日
 */
public class BaseDataResolveJob implements Job{
	
	private static Logger log = LoggerFactory.getLogger(BaseDataResolveJob.class);
	@Autowired
	ITBaseDataService baseDataService;
	@Autowired
	ITriggerConfigService triggerConfigService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		log.info("--------quartz开始解析baseData---------------------");
		try{
		    /**
		     * 解析xml并插入数据库的job逻辑:
		     * 1.正常解析数据
		     * 2.因每条数据定时解析一次就不再解析， 解析完成后将该job删除,防止重复解析
		     */
		    String dataSourceId = baseDataService.getBaseDataInfoByTriggerUuid(context.getJobDetail().getName()).getUuid();
		    String[] baseDataUuids = new String[]{dataSourceId};
		    //根据baseData的uuid解析相应的basedata并插入数据库
		    baseDataService.resolveBaseDataXml(baseDataUuids);
		    //删除job
		    triggerConfigService.cancelTriggerByDataSourceId(dataSourceId);	
		    log.info("--------quartz解析baseData任务成功---------------------");
		}catch(Exception e){
			e.printStackTrace();
			log.info("--------quartz解析baseData任务失败---------------------");
		}
	}

}
