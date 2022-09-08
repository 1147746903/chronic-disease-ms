package com.comvee.cdms.packages.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class SumPackagePriceDTO implements Serializable{

    private static final long serialVersionUID = -3218484409443770188L;

    private String doctorId;

    private Integer packageStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }
}
