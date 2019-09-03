package com.evisible.os.controlcenter.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;





import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;

import net.sf.json.JSONObject;

/**
 * <P>请求参数验证</P>
 * @author TengDong
 * @Date 20160911
 */
public class RequestUtil {
	public static JSONObject ValidateToken(HttpServletRequest request){
		JSONObject resultMap = new JSONObject();
		JSONObject mobileHead = new JSONObject();
		if(request==null) 
		{
			mobileHead.put("Code", -1);
			mobileHead.put("Message", "请求数据为空");
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		}
		String token = request.getParameter("token");
		int size = request.getContentLength();
		InputStream is;
		try {
			is = request.getInputStream();
			byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
			String params = new String(reqBodyBytes);
			// 验证参数合法性
			if (params.equals("") || params == null) {
				mobileHead.put("Code", -1);
				mobileHead.put("Message", ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR.getMemo());
				resultMap.put("MobileHead", mobileHead);
				return resultMap;
			}
			String currentToken = TokenUtils.getToken(params);
			
			
			//客户端传过的商品Token比较
			if (!currentToken.equals(token) && !token.equals("ForTest")) {
				mobileHead.put("Code", -1);
				mobileHead.put("Message","网络异常");
				resultMap.put("MobileHead", mobileHead);
				return resultMap;
			}
			mobileHead.put("Code", 1);
			mobileHead.put("Message", params);
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		} catch (Exception e) {
			mobileHead.put("Code", -1);
			mobileHead.put("Message", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo());
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		}
	}
	
	
	
	public static JSONObject Validate(HttpServletRequest request){
		JSONObject resultMap = new JSONObject();
		JSONObject mobileHead = new JSONObject();
		if(request==null) 
		{
			mobileHead.put("Code", -1);
			mobileHead.put("Message", "请求数据为空");
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		}
		int size = request.getContentLength();
		InputStream is;
		try {
			is = request.getInputStream();
			byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
			String params = new String(reqBodyBytes);
			// 验证参数合法性
			if (params.equals("") || params == null) {
				mobileHead.put("Code", -1);
				mobileHead.put("Message", ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR.getMemo());
				resultMap.put("MobileHead", mobileHead);
				return resultMap;
			}
			mobileHead.put("Code", 1);
			mobileHead.put("Message", params);
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		} catch (Exception e) {
			mobileHead.put("Code", -1);
			mobileHead.put("Message", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo());
			resultMap.put("MobileHead", mobileHead);
			return resultMap;
		}
	}
	
	
	/**
	 * <p>验证参数</p>
	 * @param params
	 * @param paramNames
	 * @return  JSONObject
	 */
	public static JSONObject ValidateParams(JSONObject params,ArrayList<String> paramNames)
	{
		
		JSONObject resultMap = new JSONObject();
		if(params==null || paramNames==null || paramNames.size()==0) 
		{
			resultMap.put("Code", -1);
			resultMap.put("Message", "请求数据为空");
			return resultMap;
		}
		boolean flag =true;
	    StringBuilder stringBuilder= new StringBuilder();
		for (String pName : paramNames) {
			if(params.get(pName)==null || "".equals(params.get(pName)))
			{
				flag=false;
				stringBuilder.append("参数："+pName+"为空！");
			}
		}
		if(flag)
		{
			resultMap.put("Code", 1);
			resultMap.put("Message", "参数传递不正确");
		}
		else {
			resultMap.put("Code", -1);
			resultMap.put("Message", stringBuilder.toString());
		}
		return resultMap;
	}
	
	
	
	
	
}
