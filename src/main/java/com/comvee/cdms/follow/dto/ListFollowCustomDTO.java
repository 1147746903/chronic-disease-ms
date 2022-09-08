package com.comvee.cdms.follow.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public class ListFollowCustomDTO {

    private String memberId;
    private String doctorId;
    private Integer followStatus;
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
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

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    @Override
    public String toString() {
        return "ListFollowCustomDTO{" +
                "memberId='" + memberId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", followStatus=" + followStatus +
                '}';
    }
}
