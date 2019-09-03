package com.evisible.os.business.cxf.impl;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.evisible.os.business.cxf.IBaseData;
import com.evisible.os.business.service.ITBaseDataService;
import com.evisible.os.controlcenter.system.entity.SysLog;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISysLogService;
import com.evisible.os.controlcenter.system.service.IUserService;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.resolve.ResolveFactory;

/**
 * <p>
 * 外部接口提供实现
 * </p>
 * 
 * @author TengDong
 * @Date 2018年1月8日
 */
@WebService(endpointInterface = "com.evisible.os.business.cxf.IBaseData")
public class BaseData implements IBaseData {
	@Autowired
	ITBaseDataService tBaseDataService;
	@Autowired
	IUserService userService;
	@Resource
	private WebServiceContext wsContext;
	/**
	 * @see SysLogService
	 */
	@Autowired
	private ISysLogService sysLogService;

	@Override
	public String putAccess(String token, String xmlString) {
		User user = null;
		// 请求数据中必须带有token以验证合法性
		if (StringConvert.isEmpty(token)) {
			return ResponseUtil.creDataTransferResult("WB_000", "请联系提供token");
		}
		// 验证token的合法性， 是否是系统内已注册过的合法token
		user = userService.validateToken(token);
		if (user == null) {
			return ResponseUtil.creDataTransferResult("WB_000", "token不合法， 请提供合法token");
		}
		if (StringConvert.isEmpty(xmlString) || !StringConvert.isXML(xmlString)) {
			// xml格式验证
			return ResponseUtil.creDataTransferResult("WB_000", "请求中xml参数为空或不是合法的xml");
		}

		String tableName = ResolveFactory.getSingleNodeInfo(xmlString, "/root/table/name");
		if (StringConvert.isEmpty(tableName) || StringConvert.isMessive(tableName)) {
			return ResponseUtil.creDataTransferResult("WB_000", "xml中表名为空或乱码，请检查xml格式");
		}
		if (StringConvert.isMessive(xmlString)) {
			return ResponseUtil.creDataTransferResult("WB_000", "xml中带有无法解析的乱码，请检查xml格式");

		}
		// 根据token找到相应用户，加日志，将该用户调用webservice接口记录到日志
		try {
			SysLog record = new SysLog();
			record.setUuid(StringConvert.getUUIDString());
			MessageContext mc = wsContext.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
			String clientIP = request.getRemoteAddr();
			if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getHeader("Proxy-Client-IP");
			}
			if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getHeader("WL-Proxy-Client-IP");
			}
			if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getRemoteAddr();
			}
			// 客户端IP
			record.setVisitIp(clientIP);
			record.setuId(user.getUuid());
			record.setuName(user.getuName());
			record.setFunId("");
			boolean isDataEmpty = false;
			if(ResolveFactory.Resolve(xmlString, "/root/item")== null){
				record.setFunName(user.getuName() + "调用了对外接口" + this.getClass()+",有效数据为空");
				isDataEmpty = true;
			}else if(ResolveFactory.Resolve(xmlString, "/root/item").size() == 0){
				record.setFunName(user.getuName() + "调用了对外接口" + this.getClass()+",有效数据为空");
				isDataEmpty = true;
			}else{
				record.setFunName(user.getuName() + "调用了对外接口" + this.getClass());
			}			
			record.setVisitOs(System.getProperty("os.name"));
			sysLogService.addSysLog(record);
			if(isDataEmpty){
				return ResponseUtil.creDataTransferResult("WB_111", "数据传输成功， 但没有有效数据");
			}
		} catch (Exception e) {
			System.out.println("----添加日志出现异常！---");
			e.printStackTrace();
		}
		String uuid = tBaseDataService.insertTransferedData(token, xmlString, user, "",
				ResolveFactory.createXmlNewUtil().getBaseDataInsertInfo(xmlString));
		if (StringConvert.isEmpty(uuid)) {
			return ResponseUtil.creDataTransferResult("WB_000", "数据插入失败");
		}
		if (tBaseDataService.resolveBaseDataXml(new String[] { uuid })) {
			return ResponseUtil.creDataTransferResult("WB_111", "数据推送成功");
		} else {
			return ResponseUtil.creDataTransferResult("WB_000", "XML解析异常");
		}
	}

}
