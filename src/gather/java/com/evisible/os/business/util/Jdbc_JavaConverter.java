/**
* @author Jiangwandong
* @version 创建时间：2018年1月19日
*/
package com.evisible.os.business.util;

import com.evisible.os.controlcenter.util.StringConvert;

/** 
* <p>jdbctype和javatype互相转化</p>
* @author Jiangwandong
* @Date ：2018年1月19日
* 
*/
public class Jdbc_JavaConverter {
	
	/**
	 * 
	* @Description: 根据javatype获取建表时的type描述
	* @param @param javaType
	* @param @param size
	* @return String  
	 */
	public static String getJdbcMysqlTypeDescription(String javaType , String size){
		String typeDesc = "";
		if(StringConvert.isEmpty(javaType)){
			javaType = "STRING";
		}
		javaType = javaType.toUpperCase();
		switch(javaType){
			case "STRING":typeDesc = (StringConvert.isEmpty(size))?"VARCHAR(255)":"VARCHAR("+size+")";
			break;
			case "VARCHAR":typeDesc = (StringConvert.isEmpty(size))?"VARCHAR(255)":"VARCHAR("+size+")";
			break;
			case "VARCHAR2":typeDesc = (StringConvert.isEmpty(size))?"VARCHAR(255)":"VARCHAR("+size+")";
			break;
			case "LONGTEXT":typeDesc = "LONGTEXT";
			break;
			case "BOOLEAN":typeDesc = "BIT";
			break;
			case "DATE":typeDesc = "DATETIME";
			break;
			case "TIMESTAMP":typeDesc = "TIMESTAMP";
			break;
			case "INT":typeDesc = (StringConvert.isEmpty(size))?"INT":"INT("+size+")";
			break;
			case "LONG":typeDesc = (StringConvert.isEmpty(size))?"BIGINT":"BIGINT("+size+")";
			break;
			case "BYTE":typeDesc = (StringConvert.isEmpty(size))?"VARBINARY":"VARBINARY("+size+")";
			break;
			case "CHAR":typeDesc = (StringConvert.isEmpty(size))?"CHAR":"CHAR("+size+")";
			break;			
			default:typeDesc = "VARCHAR(255)";
		}
		if(javaType.equals("FLOAT")||javaType.equals("DOUBLE")||javaType.equals("DECIMAL")){
			String[] sizeArr = size.split(",");
			if(sizeArr == null){
				typeDesc = javaType+"(10,6)";
			}else if(sizeArr.length == 2){
				int fieldSize = Integer.parseInt(sizeArr[0]);
				int fieldScale = Integer.parseInt(sizeArr[1]);
				typeDesc = javaType+"("+fieldSize+","+fieldScale+")";
			}else if(sizeArr.length == 1){
				int fieldSize = Integer.parseInt(sizeArr[0]);
				typeDesc = javaType+"("+fieldSize+")";
			}				
		}
		return typeDesc;
	}
	
	public static String getJdbcInsertFieldDesc(String type , String value){
		String fieldDesc = "";
		type = type.toUpperCase();
		if(!StringConvert.isEmpty(value)){
			value = value.trim();
		}else{
			if(type.equals("VARCHAR") || type.equals("LONGTEXT") || type.equals("VARCHAR2")||type.equals("STRING")){
				value = "";
			}else{
				value = null;
			}
		}
		
		switch(type){
			case "VARCHAR" : fieldDesc = "'"+value+"'";
			break;
			case "VARCHAR2" : fieldDesc = "'"+value+"'";
			break;
			case "CHAR" : fieldDesc = "'"+value+"'";
			break;
			case "BYTE" : fieldDesc = "'"+value+"'";
			break;
			case "LONGTEXT" : fieldDesc = "'"+value+"'";
			break;
			case "TIMESTAMP" :fieldDesc = StringConvert.isEmpty(value)?"STR_TO_DATE(null,'%Y-%m-%d %H:%i:%S')":"STR_TO_DATE('"+value+"','%Y-%m-%d %H:%i:%S')";
			break;
			case "DATE" :fieldDesc = StringConvert.isEmpty(value)?"STR_TO_DATE(null,'%Y-%m-%d %H:%i:%S')":"STR_TO_DATE('"+value+"','%Y-%m-%d %H:%i:%S')";
			break;
			case "DATETIME" :fieldDesc = StringConvert.isEmpty(value)?"STR_TO_DATE(null,'%Y-%m-%d %H:%i:%S')":"STR_TO_DATE('"+value+"','%Y-%m-%d %H:%i:%S')";
			break;
			default : fieldDesc = value;
		}
		return fieldDesc;
	}
	
	public static void main(String[] args){
		System.out.println(getJdbcInsertFieldDesc("DATE" , null));
	}
	
	
}
