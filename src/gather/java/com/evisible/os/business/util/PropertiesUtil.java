package com.evisible.os.business.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author TengD
 * @date 2014/11/6
 * 处理properties文件
 */
public class PropertiesUtil {
	/**
	 * 读取properties文件中的key值
	 * @param filePath properties文件路径
	 * @param key	   properties文件KEY
	 * @return
	 */
	public static String readValue(String filePath,String key) {
		  Properties props = new Properties();
	      try {
		       try {
				InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
				props.load(in);
			} catch (Exception e) {
				System.out.println("加载文件存储配置文件失败");
				System.exit(1);
			}
		       String value = props.getProperty (key);
		       return value;
	      } catch (Exception e) {
		       e.printStackTrace();
		       return null;
	      }
	}
	/**
	 * 读取properties的全部 信息
	 * @param filePath  properties文件路径
	 */
	@SuppressWarnings("rawtypes")
	public static void readProperties(String filePath) {
	     Properties props = new Properties();
	        try {
	        	try {
					InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
					props.load(in);
				} catch (Exception e) {
					System.out.println("加载文件存储配置文件失败");
					System.exit(1);
				}
	            Enumeration en = props.propertyNames();
	             while (en.hasMoreElements()) {
	              String key = (String) en.nextElement();
	                    String Property = props.getProperty (key);
	                    System.out.println(key+Property);
	               }
	        } catch (Exception e) {
	         e.printStackTrace();
	        }
	 }
	 /**
	  * 写properties文件
	 * @param filePath
	 * @param parameterName   key
	 * @param parameterValue  Value
	 */
	public static void writeProperties(String filePath,String parameterName,String parameterValue) {
	     Properties prop = new Properties();
	     try {
	    	 	try {
					InputStream fis = new FileInputStream(filePath);
					//从输入流中读取属性列表（键和元素对）
					prop.load(fis);
				} catch (Exception e) {
					System.out.println("加载文件存储配置文件失败");
					System.exit(1);
				}
	            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
	            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	            OutputStream fos = new FileOutputStream(filePath);
	            prop.setProperty(parameterName, parameterValue);
	            //以适合使用 load 方法加载到 Properties 表中的格式，
	            //将此 Properties 表中的属性列表（键和元素对）写入输出流
	            prop.store(fos, "Update '" + parameterName + "' value");
	        } catch (IOException e) {
	        	System.err.println("Visit "+filePath+" for updating "+parameterName+" value error");
	        }
	    }
	
	/**
	 * 
	 * <p>描述:对于用逗号分隔的value ， 返回list</p>
	 */
	public static List<String> readValueList(String filePath,String key) {
		  Properties props = new Properties();
	      try {
		       try {
				InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
				props.load(in);
			} catch (Exception e) {
				System.out.println("加载文件存储配置文件失败");
				System.exit(1);
			}
		       String[] value = props.getProperty (key).split(",");
		      List<String> valueList  =new ArrayList<>();
		      for(int i = 0 ; i < value.length ; i++){
		    	  valueList.add(value[i]);
		      }
		      return valueList;
	      } catch (Exception e) {
		       e.printStackTrace();
		       return null;
	      }
	}
}
