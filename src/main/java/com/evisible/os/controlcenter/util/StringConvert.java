package com.evisible.os.controlcenter.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StringConvert {
	
	
	
	
	public static String getUUIDString() {
		String uuid = java.util.UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
	}
	
	public static String convertToJson(Object obj) {
		String result = "";
		if(obj instanceof List){
			JSONArray ja = JSONArray.fromObject(obj);
			result = ja.toString();
		} else {
			JSONObject jo = JSONObject.fromObject(obj);	
			result = jo.toString();
		}
		return result;
	}

	public static String getDateCode(){
		String code = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		code = sdf.format(date);
		return code;
	}
	public static String getUTF8(String str){
		try {
			return new String(str.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	
	public static String replaceChars(String sString,String sOld,String sNew) {
        String newString = "";
        try {
            for (int i = 0; i < sString.length(); i ++) {
                if (sString.substring(i, i + sOld.length()).equals(sOld)) {
                    sString = sString.substring(0, i) + sNew + sString.substring(i + sOld.length(), sString.length());
                    i = i + sNew.length();
                }
            }
            newString = sString;
            return newString;
        } catch(Exception e) {
            System.out.println(e.toString());
            return newString;
        }
    }
	
	
	/**
	 * 
	* @Description: 替换xml中特殊字符， 以免解析出错 
	* @param  xmlStr
	* @return String   
	* @throws
	 */
	public static String replaceXmlSpecialChars(String xmlStr){
		if(!StringConvert.isEmpty(xmlStr)){
			return xmlStr
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&apos;", "'")
			.replaceAll("&quot;","\"")
			.replaceAll("&","&amp;");
		}
		return "获取数据为空";

	}

	/**
	 * 
	* @Description: 判断字符串是否为空或者为null
	* @param @param str
	* @return boolean    返回类型 
	 */
	public static boolean isEmpty(String str){
		return str == null || str.isEmpty();
	}
	
	/**
	 * 
	* @Description: 提取url中的IP地址部分
	* @param @param originalStr
	* @param @return    设定文件 
	* @return String[]    返回类型 
	* @throws
	 */
	public static String[] extractIpAddress(String urlStr){
		String regex = "^(\\w*://)?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})?([/a-zA-Z0-9]*)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(urlStr);
		if(matcher.find()){
			return new String[]{matcher.group(1),matcher.group(2),urlStr.substring(matcher.group(1).length()+matcher.group(2).length())};
		}
		return null;
	}
	
	/**
	 * <p>验证字符串是否是合法的xml</p>
	 */
	public static boolean isXML(String value) { 
		try { 
		DocumentHelper.parseText(value); 
		} catch (DocumentException e) { 
		return false; 
		}
		return true;
	}
	
	public static boolean  isMessive(String str){
		return !(Charset.forName("GBK").newEncoder().canEncode(str)||Charset.forName("ISO-8859-1").newEncoder().canEncode(str));
	}
	
	
	
	public static void main(String[] args){
		//for(int i=1;i<=15;i++) {
			System.out.println(StringConvert.getUUIDString());
		//}
		
	}

}