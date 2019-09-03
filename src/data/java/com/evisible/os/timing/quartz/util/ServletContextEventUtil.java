package com.evisible.os.timing.quartz.util;

import javax.servlet.ServletContextEvent;

/** 
* @author Jiangwandong
* @version 创建时间：2018年1月18日
* 类说明 
*/
public class ServletContextEventUtil {
	private static ServletContextEvent servletContextEvent;	

	public static ServletContextEvent getServletContextEvent() {
		return servletContextEvent;
	}
	public static void setServletContextEvent(ServletContextEvent servletContextEvent) {
		ServletContextEventUtil.servletContextEvent = servletContextEvent;
	}
	
	
}
