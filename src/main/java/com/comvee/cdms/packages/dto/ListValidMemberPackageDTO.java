package com.comvee.cdms.packages.dto;

import com.comvee.cdms.packages.cfg.ServiceCode;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public class ListValidMemberPackageDTO {

    private String memberId;
    private String doctorId;
    private List<ServiceCode> codeList;
    private String packageId;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

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

    public List<ServiceCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<ServiceCode> codeList) {
        this.codeList = codeList;
    }
}
