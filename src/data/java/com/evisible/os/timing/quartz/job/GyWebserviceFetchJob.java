package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.service.impl.TDataSourceConfigService;
import com.evisible.os.business.webservice.GYWebserviceResover;

/**
 * <p> 获取国药WEBSERVICE接口数据Job</p>
 * @author JiangWanDong
 * @Date 2018年1月17日
 */
public class GyWebserviceFetchJob implements Job {
	@Autowired
	TDataSourceConfigService dataSourceConfigService;
	@Autowired
	ITBaseDataService baseDataService;
	@Autowired
	GYWebserviceResover gyWebserviceResover;

	private static Logger log = LoggerFactory.getLogger(GyWebserviceFetchJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			log.info("--------quartz开始获取国药Webservice数据---------------------");
			TDataSourceConfig dataSourceConfig = dataSourceConfigService
					.getDataSourceConfigByTriggerId(context.getJobDetail().getName());
			String insertedResult = gyWebserviceResover.getGYWebServiceData_trigger(dataSourceConfig);
			log.info("--------quartz获取国药Webservice数据成功---------------------");
			/**
			 * 解析xml并插入数据库的job逻辑: 1.正常解析数据 2.因每条数据定时解析一次就不再解析，
			 * 解析完成后将该job删除,防止重复解析
			 * 
			 * 若获取国药数据并插入数据库成功， 会得到一条插入数据库的uuid， 若这个uuid不为空， 则解析数据
			 */
			if (insertedResult != null && !insertedResult.startsWith("error")) {
				log.info("--------quartz解析baseData任务开始---------------------");
				String[] baseDataUuids = new String[] { insertedResult };
				// 根据baseData的uuid解析相应的basedata并插入数据库
				baseDataService.resolveBaseDataXml(baseDataUuids);
				log.info("--------quartz解析baseData任务成功---------------------");
			} else {
				log.info("--------" + insertedResult + "---------------------");
			}
		} catch (Exception e) {
			log.info("--------quartz获取国药Webservice数据失败---------------------");
			e.printStackTrace();
		}

	}

}
