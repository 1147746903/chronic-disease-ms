package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class GetPrescriptionLogDTO implements Serializable {
    /**
     * 主键
     */
    private String sid;
    /**
     * 管理处方主表id
     */
    private String prescriptionId;
    /**
     * 操作人编号
     */
    private String doctorId;
    /**
     * 操作人编号列表
     */
    private List<String> doctorIdList;
    /**
     * 主键列表
     */
    private List<String> sidList;
    /**
     * 管理处方主表id列表
     */
    private List<String> prescriptionIdList;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public List<String> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<String> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }

    @Override
    public String toString() {
        return "GetPrescriptionHistoryDTO{" +
                "sid='" + sid + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorIdList=" + doctorIdList +
                ", sidList=" + sidList +
                ", prescriptionIdList=" + prescriptionIdList +
                '}';
    }
}
