package com.comvee.cdms.complication.model.po;

/**
 * @author Su
 */
public class ScreeningMachinePO {
    private String sid;

    private String machineSn;

    private String hospitalId;

    private String doctorId;

    private String insertDt;

    private String updateDt;

    private String secretKey;

    private String description;

    private String doctorName;

    private String hospitalName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn == null ? null : machineSn.trim();
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return "ScreeningMachinePO{" +
                "sid='" + sid + '\'' +
                ", machineSn='" + machineSn + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}