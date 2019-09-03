 package com.evisible.os.controlcenter.system.controller.soa;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Tree;
import com.evisible.os.controlcenter.model.vo.Fun;
import com.evisible.os.controlcenter.model.vo.FunPermission;
import com.evisible.os.controlcenter.model.vo.Permission;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SFun;
import com.evisible.os.controlcenter.system.entity.SPermission;
import com.evisible.os.controlcenter.system.service.IFunService;
import com.evisible.os.controlcenter.system.service.IPermissionService;
import com.evisible.os.controlcenter.util.RequestUtil;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;



/**
 * <p>功能控制器,对应界面的权限功能</p>
 * @author TengDong
 * @Date 20160406
 */
@Controller("funAppController")
@RequestMapping("/funApp") 
public class FunController extends BaseController{

	@Autowired 
	private IPermissionService permissionService;
	@Autowired 
	private IFunService funService;

	
	@RequestMapping(value="/getFunTreeByPermission", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object getFunTreeByPermission(HttpServletRequest request){
		try {
			JSONObject json = JSONObject.fromObject(extractPostRequestBody(request));
			return funService.getFunTreeByPermission(json.getString("userId"));
		} catch (NullPointerException | IOException nulle) {
			Tree tree=new Tree();
			tree.setText("系统没有此功能树或没有TREE类型功能");
			return tree;
		}
	}
	
	/**
	 * @param request
	 * @return
	 * @throws IOException
	 */
	static String extractPostRequestBody(HttpServletRequest request) throws IOException {
	    if ("POST".equalsIgnoreCase(request.getMethod())) {
	        Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	    }
	    return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getFun", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")  
	public String getFuns(HttpServletRequest request,
			HttpServletResponse response){
		try{
    		JSONObject validateObj = RequestUtil.ValidateToken(request);
    		if (validateObj == null|| validateObj.getJSONObject("MobileHead") == null|| 
    				validateObj.getJSONObject("MobileHead").getString("Code").equals("-1")) {
				return validateObj.toString();
			}
    		// 验证传递的参数是否为空
			String params = validateObj.getJSONObject("MobileHead").getString("Message");
			JSONObject paramJson = JSONObject.fromObject(params);
			//传参数
			ArrayList<String> paramNames = new ArrayList<String>();
			paramNames.add("roleId");
			JSONObject pValidateObj = RequestUtil.ValidateParams(paramJson,
					paramNames);
			if (pValidateObj == null|| pValidateObj.getString("Code").equals("-1")) {
				return ResponseUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR
								.getErrorCode(), pValidateObj
								.getString("Message"));
			}
			FunPermission funPermission=new FunPermission();
			String roleId=paramJson.getString("roleId").trim();
			funPermission.setRoleId(roleId);
			
			List<String> roleIds=new ArrayList<String>();
			roleIds.add(roleId);
			//权限
			List<SPermission> sPermissions=permissionService.getPermissionsByRoleId(roleIds);
			
			if(sPermissions.size()==0){
				return ResponseUtil.creComErrorResult("RolePermissionError001", "此角色没有权限，请联系管理员");
			}
			List<String> funIds=new ArrayList<String>();
			List<Permission> permissions=new ArrayList<Permission>();
			for(SPermission sPermissionList:sPermissions){
				Permission permission=new Permission();
				permission.setPid(sPermissionList.getUuid());
				permission.setPname(sPermissionList.getpName());
				permission.setPermission(sPermissionList.getPermission());
				funIds.add(sPermissionList.getFunId());
				permissions.add(permission);
			}
			funPermission.setPermissions(permissions);
			if(funIds.size()==0){
				return ResponseUtil.creComErrorResult("PermissionFunError001", "此权限没有功能，请联系管理员");
			}
			//功能
			List<SFun> sFuns=funService.getSFuns(funIds);
			if(sFuns.size()==0){
				return ResponseUtil.creComErrorResult("PermissionFunError002", "此权限没有功能，请联系管理员");
			}
			List<Fun> funs=new ArrayList<Fun>();
			for(SFun sfunlist:sFuns){
				Fun fun=new Fun();
				fun.setFid(sfunlist.getUuid());
				fun.setFname(sfunlist.getfName());
				fun.setFnamejc(sfunlist.getfNameJx());
				fun.setFtype(sfunlist.getFunType());
				fun.setSortIndex(sfunlist.getSortIndex());
				fun.setPaterId(sfunlist.getPaterId());
				fun.setUrl(sfunlist.getUrl());
				funs.add(fun);
			}
			funPermission.setFuns(funs);
			
			return this.ajax(response, ResponseUtil.creObjSucResult(funPermission), "application/json; charset=utf-8") ;
			
    	}catch(Exception e){
    		logger.error("登录（EngineerLogin）异常信息：" + e.getMessage());
			return ResponseUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.User_Code_Exist
							.getErrorCode(), "服务器请求错误");
    	}
    		        
	}
	
	/**
	 * 
	* @Description: 根据功能名称获取该功能名称下的不同用户的按钮的权限
	* @param funName 功能名称
	* @return List<String>    
	* @throws
	 */
//	public List<String> getButtonPermissionsByFun(String funName){
//		
//	}
	
}
