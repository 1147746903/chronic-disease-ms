package com.comvee.cdms.prescription.po;

import java.io.Serializable;

public class MemberPrescriptionPO implements Serializable {

    /**
     * 主键唯一标示
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 更新时间
     */
    private String modifyDt;
    /**
     * 专科医生id
     */
    private String doctorId;
    /**
     * 管理处方状态(1待制定 2保存草稿 3已完成)
     */
    private Integer schedule;
    /**
     * 选择的模块 1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    private String module;
    /**
     * 学习周期
     */
    private Integer eduCycle;
    /**
     * 0:糖尿病（非妊娠）自我管理处方 1:妊娠糖尿病自我管理处方
     */
    private Integer eohType;
    /**
     * 下发状态 1已下发 0未下发
     */
    private Integer handDown;

    private String doctorName;

    private String doctorSex;
    private String isValid; //1:有效  0:无效
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getEduCycle() {
        return eduCycle;
    }

    public void setEduCycle(Integer eduCycle) {
        this.eduCycle = eduCycle;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Integer getHandDown() {
        return handDown;
    }

    public void setHandDown(Integer handDown) {
        this.handDown = handDown;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSex() {
        return doctorSex;
    }

    public void setDoctorSex(String doctorSex) {
        this.doctorSex = doctorSex;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "MemberPrescriptionPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", schedule=" + schedule +
                ", module='" + module + '\'' +
                ", eduCycle=" + eduCycle +
                ", eohType=" + eohType +
                ", handDown=" + handDown +
                ", doctorName='" + doctorName + '\'' +
                ", doctorSex='" + doctorSex + '\'' +
                ", isValid='" + isValid + '\'' +
                '}';
    }
}
