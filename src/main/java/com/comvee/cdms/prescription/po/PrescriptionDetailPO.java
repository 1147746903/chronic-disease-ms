package com.comvee.cdms.prescription.po;

import java.io.Serializable;

public class PrescriptionDetailPO implements Serializable {
    private String sid;
    /**
     * 管理处方主表id
     */
    private String prescriptionId;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState = 0;
    /**
     * 管理处方模块：1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    private Integer type;
    /**
     * 模块内容json
     */
    private String detailJson;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否有效，1有效，0无效
     */
    private Integer isValid = 1;

    private String startMonitor; //开始监测时间
    private String endMonitor; //结束监测时间
    private String week;
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

    public String getDetailJson() {
        return detailJson;
    }

    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getStartMonitor() {
        return startMonitor;
    }

    public void setStartMonitor(String startMonitor) {
        this.startMonitor = startMonitor;
    }

    public String getEndMonitor() {
        return endMonitor;
    }

    public void setEndMonitor(String endMonitor) {
        this.endMonitor = endMonitor;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailPO{" +
                "sid='" + sid + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", saveState=" + saveState +
                ", type=" + type +
                ", detailJson='" + detailJson + '\'' +
                ", memberId='" + memberId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isValid=" + isValid +
                ", startMonitor='" + startMonitor + '\'' +
                ", endMonitor='" + endMonitor + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
