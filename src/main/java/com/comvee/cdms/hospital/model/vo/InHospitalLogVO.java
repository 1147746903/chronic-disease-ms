package com.comvee.cdms.hospital.model.vo;

import java.io.Serializable;

public class InHospitalLogVO implements Serializable {
    private String sid;
    private String memberId;
    private String memberName;
    /**
     * 入院时间
     */
    private String inHospitalDate;
    /**
     * 出院时间
     */
    private String outHospitalDate;
    /**
     * 初诊情况
     */
    private String initialDiagnosis;
    /**
     * 科室名称
     */
    private String departmentName;
    private String departmentId;
    /**
     * 床号
     */
    private String bedNo;
    /**
     * 治疗情况
     */
    private String treatmentSituation;
    /**
     * 是否回访 1回 0未回
     */
    private Integer hasReturnVisit;
    private String insertDt;
    private String modifyDt;
    private Integer isValid;
    /**
     * 入院状态。1住院 0出院
     */
    private Integer inStatus;
    /**
     * 出院医嘱JSON
     */
    private String outHospitalAdviceJson;
    /**
     * 登记号
     */
    private String patPatientId;
    /**
     * 医生编号
     */
    private String doctorZgCode;
    /**
     * 就诊号
     */
    private String admNo;
    /**
     * 主要护士信息
     */
    private String mainNurseDesc;
    /**
     * 医生名称
     */
    private String doctorZg;
    /**
     * 护理等级
     */
    private String nurLevel;
    /**
     * 饮食类型
     */
    private String eatModel;
    /**
     * 付费类型
     */
    private String chargeClass;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 身份证号
     */
    private String cardNo;
    /**
     * 就诊卡号
     */
    private String inHospitalCardNo;
    /**
     * 入院天数
     */
    private Integer inHospitalDay;
    /**
     * 注意事项
     */
    private String mattersNeedingAttent;
    /**
     * t_member表的患者手机号
     */
    private String mobilePhone;
    /**
     * 是否含有出院告知书
     */
    private Integer isNotification;

    private String hospitalId;

    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getInitialDiagnosis() {
        return initialDiagnosis;
    }

    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getTreatmentSituation() {
        return treatmentSituation;
    }

    public void setTreatmentSituation(String treatmentSituation) {
        this.treatmentSituation = treatmentSituation;
    }

    public Integer getHasReturnVisit() {
        return hasReturnVisit;
    }

    public void setHasReturnVisit(Integer hasReturnVisit) {
        this.hasReturnVisit = hasReturnVisit;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getInStatus() {
        return inStatus;
    }

    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
    }

    public String getOutHospitalAdviceJson() {
        return outHospitalAdviceJson;
    }

    public void setOutHospitalAdviceJson(String outHospitalAdviceJson) {
        this.outHospitalAdviceJson = outHospitalAdviceJson;
    }

    public String getPatPatientId() {
        return patPatientId;
    }

    public void setPatPatientId(String patPatientId) {
        this.patPatientId = patPatientId;
    }

    public String getDoctorZgCode() {
        return doctorZgCode;
    }

    public void setDoctorZgCode(String doctorZgCode) {
        this.doctorZgCode = doctorZgCode;
    }

    public String getAdmNo() {
        return admNo;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public String getMainNurseDesc() {
        return mainNurseDesc;
    }

    public void setMainNurseDesc(String mainNurseDesc) {
        this.mainNurseDesc = mainNurseDesc;
    }

    public String getDoctorZg() {
        return doctorZg;
    }

    public void setDoctorZg(String doctorZg) {
        this.doctorZg = doctorZg;
    }

    public String getNurLevel() {
        return nurLevel;
    }

    public void setNurLevel(String nurLevel) {
        this.nurLevel = nurLevel;
    }

    public String getEatModel() {
        return eatModel;
    }

    public void setEatModel(String eatModel) {
        this.eatModel = eatModel;
    }

    public String getChargeClass() {
        return chargeClass;
    }

    public void setChargeClass(String chargeClass) {
        this.chargeClass = chargeClass;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getInHospitalCardNo() {
        return inHospitalCardNo;
    }

    public void setInHospitalCardNo(String inHospitalCardNo) {
        this.inHospitalCardNo = inHospitalCardNo;
    }

    public Integer getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(Integer inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    public String getMattersNeedingAttent() {
        return mattersNeedingAttent;
    }

    public void setMattersNeedingAttent(String mattersNeedingAttent) {
        this.mattersNeedingAttent = mattersNeedingAttent;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(Integer isNotification) {
        this.isNotification = isNotification;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
