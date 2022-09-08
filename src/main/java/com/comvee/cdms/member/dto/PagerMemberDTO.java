package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PagerMemberDTO implements Serializable {

    private String groupId;

    @NotNull(message = "分组类型（1住院/2门诊居家不可为空，参数名:type）")
    private Integer type;

    private String doctorId;

    private String keyWord;

    private Integer concernStatus;
    /**
     * 管理病种 1:糖尿病 2:高血压  1,2两者都有
     */
    private String entityType;

    /**
     * 患者患病类型 1:糖尿病 2:高血压
     */
    private Integer memberType;

    /**
     *  是否根据医院切换 true是
     */
    private Boolean switchFlag;


    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * 当前医院编号
     */
    private String hospitalId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Boolean getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(Boolean switchFlag) {
        this.switchFlag = switchFlag;
    }
}
