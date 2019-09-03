package com.evisible.os.resolve.excel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.evisible.os.business.util.PinyinUtil;
import com.evisible.os.controlcenter.util.StringConvert;

/**
 * 
 * <p>
 * Excel解析监听类 ， 用于监听解析Excel的事件
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年3月30日
 */
public class ExcelListener extends AnalysisEventListener<Object> {

	private List<Object> datas = new ArrayList<Object>();
	private String xmlStr;
	Sheet sheet;
	// 指定不解析的字段
	private String unResolveField = "";

	public ExcelListener() {
	}

	public ExcelListener(String unResolveField) {
		super();
		this.unResolveField = unResolveField.trim();
	}

	public void invoke(Object object, AnalysisContext context) {
		// context.interrupt();
		datas.add(object);// 数据存储到list，供批量处理，或后续自己业务逻辑处理。
	}

	public void doAfterAllAnalysed(AnalysisContext context) {
		setXmlStr(genItemsXmlStr());
		datas.clear();// 解析结束销毁不用的资源
	}

	private String genItemsXmlStr() {
		StringBuilder builder = new StringBuilder();
		List<Object> datas = this.getDatas();
		List<Integer> removeIndexs = new ArrayList<>();
		String[] unresolveFields = unResolveField.split(",");
		if (datas == null) {
			return null;
		} else {
			@SuppressWarnings("unchecked")
			List<String> fieldsList = (List<String>) datas.get(0);
			if (!(unresolveFields==null || unresolveFields.length==0)) {
				for(int i = 0 ; i < unresolveFields.length ; i++){
					if(fieldsList.contains(unresolveFields[i])){
						removeIndexs.add(fieldsList.indexOf(unresolveFields[i]));
						//fieldsList.remove(unresolveFields[i]);
					}
				}
				
			}

			List<String> fieldList_Cap = new ArrayList<>();
			// 将带有中文的字段转换为首字母大写的缩写形式
			for (String str : fieldsList) {
				String capAbbrv = PinyinUtil.getCaptitalChars(str);
				if (fieldList_Cap.contains(capAbbrv)) {
					fieldList_Cap.add(capAbbrv + "_");
				} else {
					fieldList_Cap.add(capAbbrv);
				}
			}
			datas.remove(0);
			builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"   standalone=\"yes\" ?><root>");
				for (Object valueList : datas) {
					builder.append("<subInfo>");
					@SuppressWarnings("unchecked")
					List<String> values = (List<String>) valueList;
					int size = values.size();
					for (int i = 0; i < size; i++) {
						if (removeIndexs != null) {
							if (removeIndexs.contains(i)) {
								continue;
							}
						}
						builder.append(
								"<" + fieldList_Cap.get(i) + ">" + values.get(i) + "</" + fieldList_Cap.get(i) + ">");
					}
					builder.append("</subInfo>");
				}
			
			builder.append("</root>");
		}
		return builder.toString();
	}

	public List<Object> getDatas() {
		return datas;
	}

	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public String getXmlStr() {
		return xmlStr;
	}

	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}

}
