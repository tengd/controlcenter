package com.evisible.os.controlcenter.model;

import java.io.Serializable;

/**
 * <p>界面消息</p>
 * <p>各功能entity下继承此类</p>
 * @author TengDong
 * @Date 20160110
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msgCode;
    private String msgContent;
    private Object msgObject;
    public Message(){}
    public Message(String msgCode,String msgContent,Object msgObject){
    	this.msgCode=msgCode;
    	this.msgContent=msgContent;
    	this.msgObject=msgObject;
    }
	/**
	 * @return 消息码
	 */
	public String getmsgCode() {
		return msgCode;
	}
	/**
	 * @param msgCode 消息码
	 */
	public void setmsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	/**
	 * @return 消息内容
	 */
	public String getmsgContent() {
		return msgContent;
	}
	/**
	 * @param msgContent 消息内容
	 */
	public void setmsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	/**
	 * @return 扩展对象
	 */
	public Object getmsgObject() {
		return msgObject;
	}
	/**
	 * @param msgObject 扩展对象
	 */
	public void setmsgObject(Object msgObject) {
		this.msgObject = msgObject;
	}
	public Message(String msgCode, String msgContent) {
		super();
		this.msgCode = msgCode;
		this.msgContent = msgContent;
	}
    
	
}
