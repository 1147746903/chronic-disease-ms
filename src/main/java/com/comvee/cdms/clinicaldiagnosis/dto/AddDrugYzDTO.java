package com.comvee.cdms.clinicaldiagnosis.dto;

import java.io.Serializable;

public class AddDrugYzDTO implements Serializable {
    /**
     * 用药类型 1 长期 2 临时
     */
    private Integer type;
    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 药品编号
     */
    private String drugCode;
    /**
     * 药品剂量（服用剂量）
     */
    private String drugDose;
    /**
     * 药品剂量单位
     */
    private String drugDoseUnit;
    /**
     * 药品剂量单位中文（例如：片，颗，粒）
     */
    private String drugDoseUnitDesc;
    /**
     * 频次代码
     */
    private String drugFreqCode;
    /**
     * 频次说明
     */
    private String drugFreqDesc;
    /**
     * 药品规格
     */
    private String drugSpecification;
    /**
     * 疗程代码
     */
    private String drugDurationCode;
    /**
     * 疗程说明
     */
    private String drugDurationDesc;
    /**
     * 药品价格
     */
    private String price;
    /**
     * 是否紧急 Y 是 N不是
     */
    private String emergency = "N";
    /**
     * 是否需要皮试 Y 是 N不是
     */
    private String skinTest = "N";
    /**
     * 开始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String stopDt;
    /**
     * 用药计划
     */
    private String usePlanJson;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugDose() {
        return drugDose;
    }

    public void setDrugDose(String drugDose) {
        this.drugDose = drugDose;
    }

    public String getDrugDoseUnit() {
        return drugDoseUnit;
    }

    public void setDrugDoseUnit(String drugDoseUnit) {
        this.drugDoseUnit = drugDoseUnit;
    }

    public String getDrugDoseUnitDesc() {
        return drugDoseUnitDesc;
    }

    public void setDrugDoseUnitDesc(String drugDoseUnitDesc) {
        this.drugDoseUnitDesc = drugDoseUnitDesc;
    }

    public String getDrugFreqCode() {
        return drugFreqCode;
    }

    public void setDrugFreqCode(String drugFreqCode) {
        this.drugFreqCode = drugFreqCode;
    }

    public String getDrugFreqDesc() {
        return drugFreqDesc;
    }

    public void setDrugFreqDesc(String drugFreqDesc) {
        this.drugFreqDesc = drugFreqDesc;
    }

    public String getDrugSpecification() {
        return drugSpecification;
    }

    public void setDrugSpecification(String drugSpecification) {
        this.drugSpecification = drugSpecification;
    }

    public String getDrugDurationCode() {
        return drugDurationCode;
    }

    public void setDrugDurationCode(String drugDurationCode) {
        this.drugDurationCode = drugDurationCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getSkinTest() {
        return skinTest;
    }

    public void setSkinTest(String skinTest) {
        this.skinTest = skinTest;
    }

    public String getUsePlanJson() {
        return usePlanJson;
    }

    public void setUsePlanJson(String usePlanJson) {
        this.usePlanJson = usePlanJson;
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

    public String getDrugDurationDesc() {
        return drugDurationDesc;
    }

    public void setDrugDurationDesc(String drugDurationDesc) {
        this.drugDurationDesc = drugDurationDesc;
    }
}
