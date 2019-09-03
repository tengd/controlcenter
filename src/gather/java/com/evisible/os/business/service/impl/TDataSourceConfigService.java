package com.evisible.os.business.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.business.dao.TDataSourceConfigMapper;
import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.entity.TDataSourceConfigExample;
import com.evisible.os.business.service.ITDataSourceConfigService;
import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;

/**
 * 
 * <p>数据源配置Service</p>
 * @author JiangWanDong
 * @Date   2018年1月10日
 */
@Service("tDataSourceConfigService")
public class TDataSourceConfigService extends MybatisGenerator<TDataSourceConfigMapper> implements ITDataSourceConfigService{

	private static Logger log = LoggerFactory.getLogger(TDataSourceConfigService.class);
	
	public TDataSourceConfigService() {
		super(TDataSourceConfigMapper.class);
	}
	@Override
	public Map<String , Object> getDataSourceConfigsByType(String type , PageUI pageUI) {
		Map<String , Object> map = new HashMap<>();
		Map<String, Object> pageCondition = new HashMap<>();
		pageCondition.put("pageStart", pageUI.getValue());
		pageCondition.put("pageEnd", pageUI.getSecondValue());
		pageCondition.put("type", type);
		TDataSourceConfigExample example = new TDataSourceConfigExample();
		example.or().andTypeEqualTo(type);
		map.put("rows",this.getDao().selectByType(pageCondition));	
		map.put("total", this.getDao().countByExample(example));
		return map;
	}
	@Override
	public int addWebServiceDataSourceConfig(TDataSourceConfig dataSourceConfig) {		
		return this.getDao().insertSelective(dataSourceConfig);
	}
	@Override
	public int delWebServiceDataSourceConfig(String uuid) {		
		return this.getDao().deleteByPrimaryKey(uuid);
	}
	
	@Override
	public boolean updateDataSourceConfig(TDataSourceConfig dataSourceConfig) {
		try{
			this.getDao().updateByPrimaryKeySelective(dataSourceConfig);
		}catch(Exception e){
			e.printStackTrace();
			log.error("------更新datasourceConfig出错-------------------");
			return false;
		}
		return true;
	}
	@Override
	public TDataSourceConfig getDataSourceConfigByUuid(String uuid) {
		return this.getDao().selectByPrimaryKey(uuid);
	}
	
	/**
	 * 用于triggerJob触发时， 根据trigger的uuid获取dataSourceid
	 */
	public TDataSourceConfig getDataSourceConfigByTriggerId(String triggerUuid) {
		return this.getDao().getDataSourceConfigByTriggerId(triggerUuid);
	}

}
