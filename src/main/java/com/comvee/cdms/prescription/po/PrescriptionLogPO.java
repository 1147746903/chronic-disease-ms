package com.comvee.cdms.prescription.po;

import java.io.Serializable;

public class PrescriptionLogPO implements Serializable {

    /**
     * 主键
     */
    private String sid;
    /**
     * 管理处方主表id
     */
    private String prescriptionId;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否有效 1有 0否
     */
    private Integer isValid = 1;
    /**
     * 操作类型 1创建 2编辑(更新，修改) 3下发 4删除
     */
    private Integer operatType;
    /**
     * 操作人编号
     */
    private String doctorId;
    /**
     * 管理处方进度(1待制定，2保存草稿，3完成)
     */
    private Integer schedule;

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

    public Integer getOperatType() {
        return operatType;
    }

    public void setOperatType(Integer operatType) {
        this.operatType = operatType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "PrescriptionHistoryPO{" +
                "sid='" + sid + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isValid=" + isValid +
                ", operatType=" + operatType +
                ", doctorId='" + doctorId + '\'' +
                ", schedule=" + schedule +
                '}';
    }
}
