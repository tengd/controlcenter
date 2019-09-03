package com.evisible.os.controlcenter.system.controller.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.evisible.os.controlcenter.system.entity.SysLog;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISysLogService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




/**
 * Controller基类
 * @Author wangfei, zhangjie
 * @Create In 2014年10月8日
 */

public class BaseController {
	/**
	 * @see 日志
	 */
	@Autowired
	private ISysLogService sysLogService;  
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String MESSAGES = "messages";
	public static final String CONTENT = "content";
	
	
	
	

	public  Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static Gson GSON = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	
	/**
	 * @see 添加日志
	 * @param request 
	 * @param funId
	 * @param funName
	 * @return
	 */
	public void Log(HttpServletRequest request,String funId,String funName){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("currentUser");
		try {
			SysLog record=new SysLog();
			record.setUuid(StringConvert.getUUIDString());
			String ip = request.getHeader("x-forwarded-for");  
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	            ip = request.getHeader("Proxy-Client-IP");     
	        }  
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	            ip = request.getHeader("WL-Proxy-Client-IP");     
	         }     
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	             ip = request.getRemoteAddr();     
	        }  
	        //客户端IP
			record.setVisitIp(ip);
			record.setuId(user.getUuid());
			record.setuName(user.getuName());
			record.setFunId(funId);
			record.setFunName(funName);
			record.setVisitOs(System.getProperty("os.name"));
			
			sysLogService.addSysLog(record);
		} catch (Exception e) {
			System.out.println("----添加日志出现异常！---");
			e.printStackTrace();
		}
	}

	/**
	 * AJAX输出，返回null
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("IOException:", e);
		}
		return null;
	}
	/**
	 * AJAX输出文本，返回null
	 * 
	 * @param text
	 * @return
	 */
	public String ajaxText(HttpServletResponse response, String text) {
		return ajax(response, text, "text/plain");
	}
	
	/**
	 * 设置ajax请返回结果
	 * 
	 * @param success
	 *            请求状态
	 * @param message
	 *            提示信息
	 * @param entity
	 *            返回数据结果对象
	 */
	public Map<String,Object> setJson(boolean success, String message, Object entity) {
		Map<String,Object> json = new HashMap<String,Object>();
		json.put("success", Boolean.valueOf(success));
		json.put("message", message);
		json.put("entity", entity);
		return json;
	}

	/**
	 * AJAX输出HTML，返回null
	 * 
	 * @param html
	 * @return
	 */
	public String ajaxHtml(HttpServletResponse response, String html) {
		return ajax(response, html, "text/html");
	}

	/**
	 * AJAX输出XML，返回null
	 * 
	 * @param xml
	 * @return
	 */
	public String ajaxXml(HttpServletResponse response, String xml) {
		return ajax(response, xml, "text/xml");
	}

	/**
	 * 根据字符串输出JSON，返回null
	 * 
	 * @param jsonString
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * 根据Map输出JSON，返回null
	 * 
	 * @param jsonMap
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response,
			Map<String, String> jsonMap) {
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonWarnMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonWarnMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonSuccessMessage(HttpServletResponse response,
			String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonSuccessMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonErrorMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonErrorMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 设置页面不缓存
	 */
	public void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}

	/**
	 * 根据Object输出JSON字符串
	 */
	public String getJson(Object jsonObject) {
		return GSON.toJson(jsonObject);
	}

	/**
	 * 根据字符串输出JSON，返回null
	 * 
	 * @param jsonString
	 * @return
	 */
	public String ajaxJsonCache(HttpServletResponse response,
			String jsonString, String cacheTime) {
		return ajaxCache(response, jsonString, "text/html", cacheTime);
	}

	/**
	 * AJAX输出，返回null
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajaxCache(HttpServletResponse response, String content,
			String type, String cacheTime) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			setCache(response, cacheTime);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public void setCache(HttpServletResponse response, String cacheTime) {
		long now = System.currentTimeMillis();
		long cacheTimeLong = Long.parseLong(cacheTime);
		response.setDateHeader("Expires", now + cacheTimeLong);
		response.setDateHeader("Last-Modified", now - (now % cacheTimeLong));
		response.setHeader("Cache-Control", "max-age=" + cacheTime);
		response.setHeader("Pragma", "Pragma");
	}

	/**
	 * 公共校验参数方法
	 * 
	 * @Methods Name validateParas
	 * @Create In 2014年10月8日 By wangfei
	 * @param parametersBindingResult
	 * @param map
	 * @return boolean
	 */
	protected boolean validateParas(BindingResult parametersBindingResult,
			Map<String, Object> map) {
		if (parametersBindingResult.hasErrors()) {
			List<FieldError> fes = parametersBindingResult.getFieldErrors();
			String checkMsg = fes.get(0).getDefaultMessage();
			map.put("success", false);
			map.put("errorCode", ComErrorCodeConstants.ErrorCode.VALIDATE_ERROR
					.getErrorCode());
			map.put("msg", checkMsg);
			return false;
		}
		return true;
	}

	/**
	 * 
	* @Description: 根据传入的功能名称 ， 权限名称判断该功能是否有相应权限 
	* @param @param funName
	* @param @param permissionName
	* @return boolean 
	* @throws
	 */
	boolean isHasPermission(String funName , String permissionName){
		@SuppressWarnings("unchecked")
		Map<String , Set<String>> permissionMap = (Map<String, Set<String>>) SecurityUtils.getSubject().getSession().getAttribute("permissions");
		if(permissionMap == null){
			return false;
		}
		Set<String> permissionSet = permissionMap.get(funName);
		if(permissionSet.isEmpty() || permissionSet == null){
			return false;
		}
		if(permissionSet.contains("*")){
			return true;
		}
		return permissionSet.contains(permissionName);
	} 
	
	
	
}
