package com.evisible.os.controlcenter.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.OrgTreeNode;
import com.evisible.os.controlcenter.system.dao.SOrgMapper;
import com.evisible.os.controlcenter.system.entity.SOrg;
import com.evisible.os.controlcenter.system.entity.SOrgExample;
import com.evisible.os.controlcenter.system.service.ISOrgService;

/**
 * <p>组织Service</p>
 * @author TengDong
 * @Date 20160815
 */
@Service("sOrgService")
public class SOrgService extends MybatisGenerator<SOrgMapper> implements ISOrgService{
	private static Logger log = LoggerFactory.getLogger(SOrgService.class);
	
	public SOrgService(){
		super(SOrgMapper.class);
	}
	/**
	 * 根据组织ids获得组织
	 * */
	@Override
	public List<SOrg> getOrgs(List<String> orgids) {
		try{
			SOrgExample example=new SOrgExample();
			SOrgExample.Criteria criteria=example.createCriteria();
			criteria.andUuidIn(orgids);
			return this.getDao().selectByExample(example);
		}catch(Exception e){
			log.info("------根据组织ids获得组织-----");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得父组织
	 * @return 返回父组织S
	 */
	@Override
	public List<SOrg> getParentOrgs() {
		try{
			SOrgExample example=new SOrgExample();
			SOrgExample.Criteria criteria=example.createCriteria();
			criteria.andParentIdIsNull().andParentCodeIsNull();
			return this.getDao().selectByExample(example);
		}catch(Exception e){
			log.info("------获得父组织-----"+e.getMessage());
		}
		return null;
	}
	
	
	
	/**
	 * 根据父组织parentIds获得子组织
	 * @param parentIds
	 * @return 返回了组织S
	 */
	@Override
	public List<SOrg> getChildrenOrgs(List<String> parentIds) {
		try{
			SOrgExample example=new SOrgExample();
			SOrgExample.Criteria criteria=example.createCriteria();
			criteria.andParentIdIn(parentIds);
			
			return this.getDao().selectByExample(example);
		
		}catch(Exception e){
			log.info("------根据父组织parentIds获得子组织异常-----"+e.getMessage());
		}
		return null;
	}
	/**
	   * 创建一颗树，以json字符串形式返回
	   * @return 树
	   */
	@Override
	public String getOrgTreeToJson() {
		SOrgExample example=new SOrgExample();
		example.setOrderByClause("parent_id,sort_index asc");
		List<SOrg> list=this.getDao().selectByExample(null);
		return this.createTreeJson(list);
	}
	
	
	  
	/**
	   * 创建一颗树，以json字符串形式返回
	   * @param list 原始数据列表
	   * @return 树
	   */
	  private String createTreeJson(List<SOrg> list) {
	    JSONArray rootArray = new JSONArray();
	    for (int i=0; i<list.size(); i++) {
	       SOrg resource = list.get(i);
	      if (resource.getParentId()==null||resource.getParentId()==""||resource.getParentId().isEmpty()) {
	        JSONObject rootObj = createBranch(list, resource);
	        rootArray.add(rootObj);
	      }
	    }
	    
	    return rootArray.toString();
	  }
	  
	  
	/**
	   * 递归创建分支节点Json对象
	   * @param list 创建树的原始数据
	   * @param currentNode 当前节点
	   * @return 当前节点与该节点的子节点json对象
	   */
	  private JSONObject createBranch(List<SOrg> list, SOrg currentNode) {
	    /*
	     * 将javabean对象解析成为JSON对象
	     */
	    JSONObject currentObj = JSONObject.fromObject(currentNode);
	    JSONArray childArray = new JSONArray();
	    /*
	     * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
	     * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
	     * 节点放入当前节点的子节点列表中
	     */
	    for (int i=0; i<list.size(); i++) {
	    	SOrg newNode = list.get(i);
	      if (newNode.getParentId()!=null &&newNode.getParentId()!=""&&!newNode.getParentId().isEmpty()
	    		  && newNode.getParentId().compareTo(currentNode.getUuid()) == 0) {
	        JSONObject childObj = createBranch(list, newNode);
	        childArray.add(childObj);
	      }
	    }
	    
	    /*
	     * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
	     */
	    if (!childArray.isEmpty()) {
	      currentObj.put("children", childArray);
	    }
	    
	    return currentObj;
	  }
	
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  /*****************************此处可通用CLASS T**********************************/
	  
	  /**
		 * 获得组织combotree
		 * @return 组织s
		 */
		public String getOrgTreeNode(){
			SOrgExample example=new SOrgExample();
			example.setOrderByClause("parent_id,sort_index asc");
			List<SOrg> list=this.getDao().selectByExample(null);
			List<OrgTreeNode> listOrgTreeNode=new ArrayList<OrgTreeNode>();
			for(SOrg orgs:list){
				OrgTreeNode orgTreeNode=new OrgTreeNode();
				orgTreeNode.setId(orgs.getUuid());
				orgTreeNode.setText(orgs.getOrgName());
				orgTreeNode.setParentId(orgs.getParentId());
				orgTreeNode.setParentCode(orgs.getParentCode());
				listOrgTreeNode.add(orgTreeNode);
			}
			return this.createTreeNodeJson(listOrgTreeNode);
		}
		/**
		   * 创建一颗树，以json字符串形式返回
		   * @param list 原始数据列表
		   * @return 树
		   */
		  private String createTreeNodeJson(List<OrgTreeNode> list) {
			  JSONArray rootArray = new JSONArray();
			  for (int i=0; i<list.size(); i++) {
				  OrgTreeNode resource = list.get(i);
			      if (resource.getParentId()==null||resource.getParentId()==""||resource.getParentId().isEmpty()) {
			        JSONObject rootObj = createBranchTreeNode(list, resource);
			        rootArray.add(rootObj);
			      }
			    }
			    
			    return rootArray.toString();
		  }
		  
		
	  /**
	   * 递归创建分支节点Json对象
	   * @param list 创建树的原始数据
	   * @param currentNode 当前节点
	   * @return 当前节点与该节点的子节点json对象
	   */
	  private JSONObject createBranchTreeNode(List<OrgTreeNode> list, OrgTreeNode currentNode) {
		  /*
		     * 将javabean对象解析成为JSON对象
		     */
		    JSONObject currentObj = JSONObject.fromObject(currentNode);
		    JSONArray childArray = new JSONArray();
		    /*
		     * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		     * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
		     * 节点放入当前节点的子节点列表中
		     */
		    for (int i=0; i<list.size(); i++) {
		    	OrgTreeNode newNode = list.get(i);
			      if (newNode.getParentId()!=null &&newNode.getParentId()!=""&&!newNode.getParentId().isEmpty()&& newNode.getParentId().compareTo(currentNode.getId()) == 0) {
			        JSONObject childObj = createBranchTreeNode(list, newNode);
			        childArray.add(childObj);
			     
			      }
			    
			    /*
			     * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
			     */
			    if (!childArray.isEmpty()) {
			      currentObj.put("children", childArray);
			    }
		    }
		    return currentObj;
	  }
	  
	  
	  /********************组织DML操作**************************/  
	 
	/*
	 * 删除组织
	 * @param SOrg org组织
	 * @return boolean
	 * */
	public boolean addOrg(SOrg org) {
		try{
			int sign=this.getDao().insertSelective(org);
			if(sign>0)return true;
		}catch(Exception e){
			log.info("---------添加组织信息异常--------");
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 编辑组织
	 * @param SOrg org组织
	 * @return boolean
	 * */
	public boolean editOrg(SOrg org) {
		try{
			int sign=this.getDao().updateByPrimaryKeySelective(org);
			if(sign>0)return true;
		}catch(Exception e){
			log.info("----编辑组织异常-----");
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 删除组织
	 * @param uuid 组织id
	 * @return boolean
	 * */
	public boolean delOrg(String uuid) {
		try{
			int sign=this.getDao().deleteByPrimaryKey(uuid);
			if(sign>0)return true;
		}catch(Exception e){
			log.info("-----删除组织异常------");
			e.printStackTrace();
		}
		return false;
	}
	
	
	  
	
	  
	

}
