package com.comvee.cdms.member.vo;

/**
 * @Author linr
 * @Date 2021/8/4
 */
public class DySugarDataListVO {

    private String memberId;
    private String memberName;

    private String hospitalNo;
    private String doctorName;
    private String sensorNo;
    private String monitorDt;
    private String avgNum;//平均血糖
    private String ghb;//预估糖化
    private String coefficientOfVariation;//变异系数
    private String customLessThanRatio;//<3.0mmol/L的时间占比
    private Double awiTimeRateOfLow;//低于目标范围时间占比
    private Double awiTimeRateOfNormal;//达标时间占比
    private Double awiTimeRateOfHigh;//高于目标范围时间占比
    private Double customGreaterThanRatio;//>13.0mmol/L的时间占比
    private Integer sensorValid;//1探头有效
    private Integer outHospitalDt;//出院时间
    private String doctorId;
    private String departmentId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getMonitorDt() {
        return monitorDt;
    }

    public void setMonitorDt(String monitorDt) {
        this.monitorDt = monitorDt;
    }

    public String getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(String avgNum) {
        this.avgNum = avgNum;
    }

    public String getGhb() {
        return ghb;
    }

    public void setGhb(String ghb) {
        this.ghb = ghb;
    }

    public String getCoefficientOfVariation() {
        return coefficientOfVariation;
    }

    public void setCoefficientOfVariation(String coefficientOfVariation) {
        this.coefficientOfVariation = coefficientOfVariation;
    }

    public String getCustomLessThanRatio() {
        return customLessThanRatio;
    }

    public void setCustomLessThanRatio(String customLessThanRatio) {
        this.customLessThanRatio = customLessThanRatio;
    }

    public Double getAwiTimeRateOfLow() {
        return awiTimeRateOfLow;
    }

    public void setAwiTimeRateOfLow(Double awiTimeRateOfLow) {
        this.awiTimeRateOfLow = awiTimeRateOfLow;
    }

    public Double getAwiTimeRateOfNormal() {
        return awiTimeRateOfNormal;
    }

    public void setAwiTimeRateOfNormal(Double awiTimeRateOfNormal) {
        this.awiTimeRateOfNormal = awiTimeRateOfNormal;
    }

    public Double getAwiTimeRateOfHigh() {
        return awiTimeRateOfHigh;
    }

    public void setAwiTimeRateOfHigh(Double awiTimeRateOfHigh) {
        this.awiTimeRateOfHigh = awiTimeRateOfHigh;
    }

    public Double getCustomGreaterThanRatio() {
        return customGreaterThanRatio;
    }

    public void setCustomGreaterThanRatio(Double customGreaterThanRatio) {
        this.customGreaterThanRatio = customGreaterThanRatio;
    }

    public Integer getSensorValid() {
        return sensorValid;
    }

    public void setSensorValid(Integer sensorValid) {
        this.sensorValid = sensorValid;
    }

    public Integer getOutHospitalDt() {
        return outHospitalDt;
    }

    public void setOutHospitalDt(Integer outHospitalDt) {
        this.outHospitalDt = outHospitalDt;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
