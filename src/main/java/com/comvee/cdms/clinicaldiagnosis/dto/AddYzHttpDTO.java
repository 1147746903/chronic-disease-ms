package com.comvee.cdms.clinicaldiagnosis.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AddYzHttpDTO {

    @NotEmpty(message = "visitNo不能为空")
    private String visitNo;
    @NotEmpty(message = "memberId不能为空")
    private String memberId;
    @NotNull(message = "yzItemType不能为空")
    private Integer yzItemType;
    @NotNull(message = "yzType不能为空")
    private Integer yzType;
    @NotEmpty(message = "yzItemName不能为空")
    private String yzItemName;
    @NotEmpty(message = "yzItemCode不能为空")
    private String yzItemCode;
    @NotEmpty(message = "drugDose不能为空")
    private String drugDose;
    @NotEmpty(message = "drugDoseUnit不能为空")
    private String drugDoseUnit;
    @NotEmpty(message = "drugDoseUnitDesc不能为空")
    private String drugDoseUnitDesc;
    @NotEmpty(message = "drugFreqCode不能为空")
    private String drugFreqCode;
    @NotEmpty(message = "drugFreqDesc不能为空")
    private String drugFreqDesc;
    private String drugDurationCode;
    private String drugDurationDesc;
    private String drugSpecification;
    @NotEmpty(message = "startDt不能为空")
    private String startDt;
    private String stopDt;
    private String price;
    private String skinTest;
    private String emergency;
    /**
     * 主医嘱类型code
     */
    private String priorityCode;
    private String usePlanJson;

    @NotNull(message = "useDrugWay不能为空")
    private Integer useDrugWay;
    private String yzExplain;
    private String extData;

    @NotNull(message = "recordOrigin不能为空")
    private Integer recordOrigin;

    /**
     * 业务外键id
     */
    @NotEmpty(message = "foreignId不能为空")
    private String foreignId;
    @NotEmpty(message = "executiveDepartmentsName不能为空")
    private String executiveDepartmentsName; //执行科室名称

    /**
     * 卡号（住院卡号/就诊卡号）
     */
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(Integer recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getUsePlanJson() {
        return usePlanJson;
    }

    public void setUsePlanJson(String usePlanJson) {
        this.usePlanJson = usePlanJson;
    }

    public Integer getUseDrugWay() {
        return useDrugWay;
    }

    public void setUseDrugWay(Integer useDrugWay) {
        this.useDrugWay = useDrugWay;
    }

    public String getYzExplain() {
        return yzExplain;
    }

    public void setYzExplain(String yzExplain) {
        this.yzExplain = yzExplain;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getExecutiveDepartmentsName() {
        return executiveDepartmentsName;
    }

    public void setExecutiveDepartmentsName(String executiveDepartmentsName) {
        this.executiveDepartmentsName = executiveDepartmentsName;
    }

    @Override
    public String toString() {
        return "AddYzHttpDTO{" +
                "visitNo='" + visitNo + '\'' +
                ", memberId='" + memberId + '\'' +
                ", yzItemType=" + yzItemType +
                ", yzType=" + yzType +
                ", yzItemName='" + yzItemName + '\'' +
                ", yzItemCode='" + yzItemCode + '\'' +
                ", drugDose='" + drugDose + '\'' +
                ", drugDoseUnit='" + drugDoseUnit + '\'' +
                ", drugDoseUnitDesc='" + drugDoseUnitDesc + '\'' +
                ", drugFreqCode='" + drugFreqCode + '\'' +
                ", drugFreqDesc='" + drugFreqDesc + '\'' +
                ", drugDurationCode='" + drugDurationCode + '\'' +
                ", drugDurationDesc='" + drugDurationDesc + '\'' +
                ", drugSpecification='" + drugSpecification + '\'' +
                ", startDt='" + startDt + '\'' +
                ", stopDt='" + stopDt + '\'' +
                ", price='" + price + '\'' +
                ", skinTest='" + skinTest + '\'' +
                ", emergency='" + emergency + '\'' +
                ", priorityCode='" + priorityCode + '\'' +
                ", usePlanJson='" + usePlanJson + '\'' +
                ", useDrugWay=" + useDrugWay +
                ", yzExplain='" + yzExplain + '\'' +
                ", extData='" + extData + '\'' +
                ", recordOrigin=" + recordOrigin +
                ", foreignId='" + foreignId + '\'' +
                ", executiveDepartmentsName='" + executiveDepartmentsName + '\'' +
                '}';
    }
}
