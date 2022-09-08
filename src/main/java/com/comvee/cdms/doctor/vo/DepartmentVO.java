package com.comvee.cdms.doctor.vo;

public class DepartmentVO {

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 科室
     */
    private String departmentName;

    private Integer isVirtual; //是否是虚拟病区 1:是 0:否


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

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    @Override
    public String toString() {
        return "DepartmentVO{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
