package com.evisible.os.resolve.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.evisible.os.controlcenter.util.StringConvert;

/**
 * <p>Excel解析工具类</p>
 * @author JiangWanDong
 * @Date   2018年3月30日
 */
public class ExcelUtil {
	
	/**
	 * <p>描述:将Excel解析成Xml格式</p>
	 */
    public  String reslveExcelAsXml(String filePath , String unresolveField) throws FileNotFoundException {
    	File file = new File(filePath);
    	String fileSuffix = filePath.substring(filePath.lastIndexOf('.')+1);
    	ExcelTypeEnum fileType = null;
    	if(fileSuffix.toUpperCase().equals("XLS")){
    		fileType = ExcelTypeEnum.XLS;
    	}else if(fileSuffix.toUpperCase().equals("XLSX")){
    		fileType = ExcelTypeEnum.XLSX;
    	}else{
    		return "请检查文件格式 ， Excel解析仅支持.xls和.xlsx";
    	}
        InputStream inputStream = new FileInputStream(file);
        try {
            // 解析每行结果在listener中处理       	
            ExcelListener listener = null;
            if(!StringConvert.isEmpty(unresolveField)){
            	listener = new ExcelListener(unresolveField);
            }else{
            	listener = new ExcelListener();
            }
            ExcelReader excelReader = new ExcelReader(inputStream, fileType, null, listener);
            excelReader.read();
            return listener.getXmlStr();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

	public static void main(String[] args){
		try {
			System.out.println(new ExcelUtil().reslveExcelAsXml("E:\\jzny-payment-201803291519.xls" , "序号"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
