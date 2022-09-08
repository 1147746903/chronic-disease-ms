package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2020/3/4 17:17
 */
public class TodayBloodDTO {

    /**
     * 成员id
     */
    private String memberId;
    /**
     * 血糖记录code
     */
    private String paramCode;
    /**
     * 血糖记录值
     */
    private String paramValue;
    /**
     * 记录时间
     */
    private String recordDt;
    /**
     * 血糖等级 1偏低 3-正常 5-偏高
     */
    private String paramLevel;
    /**
     * 部门编号
     */
    private String departmentId;
    /**
     * 在院状态 1：在院 0 非在院
     */
    private Integer inHos;

    /**
     * 开始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String endDt;
    /**
     * 时间节点
     */
    private List<String> paramCodeList;
    /**
     * 是否异常
     */
    private boolean isAbnormal;
    /**
     * 科室编号
     */
    private List<String> departmentIdList;
    /**
     * 是否偏高
     */
    private boolean highAbnormal;
    /**
     * 是否偏低
     */
    private boolean lowAbnormal;
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 大于某个值的血糖（16.7）
     */
    private Double highByValue;
    /**
     * 患者在院状态 1：在院 0 非在院
     */
    private Integer checkinStatus;

    private List<String> memberIdList;

    private Double highValue; //最高血糖测量
    private Double lowValue; //最低血糖测量

    private Integer isLook; //是否已查看

    private String hospitalId; //医院id

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(String paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean abnormal) {
        isAbnormal = abnormal;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public boolean isHighAbnormal() {
        return highAbnormal;
    }

    public void setHighAbnormal(boolean highAbnormal) {
        this.highAbnormal = highAbnormal;
    }

    public boolean isLowAbnormal() {
        return lowAbnormal;
    }

    public void setLowAbnormal(boolean lowAbnormal) {
        this.lowAbnormal = lowAbnormal;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Double getHighByValue() {
        return highByValue;
    }

    public void setHighByValue(Double highByValue) {
        this.highByValue = highByValue;
    }

    public Integer getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(Integer checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Integer getIsLook() {
        return isLook;
    }

    public void setIsLook(Integer isLook) {
        this.isLook = isLook;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "TodayBloodDTO{" +
                "memberId='" + memberId + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", paramLevel='" + paramLevel + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", inHos=" + inHos +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", paramCodeList=" + paramCodeList +
                ", isAbnormal=" + isAbnormal +
                ", departmentIdList=" + departmentIdList +
                ", highAbnormal=" + highAbnormal +
                ", lowAbnormal=" + lowAbnormal +
                ", doctorId='" + doctorId + '\'' +
                ", highByValue=" + highByValue +
                ", checkinStatus=" + checkinStatus +
                ", memberIdList=" + memberIdList +
                ", highValue=" + highValue +
                ", lowValue=" + lowValue +
                ", isLook=" + isLook +
                '}';
    }
}
