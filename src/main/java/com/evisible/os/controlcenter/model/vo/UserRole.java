package com.evisible.os.controlcenter.model.vo;

import java.util.List;

/**
 * <p>用户角色信息封装</p>
 * @author TengDong
 * @Date 20160815
 */
public class UserRole {
	private String token;
	private String userId;
	private List<Role> roles;
	public UserRole(){}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
