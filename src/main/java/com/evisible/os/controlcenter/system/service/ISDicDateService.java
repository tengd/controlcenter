package com.evisible.os.controlcenter.system.service;

import java.util.List;
import java.util.Map;

import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SDicDate;



/**
 * <p>字典</p>
 * @author TengDong
 * @Date 20160406
 */
public interface ISDicDateService {
	static final String Mapper="com.usercenter.system.dao.sql.SDicDateMapper.";
	/**
	 * @param typecode 字典数据类型
	 * @return 字典数据信息  List<SDicDateObject>
	 */
	List<SDicDate>  getSDicDatesByTypecode(String typecode);
	
	
	/**
	 * @param value
	 * @param pageUI
	 * @return 查询字典
	 */
	Map<String,Object> getSDicDatesByTypeCodeOrName(String QueryValue,PageUI pageUI);
	
	
	/**
	 *  删除字典初使数据
	 * @param uuid
	 * @return boolean
	 */
	public boolean delDicDate(String uuid);
	
	/**
	 * @see插入字典初使数据
	 * @param dicDate
	 * @return boolean
	 */
	public boolean insertDicDate(SDicDate dicDate);
	
	/**
	 * @see 编辑字典初使数据
	 * @param dicDate
	 * @return
	 */
	public boolean updateDicDate(SDicDate dicDate);
	
	/**
	 * @see 根据字典数据获取字典Name
	 * @param dicDate
	 * @return
	 */
	SDicDate getDicByDicValue(String dicValue);
	
}
