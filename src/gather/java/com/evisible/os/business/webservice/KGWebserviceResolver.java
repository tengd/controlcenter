package com.evisible.os.business.webservice;

import java.util.Date;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evisible.os.WebServiceClient;
import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.util.PropertiesUtil;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.util.DateConvert;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;
import com.evisible.os.resolve.ResolveFactory;

/**
 * <p>
 * 获取昆钢WEBSERVICE数据
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年2月11日
 */
@Component
public class KGWebserviceResolver {
	private static Logger log = LoggerFactory.getLogger(KGWebserviceResolver.class);

	@Autowired
	ITBaseDataService tBaseDataService;

	public Message getKGWebServiceData(String queryUrl, String startTime, String dataSourceId) {
		String propertyPath = "properties/webservice_config.properties";
		String[] methods = PropertiesUtil.readValue(propertyPath, "kg.method").split(",");
		String[] customers = PropertiesUtil.readValue(propertyPath, "kg.customer").split(",");
		
		for (int i = 0; i < methods.length; i++) {
			for (int j = 0; j < customers.length; j++) {
				// 获取WebService数据
				String webServiceResult = "";
				try {
					webServiceResult = getKGWebServiceRawData(queryUrl, startTime , methods[i] , customers[j]);
					log.info(webServiceResult);
					// 为了减少使用dom4j加载整个xml资源来判断返回格式是否为xml浪费的资源，
					// 这里直接看字符串是否以<?xml开头来判断是否是xml
					if (!webServiceResult.startsWith("<?xml")) {
						return new Message(MsgConstant.ERRORCODE, webServiceResult);
					}
				} catch (Exception e) {
					log.info("获取WEBSERVICE数据失败");
					return new Message(MsgConstant.ERRORCODE, "获取WEBSERVICE数据失败");
				}
				log.info("----------获取到昆钢数据， 开始写入basedata表-------------------");
				// 将获取到的原始数据插入BaseData
				String tableName = "";
				if(methods[i].equals("PurchaseOrderInterface")){
					tableName = "ga_kg_balance";
				}else if(methods[i].equals("ZSD_DZ_OD_DOWNLOAD")){
					tableName = "t_kg_delivery_info";
				}
				String insertUuid = tBaseDataService.insertTransferedData("", webServiceResult, null, dataSourceId,
						ResolveFactory.createXmlOldUtil().getBaseDataInsertInfo(tableName, "昆钢", ""));
				if (StringConvert.isEmpty(insertUuid)) {
					log.info("----------昆钢数据插入database表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "昆钢数据插入database表失败");
				}
				log.info("----------昆钢原始数据插入basedata表成功 ， 开始解析xml-------------------");
				// 将原始数据插入baseData后， 将其解析，插入相应表
				String[] baseDataUuids = new String[] { insertUuid };
				if (!tBaseDataService.resolveBaseDataXml(baseDataUuids)) {
					log.info("----------解析basedata并插入表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "昆钢数据插入生成表失败");
				}
				log.info("----------解析basedata并插入表成功 , 昆钢数据获取成功-------------------");
				
			}
		}
		return new Message(MsgConstant.SUCCESSCODE, "昆钢数据获取成功");
	}

	/**
	 * 获取昆钢的Webservice接口原始数据
	 */
	private String getKGWebServiceRawData(String url, String begindata, String method, String customer)
			throws Exception {
		String propertyPath = "properties/webservice_config.properties";
		String propertyFilePath = ((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
				.substring(0, (this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
						.lastIndexOf("/classes")))
				+ "/classes/" + propertyPath;
		if (begindata != null && begindata.length() > 0) {
			begindata = begindata.replaceAll("-", "");
			// 将传入的新的begindata写入property
			PropertiesUtil.writeProperties(propertyFilePath, "kg.begindata", begindata);
		} else {
			begindata = DateConvert.date2StrForDay(new Date(), "yyyyMMdd", -1);
		}
		String webServiceResult = "";
		String enddata = DateConvert.date2StrForDay(new Date(), "yyyyMMdd", 0);
		QName qName = new QName(PropertiesUtil.readValue(propertyPath, "kg.targetNamespace"), method);
		Object[] params = new Object[] { begindata, enddata, customer };
		webServiceResult = WebServiceClient.result(url, qName, params);
		log.info("----------已获取到昆钢数据， 时间段：" + begindata + "--" + enddata + "---------------");
		return StringConvert.replaceXmlSpecialChars(webServiceResult);
	}

	public Message getKGWebServiceData_trigger(TDataSourceConfig config) {
		return getKGWebServiceData(config.getUrl(), "", config.getUuid());
	}

}
