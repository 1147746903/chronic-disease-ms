package com.comvee.cdms.bloodmonitor.dto;

/**
 * @author: suyz
 * @date: 2018/11/9
 */
public class GetMemberMonitorDTO {

    private String memberId;
    private String planId;
    private Integer inProgress;
    /**
     * 病种 1 糖尿病 2 高血压
     */
    private Integer illnessType;
    /**
     * 详细类型  0 2型糖尿病  1 妊娠糖尿病 2 高血压
     */
    private Integer eohType;

    public Integer getIllnessType() {
        return illnessType;
    }

    public void setIllnessType(Integer illnessType) {
        this.illnessType = illnessType;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Integer getInProgress() {
        return inProgress;
    }

    public void setInProgress(Integer inProgress) {
        this.inProgress = inProgress;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
