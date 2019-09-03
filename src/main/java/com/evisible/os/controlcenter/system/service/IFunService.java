package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evisible.os.controlcenter.model.Tree;
import com.evisible.os.controlcenter.system.entity.SFun;
import com.evisible.os.controlcenter.system.entity.SFunExample;



/**
 * <p>功能</p>
 * @author TengDong
 * @Date 20160328
 */
public interface IFunService {
	static final String Mapper="com.usercenter.system.dao.sql.SFunMapper.";
	
	/**
	 * @return 授权树
	 */
	public List<Tree>  getFunTreeByPermission(String userId);
	
	/**
	 * @return  功能树数据
	 */
	public List<Tree> getFunTree();
	
	/**
	 * @param value查询值
	 * @return 返回功能列表
	 */
	public Map<String,Object> getSfuns(String value);
	
	
	/**
	 * @return 返回父功能列表
	 */
	public List<SFun> getPaterFuns();
	
	
	/**
	 * <p>授权领先教育功能</p>
	 * @param userId 用户
	 * @param type 功能类型
	 * @return List
	 */
	public List<SFun> getLXEduFuns(String userId,String type);
	
	/**
	 * @see添加功能信息
	 * @param fun
	 * @return boolean
	 */
	boolean addFun(SFun record);
	
	/**
	 * @see更新功能信息
	 * @param record fun
	 * @return boolean
	 */
	boolean editFun(SFun record);
	
	/**
	 * @see删除功能
	 * @param uuid 
	 * @return boolean
	 */
	boolean delFun(String uuid);
	
	/**
	 * uuid查找多个功能
	 * @param uuids 
	 * @return List
	 */
	public List<SFun> getSFuns(List<String> uuids);
	
	/**
	 * 根据userId查找按照功能区分的permission
	 * @param uuids 
	 * @return List
	 */
	public  Map<String , Set<String>> getFunPermissionsByUserId(String userId);
	
}
