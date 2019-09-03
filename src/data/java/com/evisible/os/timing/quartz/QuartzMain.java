package com.evisible.os.timing.quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.evisible.os.timing.quartz.util.ServletContextEventUtil;

/**
 * <p>这个类只是作为启动类</p>
 * @author Administrator
 *
 */
public class QuartzMain implements ServletContextListener  {
	// 服务器启动时执行该事件
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	ServletContextEventUtil.setServletContextEvent(sce);
           QuartzLoad.run(sce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 服务器停止时执行该事件
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
           QuartzLoad.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
