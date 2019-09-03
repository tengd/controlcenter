package com.evisible.os.timing.quartz.util;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 
 * <p>重写createJobInstance方法， 使得spring能够注入bean给Quartz</p>
 * @author JiangWanDong
 * @Date   2018年2月1日
 */
public class JobFactoryUtil extends AdaptableJobFactory{
	@Autowired  
    private AutowireCapableBeanFactory capableBeanFactory;  
  
    @Override  
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);  
        capableBeanFactory.autowireBean(jobInstance);  
        return jobInstance;  
    }  
}
