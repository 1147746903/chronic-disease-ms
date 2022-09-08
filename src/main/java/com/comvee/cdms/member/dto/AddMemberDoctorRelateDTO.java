package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class AddMemberDoctorRelateDTO implements Serializable{

    @NotEmpty
    private String doctorId;
    @NotEmpty
    private String memberId;
    private String operatorId;

    @NotEmpty
    private String groupId;

    private Integer concernStatus;

    /**
     * 医患关系创建方式 1 web端添加患者  2 APP添加患者 3 扫描二维码 4 小程序患者申请添加 5 筛查系统同步 6转诊添加医患关系
     */
    private Integer relationWay;

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getRelationWay() {
        return relationWay;
    }

    public void setRelationWay(Integer relationWay) {
        this.relationWay = relationWay;
    }
}
