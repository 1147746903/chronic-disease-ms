package com.comvee.cdms.department.model.dto;

/**
 * @author: suyz
 * @date: 2019/1/21
 */
public class UpdateDepartmentDTO {

    private String departmentId;
    private String departmentName;
    private Integer doctorNumberIncr;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDoctorNumberIncr() {
        return doctorNumberIncr;
    }

    public void setDoctorNumberIncr(Integer doctorNumberIncr) {
        this.doctorNumberIncr = doctorNumberIncr;
    }
}
