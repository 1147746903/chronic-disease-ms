package com.comvee.cdms.virtualward.model.param;

import java.util.List;

public class ListVirtualWardMemberByMonitorParam {

    private String hospitalId;
    private List<String> departmentIdList;
    private Integer monitor;

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

    public Integer getMonitor() {
        return monitor;
    }

    public void setMonitor(Integer monitor) {
        this.monitor = monitor;
    }
}
