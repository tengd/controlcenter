package com.evisible.os.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evisible.os.business.dao.TBaseDataMapper;
import com.evisible.os.business.entity.TBaseData;
import com.evisible.os.business.entity.TBaseDataExample;
import com.evisible.os.business.entity.TableDescription;
import com.evisible.os.business.service.IDataBaseTablesService;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.util.Jdbc_JavaConverter;
import com.evisible.os.business.util.TableInfoUtil;
import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.resolve.ResolveFactory;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;

/**
 * <p>
 * 外部传入数据xml解析类
 * </p>
 * 
 * @author Jiangwandong
 * @Date 2018年1月3日
 * 
 */
@Service("tBaseDataService")
public class TBaseDataService extends MybatisGenerator<TBaseDataMapper> implements ITBaseDataService {
	private static Logger log = LoggerFactory.getLogger(TBaseDataService.class);
	@Autowired
	private ITriggerConfigService triggerConfigService;
	@Autowired
	private IDataBaseTablesService tDataBaseTablesService;

	public TBaseDataService() {
		super(TBaseDataMapper.class);
	}

	/**
	 * 将传入的原始数据插入base_data表，插入成功后返回新插入的uuid ， 若查询失败，返回空
	 */
	@Override
	public String insertTransferedData(String token, String xmlStr, User user, String dataSourceId , Map<String , String> insertInfo) {
		TBaseData baseData = null;
		String uuid = StringConvert.getUUIDString();
		if (user == null) {
			baseData = new TBaseData(uuid, token, insertInfo.get("tableName").toLowerCase(), insertInfo.get("companyCode"), insertInfo.get("companyName"), "", xmlStr, new Date());
		} else {
			baseData = new TBaseData(uuid, token, insertInfo.get("tableName").toLowerCase(), insertInfo.get("companyCode"), insertInfo.get("companyName"),  user.getSalt(), xmlStr,
					new Date());
		}
		baseData.setDatasourceConfigId(dataSourceId);
		try {
			this.getDao().insertSelective(baseData);
			return uuid;
		} catch (Exception e) {
			log.info("-----------插入数据异常------------");
			return null;
		}
	}

	@Override
	public Map<String, Object> getBaseDatas(TBaseData baseData, PageUI pageUI) {
		Map<String, Object> baseDatas = new HashMap<>();
		Map<String, Object> pageCondition = new HashMap<>();
		pageCondition.put("pageStart", pageUI.getValue());
		pageCondition.put("pageEnd", pageUI.getSecondValue());
		pageCondition.put("datasource_config_id", baseData.getDatasourceConfigId());
		pageCondition.put("tableName", baseData.getTableName());
		pageCondition.put("is_succ", baseData.getIsSucc());
		pageCondition.put("is_locked", baseData.getIsLocked());
		try {
			baseDatas.put("rows", this.getDao().selectBaseDatas(pageCondition));
			pageCondition.clear();
			baseDatas.put("total", this.getDao().countByExample(null));
		} catch (Exception e) {
			log.info("------查询t_base_data表出错----------");
			e.printStackTrace();
		}
		return baseDatas;
	}

