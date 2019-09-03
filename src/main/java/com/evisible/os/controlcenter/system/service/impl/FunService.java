package com.evisible.os.controlcenter.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.Attributes;
import com.evisible.os.controlcenter.model.Tree;
import com.evisible.os.controlcenter.model.TreeChildren;
import com.evisible.os.controlcenter.system.dao.SFunMapper;
import com.evisible.os.controlcenter.system.dao.SPermissionMapper;
import com.evisible.os.controlcenter.system.entity.SFun;
import com.evisible.os.controlcenter.system.entity.SFunExample;
import com.evisible.os.controlcenter.system.entity.SPermission;
import com.evisible.os.controlcenter.system.entity.SPermissionExample;
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.service.IFunService;
import com.evisible.os.controlcenter.system.service.ISRoleService;



@Service("funService")
public class FunService extends MybatisGenerator<SFunMapper> implements IFunService {
	private static Logger log = LoggerFactory.getLogger(FunService.class);
	@Autowired
	private ISRoleService sroleService;
	@Autowired
	private PermissionService permissionService;
	
    public FunService(){
    	super(SFunMapper.class);
    }
  
	private List<String> getFunId(String userId){
    	//获得角色
		 List<SRole> rolesList=sroleService.getRolesByUser(userId);
		//获得权限
		 List<String> rolesId=new ArrayList<String>();
		 for(SRole roles:rolesList){
			 rolesId.add(roles.getUuid());
		 }
		 if(rolesId.size()==0){
			 log.error("----------------------请给此用户角色-------------------");
			 return null; 
		 }
		 List<SPermission> permissionsList=permissionService.getPermissionsByRoleId(rolesId);
		//获得功能树ID
		 List<String> funId=new  ArrayList<String>();
		 for(SPermission permissions:permissionsList){
			 funId.add(permissions.getFunId());
		 }
		 if(funId.size()==0){
			 log.error("----------------------请给此用户权限-------------------");
			 return null;
		 }
		 return funId;
    }
	
