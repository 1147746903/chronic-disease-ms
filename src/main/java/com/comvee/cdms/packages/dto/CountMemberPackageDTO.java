package com.comvee.cdms.packages.dto;

/**
 * @author: suyz
 * @date: 2019/3/26
 */
public class CountMemberPackageDTO {

    private String memberId;
    private String packageId;
    private String doctorId;
    private Integer packageStatus;
    private String startDtMin;
    private String startDtMax;
    private Integer isRead;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public String getStartDtMin() {
        return startDtMin;
    }

    public void setStartDtMin(String startDtMin) {
        this.startDtMin = startDtMin;
    }

    public String getStartDtMax() {
        return startDtMax;
    }

    public void setStartDtMax(String startDtMax) {
        this.startDtMax = startDtMax;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
