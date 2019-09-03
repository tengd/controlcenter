package com.evisible.os.controlcenter.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.dao.SDicDateMapper;
import com.evisible.os.controlcenter.system.entity.SDicDate;
import com.evisible.os.controlcenter.system.entity.SDicDateExample;
import com.evisible.os.controlcenter.system.service.ISDicDateService;

@Service("sDicDateService")
public class SDicDateService extends MybatisGenerator<SDicDateMapper> implements ISDicDateService {
	private static Logger log = LoggerFactory.getLogger(SDicDateService.class);
	public SDicDateService(){
		super(SDicDateMapper.class);
	}
	/*
	 * 字典类型获取数据字典数据
	 * **/
	public List<SDicDate> getSDicDatesByTypecode(String typecode) {
		try {
			SDicDateExample example=new SDicDateExample();
			example.createCriteria().andTypecodeLike(typecode+"%");
			example.setOrderByClause("createdate desc");
			return this.getDao().selectByExample(example);
		} catch (Exception e) {
			log.info("-----------------字典类型获取数据字典数据异常---------------------");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @see 查询字典值
	 * */
	@Override
	public Map<String,Object> getSDicDatesByTypeCodeOrName(String QueryValue, PageUI pageUI) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> example=new HashMap<String,Object>();
			example.put("QueryValue", QueryValue);
			example.put("value", pageUI.getValue());
			example.put("secondValue", pageUI.getSecondValue());
			map.put("rows", this.getDao().selectByExampleTypeCodeOrName(example));
			map.put("total", this.getDao().countByExample(null));
			return map;
		} catch (Exception e) {
			log.info("---------------查询字典值信息出错-----------------------");
			e.printStackTrace();
			map.clear();
		}
		return map;
	}
	/**
	 * 删除字典数据
	 * */
	@Override
	public boolean delDicDate(String uuid) {
		try {
			int sign=this.getDao().deleteByPrimaryKey(uuid);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("-----------删除字典数据异常------------");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 插入字典值
	 * */
	@Override
	public boolean insertDicDate(SDicDate dicDate) {
		try {
			int sign=this.getDao().insertSelective(dicDate);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("-----------插入字典值异常------------");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 更新字典值
	 * */
	@Override
	public boolean updateDicDate(SDicDate dicDate) {
		try {
			int sign=this.getDao().updateByPrimaryKeySelective(dicDate);
			if(sign>0)return true;
		} catch (Exception e) {
			log.info("-----------更新字典值异常------------");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public SDicDate getDicByDicValue(String dicValue){
		SDicDateExample example = new SDicDateExample();
		example.or().andDValueEqualTo(dicValue);
		List<SDicDate> dicList = this.getDao().selectByExample(example);
		if(dicList.size()>0){
			return dicList.get(0);
		}
		return null;
	}

}