	/**
	 *@see 权限树
	 * @see功能类型为tree
	 * **/
	@Override
	public List<Tree> getFunTreeByPermission(String userId) {
		List<String> funId=this.getFunId(userId);
		 try{
				//获得叶子节点
				SFunExample example=new SFunExample();
				SFunExample.Criteria criteria=example.createCriteria();
				criteria.andFunTypeEqualTo("tree");
				criteria.andUuidIn(funId);
				example.setOrderByClause("sort_index asc");
				List<SFun> nodeList=this.getDao().selectByExample(example);
				
				List<String> paterIdList=new ArrayList<String>();
				
				for(SFun children:nodeList){
					paterIdList.add(children.getPaterId());
				}
				if(paterIdList.size()==0){
					 log.error("----------------------此功能需要有个父功能-------------------");
					 return null;
				}
				Set<String> paterId=new HashSet<String>();
				paterId.addAll(paterIdList);
				paterIdList.clear();
				paterIdList.addAll(paterId);
				
				//根据父节点找叶子节点
				List<Tree> treeList=new ArrayList<Tree>();
				
				for(String paterUuid:paterIdList){
					Tree tree=new Tree();
					if(paterUuid==null||paterUuid.isEmpty()) continue;
					SFun sfun=this.getDao().selectByPrimaryKey(paterUuid);
					tree.setId(sfun.getUuid());
					tree.setState("open");
					tree.setText(sfun.getfName());
					example.clear();
					SFunExample.Criteria criteriaP=example.createCriteria();
					//权限ID
					criteriaP.andUuidIn(funId);
					criteriaP.andPaterIdEqualTo(paterUuid);
					
					example.setOrderByClause("sort_index asc");
					List<SFun> funlist=this.getDao().selectByExample(example);
					List<TreeChildren> childrens=new ArrayList<TreeChildren>();
					for(SFun funs:funlist){
						TreeChildren treeChildren=new TreeChildren();
						treeChildren.setId(funs.getUuid());
						treeChildren.setState("open");
						treeChildren.setText(funs.getfName());
						Attributes attributes=new Attributes();
						attributes.setUrl(funs.getUrl());
						treeChildren.setAttributes(attributes);
						childrens.add(treeChildren);
					}
					tree.setChildren(childrens);
					treeList.add(tree);
				}
				
				
				
				return treeList;
				
			}catch(Exception e){
				log.info("----------------------组织功能功能树异常-------------------");
				e.printStackTrace();
				return null;
			}
		
	}
	/**
	 *@see 查功能列表
	 * **/
	@Override
	public Map<String,Object> getSfuns(String value) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.clear();
			map.put("rows", this.getDao().selectByExampleKZByValue(value));
			map.put("total", this.getDao().countByExample(null));
		} catch (Exception e) {
			log.info("----------------------查功能列表异常-------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}
	/**
	 * @see查询父功能列表
	 * **/
	@Override
	public List<SFun> getPaterFuns() {
		try {
			SFunExample example=new SFunExample();
			example.or().andPaterIdEqualTo("");
			example.or().andPaterIdIsNull();
			return this.getDao().selectByExample(example);
		} catch (Exception e) {
			log.info("----------------------查询父功能列表异常-------------------");
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 *@see 添加功能信息
	 * **/
	@Override
	public boolean addFun(SFun record) {
		try {
			int sign=this.getDao().insertSelective(record);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("----------------------添加功能信息异常-------------------");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * @see更新功能信息
	 * */
	@Override
	public boolean editFun(SFun record) {
		try {
			int sign=this.getDao().updateByPrimaryKeySelective(record);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("----------------------更新功能信息异常-------------------");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * @see 删除功能
	 * */
	@Override
	public boolean delFun(String uuid) {//事务处理
		SqlSession session=this.getSqlSessionTemplate().getSqlSessionFactory().openSession();
		try {
			session.getConnection().setAutoCommit(false); // 设置自动提交为false
			//删除功能对应的权限
			SPermissionMapper mapper1=session.getMapper(SPermissionMapper.class);
			SPermissionExample example=new SPermissionExample();
			example.createCriteria().andFunIdEqualTo(uuid);
			mapper1.deleteByExample(example);
			
			SFunMapper mapper2=session.getMapper(SFunMapper.class);
			mapper2.deleteByPrimaryKey(uuid);
		
			session.commit();
			return true;
		} catch (Exception e) {
			session.rollback();
			log.info("----------------------删除功能信息异常-------------------");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return false;
	}
	/**
	 * @see 功能树
	 * */
	@Override
	public List<Tree> getFunTree() {
		try{
			//获得父节点
			SFunExample example=new SFunExample();
			example.or().andPaterIdEqualTo("");
			example.or().andPaterIdIsNull();
			example.setOrderByClause("sort_index desc");
			List<SFun> funPList=this.getDao().selectByExample(example);
			
			List<Tree> trees=new ArrayList<Tree>();
			//获得子节点
			for(SFun funP:funPList){
				Tree tree=new Tree();
				tree.setId(funP.getUuid());
				tree.setState("open");
				tree.setText(funP.getfName());
				//获取子节点
				SFunExample exampleChild=new SFunExample();
				SFunExample.Criteria criteriaChild=exampleChild.createCriteria();
				criteriaChild.andPaterIdEqualTo(funP.getUuid());
				criteriaChild.andFunTypeEqualTo("tree");
				exampleChild.setOrderByClause("sort_index desc");
				List<SFun> funCList=this.getDao().selectByExample(exampleChild);
				List<TreeChildren> listctree=new ArrayList<TreeChildren>();
				 for(SFun funC:funCList){
					 TreeChildren children=new TreeChildren();
					 children.setId(funC.getUuid());
					 children.setText(funC.getfName());
					 children.setState("open");
					 Attributes attributes=new  Attributes();
					 attributes.setUrl(funC.getUrl());
					 children.setAttributes(attributes);
					 listctree.add(children);
				 }
				 tree.setChildren(listctree);
				 trees.add(tree);
			}
			
			return trees;
			
		}catch(Exception e){
			log.info("----------------------组织功能功能树异常-------------------");
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * @see根据用户获得领先教育功能
	 * */
	@Override
	public List<SFun> getLXEduFuns(String userId,String type) {
		//授权功能
		try {
			List<String> funids=this.getFunId(userId);
			SFunExample example=new SFunExample();
			SFunExample.Criteria criteria=example.createCriteria();
			criteria.andUuidIn(funids);
			criteria.andFunTypeEqualTo(type);
//			example.setOrderByClause("sort_index desc");
			return this.getDao().selectByExample(example);
		} catch (Exception e) {
			log.info("----------------------根据用户获得领先教育功能-------------------");
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * uuid查找多个功能
	 * @param uuids 
	 * @return List
	 */
	public List<SFun> getSFuns(List<String> uuids) {
		try{
			SFunExample example=new SFunExample();
			SFunExample.Criteria criteria=example.createCriteria();
			criteria.andUuidIn(uuids);
			return this.getDao().selectByExample(example);
		}catch(Exception e){
			log.info("----------------------根据角色功能出错-------------------");
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 
	* @Description: 根据用户Id获取该用户不同fun下的permission列表 
	* @param  userId 
	* @return Map<String,List<String>>  返回map， key代表function的名称， List中存放该function下的permission列表
	* @throws
	 */
	public  Map<String , Set<String>> getFunPermissionsByUserId(String userId){
		//根据userId获取当前User拥有的角色
		List<SRole> roleList = sroleService.getRolesByUser(userId);
		List<String> roleIdList = new ArrayList<>();
		List<SPermission> permissionList = new ArrayList<>();
		Map<String , Set<String>> permissionMap = new HashMap<>();
		for(SRole role : roleList){
			roleIdList.add(role.getUuid());
		}
		if(roleList.size()>0){
			permissionList = permissionService.getPermissionsByRoleId(roleIdList);
		}
		for(SPermission permission : permissionList){
			String permissionStr = permission.getPermission();
			String fun_id = permission.getFunId();
			List<String> funIds = new ArrayList<>();
			funIds.add(fun_id);
			SFun fun = this.getSFuns(funIds).get(0);
			String[] funUrlArr = fun.getUrl().split("/");
			String funName = funUrlArr[funUrlArr.length-1];
			String[] permissionArg = permissionStr.split(":");
			if(permissionArg.length != 3){
				return null;
			}
			String[] permissionArr = permissionArg[2].split(",");
			//利用HashSet不重复特性， 得到该角色的所有去重的permission
			Set<String> permissionSet = new HashSet<>();
			for(String per : permissionArr){
				permissionSet.add(per);
			}
			if(!permissionSet.contains("*")){
				//hashmap相同key插入的时候，value会被覆盖， 为了合并权限， 在放入map前要先判断该key是否存在
				if(!permissionMap.containsKey(funName)){
					permissionMap.put(funName,permissionSet);
				}else{
					Set<String> permissionSet_old = permissionMap.get(funName);
					permissionSet_old.addAll(permissionSet);
					permissionMap.put(funName,permissionSet_old);
				}
				
			}else{
				Set<String> set = new HashSet<>();
				set.add("*");
				permissionMap.put(funName,set);
			}
			
		}
		return  permissionMap;
	}

	
	

}
