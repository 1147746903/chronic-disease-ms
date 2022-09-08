package com.comvee.cdms.doctor.dto;

public class AddSettingDTO {

    private Integer isRemind; //是否提醒
//    @NotEmpty(message = "最高血糖值不能为空！")
    private Double bloodSugarMax; //最高血糖值
//    @NotEmpty(message = "最低血糖值不能为空！")
    private Double bloodSugarMin; //最低血糖值

    private String doctorId; //医生id
    private String hospitalId; //医院id

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
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

    @Override
    public String toString() {
        return "AddSettingDTO{" +
                "isRemind=" + isRemind +
                ", bloodSugarMax=" + bloodSugarMax +
                ", bloodSugarMin=" + bloodSugarMin +
                ", doctorId='" + doctorId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
