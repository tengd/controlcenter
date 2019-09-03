package com.evisible.os.business.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>对外WebService接口</p>
 * @author TengDong
 * @Data 20180108
 */
@WebService
public interface IBaseData {
	/**
	 * @param token token
	 * @param xmlString Xml字符串
	 * @return 报文
	 */
	public String putAccess(@WebParam(name="token")String token,@WebParam(name="xml")String xmlString);
	
	
	
}
