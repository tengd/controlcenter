package com.evisible.os.controlcenter.base;

/**
 * <p>用于spring多数据源切换,使用不同数据源时，保持线程安全</p>
 * @author JiangWanDong
 * @Date   2018年3月23日
 */
public class DataSourceHolder {

	public static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();
	
	public static void setDataSource(String name) {
		HOLDER.set(name);
    }
    
    public static String getDataSouce() {
        return HOLDER.get();
    }
    
    public static void clear() {
    	HOLDER.remove();
    }
}
