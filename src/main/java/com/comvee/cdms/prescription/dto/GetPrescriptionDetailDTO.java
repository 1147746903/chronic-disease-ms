package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class GetPrescriptionDetailDTO implements Serializable {
    private String sid;
    /**
     * 管理处方主表id
     */
    private String prescriptionId;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState;
    /**
     * 管理处方模块：1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    private Integer type;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 是否有效，1有效，0无效
     */
    private Integer isValid;
    /**
     * 主键列表
     */
    private List<String> sidList;

    private String hospitalId;

    private Integer eohType;

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

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

    public Integer getSaveState() {
        return saveState;
    }

    public void setSaveState(Integer saveState) {
        this.saveState = saveState;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    @Override
    public String toString() {
        return "SelectPrescriptionDetailDTO{" +
                "sid='" + sid + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", saveState=" + saveState +
                ", type=" + type +
                ", memberId='" + memberId + '\'' +
                ", isValid=" + isValid +
                ", sidList=" + sidList +
                '}';
    }
}
