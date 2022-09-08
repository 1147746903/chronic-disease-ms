package com.comvee.cdms.packages.dto;

import com.comvee.cdms.packages.cfg.ServiceCode;

/**
 * @author: suyz
 * @date: 2018/11/5
 */
public class UsePackageServiceDTO {

    private String memberId;
    private String doctorId;
    private ServiceCode serviceCode;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }
}
