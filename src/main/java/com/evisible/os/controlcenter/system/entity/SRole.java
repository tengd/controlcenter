package com.evisible.os.controlcenter.system.entity;

import java.io.Serializable;
import java.util.Date;

public class SRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.uuid
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.r_name
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private String rName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.remark
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.creator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.createdate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.updator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_role.updatedate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    private Date updatedate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.uuid
     *
     * @return the value of s_role.uuid
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.uuid
     *
     * @param uuid the value for s_role.uuid
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.r_name
     *
     * @return the value of s_role.r_name
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public String getrName() {
        return rName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.r_name
     *
     * @param rName the value for s_role.r_name
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.remark
     *
     * @return the value of s_role.remark
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.remark
     *
     * @param remark the value for s_role.remark
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.creator
     *
     * @return the value of s_role.creator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.creator
     *
     * @param creator the value for s_role.creator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.createdate
     *
     * @return the value of s_role.createdate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.createdate
     *
     * @param createdate the value for s_role.createdate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.updator
     *
     * @return the value of s_role.updator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.updator
     *
     * @param updator the value for s_role.updator
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_role.updatedate
     *
     * @return the value of s_role.updatedate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_role.updatedate
     *
     * @param updatedate the value for s_role.updatedate
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}