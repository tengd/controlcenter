package com.evisible.os.business.service;

import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;

/**
 * 
 * <p>库表Service类</p>
 * @author JiangWanDong
 * @Date   2018年1月11日
 */
public interface IDataBaseTablesService {
	Map<String , Object> getDataBaseTables(PageUI pageUI);
	Map<String , Object> getSingleTableInfo(String tableName);
	int deleteTable(String tabelName);
	int updateFieldComment(String tableName , String field , String type , String comment);
	void updateTableComment(String tableName , String comment);
}
