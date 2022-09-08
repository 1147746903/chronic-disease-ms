package com.comvee.cdms.glu.model;

import java.io.Serializable;

/**
 * Created by hugh on 2017/3/7.
 */
public class MachineVersionModel implements Serializable {
    private String sid; 
    private String machineType;
    private String versionNum;
    private String versionMsg;
    private String insertDt;
    private String modifyDt; 
    private String beNewest;
    private String upgradeNum;
    private String upgradeUrl;
    private String isForce;
    
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getVersionMsg() {
		return versionMsg;
	}
	public void setVersionMsg(String versionMsg) {
		this.versionMsg = versionMsg;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getBeNewest() {
		return beNewest;
	}
	public void setBeNewest(String beNewest) {
		this.beNewest = beNewest;
	}
	public String getUpgradeNum() {
		return upgradeNum;
	}
	public void setUpgradeNum(String upgradeNum) {
		this.upgradeNum = upgradeNum;
	}
	public String getUpgradeUrl() {
		return upgradeUrl;
	}
	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}
	public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
    
}
