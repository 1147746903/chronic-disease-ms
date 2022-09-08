package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @Author linr
 * @Date 2021/7/29
 */
public class ListMemberBloodSugarHospitalDTO {
    private String departmentId;  //科室id
    private String doctorId;  //医生id
    private String startDt;
    private String endDt;
    private String keyword;
    private List<String> paramCodeList;
    private String paramCode;
    private Integer warnType; //血糖预警类型 1高2低
    private String hospitalId;
    /**
     * 科室idList
     */
    private List<String> departmentIdList;


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

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public Integer getWarnType() {
        return warnType;
    }

    public void setWarnType(Integer warnType) {
        this.warnType = warnType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }
}
