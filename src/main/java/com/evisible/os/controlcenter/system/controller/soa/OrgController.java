package com.evisible.os.controlcenter.system.controller.soa;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SOrg;
import com.evisible.os.controlcenter.system.entity.SOrgUser;
import com.evisible.os.controlcenter.system.service.ISOrgService;
import com.evisible.os.controlcenter.system.service.ISOrgUserService;
import com.evisible.os.controlcenter.util.RequestUtil;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;


/**
 * <p>组织控制器,界面组织</p>
 * @author TengDong
 * @date 20160816
 */
@Controller("OrgAppController")
@RequestMapping("/orgApp") 
public class OrgController extends BaseController {
	  @Autowired
      private ISOrgUserService sOrgUserService;
	  @Autowired
      private ISOrgService sOrgService;
	  
	  @ResponseBody
	  @RequestMapping(value="/getOrg", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")  
	  public String getOrg(HttpServletRequest request, 
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
				paramNames.add("userId");
				JSONObject pValidateObj = RequestUtil.ValidateParams(paramJson,
						paramNames);
				if (pValidateObj == null|| pValidateObj.getString("Code").equals("-1")) {
					return ResponseUtil.creComErrorResult(
							ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR
									.getErrorCode(), pValidateObj
									.getString("Message"));
				}
				String userId=paramJson.getString("userId").trim();
				
				com.evisible.os.controlcenter.model.vo.UserOrg userOrg=new com.evisible.os.controlcenter.model.vo.UserOrg();
				userOrg.setUserId(userId);
				List<SOrgUser> orgUsers=sOrgUserService.getOrgUsers(userId);
				if(orgUsers.size()==0){
					ResponseUtil.creComErrorResult("OreUser001", "此用户没有组织,联系管理员");
				}
				List<String> orgIds=new ArrayList<String>();
				for(SOrgUser souList:orgUsers){
					orgIds.add(souList.getOrgId());
				}
				//组织
				List<SOrg> sorgs=sOrgService.getOrgs(orgIds);
				if(sorgs.size()==0){
					ResponseUtil.creComErrorResult("Org001", "没有组织,联系管理员");
				}
				List<com.evisible.os.controlcenter.model.vo.Org> orgs=new ArrayList<com.evisible.os.controlcenter.model.vo.Org>();
				for(SOrg sorglist:sorgs){
					com.evisible.os.controlcenter.model.vo.Org org=new com.evisible.os.controlcenter.model.vo.Org();
					org.setOrgId(sorglist.getUuid());
					org.setOrgCode(sorglist.getOrgCode());
					org.setOrgName(sorglist.getOrgName());
					org.setParentCode(sorglist.getOrgCode());
					org.setParentId(sorglist.getParentId());
					org.setSortIndex(sorglist.getSortIndex());
					orgs.add(org);
				}
				userOrg.setOrgs(orgs);
				
				return this.ajax(response, ResponseUtil.creObjSucResult(userOrg), "application/json; charset=utf-8");
				
			}catch(Exception e){
				logger.error("登录（EngineerLogin）异常信息：" + e.getMessage());
				return ResponseUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.User_Code_Exist
								.getErrorCode(), "服务器请求错误");
			}
	  }
}
