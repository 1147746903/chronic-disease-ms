package com.comvee.cdms.bloodmonitor.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/5/15
 */
public class ListMonitorPlanTemplateMapperDTO {
    /**
     * 监测方案模板类型 1 web 2 app
     */
    private Integer monitorType;
    private List<String> doctorIdList;
    private Integer planType;
    private Integer permission;
    private String hospitalId;
    /**
     * 适用类型0非妊娠,1妊娠
     */
    private Integer eohType;

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }
}