	/**
	 * 将t_base_data中xml解析之后建表，插入表
	 */
	//@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public boolean resolveBaseDataXml(String[] uuidsArr) {
		for (int i = 0; i < uuidsArr.length; i++) {
			TBaseData baseData = this.getDao().selectByPrimaryKey(uuidsArr[i]);
			String tableName = baseData.getTableName();
			Map<String, String> createTableMap = null;
			String xmlText = baseData.getXmlText();
			//根据xml格式判断当前xml是旧格式还是新格式
			String xmlFormat = ResolveFactory.isXmlFormatNew(xmlText);	
			int tableCount = this.getDao("data").findIsTableExists(tableName);
			// 查找当前数据库中是否存在相应的表， 若不存在，则创建新表
			if (tableCount == 0) {
				try {
					if(xmlFormat.equals("new")){
						createTableMap = ResolveFactory.createXmlNewUtil().genCreateTableMap(xmlText);
					}else{
						createTableMap = ResolveFactory.createXmlOldUtil().genCreateTableMap(xmlText,tableName);
					}
					this.getDao("data").createTableByXmlMap(createTableMap);
				} catch (DocumentException e) {
					e.printStackTrace();
					log.info("------database解析xml出错----------");
					return false;
				}
			}
			// 判断xml中table的字段和当前表中的字段对应关系，若xml中存在新增字段， 则修改表结构，将xml中新增的字段加入表中再插入
			try {
				if(xmlFormat.equals("new")){
					String alterFieldStr = ResolveFactory.createXmlNewUtil().getAddFieldDescription(xmlText, (List<TableDescription>) tDataBaseTablesService.getSingleTableInfo(tableName).get("rows"));
					if(!StringConvert.isEmpty(alterFieldStr)){
						this.getDao("data").alterTable(tableName, "ADD", alterFieldStr);
					}
				}				
			} catch (Exception e) {
				log.info("------database解析修改表结构出错----------");
				e.printStackTrace();
				return false;
			}
			List<TableDescription> tableFieldInfoList = (List<TableDescription>) tDataBaseTablesService.getSingleTableInfo(tableName).get("rows");
			String fieldListStr = TableInfoUtil.getFieldListStrByTableInfo(tableFieldInfoList);
			List<String> fieldList = TableInfoUtil.getFieldListByTableInfo(tableFieldInfoList);
			Map<String, String> fieldTypeMap = TableInfoUtil.getFieldTypeByTableName(tableFieldInfoList);
			List<Map<String, Object>> itemList = null;
			
			try {
				if(xmlFormat.equals("new")){
					itemList = ResolveFactory.Resolve(baseData.getXmlText(), "/root/item");
				}else{
					itemList = ResolveFactory.Resolve(baseData.getXmlText(), "/root/subInfo");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			List<String> valueStrList = new ArrayList<>();
			for (Map<String, Object> itemInfoMap : itemList) {
				StringBuffer insertFieldValueSb = new StringBuffer("(");
				for (String field : fieldList) {
					if (field.toUpperCase().equals("UUID")) {
						insertFieldValueSb.append(Jdbc_JavaConverter.getJdbcInsertFieldDesc("VARCHAR", StringConvert.getUUIDString())+ ",");
					} else {
						insertFieldValueSb.append(Jdbc_JavaConverter.getJdbcInsertFieldDesc(fieldTypeMap.get(field.toLowerCase()),(String) itemInfoMap.get(field.toLowerCase())) + ",");
					}
				}
				valueStrList.add(insertFieldValueSb.replace(insertFieldValueSb.length() - 1, insertFieldValueSb.length(), ")").toString());
			}
			 try{
				//为避免数据库拒绝服务， 批量插入的数据条数限制在50条每批
					for(int j = 0, endIndex = 0; j < valueStrList.size() ; j+= 50){
						endIndex += 50;
						if(endIndex >= valueStrList.size()){
							endIndex = valueStrList.size();
						}
						this.getDao("data").insertBatchResolvedBaseData(tableName, fieldListStr.toString(), valueStrList.subList(j, endIndex));
					}
			 }catch(Exception e){
				 e.printStackTrace();
				 log.info("------database插入生成表出错----------");
				 return false;
			 }
			// 数据成功解析并插入数据库后，将成功标志改为已成功
			TBaseData baseDataBean = new TBaseData();
			baseDataBean.setUuid(uuidsArr[i]);
			baseDataBean.setIsSucc(true);
			baseDataBean.setIsLocked(true);
			baseDataBean.setAnalysisTime(new Date());
			this.getDao().updateByPrimaryKeySelective(baseDataBean);
			// 插入成功后， 该条数据的定时任务设为不可用，防止定时任务重复执行
			triggerConfigService.setTriggerAvailableByDataSourceId(baseData.getUuid(), false);
		}
		return true;
	}

	@Override
	public TBaseData getBaseData(String uuid) {
		return this.getDao().selectByPrimaryKey(uuid);
	}
	
	@Override
	public boolean deleteBaseData(String[] uuids){
		try {
			for (int i = 0; i < uuids.length; i++) {
				this.getDao().deleteByPrimaryKey(uuids[i]);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	

	public boolean lockBaseData(String[] uuids) {
		try {
			for (int i = 0; i < uuids.length; i++) {
				TBaseData baseDataBean = new TBaseData();
				baseDataBean.setUuid(uuids[i]);
				TBaseData baseData = this.getDao().selectByPrimaryKeyWithoutBlob(uuids[i]);
				boolean isLocked = baseData.getIsLocked();
				// isLocked==1时是已锁定
				if (isLocked == true) {
					baseDataBean.setIsLocked(false);
					triggerConfigService.setTriggerAvailableByDataSourceId(baseData.getUuid(), true);
				} else {
					// 若该条数据当前没有锁定，则将其设为锁定状态
					baseDataBean.setIsLocked(true);
					// 同时查找t_trigger_config中的触发状态，已锁定数据不能被触发， 将可被触发状态改为不可被触发
					triggerConfigService.setTriggerAvailableByDataSourceId(baseData.getUuid(), false);
				}
				
				this.getDao().updateByPrimaryKeySelective(baseDataBean);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public int deleteBaseData(TBaseDataExample example) {
		return this.getDao().deleteByExample(example);
	}

	@Override
	public TBaseData getBaseDataInfoByTriggerUuid(String triggerUuid) {
		return this.getDao().getBaseDataInfoByTriggerUuid(triggerUuid);
	}

	@Override
	public boolean alterTable(String tableName, String alterType, String alterDesc) {
		try {
			this.getDao().alterTable(tableName, alterType, alterDesc);
			return true;
		} catch (Exception e) {
			log.error("-------Altertable操作出错----------------");
			e.printStackTrace();
			return false;
		}
	}
	
	

}
