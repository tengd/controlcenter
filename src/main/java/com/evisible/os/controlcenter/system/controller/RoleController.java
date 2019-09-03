package com.evisible.os.controlcenter.system.controller;

import java.util.Date;
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
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISRoleService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;




/**
 * <p>角色控制器,对应角色信息处理界面</p>
 * @author TengDong
 * @Date 20160408
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private ISRoleService sroleService;
	
	/**
	 * @param rname 角色名称
	 * @param page 页号
	 * @param rows 页数
	 * @return 角色S
	 */
	@RequestMapping(value = "/getRoles")
	@ResponseBody
	public Map<String,Object> getRoles(String rname,@RequestParam("page") int page,
			@RequestParam("rows") int rows){
		PageUI pageUI=new PageUI(page,rows);
		return sroleService.getRoles(pageUI,rname);
	}
	
	/**
	 * 处理角色增加与修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doRole", method = RequestMethod.POST)
	public void doRole(HttpServletRequest request){
		String uuid=request.getParameter("uuid").toString().trim();
		String rName=request.getParameter("rName").toString().trim();
		String remark=request.getParameter("remark").toString().trim();
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
		SRole srole=new SRole();
		srole.setrName(rName);
		srole.setRemark(remark);
		if(uuid.isEmpty()){
			String iuuid=StringConvert.getUUIDString();
			//日志
			try {
				this.Log(request, iuuid, "添加角色");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			/*插入*/
			srole.setUuid(iuuid);
			srole.setCreator(user.getuName());
			srole.setCreatedate(new Date());
			try {
				sroleService.addRole(srole);
			} catch (Exception e) {
				log.info("-------------插入角色信息错误 ----------------");
				e.printStackTrace();
			}
		}else{
			//日志
			try {
				this.Log(request, uuid, "uuid:"+uuid+"角色被更新");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			/*更新*/
			srole.setUuid(uuid);
			srole.setUpdator(user.getuName());
			srole.setUpdatedate(new Date());
			try {
				 sroleService.editRole(srole);
			} catch (Exception e) {
				log.info("-------------更新角色信息错误 ----------------");
				e.printStackTrace();
			}
		}
		
	}
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	@ResponseBody
	public Object delRole(HttpServletRequest request,
			@RequestParam("uuid") String uuid){
		Message message=new Message();
		try {
			//日志
			try {
				this.Log(request, uuid, "uuid:"+uuid+"角色被删除");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			if(sroleService.delRole(uuid)){
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
