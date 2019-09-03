package com.evisible.os.controlcenter.system.controller;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.Tree;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SFun;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.IFunService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.constant.MsgConstant;


/**
 * <p>功能控制器,对应界面功能树,菜单树</p>
 * @author TengDong
 * @Date 20160406
 */
@Controller
@RequestMapping("/fun") 
public class FunController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(FunController.class);
	@Autowired 
	private IFunService funService;
	
	/**
	 * 页面跳转方法
	 * @return
	 */
	@RequestMapping("/getFunJump")
	public ModelAndView getFunJump(HttpServletRequest request){
		String url = request.getParameter("url");
		String dataSourceId = request.getParameter("dataSourceId");
		String dataSourceType = request.getParameter("dataSourceType");
		ModelAndView model = new ModelAndView();
		model.setViewName(url);
		model.addObject("dataSourceId",dataSourceId);
		model.addObject("dataSourceType", dataSourceType);
		return model;
	}
	
	/**
	 * 获取权限树
	 * @return List<Tree>
	 */
	@RequestMapping("/getFunTreeByPermission") 
	@ResponseBody
	public Object getFunTreeByPermission(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("currentUser");
		try {
			return funService.getFunTreeByPermission(user.getUuid());
		} catch (NullPointerException nulle) {
			log.info("-----------------系统没有此功能树或没有TREE类型功能---------------------------------------");
			Tree tree=new Tree();
			tree.setText("系统没有此功能树或没有TREE类型功能");
			return tree;
		}
	}
	
	/**
	 * 获取功能树
	 * @return List<Tree>
	 */
	@RequestMapping("/getFunTree") 
	@ResponseBody
	public Object getFunTree(){
		return funService.getFunTree();
	}
	
	
	@RequiresPermissions("功能管理")
	@RequestMapping(value = "/getSfuns")
	@ResponseBody
	public Map<String, Object> getSfuns(String value){
		if(value==null||value.isEmpty()){
			return funService.getSfuns(value);
		}else{
			return funService.getSfuns(StringConvert.getUTF8(value));
		}
	}

	@RequestMapping("/getPaterFuns") 
	@ResponseBody
	public Object getPaterFuns(){
		return funService.getPaterFuns();
	}

	@RequestMapping(value = "/doFun", method = RequestMethod.POST)
	public void doFun(HttpServletRequest request){
		String uuid=request.getParameter("uuid").toString().trim();
		String fName=request.getParameter("fName").toString().trim();
		String fNameJx=request.getParameter("fNameJx").toString().trim();
		String funType=request.getParameter("funType").toString().trim();
		String sortIndex=request.getParameter("sortIndex").toString().trim();
		String paterId=request.getParameter("paterId").toString().trim();
		String url=request.getParameter("url").toString().trim();
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
		SFun record=new SFun();
		record.setfName(fName);
		record.setfNameJx(fNameJx);
		record.setFunType(funType);
		record.setSortIndex(Integer.parseInt(sortIndex));
		record.setPaterId(paterId);
		record.setUrl(url);
		if(uuid.isEmpty()){
			String iuuid=StringConvert.getUUIDString();
			//日志
			try {
				this.Log(request, iuuid, fName+"功能被插入,类型为:"+funType);
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			//新增
			record.setUuid(iuuid);
			record.setCreator(user.getuName());
			try {
				funService.addFun(record);
			} catch (Exception e) {
				log.info("------------新增功能异常-------------");
				e.printStackTrace();
			}
		}else{
			//日志
			try {
				this.Log(request, uuid, fName+"功能被更新");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			//更新
			record.setUuid(uuid);
			record.setUpdator(user.getuName());
			record.setUpdatedate(new Date());
			try {
				funService.editFun(record);
			} catch (Exception e) {
				log.info("------------修改功能异常-------------");
				e.printStackTrace();
			}
		}
	}
	/**
	 * @see删除功能
	 * */
	@RequestMapping(value = "/delFun", method = RequestMethod.POST)
	@ResponseBody
	public Object delFun(HttpServletRequest request,
			@RequestParam("uuid") String uuid){
		Message message=new Message();
		try {
			//日志
			try {
				this.Log(request, uuid, "功能ID:"+uuid+"被删除");
			} catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			}
			if(funService.delFun(uuid)){
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
