package com.comvee.cdms.doctor.bo;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/7/25 9:45.
 */
public class DoctorMemberBO {
    @Override
    public String toString() {
        return "DoctorMemberBO{}";
    }

    /**
     * 患者id数组
     */
    private String[] memberIds;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 分组id
     */
    private String groupId;

    private List<String> memberIdList;

    private Long modifyTimestamp;

    public String[] getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String[] memberIds) {
        this.memberIds = memberIds;
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

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public Long getModifyTimestamp() {
        return modifyTimestamp;
    }

    public void setModifyTimestamp(Long modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }
}
