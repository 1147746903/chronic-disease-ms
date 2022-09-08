package com.comvee.cdms.packages.dto;

/**
 * @author wyc
 * @date 2019/5/6 10:18
 */
public class DeleteMemberPackageDTO {
    private String sid;
    private String memberId;
    private String doctorId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
}
