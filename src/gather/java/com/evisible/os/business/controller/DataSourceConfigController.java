package com.evisible.os.business.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.service.ITDataSourceConfigService;
import com.evisible.os.controlcenter.model.PageUI;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SDicDate;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISDicDateService;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.timing.quartz.service.ITriggerConfigService;

/**
 * 
 * <p>
 * 数据源配置Controller
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月10日
 */
@Controller("dataSourceConfigController")
@RequestMapping("/dataSourceConfig")
public class DataSourceConfigController extends BaseController {

	@Autowired
	private ITDataSourceConfigService tDataSourceService;
	@Autowired
	private ITriggerConfigService triggerConfigService;
	@Autowired
	private ISDicDateService sDicDateService;

	@RequestMapping("/getDataSourceConfigs")
	@ResponseBody
	public Map<String, Object> getDataSourceConfigs(HttpServletRequest request, @RequestParam("page") int page,
			@RequestParam("rows") int rows) {
		String type = request.getParameter("type");
		if (StringConvert.isEmpty(type)) {
			return null;
		}
		PageUI pageUI = new PageUI(page, rows);
		Map<String, Object> map = tDataSourceService.getDataSourceConfigsByType(type.toLowerCase(), pageUI);
		return map;
	}

	@RequestMapping("/addDataSourceConfig")
	@ResponseBody
	public String addDataSourceConfig(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		TDataSourceConfig dataSourceConfig = new TDataSourceConfig();
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		Integer port = 21;
		String portStr = request.getParameter("port");
		if (!StringConvert.isEmpty(portStr)) {
			try {
				port = Integer.parseInt(portStr);
			} catch (NumberFormatException e) {
				return "port 格式错误";
			}
		}
		String dsValue = request.getParameter("dsValue");
		SDicDate dic = sDicDateService.getDicByDicValue(dsValue);		
		dataSourceConfig.setDsName(dic.getdName());
		dataSourceConfig.setDsId(dic.getUuid());
		dataSourceConfig.setUrl(request.getParameter("url"));
		dataSourceConfig.setCreatedate(new Date());
		dataSourceConfig.setCreator(user.getuName());
		dataSourceConfig.setCreatorId(user.getUuid());
		dataSourceConfig.setType(request.getParameter("type"));
		dataSourceConfig.setPort(port);
		dataSourceConfig.setDsPassword(request.getParameter("ds_password"));
		dataSourceConfig.setDsUsername(request.getParameter("ds_username"));
		// 没有uuid就插入，有uuid就根据uuid更新
		if (StringConvert.isEmpty(uuid)) {
			dataSourceConfig.setUuid(StringConvert.getUUIDString());
			try {
				tDataSourceService.addWebServiceDataSourceConfig(dataSourceConfig);
				return "数据插入成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "数据插入失败";
			}
		} else {
			dataSourceConfig.setUuid(uuid);
			dataSourceConfig.setUpdator(user.getuName());
			if (tDataSourceService.updateDataSourceConfig(dataSourceConfig)) {
				return "数据修改成功";
			}
			return "数据修改失败";
		}

	}

	@RequestMapping("/delDataSourceConfig")
	@ResponseBody
	public String delDataSourceConfig(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			tDataSourceService.delWebServiceDataSourceConfig(uuid);
			// 除了删除数据源本身， 还需要将定时触发相关任务删除
			triggerConfigService.cancelTriggerByDataSourceId(uuid);
			return "数据删除成功";
		} catch (Exception e) {
			return "数据删除失败";
		}

	}

}
