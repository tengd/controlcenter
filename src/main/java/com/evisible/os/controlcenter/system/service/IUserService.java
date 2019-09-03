package com.evisible.os.controlcenter.system.service;

import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.entity.UserWithBLOBs;

/**
 * <p>用户</p>
 * @author TengDong
 * @Date
 */
public interface IUserService {
	static final String Mapper="com.usercenter.system.dao.sql.UserMapper.";
	/**
 	 * 统计用户
 	 * @return int
 	 */
 	int countUser();
	
	/**
	 * 验证用户
	 * @param map
	 * @return boolean
	 */
	boolean validateUser(Map<String,Object> map);
	

	/**
	 * @param value 查询值
	 * @param pageUI  分页
	 * @return
	 */
	Map<String,Object> getUsers(String value,PageUI pageUI);
	
	Map<String,Object> getUsersByNotLocked(String uname,PageUI pageUI);
	/**
	 * <p>删除用户</p>
	 * @param uuid 
	 * @return boolean
	 */
	boolean delUser(String uuid);
	/**
	 * <p>插入用户</p>
	 * @param record 用户实体
	 * @return boolean
	 */
	boolean insertUser(UserWithBLOBs record);
	
	/**
	 * @param uname 登录用户
	 * @return 获得单个用户
	 */
	User getUserByname(String uname);
	
	
	/**
	 * <p>取单个用户</p>
	 * @param user
	 * @return
	 */
	User getUser(User user);
	
	/**
	 * <p>更新用户</p>
	 * @param record  用户实体
	 * @return boolean
	 */
	boolean updateUser(UserWithBLOBs record);
	
	/**
	 * <p>手机号初始化密码</p>
	 * @param User
	 * @return boolean
	 */
	boolean initPass(User record);
	
	/**
	 * <p>根据token查找用户</p>
	 * @param user
	 * @return User
	 */
	User getUserByToken(User user);
	
	/**
	 * <p>验证外部接口传来的token是否合法</p>
	 */
	User validateToken(String token);
	
}
