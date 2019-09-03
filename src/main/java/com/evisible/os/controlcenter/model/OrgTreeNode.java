package com.evisible.os.controlcenter.model;

import java.util.List;

public class OrgTreeNode extends Message{
	
	private static final long serialVersionUID = 1L;

		private String id;
	    
	    private String text;
	    
	    private String parentId;
	    
	    private String parentCode;
	    
	   
		public String getParentCode() {
			return parentCode;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}

		private List<OrgTreeNode> children;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    public List<OrgTreeNode> getChildren() {
	        return children;
	    }

	    public OrgTreeNode(String id, String text) {
	        super();
	        this.id = id;
	        this.text = text;
	    }

	    public void setChildren(List<OrgTreeNode> children) {
	        this.children = children;
	    }

	    public OrgTreeNode() {
	        super();
	    }
	    public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

}
