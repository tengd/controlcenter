package com.evisible.os.controlcenter.model.vo;

import java.util.List;

/**
 * <p>权限功能封装</p>
 * @author TengDong
 * @date 20160815
 */
public class FunPermission {
	private String roleId;
	private List<Permission> permissions;
	private List<Fun> funs;
	public FunPermission(){}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public List<Fun> getFuns() {
		return funs;
	}
	public void setFuns(List<Fun> funs) {
		this.funs = funs;
	}
	
}
