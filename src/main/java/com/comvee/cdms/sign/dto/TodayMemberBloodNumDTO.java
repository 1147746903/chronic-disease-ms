package com.comvee.cdms.sign.dto;

/**
 * @author wyc
 * @date 2020/3/5 14:24
 */
public class TodayMemberBloodNumDTO {

    private String doctorId;

    private String hospitalId;

    /**
     * 是否有医患关系 false无
     */
    private boolean  joinDocMember;

    private Integer inHos;

    private Integer   virtualWard; //虚拟病区权限


    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public boolean getJoinDocMember() {
        return joinDocMember;
    }

    public void setJoinDocMember(boolean joinDocMember) {
        this.joinDocMember = joinDocMember;
    }

    public boolean isJoinDocMember() {
        return joinDocMember;
    }

    public Integer getVirtualWard() {
        return virtualWard;
    }

    public void setVirtualWard(Integer virtualWard) {
        this.virtualWard = virtualWard;
    }
}
