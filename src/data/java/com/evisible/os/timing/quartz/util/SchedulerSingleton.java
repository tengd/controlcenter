package com.evisible.os.timing.quartz.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * <p>
 * 创建Quartz的Scheduler单例
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月17日
 */
public class SchedulerSingleton {
	private static Scheduler sched;

	private SchedulerSingleton() {
	}

	public static Scheduler getScheduler() {
		if (sched == null) {
			synchronized (SchedulerSingleton.class) {
				if (sched == null) {
					try {
						sched = new StdSchedulerFactory().getScheduler();
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sched;
	}
}
