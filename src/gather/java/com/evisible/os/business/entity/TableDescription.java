package com.evisible.os.business.entity;
/**
 * 
 * <p>表描述Entity</p>
 * @author JiangWanDong
 * @Date   2018年1月11日
 */
public class TableDescription {
	private String field;
	private String type;
	private String isNULL;
	private String key;
		public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private String defaultValue;
	private String comment;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsNULL() {
		return isNULL;
	}
	public void setIsNULL(String isNULL) {
		this.isNULL = isNULL;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
