package com.comvee.cdms.doctor.po;

public class DoctorServiceSettingPO {

    private String sid;
    private String doctorId; //医生id
    private String hospitalId; //医院id
    private Double bloodSugarMax; //最高血糖
    private Double bloodSugarMin; //最低血糖
    private Integer isRemind; //是否提醒 1:是 0:否
    private String insertDt; //插入时间
    private String modifyDt; //修改时间
    private Integer isValid; //是否有效 1:有效 0:无效

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Double getBloodSugarMax() {
        return bloodSugarMax;
    }

    public void setBloodSugarMax(Double bloodSugarMax) {
        this.bloodSugarMax = bloodSugarMax;
    }

    public Double getBloodSugarMin() {
        return bloodSugarMin;
    }

    public void setBloodSugarMin(Double bloodSugarMin) {
        this.bloodSugarMin = bloodSugarMin;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
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

    @Override
    public String toString() {
        return "DoctorServiceSettingPO{" +
                "sid='" + sid + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", bloodSugarMax='" + bloodSugarMax + '\'' +
                ", bloodSugarMin='" + bloodSugarMin + '\'' +
                ", isRemind=" + isRemind +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
