package com.evisible.os.controlcenter.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SOrg;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISOrgService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * <p>组织控制器,对应组织管理界面</p>
 * @author TengDong
 * @date 20160908
 */
@Controller
@RequestMapping("/org")
public class OrgController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(OrgController.class);
	@Autowired
    private ISOrgService sOrgService;
	/**
	 * 获得组织
	 * @return List<Org>
	 */
	@RequestMapping("/getParentOrgs") 
	@ResponseBody
	public Object getParentOrgs(HttpServletRequest request) {
		Message message=new Message();
		try {
			List<SOrg> parentOrgs=sOrgService.getParentOrgs();
			if(parentOrgs.size()==0){
				message.setmsgCode(MsgConstant.FAILCODE);
				message.setmsgContent(MsgConstant.FAILCONTENT+"请建立组织");
				return message;
			}
			return parentOrgs;
		} catch (Exception e) {
			log.info("-----获得父组织异常-----"+e.getMessage());
			message.setmsgCode(MsgConstant.FAILCODE);
			message.setmsgContent(MsgConstant.FAILCONTENT);
			return message;
		}
	}
	/**
	 * 获得子组织
	 * @return List<Org>
	 */
	@RequestMapping("/getChildrenOrgs") 
	@ResponseBody
	public Object getChildrenOrgs(HttpServletRequest request) {
		Message message=new Message();
		String parentId=request.getParameter("parentId").trim();
		if(!StringUtils.isNotEmpty(parentId)){
			message.setmsgCode("paamNullError001");
			message.setmsgContent("参数接收错误");
			return message;
		}
		try {
			List<String> parentIds=new ArrayList<String>();
			parentIds.add(parentId);
			List<SOrg> childrenOrgs=sOrgService.getChildrenOrgs(parentIds);
			if(childrenOrgs.size()==0){
				message.setmsgCode(MsgConstant.FAILCODE);
				message.setmsgContent(MsgConstant.FAILCONTENT+"请建立子组织");
				return message;
			}
			return childrenOrgs;
		} catch (Exception e) {
			log.info("-----获得子组织异常-----"+e.getMessage());
			message.setmsgCode(MsgConstant.FAILCODE);
			message.setmsgContent(MsgConstant.FAILCONTENT);
			return message;
		}
	}
	/**
	 * 获得树形组织
	 * @return List<Org>
	 */
	@RequestMapping("/getTreeOrgs") 
	@ResponseBody
	public Object getTreeOrgs(HttpServletRequest request) {
	        return sOrgService.getOrgTreeToJson();
	}
	
	/**
	 * 获得组织
	 * @return List<OrgTreeNode>
	 */
	@RequestMapping("/getOrgTreeNode") 
	@ResponseBody
	public Object getTreeNodeOrg(HttpServletRequest request) {
	        return sOrgService.getOrgTreeNode();
	}
	
	/**
	 * 添加组织
	 * @return List<OrgTreeNode>
	 */
	@RequestMapping(value = "/doOrg", method = RequestMethod.POST)
	@ResponseBody
	public Object doOrg(HttpServletRequest request,
			@RequestBody Map<String, Object> param){
		Message message=new Message();
		String uuid=StringConvert.getUUIDString();
		String orgCode=param.get("orgCode").toString().trim();
		String orgName=param.get("orgName").toString().trim();
		String parentCode=param.get("parentCode").toString().trim();	
		String parentId=param.get("id").toString().trim();
		String sortIndex=param.get("sortIndex").toString().trim();
		// 当前用户
		User user = (User) request.getSession().getAttribute("currentUser");
		if(user==null){
			log.info("------------------用户Session为空了----------------------");
			try {
				throw new NullPointerException("Session ERROR NULL: " + Class.class+ ":" + Class.class.getMethods());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SOrg recode=new SOrg();
		recode.setUuid(uuid);
		recode.setOrgCode(orgCode);
		recode.setOrgName(orgName);
		recode.setParentCode(parentCode);
		recode.setParentId(parentId);
		if(sortIndex.isEmpty()&&sortIndex=="")sortIndex="0";
		recode.setSortIndex(new Integer(sortIndex));
		recode.setCreator(user.getuName());
		if(sOrgService.addOrg(recode)){
			message.setmsgCode(MsgConstant.SUCCESSCODE);
			message.setmsgContent(MsgConstant.SUCCESSCONTENT);
		}else{
			message.setmsgCode(MsgConstant.ERRORCODE);
			message.setmsgContent(MsgConstant.ERRORCONTENT);
		}
		return message;
			
		
	}
	
	/**
	 * @see删除组织
	 * */
	@RequestMapping(value = "/delOrg", method = RequestMethod.POST)
	@ResponseBody
	public Object delOrg(HttpServletRequest request,
			@RequestParam("uuid") String uuid){
		return null;
	}
	
	
}
