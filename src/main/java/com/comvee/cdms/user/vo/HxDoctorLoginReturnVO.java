package com.comvee.cdms.user.vo;

import java.util.Set;

/**
 * @author 林雨堆
 * @date 2019-11-12
 */
public class HxDoctorLoginReturnVO {

    /**
     * 华西用户编号
     */
    private String hxUserId;
    private Object doctor;
    private Set<String> authorityList;

    public void setHxUserId(String hxUserId) {
        this.hxUserId = hxUserId;
    }

    public String getHxUserId() {
        return hxUserId;
    }

    public void setDoctor(Object doctor) {
        this.doctor = doctor;
    }

    public Object getDoctor() {
        return doctor;
    }

    public void setAuthorityList(Set<String> authorityList) {
        this.authorityList = authorityList;
    }

    public Set<String> getAuthorityList() {
        return authorityList;
    }
}
