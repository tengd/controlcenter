package com.evisible.os.business.entity;
/**
 * 
 * <p>类表Entity</p>
 * @author JiangWanDong
 * @Date   2018年1月11日
 */
public class TDataBaseTables {
	private String tableName;
	private String tableComment;
	
	public TDataBaseTables(){}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	

}
