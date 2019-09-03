package com.evisible.os.controlcenter.model.vo;

/**
 * <p>权限</p>
 * @author TengDong
 * @date 20160815
 */
public class Permission {
		private String pid;
		private String pname;
		private String permission;
		public Permission(){}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getPname() {
			return pname;
		}
		public void setPname(String pname) {
			this.pname = pname;
		}
		public String getPermission() {
			return permission;
		}
		public void setPermission(String permission) {
			this.permission = permission;
		}
		
}
