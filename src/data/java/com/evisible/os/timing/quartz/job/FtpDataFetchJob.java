package com.evisible.os.timing.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.evisible.os.business.service.ITFtpFileService;
import com.evisible.os.timing.quartz.entity.TTriggerConfig;

/**
 * 
 * <p>获取ftp服务器内容并插入ftpfile表的定时job</p>
 * @author JiangWanDong
 * @Date   2018年1月31日
 */
public class FtpDataFetchJob implements Job{
	private static Logger log = LoggerFactory.getLogger(FtpDataFetchJob.class);
	@Autowired
	ITFtpFileService ftpFileService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		log.info("--------quartz开始获取ftp服务器数据---------------------");
	    TTriggerConfig config = (TTriggerConfig) context.getJobDetail().getJobDataMap().get("triggerConfig");
	    ftpFileService.getFtpFilesInfo(config.getDatasourceId());
		log.info("--------quartz获取ftp服务器数据成功 , 开始解析数据---------------------");
		//将当前数据库中未解析的ftp数据解析， 插入表， 并且将解析完成的数据放入bak文件夹
		ftpFileService.resolveAllUnResolvedFtpData();
		log.info("--------quartz解析ftp服务器数据成功 ---------------------");
	}

}
