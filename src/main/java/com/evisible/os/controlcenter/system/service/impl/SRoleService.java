package com.evisible.os.controlcenter.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.SRoleMapper;
import com.evisible.os.controlcenter.system.dao.SUserRoleMapper;
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.entity.SUserRoleExample;
import com.evisible.os.controlcenter.system.service.ISRoleService;
@Service("sroleService")
public class SRoleService extends MybatisGenerator<SRoleMapper> implements ISRoleService{
	private static Logger log = LoggerFactory.getLogger(SRoleService.class);
	
	public SRoleService(){
		super(SRoleMapper.class);
	}
	/**
	 * 获得所有角色
	 * */
	@Override
	public Map<String,Object> getRoles(PageUI pageUI,String rname) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> example=new HashMap<String,Object>();
			example.put("rname", rname);
			example.put("value", pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			
			map.put("rows", this.getDao().selectByExampleKZ(example));
			example.clear();
			map.put("total", this.getDao().countByExample(null));
			return map;
		} catch (Exception e) {
			log.info("------------------获取所有角色异常--------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}
	
	/**
	 * @see 用户获得角色
	 * */
	@Override
	public List<SRole> getRolesByUser(String UserId) {
		if(UserId==null || UserId.isEmpty()) return null;
		try {
			return this.getDao().selectByExampleKZByUserId(UserId);
		} catch (Exception e) {
			log.info("------------------用户获得角色异常--------------------");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加角色
	 * */
	@Override
	public boolean addRole(SRole role) {
		try {
			int sign=this.getDao().insertSelective(role);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("------------------添加角色异常--------------------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 修改角色
	 * */
	@Override
	public boolean editRole(SRole role) {
		try {
			int sign=this.getDao().updateByPrimaryKeySelective(role);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("------------------修改角色异常--------------------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除角色与角色对应的用户关系
	 * @throws Exception 
	 * */
	@Override
	@Transactional
	public boolean delRole(String role_uuid) {//事务处理
		SqlSession session=this.getSqlSessionTemplate().getSqlSessionFactory().openSession();
		 try {
			  //session.getConnection().setAutoCommit(false); // 设置自动提交为false
				 SRoleMapper mapper1 = session.getMapper(SRoleMapper.class);
				 mapper1.deleteByPrimaryKey(role_uuid);
				 
				 SUserRoleMapper mapper2=session.getMapper(SUserRoleMapper.class);
				 SUserRoleExample example=new SUserRoleExample();
				 example.createCriteria().andRoleidEqualTo(role_uuid);
				 mapper2.deleteByExample(example);
			 session.commit();
			 return true;
		} catch (Exception e) {
			session.rollback();
			log.info("-------------删除角色与角色对应的用户关系异常---------------");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return false;
	}
	

}
