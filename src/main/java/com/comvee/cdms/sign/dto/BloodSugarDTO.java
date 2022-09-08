package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2020/3/4 15:14
 */
public class BloodSugarDTO {

    /**
     * 科室编号
     */
    private String departmentId;
    private String departmentIdStr;
    /***筛选条件***/
    private List<String> departmentIdList;
    /**
     * 血糖级别 1 偏低 3 正常 5 偏高 1,2 偏低&偏高 1,3 偏低&正常 3,5 偏高&正常 1,3,5 偏低&偏高&正常
     */
    private String paramLevel;
    private String startDt;
    private String endDt;
    private String doctorId;
    /**
     * 是否住院记录 0否 1是
     */
    private Integer inHos;

    /**
     * 医院id
     */
    private String hospitalId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentIdStr() {
        return departmentIdStr;
    }

    public void setDepartmentIdStr(String departmentIdStr) {
        this.departmentIdStr = departmentIdStr;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
