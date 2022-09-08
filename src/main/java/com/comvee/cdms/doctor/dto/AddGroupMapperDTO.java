package com.comvee.cdms.doctor.dto;

import java.io.Serializable;

/**
 * @author 李左河
 * @date 2018/7/25 9:29.
 */
public class AddGroupMapperDTO implements Serializable{

    private String groupName;

    private String doctorId;

    private String groupId;

    private String sort;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
