package com.evisible.os.controlcenter.base;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * <p>用于spring多数据源切换，继承AbstractRoutingDataSource，在获取sqlSessionTemplate时指定数据源</p>
 * @author JiangWanDong
 * @Date   2018年3月23日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSouce();
	}
}
