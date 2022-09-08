package com.comvee.cdms.doctor.vo;

import java.io.Serializable;

public class DoctorDepartVO implements Serializable {
    /**
     * 科室名称
     */
    private String departName;
    /**
     * 科室编号
     */
    private String departId;
    /**
     * 患者人数
     */
    private Long peopleNum;

    private Integer isVirtual; //'是否是虚拟病区医生 2:虚拟病区 1:普通科室'

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Long getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Long peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    @Override
    public String toString() {
        return "DoctorDepartVO{" +
                "departName='" + departName + '\'' +
                ", departId='" + departId + '\'' +
                ", peopleNum=" + peopleNum +
                ", isVirtual=" + isVirtual +
                '}';
    }
}
