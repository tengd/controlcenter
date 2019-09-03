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
 * <p>云煤能源的webservice处理类， 需要获取火车和卡车的数据并分别建表</p>
 * @author JiangWanDong
 * @Date 2018年2月8日
 */
@Component
public class YMNYWebserviceResolver {

	private static Logger log = LoggerFactory.getLogger(YMNYWebserviceResolver.class);

	@Autowired
	ITBaseDataService tBaseDataService;

	public Message getYMNYWebServiceData(String queryUrl, String startTime, String dataSourceId) {
		// 获取云煤能源WebService数据
		String[] webServiceResult;
		try {
			webServiceResult = getYMNYWebServiceRawData(queryUrl, startTime);
			// 为了减少使用dom4j加载整个xml资源来判断返回格式是否为xml浪费的资源，
			// 这里直接看字符串是否以<?xml开头来判断是否是xml
			if (!StringConvert.isEmpty(webServiceResult[0]) && webServiceResult[0].startsWith("<?xml")) {
				log.info("获取到云煤能源火车数据："+webServiceResult[0]);
				log.info("--------------开始将云煤能源火车数据插入basedata表---------------------");
				String insertUuid_train = tBaseDataService.insertTransferedData("", webServiceResult[0], null,
						dataSourceId,ResolveFactory.createXmlOldUtil().getBaseDataInsertInfo("ga_ymny_trainweight", "云煤能源", ""));
				if (StringConvert.isEmpty(insertUuid_train)) {
					log.info("----------云煤能源火车数据插入database表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "国药数据插入database表失败");
				}
				log.info("--------------云煤能源火车数据插入basedata表成功 ， 开始解析云煤能源train的数据---------------------");
				String[] baseDataUuids_train = new String[] { insertUuid_train };
				if (!tBaseDataService.resolveBaseDataXml(baseDataUuids_train)) {
					log.info("----------解析basedata并插入表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "云煤能源火车数据数据插入生成表失败");
				}
			}
			
			if (!StringConvert.isEmpty(webServiceResult[1]) && webServiceResult[1].startsWith("<?xml")) {
				log.info("获取到云煤能源卡车数据："+webServiceResult[1]);
				log.info("--------------开始将云煤能源卡车数据插入basedata表---------------------");
				String insertUuid_truck = tBaseDataService.insertTransferedData("", webServiceResult[1], null,
						dataSourceId,
						ResolveFactory.createXmlOldUtil().getBaseDataInsertInfo("ga_ymny_truckweight", "云煤能源", ""));
				if (StringConvert.isEmpty(insertUuid_truck)) {
					log.info("----------云煤能源卡车数据插入database表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "云煤能源卡车数据插入database表失败");
				}
				log.info("--------------云煤能源卡车数据插入basedata表成功 ， 开始解析云煤能源卡车的数据---------------------");
				String[] baseDataUuids_truck = new String[] { insertUuid_truck };
				if (!tBaseDataService.resolveBaseDataXml(baseDataUuids_truck)) {
					log.info("----------解析basedata并插入表失败-------------------");
					return new Message(MsgConstant.ERRORCODE, "云煤能源卡车数据数据插入生成表失败");
				}
			}

		} catch (Exception e) {
			log.info("获取WEBSERVICE数据失败");
			return new Message(MsgConstant.ERRORCODE, "获取WEBSERVICE数据失败");
		}
		return new Message(MsgConstant.SUCCESSCODE, "云煤能源数据获取成功");
	}

	/**
	 * 获取云煤能源的webservice数据， 分别为火车和卡车的数据， 放在数组内
	 */
	private String[] getYMNYWebServiceRawData(String url, String begindata) throws Exception {
		String propertyUrl = "properties/webservice_config.properties";
		String propertyFilePath = ((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
				.substring(0, (this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
						.lastIndexOf("/classes")))
				+ "/classes/"+propertyUrl;		
		if (!StringConvert.isEmpty(begindata)) {
			begindata = begindata.replaceAll("-", "");
			PropertiesUtil.writeProperties(propertyFilePath, "ymny.begindata", begindata);
		} else {
			begindata = DateConvert.date2StrForDay(new Date(), "yyyyMMdd", -1);
		}
		return new String[] {
				StringConvert.replaceXmlSpecialChars(WebServiceClient.result(url, new QName(
						PropertiesUtil.readValue(propertyUrl, "ymny.targetNamespace"),
						PropertiesUtil.readValue(propertyUrl, "ymny.trainMethod")),
						new Object[] { begindata, DateConvert.date2StrForDay(new Date(), "yyyyMMdd", 0), "" })),
				StringConvert.replaceXmlSpecialChars(WebServiceClient.result(url, new QName(
						PropertiesUtil.readValue(propertyUrl, "ymny.targetNamespace"),
						PropertiesUtil.readValue(propertyUrl, "ymny.truckMethod")),
						new Object[] { begindata, DateConvert.date2StrForDay(new Date(), "yyyyMMdd", 0), "" })) };
	}
	
	public Message getYMNYWebServiceData_trigger(TDataSourceConfig config){
		return getYMNYWebServiceData(config.getUrl() , "" , config.getUuid());
	}

}
