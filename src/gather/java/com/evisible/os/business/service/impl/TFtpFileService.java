package com.evisible.os.business.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evisible.os.business.dao.TFtpFileMapper;
import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.entity.TFtpFile;
import com.evisible.os.business.entity.TFtpFileExample;
import com.evisible.os.business.entity.TableDescription;
import com.evisible.os.business.service.IDataBaseTablesService;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.service.ITDataSourceConfigService;
import com.evisible.os.business.service.ITFtpFileService;
import com.evisible.os.business.util.FtpUtil;
import com.evisible.os.business.util.Jdbc_JavaConverter;
import com.evisible.os.business.util.PropertiesUtil;
import com.evisible.os.business.util.TableInfoUtil;
import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.entity.SDicDate;
import com.evisible.os.controlcenter.system.service.ISDicDateService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;
import com.evisible.os.resolve.ResolveFactory;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;

/**
 * 
 * <p>
 * ftp文件处理service
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月30日
 */
@Service("tFtpFileService")
public class TFtpFileService extends MybatisGenerator<TFtpFileMapper> implements ITFtpFileService {

	private static Logger log = LoggerFactory.getLogger(TFtpFileService.class);
	@Autowired
	private ITriggerConfigService triggerConfigService;
	@Autowired
	private IDataBaseTablesService tDataBaseTablesService;
	@Autowired
	private ITDataSourceConfigService dataSourceConfigService;
	@Autowired
	private ITBaseDataService tBaseDataService;
	@Autowired
	private ISDicDateService iSDicDateService;

	public TFtpFileService() {
		super(TFtpFileMapper.class);
	}

	/**
	 * @Description: 获取指定路径下的文件列表并将文件信息插入ftpfile表
	 */
	@Override
	public Message getFtpFilesInfo(String dataSourceId) {
		FTPClient ftp = null;
		List<TFtpFile> fileList = null;
		TDataSourceConfig dataSourceConfig = dataSourceConfigService.getDataSourceConfigByUuid(dataSourceId);
		try {
			log.info("--------开始获取ftp文件----------------");
			ftp = FtpUtil.getFtpClientAngLogin(StringConvert.extractIpAddress(dataSourceConfig.getUrl())[1],
					dataSourceConfig.getPort(), dataSourceConfig.getDsUsername(), dataSourceConfig.getDsPassword());
			fileList = FtpUtil.getFileList(StringConvert.extractIpAddress(dataSourceConfig.getUrl())[2].isEmpty() ? "/"
					: StringConvert.extractIpAddress(dataSourceConfig.getUrl())[2], false, ftp);
			log.info("--------获取ftp文件成功----------------");
		} catch (Exception e) {
			log.error("--------连接ftp并获取文件出错----------------");
			e.printStackTrace();
			return new Message(MsgConstant.ERRORCODE, "连接ftp并获取文件出错");
		} finally {
			FtpUtil.disconnectFtp(ftp);
		}
		log.info("--------开始插入ftp文件信息----------------");
		// 获取的文件列表中不全是xml文件， 只获取xml文件 ， 并统计成功获取的xml文件数
		int succFileCount = 0;
		// 将未解析文件信息存入ftpfile表中
		for (TFtpFile file : fileList) {
			try {
				if (file.getType().equals(".xml") || file.getType().equals(".xls") ||file.getType().equals(".xlsx")) {
					// file.setRoleId(roleSb.toString());
					file.setUuid(StringConvert.getUUIDString());
					file.setDatasourceId(dataSourceId);
					file.setDatasourceName(dataSourceConfig.getDsName());
					file.setLockSign(false);
					file.setResolveSign(false);
					file.setStorepath(dataSourceConfig.getUrl());
					file.setStoredate(new Date());
					// file.setCreator(((User)SecurityUtils.getSubject().getSession().getAttribute("currentUser")).getUuid());
					this.getDao().insert(file);
					succFileCount++;
				}
			} catch (Exception e) {
				log.error("--------插入ftp文件信息出错----------------");
				e.printStackTrace();
				return new Message(MsgConstant.ERRORCODE, "插入ftp文件信息出错");
			}
		}
		log.info("--------插入ftp文件信息成功----------------");
		return new Message(MsgConstant.SUCCESSCODE, "ftp文件获取成功：共获取" + succFileCount + "个文件");
	}

