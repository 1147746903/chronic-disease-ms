package com.comvee.cdms.defender.model;

import java.io.Serializable;

/**
 * @File name:   PatientModel.java   TODO
 * @Create on:   2018年8月2日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public class PatientModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pid; //患者id

    private String openid;//公众号openid

    private String pname;//用户名字	

    private String pidcardno;//身份证

    private String pphonenum;//电话号码

    private String insertDt;

    private String modifyDt;

    private Integer isValid;
    
    private String diseaseType;
    
    
    private String ecardno;
    
    private String diabetesAccede;
    private String daccedeTime;
    private String pressureAccede;
    private String paccedeTime;
    
    
	private String picUrl;
	private String sex;

	public String getEcardno() {
		return ecardno;
	}

	public void setEcardno(String ecardno) {
		this.ecardno = ecardno;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

//	public String getOpenid() {
//		return openid;
//	}
//
//	public void setOpenid(String openid) {
//		this.openid = openid;
//	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPidcardno() {
		return pidcardno;
	}

	public void setPidcardno(String pidcardno) {
		this.pidcardno = pidcardno;
	}

	public String getPphonenum() {
		return pphonenum;
	}

	public void setPphonenum(String pphonenum) {
		this.pphonenum = pphonenum;
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

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}

	public String getDiabetesAccede() {
		return diabetesAccede;
	}

	public void setDiabetesAccede(String diabetesAccede) {
		this.diabetesAccede = diabetesAccede;
	}

	public String getDaccedeTime() {
		return daccedeTime;
	}

	public void setDaccedeTime(String daccedeTime) {
		this.daccedeTime = daccedeTime;
	}

	public String getPressureAccede() {
		return pressureAccede;
	}

	public void setPressureAccede(String pressureAccede) {
		this.pressureAccede = pressureAccede;
	}

	public String getPaccedeTime() {
		return paccedeTime;
	}

	public void setPaccedeTime(String paccedeTime) {
		this.paccedeTime = paccedeTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
    
	
    
}
