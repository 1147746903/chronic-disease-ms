package com.comvee.cdms.doctor.vo;

import com.comvee.cdms.doctor.po.DoctorPO;
//import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
public class DoctorAndApplyInfoVO {

    private DoctorPO doctor;
    private Integer bindStatus;

    public DoctorPO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorPO doctor) {
        this.doctor = doctor;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }
}
