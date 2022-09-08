package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class GetPrescriptionSugarmonitorDTO implements Serializable {
	
	/**
     * 监测方案名称
     */
	private String schemeName; 
	/**
     * 是否达标
     */
	private String isTolevel; 	
	/**
     * 治疗方案
     */
	private String treatment;

	/**
	 * 适用类型0非妊娠,1妊娠
	 */
	private Integer eohType;

	public Integer getEohType() {
		return eohType;
	}

	public void setEohType(Integer eohType) {
		this.eohType = eohType;
	}
	
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
 
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getIsTolevel() {
		return isTolevel;
	}
	public void setIsTolevel(String isTolevel) {
		this.isTolevel = isTolevel;
	} 
 
	 
    
}