	/**
	 * 根据条件获取ftpfile列表中信息， 带分页
	 */
	@Override
	public Map<String, Object> getFtpBaseData(PageUI pageUI, TFtpFile file) {
		Map<String, Object> ftpBaseDatas = new HashMap<>();
		Map<String, Object> pageCondition = new HashMap<>();
		pageCondition.put("pageStart", pageUI.getValue());
		pageCondition.put("pageEnd", pageUI.getSecondValue());
		pageCondition.put("lock_sign", file.getLockSign());
		pageCondition.put("resolve_sign", file.getResolveSign());
		pageCondition.put("datasource_id", file.getDatasourceId());
		pageCondition.put("file_name", file.getFilename());
		pageCondition.put("role_id", file.getRoleId());
		TFtpFileExample example = new TFtpFileExample();
		example.or().andResolveSignEqualTo(file.getResolveSign());
		try {
			ftpBaseDatas.put("rows", this.getDao().selectFtpFiles(pageCondition));
			pageCondition.clear();
			ftpBaseDatas.put("total", this.getDao().countByExample(example));
		} catch (Exception e) {
			log.info("------查询t_base_data表出错----------");
			e.printStackTrace();
		}
		return ftpBaseDatas;
	}

	/**
	 * 
	 * @Description: 将ftpfile表中相应uuid的数据锁定， 锁定后的数据不能触发 @param uuids @return
	 *               String @throws
	 */
	public boolean lockFtpFileData(String[] uuids) {
		try {
			for (int i = 0; i < uuids.length; i++) {
				TFtpFile ftpFileBean = new TFtpFile();
				ftpFileBean.setUuid(uuids[i]);
				TFtpFile ftpFile = this.getDao().selectByPrimaryKey(uuids[i]);
				boolean isLocked = ftpFile.getLockSign();
				// isLocked==1时是已锁定
				if (isLocked == true) {
					ftpFileBean.setLockSign(false);
					triggerConfigService.setTriggerAvailableByDataSourceId(ftpFile.getUuid(), true);
				} else {
					// 若该条数据当前没有锁定，则将其设为锁定状态
					ftpFileBean.setLockSign(true);
					// 同时查找t_trigger_config中的触发状态，已锁定数据不能被触发， 将可被触发状态改为不可被触发
					triggerConfigService.setTriggerAvailableByDataSourceId(ftpFile.getUuid(), false);
				}
				this.getDao().updateByPrimaryKeySelective(ftpFileBean);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据uuid获取文件的内容， 若为xml ， 直接获取xml内容， 若为excel文件， 将其解析为xml格式返回。
	 */
	@Override
	public String getFtpFileContent(String uuid) throws IOException {
		FTPClient ftp = null;
		TFtpFile ftpFile = this.getDao().selectByPrimaryKey(uuid);
		String fileType = ftpFile.getType();
		if (!(fileType.equals(".xml")||fileType.equals(".xls")||fileType.equals(".xlsx"))) {
			return MsgConstant.FORMATERRORCONTENT;
		}
		TDataSourceConfig dataSourceConfig = dataSourceConfigService
				.getDataSourceConfigByUuid(ftpFile.getDatasourceId());
		try {
			ftp = FtpUtil.getFtpClientAngLogin(StringConvert.extractIpAddress(dataSourceConfig.getUrl())[1],
					dataSourceConfig.getPort(), dataSourceConfig.getDsUsername(), dataSourceConfig.getDsPassword());
			if(ftpFile.getType().equals(".xls") || ftpFile.getType().equals(".xlsx")){
				//从字典中获取相应厂家不解析的字段名
				List<SDicDate> dics = iSDicDateService.getSDicDatesByTypecode("unresolveField_ftp_"+dataSourceConfig.getDsUsername());
				String unresolveFields = "";
				if(!(dics == null || dics.size() == 0)){
					unresolveFields = dics.get(0).getdValue();
				}
				return FtpUtil.getFtpExcelFileContent(StringConvert.extractIpAddress(ftpFile.getStorepath())[2], ftpFile.getFilename(),ftp,unresolveFields);
			}
			if(ftpFile.getType().equals(".txt") || ftpFile.getType().equals(".xml")){
				return FtpUtil.getFtpXmlFileContent(StringConvert.extractIpAddress(ftpFile.getStorepath())[2],
						ftpFile.getFilename(), ftp);
			}
			return null;
		}finally {
			FtpUtil.disconnectFtp(ftp);
		}
	}

	/**
	 * 根据ftp文件的uuid， 查找文件信息，将文件解析之后插入数据库
	 */
	@Override
	public Message resolveFtpFileData(String[] uuidsArr) {
		if (uuidsArr == null) {
			return new Message(MsgConstant.ERRORCODE, "未找到待解析数据");
		}
		int successCount = 0;
		for (int i = 0; i < uuidsArr.length; i++) {
			try {
				TFtpFile file = this.getDao().selectByPrimaryKey(uuidsArr[i]);
				String tableName = "ga_" + TableInfoUtil.extractTableName(file.getFilename());
				Map<String, String> createTableMap = null;
				String xmlContent = ResolveFactory.createXmlOldUtil().handleOldXmlStr(StringConvert.replaceXmlSpecialChars(getFtpFileContent(uuidsArr[i])));
				//若文件中xml内容存在乱码或者为空，跳过该文件，不解析
				if(StringConvert.isMessive(xmlContent) || StringConvert.isEmpty(xmlContent)){
					log.error("-----------xml中存在乱码--------------");
					continue;
				}
				if (StringConvert.isEmpty(xmlContent)) {
					return new Message(MsgConstant.ERRORCODE, "在ftp服务器上未找到指定文件");
				}
				String xmlFormat = ResolveFactory.isXmlFormatNew(xmlContent);
				// 查找当前数据库中是否存在相应的表， 若不存在，则创建新表
				if (this.getDao().findIsTableExists(tableName) == 0) {
					if (xmlFormat.equals("new")) {
						createTableMap = ResolveFactory.createXmlNewUtil().genCreateTableMap(xmlContent);
					}else{
						createTableMap = ResolveFactory.createXmlOldUtil().genCreateTableMap(xmlContent, tableName);
					}
					if(createTableMap== null){
						continue;
					}
					this.getDao().createTableByXmlMap(createTableMap);
				}
				// 判断xml中table的字段和当前表中的字段对应关系，若xml中存在新增字段，
				// 则修改表结构，将xml中新增的字段加入表中再插入
				if (xmlFormat.equals("new")) {
					@SuppressWarnings("unchecked")
					String alterFieldStr = ResolveFactory.createXmlNewUtil().getAddFieldDescription(xmlContent,
							(List<TableDescription>) tDataBaseTablesService.getSingleTableInfo(tableName).get("rows"));
					if (!StringConvert.isEmpty(alterFieldStr)) {
						tBaseDataService.alterTable(tableName, "ADD", alterFieldStr);
					}
				}
				if (xmlFormat.equals("old")) {
					@SuppressWarnings("unchecked")
					String alterFieldStr = ResolveFactory.createXmlOldUtil().getAddFieldDescription(xmlContent,
							(List<TableDescription>) tDataBaseTablesService.getSingleTableInfo(tableName).get("rows"));
					if (!StringConvert.isEmpty(alterFieldStr)) {
						tBaseDataService.alterTable(tableName, "ADD", alterFieldStr);
					}
				}
				@SuppressWarnings("unchecked")				
				List<TableDescription> tableFieldInfoList = (List<TableDescription>) tDataBaseTablesService
						.getSingleTableInfo(tableName).get("rows");
				String fieldListStr = TableInfoUtil.getFieldListStrByTableInfo(tableFieldInfoList);
				List<String> fieldList = TableInfoUtil.getFieldListByTableInfo(tableFieldInfoList);
				Map<String, String> fieldTypeMap = TableInfoUtil.getFieldTypeByTableName(tableFieldInfoList);
				List<Map<String, Object>> itemList = null;
				
				if(xmlFormat.equals("old")) {
					if(xmlContent.contains("<ZDEIDOC1><IDOC BEGIN=\"1\"><EDI_DC40 SEGMENT=\"1\">")){
						itemList = ResolveFactory.Resolve(xmlContent, "/ZDEIDOC1/IDOC/ZDEITE1");
					}else{
						itemList = ResolveFactory.Resolve(xmlContent, "/root/subInfo");
					}
				}else{
					itemList = ResolveFactory.Resolve(xmlContent, "/root/item");
				}
				List<String> valueStrList = new ArrayList<>();
				for (Map<String, Object> itemInfoMap : itemList) {
					StringBuffer insertFieldValueSb = new StringBuffer("(");
					for (String field : fieldList) {
						if (field.toUpperCase().equals("UUID")) {
							insertFieldValueSb.append(
									Jdbc_JavaConverter.getJdbcInsertFieldDesc("VARCHAR", StringConvert.getUUIDString())
											+ ",");
						} else {
							insertFieldValueSb.append(Jdbc_JavaConverter.getJdbcInsertFieldDesc(fieldTypeMap.get(field.toLowerCase()),
									((String) itemInfoMap.get(field.toLowerCase()))) + ",");
						}
					}
					valueStrList.add(insertFieldValueSb
							.replace(insertFieldValueSb.length() - 1, insertFieldValueSb.length(), ")").toString());
				}				
				//为避免数据库拒绝服务， 批量插入的数据条数限制在200条每批
				for(int j = 0, endIndex = 0; j < valueStrList.size() ; j+= 200){
					endIndex += 200;
					if(endIndex >= valueStrList.size()){
						endIndex = valueStrList.size();
					}
					this.getDao().insertBatchResolvedBaseData(tableName, fieldListStr.toString(), valueStrList.subList(j, endIndex));
				}
				System.gc();
				// 解析成功后将文件移动到bak中
				FTPClient ftp = null;
				TDataSourceConfig dataSourceConfig = dataSourceConfigService
						.getDataSourceConfigByUuid(file.getDatasourceId());
				try {
					ftp = FtpUtil.getFtpClientAngLogin(StringConvert.extractIpAddress(dataSourceConfig.getUrl())[1],
							dataSourceConfig.getPort(), dataSourceConfig.getDsUsername(),
							dataSourceConfig.getDsPassword());
					FtpUtil.moveFile(StringConvert.extractIpAddress(file.getStorepath())[2], file.getFilename(),
							StringConvert.extractIpAddress(file.getStorepath())[2]+"bak/" + file.getFilename(), ftp);
				} finally {
					FtpUtil.disconnectFtp(ftp);
				}
				// 数据成功解析并插入数据库后，将成功标志改为已成功
				TFtpFile ftpFile = new TFtpFile();
				ftpFile.setUuid(uuidsArr[i]);
				ftpFile.setResolveSign(true);
				ftpFile.setLockSign(true);
				ftpFile.setUpdatedate(new Date());
				ftpFile.setStorepath(file.getStorepath() + "bak/");
				
				this.getDao().updateByPrimaryKeySelective(ftpFile);
				successCount++;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("------------插入uuid为" + uuidsArr[i] + "的数据出错 , 原因:" + e.getMessage());
				// 在某一条数据插入失败时， 不影响之后的数据插入
				continue;
			}
		}
		if(successCount == uuidsArr.length){
			return new Message(MsgConstant.SUCCESSCODE, "ftp文件解析成功，共"+successCount+"条");
		}else{
			return new Message(MsgConstant.SUCCESSCODE, "ftp解析文件"+uuidsArr.length+"条，成功"+successCount+"条");	
		}
		
	}

	/**
	 * @Description: 将t_ftpfile表中解析所有解析标志为未解析的数据解析 @return boolean 返回类型 @throws
	 */
	public void resolveAllUnResolvedFtpData() {
		TFtpFileExample example = new TFtpFileExample();
		example.or().andResolveSignEqualTo(false).andLockSignEqualTo(false);
		List<TFtpFile> fileList = this.getDao().selectByExample(example);
		String[] uuids = null;
		if (fileList != null) {
			uuids = new String[fileList.size()];
			for (int i = 0; i < fileList.size(); i++) {
				uuids[i] = fileList.get(i).getUuid();
			}
		}
		resolveFtpFileData(uuids);
	}

	/*
	 * 删除ftp文件信息
	 */
	@Override
	public boolean deleteFtpFileData(String[] uuids) {
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

}
