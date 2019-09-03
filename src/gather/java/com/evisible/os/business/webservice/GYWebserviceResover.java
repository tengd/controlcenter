
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
import com.evisible.os.business.util.Sign;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.util.DateConvert;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;
import com.evisible.os.resolve.ResolveFactory;

/**
 * <p>国药的webservice处理类， 需要调用两次接口， 第一次获取验证码， 第二次获取xml内容 </p>
 * @author JiangWanDong
 * @Date 2018年2月8日
 */
@Component
public class GYWebserviceResover {
	private static Logger log = LoggerFactory.getLogger(GYWebserviceResover.class);

	@Autowired
	ITBaseDataService tBaseDataService;
	
	public Message getGYWebServiceData(String queryUrl , String startTime , String dataSourceId){
		// 获取国药WebService数据
		String webServiceResult = "";
		try {
			webServiceResult = getGYWebServiceRawData(queryUrl, startTime);
			log.info(webServiceResult);
			// 为了减少使用dom4j加载整个xml资源来判断返回格式是否为xml浪费的资源，
			// 这里直接看字符串是否以<?xml开头来判断是否是xml
			if (!webServiceResult.startsWith("<?xml")) {
				return new Message(MsgConstant.ERRORCODE,webServiceResult);
			}
		} catch (Exception e) {
			log.info("获取WEBSERVICE数据失败");
			return new Message(MsgConstant.ERRORCODE,"获取WEBSERVICE数据失败");
		}
		log.info("----------获取到国药数据， 开始写入basedata表-------------------");
		// 将获取到的原始数据插入BaseData
		String insertUuid = tBaseDataService.insertTransferedData("", webServiceResult, null, dataSourceId , ResolveFactory.createXmlNewUtil().getBaseDataInsertInfo(webServiceResult));
		if (StringConvert.isEmpty(insertUuid)) {
			log.info("----------国药数据插入database表失败-------------------");
			return new Message(MsgConstant.ERRORCODE,"国药数据插入database表失败");
		}
		log.info("----------国药原始数据插入basedata表成功 ， 开始解析xml-------------------");
		// 将原始数据插入baseData后， 将其解析，插入相应表
		String[] baseDataUuids = new String[] { insertUuid };
		if (!tBaseDataService.resolveBaseDataXml(baseDataUuids)) {
			log.info("----------解析basedata并插入表失败-------------------");
			return new Message(MsgConstant.ERRORCODE,"国药数据插入生成表失败");
		}
		log.info("----------解析basedata并插入表成功 , 国药数据获取成功-------------------");
		return new Message(MsgConstant.SUCCESSCODE,"国药数据获取成功");
	}
	
	/**
	 * 获取国药的Webservice接口原始数据
	 */
	private String getGYWebServiceRawData(String url, String begindata) throws Exception {
		String propertyPath = "properties/webservice_config.properties";
		String propertyFilePath = ((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).substring(0, (this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).lastIndexOf("/classes"))) + "/classes/"+propertyPath;	
		// step1：获取国药的验证码
		if (begindata != null && begindata.length() > 0) {
			begindata = begindata.replaceAll("-", "");
			// 将传入的新的begindata写入property
			PropertiesUtil.writeProperties(propertyFilePath,"gy.begindata", begindata);
		} else {
			begindata = DateConvert.date2StrForDay(new Date(), "yyyyMMdd", -1);
		}
		QName qName = new QName(PropertiesUtil.readValue(propertyPath, "gy.targetNamespace"),PropertiesUtil.readValue(propertyPath, "gy.method_1"));
		String validateCode = WebServiceClient.result(url, qName, new Object[] {PropertiesUtil.readValue(propertyPath, "gy.id"),PropertiesUtil.readValue(propertyPath, "gy.paswd")});
		log.info("----------------------已获取到国药的验证码：" + validateCode + "---------------");
		// Step2.获取到国药验证码之后， 使用验证码获取xml
		String webServiceResult = "";
		if (validateCode != null) {
			String enddata = DateConvert.date2StrForDay(new Date(), "yyyyMMdd", 0);
			qName = new QName(PropertiesUtil.readValue(propertyPath, "gy.targetNamespace"),PropertiesUtil.readValue(propertyPath, "gy.method_2"));
			Object[] params = new Object[] {PropertiesUtil.readValue(propertyPath, "gy.id"),PropertiesUtil.readValue(propertyPath, "gy.paswd"),PropertiesUtil.readValue(propertyPath, "gy.port"), begindata, enddata, Sign.getEnv().md5code(validateCode)};
			webServiceResult = WebServiceClient.result(url, qName, params);
			log.info("----------已获取到国药数据， 时间段：" + begindata + "--" + enddata + "---------------");
		}

		return StringConvert.replaceXmlSpecialChars(webServiceResult);
	}	
	
	/**
	 * 
	 * <p>
	 * 本方法为Quartz调用的获取国药WebService数据调用的方法
	 * </p>
	 * 
	 * @author JiangWanDong
	 */
	public String getGYWebServiceData_trigger(TDataSourceConfig dataSourceConfig) {
		String webServiceResult = "";
		try {
			String queryUrl = dataSourceConfig.getUrl();
			webServiceResult = getGYWebServiceRawData(queryUrl, null);
			log.info(webServiceResult);
			// 为了减少使用dom4j加载整个xml资源来判断返回格式是否为xml浪费的资源，
			// 这里直接看字符串是否以<?xml开头来判断是否是xml
			if (!webServiceResult.startsWith("<?xml")) {
				return "error_" + webServiceResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("获取WEBSERVICE数据失败");
		}
		String insertedUuid = "";
		try {
			insertedUuid = tBaseDataService.insertTransferedData("", webServiceResult, null,
					dataSourceConfig.getUuid() , ResolveFactory.createXmlNewUtil().getBaseDataInsertInfo(webServiceResult));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return insertedUuid;
	}
}
