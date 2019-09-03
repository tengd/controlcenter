package com.evisible.os.resolve;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.jaxen.JaxenException;

import com.evisible.os.business.entity.TableDescription;
import com.evisible.os.business.util.Jdbc_JavaConverter;
import com.evisible.os.controlcenter.util.StringConvert;

/**
 * <p>
 * Xml解析工具类
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月4日
 */
public class XmlNewUtil {

	// 生成建表语句描述，根据xml内容生成建表语句
	public String getCreateTableDescription(String xmlStr) {
		if (!StringConvert.isXML(xmlStr)) {
			return null;
		}
		List<Map<String, Object>> fieldList = null;
		fieldList = ResolveFactory.Resolve(xmlStr, "/root/table/field");
		if(fieldList == null){
			return null;
		}
		StringBuffer sb = new StringBuffer("(uuid VARCHAR(32) PRIMARY KEY, ");
			for (Map<String, Object> map : fieldList) {
				sb.append((String) map.get("name") + " " + Jdbc_JavaConverter
						.getJdbcMysqlTypeDescription((String) map.get("type"), (String) map.get("size")) + " COMMENT '"
						+ (String) map.get("comment") + "',");
			}
			sb.deleteCharAt(sb.length() - 1).append(")");
		return sb.toString();
	}

