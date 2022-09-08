package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class DelPrescriptionDTO implements Serializable {
    /**
     * 主键唯一标示
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 专科医生id
     */
    private String doctorId;
    /**
     * 医院编号
     */
    private String hospitalId;
    /**
     * 唯一标示列表
     */
    private List<String> sidList;

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "DelPrescriptionDTO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", sidList=" + sidList +
                '}';
    }
}
