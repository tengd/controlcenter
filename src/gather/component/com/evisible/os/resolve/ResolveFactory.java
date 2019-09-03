package com.evisible.os.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evisible.os.resolve.excel.ExcelUtil;

/**
 * <p>
 * 解析工厂
 * </p>
 * 
 * @author TengDong
 * @Date 2018年1月8日
 */
public class ResolveFactory {
	private static Logger log = LoggerFactory.getLogger(ResolveFactory.class);

	public ResolveFactory() {
	}

	public static XmlNewUtil createXmlNewUtil() {
		log.info("------创建XmlNewUtil类开始------");
		return new XmlNewUtil();
	}

	public static XmlOldUtil createXmlOldUtil() {
		log.info("------创建XmlOldUtil类开始------");
		return new XmlOldUtil();
	}

	public static JsonUtil createJsonUtil() {
		log.info("------创建JsonUtil类开始------");
		return new JsonUtil();
	}
	
	/**
	 * 获取解析excel的工具类
	 */
	public static ExcelUtil createExcelUtil(){
		log.info("------创建ExcelUtil类开始------");
		return new ExcelUtil();
	}

	/**
	 * <p>
	 * 获取非最下层节点的信息， 其子节点信息以List形式返回
	 * </p>
	 * 
	 * @author JiangWanDong
	 * @throws DocumentException
	 * @throws JaxenException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> Resolve(String xml_Str, String xpathExpr) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Document document = DocumentHelper.parseText(xml_Str); // 将字符串转为XML
			XPath xpath = new Dom4jXPath(xpathExpr);
			List<Node> nodeList = xpath.selectNodes(document);
			Map<String, Object> hashMap = null;
			for (Object result : nodeList) {
				Node node = (Node) result;
				Document document_node = DocumentHelper.parseText(node.asXML());
				Element root = document_node.getRootElement();
				List<Element> elements = root.elements();
				if (!elements.isEmpty()) {
					hashMap = new HashMap<String, Object>();
				}
				for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
					Element element = it.next();
					String name = element.getName();
					String text = element.getText();
					hashMap.put(name.toLowerCase(), text.toLowerCase());
				}
				if (hashMap != null && hashMap.size() > 0) {
					list.add(hashMap);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	/**
	 * <p>
	 * 获取单个节点的内容，节点路径以xpath指定
	 * </p >
	 * 
	 * @author JiangWanDong
	 * @throws DocumentException
	 */
	public static String getSingleNodeInfo(String xmlStr, String xpathExpr) {
		String nodeText = "";
		Document document;
		try {
			document = DocumentHelper.parseText(xmlStr);
			Node node = document.selectSingleNode(xpathExpr);
			nodeText = node.getText();
			return nodeText;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;			
		}
		
	}

	/**
	 * 
	 * @Description: 判断传入的xml是否是新格式（标准格式） @param xmlStr @return boolean @throws
	 */
	public static String isXmlFormatNew(String xmlStr) {
		try{
			getSingleNodeInfo(xmlStr,"/root/company/name");
			getSingleNodeInfo(xmlStr,"/root/table/name");
		}catch(Exception e){
			return "old";
		}
		return "new";
	}
}
