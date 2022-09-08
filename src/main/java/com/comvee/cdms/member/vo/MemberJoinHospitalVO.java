package com.comvee.cdms.member.vo;

public class MemberJoinHospitalVO {
    private String sid;
    private String doctorId;
    private String memberId;
    private String memberName;
    private String mobilePhone;
    private String sex;
    private String birthday;
    private Integer age;
    private String idCard;
    private String diabetesType;
    private String mark;
    private String grading;
    private String status;
    private String hospitalName;//建档医院
    private Integer thisHospital;
    private String filingDate;
    private Integer attention;
    private Integer diabetesLever;
    private Integer hypLayer;
    private Integer hypLever;
    private String visitDate;//就诊日期
    private String departName;//就诊科室
    private String valid;
    private String picUrl;
    private String committeeId;
    private String committeeName;


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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Integer getThisHospital() {
        return thisHospital;
    }

    public void setThisHospital(Integer thisHospital) {
        this.thisHospital = thisHospital;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getDiabetesLever() {
        return diabetesLever;
    }

    public void setDiabetesLever(Integer diabetesLever) {
        this.diabetesLever = diabetesLever;
    }

    public Integer getHypLayer() {
        return hypLayer;
    }

    public void setHypLayer(Integer hypLayer) {
        this.hypLayer = hypLayer;
    }

    public Integer getHypLever() {
        return hypLever;
    }

    public void setHypLever(Integer hypLever) {
        this.hypLever = hypLever;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberJoinHospitalVO that = (MemberJoinHospitalVO) o;

        return memberId != null ? memberId.equals(that.memberId) : that.memberId == null;
    }

    @Override
    public int hashCode() {
        return memberId != null ? memberId.hashCode() : 0;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
