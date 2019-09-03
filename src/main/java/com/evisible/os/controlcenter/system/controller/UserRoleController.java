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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SUserRole;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISUserRoleService;
import com.evisible.os.controlcenter.util.Descartes;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;

@Controller
@RequestMapping("userRole")
public class UserRoleController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(UserRoleController.class);
	@Autowired
	private ISUserRoleService suserRoleService;
	/**
	 * 添加用户与角色关系
	 * @param param 
	 * @return boolean
	 */
	@RequestMapping(value = "addUserRoles")
	@ResponseBody
	public Object addUserRoles(@RequestBody Map<String, Object> param,HttpServletRequest request){
		String[] roleIds=param.get("roleIds").toString().split(",");
		String[] userIds=param.get("userIds").toString().split(",");
		List<String> roleIdsList=new ArrayList<String>();
		List<String> userIdsList=new ArrayList<String>();
		for(int i=0;i<roleIds.length;i++){
			roleIdsList.add(roleIds[i].toString()+",");
		}
		for(int j=0;j<userIds.length;j++){
			userIdsList.add(userIds[j].toString()+",");
		}
		//求集合笛卡尔积
		List<List<String>> dimvalue= new ArrayList<List<String>>();
		dimvalue.add(roleIdsList);
		dimvalue.add(userIdsList);
		
		List<String> result=new ArrayList<String>();
		Descartes.run(dimvalue, result, 0, "");
		
		List<SUserRole>  userRoleList=new ArrayList<SUserRole>();
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
		for(String s:result){
			SUserRole sUserRole=new SUserRole();
			String uuid=StringConvert.getUUIDString();
			sUserRole.setUuid(uuid);
			String roleId=s.split(",")[0].toString();
			sUserRole.setRoleid(roleId);
			String userid=s.split(",")[1].toString();
			sUserRole.setUserid(userid);
			sUserRole.setCreator(user.getuName());
			//日志
			try {
				this.Log(request, uuid,"用户ID:"+userid+"角色ID:"+roleId+"建立了关系" );
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			userRoleList.add(sUserRole);
		}
		Message message=new Message();
		
		if(suserRoleService.addUserRoles(userRoleList)){
			message.setmsgCode(MsgConstant.SUCCESSCODE);
			message.setmsgContent(MsgConstant.SUCCESSCONTENT);
		}else{
			message.setmsgCode(MsgConstant.ERRORCODE);
			message.setmsgContent(MsgConstant.ERRORCONTENT);
		}
		return message;
	}
	
	@RequestMapping(value = "/getUserRoles")
	@ResponseBody
	public Map<String,Object> getUserRoles(@RequestParam("page") int page,@RequestParam("rows") int rows){
		PageUI pageUI = new PageUI(page, rows);
		return suserRoleService.getUserRoles(pageUI);
	}
	/**
	 * @see移除用户角色对应关系
	 * @param param
	 * @return Object
	 */
	@RequestMapping(value = "delUserRoles")
	@ResponseBody
	public Object delUserRoles(@RequestBody Map<String, Object> param,
			HttpServletRequest request){
		Message message=new Message();
		String[] userRoleIds=param.get("userRoleIds").toString().split(",");
		List<String> userRoleUuids=new ArrayList<String>(); 
		for(int i=0;i<userRoleIds.length;i++){
			//日志
			try {
				this.Log(request, userRoleIds[i],"角色ID:"+userRoleIds[i]+"对应的角色被移出" );
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			userRoleUuids.add(userRoleIds[i].toString());
		}
		if(suserRoleService.delSUerRole(userRoleUuids)){
			message.setmsgCode(MsgConstant.SUCCESSCODE);
			message.setmsgContent(MsgConstant.SUCCESSCONTENT);
		}else{
			message.setmsgCode(MsgConstant.ERRORCODE);
			message.setmsgContent(MsgConstant.ERRORCONTENT);
		}
		return message;
	}
}
