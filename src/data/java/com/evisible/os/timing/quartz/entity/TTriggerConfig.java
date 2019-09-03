package com.evisible.os.timing.quartz.entity;

import java.util.Date;

public class TTriggerConfig {
    private String uuid;

    private String datasourceId;

    private String quartzCorn;

    private String clazz;

    private Boolean isAvailable;

    private String creatorId;

    private String creator;

    private Date createdate;

    private String updator;

    private Date updatedate;
    

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getQuartzCorn() {
        return quartzCorn;
    }

    public void setQuartzCorn(String quartzCorn) {
        this.quartzCorn = quartzCorn;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

	public TTriggerConfig() {
		super();
	}

    
    
}