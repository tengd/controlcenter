package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SPermission;

/**
 * <p>权限</p>
 * @author TengDong
 * @Date 20160414
 */
public interface IPermissionService {
	static final String Mapper="com.usercenter.system.dao.sql.SPermissionMapper.";
	/**
	 * @see 获得所有权限
	 * @param pName
	 * @param pageUI
	 * @return 权限
	 */
	Map<String,Object> getPermissions(String pName,PageUI pageUI);
	
	
	/**
	 * @see  角色对应所有权限
	 * @param RoleId 角色ID
	 * @return  权限
	 */
	List<SPermission> getPermissionsByRoleId(List<String> RoleId);
	
	/**
	 * 批量删除权限
	 * @param uuids
	 * @return boolean
	 */
	boolean delSPermission(List<String> uuids);
	
	/**
	 * 插入权限
	 * @param permission
	 * @return boolean
	 */
	boolean addSPermission(SPermission permission);
	
	/**
	 * 更新权限
	 * @param permission
	 * @return boolean
	 */
	boolean editSPermission(SPermission permission);
	

	
	
	
}
