package com.comvee.cdms.app.doctorapp.model.app;

public class InHospitalAbnormalSugarPatientVO {

    private String memberId;
    private String memberName;
    private Integer sex;
    private String diabetesType;
    private String departmentId;
    private String departmentName;
    private String hospitalNo;
    private String lowBloodSugarValue;
    private String highBloodSugarValue;
    private String picUrl;
    private String bloodSugarRecordId;
    private String bedNo;

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getLowBloodSugarValue() {
        return lowBloodSugarValue;
    }

    public void setLowBloodSugarValue(String lowBloodSugarValue) {
        this.lowBloodSugarValue = lowBloodSugarValue;
    }

    public String getHighBloodSugarValue() {
        return highBloodSugarValue;
    }

    public void setHighBloodSugarValue(String highBloodSugarValue) {
        this.highBloodSugarValue = highBloodSugarValue;
    }

    public String getBloodSugarRecordId() {
        return bloodSugarRecordId;
    }

    public void setBloodSugarRecordId(String bloodSugarRecordId) {
        this.bloodSugarRecordId = bloodSugarRecordId;
    }
}
