package com.evisible.os.controlcenter.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 格式化响应信息的工具类
 * 
 * @author
 * @date 2013-12-12
 * @modify
 */
public class ResponseUtil {

	public static String creObjSucResult(Object obj) {
		JSONObject resultMap = new JSONObject();
		JSONObject MobileHead = new JSONObject();
		MobileHead.put("Code", 1);
		MobileHead.put("Message", "请求成功");
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String json = gson.toJson(obj);
		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(json);
		resultMap.put("MobileHead", MobileHead);
		resultMap.put("MobileBody", jsonObj);
		return resultMap.toString();
	}
	public static String creNewDateObjSuc(Object obj) {
		JSONObject resultMap = new JSONObject();
		JSONObject MobileHead = new JSONObject();
		MobileHead.put("Code", 1);
		MobileHead.put("Message", "请求成功");
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd")
				.create();
		String json = gson.toJson(obj);
		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(json);
		resultMap.put("MobileHead", MobileHead);
		resultMap.put("MobileBody", jsonObj);
		return resultMap.toString();
	}
	public static String creObjSucResult(Object obj,String message) {
		JSONObject resultMap = new JSONObject();
		JSONObject MobileHead = new JSONObject();
		MobileHead.put("Code", 1);
		MobileHead.put("Message", message);
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String json = gson.toJson(obj);
		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(json);
		resultMap.put("MobileHead", MobileHead);
		resultMap.put("MobileBody", jsonObj);
		return resultMap.toString();
	}
	public static String creObjSucResultArray(Object obj) throws UnsupportedEncodingException {
		JSONObject resultMap = new JSONObject();
		JSONObject MobileHead = new JSONObject();
		MobileHead.put("Code", 1);
		MobileHead.put("Message", "请求成功");
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String json = gson.toJson(obj);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(json);
		resultMap.put("MobileHead", MobileHead);
		resultMap.put("MobileBody", jsonArray);
		return new String(resultMap.toString());
    }
	public static Map<String, Object> creJsonArraySucResult(Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("IsSuccess", true);
		resultMap.put("Message",
				ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String json = gson.toJson(obj);
		JSONArray jsonArry = new JSONArray();
		jsonArry = JSONArray.fromObject(json);
		resultMap.put("ResultData", jsonArry);
		return resultMap;
	}

		/**
	 * 返回空查询结果
	 * 
	 * @return
	 */
	public static String creComEmptyResult() {
		JSONObject mobileHead = new JSONObject();
		JSONObject resultMap = new JSONObject();
		mobileHead.put("Code", 1);
		mobileHead.put("Message", "操作成功");
		resultMap.put("MobileHead", mobileHead);
		return resultMap.toString();
	}

	/**
	 * 返回自定义结果
	 * 
	 * @return
	 */
	public static String creComSuccessResult(String message) {
		JSONObject mobileHead = new JSONObject();
		JSONObject resultMap = new JSONObject();
		mobileHead.put("Code", 1);
		mobileHead.put("Message", message);
		resultMap.put("MobileHead", mobileHead);
		return resultMap.toString();
	}

	public static String creComErrorResult(String errorCode, String message) {
		JSONObject mobileHead = new JSONObject();
		JSONObject resultMap = new JSONObject();
		mobileHead.put("Code", -1);
		mobileHead.put("Message", message);
		resultMap.put("MobileHead", mobileHead);
		return resultMap.toString();
	}	

	public static String creDataTransferResult(String errCode , String message){
		JSONObject responseMsg = new JSONObject();
		JSONObject headMsg = new JSONObject();
		headMsg.put("Code", errCode);
		headMsg.put("Message", message);
		responseMsg.put("Head", headMsg);
		return responseMsg.toString();
	}
	
}
