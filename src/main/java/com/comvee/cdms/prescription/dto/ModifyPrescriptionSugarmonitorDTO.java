package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class ModifyPrescriptionSugarmonitorDTO implements Serializable {

	/**
     * 主键
     */
	private String sid; 
	/**
     * 患者ID
     */
	private String mid; 
	/**
     * 处方ID
     */
	private String eohPrescriptionId; 
	/**
     * 监测方案名ID
     */
	private String sugarmonitorId; 
	/**
     * 添加时间
     */
	private String insertDt; 
	/**
     *  
     */
	private String recordDt; 
	/**
     * 是否有效
     */
	private String isValid; 
	/**
     * 监测方案名称
     */
	private String schemeName; 
	/**
     * 是否达标
     */
	private String istoLevel; 	
	/**
     * 指导说明
     */
	private String guidDesc; 
	/**
     * 方案详情
     */
	private String monitorScheme; 
	/**
     * 每月监测总数
     */
	private String monitorMonth; 
	/**
     * 每季度监测总数
     */
	private String monitorQuarter; 
	/**
     * 每半年监测总数
     */
	private String monitorHalfyear; 
	/**
     * 监测方案说明
     */
	private String monitorText;
	/**
	 * 血糖监测方案
	 */
    private String bloodPlan;
	/**
	 * 糖化方案
	 */
    private String hba1cPlan;
    
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
	public String getBloodPlan() {
		return bloodPlan;
	}
	public void setBloodPlan(String bloodPlan) {
		this.bloodPlan = bloodPlan;
	}
	public String getHba1cPlan() {
		return hba1cPlan;
	}
	public void setHba1cPlan(String hba1cPlan) {
		this.hba1cPlan = hba1cPlan;
	}
    
    
    
}
