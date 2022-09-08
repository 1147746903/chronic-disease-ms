package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class GetPrescriptionSportOutDTO implements Serializable {

	/**
     * 运动
     */
    private String sportPlan;
    
    /**
     * 运动
     */
    private String triglycerideTg;
    
    /**
     * 运动
     */
    private String cholesterolTc;
    
    /**
     * 运动
     */
    private String hdlC; 
    
    /**
     * 运动
     */
    private String ldlC;
    
    /**
     * 运动
     */
    private String hr;
    
    /**
     * 患者id
     */
    private String memberId;
    
    /**
     * bmi
     */
    private String sprotBmi;
    
    /**
     *  
     */
    private String memberSprotCase;
    
    /**
     *  
     */
    private String sportFrequency;
    
    /**
     *  
     */
    private String sportDuration;
    
    /**
     *  
     */
    private String sportOpportunity;
    
    /**
     * 体重
     */
    private String sprotWeight;

	/**
	 * 孕周
	 * @return
	 */
	private String gestationalWeeks;

	/**
	 * 出生日期
	 */
	private String sportBirth;

    public String getSportBirth() {
        return sportBirth;
    }

    public void setSportBirth(String sportBirth) {
        this.sportBirth = sportBirth;
    }

    public String getGestationalWeeks() {
		return gestationalWeeks;
	}

	public void setGestationalWeeks(String gestationalWeeks) {
		this.gestationalWeeks = gestationalWeeks;
	}

	public String getSportPlan() {
		return sportPlan;
	}

	public void setSportPlan(String sportPlan) {
		this.sportPlan = sportPlan;
	}

	public String getTriglycerideTg() {
		return triglycerideTg;
	}

	public void setTriglycerideTg(String triglycerideTg) {
		this.triglycerideTg = triglycerideTg;
	}

	public String getCholesterolTc() {
		return cholesterolTc;
	}

	public void setCholesterolTc(String cholesterolTc) {
		this.cholesterolTc = cholesterolTc;
	}

	public String getHdlC() {
		return hdlC;
	}

	public void setHdlC(String hdlC) {
		this.hdlC = hdlC;
	}

	public String getLdlC() {
		return ldlC;
	}

	public void setLdlC(String ldlC) {
		this.ldlC = ldlC;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSprotBmi() {
		return sprotBmi;
	}

	public void setSprotBmi(String sprotBmi) {
		this.sprotBmi = sprotBmi;
	}

	public String getMemberSprotCase() {
		return memberSprotCase;
	}

	public void setMemberSprotCase(String memberSprotCase) {
		this.memberSprotCase = memberSprotCase;
	}

	public String getSportFrequency() {
		return sportFrequency;
	}

	public void setSportFrequency(String sportFrequency) {
		this.sportFrequency = sportFrequency;
	}

	public String getSportDuration() {
		return sportDuration;
	}

	public void setSportDuration(String sportDuration) {
		this.sportDuration = sportDuration;
	}

	public String getSportOpportunity() {
		return sportOpportunity;
	}

	public void setSportOpportunity(String sportOpportunity) {
		this.sportOpportunity = sportOpportunity;
	}

	public String getSprotWeight() {
		return sprotWeight;
	}

	public void setSprotWeight(String sprotWeight) {
		this.sprotWeight = sprotWeight;
	}
 
     
}
