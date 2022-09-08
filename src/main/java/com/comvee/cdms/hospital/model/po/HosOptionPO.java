package com.comvee.cdms.hospital.model.po;

import java.io.Serializable;

/**
 * 医院选项实体
 */
public class HosOptionPO implements Serializable {
    private String sid;
    private String hospitalId="0";
    private String doctorId ="0";
    private String optionJson;
    private String explain;
    private String insertDt;
    private String modifyDt;
    private Integer isValid=1;
    private String pjoType;

    public String getPjoType() {
        return pjoType;
    }

    public void setPjoType(String pjoType) {
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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
}
