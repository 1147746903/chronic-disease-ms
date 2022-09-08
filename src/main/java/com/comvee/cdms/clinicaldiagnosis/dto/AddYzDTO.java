package com.comvee.cdms.clinicaldiagnosis.dto;

import java.io.Serializable;

public class AddYzDTO implements Serializable {
    private String patientId;
    private String visitNo;
    private String memberId;
    private String yzId;
    private String yzGroupId;
    private Integer yzItemType;
    private Integer yzType;
    private String yzItemName;
    private String yzItemCode;
    private String drugDose;
    private String drugDoseUnit;
    private String drugDoseUnitDesc;
    private String drugFreqCode;
    private String drugFreqDesc;
    private String drugDurationCode;
    private String drugDurationDesc;
    private String drugSpecification;
    private String doctorCode;
    private String doctorName;
    private String startDate;
    private String startTime;
    private String stopDate;
    private String stopTime;
    private String price;
    private String skinTest;
    private String emergency;
    private Integer recordOrigin = 1;
    private String insertDt;
    /**
     * 主医嘱类型code
     */
    private String priorityCode;
    private String usePlanJson;

    /**
     * v6.1.0新增
     */
    private Integer useDrugWay;
    private Integer yzStatus;
    private String yzExplain;
    private String extData;

    private String startDt;
    private String stopDt;

    /**
     * 医院编号
     * hospital_id
     */
    private String hospitalId;

    /**
     * 科室编号
     * department_id
     */
    private String departmentId;

    /**
     * 医生编号
     * doctor_id
     */
    private String doctorId;

    private String foreignId;

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

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public Integer getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(Integer recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public String getUsePlanJson() {
        return usePlanJson;
    }

    public void setUsePlanJson(String usePlanJson) {
        this.usePlanJson = usePlanJson;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
    public String getYzId() {
        return yzId;
    }
    public void setYzId(String yzId) {
        this.yzId = yzId;
    }
    public String getYzGroupId() {
        return yzGroupId;
    }
    public void setYzGroupId(String yzGroupId) {
        this.yzGroupId = yzGroupId;
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
    public String getDoctorCode() {
        return doctorCode;
    }
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
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
        return "AddYzDTO{" +
                "patientId='" + patientId + '\'' +
                ", visitNo='" + visitNo + '\'' +
                ", memberId='" + memberId + '\'' +
                ", yzId='" + yzId + '\'' +
                ", yzGroupId='" + yzGroupId + '\'' +
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
                ", doctorCode='" + doctorCode + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", price='" + price + '\'' +
                ", skinTest='" + skinTest + '\'' +
                ", emergency='" + emergency + '\'' +
                ", recordOrigin=" + recordOrigin +
                ", insertDt='" + insertDt + '\'' +
                ", priorityCode='" + priorityCode + '\'' +
                ", usePlanJson='" + usePlanJson + '\'' +
                ", useDrugWay=" + useDrugWay +
                ", yzStatus=" + yzStatus +
                ", yzExplain='" + yzExplain + '\'' +
                ", extData='" + extData + '\'' +
                ", startDt='" + startDt + '\'' +
                ", stopDt='" + stopDt + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", foreignId='" + foreignId + '\'' +
                '}';
    }
}
