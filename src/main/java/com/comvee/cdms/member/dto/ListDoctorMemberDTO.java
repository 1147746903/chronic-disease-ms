package com.comvee.cdms.member.dto;

import java.util.List;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_doctor_member
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class ListDoctorMemberDTO {


    /**
     * 患者编号
     * member_id
     */
    private String memberId;

    /**
     * 医生编号
     * doctor_id
     */
    private String doctorId;

    /**
     * 是否是患者的主治医生 1是 0否
     * is_attend
     */
    private Integer isAttend;

    /**
     * 0：默认分组，非0为真实分组
     * group_id
     */
    private String groupId;

    /**
     * 关注状态 1 已关注 0 未关注
     * concern_status
     */
    private Integer concernStatus;

    private List<String> doctorIdList;

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

    public Integer getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Integer isAttend) {
        this.isAttend = isAttend;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }
}