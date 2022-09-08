package com.comvee.cdms.member.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_member_in_hospital_log
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class MemberInHospitalLogPO {
    /**
     * sid
     */
    private String sid;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 患者姓名
     * member_name
     */
    private String memberName;

    /**
     * 住院时间
     * in_hospital_date
     */
    private String inHospitalDate;

    /**
     * 出院时间
     * out_hospital_date
     */
    private String outHospitalDate;

    /**
     * 部门名称
     * department_name
     */
    private String departmentName;

    /**
     * 部门id
     * department_id
     */
    private String departmentId;

    /**
     * 病床号
     * bed_no
     */
    private String bedNo;

    /**
     * 是否回访 1回 0未回
     * has_return_visit
     */
    private Integer hasReturnVisit;

    /**
     * 插入时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 修改时间
     * modify_dt
     */
    private String modifyDt;

    /**
     * 是否有效：0无效，1有效
     * is_valid
     */
    private Integer isValid;

    /**
     * 性别：1男，2女
     * sex
     */
    private Integer sex;

    /**
     * 身份证号
     * card_no
     */
    private String cardNo;

    /**
     * 就诊卡号
     * in_hospital_card_no
     */
    private String inHospitalCardNo;

    /**
     * 住院天数
     * in_hospital_day
     */
    private Integer inHospitalDay;

    /**
     * 注意事项
     * matters_needing_attent
     */
    private String mattersNeedingAttent;

    /**
     * 入院状态。1住院 0出院
     * in_status
     */
    private Integer inStatus;

    /**
     * 登记号
     * pat_patient_id
     */
    private String patPatientId;

    /**
     * 医生编号
     * doctor_zg_code
     */
    private String doctorZgCode;

    /**
     * adm_no
     */
    private String admNo;

    /**
     * 主要护士
     * main_nurse_desc
     */
    private String mainNurseDesc;

    /**
     * 医生名称
     * doctor_zg
     */
    private String doctorZg;

    /**
     * 护理等级
     * nur_level
     */
    private String nurLevel;

    /**
     * 饮食内容
     * eat_model
     */
    private String eatModel;

    /**
     * 付费类型
     * charge_class
     */
    private String chargeClass;

    /**
     * hospital_no
     */
    private String hospitalNo;

    /**
     * 是否有出院告知书：0无，1有
     * is_notification
     */
    private String isNotification;

    /**
     * 医院编号
     * hospital_id
     */
    private String hospitalId;

    /**
     * 初诊情况
     * initial_diagnosis
     */
    private String initialDiagnosis;

    /**
     * 治疗情况
     * treatment_situation
     */
    private String treatmentSituation;

    /**
     * 出院医嘱JSON
     * out_hospital_advice_json
     */
    private String outHospitalAdviceJson;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.sid
     *
     * @return the value of t_member_in_hospital_log.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.sid
     *
     * @param sid the value for t_member_in_hospital_log.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.member_id
     *
     * @return the value of t_member_in_hospital_log.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.member_id
     *
     * @param memberId the value for t_member_in_hospital_log.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.member_name
     *
     * @return the value of t_member_in_hospital_log.member_name
     *
     * @mbggenerated
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.member_name
     *
     * @param memberName the value for t_member_in_hospital_log.member_name
     *
     * @mbggenerated
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.in_hospital_date
     *
     * @return the value of t_member_in_hospital_log.in_hospital_date
     *
     * @mbggenerated
     */
    public String getInHospitalDate() {
        return inHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.in_hospital_date
     *
     * @param inHospitalDate the value for t_member_in_hospital_log.in_hospital_date
     *
     * @mbggenerated
     */
    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.out_hospital_date
     *
     * @return the value of t_member_in_hospital_log.out_hospital_date
     *
     * @mbggenerated
     */
    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.out_hospital_date
     *
     * @param outHospitalDate the value for t_member_in_hospital_log.out_hospital_date
     *
     * @mbggenerated
     */
    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.department_name
     *
     * @return the value of t_member_in_hospital_log.department_name
     *
     * @mbggenerated
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.department_name
     *
     * @param departmentName the value for t_member_in_hospital_log.department_name
     *
     * @mbggenerated
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.department_id
     *
     * @return the value of t_member_in_hospital_log.department_id
     *
     * @mbggenerated
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.department_id
     *
     * @param departmentId the value for t_member_in_hospital_log.department_id
     *
     * @mbggenerated
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.bed_no
     *
     * @return the value of t_member_in_hospital_log.bed_no
     *
     * @mbggenerated
     */
    public String getBedNo() {
        return bedNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.bed_no
     *
     * @param bedNo the value for t_member_in_hospital_log.bed_no
     *
     * @mbggenerated
     */
    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.has_return_visit
     *
     * @return the value of t_member_in_hospital_log.has_return_visit
     *
     * @mbggenerated
     */
    public Integer getHasReturnVisit() {
        return hasReturnVisit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.has_return_visit
     *
     * @param hasReturnVisit the value for t_member_in_hospital_log.has_return_visit
     *
     * @mbggenerated
     */
    public void setHasReturnVisit(Integer hasReturnVisit) {
        this.hasReturnVisit = hasReturnVisit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.insert_dt
     *
     * @return the value of t_member_in_hospital_log.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.insert_dt
     *
     * @param insertDt the value for t_member_in_hospital_log.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.modify_dt
     *
     * @return the value of t_member_in_hospital_log.modify_dt
     *
     * @mbggenerated
     */
    public String getModifyDt() {
        return modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.modify_dt
     *
     * @param modifyDt the value for t_member_in_hospital_log.modify_dt
     *
     * @mbggenerated
     */
    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.is_valid
     *
     * @return the value of t_member_in_hospital_log.is_valid
     *
     * @mbggenerated
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.is_valid
     *
     * @param isValid the value for t_member_in_hospital_log.is_valid
     *
     * @mbggenerated
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.sex
     *
     * @return the value of t_member_in_hospital_log.sex
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.sex
     *
     * @param sex the value for t_member_in_hospital_log.sex
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.card_no
     *
     * @return the value of t_member_in_hospital_log.card_no
     *
     * @mbggenerated
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.card_no
     *
     * @param cardNo the value for t_member_in_hospital_log.card_no
     *
     * @mbggenerated
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.in_hospital_card_no
     *
     * @return the value of t_member_in_hospital_log.in_hospital_card_no
     *
     * @mbggenerated
     */
    public String getInHospitalCardNo() {
        return inHospitalCardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.in_hospital_card_no
     *
     * @param inHospitalCardNo the value for t_member_in_hospital_log.in_hospital_card_no
     *
     * @mbggenerated
     */
    public void setInHospitalCardNo(String inHospitalCardNo) {
        this.inHospitalCardNo = inHospitalCardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.in_hospital_day
     *
     * @return the value of t_member_in_hospital_log.in_hospital_day
     *
     * @mbggenerated
     */
    public Integer getInHospitalDay() {
        return inHospitalDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.in_hospital_day
     *
     * @param inHospitalDay the value for t_member_in_hospital_log.in_hospital_day
     *
     * @mbggenerated
     */
    public void setInHospitalDay(Integer inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.matters_needing_attent
     *
     * @return the value of t_member_in_hospital_log.matters_needing_attent
     *
     * @mbggenerated
     */
    public String getMattersNeedingAttent() {
        return mattersNeedingAttent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.matters_needing_attent
     *
     * @param mattersNeedingAttent the value for t_member_in_hospital_log.matters_needing_attent
     *
     * @mbggenerated
     */
    public void setMattersNeedingAttent(String mattersNeedingAttent) {
        this.mattersNeedingAttent = mattersNeedingAttent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.in_status
     *
     * @return the value of t_member_in_hospital_log.in_status
     *
     * @mbggenerated
     */
    public Integer getInStatus() {
        return inStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.in_status
     *
     * @param inStatus the value for t_member_in_hospital_log.in_status
     *
     * @mbggenerated
     */
    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.pat_patient_id
     *
     * @return the value of t_member_in_hospital_log.pat_patient_id
     *
     * @mbggenerated
     */
    public String getPatPatientId() {
        return patPatientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.pat_patient_id
     *
     * @param patPatientId the value for t_member_in_hospital_log.pat_patient_id
     *
     * @mbggenerated
     */
    public void setPatPatientId(String patPatientId) {
        this.patPatientId = patPatientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.doctor_zg_code
     *
     * @return the value of t_member_in_hospital_log.doctor_zg_code
     *
     * @mbggenerated
     */
    public String getDoctorZgCode() {
        return doctorZgCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.doctor_zg_code
     *
     * @param doctorZgCode the value for t_member_in_hospital_log.doctor_zg_code
     *
     * @mbggenerated
     */
    public void setDoctorZgCode(String doctorZgCode) {
        this.doctorZgCode = doctorZgCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.adm_no
     *
     * @return the value of t_member_in_hospital_log.adm_no
     *
     * @mbggenerated
     */
    public String getAdmNo() {
        return admNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.adm_no
     *
     * @param admNo the value for t_member_in_hospital_log.adm_no
     *
     * @mbggenerated
     */
    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.main_nurse_desc
     *
     * @return the value of t_member_in_hospital_log.main_nurse_desc
     *
     * @mbggenerated
     */
    public String getMainNurseDesc() {
        return mainNurseDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.main_nurse_desc
     *
     * @param mainNurseDesc the value for t_member_in_hospital_log.main_nurse_desc
     *
     * @mbggenerated
     */
    public void setMainNurseDesc(String mainNurseDesc) {
        this.mainNurseDesc = mainNurseDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.doctor_zg
     *
     * @return the value of t_member_in_hospital_log.doctor_zg
     *
     * @mbggenerated
     */
    public String getDoctorZg() {
        return doctorZg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.doctor_zg
     *
     * @param doctorZg the value for t_member_in_hospital_log.doctor_zg
     *
     * @mbggenerated
     */
    public void setDoctorZg(String doctorZg) {
        this.doctorZg = doctorZg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.nur_level
     *
     * @return the value of t_member_in_hospital_log.nur_level
     *
     * @mbggenerated
     */
    public String getNurLevel() {
        return nurLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.nur_level
     *
     * @param nurLevel the value for t_member_in_hospital_log.nur_level
     *
     * @mbggenerated
     */
    public void setNurLevel(String nurLevel) {
        this.nurLevel = nurLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.eat_model
     *
     * @return the value of t_member_in_hospital_log.eat_model
     *
     * @mbggenerated
     */
    public String getEatModel() {
        return eatModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.eat_model
     *
     * @param eatModel the value for t_member_in_hospital_log.eat_model
     *
     * @mbggenerated
     */
    public void setEatModel(String eatModel) {
        this.eatModel = eatModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.charge_class
     *
     * @return the value of t_member_in_hospital_log.charge_class
     *
     * @mbggenerated
     */
    public String getChargeClass() {
        return chargeClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.charge_class
     *
     * @param chargeClass the value for t_member_in_hospital_log.charge_class
     *
     * @mbggenerated
     */
    public void setChargeClass(String chargeClass) {
        this.chargeClass = chargeClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.hospital_no
     *
     * @return the value of t_member_in_hospital_log.hospital_no
     *
     * @mbggenerated
     */
    public String getHospitalNo() {
        return hospitalNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.hospital_no
     *
     * @param hospitalNo the value for t_member_in_hospital_log.hospital_no
     *
     * @mbggenerated
     */
    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.is_notification
     *
     * @return the value of t_member_in_hospital_log.is_notification
     *
     * @mbggenerated
     */
    public String getIsNotification() {
        return isNotification;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.is_notification
     *
     * @param isNotification the value for t_member_in_hospital_log.is_notification
     *
     * @mbggenerated
     */
    public void setIsNotification(String isNotification) {
        this.isNotification = isNotification;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.hospital_id
     *
     * @return the value of t_member_in_hospital_log.hospital_id
     *
     * @mbggenerated
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.hospital_id
     *
     * @param hospitalId the value for t_member_in_hospital_log.hospital_id
     *
     * @mbggenerated
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.initial_diagnosis
     *
     * @return the value of t_member_in_hospital_log.initial_diagnosis
     *
     * @mbggenerated
     */
    public String getInitialDiagnosis() {
        return initialDiagnosis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.initial_diagnosis
     *
     * @param initialDiagnosis the value for t_member_in_hospital_log.initial_diagnosis
     *
     * @mbggenerated
     */
    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.treatment_situation
     *
     * @return the value of t_member_in_hospital_log.treatment_situation
     *
     * @mbggenerated
     */
    public String getTreatmentSituation() {
        return treatmentSituation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.treatment_situation
     *
     * @param treatmentSituation the value for t_member_in_hospital_log.treatment_situation
     *
     * @mbggenerated
     */
    public void setTreatmentSituation(String treatmentSituation) {
        this.treatmentSituation = treatmentSituation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_in_hospital_log.out_hospital_advice_json
     *
     * @return the value of t_member_in_hospital_log.out_hospital_advice_json
     *
     * @mbggenerated
     */
    public String getOutHospitalAdviceJson() {
        return outHospitalAdviceJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_in_hospital_log.out_hospital_advice_json
     *
     * @param outHospitalAdviceJson the value for t_member_in_hospital_log.out_hospital_advice_json
     *
     * @mbggenerated
     */
    public void setOutHospitalAdviceJson(String outHospitalAdviceJson) {
        this.outHospitalAdviceJson = outHospitalAdviceJson;
    }

    @Override
    public String toString() {
        return "MemberInHospitalLogPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", inHospitalDate='" + inHospitalDate + '\'' +
                ", outHospitalDate='" + outHospitalDate + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", hasReturnVisit=" + hasReturnVisit +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isValid=" + isValid +
                ", sex=" + sex +
                ", cardNo='" + cardNo + '\'' +
                ", inHospitalCardNo='" + inHospitalCardNo + '\'' +
                ", inHospitalDay=" + inHospitalDay +
                ", mattersNeedingAttent='" + mattersNeedingAttent + '\'' +
                ", inStatus=" + inStatus +
                ", patPatientId='" + patPatientId + '\'' +
                ", doctorZgCode='" + doctorZgCode + '\'' +
                ", admNo='" + admNo + '\'' +
                ", mainNurseDesc='" + mainNurseDesc + '\'' +
                ", doctorZg='" + doctorZg + '\'' +
                ", nurLevel='" + nurLevel + '\'' +
                ", eatModel='" + eatModel + '\'' +
                ", chargeClass='" + chargeClass + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", isNotification='" + isNotification + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", initialDiagnosis='" + initialDiagnosis + '\'' +
                ", treatmentSituation='" + treatmentSituation + '\'' +
                ", outHospitalAdviceJson='" + outHospitalAdviceJson + '\'' +
                '}';
    }
}