package com.comvee.cdms.bloodmonitor.dto;
/**
 * 
 * @author 李左河
 *
 */
public class MemberMonitorPlanDetailDTO {
    /**
     * 日期code， 长期对应星期几(1-7)， 临时对应具体日期(xxxx-xx-xx)
     */
    private String dateCode;
    /**
     * //患者id
     */
    private String memberId;
    /**
     * //方案类型 1 长期 2 临时
     */
    private Integer planType;

	/**
	 * 类型  0 2型糖尿病  1 妊娠糖尿病 2 高血压
	 */
	private Integer eohType;

	/*
    病种： 1 糖尿病 2 高血压
     */
	private Integer illnessType;

	public Integer getEohType() {
		return eohType;
	}

	public void setEohType(Integer eohType) {
		this.eohType = eohType;
	}

	public Integer getIllnessType() {
		return illnessType;
	}

	public void setIllnessType(Integer illnessType) {
		this.illnessType = illnessType;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
}