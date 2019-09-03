package com.evisible.os.controlcenter.model.vo;

import java.util.List;

/**
 * <p>封装数据字典VO</p>
 * @author TengDong
 * @date 20160816
 */
public class SDicDateObject {
	
	private List<SDicDate> SDicDates;
	
	public SDicDateObject(){}
	
	
	public List<SDicDate> getSDicDates() {
		return SDicDates;
	}


	public void setSDicDates(List<SDicDate> sDicDates) {
		SDicDates = sDicDates;
	}

	
}
