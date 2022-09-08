package com.comvee.cdms.packages.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/11
 */
public class ListMemberPackageDTO implements Serializable{

    private String memberId;
    private String doctorId;
    private String keyword;
    private Integer packageStatus;
    private List<String> doctorList;
    private Integer isRead;
    private String hospitalId;

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }
}
