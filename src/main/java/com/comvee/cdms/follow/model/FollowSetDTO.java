package com.comvee.cdms.follow.model;

import java.io.Serializable;

/**
 * 随访参数表
 * @author 林雨堆
 * @date 2018-03-27 10:30
 */
public class FollowSetDTO implements Serializable {

    /**
     * 设置对象 1当前患者 2所有患者
     */
    private String setUser;
    /**
     * 随访方式 1提醒医生随访 2自动下发患者填写
     */
    private String followType;

    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 医生编号
     */
    private String doctorId;

    public String getSetUser() {
        return setUser;
    }

    public void setSetUser(String setUser) {
        this.setUser = setUser;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
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
