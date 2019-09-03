package com.evisible.os.timing.quartz;

import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.evisible.os.timing.quartz.service.ITriggerConfigService;
import com.evisible.os.timing.quartz.util.SchedulerSingleton;

/**
 * <p>
 * 一个是启动quartz的方法，一个是关闭方法，这个类里面可以定制每隔多少时间循环调用业务类一次
 * </p>
 * 
 * @author Administrator
 *
 */
public class QuartzLoad {
	private static Scheduler sched;
	static{
		sched = SchedulerSingleton.getScheduler();
	}
	
	public static void run(ServletContextEvent sce) throws Exception {
    	WebApplicationContext rwp = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
    	ITriggerConfigService service = (ITriggerConfigService) rwp.getBean("triggerConfigService");
		service.addJobsToSchedule();
	}

	// 停止
	public static void stop() throws Exception {
		sched.shutdown();
	}
}
