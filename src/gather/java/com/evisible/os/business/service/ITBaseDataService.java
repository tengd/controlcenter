package com.evisible.os.business.service;

import java.util.Map;

import com.evisible.os.business.entity.TBaseData;
import com.evisible.os.business.entity.TBaseDataExample;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.User;

/** 
 * <p>外部传入数据处理service</p>
* @author Jiangwandong
* @Date 2018年1月3日
* 
*/
public interface ITBaseDataService {
	
	/**
	 * <p>将传来的xml解析之后放入t_base_data表中</p>
	 */
	String insertTransferedData(String token, String xmlStr, User user, String dataSourceId , Map<String , String> insertInfo);
	/**
	 * <p>将t_base_data表中信息查询出来展示</p>
	 */
	Map<String , Object> getBaseDatas(TBaseData baseData ,PageUI pageUI);
	
	/**
	 * 
	 * <p>将数组中uuid对应的t_base_data中的xml信息解析之后存入指定的表</p>
	 * @author JiangWanDong
	 */
	boolean resolveBaseDataXml(String[] uuidsArr);
	
	/**
	 * 
	 * <p>根据uuid获取baseData</p>
	 * @author JiangWanDong
	 */
	TBaseData getBaseData(String uuid);
	
	
	/**
	 * 
	 * <p>根据uuid锁定数据</p>
	 * @author JiangWanDong
	 */
	public boolean lockBaseData(String[] uuids);
	
	
	/**
	 * 
	 * <p>删除BaseData</p>
	 * @author JiangWanDong
	 */
	public int deleteBaseData(TBaseDataExample example);
	
	public TBaseData getBaseDataInfoByTriggerUuid(String triggerUuid);
	
	boolean alterTable(String tableName , String alterType , String alterDesc);	
	/**
	 * 根据uuid删除basedata
	 */
	public boolean deleteBaseData(String[] uuids);
	
}
