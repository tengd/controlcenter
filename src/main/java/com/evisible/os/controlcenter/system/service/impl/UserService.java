package com.evisible.os.controlcenter.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.UserMapper;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.entity.UserExample;
import com.evisible.os.controlcenter.system.entity.UserWithBLOBs;
import com.evisible.os.controlcenter.system.service.ISUserRoleService;
import com.evisible.os.controlcenter.system.service.IUserService;

@Service("userService")
public class UserService extends MybatisGenerator<UserMapper> implements IUserService {
	@Autowired
	private ISUserRoleService suserRoleService;
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	public UserService(){
		super(UserMapper.class);
	}
	@Override
	public int countUser() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validateUser(Map<String, Object> map) {
		
		return false;
	}

	@Override
	public boolean delUser(String uuid) {
		try {
			int sign=this.getDao().deleteByPrimaryKey(uuid);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("-----------删除用户异常------------");
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * 添加用户信息
	 * **/
	@Override
	public boolean insertUser(UserWithBLOBs record) {
			try {
				int sign=this.getDao().insertSelective(record);
				if(sign>0){
					return true;
				}
			} catch (Exception e) {
				log.info("-----------添加用户异常------------");
				e.printStackTrace();
			}
			return false;
		
	}

	/*
	 * 查询单个用户
	 * */
	@Override
	public User getUserByname(String uname) {
		return null;
	}

	/*
	 * <p>查询单条用户信息</p>
	 * **/
	@Override
	public User getUser(User user) {
		try{
			UserExample example=new UserExample();
			example.setDistinct(true);
			//用户为正常非锁定用户
			//example.or().andUNameEqualTo(user.getuName()).andPasEqualTo(user.getPas()).andLockedEqualTo(0).andSignEqualTo(0);
			example.or().andAccountEqualTo(user.getAccount()).andPasEqualTo(user.getPas()).andLockedEqualTo(0).andSignEqualTo(0);
			List<User> userList=this.getDao().selectByExample(example);
			if(userList.size()==0){
				log.info("---------------没有此用户-----------------------");
				return null;
			}
			return userList.get(0);
		}catch(Exception e){
			log.info("---------------根据用户名查询单个用户信息出错-----------------------");
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * <p>查询单条用户信息</p>
	 * **/
	@Override
	public User getUserByToken(User user) {
		try{
			UserExample example=new UserExample();
			example.setDistinct(true);
			example.or().andSaltEqualTo(user.getSalt()).andLockedEqualTo(0);
			List<User> userList=this.getDao().selectByExample(example);
			if(userList.size()==0){
				log.info("---------------没有此用户-----------------------");
				return null;
			}
			return userList.get(0);
		}catch(Exception e){
			log.info("---------------根据用户名查询单个用户信息出错-----------------------");
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 获取用户信息
	 * */
	@Override
	public Map<String,Object> getUsers(String uname,PageUI pageUI) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> example=new HashMap<String,Object>();
			example.put("uname", uname);
			example.put("value", pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			map.put("rows", this.getDao().selectByExampleKZ(example));
			map.put("total",  this.getDao().countByExample(null));
			return map;
		} catch (Exception e) {
			log.info("---------------获取用户信息出错-----------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}
	
	/*
	 * 获取用户信息
	 * */
	@Override
	public Map<String,Object> getUsersByNotLocked(String uname,PageUI pageUI) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> example=new HashMap<String,Object>();
			example.put("uname", uname);
			example.put("value", pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			map.put("rows", this.getDao().getUsersByNotLocked(example));
			
			map.put("total", this.getDao().countUsersByNotLocked(example));
			return map;
		} catch (Exception e) {
			log.info("---------------获取用户信息出错-----------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}
	/*
	 * 更新用户
	 * **/
	@Override
	public boolean updateUser(UserWithBLOBs record) {
		try {
			int sign=this.getDao().updateByPrimaryKeySelective(record);
			if(sign>0){
				return true;
			}
		} catch (Exception e) {
			log.info("-----------------更新用户异常--------------------");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * <p>手机号初始化密码</p>
	 * @param User
	 * @return boolean
	 */
	public boolean initPass(User record) {
		try {

			UserWithBLOBs user=new UserWithBLOBs();
			user.setPas(record.getPas());
			user.setSalt(record.getSalt());
			
			UserExample example=new UserExample();
			UserExample.Criteria criteria=example.createCriteria();
			criteria.andPhoneEqualTo(record.getPhone());
			
			
			int sign=this.getDao().updateByExampleSelective(user, example);
			if(sign>0){
				return true;
			}
		} catch (Exception e) {
			log.info("-----------------更新用户异常--------------------");
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public User validateToken(String token) {
		User user = new User();
		user.setSalt(token);
		try {
			user = getUserByToken(user);
		}catch(Exception e){
			log.info("-----------------获得token用户异常--------------------");
			e.printStackTrace();
			return null;		
		}
		return user;
	}
	
	
}
