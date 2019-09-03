package com.evisible.os.controlcenter.system.controller;

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
import com.evisible.os.controlcenter.system.entity.UserWithBLOBs;
import com.evisible.os.controlcenter.system.service.IUserService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.MD5.MD5;
import com.evisible.os.controlcenter.util.constant.MsgConstant;

/**
 * <p>用户控制器,对应用户信息处理界面</p>
 * @author TengDong
 * @Date 20160406
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/getUserInfo")
	public String getUserInfo(HttpServletRequest request) {
		// 当前用户
		String currentUser = (String) request.getSession().getAttribute(
				"currentUser");
		System.out.println("当前登录的用户为[" + currentUser + "]");
		request.setAttribute("currUser", currentUser);
		return "/loginUrl/main";
	}

	@RequestMapping(value = "/getUsers")
	@ResponseBody
	public Map<String,Object> getUsers(HttpServletRequest request,
			String uname,@RequestParam("page") int page,
			@RequestParam("rows") int rows) {
		PageUI pageUI = new PageUI(page, rows);
		return userService.getUsers(uname,pageUI);
	}
	
	@RequestMapping(value = "/getUsersByNotLocked")
	@ResponseBody
	public Map<String,Object> getUsersByNotLocked(HttpServletRequest request,
			String uname,@RequestParam("page") int page,
			@RequestParam("rows") int rows) {
		PageUI pageUI = new PageUI(page, rows);
		return userService.getUsersByNotLocked(uname,pageUI);
	}

	/**
	 * @see插入与更新用户信息
	 * */
	@RequestMapping(value = "/doUser", method = RequestMethod.POST)
	public Object doUser(HttpServletRequest request) {
		Message message=new Message();
		// 封装实体
		UserWithBLOBs record = new UserWithBLOBs();
		String uuid = request.getParameter("uuid").toString().trim();
		String unmae = request.getParameter("uName").toString().trim();
		record.setAccount(request.getParameter("jobNum").toString().trim());   //工号
		record.setuName(unmae);
		String pas = request.getParameter("pas").toString().trim();
		MD5 md5Password = new MD5(pas);
		record.setPas(md5Password.mac32());
		MD5 md5Salt = new MD5(unmae + pas);
		//这个可以取shiro中的token
		record.setSalt(md5Salt.mac32());
		
		record.setPhone(request.getParameter("phone").toString().trim());
		record.setAddress(request.getParameter("address").toString().trim());
		record.setEmail(request.getParameter("email").toString().trim());
		record.setWecharNo(request.getParameter("wecharNo").toString().trim());
		record.setQqNo(request.getParameter("qqNo").toString().trim());
		if (uuid.isEmpty()) {
			/* 插入操作 */
		/*	record.setsDicDateId(request.getParameter("sDicDateId").toString()
					.trim());*/
			record.setUuid(StringConvert.getUUIDString());
			try {
				//日志
				try {
					this.Log(request, uuid,unmae+"被插入" );
				} catch (Exception e) {
					log.debug("------日志处理异常---------"+e.getMessage());
				}
				userService.insertUser(record);
				message.setmsgCode(MsgConstant.SUCCESSCODE);
				message.setmsgContent(MsgConstant.SUCCESSCONTENT);
				return message;
			} catch (Exception e) {
				message.setmsgCode(MsgConstant.ERRORCODE);
				message.setmsgContent(MsgConstant.ERRORCONTENT);
				return message;
			}
		} else {
			/* 更新操作 */
			try {
				record.setUuid(uuid);
				//日志
				try {
					this.Log(request, uuid,unmae+"被更新" );
				} catch (Exception e) {
					log.debug("------日志处理异常---------"+e.getMessage());
				}
				userService.updateUser(record);
				message.setmsgCode(MsgConstant.SUCCESSCODE);
				message.setmsgContent(MsgConstant.SUCCESSCONTENT);
				return message;
			} catch (Exception e) {
				message.setmsgCode(MsgConstant.ERRORCODE);
				message.setmsgContent(MsgConstant.ERRORCONTENT);
				return message;
			}
		}
	}

	/**
	 * @see删除用户信息
	 * */
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	public Object delUser(HttpServletRequest request,
			@RequestParam("uuid") String uuid) {
		Message message=new Message();
		try {
			//日志
			try {
				this.Log(request, uuid,"uuid:"+uuid+"用户被删除" );
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			if(userService.delUser(uuid)){
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
	 * @see更新用户锁
	 * */
	@RequestMapping(value = "/updateUserLocked", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserLocked(HttpServletRequest request,
			@RequestParam("uuid") String uuid,
			@RequestParam("locked") int locked){
		Message message=new Message();
		try {
			UserWithBLOBs record=new UserWithBLOBs();
			record.setUuid(uuid);
			record.setLocked(locked);
			//日志
			try {
				this.Log(request, uuid,"uuid:"+uuid+"用户锁更新" );
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			if(userService.updateUser(record)){
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

}
