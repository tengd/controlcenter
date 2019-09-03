/**
* @author Jiangwandong
* @version 创建时间：2018年2月1日
*/
package com.evisible.os.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.evisible.os.business.entity.TableDescription;
/**
 * 
 * <p>表描述信息的工具类，用于根据指定表的信息得到不同形式的表结构的描述</p>
 * @author JiangWanDong
 * @Date   2018年2月1日
 */
public class TableInfoUtil {
	public static Map<String , String> getFieldTypeByTableName(List<TableDescription> tableFieldInfoList){
		Map<String , String> typeMap = new HashMap<>();
		if(tableFieldInfoList != null){
			for(TableDescription td : tableFieldInfoList){
				typeMap.put(td.getField().toLowerCase(), td.getType().contains(")")?(td.getType().substring(0, td.getType().indexOf("("))).toLowerCase():td.getType().toLowerCase());
			}
		}
		return typeMap;
	}

	public static String getFieldListStrByTableInfo(List<TableDescription> tableFieldInfoList){
		StringBuffer sb = new StringBuffer("(");
		if(tableFieldInfoList != null){
			for(TableDescription td : tableFieldInfoList){
				sb.append(td.getField()+",");
			}
		}
		sb.replace(sb.length()-1, sb.length(), ")");
		return sb.toString().toLowerCase();
	}
	
	public static List<String> getFieldListByTableInfo(List<TableDescription> tableFieldInfoList){
		List<String> fieldList = new ArrayList<>();
		if(tableFieldInfoList != null){
			for(TableDescription td : tableFieldInfoList){
				fieldList.add(td.getField().toLowerCase());
			}
		}
		return fieldList;
	}
	
	public static String extractTableName(String str){
		String oriTableName = str.replaceAll(" ", "").replaceAll("-", "_");
		oriTableName = PinyinUtil.getCaptitalChars(oriTableName).toLowerCase();
		oriTableName = oriTableName.substring(0, oriTableName.lastIndexOf('.'));
		String regex = "^(([a-zA-Z]+_?)+)([0-9_]*)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(oriTableName);
		if(matcher.find()){
			System.out.println("true");
			oriTableName = matcher.group(1);
			if(oriTableName.endsWith("_")){
				return oriTableName.substring(0, oriTableName.length()-1);
			}
			return oriTableName;
		}else{
			return oriTableName;
		}
	}
	
	public static void main(String[] args){

		//System.out.println(extractTableName("ythgf_store_ 20180104-023011-072.xls"));
		//System.out.println("sad8asd sada    asdas da  ".replaceAll(" ", ""));
	}
}
