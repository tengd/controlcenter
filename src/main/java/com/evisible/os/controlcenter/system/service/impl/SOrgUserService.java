package com.evisible.os.controlcenter.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.MybatisGenerator;
import com.evisible.os.controlcenter.system.dao.SOrgUserMapper;
import com.evisible.os.controlcenter.system.entity.SOrgUser;
import com.evisible.os.controlcenter.system.entity.SOrgUserExample;
import com.evisible.os.controlcenter.system.service.ISOrgUserService;

/**
 * <p>组织用户Service</p>
 * @author TengDong
 * @Date 20160815
 */
@Service("sOrgUserService")
public class SOrgUserService extends MybatisGenerator<SOrgUserMapper> implements  ISOrgUserService{
	private static Logger log = LoggerFactory.getLogger(SOrgUserService.class);
    public SOrgUserService(){
    	super(SOrgUserMapper.class);
    }
    /**
     * 根据用户获得所在的所有组织
     * */
	@Override
	public List<SOrgUser> getOrgUsers(String uid) {
		try{
			SOrgUserExample example=new SOrgUserExample();
			SOrgUserExample.Criteria criteria=example.createCriteria();
			criteria.andUIdEqualTo(uid);
			return this.getDao().selectByExample(example);
		}catch(Exception e){
			log.info("----------获得用户组织失败---------");
			  e.printStackTrace();
		}
		return null;
	}
}
