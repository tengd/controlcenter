package com.evisible.os.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.business.entity.TDataBaseTables;
import com.evisible.os.business.entity.TableDescription;

/** 
* @author Jiangwandong
* @version 创建时间：2018年1月11日
* 类说明 
*/
public interface TDataBaseTablesMapper {
	// int countByExample(TDataBaseTables example);
	List<TDataBaseTables> getDataBaseTables(@Param("pageStart") int pageStart , @Param("pageEnd") int pageEnd);
	List<TableDescription> getSingleTableDescription(@Param("tableName") String tabelName);
	int deleteTable(@Param("tableName") String tabelName);
	int getDataBaseTablesCount();
	int updateFieldComment(@Param("tableName")String tableName, @Param("field")String field, @Param("type")String type, @Param("comment")String comment) ;
	void updateTableComment(@Param("tableName") String tableName,@Param("comment") String comment);

}
