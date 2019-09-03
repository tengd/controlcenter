package com.evisible.os.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.business.dao.TDataBaseTablesMapper;
import com.evisible.os.business.entity.TDataBaseTables;
import com.evisible.os.business.service.IDataBaseTablesService;
import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;

/**
 * 
 * <p>库表Service类</p>
 * @author JiangWanDong
 * @Date   2018年1月11日
 */
@Service("dataBaseTablesService")
public class DataBaseTablesService  extends MybatisGenerator<TDataBaseTablesMapper> implements IDataBaseTablesService{
	private static Logger log = LoggerFactory.getLogger(DataBaseTablesService.class);	
	public DataBaseTablesService() {
		super(TDataBaseTablesMapper.class);
	}
	
	public Map<String , Object> getDataBaseTables(PageUI pageUI){
		Map<String, Object> dataBaseTables = new HashMap<>();
		try {
			List<TDataBaseTables> list = this.getDao("data").getDataBaseTables(pageUI.getValue(), pageUI.getSecondValue()); 
			dataBaseTables.put("rows", list);
			dataBaseTables.put("total",this.getDao().getDataBaseTablesCount());
		} catch (Exception e) {
			log.info("------查询库表出错----------");
			e.printStackTrace();
		}
		return dataBaseTables ;
	}
	@Override
	public Map<String, Object> getSingleTableInfo(String tableName) {
		Map<String, Object> singleTableInfo = new HashMap<>();
		try {
			singleTableInfo.put("rows", this.getDao("data").getSingleTableDescription(tableName));
			singleTableInfo.put("total", 1);
		} catch (Exception e) {
			log.info("------查询表描述信息出错----------");
			e.printStackTrace();
		}
		return singleTableInfo;
	}
	@Override
	public int deleteTable(String tabelName) {
		return this.getDao("data").deleteTable(tabelName);
	}
	@Override
	public int updateFieldComment(String tableName, String field, String type, String comment) {
		return this.getDao("data").updateFieldComment(tableName, field, type, comment);
	}
	
	@Override
	public void updateTableComment(String tableName, String comment) {
		this.getDao("data").updateTableComment(tableName,comment);
	}


}
