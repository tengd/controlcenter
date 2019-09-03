package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>Job工厂</p>
 * @author TengDong
 * @20180119
 *
 */
public class JobFactory {
	private static Logger log = LoggerFactory.getLogger(JobFactory.class);
  
	public static Job createJob(String clazz) throws ClassNotFoundException{
		Class<?> c=null;
    	try {
			c=Class.forName(clazz);
			try {
				return (Job)c.newInstance();
			} catch (InstantiationException e) {
				log.error("---Job类实例化错误----"+e.getMessage());
			} catch (IllegalAccessException e) {
				log.error("---Job类安全权限异常----"+e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			log.error("---Job类未找到异常----"+e.getMessage());
			return null;
		}
		return null;
	}

}
