package com.comvee.cdms.department.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_department
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddDepartmentDTO {

    /**
     * 科室名称
     * department_name
     */
    @NotEmpty
    private String departmentName;

    /**
     * 医院id
     * hospital_id
     */
    @NotEmpty
    private String hospitalId;

    /**
     * 是否是虚拟病区 1:普通科室 2:虚拟病区
     */
    private Integer isVirtual;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }
}