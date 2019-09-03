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

import com.evisible.os.controlcenter.model.vo.SDicDateObject;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.service.ISDicDateService;
import com.evisible.os.controlcenter.util.RequestUtil;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;

/**
 * <p>初使化数据字典</p>
 * @author TengDong
 * @date 20160816
 */
@Controller("SDicDateAppController")
@RequestMapping("/dicdateApp") 
public class SDicDateController extends  BaseController{
	@Autowired
	private ISDicDateService sDicDateService;
	
	@ResponseBody
	@RequestMapping(value="/getDicDates", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")  
	public String getSDicDates(HttpServletRequest request, 
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
			paramNames.add("typecode");
			
			JSONObject pValidateObj = RequestUtil.ValidateParams(paramJson,paramNames);
			
			if (pValidateObj == null|| pValidateObj.getString("Code").equals("-1")) {
				return ResponseUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR
								.getErrorCode(), pValidateObj
								.getString("Message"));
			}
			String typecode=paramJson.getString("typecode");
			List<com.evisible.os.controlcenter.system.entity.SDicDate> sdicDates=sDicDateService.getSDicDatesByTypecode(typecode);
			if(sdicDates.size()==0){
				ResponseUtil.creComErrorResult("SDicDate001", "没有此类型数据字典,联系管理员");
			}
			SDicDateObject sdicDateObject=new SDicDateObject();
			
			List<com.evisible.os.controlcenter.model.vo.SDicDate> SDicDates=new ArrayList<com.evisible.os.controlcenter.model.vo.SDicDate>();
			for(com.evisible.os.controlcenter.system.entity.SDicDate sdList:sdicDates){
				com.evisible.os.controlcenter.model.vo.SDicDate sdicDate=new com.evisible.os.controlcenter.model.vo.SDicDate();
				sdicDate.setDicid(sdList.getUuid());
				sdicDate.setTypecode(sdList.getTypecode());
				sdicDate.setDname(sdList.getdName());
				sdicDate.setDvalue(sdList.getdValue());
				SDicDates.add(sdicDate);
			}
			sdicDateObject.setSDicDates(SDicDates);
			
			return this.ajax(response, ResponseUtil.creObjSucResult(sdicDateObject), "application/json; charset=utf-8");
			
		}catch(Exception e){
			logger.error("登录（EngineerLogin）异常信息：" + e.getMessage());
			return ResponseUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.User_Code_Exist
							.getErrorCode(), "服务器请求错误");
		}
	}
}
