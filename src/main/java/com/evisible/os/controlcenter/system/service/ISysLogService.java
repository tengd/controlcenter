package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SysLog;


/**
 * <p>日志操作</p>
 * @author TengDong
 * @Date
 */
public interface ISysLogService {
	static final String Mapper="com.usercenter.system.dao.sql.SysLogMapper.";
	
	/**
	 * @see 添加日志信息
	 * @param sysLog
	 * @return boolean
	 */
	boolean addSysLog(SysLog record);
	
	
	/**
	 * @see 时间段获取系统日志
	 * @param pageUI 分页
	 * @return SyslogS
	 */
	Map<String,Object> getSysLog(PageUI pageUI,String startDate, String endDate);
	
	
	
	
	/**
	 * @删除日志信息
	 * @param sysLogUuids 批量删除uuids
	 * @return boolean
	 */
	boolean delSysLog(List<String> sysLogUuids);
	
	
	
}
