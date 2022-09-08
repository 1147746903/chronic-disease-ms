package com.comvee.cdms.prescription.bo;

import java.io.Serializable;
import java.util.List;

public class ApiMemberBO implements Serializable {

    private String memberId;
    private String memberName;
    private String picUrl;
    private String mobilePhone;
    private String sex;
    private String birthday;
    private String idCard;
    private String profession;
    private String diabetesType;
    private String diabetesDate;
    private Integer isValid;
    private String insertDt;
    private String modifyDt;
    private String height;
    private String weight;
    private String bmi;
    private String cost;
    private String bloodPressure;
    private String memberInfo;
    /**
     * 是否住院
     */
    private Integer inHos;
    /**
     * 登记号
     */
    private String patPatientId;
    private String hospitalNo;
    /**
     * 住院号
     */
    private String referral;
    /**
     * 就诊卡号
     */
    private String patientCard;
    private String address;
    /**
     * 劳动强度
     */
    private String labourIntensity;
    /**
     * 就诊号
     */
    private String visitNo;

    /**筛选条件**/
    /**
     * 患者id List
     */
    private List<String> memberIds;
    /**end**/

    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     */
    private String chd;

    /**
     * 是否有糖尿病 1:是 2:否
     */
    private String isDiabetes;

    /**
     * 高血压:1有、2无
     * essential_hyp
     */
    private String essentialHyp;

    /**
     * 高血压分级1 一级支持 2 二级支持 3 三级支持 0其他
     */
    private String gxyLevel;

    /**
     * 高血压分层 1 低危 2 中危 3 高危
     */
    private String gxyLayer;

    private String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getPatPatientId() {
        return patPatientId;
    }

    public void setPatPatientId(String patPatientId) {
        this.patPatientId = patPatientId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
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

    public String getGxyLevel() {
        return gxyLevel;
    }

    public void setGxyLevel(String gxyLevel) {
        this.gxyLevel = gxyLevel;
    }

    public String getGxyLayer() {
        return gxyLayer;
    }

    public void setGxyLayer(String gxyLayer) {
        this.gxyLayer = gxyLayer;
    }
}
