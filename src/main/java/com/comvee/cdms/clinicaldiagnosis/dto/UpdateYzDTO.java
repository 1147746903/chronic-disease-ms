package com.comvee.cdms.clinicaldiagnosis.dto;

import javax.validation.constraints.NotEmpty;

public class UpdateYzDTO {


    /**
     * 医嘱id
     * yz_id
     */
    @NotEmpty(message = "医嘱id不能为空")
    private String yzId;

    /**
     * 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他 5 胰岛素泵
     * yz_item_type
     */
    private Integer yzItemType;

    /**
     * 医嘱类型 1 长期医嘱 2 临时医嘱
     * yz_type
     */
    private Integer yzType;

    /**
     * 医嘱项目名称
     * yz_item_name
     */
    private String yzItemName;

    /**
     * 医嘱项目代码
     * yz_item_code
     */
    private String yzItemCode;

    /**
     * 剂量
     * drug_dose
     */
    private String drugDose;

    /**
     * 剂量单位
     * drug_dose_unit
     */
    private String drugDoseUnit;

    /**
     * 剂量单位描述
     * drug_dose_unit_desc
     */
    private String drugDoseUnitDesc;

    /**
     * 频次code
     * drug_freq_code
     */
    private String drugFreqCode;

    /**
     * 频次描述
     * drug_freq_desc
     */
    private String drugFreqDesc;

    /**
     * 疗程代码
     * drug_duration_code
     */
    private String drugDurationCode;

    /**
     * 疗程描述
     * drug_duration_desc
     */
    private String drugDurationDesc;

    /**
     * 规格
     * drug_specification
     */
    private String drugSpecification;

    /**
     * 开始日期
     * start_date
     */
    private String startDate;

    /**
     * 开始时间
     * start_time
     */
    private String startTime;

    /**
     * 结束日期
     * stop_date
     */
    private String stopDate;

    /**
     * 结束时间
     * stop_time
     */
    private String stopTime;

    /**
     * 价格
     * price
     */
    private String price;

    /**
     * 是否皮试 Y 是 N不是
     * skin_test
     */
    private String skinTest;

    /**
     * 是否紧急 Y 是 N不是
     * emergency
     */
    private String emergency;

    /**
     * 主医嘱类型code
     * priority_code
     */
    private String priorityCode;

    /**
     * 用药方式 1 其他 2 皮下注射 2 静脉注射
     * use_drug_way
     */
    private Integer useDrugWay;

    /**
     * 医嘱状态 1 未发送 2 已发送 3 已校对 4 在执行 5 已执行
     * yz_status
     */
    private Integer yzStatus;

    /**
     * 医嘱说明
     * yz_explain
     */
    private String yzExplain;

    /**
     * 开始时间
     * start_dt
     */
    private String startDt;

    /**
     * 结束时间
     * stop_dt
     */
    private String stopDt;

    /**
     * 用药计划
     * use_plan_json
     */
    private String usePlanJson;

    /**
     * 扩展数据， json格式
     * ext_data
     */
    private String extData;

    public String getYzId() {
        return yzId;
    }

    public void setYzId(String yzId) {
        this.yzId = yzId;
    }

    public Integer getYzItemType() {
        return yzItemType;
    }

    public void setYzItemType(Integer yzItemType) {
        this.yzItemType = yzItemType;
    }

    public Integer getYzType() {
        return yzType;
    }

    public void setYzType(Integer yzType) {
        this.yzType = yzType;
    }

    public String getYzItemName() {
        return yzItemName;
    }

    public void setYzItemName(String yzItemName) {
        this.yzItemName = yzItemName;
    }

    public String getYzItemCode() {
        return yzItemCode;
    }

    public void setYzItemCode(String yzItemCode) {
        this.yzItemCode = yzItemCode;
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

    public String getDrugDurationCode() {
        return drugDurationCode;
    }

    public void setDrugDurationCode(String drugDurationCode) {
        this.drugDurationCode = drugDurationCode;
    }

    public String getDrugDurationDesc() {
        return drugDurationDesc;
    }

    public void setDrugDurationDesc(String drugDurationDesc) {
        this.drugDurationDesc = drugDurationDesc;
    }

    public String getDrugSpecification() {
        return drugSpecification;
    }

    public void setDrugSpecification(String drugSpecification) {
        this.drugSpecification = drugSpecification;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSkinTest() {
        return skinTest;
    }

    public void setSkinTest(String skinTest) {
        this.skinTest = skinTest;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public Integer getUseDrugWay() {
        return useDrugWay;
    }

    public void setUseDrugWay(Integer useDrugWay) {
        this.useDrugWay = useDrugWay;
    }

    public Integer getYzStatus() {
        return yzStatus;
    }

    public void setYzStatus(Integer yzStatus) {
        this.yzStatus = yzStatus;
    }

    public String getYzExplain() {
        return yzExplain;
    }

    public void setYzExplain(String yzExplain) {
        this.yzExplain = yzExplain;
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

    public String getUsePlanJson() {
        return usePlanJson;
    }

    public void setUsePlanJson(String usePlanJson) {
        this.usePlanJson = usePlanJson;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }
}
