package test;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.jaxen.JaxenException;

import com.evisible.os.resolve.ResolveFactory;




/**
 * 
 * <p>xml解析测试</p>
 * @author JiangWanDong
 * @Date   2018年1月9日
 */
public class XmlTest {

	public static void main(String[] args) throws DocumentException, JaxenException {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <!-- edited with XMLSpy v2013 (http://www.altova.com) by  () --> <root> 	<company>"
				+ "<name>云南云天化红磷分公司</name><code>NO0001559</code></company><table>"
				+ "<name>ga_yth_hl_weight</name><comment>过磅信息表</comment><field><name>ticketno</name>"
				+ "<size>13</size><type>String</type><comment>交易磅单号</comment></field><field><name>truckno</name>"
				+ "<size>15</size><type>String</type><comment>车牌号</comment></field><field><name>product</name><size>20</size>"
				+ "<type>String</type><comment>产品名称</comment></field><field>"
				+ "<name>sender</name><size>200</size><type>String</type><comment>供应商</comment></field><field><name>receiver</name><size>200</size>"
				+ "<type>String</type><comment>收货单位</comment></field><field><name>transporter</name><size>200</size><type>String</type><comment>物流公司</comment>"
				+ "</field><field><name>firstdatetime</name><size/><type>date</type><comment>临时称重时间</comment></field><field><name>seconddatetime</name><size/><type>date</type><comment>交易称重时间</comment>"
				+ "</field><field><name>firstweight</name><size>20</size><type>String</type><comment>临时重量</comment></field><field><name>secondweight</name>"
				+ "<size>20</size><type>String</type><comment>交易重量</comment></field><field><name>net</name><size>20</size><type>String</type><comment>净重</comment>"
				+ "</field><field><name>username1</name><size>20</size><type>String</type><comment>临时称重操作员名</comment></field>"
				+ "<field><name>username2</name><size>20</size><type>String</type><comment>交易称重操作员名</comment></field><field>"
				+ "<name>bc1</name><size>10</size><type>String</type><comment>临时称重班次</comment></field><field><name>bc2</name>"
				+ "<size>10</size><type>String</type><comment>交易称重班次</comment></field></table><item><ticketno>1201711200067</ticketno><truckno>云G42056</truckno><product>粉煤</product>"
				+ "<sender>弥勒县金昌贸易有限公司</sender><receiver>生产制造中心合成氨厂</receiver><transporter>红河上齐物流有限公司</transporter><firstdatetime>2017-11-20 15:30:13</firstdatetime><seconddatetime>2017-11-20 15:52:31</seconddatetime>"
				+ "<firstweight>42840</firstweight><secondweight>13080</secondweight><net>29760</net><username1>李丹</username1>"
				+ "<username2>李丹</username2><bc1>早班</bc1><bc2>早班</bc2></item></root>";
		String xpathExpr = "/root/table/field[name='RELATEBILLNO']";
		//System.out.println(XmlUtil.getSingleNodeInfo(xmlStr, xpathExpr));
		List<Map<String , Object>> list = ResolveFactory.Resolve(xmlStr , xpathExpr);
		for(int i = 0 ; i < list.size() ; i++){
			System.out.println(list.get(i));
		}
		
	}
	
	private static List<Map<String , Object>> resolve(String xmlStr , String path) throws DocumentException, JaxenException{
		
		return null;
	}

}
