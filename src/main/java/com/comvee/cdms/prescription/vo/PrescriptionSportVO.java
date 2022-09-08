package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class PrescriptionSportVO implements Serializable {
 
	private String sid;
	/** 患者id ,(字段类型:bigint) **/
	private String memberId;
	/** 处方id ,(字段类型:bigint) **/
	private String prescriptionId;
	/** 运动建议id ,(字段类型:bigint) **/
	private String sportSuggestId;
	/** 运动建议信息 ,(字段类型:varchar) **/
	private String sportSuggestText;
	/** 运动目的id ,(字段类型:bigint) **/
	private String sportPurposeId;
	/** 运动目的信息 ,(字段类型:varchar) **/
	private String sportPurposeText;
	/** 运动强度id ,(字段类型:varchar) **/
	private String sportStrength;
	/** 运动项目id ,(字段类型:varchar) **/
	private String sportItem;
	/** 运动项目text ,(字段类型:varchar) **/
	private String sportItemText;
	/** 最大的心率 ,(字段类型:varchar) **/
	private String maxHr;
	/** 目标心率 ,(字段类型:varchar) **/
	private String targetHr;
	/** 运动频率 ,(字段类型:varchar) **/
	private String sportTime;
	/** 运动频率文本 ,(字段类型:varchar) **/
	private String sportTimeText;
	/** 持续时间 ,(字段类型:varchar) **/
	private String sportSpanTime;
	/** 持续时间文本 ,(字段类型:varchar) **/
	private String sportSpanTimeText;
	/** ,(字段类型:datetime) **/
	private String insertDt;
	/** ,(字段类型:tinyint) **/
	private String isValid;
	/** 0表示降脂处方1表示降糖处方 ,(字段类型:tinyint) **/
	private String type;
	/** 适宜情况 ,(字段类型:varchar) **/
	private String adaptCondition;
	/** 运动处方生成时不满足逻辑时的文案 ,(字段类型:varchar) **/
	private String execptionText;
	private String sportItemJson;
	private String twoSportStrength;
	private String twoSportFrequency;
	private String twoSportContinue;
	private String twoSportRange;
	private String twoSportMethod;
	private String sportRule;
	/**
	 *  运动明细
	 */
	private String sportItemDetailJson;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(String prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public String getSportSuggestId() {
		return sportSuggestId;
	}
	public void setSportSuggestId(String sportSuggestId) {
		this.sportSuggestId = sportSuggestId;
	}
	public String getSportSuggestText() {
		return sportSuggestText;
	}
	public void setSportSuggestText(String sportSuggestText) {
		this.sportSuggestText = sportSuggestText;
	}
	public String getSportPurposeId() {
		return sportPurposeId;
	}
	public void setSportPurposeId(String sportPurposeId) {
		this.sportPurposeId = sportPurposeId;
	}
	public String getSportPurposeText() {
		return sportPurposeText;
	}
	public void setSportPurposeText(String sportPurposeText) {
		this.sportPurposeText = sportPurposeText;
	}
	public String getSportStrength() {
		return sportStrength;
	}
	public void setSportStrength(String sportStrength) {
		this.sportStrength = sportStrength;
	}
	public String getSportItem() {
		return sportItem;
	}
	public void setSportItem(String sportItem) {
		this.sportItem = sportItem;
	}
	public String getSportItemText() {
		return sportItemText;
	}
	public void setSportItemText(String sportItemText) {
		this.sportItemText = sportItemText;
	}
	public String getMaxHr() {
		return maxHr;
	}
	public void setMaxHr(String maxHr) {
		this.maxHr = maxHr;
	}
	public String getTargetHr() {
		return targetHr;
	}
	public void setTargetHr(String targetHr) {
		this.targetHr = targetHr;
	}
	public String getSportTime() {
		return sportTime;
	}
	public void setSportTime(String sportTime) {
		this.sportTime = sportTime;
	}
	public String getSportTimeText() {
		return sportTimeText;
	}
	public void setSportTimeText(String sportTimeText) {
		this.sportTimeText = sportTimeText;
	}
	public String getSportSpanTime() {
		return sportSpanTime;
	}
	public void setSportSpanTime(String sportSpanTime) {
		this.sportSpanTime = sportSpanTime;
	}
	public String getSportSpanTimeText() {
		return sportSpanTimeText;
	}
	public void setSportSpanTimeText(String sportSpanTimeText) {
		this.sportSpanTimeText = sportSpanTimeText;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdaptCondition() {
		return adaptCondition;
	}
	public void setAdaptCondition(String adaptCondition) {
		this.adaptCondition = adaptCondition;
	}
	public String getExecptionText() {
		return execptionText;
	}
	public void setExecptionText(String execptionText) {
		this.execptionText = execptionText;
	}
	public String getSportItemJson() {
		return sportItemJson;
	}
	public void setSportItemJson(String sportItemJson) {
		this.sportItemJson = sportItemJson;
	}
	public String getTwoSportStrength() {
		return twoSportStrength;
	}
	public void setTwoSportStrength(String twoSportStrength) {
		this.twoSportStrength = twoSportStrength;
	}
	public String getTwoSportFrequency() {
		return twoSportFrequency;
	}
	public void setTwoSportFrequency(String twoSportFrequency) {
		this.twoSportFrequency = twoSportFrequency;
	}
	public String getTwoSportContinue() {
		return twoSportContinue;
	}
	public void setTwoSportContinue(String twoSportContinue) {
		this.twoSportContinue = twoSportContinue;
	}
	public String getTwoSportRange() {
		return twoSportRange;
	}
	public void setTwoSportRange(String twoSportRange) {
		this.twoSportRange = twoSportRange;
	}
	public String getTwoSportMethod() {
		return twoSportMethod;
	}
	public void setTwoSportMethod(String twoSportMethod) {
		this.twoSportMethod = twoSportMethod;
	}
	public String getSportRule() {
		return sportRule;
	}
	public void setSportRule(String sportRule) {
		this.sportRule = sportRule;
	}
	public String getSportItemDetailJson() {
		return sportItemDetailJson;
	}
	public void setSportItemDetailJson(String sportItemDetailJson) {
		this.sportItemDetailJson = sportItemDetailJson;
	}

}
