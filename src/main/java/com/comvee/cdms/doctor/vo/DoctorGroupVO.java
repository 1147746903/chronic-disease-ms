package com.comvee.cdms.doctor.vo;

/**
 *
 * @author 李左河
 * @date 2018/7/25 9:22
 *
 */
public class DoctorGroupVO {

    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 领导医生id
     */
    private String doctorId;

    /**
     * 人数
     */
    private Long peopleNumber;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    public Long getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Long peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}

