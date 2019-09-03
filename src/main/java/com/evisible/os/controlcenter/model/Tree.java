package com.evisible.os.controlcenter.model;

import java.util.List;

/**
 * <p>EasyUi-tree数据封装</p>
 * @author TengDong
 * @Date 20160328
 */
public class Tree extends Message {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String state; 
	private List<TreeChildren> children;
	public Tree(){}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the children
	 */
	public List<TreeChildren> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeChildren> children) {
		this.children = children;
	}
	
	
}
