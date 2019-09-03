package com.evisible.os.controlcenter.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.SUserRoleMapper;
import com.evisible.os.controlcenter.system.entity.SUserRole;
import com.evisible.os.controlcenter.system.entity.SUserRoleExample;
import com.evisible.os.controlcenter.system.service.ISUserRoleService;


/**
 * <p>用户与角色关系</p>
 * @author TengDong
 * @Date 20160408
 */
@Service("suserRoleService")
public class SUserRoleService extends MybatisGenerator<SUserRoleMapper> implements ISUserRoleService{
	private static Logger log = LoggerFactory.getLogger(SUserRoleService.class);
	public SUserRoleService(){
		super(SUserRoleMapper.class);
	}

	/**
	 * 删除用户与角色关系
	 * */
	@Override
	public boolean delSUerRole(List<String> userRoleUuids) {
		try {
			SUserRoleExample example=new SUserRoleExample();
			example.createCriteria().andUuidIn(userRoleUuids);
			int sign=this.getDao().deleteByExample(example);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("----------------用户与角色关系异常------------------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 添加用户与角色关系
	 * **/
	@Override
	public boolean addUserRoles(List<SUserRole>  userRoleList) {
		try {
			int sign=this.getDao().addUserRoles(userRoleList);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("----------------添加用户与角色关系异常------------------");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询用户与角色关系
	 * */
	@Override
	public Map<String,Object> getUserRoles(PageUI pageUI) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			SUserRoleExample example=new SUserRoleExample();
			example.setOrderByClause("ur.createdate desc");
			example.setLimitValue(pageUI.getValue(), pageUI.getSecondValue());
			map.put("rows", this.getDao().selectByExampleKZ(example));
			map.put("total", this.getDao().countSelectByExampleKZ(example));
			return map;
			
		} catch (Exception e) {
			log.info("----------------查询用户与角色关系异常------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}

}
