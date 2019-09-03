package com.evisible.os.controlcenter.model.vo;

/**
 * <p>角色信息封装</p>
 * @author TengDong
 * @Date 20160815
 */
public class Role {
		private String roleId;
		private String rname;
		private String remark;
		public Role(){}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getRname() {
			return rname;
		}
		public void setRname(String rname) {
			this.rname = rname;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
}
