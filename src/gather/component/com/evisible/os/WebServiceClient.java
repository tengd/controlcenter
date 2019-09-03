package com.evisible.os;

import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
/**
 * <p>WebService客户端</p>
 * @author TengDong
 * @Date 20180115
 */
public class WebServiceClient {
	/**
	 * @param url 地址
	 * @param qName 命名空间，方法
	 * @param objs 参数
	 * @return String
	 * @throws Exception 
	 */
	public static String result(String url,QName qName,Object[] objs) throws Exception{
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
		Client client=null;
		 try {
			 client= clientFactory.createClient(url); 
			        Object[] result=client.invoke(qName,objs);
			        return result[0].toString();
		} catch (Exception e) {
			client.destroy();
			e.printStackTrace();
			return null;
		}		
	}
}
