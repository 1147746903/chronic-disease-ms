package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class DelPrescriptionDetailDTO implements Serializable {
    private String sid;
    /**
     * 管理处方主表id
     */
    private String prescriptionId;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 主键列表
     */
    private List<String> sidList;
    /**
     * 管理处方主表列表
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
        return "DelPrescriptionDetailDTO{" +
                "sid='" + sid + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", sidList=" + sidList +
                ", prescriptionIdList=" + prescriptionIdList +
                '}';
    }
}
