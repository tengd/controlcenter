package com.evisible.os.controlcenter.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.SysLogMapper;
import com.evisible.os.controlcenter.system.entity.SysLog;
import com.evisible.os.controlcenter.system.entity.SysLogExample;
import com.evisible.os.controlcenter.system.service.ISysLogService;

/**
 * <p>
 * 系统日志记录
 * </p>
 * 
 * @author TengDong
 * @Date 20170412
 */
@Service("sysLogService")
public class SysLogService extends MybatisGenerator<SysLogMapper> implements
		ISysLogService {

	private static Logger log = LoggerFactory.getLogger(SysLogMapper.class);

	public SysLogService() {
		super(SysLogMapper.class);
	}

	/**
	 * 添加日志
	 * 
	 * @param record
	 * @return boolean
	 * */
	@Override
	public boolean addSysLog(SysLog record) {
		try {
			int sign = this.getDao().insertSelective(record);
			if (sign > 0)
				return true;
		} catch (Exception e) {
			log.info("----------添加日志异常----------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see 时间段获得日志
	 * @param pageUI
	 * @param startDate
	 * @param endDate
	 * @return map
	 * */
	@Override
	public Map<String, Object> getSysLog(PageUI pageUI, String startDate,
			String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String,Object> example=new HashMap<String,Object>();
			example.put("startDate",startDate);
			example.put("endDate", endDate);
			example.put("value", pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			
			map.put("rows", this.getDao().selectByExampleKZ(example));
			
			map.put("total", this.getDao().countSelectByExampleKZ(example));
			example.clear();
			return map;
		} catch (Exception e) {
			log.info("---------------获取日志信息出错-----------------------");
			e.printStackTrace();
			map.clear();
		}

		return map;
	}

	/***
	 * 清日志释放表
	 * 
	 * @param uuids
	 * @return boolean
	 * **/
	@Override
	public boolean delSysLog(List<String> sysLogUuids) {
		try {
			SysLogExample example = new SysLogExample();
			SysLogExample.Criteria criteria = example.createCriteria();
			criteria.andUuidIn(sysLogUuids);
			int sign = this.getDao().deleteByExample(example);
			if (sign > 0)
				return true;
		} catch (Exception e) {
			log.info("-----------清日志释放异常------------");
			e.printStackTrace();
		}
		return false;
	}

}
