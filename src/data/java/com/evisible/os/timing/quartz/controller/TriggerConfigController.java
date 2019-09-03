package com.evisible.os.timing.quartz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.util.constant.MsgConstant;
import com.evisible.os.timing.quartz.entity.TTriggerConfig;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;

/**
 * 
 * <p>TriggerConfig的Controller类， 处理定时任务类的请求</p>
 * @author JiangWanDong
 * @Date   2018年1月16日
 */
@Controller("triggerConfigController")
@RequestMapping("/triggerConfig")
public class TriggerConfigController extends BaseController{
	
	@Autowired
	private ITriggerConfigService triggerConfigService;
	
	/**
	 * <p>设置数据源的定时触发参数，若t_trigger_config 中有该数据源定时设置， 则更新，若没有，则新建。</p>
	 * @author JiangWanDong
	 */
	@RequestMapping("/setDataSourceTriggerConfig")
	@ResponseBody
	public Object setDataSourceTriggerConfig(HttpServletRequest request , HttpServletResponse resp){
		resp.setContentType("text/html;charset=UTF-8");  
		String cron = request.getParameter("cron");
		String triggerclazz = request.getParameter("triggerclazz");
		String dataSourceId = request.getParameter("dataSourceId");
		//如果当前t_trigger_config 中有该数据源定时设置， 则更新数据
		List<TTriggerConfig> list = triggerConfigService.isDataSourceTriggerConfigered(dataSourceId);
		if(list.size() > 0){
			TTriggerConfig record = list.get(0);
			record.setClazz(triggerclazz);
			record.setDatasourceId(dataSourceId);
			record.setQuartzCorn(cron);
			if(triggerConfigService.updateTriggerConfig(record)){
				return new Message(MsgConstant.SUCCESSCODE,"定时任务设置成功");
			}else{
				return new Message(MsgConstant.ERRORCODE,"定时任务设置失败");
			}
			
		}else{
			TTriggerConfig record = new TTriggerConfig();
			record.setClazz(triggerclazz);
			record.setDatasourceId(dataSourceId);
			record.setQuartzCorn(cron);
			//将定时信息插入triggerConfig
			if(!triggerConfigService.addTriggerConfig(record)){
				return new Message(MsgConstant.ERRORCODE,"插入TriggerConfig信息失败");
			}
			//将新加入的任务加入quartz的job池执行
			try {
				triggerConfigService.addJobTrigger(record);
			} catch (Exception e) {
				return new Message(MsgConstant.ERRORCODE,"定时触发设置失败");
			}
			 return new Message(MsgConstant.ERRORCODE,"定时设置成功！");
		}

	}
	
	/**
	 * 
	 * <p>取消定时任务 ， 将当前quartz任务池中的任务取消， 并且将t_trigger_config中is_available设为0(不可用)</p>
	 * @author JiangWanDong
	 */
	@RequestMapping("/cancelSchedule")
	@ResponseBody
	public Message cancelSchedule(HttpServletRequest request){
		Message message = new Message();
		String dataSourceId = request.getParameter("dataSourceId");
		if(triggerConfigService.cancelTriggerByDataSourceId(dataSourceId)){
			message.setmsgContent("取消定时任务成功！");
			return message;
		}else{
			message.setmsgContent("取消定时任务失败！");
			return message;
		}
	}

}
