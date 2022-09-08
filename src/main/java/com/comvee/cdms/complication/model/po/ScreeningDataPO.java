package com.comvee.cdms.complication.model.po;

/**
 * @author wyc
 * @date 2019/4/22 16:27
 */
public class ScreeningDataPO {
    private String screeningId;
    private String patientName;
    private String idCard;
    private Integer sex;
    private Integer age;
    private String screeningDt;
    private String leftVpt;
    private String rightVpt;
    private String vptDataJson;
    private String leftAbi;
    private String rightAbi;
    private String abiDataJson;
    private String modules;
    private String insertDt;
    private String updateDt;
    private String doctorId;
    private String memberId;
    private Integer valid;

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getScreeningDt() {
        return screeningDt;
    }

    public void setScreeningDt(String screeningDt) {
        this.screeningDt = screeningDt;
    }

    public String getLeftVpt() {
        return leftVpt;
    }

    public void setLeftVpt(String leftVpt) {
        this.leftVpt = leftVpt;
    }

    public String getRightVpt() {
        return rightVpt;
    }

    public void setRightVpt(String rightVpt) {
        this.rightVpt = rightVpt;
    }

    public String getVptDataJson() {
        return vptDataJson;
    }

    public void setVptDataJson(String vptDataJson) {
        this.vptDataJson = vptDataJson;
    }

    public String getLeftAbi() {
        return leftAbi;
    }

    public void setLeftAbi(String leftAbi) {
        this.leftAbi = leftAbi;
    }

    public String getRightAbi() {
        return rightAbi;
    }

    public void setRightAbi(String rightAbi) {
        this.rightAbi = rightAbi;
    }

    public String getAbiDataJson() {
        return abiDataJson;
    }

    public void setAbiDataJson(String abiDataJson) {
        this.abiDataJson = abiDataJson;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
