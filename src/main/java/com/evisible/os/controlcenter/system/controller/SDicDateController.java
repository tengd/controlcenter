package com.evisible.os.controlcenter.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SDicDate;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISDicDateService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;


@Controller
@RequestMapping("/dicdate") 
public class SDicDateController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ISDicDateService sDicDateService;
	@RequestMapping(value="/getDicDates")  
	@ResponseBody
	public Object getSDicDates(@RequestParam("typecode") String typecode){
		/*验证参数*/
		if(typecode.isEmpty()){
			Message message=new Message();
			message.setmsgCode(MsgConstant.PARERRORCODE);
			message.setmsgContent(MsgConstant.PARERRORCONTENT);
			return message;
		}
		
		return sDicDateService.getSDicDatesByTypecode(typecode);
	}
	
	@RequestMapping(value = "/getSDicDatesByTypeCodeOrName")
	@ResponseBody
	public Map<String,Object> getSDicDatesByTypeCodeOrName(String queryValue,@RequestParam("page") int page,
			@RequestParam("rows") int rows) {
		PageUI pageUI = new PageUI(page, rows);
		return sDicDateService.getSDicDatesByTypeCodeOrName(queryValue, pageUI);
	}

	/**
	 * @see删除字典信息
	 * */
	@RequestMapping(value = "/delDicDate", method = RequestMethod.POST)
	@ResponseBody
	public Object delDicDate(HttpServletRequest request,
			@RequestParam("uuid") String uuid) {
		Message message=new Message();
		try {
			//日志
			try {
				this.Log(request, uuid, "uuid:"+uuid+"对应字典值被删除");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			if(sDicDateService.delDicDate(uuid)){
				message.setmsgCode(MsgConstant.SUCCESSCODE);
				message.setmsgContent(MsgConstant.SUCCESSCONTENT);
			}else{
				message.setmsgCode(MsgConstant.ERRORCODE);
				message.setmsgContent(MsgConstant.ERRORCONTENT);
			}
		} catch (Exception e) {
			log.info("------------------删除用户信息异常----------------------");
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * @throws Exception 
	 * @throws SecurityException 
	 * @see插入与更新字典信息
	 * */
	@RequestMapping(value = "/doDicDate", method = RequestMethod.POST)
	public void doDicDate(HttpServletRequest request)  {
		// 封装实体
		SDicDate record = new SDicDate();
		String uuid=request.getParameter("uuid").toString().trim();
		record.setTypecode(request.getParameter("typecode").toString().trim());
		record.setdName(request.getParameter("dName").toString().trim());
		record.setdValue(request.getParameter("dValue").toString().trim());
		// 当前用户
		User user = (User) request.getSession().getAttribute("currentUser");
		if(user==null){
			log.info("------------------用户Session为空了----------------------");
			try {
				throw new NullPointerException("Session ERROR NULL: " + Class.class+ ":" + Class.class.getMethods());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (uuid.isEmpty()) {
			String iuuid=StringConvert.getUUIDString();
			//日志
			try {
				this.Log(request, iuuid, "uuid:"+iuuid+"插入了字典值");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			/* 插入操作 */
			record.setUuid(iuuid);
			record.setCreator(user.getuName());
			try {
				sDicDateService.insertDicDate(record);
			} catch (Exception e) {
				log.info("------------------插入字典信息异常----------------------");
				e.printStackTrace();
			}
		} else {
			/* 更新操作 */
			try {
				//日志
				try {
					this.Log(request, uuid, "uuid:"+uuid+"字典值被更新");
				} catch (Exception e) {
					log.debug("------日志处理异常---------"+e.getMessage());
				}
				record.setUuid(uuid);
				record.setUpdatedate(new Date());
				record.setUpdator(user.getuName());
				sDicDateService.updateDicDate(record);
			} catch (Exception e) {
				log.info("------------------更新字典信息异常----------------------");
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/getDicDatesStr")  
	@ResponseBody
	public String getSDicDatesStr(@RequestParam("typecode") String typecode){
		/*验证参数*/
		if(typecode.isEmpty()){
			return "";
		}
		List<SDicDate> dicList = sDicDateService.getSDicDatesByTypecode(typecode);
		if(dicList.size()>0){
			StringBuffer sb = new StringBuffer();
			for(SDicDate dic : dicList){
				sb.append(dic.getdValue()+",");
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}
		return "";
	}
}
