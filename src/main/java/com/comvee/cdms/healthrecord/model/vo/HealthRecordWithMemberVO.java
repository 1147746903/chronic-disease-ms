package com.comvee.cdms.healthrecord.model.vo;

public class HealthRecordWithMemberVO {

    /**
     * sid
     */
    private String sid;

    /**
     * member_id
     */
    private String memberId;

    /**
     * hospital_id
     */
    private String hospitalId;

    /**
     * checkup_date
     */
    private String checkupDate;

    /**
     * visit_type
     */
    private Integer visitType;

    /**
     * insert_dt
     */
    private String insertDt;

    private String memberName;

    private Integer sex;

    private String birthday;

    private String mobilePhone;

    private String isDiabetes;

    private String essentialHyp;
    private String jwSyncStatus;

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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getJwSyncStatus() {
        return jwSyncStatus;
    }

    public void setJwSyncStatus(String jwSyncStatus) {
        this.jwSyncStatus = jwSyncStatus;
    }
}
