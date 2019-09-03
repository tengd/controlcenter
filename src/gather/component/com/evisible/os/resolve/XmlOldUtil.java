
package com.evisible.os.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.jaxen.JaxenException;

import com.evisible.os.business.entity.TableDescription;
import com.evisible.os.business.util.Jdbc_JavaConverter;

/**
 * <p>
 * xml解析工具类， 生成建表语句 ， 生成插入语句 ，针对未规范的旧的xml格式
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年2月8日
 */
public class XmlOldUtil {
	/**
	 * 
	 * @Description: 旧格式的建表语句，只有subInfo节点
	 */
	public static String getCreateTableDescription(String xmlStr) {
		if (!xmlStr.startsWith("<?xml")) {
			return null;
		}
		List<Map<String, Object>> fieldList = null;
		if(xmlStr.contains("<ZDEIDOC1><IDOC BEGIN=\"1\"><EDI_DC40 SEGMENT=\"1\">")){
			fieldList = ResolveFactory.Resolve(xmlStr, "/ZDEIDOC1/IDOC/ZDEITE1[1]");
		}else{
			fieldList = ResolveFactory.Resolve(xmlStr, "/root/subInfo[1]");
			if(fieldList == null || fieldList.size() ==0){
				return null;
			}
		}
		StringBuffer sb = new StringBuffer("(uuid VARCHAR(32) PRIMARY KEY, ");
		if (fieldList != null && fieldList.size() > 0) {
			Map<String, Object> map = fieldList.get(0);
			Set<String> keySet = map.keySet();
			for (String str : keySet) {
				sb.append(str + " " + Jdbc_JavaConverter.getJdbcMysqlTypeDescription("", "") + " COMMENT '" + "',");
			}
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		return sb.toString();
	}

	public Map<String, String> getBaseDataInsertInfo(String tableName , String companyName , String companyCode) {
		Map<String, String> map = new HashMap<>();
		map.put("companyName", companyName);
		map.put("companyCode", "");
		map.put("tableName", tableName);
		return map;
	}
	
	/**
	 * <p>
	 * 指定xml xpath ， 根据node的内容生成建表语句
	 * </p>
	 * 
	 * @author JiangWanDong
	 * @throws DocumentException
	 * @throws JaxenException
	 */
	@SuppressWarnings("static-access")
	public Map<String, String> genCreateTableMap(String xmlStr , String tableName) throws DocumentException {
		Map<String, String> tableCreMap = new HashMap<>();
		XmlOldUtil xmlUtil = ResolveFactory.createXmlOldUtil();
		tableCreMap.put("tableName", tableName);
		tableCreMap.put("tableDescription", " ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=''");
		String tableDesc =  xmlUtil.getCreateTableDescription(xmlStr);
		tableCreMap.put("tableAttrs",tableDesc);
		if(tableDesc == null){
			return null;
		}
		return tableCreMap;
	}
	
	/**
	 * 对比新传入的xml中的字段与当前表中存在的字段，若传入的xml中的字段数量大于表中的字段， 则生成修改表字段的语句
	 * @throws DocumentException 
	 * @throws JaxenException 
	 */
	public String getAddFieldDescription(String xmlStr , List<TableDescription> currTableDescList){
		try {
			//解析xml中table节点的字段
			List<Map<String, Object>> xmlFieldList = null;
			if(xmlStr.contains("<ZDEIDOC1><IDOC BEGIN=\"1\"><EDI_DC40 SEGMENT=\"1\">")){
				xmlFieldList = ResolveFactory.Resolve(xmlStr, "/ZDEIDOC1/IDOC/ZDEITE1[1]");
			}else{
				xmlFieldList = ResolveFactory.Resolve(xmlStr, "/root/subInfo[1]");
			}
			List<String> currXmlFields = new ArrayList<>();
			Set<String> keySet = xmlFieldList.get(0).keySet();
			//将xml中所有field的名称放入一个列表currXmlFields
			for (String field : keySet) {
				currXmlFields.add(field.trim().toLowerCase());
			}
			List<String> currTableFields = new ArrayList<>();
			for (TableDescription tableDescription : currTableDescList) {
				currTableFields.add(tableDescription.getField().toLowerCase());
			}
			currXmlFields.removeAll(currTableFields);
			if (currXmlFields.size() > 0) {
				StringBuffer alterStrBuf = new StringBuffer("(");
				for (String fieldName : currXmlFields) {
						alterStrBuf.append(fieldName.toLowerCase() + " "
								+ Jdbc_JavaConverter.getJdbcMysqlTypeDescription("STRING", "") + " COMMENT ''");
						alterStrBuf.append(",");

				}
				alterStrBuf.replace(alterStrBuf.length() - 1, alterStrBuf.length(), ")");
				return alterStrBuf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;		
	}
	
	/**
	 * 某些旧格式的xml格式存在问题， 如存在两个<xml>头 ， 需要处理
	 */
	public String handleOldXmlStr(String xmlStr){
		return xmlStr.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:revInvoiceStatus xmlns:ns0=\"cxfWebService\"><xml>", "").replace("</xml></ns0:revInvoiceStatus>", "");
	}
}
