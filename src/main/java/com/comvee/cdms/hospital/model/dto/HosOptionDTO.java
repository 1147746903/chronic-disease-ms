package com.comvee.cdms.hospital.model.dto;

import java.io.Serializable;

/**
 * 医院选项参数
 */
public class HosOptionDTO implements Serializable {
    private String sid;
    private String hospitalId="0";
    private String doctorId ="0";
    private String optionJson;
    private String explain;
    private Integer isValid=1;
    private Integer pjoType;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getPjoType() {
        return pjoType;
    }

    public void setPjoType(Integer pjoType) {
        this.pjoType = pjoType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
        this.doctorId = doctorId;
    }

    public String getOptionJson() {
        return optionJson;
    }

    public void setOptionJson(String optionJson) {
        this.optionJson = optionJson;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
