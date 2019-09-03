package com.evisible.os.timing.quartz.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.evisible.os.timing.quartz.entity.TTriggerConfig;

/**
 * 
 * <p>定时设置Service接口</p>
 * @author JiangWanDong
 * @Date   2018年1月16日
 */
public interface ITriggerConfigService {
	boolean addTriggerConfig(TTriggerConfig record);
	
	List<TTriggerConfig> isDataSourceTriggerConfigered(String dataSourceId);
	
	boolean updateTriggerConfig(TTriggerConfig record);
	
	List<TTriggerConfig> getAvailableTriggers();
	
	boolean addJobsToSchedule();
	
	boolean setTriggerAvailableByDataSourceId(String dataSourceId , boolean isAvailable );
	
	boolean cancelTriggerByDataSourceId(String dataSourceId);
	
	void addJobTrigger(TTriggerConfig config) throws ParseException, SchedulerException, ClassNotFoundException;
	
	void setJobTrigger(TTriggerConfig record) throws SchedulerException, ClassNotFoundException, ParseException;
}
