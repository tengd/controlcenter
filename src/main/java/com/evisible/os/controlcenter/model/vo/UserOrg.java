package com.evisible.os.controlcenter.model.vo;

import java.util.List;

/**
 * <p>用户组织</p>
 * @author TengDong
 * @date20160816
 */
public class UserOrg {
      private String userId;
      private List<Org> orgs;
      public UserOrg(){}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Org> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}
      
}
