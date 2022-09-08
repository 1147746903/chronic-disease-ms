package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class ModifyPrescriptionSportDTO implements Serializable {

	/**
     * 运动治疗方案
     */
    private String xdjh;
	/**
     * 处方ID
     */
    private String prescriptId;
	/**
     * 运动方式
     */
    private String sportListStr;
	/**
     *  
     */
    private String sportDaParam; 
	/**
     * 患者信息
     */
    private String archivesJson;
	/**
     * 运动问题
     */
    private String sportOutJson;
	/**
     * 运动小贴士
     */
    private String sportTips;
	/**
     * 患者运动信息
     */
    private String sportInfoStr;
 
    private String lowSportWay;
    private String middleSportWay;
    private String highSportWay;
    
	public String getXdjh() {
		return xdjh;
	}
	public void setXdjh(String xdjh) {
		this.xdjh = xdjh;
	}
	public String getPrescriptId() {
		return prescriptId;
	}
	public void setPrescriptId(String prescriptId) {
		this.prescriptId = prescriptId;
	}
	public String getSportListStr() {
		return sportListStr;
	}
	public void setSportListStr(String sportListStr) {
		this.sportListStr = sportListStr;
	}
	public String getSportDaParam() {
		return sportDaParam;
	}
	public void setSportDaParam(String sportDaParam) {
		this.sportDaParam = sportDaParam;
	}
	public String getArchivesJson() {
		return archivesJson;
	}
	public void setArchivesJson(String archivesJson) {
		this.archivesJson = archivesJson;
	}
	public String getSportOutJson() {
		return sportOutJson;
	}
	public void setSportOutJson(String sportOutJson) {
		this.sportOutJson = sportOutJson;
	}
	public String getSportTips() {
		return sportTips;
	}
	public void setSportTips(String sportTips) {
		this.sportTips = sportTips;
	}
	public String getSportInfoStr() {
		return sportInfoStr;
	}
	public void setSportInfoStr(String sportInfoStr) {
		this.sportInfoStr = sportInfoStr;
	}
	public String getLowSportWay() {
		return lowSportWay;
	}
	public void setLowSportWay(String lowSportWay) {
		this.lowSportWay = lowSportWay;
	}
	public String getMiddleSportWay() {
		return middleSportWay;
	}
	public void setMiddleSportWay(String middleSportWay) {
		this.middleSportWay = middleSportWay;
	}
	public String getHighSportWay() {
		return highSportWay;
	}
	public void setHighSportWay(String highSportWay) {
		this.highSportWay = highSportWay;
	}

    
}
