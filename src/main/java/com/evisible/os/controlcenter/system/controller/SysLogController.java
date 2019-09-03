package com.evisible.os.controlcenter.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.service.ISysLogService;
import com.evisible.os.controlcenter.util.constant.MsgConstant;

/**
 * <p>用户控制器,对应日志处理界面</p>
 * @author TengDong
 * @Date 20170412
 */
@Controller
@RequestMapping("sysLog")
public class SysLogController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(SysLogController.class);
	@Autowired
	private ISysLogService sysLogService;

	/**
	 * @see 时间段获取系统日志
	 * */
	@RequestMapping(value = "/getSysLogs")
	@ResponseBody
	public Map<String, Object> getSysLogs(HttpServletRequest request,
			String startDate, String endDate,
			@RequestParam("page") int page, @RequestParam("rows") int rows) {
		PageUI pageUI = new PageUI(page, rows);
		return sysLogService.getSysLog(pageUI, startDate, endDate);
		
	}
	/**
	 * @see 清除系统日志
	 * */
	@RequestMapping(value = "/delSysLogs",method = RequestMethod.POST)
	@ResponseBody
	public Object delSysLogs(HttpServletRequest request,
			@RequestBody Map<String, Object> param) {
		Message message=new Message();
		try {
			String[] logUuids=param.get("uuids").toString().split(",");
			List<String> logUuidsList=new ArrayList<String>(); 
			for(int i=0;i<logUuids.length;i++){
				/*//日志
				try {
					this.Log(request, logUuids[i], "清除日志信息,释放表数据");
				} catch (Exception e) {
					log.debug("------日志处理异常---------"+e.getMessage());
				}*/
				logUuidsList.add(logUuids[i].toString());
			}
			if(sysLogService.delSysLog(logUuidsList)){
				message.setmsgCode(MsgConstant.SUCCESSCODE);
				message.setmsgContent(MsgConstant.SUCCESSCONTENT);
			}else{
				message.setmsgCode(MsgConstant.ERRORCODE);
				message.setmsgContent(MsgConstant.ERRORCONTENT);
			}
		} catch (Exception e) {
			log.info("------------------清除系统日志异常----------------------");
			e.printStackTrace();
		}
		return message;
	}
}
