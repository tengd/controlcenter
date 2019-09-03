package com.evisible.os.business.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.business.entity.TBaseData;
import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.entity.TFtpFile;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.business.service.ITDataSourceConfigService;
import com.evisible.os.business.service.ITFtpFileService;
import com.evisible.os.business.webservice.GYWebserviceResover;
import com.evisible.os.business.webservice.KGWebserviceResolver;
import com.evisible.os.business.webservice.YMNYWebserviceResolver;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.IUserService;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;
import com.evisible.os.resolve.ResolveFactory;

/**
 * @author Jiangwandong
 * @Date 创建时间：2018年1月2日 本类作为与外部接口通信，接收请求时的controller
 */

@Controller("baseDataController")
@RequestMapping("/gather")
public class BaseDataController extends BaseController {
	@Autowired
	ITBaseDataService tBaseDataService;
	@Autowired
	IUserService userService;
	@Autowired
	ITFtpFileService ftpFileService;
	@Autowired
	ITDataSourceConfigService dataSourceConfigService;
	@Autowired
	GYWebserviceResover  gyWebserviceResover;
	@Autowired
	YMNYWebserviceResolver ymnyWebserviceResolver;
	@Autowired
	KGWebserviceResolver kgWebserviceResolver;

	/**
	 * <p> 处理外界推送的http请求，解析并存入t_base_data </p>
	 * @author JiangWanDong
	 */
	@ResponseBody
	@RequestMapping(value = "/dataTransfer/put", method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public String handleHttpDataTransferRequest(HttpServletRequest request) {
		String token = request.getParameter("token");
		if(StringConvert.isEmpty(token)){
			return ResponseUtil.creDataTransferResult("WB_000", "请求中没有token");
		}
		//使用token获取相应用户，判断合法性
		User user = userService.validateToken(token);
		if (user == null) {
			return ResponseUtil.creDataTransferResult("WB_000", "不是合法的token");
		}
		String xml = request.getParameter("xml");
		if (StringConvert.isEmpty(xml)) {
			return ResponseUtil.creDataTransferResult("WB_000", "请求中xml参数为空");
		}
		//按照系统设计，主动推送数据的厂家按照标准格式推送，因此使用标准格式解析-存入数据
		Map<String , String> baseDataInfoMap = ResolveFactory.createXmlNewUtil().getBaseDataInsertInfo(xml);
		if(baseDataInfoMap == null){
			return ResponseUtil.creDataTransferResult("WB_000", "xml参数解析错误， 请检查xml格式！");
		}
		String insertedUuid = tBaseDataService.insertTransferedData(token, xml, user, "" , baseDataInfoMap);
		if (StringConvert.isEmpty(insertedUuid)) {
			return ResponseUtil.creDataTransferResult("WB_000", "插入原始数据失败");
		}
		if (!tBaseDataService.resolveBaseDataXml(new String[] {insertedUuid})) {
			return ResponseUtil.creDataTransferResult("WB_000", "解析数据并插入生成表失败");
		}
		return ResponseUtil.creDataTransferResult("WB_111", "数据推送成功");

	}

	@RequestMapping("/getBaseData")
	@ResponseBody
	public Map<String, Object> getBaseDatas(@RequestParam("page") int page, @RequestParam("rows") int rows,
			HttpServletRequest request) {
		PageUI pageUI = new PageUI(page, rows);
		TBaseData baseData = new TBaseData();
		baseData.setDatasourceConfigId(request.getParameter("dataSourceId"));
		System.out.println(request.getParameter("tableName"));
		baseData.setTableName(request.getParameter("tableName"));
		return tBaseDataService.getBaseDatas(baseData, pageUI);
	}

	/**
	 * <p> 根据传来的uuid ， 将t_base_data表中对应的xml解析，生成新表， 插入数据 </p>
	 * @author JiangWanDong
	 */
	@RequestMapping("/resolveBaseData")
	@ResponseBody
	public Object resolveBaseData(HttpServletRequest request) {
		String[] uuidsArr;
		@SuppressWarnings("unused")
		String msg = "";
		try {
			uuidsArr = request.getParameter("uuids").split(",");
		} catch (NullPointerException e) {
			return new Message(MsgConstant.SUCCESSCODE , "uuids为空!");
		}
		if (tBaseDataService.resolveBaseDataXml(uuidsArr)){
			return new Message(MsgConstant.SUCCESSCODE , "xml数据解析成功");
		}
		return null;
			
	}

	/**
	 * <p>
	 * 获取baseData的xmlText
	 * </p>
	 * 
	 * @author JiangWanDong
	 */
	@RequestMapping("/getBaseDataXmlText")
	@ResponseBody
	public TBaseData getBaseDataXmlText(@RequestParam("uuid") String uuid) {
		return tBaseDataService.getBaseData(uuid);
	}

	/**
	 * 
	 * <p>
	 * 根据uuid锁定数据
	 * </p>
	 * 
	 * @author JiangWanDong
	 */
	@RequestMapping("/lockData")
	@ResponseBody
	public Object lockBaseData(HttpServletRequest request) {
		String uuids = request.getParameter("uuids");
		String dataSourceId = request.getParameter("dataSourceId");
		String[] uuidsArr = null;
		if (!StringConvert.isEmpty(uuids)) {
			uuidsArr = uuids.split(",");
		}
		TDataSourceConfig dataSourceConfig =  dataSourceConfigService.getDataSourceConfigByUuid(dataSourceId);
		if(dataSourceConfig == null &&  tBaseDataService.lockBaseData(uuidsArr)){
			return new Message(SUCCESS, "锁定数据成功");
		}
		String dataType = dataSourceConfig.getType();
		if (!StringConvert.isEmpty(dataType)) {
			if (dataType.equals("webservice") && tBaseDataService.lockBaseData(uuidsArr)) {
				return new Message(SUCCESS, "锁定数据成功");
			} else if (dataType.equals("ftp") && ftpFileService.lockFtpFileData(uuidsArr)) {
				return new Message(SUCCESS, "锁定数据成功");
			}
		}
		return new Message(ERROR, "锁定数据失败");
	}

	/**
	 * 
	 * <p>
	 * 获取并插入Webservice消息
	 * </p>
	 * 
	 * @author JiangWanDong
	 */
	@RequestMapping("/getWebServiceData")
	@ResponseBody
	public Object getWebServiceData(HttpServletRequest request) {
		if(!StringConvert.isEmpty(request.getParameter("dsValue"))){
			switch(request.getParameter("dsValue").toUpperCase()){
			case "GY": return gyWebserviceResover.getGYWebServiceData(request.getParameter("url"), request.getParameter("startTime"), request.getParameter("dataSourceId"));
			case "YMNY" :return ymnyWebserviceResolver.getYMNYWebServiceData(request.getParameter("url"), request.getParameter("startTime"), request.getParameter("dataSourceId"));
			case "KG" : return kgWebserviceResolver.getKGWebServiceData(request.getParameter("url"), request.getParameter("startTime"), request.getParameter("dataSourceId"));
			default : return new Message(MsgConstant.ERRORCODE,"不支持该厂家接口调用");
			}			
		}
		return new Message(MsgConstant.ERRORCODE,"厂家信息为空");
	}



	/*
	 * 获取ftp服务器上未解析文件信息，存入ftp文件信息表
	 */
	@RequestMapping(value = "/getFtpServerData" , method=RequestMethod.GET)
	@ResponseBody
	public Object getFtpServerData(@RequestParam("dataSourceId") String dataSourceId) {
		return ftpFileService.getFtpFilesInfo(dataSourceId);
	}

	/**
	 * 从ftpfile表中获取ftp文件信息
	 */
	@RequestMapping("/getFtpBaseData")
	@ResponseBody
	public Map<String, Object> getFtpBaseData(@RequestParam("page") int page, @RequestParam("rows") int rows,
			HttpServletRequest request) {
		PageUI pageUI = new PageUI(page, rows);
		TFtpFile file = new TFtpFile();
		file.setResolveSign(false);
		if (!StringConvert.isEmpty(request.getParameter("resolve_sign")) && request.getParameter("resolve_sign").equals("true")) {
				file.setResolveSign(true);		
		}
		file.setRoleId(request.getParameter("roleId"));
		file.setDatasourceId(request.getParameter("dataSourceId"));
		file.setFilename(request.getParameter("fileName"));
		return ftpFileService.getFtpBaseData(pageUI, file);
	}

	@RequestMapping("/getFtpFileContent")
	@ResponseBody
	public Map<String , String> getFtpFileContent(HttpServletRequest request) {
		Map<String , String> resultMap = new HashMap<>();
		try {
			resultMap.put("xmlText",ftpFileService.getFtpFileContent(request.getParameter("uuid")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return resultMap;
	}
	
	/**
	 * <p> 根据传来的uuid ， 将t_base_data表中对应的xml解析，生成新表， 插入数据 </p>
	 * @author JiangWanDong
	 */
	@RequestMapping("/resolveFtpFileData")
	@ResponseBody
	public Object resolveFtpFileData(HttpServletRequest request) {
		return ftpFileService.resolveFtpFileData(request.getParameter("uuids").split(","));
	}
	
	@RequestMapping("/deleteData")
	@ResponseBody
	public Message deleteBaseDatas(HttpServletRequest request){
		String uuids = request.getParameter("uuids");
		String[] uuidsArr = null;
		if (!StringConvert.isEmpty(uuids)) {
			uuidsArr = uuids.split(",");
		}
		String dataType = request.getParameter("dataType");
		if (!StringConvert.isEmpty(dataType)) {
			if ((dataType.equals("webservice") || StringConvert.isEmpty(dataType)) && tBaseDataService.deleteBaseData(uuidsArr)) {
				return new Message(SUCCESS, "删除数据成功");
			} else if (dataType.equals("ftp") && ftpFileService.deleteFtpFileData(uuidsArr)) {
				return new Message(SUCCESS, "删除数据成功");
			}
		}
		return new Message(ERROR, "删除数据失败");
	}
	
}
