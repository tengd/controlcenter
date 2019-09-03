package com.evisible.os.controlcenter.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.SPermissionMapper;
import com.evisible.os.controlcenter.system.entity.SPermission;
import com.evisible.os.controlcenter.system.entity.SPermissionExample;
import com.evisible.os.controlcenter.system.service.IPermissionService;
@Service("permissionService")
public class PermissionService extends MybatisGenerator<SPermissionMapper> implements IPermissionService {
	private static Logger log = LoggerFactory.getLogger(PermissionService.class);
	
	public PermissionService(){
		super(SPermissionMapper.class);
	}
	/**
	 * 获得所有权限信息
	 * */
	@Override
	public Map<String,Object> getPermissions(String pName, PageUI pageUI) {
		Map<String,Object> example=new HashMap<String,Object>();
		Map<String,Object> map=new HashMap<String,Object>();
		example.clear();
		try {
			example.put("pName", pName);
			example.put("value",pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			map.put("rows", this.getDao().selectByExampleKZ11(example));
			map.put("total", this.getDao().countByExample(null));
			return map;
		} catch (Exception e) {
			log.info("--------------获得所有权限信息异常----------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}

	/**
	 * 批量删除
	 * */
	@Override
	public boolean delSPermission(List<String> uuids) {
		try {
			SPermissionExample example=new SPermissionExample();
			example.createCriteria().andUuidIn(uuids);
			int sign=this.getDao().deleteByExample(example);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("--------------删除用户权限异常----------------");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加权限规则
	 * **/
	@Override
	public boolean addSPermission(SPermission permission) {
		try {
			int sign=this.getDao().insertSelective(permission);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("--------------添加权限规则信息异常----------------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 编辑权限
	 * */
	@Override
	public boolean editSPermission(SPermission permission) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 角色对应权限
	 * **/
	public List<SPermission> getPermissionsByRoleId(List<String> RoleId) {
		try {
			List<SPermission> sPermissions=new ArrayList<SPermission>();
			SPermissionExample example=new SPermissionExample();
			SPermissionExample.Criteria criteria=example.createCriteria();
			criteria.andRoleIdIn(RoleId);
			sPermissions=this.getDao().selectByExample(example);
			return sPermissions;
		} catch (Exception e) {
			log.info("--------------角色对应权限信息异常----------------");
			e.printStackTrace();
		}
		return null;
	}

}
