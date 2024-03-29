package com.evisible.os.controlcenter.system.entity;

import java.util.Date;

public class SysLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.uuid
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.visit_ip
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String visitIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.u_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String uId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.u_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String uName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.fun_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String funId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.fun_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String funName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.visit_time
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private Date visitTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_sys_log.visit_os
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    private String visitOs;
    
    
    /**
     * 日志描述
     */
    @SuppressWarnings("unused")
	private String logDescribe;
    
    
    

    /**
     * @return
     */
    public String getLogDescribe() {
		return "用户:"+this.getuName()+",操作了"+this.getFunName()+".IP:"
			   +this.getVisitIp()+",操作系统："+this.getVisitOs();
	}

	

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.uuid
     *
     * @return the value of s_sys_log.uuid
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.uuid
     *
     * @param uuid the value for s_sys_log.uuid
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.visit_ip
     *
     * @return the value of s_sys_log.visit_ip
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getVisitIp() {
        return visitIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.visit_ip
     *
     * @param visitIp the value for s_sys_log.visit_ip
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.u_id
     *
     * @return the value of s_sys_log.u_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getuId() {
        return uId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.u_id
     *
     * @param uId the value for s_sys_log.u_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setuId(String uId) {
        this.uId = uId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.u_name
     *
     * @return the value of s_sys_log.u_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getuName() {
        return uName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.u_name
     *
     * @param uName the value for s_sys_log.u_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setuName(String uName) {
        this.uName = uName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.fun_id
     *
     * @return the value of s_sys_log.fun_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getFunId() {
        return funId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.fun_id
     *
     * @param funId the value for s_sys_log.fun_id
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setFunId(String funId) {
        this.funId = funId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.fun_name
     *
     * @return the value of s_sys_log.fun_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getFunName() {
        return funName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.fun_name
     *
     * @param funName the value for s_sys_log.fun_name
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setFunName(String funName) {
        this.funName = funName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.visit_time
     *
     * @return the value of s_sys_log.visit_time
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public Date getVisitTime() {
        return visitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.visit_time
     *
     * @param visitTime the value for s_sys_log.visit_time
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_sys_log.visit_os
     *
     * @return the value of s_sys_log.visit_os
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public String getVisitOs() {
        return visitOs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_sys_log.visit_os
     *
     * @param visitOs the value for s_sys_log.visit_os
     *
     * @mbggenerated Wed Apr 12 16:00:12 CST 2017
     */
    public void setVisitOs(String visitOs) {
        this.visitOs = visitOs;
    }
}