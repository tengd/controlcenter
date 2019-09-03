package com.evisible.os.controlcenter.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 数据源注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataSource {
	
	String value() default DataSource.system;
	
	/**
	 * 根据applicationContext-mybatis文件中的dataSource中配置的key指定，可扩展至多个数据源
	 */
	public static String system = "system";
    public static String data = "data";
}
