package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李左河
 * @date 2018/7/26 11:04.
 */
public class UpdateDoctorGroupDTO {
    @Override
    public String toString() {
        return "UpdateDoctorGroupDTO{}";
    }

    /**
     * 分组id
     */
    @NotEmpty(message = "分组id，不能为空")
    private String groupId;
    /**
     * 分组名称
     */
    @NotEmpty(message = "请完善分组名称信息")
    private String groupName;
    /**
     * 患者id字符串，用逗号分隔
     */
    private String memberIds;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医患关系创建方式 1 web端添加患者  2 APP添加患者 3 扫描二维码 4 小程序患者申请添加 5 筛查系统同步 6转诊添加医患关系
     */
    private Integer relationWay;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public Integer getRelationWay() {
        return relationWay;
    }

    public void setRelationWay(Integer relationWay) {
        this.relationWay = relationWay;
    }
}
