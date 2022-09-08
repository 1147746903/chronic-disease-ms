package com.comvee.cdms.bloodmonitor.po;

import java.util.List;
/**
 * 
 * @author 李左河
 *
 */
public class SchemaPO {
    private String planId;
    private String memberId;
    /**
     * 类型1长期2临时
     */
    private String planType;
    private String insertDt;
    private String isValid;
    /**
     * 监测方案详情
     */
    private String planDetail;
    /**
     * 方案名称
     */
    private String planName;
    /**
     * 适用说明
     */
    private String applyExplain;

    /***筛选条件****/
    private List<String> midList;
    private List<String> departmentIdList;
    private String dateCode;
    private String weekCode;
    private int checkinStatus;
    private String doctorId;
    private boolean joinDocMember;

    /**
     * 病种 1 糖尿病 2 高血压
     */
    private Integer illnessType;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /******end*****/



    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
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

    public String getPlanDetail() {
        return planDetail;
    }

    public void setPlanDetail(String planDetail) {
        this.planDetail = planDetail;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
    }

    
    public List<String> getMidList() {
        return midList;
    }

    public void setMidList(List<String> midList) {
        this.midList = midList;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getWeekCode() {
        return weekCode;
    }

    public void setWeekCode(String weekCode) {
        this.weekCode = weekCode;
    }

    @Override
    public String toString() {
        return "SchemaPO{" +
                "planId=" + planId +
                ", memberId=" + memberId +
                ", planType='" + planType + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", isValid='" + isValid + '\'' +
                ", planDetail='" + planDetail + '\'' +
                ", planName='" + planName + '\'' +
                ", applyExplain='" + applyExplain + '\'' +
                '}';
    }

    public void setCheckinStatus(int checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public int getCheckinStatus() {
        return checkinStatus;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setJoinDocMember(boolean joinDocMember) {
        this.joinDocMember = joinDocMember;
    }

    public boolean isJoinDocMember() {
        return joinDocMember;
    }

    public Integer getIllnessType() {
        return illnessType;
    }

    public void setIllnessType(Integer illnessType) {
        this.illnessType = illnessType;
    }
}
