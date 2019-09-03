package com.evisible.os.controlcenter.system.controller.soa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.sms.HttpSender;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.IUserService;
import com.evisible.os.controlcenter.util.RequestUtil;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.VerifyCodeUtil;
import com.evisible.os.controlcenter.util.MD5.MD5;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;

/**
 * <p>用户管理接口</p>
 * @author TengDong
 * @date 20160907
 */
@Controller("userAppController")
@RequestMapping("userApp")
public class UserController extends BaseController{
	//初始化密码，后面可写配置文件
	private static final String InitPas=VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 6, null);  ;
	
	//短信
	private static final String url="http://222.73.117.158/msg/";		// 应用地址
	private static final String account="jiekou-clcs-02";						// 账号
	private static final String pswd = "jkoDMEIE5d";							// 密码
	private static final boolean needstatus = true;								// 是否需要状态报告，需要true，不需要false
	private static final String extno = null;											// 扩展码
	
	@Autowired
	private IUserService userService;    
	/**
	 * @see通过手机短信初始化密码
	 * */
	@ResponseBody
	@RequestMapping(value="/initPass", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")  
	public Object addUser(HttpServletRequest request, 
			HttpServletResponse response) {
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
			paramNames.add("mobile");
			
			JSONObject pValidateObj = RequestUtil.ValidateParams(paramJson,
					paramNames);
			if (pValidateObj == null|| pValidateObj.getString("Code").equals("-1")) {
				return ResponseUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR
								.getErrorCode(), pValidateObj
								.getString("Message"));
			}
			User record=new User();
			String mobile=paramJson.getString("mobile").trim();
			record.setPhone(mobile);
			
			MD5 md5InitPassword = new MD5(InitPas);
			record.setPas(md5InitPassword.mac32());
			//盐初始化成初始密码
			record.setSalt(md5InitPassword.mac32());
			
			if(userService.initPass(record)){
				try {
					String returnString = HttpSender.batchSend(url, account, pswd,
							mobile, "您好!您的初始化密码为:" + InitPas, needstatus, extno);
					System.out.println("短信服务器返回的字符串为："+returnString);
					Map<String,Object> map=new HashMap<String,Object>();
					String[] responseStr1=returnString.split("\n");
					String[] row1=responseStr1[0].split(",");
					String resptime=row1[0];
					String respstatus=row1[1];
					String msgid=responseStr1[1];
					
					map.put("resptime", resptime);
					map.put("respstatus", respstatus);
					if(StringUtils.isNotEmpty(msgid))
					map.put("msgid", msgid);
					
					return this.ajax(response, ResponseUtil.creObjSucResult(map), "application/json; charset=utf-8");
				} catch (Exception e) {
					// TODO 处理异常
					logger.error("发送（senderVerifyCode）短信验证码短信接口方出错,请联系短信提供商：" + e.getMessage());
					return ResponseUtil.creComErrorResult(
							ComErrorCodeConstants.ErrorCode.User_Code_Exist
									.getErrorCode(), "服务器请求错误");
				}
				
			}else{
				return this.ajax(response, ResponseUtil.creComErrorResult("InitPasswordError001", "初始化密码失败,联系管理员"), "application/json; charset=utf-8");
			}
			
		
		}catch(Exception e){
			logger.error("发送（senderVerifyCode）短信验证码信息：" + e.getMessage());
			return ResponseUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.User_Code_Exist
							.getErrorCode(), "服务器请求错误");
		}
		
		
		
	}
		
}
