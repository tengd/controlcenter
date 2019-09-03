package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.service.impl.TDataSourceConfigService;
import com.evisible.os.business.webservice.KGWebserviceResolver;

/** 
* <p>昆钢数据定时获取任务</p>
* @author Jiangwandong
* @Date ：2018年2月11日
* 
*/
public class KGWebserviceFetchJob implements Job{
	@Autowired
	TDataSourceConfigService dataSourceConfigService;
	@Autowired
	ITBaseDataService baseDataService;
	@Autowired
	KGWebserviceResolver kgWebserviceResover;

	private static Logger log = LoggerFactory.getLogger(KGWebserviceFetchJob.class);

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			log.info("--------quartz开始获取昆钢Webservice数据---------------------");
			kgWebserviceResover.getKGWebServiceData_trigger(dataSourceConfigService
					.getDataSourceConfigByTriggerId(context.getJobDetail().getName()));
			log.info("--------quartz获取昆钢Webservice数据成功---------------------");
		} catch (Exception e) {
			log.info("--------quartz获取昆钢Webservice数据失败---------------------");
			e.printStackTrace();
		}
		
	}

}
