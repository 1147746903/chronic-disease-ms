package com.comvee.cdms.follow.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class CountFollowDTO implements Serializable {

    private static final long serialVersionUID = 3622876943413346649L;

    private String doctorId;

    private Integer followStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }
}
