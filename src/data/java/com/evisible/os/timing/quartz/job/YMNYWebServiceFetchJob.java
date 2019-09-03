
package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.evisible.os.business.service.impl.TDataSourceConfigService;
import com.evisible.os.business.webservice.YMNYWebserviceResolver;

/**
 * 
 * <p>云煤能源自动获取数据job</p>
 * @author JiangWanDong
 * @Date   2018年2月9日
 */
public class YMNYWebServiceFetchJob implements Job{
	private static Logger log = LoggerFactory.getLogger(YMNYWebServiceFetchJob.class);

	@Autowired
	YMNYWebserviceResolver ymnyWebserviceResolver;
	@Autowired
	TDataSourceConfigService dataSourceConfigService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			log.info("--------quartz开始获取云煤能源Webservice数据---------------------");
			ymnyWebserviceResolver.getYMNYWebServiceData_trigger(dataSourceConfigService
					.getDataSourceConfigByTriggerId(context.getJobDetail().getName()));
			log.info("--------quartz获取云煤能源Webservice数据成功---------------------");
		} catch (Exception e) {
			log.info("--------quartz获取云煤能源Webservice数据失败---------------------");
			e.printStackTrace();
		}
		
	}

}
