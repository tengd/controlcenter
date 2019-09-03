package com.evisible.os.business.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.business.service.IDataBaseTablesService;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;

/**
 * 
 * <p>库表Controller类</p>
 * @author JiangWanDong
 * @Date   2018年1月11日
 */
@Controller("dataBaseTablesController")
@RequestMapping("/dataBaseTables")
public class DataBaseTablesController extends BaseController {
	@Autowired
	IDataBaseTablesService dataBaseTablesService;


	
	@RequestMapping("/getDataBaseTablesInfo")
	@ResponseBody
	public Map<String , Object> getDataBaseTablesInfo(@RequestParam("page") int page,
			@RequestParam("rows") int rows){
		PageUI pageUI = new PageUI(page, rows);
		Map<String , Object> tables =  dataBaseTablesService.getDataBaseTables(pageUI);
		return tables;
	}
	
	@RequestMapping("/getSingleTableInfo")
	@ResponseBody
	public Map<String , Object> getSingleTableInfo(@RequestParam("tableName") String tableName){
		return dataBaseTablesService.getSingleTableInfo(tableName);
	}
	
	@RequestMapping("/deleteTable")
	@ResponseBody
	public String deleteTable(@RequestParam("tableName") String tableName){
		try{
			//System.out.println(tableName);
			dataBaseTablesService.deleteTable(tableName);
			return "删除成功";
		}catch(Exception e){
			return "删除失败";
		}
		
		
	}
	
	@RequestMapping("/updateComment")
	@ResponseBody
	public String updateTableField(HttpServletRequest request){//, @RequestParam("type") String type , @RequestParam("field") String field , @RequestParam("comment") String comment){
		try{
			String tableName = request.getParameter("tableName");
			String type = request.getParameter("type");
			String field = request.getParameter("field");
			String comment = java.net.URLDecoder.decode(request.getParameter("comment"), "UTF-8");
			dataBaseTablesService.updateFieldComment(tableName, field, type, comment);
			return "修改成功";
		}catch(Exception e){
			e.printStackTrace();
			return "修改失败";
		}
		
	}
	
	@RequestMapping("/updateTableComment")
	@ResponseBody
	public String updateTableComment(HttpServletRequest request){
		try{		 
			String tableName = request.getParameter("tableName");
			String comment = java.net.URLDecoder.decode(request.getParameter("comment"), "UTF-8");
			dataBaseTablesService.updateTableComment(tableName, comment);
			return "修改成功";
		}catch(Exception e){
			e.printStackTrace();
			return "修改失败";
		}
		
	}
}
