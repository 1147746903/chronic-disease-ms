package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2020/3/5 16:52
 */
public class TodayHosMemberBloodDTO {

    /**
     * 入住状态 0-未入住 1-入住
     */
    private String checkinStatus;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 科室id
     */
    private List<String> departmentIdList;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 血糖code
     */
    private String paramCode;
    /**
     * 血糖记录时间
     */
    private String recordDt;
    /**
     * 血糖等级 1偏低 3-正常  5-偏高
     */
    private String paramLevel;
    private String startDt;
    private String endDt;
    private boolean isAbnormal;  //是否异常
    private String departmentIdStr;  //科室id串
    private String doctorId;  //医生id
    private List<String> paramCodeList;

    private Integer isLoadVirtualWard; //是否加载虚拟病区患者数据  1:是 0:否

    private List<String> virtualWardDepartIdList;
    /**
     * 是否加载虚拟病区数据
     */
    private Boolean loadVirtualWard;

    public Boolean getLoadVirtualWard() {
        return loadVirtualWard;
    }

    public void setLoadVirtualWard(Boolean loadVirtualWard) {
        this.loadVirtualWard = loadVirtualWard;
    }

    public List<String> getVirtualWardDepartIdList() {
        return virtualWardDepartIdList;
    }

    public void setVirtualWardDepartIdList(List<String> virtualWardDepartIdList) {
        this.virtualWardDepartIdList = virtualWardDepartIdList;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

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

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean abnormal) {
        isAbnormal = abnormal;
    }

    public String getDepartmentIdStr() {
        return departmentIdStr;
    }

    public void setDepartmentIdStr(String departmentIdStr) {
        this.departmentIdStr = departmentIdStr;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public Integer getIsLoadVirtualWard() {
        return isLoadVirtualWard;
    }

    public void setIsLoadVirtualWard(Integer isLoadVirtualWard) {
        this.isLoadVirtualWard = isLoadVirtualWard;
    }

    @Override
    public String toString() {
        return "TodayHosMemberBloodDTO{" +
                "checkinStatus='" + checkinStatus + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", departmentIdList=" + departmentIdList +
                ", memberId='" + memberId + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", paramLevel='" + paramLevel + '\'' +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", isAbnormal=" + isAbnormal +
                ", departmentIdStr='" + departmentIdStr + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", paramCodeList=" + paramCodeList +
                ", isLoadVirtualWard=" + isLoadVirtualWard +
                '}';
    }
}
