package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SRole;

/**
 * <p>角色</p>
 * @author TengDong
 * @Date 20160408
 *
 */
public interface ISRoleService {
	
	
	/**
	 * @param pageUI 分页
	 * @return 所有角色
	 */
	Map<String,Object>	getRoles(PageUI pageUI,String rname);
	
	/**
	 * @see 用户获得用户角色
	 * @param UserId 用户ID
	 * @return 所有角色
	 */
	List<SRole> getRolesByUser(String UserId);
	
	/**
	 * 添加角色
	 * @param role  角色
	 * @return boolean
	 */
	boolean addRole(SRole role);
	
	
	/**
	 * 编辑角色
	 * @param role 角色
	 * @return boolean
	 */
	boolean editRole(SRole role);
	
	/**
	 * 删除角色
	 * @param role 角色
	 * @return boolean
	 */
	boolean delRole(String role_uuid) throws Exception;
	
	
	
}		
