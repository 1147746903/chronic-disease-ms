package com.comvee.cdms.clinicaldiagnosis.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileDocOrderDetailVO implements Serializable {
    /**
     * 记录唯一编号
     */
     private String logId;
    /**
     * 身份证号
     */
     private String idCard; 
    /**
     * 门诊号(就诊号)
     */
     private String visitNo; 
    /**
     * 住院号
     */
     private String hospitalNo; 
    /**
     * 就诊卡号
     */
     private String patientCard; 
    /**
     * 是否门诊 1是 0否
     */
     private Integer isVisit; 
    /**
     * 患者名称
     */
     private String memberName; 
    /**
     * 日期类型 1长期 2短期
     */
     private Integer dateType; 
    /**
     * 医嘱编号
     */
     private String orderNo; 
    /**
     * 医嘱类别
     */
     private String orderType; 
    /**
     * 医嘱项目
     */
     private String orderProject; 
    /**
     * 开始时间
     */
     private String startDt; 
    /**
     * 结束时间
     */
     private String stopDt; 
    /**
     * 医嘱医生（开具）
     */
     private String orderDoctor; 
    /**
     * 复核人
     */
     private String reviewer; 
    /**
     * 执行人
     */
     private String executor; 
    /**
     * 停嘱医生
     */
     private String stopDoctor; 
    /**
     * 备注
     */
     private String remark; 
    /**
     * 创建时间
     */
     private String insertDt; 
    /**
     * 更新时间
     */
     private String modifyDt;
    private String visitType;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public Integer getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(Integer isVisit) {
        this.isVisit = isVisit;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderProject() {
        return orderProject;
    }

    public void setOrderProject(String orderProject) {
        this.orderProject = orderProject;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStopDt() {
        return stopDt;
    }

    public void setStopDt(String stopDt) {
        this.stopDt = stopDt;
    }

    public String getOrderDoctor() {
        return orderDoctor;
    }

    public void setOrderDoctor(String orderDoctor) {
        this.orderDoctor = orderDoctor;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getStopDoctor() {
        return stopDoctor;
    }

    public void setStopDoctor(String stopDoctor) {
        this.stopDoctor = stopDoctor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitType() {
        return visitType;
    }
}
