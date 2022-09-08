package com.comvee.cdms.clinicaldiagnosis.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileUseDrugDetailVO implements Serializable {
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
     * 药品类型
     */
     private String drugType; 
    /**
     * 药品名称通用
     */
     private String drugNameA; 
    /**
     * 药品名称商用
     */
     private String drugNameB; 
    /**
     * 用法
     */
     private String useFun; 
    /**
     * 用量
     */
     private String useCount; 
    /**
     * 使用频次
     */
     private String useFreq; 
    /**
     * 使用日期
     */
     private String useDate; 
    /**
     * 单价
     */
     private String price; 
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

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugNameA() {
        return drugNameA;
    }

    public void setDrugNameA(String drugNameA) {
        this.drugNameA = drugNameA;
    }

    public String getDrugNameB() {
        return drugNameB;
    }

    public void setDrugNameB(String drugNameB) {
        this.drugNameB = drugNameB;
    }

    public String getUseFun() {
        return useFun;
    }

    public void setUseFun(String useFun) {
        this.useFun = useFun;
    }

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount;
    }

    public String getUseFreq() {
        return useFreq;
    }

    public void setUseFreq(String useFreq) {
        this.useFreq = useFreq;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
