package com.evisible.os.business.service;

import java.util.Map;

import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.controlcenter.model.PageUI;

/**
 * 
 * <p>数据源配置Service</p>
 * @author JiangWanDong
 * @Date   2018年1月10日
 */
public interface ITDataSourceConfigService {
	/**
	 * 
	 * <p>根据数据源类型获取dataSource列表</p>
	 * @author JiangWanDong
	 */
	Map<String , Object> getDataSourceConfigsByType(String type , PageUI pageUI);
	
	int addWebServiceDataSourceConfig(TDataSourceConfig dataSourceConfig);
	
	int delWebServiceDataSourceConfig(String uuid);
	
	boolean updateDataSourceConfig(TDataSourceConfig dataSourceConfig);
	
	TDataSourceConfig getDataSourceConfigByUuid(String uuid);
	
	TDataSourceConfig getDataSourceConfigByTriggerId(String triggerUuid);
}
