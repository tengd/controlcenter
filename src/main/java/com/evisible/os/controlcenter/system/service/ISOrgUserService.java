package com.evisible.os.controlcenter.system.service;

import java.util.List;

import com.evisible.os.controlcenter.system.entity.SOrgUser;

/**
 * <p>组织用户</p>
 * @author TengDong
 * @Date 20160815
 */
public interface ISOrgUserService {
      /**
       * 根据用户获得所在的所有组织
       * @param uid
       * @return 组织用户S
     */
    List<SOrgUser> getOrgUsers(String uid);
}
