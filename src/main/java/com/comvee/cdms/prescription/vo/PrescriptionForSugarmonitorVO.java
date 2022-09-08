package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class PrescriptionForSugarmonitorVO implements Serializable {
	
    /**
     * 模块主键
     */
    private String sid;
    /**
     * 监测方案信息
     */
    private String sugarmonitorInfo;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState = 0;
    
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSugarmonitorInfo() {
		return sugarmonitorInfo;
	}
	public void setSugarmonitorInfo(String sugarmonitorInfo) {
		this.sugarmonitorInfo = sugarmonitorInfo;
	}
	public Integer getSaveState() {
		return saveState;
	}
	public void setSaveState(Integer saveState) {
		this.saveState = saveState;
	}
	 
    
	 
}
