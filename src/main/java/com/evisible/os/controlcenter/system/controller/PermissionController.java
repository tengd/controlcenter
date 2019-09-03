package com.evisible.os.controlcenter.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
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
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SPermission;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.IPermissionService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;


/**
 * <p>权限控制器,对应权限信息处理界面</p>
 * @author TengDong
 * @Date 20160414
 */
@Controller
@RequestMapping("per")
public class PermissionController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(PermissionController.class);
	@Autowired 
	private IPermissionService permissionService;
	@RequestMapping(value = "/getPermissions")
	@ResponseBody
	public Map<String,Object> getPermissions(String pname,@RequestParam("page") int page,
			@RequestParam("rows") int rows){
		PageUI pageUI=new PageUI(page,rows);
		return permissionService.getPermissions(pname, pageUI);
	}
	@RequestMapping(value = "/addSPermission", method = RequestMethod.POST)
	public void addSPermission(HttpServletRequest request){
		SPermission sPermission=new SPermission();
		String uuid=StringConvert.getUUIDString();
		sPermission.setUuid(uuid);
		sPermission.setFunId(request.getParameter("fun_uuid").toString());
		sPermission.setRoleId(request.getParameter("role_uuid").toString());
		String pName=request.getParameter("pName").toString();
		sPermission.setpName(pName);
		sPermission.setPermission(request.getParameter("permission").toString());
		sPermission.setCreator(( (User)request.getSession().getAttribute("currentUser")).getuName());
		try {
			//日志
			try {
				this.Log(request, uuid, pName);
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			permissionService.addSPermission(sPermission);
		} catch (Exception e) {
			log.info("------------权限错误------------------");
			e.printStackTrace();
		}
			
	}
	@RequestMapping(value = "delSPermission",method=RequestMethod.POST)
	@ResponseBody
	public Object delSPermission(HttpServletRequest request,
			@RequestBody Map<String, Object> param){
		Message message=new Message();
		String[] permissionIds=param.get("permission_ids").toString().split(",");
		List<String> uuids=new ArrayList<String>(); 
		for(int i=0;i<permissionIds.length;i++){
			//日志
			try {
				this.Log(request, permissionIds[i], "权限ID:"+permissionIds[i]+"被删除");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			uuids.add(permissionIds[i].toString());
		}
		if(permissionService.delSPermission(uuids)){
			message.setmsgCode(MsgConstant.SUCCESSCODE);
			message.setmsgContent(MsgConstant.SUCCESSCONTENT);
		}else{
			message.setmsgCode(MsgConstant.ERRORCODE);
			message.setmsgContent(MsgConstant.ERRORCONTENT);
		}
		return message;
	}
	
	@RequestMapping("/getPermissionsByPageName")
	@ResponseBody
	public String getPermissionsByPageName(HttpServletRequest request){
		String page = request.getParameter("pageurl");
		@SuppressWarnings("unchecked")
		Map<String, Set<String>> permissionMap =  (Map<String, Set<String>>) SecurityUtils.getSubject().getSession()
				.getAttribute("permissions");
		Set<String> permissions = permissionMap.get(page);
		StringBuffer sb = new StringBuffer();
		for(String permission : permissions){
			sb.append(permission);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

}
