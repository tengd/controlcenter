package com.evisible.os.controlcenter.system.service;

import java.util.List;
import com.evisible.os.controlcenter.system.entity.SOrg;

/**
 * <p>组织</p>
 * @author TengDong
 * @Date 20160815
 */
public interface ISOrgService {
		/**
		 * 根据组织ids获得组织
		 * @param orgids
		 * @return 返回组织S
		 */
		List<SOrg> getOrgs(List<String> orgids);
		
		/**
		 * 获得父组织
		 * @return 返回组织S
		 */
		List<SOrg> getParentOrgs();
		
		/**
		 * 获得组织combotree
		 * @return 组织s
		 */
		String getOrgTreeNode();
		
		/**
		 * 根据父组织parentIds获得子组织
		 * @param parentIds
		 * @return 返回了组织S
		 */
		List<SOrg> getChildrenOrgs(List<String> parentIds);
		
		/**
		 * @return 组织树json
		 */
		public String getOrgTreeToJson();
		
		
		
		/**
		 * 添加组织
		 * @param org 组织
		 * @return boolean
		 */
		public boolean addOrg(SOrg org);
		
		
		/**
		 * 编辑组织
		 * @param org 组织
		 * @return boolean
		 */
		public boolean editOrg(SOrg org);
		
		/**
		 * 删除组织
		 * @param uuid 组织id
		 * @return boolean
		 */
		public boolean delOrg(String uuid);
		
		
}
