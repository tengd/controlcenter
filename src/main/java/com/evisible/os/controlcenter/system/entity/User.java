package com.evisible.os.controlcenter.system.entity;

import java.util.Date;

public class User implements java.io.Serializable{
    
	private static final long serialVersionUID = 1L;

	private String uuid;

    private String sDicDateId;

    private String uName;

    private String account;

    private String pas;

    private String salt;

    private String phone;

    private String address;

    private String email;

    private String wecharNo;

    private String qqNo;

    private Integer sign;

    private Float integral;

    private Integer integralS;

    private Integer integralE;

    private Integer locked;

    private Date createdate;

    private Integer medalNumber;

    private String uDescribe;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getsDicDateId() {
        return sDicDateId;
    }

    public void setsDicDateId(String sDicDateId) {
        this.sDicDateId = sDicDateId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWecharNo() {
        return wecharNo;
    }

    public void setWecharNo(String wecharNo) {
        this.wecharNo = wecharNo;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Float getIntegral() {
        return integral;
    }

    public void setIntegral(Float integral) {
        this.integral = integral;
    }

    public Integer getIntegralS() {
        return integralS;
    }

    public void setIntegralS(Integer integralS) {
        this.integralS = integralS;
    }

    public Integer getIntegralE() {
        return integralE;
    }

    public void setIntegralE(Integer integralE) {
        this.integralE = integralE;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getMedalNumber() {
        return medalNumber;
    }

    public void setMedalNumber(Integer medalNumber) {
        this.medalNumber = medalNumber;
    }

    public String getuDescribe() {
        return uDescribe;
    }

    public void setuDescribe(String uDescribe) {
        this.uDescribe = uDescribe;
    }
}