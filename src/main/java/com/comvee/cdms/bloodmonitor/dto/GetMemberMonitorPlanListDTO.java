package com.comvee.cdms.bloodmonitor.dto;

/**
 * @Author linr
 * @Date 2021/8/2
 */
public class GetMemberMonitorPlanListDTO {
    private String departmentId;
    private String doctorId;
    private String recordDt;
    private String keyword;

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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
