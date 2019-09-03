package cxf;

import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class GyClient {
	
	public static void main(String[] args) {
		 JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance(); 
	        Client client = clientFactory.createClient("http://112.112.9.205:6666/XxdjWebService.asmx?WSDL"); 
	        //命名空间 targetNamespace="http://tempuri.org/"
	        QName qName=new QName("http://tempuri.org/","GetTempCode");
	        try {
	            Object[] result=client.invoke(qName, new Object[]{"21001","666666"});
	            System.out.println(result[0].toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
}
