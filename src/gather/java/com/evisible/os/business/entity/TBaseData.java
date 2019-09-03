package com.evisible.os.business.entity;

import java.util.Date;

public class TBaseData {
    private String uuid;

    private String roleId;

    private String datasourceConfigId;

    private String token;

    private String tableName;

    private String companyCode;

    private String companyName;

    private Date analysisTime;

    private String pushAuthor;

    private Date pushTime;

    private Boolean isSucc;

    private Boolean isLocked;

    private String xmlText;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDatasourceConfigId() {
        return datasourceConfigId;
    }

    public void setDatasourceConfigId(String datasourceConfigId) {
        this.datasourceConfigId = datasourceConfigId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getPushAuthor() {
        return pushAuthor;
    }

    public void setPushAuthor(String pushAuthor) {
        this.pushAuthor = pushAuthor;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Boolean getIsSucc() {
        return isSucc;
    }

    public void setIsSucc(Boolean isSucc) {
        this.isSucc = isSucc;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getXmlText() {
        return xmlText;
    }

    public void setXmlText(String xmlText) {
        this.xmlText = xmlText;
    }
    
    public TBaseData(){}
    
	public TBaseData(String token, String tableName, String companyCode, String companyName, String pushAuthor,
			String xmlText, Date pushTime) {
		super();
		this.token = token;
		this.tableName = tableName;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.pushAuthor = pushAuthor;
		this.xmlText = xmlText;
		this.pushTime = pushTime;
	}
	
	public TBaseData(String uuid , String token, String tableName, String companyCode, String companyName, String pushAuthor,
			String xmlText, Date pushTime) {
		super();
		this.uuid = uuid;
		this.token = token;
		this.tableName = tableName;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.pushAuthor = pushAuthor;
		this.xmlText = xmlText;
		this.pushTime = pushTime;
	}
}