	/**
	 * 标准xml中带有公司名、公司编码、表名， 将这些信息解析出来之后存入map返回， 以便构成basedata的相应字段信息
	 */
	public Map<String, String> getBaseDataInsertInfo(String xmlStr) {
		Map<String, String> map = new HashMap<>();
			map.put("companyName", ResolveFactory.getSingleNodeInfo(xmlStr, "/root/company/name"));
			map.put("companyCode", ResolveFactory.getSingleNodeInfo(xmlStr, "/root/company/code"));
			map.put("tableName", ResolveFactory.getSingleNodeInfo(xmlStr, "/root/table/name"));		
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
	public Map<String, String> genCreateTableMap(String xmlStr) throws DocumentException {
		Map<String, String> tableCreMap = new HashMap<>();
		String tableName = "";
		String comment = "";
		tableName = ResolveFactory.getSingleNodeInfo(xmlStr, "/root/table/name");
		comment = ResolveFactory.getSingleNodeInfo(xmlStr, "/root/table/comment");
		tableCreMap.put("tableName", tableName);
		tableCreMap.put("tableDescription", " ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + comment + "'");
		tableCreMap.put("tableAttrs", ResolveFactory.createXmlNewUtil().getCreateTableDescription(xmlStr));
		return tableCreMap;
	}

	/**
	 * 对比新传入的xml中的字段与当前表中存在的字段，若传入的xml中的字段数量大于表中的字段， 则生成修改表字段的语句
	 * 
	 * @throws DocumentException
	 * @throws JaxenException
	 */
	public String getAddFieldDescription(String xmlStr, List<TableDescription> currTableDescList) {
		try {
			// 解析xml中table节点的字段
			List<Map<String, Object>> xmlFieldList = ResolveFactory.Resolve(xmlStr, "/root/table/field");
			List<String> currXmlFields = new ArrayList<>();
			// 将xml中所有field的名称放入一个列表currXmlFields
			for (Map<String, Object> map : xmlFieldList) {
				currXmlFields.add(((String) map.get("name")).toLowerCase());
			}
			List<String> currTableFields = new ArrayList<>();
			for (TableDescription tableDescription : currTableDescList) {
				currTableFields.add(tableDescription.getField().toLowerCase());
			}
			
			currXmlFields.removeAll(currTableFields);
			if (currXmlFields.size() > 0) {
				StringBuffer alterStrBuf = new StringBuffer("(");
				for (String fieldName : currXmlFields) {
					String xpathExpr = "/root/table/field[name='" + fieldName+ "']";
					List<Map<String, Object>> newXmlFieldMaps = ResolveFactory.Resolve(xmlStr.toLowerCase(), xpathExpr);
					if (newXmlFieldMaps.size() > 0) {
						String type = (String) newXmlFieldMaps.get(0).get("type");
						String comment = (String) newXmlFieldMaps.get(0).get("comment");
						String size = (String) newXmlFieldMaps.get(0).get("size");
						alterStrBuf.append(fieldName + " "
								+ Jdbc_JavaConverter.getJdbcMysqlTypeDescription(type, size) + " COMMENT '" + comment
								+ "'");
						alterStrBuf.append(",");
					}
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

	public static void main(String[] args) throws UnsupportedEncodingException, JaxenException, DocumentException {
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"
		// standalone=\"yes\"?><root><company><name>云南云天化红磷分公司</name><code>NO0001559</code></company><table><name>ga_yth_hl_weight</name><comment>过磅信息表</comment><field><name>ticketno</name><size>13</size><type>String</type><comment>交易磅单号</comment></field><field><name>truckno</name><size>15</size><type>String</type><comment>车牌号</comment></field></table>
		// <item><ticketno>1201711200067</ticketno><truckno>云G42056</truckno></item><item><ticketno>1201711200067</ticketno><truckno>云G42056</truckno></item></root>";
		// List<Map<String, String>> list = getXmlStrInfoList(xmlStr,
		// "/root/table/field");
		// String xmlStr = "<?xml version='1.0' encoding='utf-8'
		// standalone='yes'?> <root> <company> <name>国控云南</name>
		// <code>GKYN10001</code> </company>
		// <table><name>GA_GKYN_XS</name><comment>销售数据</comment><field>
		// <name>CREATEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFMC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>BILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTNAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCITY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTADDRESS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>GOODS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SPEC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>LOTNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ENDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRODUCER</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>QTY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>MSUNITNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSLX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SENDADDR</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>YWY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>KPY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>WAREBRAND</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RECHECKDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NDBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XTSJRQ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELEASEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>DJGZBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ZFBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELATEBILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field></table><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019016</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11048135320</GOODS> <NAME>酚咖片</NAME>
		// <SPEC>10片/板*2板*复方(500mg:65mg)</SPEC> <LOTNO>17050414</LOTNO>
		// <PRDDATE>20170428</PRDDATE> <ENDDATE>20190427</ENDDATE>
		// <PRODUCER>中美天津史克制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>400</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>11.5</PRC> <JE>4600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>中美史克</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item></root>";//
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019015</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>12361931720</GOODS> <NAME>参松养心胶囊</NAME> <SPEC>0.4g*36粒</SPEC>
		// <LOTNO>1709022</LOTNO> <PRDDATE>20170927</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>北京以岭药业有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>24.37</PRC> <JE>97480</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019014</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1240193021a</GOODS> <NAME>通心络胶囊</NAME> <SPEC>0.26gx30粒</SPEC>
		// <LOTNO>A1709010</LOTNO> <PRDDATE>20170909</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>3200</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>25.5</PRC> <JE>81600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019013</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1234190369a</GOODS> <NAME>连花清瘟胶囊</NAME> <SPEC>0.35g*24粒</SPEC>
		// <LOTNO>A1708054</LOTNO> <PRDDATE>20170821</PRDDATE>
		// <ENDDATE>20200131</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>10.76</PRC> <JE>43040</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 11:17:10</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-12 14:38:42</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18023681</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002095a</GOODS> <NAME>维生素D滴剂（胶囊型）</NAME> <SPEC>每粒含D3
		// 400单位*20粒</SPEC> <LOTNO>12974025</LOTNO> <PRDDATE>20171120</PRDDATE>
		// <ENDDATE>20191031</ENDDATE>
		// <PRODUCER>国药控股星鲨制药（厦门）有限公司（原厦门星鲨制药有限公司）</PRODUCER> <JX>胶囊剂，硬胶囊</JX>
		// <QTY>600</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>22</PRC> <JE>13200</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE></RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE></RELEASEDATE> <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ>
		// <RELATEBILLNO></RELATEBILLNO> </item><item> <CREATEDATE>2018-01-09
		// 08:17:20</CREATEDATE> <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016148</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002241a</GOODS> <NAME>吡格列酮二甲双胍片(15mg/500mg)</NAME>
		// <SPEC>30片*复方制剂(盐酸吡格列酮15mg;盐酸二甲双胍500mg)</SPEC> <LOTNO>170841</LOTNO>";
		// String xmlStr = "<?xml version='1.0' encoding='utf-8'
		// standalone='yes'?><root><company><name>国控云南</name><code>GKYN10001</code></company><table><name>GA_GKYN_XS</name><comment>销售数据</comment><field>
		// <name>CREATEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFMC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>BILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTNAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCITY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTADDRESS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>GOODS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SPEC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>LOTNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ENDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRODUCER</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>QTY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>MSUNITNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSLX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SENDADDR</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>YWY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>KPY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>WAREBRAND</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RECHECKDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NDBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XTSJRQ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELEASEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>DJGZBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ZFBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELATEBILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field></table><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019016</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11048135320</GOODS> <NAME>酚咖片</NAME>
		// <SPEC>10片/板*2板*复方(500mg:65mg)</SPEC> <LOTNO>17050414</LOTNO>
		// <PRDDATE>20170428</PRDDATE> <ENDDATE>20190427</ENDDATE>
		// <PRODUCER>中美天津史克制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>400</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>11.5</PRC> <JE>4600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>中美史克</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019015</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>12361931720</GOODS> <NAME>参松养心胶囊</NAME> <SPEC>0.4g*36粒</SPEC>
		// <LOTNO>1709022</LOTNO> <PRDDATE>20170927</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>北京以岭药业有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>24.37</PRC> <JE>97480</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019014</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1240193021a</GOODS> <NAME>通心络胶囊</NAME> <SPEC>0.26gx30粒</SPEC>
		// <LOTNO>A1709010</LOTNO> <PRDDATE>20170909</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>3200</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>25.5</PRC> <JE>81600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019013</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1234190369a</GOODS> <NAME>连花清瘟胶囊</NAME> <SPEC>0.35g*24粒</SPEC>
		// <LOTNO>A1708054</LOTNO> <PRDDATE>20170821</PRDDATE>
		// <ENDDATE>20200131</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>10.76</PRC> <JE>43040</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 11:17:10</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-12 14:38:42</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18023681</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002095a</GOODS> <NAME>维生素D滴剂（胶囊型）</NAME> <SPEC>每粒含D3
		// 400单位*20粒</SPEC> <LOTNO>12974025</LOTNO> <PRDDATE>20171120</PRDDATE>
		// <ENDDATE>20191031</ENDDATE>
		// <PRODUCER>国药控股星鲨制药（厦门）有限公司（原厦门星鲨制药有限公司）</PRODUCER> <JX>胶囊剂，硬胶囊</JX>
		// <QTY>600</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>22</PRC> <JE>13200</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE></RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-12</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-09 08:17:20</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18016148</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002241a</GOODS> <NAME>吡格列酮二甲双胍片(15mg/500mg)</NAME>
		// <SPEC>30片*复方制剂(盐酸吡格列酮15mg;盐酸二甲双胍500mg)</SPEC> <LOTNO>170841</LOTNO>
		// <PRDDATE>20170811</PRDDATE> <ENDDATE>20190731</ENDDATE>
		// <PRODUCER>杭州中美华东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>100</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>106.5</PRC> <JE>10650</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016147</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002241a</GOODS> <NAME>吡格列酮二甲双胍片(15mg/500mg)</NAME>
		// <SPEC>30片*复方制剂(盐酸吡格列酮15mg;盐酸二甲双胍500mg)</SPEC> <LOTNO>170450</LOTNO>
		// <PRDDATE>20170425</PRDDATE> <ENDDATE>20190331</ENDDATE>
		// <PRODUCER>杭州中美华东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>13</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>106.5</PRC> <JE>1384.5</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016146</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11130637440</GOODS> <NAME>乙酰半胱氨酸泡腾片</NAME>
		// <SPEC>0.6gx6片</SPEC> <LOTNO>PL1708007</LOTNO>
		// <PRDDATE>20170821</PRDDATE> <ENDDATE>20190820</ENDDATE>
		// <PRODUCER>浙江金华康恩贝生物制药有限公司</PRODUCER> <JX>泡腾片</JX> <QTY>20</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>32.28</PRC> <JE>645.6</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016145</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11120123850</GOODS> <NAME>盐酸普拉克索片(森福罗)</NAME>
		// <SPEC>0.25mgx30片</SPEC> <LOTNO>702917A</LOTNO>
		// <PRDDATE>20170328</PRDDATE> <ENDDATE>20200229</ENDDATE>
		// <PRODUCER>BoehringerIngelheimInternationalGmbH</PRODUCER>
		// <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX> <QTY>30</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>190</PRC> <JE>5700</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>勃林格</WAREBRAND> <RECHECKDATE>2018-01-09
		// 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-09</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:57:16</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006654</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01004028S</GOODS> <NAME>拉莫三嗪片(利必通)</NAME>
		// <SPEC>50mgx30片/盒</SPEC> <LOTNO>AD2P</LOTNO>
		// <PRDDATE>20170501</PRDDATE> <ENDDATE>20200430</ENDDATE>
		// <PRODUCER>GlaxoSmithklinePharmaceuticalsSA</PRODUCER>
		// <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX> <QTY>10</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>93</PRC> <JE>930</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>葛兰素史克</WAREBRAND> <RECHECKDATE>2018-01-04
		// 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-04</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:57:16</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006653</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01000338a</GOODS> <NAME>替格瑞洛片(倍林达)</NAME>
		// <SPEC>90mgx14片</SPEC> <LOTNO>1705151</LOTNO>
		// <PRDDATE>20170301</PRDDATE> <ENDDATE>20190228</ENDDATE>
		// <PRODUCER>阿斯利康制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>10</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>163</PRC> <JE>1630</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>阿斯利康</WAREBRAND> <RECHECKDATE>2018-01-04
		// 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-04</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:50:46</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006601</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01001357a</GOODS> <NAME>奥氮平片</NAME>
		// <SPEC>5mg*14片/板*1板</SPEC> <LOTNO>170909</LOTNO>
		// <PRDDATE>20170925</PRDDATE> <ENDDATE>20190831</ENDDATE>
		// <PRODUCER>江苏豪森药业集团有限公司(原江苏豪森药业股份有限公司)</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>30</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>122</PRC> <JE>3660</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>江苏豪森</WAREBRAND>
		// <RECHECKDATE>2018-01-04 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-04</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 17:11:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18015633</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002144b</GOODS> <NAME>磷酸西格列汀片(捷诺维)</NAME>
		// <SPEC>100mg(以西格列汀计)*7片/板*1板</SPEC> <LOTNO>N028454</LOTNO>
		// <PRDDATE>20170905</PRDDATE> <ENDDATE>20200904</ENDDATE>
		// <PRODUCER>杭州默沙东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>20</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>57.67</PRC> <JE>1153.4</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY>
		// <WAREBRAND>MerckSharp&DohmeItaliaSPA(意大利)</WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 17:11:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18015632</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01001943a</GOODS> <NAME>西格列汀二甲双胍片(II)(捷诺达)</NAME>
		// <SPEC>14片/板*2板(磷酸西格列汀50mg和盐酸二甲双胍850mg)</SPEC> <LOTNO>N009300</LOTNO>
		// <PRDDATE>20161228</PRDDATE> <ENDDATE>20181227</ENDDATE>
		// <PRODUCER>MerckSharp&DohmeBV</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>24</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>137.39</PRC>
		// <JE>3297.36</JE> <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼
		// </SENDADDR> <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002227</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1115015063b</GOODS> <NAME>培哚普利叔丁胺片(雅施达)</NAME>
		// <SPEC>8mgx15片</SPEC> <LOTNO>2011807</LOTNO>
		// <PRDDATE>20170626</PRDDATE> <ENDDATE>20190625</ENDDATE>
		// <PRODUCER>施维雅（天津）制药有限公司</PRODUCER> <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX>
		// <QTY>120</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>80</PRC> <JE>9600</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>施维雅/0</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002226</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01003164a</GOODS> <NAME>七叶洋地黄双苷滴眼液(施图伦)</NAME>
		// <SPEC>0.4ml</SPEC> <LOTNO>170558</LOTNO> <PRDDATE>20170524</PRDDATE>
		// <ENDDATE>20200430</ENDDATE> <PRODUCER>德国视都灵药品有限责任公司</PRODUCER>
		// <JX>滴眼剂，洗眼剂，粉剂眼药</JX> <QTY>1500</QTY> <MSUNITNO>支</MSUNITNO>
		// <PRC>3.6</PRC> <JE>5400</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>深圳康哲</WAREBRAND> <RECHECKDATE>2018-01-02
		// 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-02 14:43:52</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18002225</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11171329250</GOODS> <NAME>贝前列素钠片</NAME> <SPEC>40ug*10片</SPEC>
		// <LOTNO>B037K</LOTNO> <PRDDATE>20170629</PRDDATE>
		// <ENDDATE>20200531</ENDDATE> <PRODUCER>北京泰德制药股份有限公司</PRODUCER>
		// <JX>薄膜衣片，包衣片</JX> <QTY>200</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>73.84</PRC> <JE>14768</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>北京泰德</WAREBRAND> <RECHECKDATE>2018-01-02
		// 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-02 14:43:52</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18002224</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21141906340</GOODS> <NAME>熊去氧胆酸胶囊(优思弗)</NAME>
		// <SPEC>250mg×25粒</SPEC> <LOTNO>17H09683L</LOTNO>
		// <PRDDATE>20170809</PRDDATE> <ENDDATE>20220731</ENDDATE>
		// <PRODUCER>Dr.FalkPharmaGmbH德国</PRODUCER> <JX>胶囊剂，硬胶囊</JX>
		// <QTY>100</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>220</PRC> <JE>22000</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>深圳康哲</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002223</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21150107410</GOODS> <NAME>银杏叶提取物片(金纳多)</NAME>
		// <SPEC>40mg×20片</SPEC> <LOTNO>0681016</LOTNO>
		// <PRDDATE>20160601</PRDDATE> <ENDDATE>20210531</ENDDATE>
		// <PRODUCER>德国威玛舒培博士药厂</PRODUCER> <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX>
		// <QTY>200</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>37.6</PRC> <JE>7520</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>北京科园信海医药经营有限公司</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002222</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21158106740</GOODS> <NAME>盐酸乐卡地平片(再宁平)</NAME>
		// <SPEC>10mg×7片</SPEC> <LOTNO>ZZ7C07</LOTNO>
		// <PRDDATE>20170201</PRDDATE> <ENDDATE>20200131</ENDDATE>
		// <PRODUCER>RecordatiS.P.A.(意大利)</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>300</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>31.96</PRC> <JE>9588</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 11:26:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18001526</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002912a</GOODS> <NAME>厄贝沙坦片</NAME>
		// <SPEC>75mg*7片/板*4板</SPEC> <LOTNO>001B17011</LOTNO>
		// <PRDDATE>20170821</PRDDATE> <ENDDATE>20200731</ENDDATE>
		// <PRODUCER>浙江华海药业股份有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>200</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>18</PRC> <JE>3600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND></WAREBRAND> <RECHECKDATE>2018-01-02
		// 14:49:15</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-08 15:43:58</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18014998</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>31150506120</GOODS> <NAME>硝苯地平控释片(拜新同R)</NAME>
		// <SPEC>30mg×7片</SPEC> <LOTNO>BJ36673</LOTNO>
		// <PRDDATE>20170405</PRDDATE> <ENDDATE>20200404</ENDDATE>
		// <PRODUCER>拜耳医药保健有限公司</PRODUCER> <JX>调释片，缓释片，控释片，长效片 多层片</JX>
		// <QTY>540</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>31.129</PRC>
		// <JE>16809.66</JE> <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼
		// </SENDADDR> <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>拜耳</WAREBRAND>
		// <RECHECKDATE>2018-01-08 16:33:58</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-08</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 15:43:58</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18014997</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>31040400240</GOODS> <NAME>阿司匹林肠溶片</NAME>
		// <SPEC>100mg*30片</SPEC> <LOTNO>BJ37483</LOTNO>
		// <PRDDATE>20170920</PRDDATE> <ENDDATE>20200919</ENDDATE>
		// <PRODUCER>拜耳医药保健有限公司</PRODUCER> <JX>肠溶片(肠衣片)</JX> <QTY>1350</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>15.5</PRC> <JE>20925</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>拜耳</WAREBRAND> <RECHECKDATE>2018-01-08
		// 16:33:58</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-08</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item></root>";
		// String xmlStr = "<?xml version='1.0' encoding='utf-8'
		// standalone='yes'?><root><company><name>国控云南</name><code>GKYN10001</code></company><table><name>GA_GKYN_XS</name><comment>销售数据</comment><field>
		// <name>CREATEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSFMC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>BILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCODE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTNAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTCITY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>CSTADDRESS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>GOODS</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NAME</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SPEC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>LOTNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ENDDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRODUCER</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>QTY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>MSUNITNO</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>PRC</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>JE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XSLX</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>SENDADDR</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>YWY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>KPY</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>WAREBRAND</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RECHECKDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>NDBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>XTSJRQ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELEASEDATE</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>DJGZBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>ZFBZ</name>
		// <size>150</size><type>String</type><comment></comment></field><field>
		// <name>RELATEBILLNO</name>
		// <size>150</size><type>String</type><comment></comment></field></table><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019016</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11048135320</GOODS> <NAME>酚咖片</NAME>
		// <SPEC>10片/板*2板*复方(500mg:65mg)</SPEC> <LOTNO>17050414</LOTNO>
		// <PRDDATE>20170428</PRDDATE> <ENDDATE>20190427</ENDDATE>
		// <PRODUCER>中美天津史克制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>400</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>11.5</PRC> <JE>4600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>中美史克</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019015</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>12361931720</GOODS> <NAME>参松养心胶囊</NAME> <SPEC>0.4g*36粒</SPEC>
		// <LOTNO>1709022</LOTNO> <PRDDATE>20170927</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>北京以岭药业有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>24.37</PRC> <JE>97480</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019014</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1240193021a</GOODS> <NAME>通心络胶囊</NAME> <SPEC>0.26gx30粒</SPEC>
		// <LOTNO>A1709010</LOTNO> <PRDDATE>20170909</PRDDATE>
		// <ENDDATE>20200831</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>3200</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>25.5</PRC> <JE>81600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 12:11:56</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-10 10:31:45</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18019013</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1234190369a</GOODS> <NAME>连花清瘟胶囊</NAME> <SPEC>0.35g*24粒</SPEC>
		// <LOTNO>A1708054</LOTNO> <PRDDATE>20170821</PRDDATE>
		// <ENDDATE>20200131</ENDDATE> <PRODUCER>石家庄以岭药业股份有限公司</PRODUCER>
		// <JX>胶囊剂，硬胶囊</JX> <QTY>4000</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>10.76</PRC> <JE>43040</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>以岭药业</WAREBRAND> <RECHECKDATE>2018-01-10
		// 11:17:10</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-10</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-12 14:38:42</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18023681</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002095a</GOODS> <NAME>维生素D滴剂（胶囊型）</NAME> <SPEC>每粒含D3
		// 400单位*20粒</SPEC> <LOTNO>12974025</LOTNO> <PRDDATE>20171120</PRDDATE>
		// <ENDDATE>20191031</ENDDATE>
		// <PRODUCER>国药控股星鲨制药（厦门）有限公司（原厦门星鲨制药有限公司）</PRODUCER> <JX>胶囊剂，硬胶囊</JX>
		// <QTY>600</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>22</PRC> <JE>13200</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE></RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-12</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-09 08:17:20</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18016148</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002241a</GOODS> <NAME>吡格列酮二甲双胍片(15mg/500mg)</NAME>
		// <SPEC>30片*复方制剂(盐酸吡格列酮15mg;盐酸二甲双胍500mg)</SPEC> <LOTNO>170841</LOTNO>
		// <PRDDATE>20170811</PRDDATE> <ENDDATE>20190731</ENDDATE>
		// <PRODUCER>杭州中美华东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>100</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>106.5</PRC> <JE>10650</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016147</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002241a</GOODS> <NAME>吡格列酮二甲双胍片(15mg/500mg)</NAME>
		// <SPEC>30片*复方制剂(盐酸吡格列酮15mg;盐酸二甲双胍500mg)</SPEC> <LOTNO>170450</LOTNO>
		// <PRDDATE>20170425</PRDDATE> <ENDDATE>20190331</ENDDATE>
		// <PRODUCER>杭州中美华东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>13</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>106.5</PRC> <JE>1384.5</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016146</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11130637440</GOODS> <NAME>乙酰半胱氨酸泡腾片</NAME>
		// <SPEC>0.6gx6片</SPEC> <LOTNO>PL1708007</LOTNO>
		// <PRDDATE>20170821</PRDDATE> <ENDDATE>20190820</ENDDATE>
		// <PRODUCER>浙江金华康恩贝生物制药有限公司</PRODUCER> <JX>泡腾片</JX> <QTY>20</QTY>
		// <MSUNITNO>瓶</MSUNITNO> <PRC>32.28</PRC> <JE>645.6</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-09 08:17:20</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18016145</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11120123850</GOODS> <NAME>盐酸普拉克索片(森福罗)</NAME>
		// <SPEC>0.25mgx30片</SPEC> <LOTNO>702917A</LOTNO>
		// <PRDDATE>20170328</PRDDATE> <ENDDATE>20200229</ENDDATE>
		// <PRODUCER>BoehringerIngelheimInternationalGmbH</PRODUCER>
		// <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX> <QTY>30</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>190</PRC> <JE>5700</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>勃林格</WAREBRAND> <RECHECKDATE>2018-01-09
		// 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-09</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:57:16</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006654</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01004028S</GOODS> <NAME>拉莫三嗪片(利必通)</NAME>
		// <SPEC>50mgx30片/盒</SPEC> <LOTNO>AD2P</LOTNO>
		// <PRDDATE>20170501</PRDDATE> <ENDDATE>20200430</ENDDATE>
		// <PRODUCER>GlaxoSmithklinePharmaceuticalsSA</PRODUCER>
		// <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX> <QTY>10</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>93</PRC> <JE>930</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>葛兰素史克</WAREBRAND> <RECHECKDATE>2018-01-04
		// 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-04</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:57:16</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006653</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01000338a</GOODS> <NAME>替格瑞洛片(倍林达)</NAME>
		// <SPEC>90mgx14片</SPEC> <LOTNO>1705151</LOTNO>
		// <PRDDATE>20170301</PRDDATE> <ENDDATE>20190228</ENDDATE>
		// <PRODUCER>阿斯利康制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>10</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>163</PRC> <JE>1630</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>阿斯利康</WAREBRAND> <RECHECKDATE>2018-01-04
		// 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-04</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-04 09:50:46</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18006601</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01001357a</GOODS> <NAME>奥氮平片</NAME>
		// <SPEC>5mg*14片/板*1板</SPEC> <LOTNO>170909</LOTNO>
		// <PRDDATE>20170925</PRDDATE> <ENDDATE>20190831</ENDDATE>
		// <PRODUCER>江苏豪森药业集团有限公司(原江苏豪森药业股份有限公司)</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>30</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>122</PRC> <JE>3660</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>江苏豪森</WAREBRAND>
		// <RECHECKDATE>2018-01-04 10:26:03</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-04</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 17:11:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18015633</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002144b</GOODS> <NAME>磷酸西格列汀片(捷诺维)</NAME>
		// <SPEC>100mg(以西格列汀计)*7片/板*1板</SPEC> <LOTNO>N028454</LOTNO>
		// <PRDDATE>20170905</PRDDATE> <ENDDATE>20200904</ENDDATE>
		// <PRODUCER>杭州默沙东制药有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>20</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>57.67</PRC> <JE>1153.4</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY>
		// <WAREBRAND>MerckSharp&amp;DohmeItaliaSPA(意大利)</WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 17:11:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18015632</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01001943a</GOODS> <NAME>西格列汀二甲双胍片(II)(捷诺达)</NAME>
		// <SPEC>14片/板*2板(磷酸西格列汀50mg和盐酸二甲双胍850mg)</SPEC> <LOTNO>N009300</LOTNO>
		// <PRDDATE>20161228</PRDDATE> <ENDDATE>20181227</ENDDATE>
		// <PRODUCER>MerckSharp&amp;DohmeBV</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>24</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>137.39</PRC>
		// <JE>3297.36</JE> <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼
		// </SENDADDR> <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-09 09:07:19</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-09</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002227</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>1115015063b</GOODS> <NAME>培哚普利叔丁胺片(雅施达)</NAME>
		// <SPEC>8mgx15片</SPEC> <LOTNO>2011807</LOTNO>
		// <PRDDATE>20170626</PRDDATE> <ENDDATE>20190625</ENDDATE>
		// <PRODUCER>施维雅（天津）制药有限公司</PRODUCER> <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX>
		// <QTY>120</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>80</PRC> <JE>9600</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>施维雅/0</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002226</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01003164a</GOODS> <NAME>七叶洋地黄双苷滴眼液(施图伦)</NAME>
		// <SPEC>0.4ml</SPEC> <LOTNO>170558</LOTNO> <PRDDATE>20170524</PRDDATE>
		// <ENDDATE>20200430</ENDDATE> <PRODUCER>德国视都灵药品有限责任公司</PRODUCER>
		// <JX>滴眼剂，洗眼剂，粉剂眼药</JX> <QTY>1500</QTY> <MSUNITNO>支</MSUNITNO>
		// <PRC>3.6</PRC> <JE>5400</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>深圳康哲</WAREBRAND> <RECHECKDATE>2018-01-02
		// 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-02 14:43:52</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18002225</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>11171329250</GOODS> <NAME>贝前列素钠片</NAME> <SPEC>40ug*10片</SPEC>
		// <LOTNO>B037K</LOTNO> <PRDDATE>20170629</PRDDATE>
		// <ENDDATE>20200531</ENDDATE> <PRODUCER>北京泰德制药股份有限公司</PRODUCER>
		// <JX>薄膜衣片，包衣片</JX> <QTY>200</QTY> <MSUNITNO>盒</MSUNITNO>
		// <PRC>73.84</PRC> <JE>14768</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>北京泰德</WAREBRAND> <RECHECKDATE>2018-01-02
		// 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-02 14:43:52</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18002224</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21141906340</GOODS> <NAME>熊去氧胆酸胶囊(优思弗)</NAME>
		// <SPEC>250mg×25粒</SPEC> <LOTNO>17H09683L</LOTNO>
		// <PRDDATE>20170809</PRDDATE> <ENDDATE>20220731</ENDDATE>
		// <PRODUCER>Dr.FalkPharmaGmbH德国</PRODUCER> <JX>胶囊剂，硬胶囊</JX>
		// <QTY>100</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>220</PRC> <JE>22000</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>深圳康哲</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002223</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21150107410</GOODS> <NAME>银杏叶提取物片(金纳多)</NAME>
		// <SPEC>40mg×20片</SPEC> <LOTNO>0681016</LOTNO>
		// <PRDDATE>20160601</PRDDATE> <ENDDATE>20210531</ENDDATE>
		// <PRODUCER>德国威玛舒培博士药厂</PRODUCER> <JX>片剂(非包衣片、素片、压制片)，浸膏片</JX>
		// <QTY>200</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>37.6</PRC> <JE>7520</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>北京科园信海医药经营有限公司</WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 14:43:52</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18002222</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>21158106740</GOODS> <NAME>盐酸乐卡地平片(再宁平)</NAME>
		// <SPEC>10mg×7片</SPEC> <LOTNO>ZZ7C07</LOTNO>
		// <PRDDATE>20170201</PRDDATE> <ENDDATE>20200131</ENDDATE>
		// <PRODUCER>RecordatiS.P.A.(意大利)</PRODUCER> <JX>薄膜衣片，包衣片</JX>
		// <QTY>300</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>31.96</PRC> <JE>9588</JE>
		// <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR>
		// <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND></WAREBRAND>
		// <RECHECKDATE>2018-01-02 15:42:25</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-02</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-02 11:26:25</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18001526</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>KM01002912a</GOODS> <NAME>厄贝沙坦片</NAME>
		// <SPEC>75mg*7片/板*4板</SPEC> <LOTNO>001B17011</LOTNO>
		// <PRDDATE>20170821</PRDDATE> <ENDDATE>20200731</ENDDATE>
		// <PRODUCER>浙江华海药业股份有限公司</PRODUCER> <JX>薄膜衣片，包衣片</JX> <QTY>200</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>18</PRC> <JE>3600</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND></WAREBRAND> <RECHECKDATE>2018-01-02
		// 14:49:15</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-02</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item><item>
		// <CREATEDATE>2018-01-08 15:43:58</CREATEDATE> <XSFCODE>1</XSFCODE>
		// <XSFMC>国药控股云南有限公司</XSFMC> <BILLNO>18014998</BILLNO>
		// <CSTCODE>KM200303a</CSTCODE> <CSTNAME>昆明百姓缘药业有限公司</CSTNAME>
		// <CSTCITY></CSTCITY> <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>31150506120</GOODS> <NAME>硝苯地平控释片(拜新同R)</NAME>
		// <SPEC>30mg×7片</SPEC> <LOTNO>BJ36673</LOTNO>
		// <PRDDATE>20170405</PRDDATE> <ENDDATE>20200404</ENDDATE>
		// <PRODUCER>拜耳医药保健有限公司</PRODUCER> <JX>调释片，缓释片，控释片，长效片 多层片</JX>
		// <QTY>540</QTY> <MSUNITNO>盒</MSUNITNO> <PRC>31.129</PRC>
		// <JE>16809.66</JE> <XSLX>销售</XSLX> <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼
		// </SENDADDR> <YWY></YWY> <KPY>李志明</KPY> <WAREBRAND>拜耳</WAREBRAND>
		// <RECHECKDATE>2018-01-08 16:33:58</RECHECKDATE> <NDBZ>NO</NDBZ>
		// <XTSJRQ></XTSJRQ> <RELEASEDATE>2018-01-08</RELEASEDATE>
		// <DJGZBZ>NO</DJGZBZ> <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO>
		// </item><item> <CREATEDATE>2018-01-08 15:43:58</CREATEDATE>
		// <XSFCODE>1</XSFCODE> <XSFMC>国药控股云南有限公司</XSFMC>
		// <BILLNO>18014997</BILLNO> <CSTCODE>KM200303a</CSTCODE>
		// <CSTNAME>昆明百姓缘药业有限公司</CSTNAME> <CSTCITY></CSTCITY>
		// <CSTADDRESS>昆明经开区中豪新册产业城A区2幢4楼、5楼</CSTADDRESS>
		// <GOODS>31040400240</GOODS> <NAME>阿司匹林肠溶片</NAME>
		// <SPEC>100mg*30片</SPEC> <LOTNO>BJ37483</LOTNO>
		// <PRDDATE>20170920</PRDDATE> <ENDDATE>20200919</ENDDATE>
		// <PRODUCER>拜耳医药保健有限公司</PRODUCER> <JX>肠溶片(肠衣片)</JX> <QTY>1350</QTY>
		// <MSUNITNO>盒</MSUNITNO> <PRC>15.5</PRC> <JE>20925</JE> <XSLX>销售</XSLX>
		// <SENDADDR>昆明经开区中豪新册产业城A区2幢4楼、5楼 </SENDADDR> <YWY></YWY>
		// <KPY>李志明</KPY> <WAREBRAND>拜耳</WAREBRAND> <RECHECKDATE>2018-01-08
		// 16:33:58</RECHECKDATE> <NDBZ>NO</NDBZ> <XTSJRQ></XTSJRQ>
		// <RELEASEDATE>2018-01-08</RELEASEDATE> <DJGZBZ>NO</DJGZBZ>
		// <ZFBZ>正常</ZFBZ> <RELATEBILLNO></RELATEBILLNO> </item></root>";
		// String text = getSingleNodeInfo(xmlStr , "/root/table/name");
		// System.out.println("----"+text);
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println("--" + list.get(i));
		// }
		// System.out.println(genCreateTableString(xmlStr));
	}
}
