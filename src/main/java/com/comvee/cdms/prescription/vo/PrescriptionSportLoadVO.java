package com.comvee.cdms.prescription.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PrescriptionSportLoadVO implements Serializable {

    /**
     *  
     */
    private List<Map<String, Object>> sportOutList;
    private String sportPlan;
    private String sportRule;
    private String sportTips;
    private Map<String, Object> sportItem;
    private Map<String, String> pictureMapping;
    private String contraindication;
    private Map<String, Object> sportInfoStr;
	//是否有运动习惯  
	private String memberSprotCase; 
	//开始运动时间 
	private String sportOpportunity; 
	//运动时长 
	private String sportDuration; 
	//运动频率 
	private String sportFrequency; 
	//运动方式  
	private String sprotMethod; 
    
	public List<Map<String, Object>> getSportOutList() {
		return sportOutList;
	}
	public void setSportOutList(List<Map<String, Object>> sportOutList) {
		this.sportOutList = sportOutList;
	}
	public String getSportPlan() {
		return sportPlan;
	}
	public void setSportPlan(String sportPlan) {
		this.sportPlan = sportPlan;
	}
	public String getSportRule() {
		return sportRule;
	}
	public void setSportRule(String sportRule) {
		this.sportRule = sportRule;
	}
	public String getSportTips() {
		return sportTips;
	}
	public void setSportTips(String sportTips) {
		this.sportTips = sportTips;
	}
 
	public Map<String, Object> getSportItem() {
		return sportItem;
	}
	public void setSportItem(Map<String, Object> sportItem) {
		this.sportItem = sportItem;
	}
	public Map<String, String> getPictureMapping() {
		return pictureMapping;
	}
	public void setPictureMapping(Map<String, String> pictureMapping) {
		this.pictureMapping = pictureMapping;
	}
	public String getContraindication() {
		return contraindication;
	}
	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}
	public Map<String, Object> getSportInfoStr() {
		return sportInfoStr;
	}
	public void setSportInfoStr(Map<String, Object> sportInfoStr) {
		this.sportInfoStr = sportInfoStr;
	}
	public String getMemberSprotCase() {
		return memberSprotCase;
	}
	public void setMemberSprotCase(String memberSprotCase) {
		this.memberSprotCase = memberSprotCase;
	}
	public String getSportOpportunity() {
		return sportOpportunity;
	}
	public void setSportOpportunity(String sportOpportunity) {
		this.sportOpportunity = sportOpportunity;
	}
	public String getSportDuration() {
		return sportDuration;
	}
	public void setSportDuration(String sportDuration) {
		this.sportDuration = sportDuration;
	}
	public String getSportFrequency() {
		return sportFrequency;
	}
	public void setSportFrequency(String sportFrequency) {
		this.sportFrequency = sportFrequency;
	}
	public String getSprotMethod() {
		return sprotMethod;
	}
	public void setSprotMethod(String sprotMethod) {
		this.sprotMethod = sprotMethod;
	}
 
 
   
 
}
