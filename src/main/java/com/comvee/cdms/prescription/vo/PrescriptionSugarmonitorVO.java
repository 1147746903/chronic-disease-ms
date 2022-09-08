package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class PrescriptionSugarmonitorVO implements Serializable {
	
	private String sid; 
	private String mid; 
	private String eohPrescriptionId; 
	private String sugarmonitorId; 
	private String insertDt; 
	private String recordDt; 
	private String isValid; 
	private String schemeName; 
	private String istoLevel; 	
	private String guidDesc; 
	private String monitorScheme; 
	private String monitorMonth; 
	private String monitorQuarter; 
	private String monitorHalfyear; 
	private String monitorText;
	private String monitorSuggestions;
	private String eohType;
	private String preSelect;

	public String getPreSelect() {
		return preSelect;
	}

	public void setPreSelect(String preSelect) {
		this.preSelect = preSelect;
	}

	public String getEohType() {
		return eohType;
	}

	public void setEohType(String eohType) {
		this.eohType = eohType;
	}

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getEohPrescriptionId() {
		return eohPrescriptionId;
	}
	public void setEohPrescriptionId(String eohPrescriptionId) {
		this.eohPrescriptionId = eohPrescriptionId;
	}
	public String getSugarmonitorId() {
		return sugarmonitorId;
	}
	public void setSugarmonitorId(String sugarmonitorId) {
		this.sugarmonitorId = sugarmonitorId;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getRecordDt() {
		return recordDt;
	}
	public void setRecordDt(String recordDt) {
		this.recordDt = recordDt;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getIstoLevel() {
		return istoLevel;
	}
	public void setIstoLevel(String istoLevel) {
		this.istoLevel = istoLevel;
	}
	public String getGuidDesc() {
		return guidDesc;
	}
	public void setGuidDesc(String guidDesc) {
		this.guidDesc = guidDesc;
	}
	public String getMonitorScheme() {
		return monitorScheme;
	}
	public void setMonitorScheme(String monitorScheme) {
		this.monitorScheme = monitorScheme;
	}
	public String getMonitorMonth() {
		return monitorMonth;
	}
	public void setMonitorMonth(String monitorMonth) {
		this.monitorMonth = monitorMonth;
	}
	public String getMonitorQuarter() {
		return monitorQuarter;
	}
	public void setMonitorQuarter(String monitorQuarter) {
		this.monitorQuarter = monitorQuarter;
	}
	public String getMonitorHalfyear() {
		return monitorHalfyear;
	}
	public void setMonitorHalfyear(String monitorHalfyear) {
		this.monitorHalfyear = monitorHalfyear;
	}
	public String getMonitorText() {
		return monitorText;
	}
	public void setMonitorText(String monitorText) {
		this.monitorText = monitorText;
	}
	public String getMonitorSuggestions() {
		return monitorSuggestions;
	}
	public void setMonitorSuggestions(String monitorSuggestions) {
		this.monitorSuggestions = monitorSuggestions;
	}

}
