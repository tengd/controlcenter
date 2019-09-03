package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SUserRole;



/**
 * <p>用户成角色</p>
 * @author TengDong
 * @Date 20160408
 */
public interface ISUserRoleService {
	
	/**
	 *  @see删除用户或角色数据
	 * @param roleUuid
	 * @return boolean
	 */
	boolean delSUerRole(List<String> userRoleUuids);
	
	/**
	 * @see 添加用户与角色关系
	 * @param SUserRoles
	 * @return boolean
	 */
	boolean addUserRoles(List<SUserRole>  userRoleList);
	
	/**
	 * @param pageUI 分页
	 * @return 所有用户与角色
	 */
	Map<String,Object>	getUserRoles(PageUI pageUI);
}
