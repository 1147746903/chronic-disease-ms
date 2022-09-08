package com.comvee.cdms.member.bo;

import java.io.Serializable;

public class MemberCheckinBO implements Serializable {
    private static final long serialVersionUID = 7022829255926954047L;
    /**
     * 用户id
     */
    private String memberId;
    /**
     * 用户姓名
     */
    private String memberName;
    /**
     * 头像地址
     */
    private String picUrl;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 性别 1男2女
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 职业
     */
    private String profession;
    /**
     * 糖尿病类型
     */
    private String diabetesType;
    /**
     * 确诊日期
     */
    private String diabetesDate;
    /**
     * 身高
     */
    private String height;
    /**
     * 体重
     */
    private String weight;
    /**
     * bmi
     */
    private String bmi;
    private String isValid;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 患者在院状态 1在院 0出院
     */
    private Integer inStatus;
    /**
     * 1- 已关注 0- 不关注
     */
    private Integer concernStatus;
    /**
     * 付费类型
     */
    private String chargeClass;
    /**
     * 劳动强度
     */
    private String labourIntensity;
    /**
     * 门诊号
     */
    private String visitNo;
    /**
     * 日常习惯
     */
    private String dailyHabitJson;
    /**
     * 糖尿病并发症
     */
    private String diabetesComplicationJson;
    /**
     * 低血糖
     */
    private String hypoglycemiaJson;
    /**
     * 教育情况
     */
    private String educationSituationJson;
    /**
     * 糖尿病症状
     */
    private String diabetesSymptomJson;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 基本信息
     */
    private String basicJson;

    /**
     * 标签
     */
    private String label;
    private int age;
    private int diabetesDt;

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
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getDiabetesType() {
        return diabetesType;
    }
    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }
    public String getDiabetesDate() {
        return diabetesDate;
    }
    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getBmi() {
        return bmi;
    }
    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
    public String getIsValid() {
        return isValid;
    }
    public void setIsValid(String isValid) {
        this.isValid = isValid;
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
    private String hospitalNo;
    private String inHospitalDate;
    private String outHospitalDate;
    private String patientCard;
    private String departmentId;
    private String departmentName;
    private String roomId;
    private String roomNo;
    private String bedId;
    private String bedNo;
    private String hospitalId;
    /**
     * 转诊：1 是，2 不是
     */
    private String referral;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }
    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }
    public String getInHospitalDate() {
        return inHospitalDate;
    }
    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }
    public String getOutHospitalDate() {
        return outHospitalDate;
    }
    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }
    public String getPatientCard() {
        return patientCard;
    }
    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
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
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getRoomNo() {
        return roomNo;
    }
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
    public String getBedId() {
        return bedId;
    }
    public void setBedId(String bedId) {
        this.bedId = bedId;
    }
    public String getBedNo() {
        return bedNo;
    }
    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }
    public String getReferral() {
        return referral;
    }
    public void setReferral(String referral) {
        this.referral = referral;
    }

    public Integer getInStatus() {
        return inStatus;
    }

    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
    }
    public Integer getConcernStatus() {
        return concernStatus;
    }
    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }
    public String getChargeClass() {
        return chargeClass;
    }
    public void setChargeClass(String chargeClass) {
        this.chargeClass = chargeClass;
    }
    public String getLabourIntensity() {
        return labourIntensity;
    }
    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity;
    }
    public String getVisitNo() {
        return visitNo;
    }
    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }
    public String getDailyHabitJson() {
        return dailyHabitJson;
    }
    public void setDailyHabitJson(String dailyHabitJson) {
        this.dailyHabitJson = dailyHabitJson;
    }
    public String getDiabetesComplicationJson() {
        return diabetesComplicationJson;
    }
    public void setDiabetesComplicationJson(String diabetesComplicationJson) {
        this.diabetesComplicationJson = diabetesComplicationJson;
    }
    public String getHypoglycemiaJson() {
        return hypoglycemiaJson;
    }
    public void setHypoglycemiaJson(String hypoglycemiaJson) {
        this.hypoglycemiaJson = hypoglycemiaJson;
    }
    public String getEducationSituationJson() {
        return educationSituationJson;
    }
    public void setEducationSituationJson(String educationSituationJson) {
        this.educationSituationJson = educationSituationJson;
    }
    public String getDiabetesSymptomJson() {
        return diabetesSymptomJson;
    }
    public void setDiabetesSymptomJson(String diabetesSymptomJson) {
        this.diabetesSymptomJson = diabetesSymptomJson;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBasicJson() {
        return basicJson;
    }
    public void setBasicJson(String basicJson) {
        this.basicJson = basicJson;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDiabetesDt(int diabetesDt) {
        this.diabetesDt = diabetesDt;
    }

    public int getAge() {
        return age;
    }

    public int getDiabetesDt() {
        return diabetesDt;
    }
}
