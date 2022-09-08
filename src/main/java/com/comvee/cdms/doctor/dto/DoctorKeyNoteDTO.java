package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2020/3/6 15:58
 */
public class DoctorKeyNoteDTO {
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医护人员id
     */
    @NotEmpty(message = "医护人员编号不可为空")
    private String doctorId;
    /**
     * 操作者id
     */
    private String operatorId;
    /**
     * 是否是住院关注项目 0 否 1 是
     */
    @NotNull(message = "是否住院关注项目不可为空")
    private Integer inHos;
    /**
     * 重点关注项目id串
     */
    @NotEmpty(message = "重点关注项目id串不可为空，多个“,”隔开")
    private String keyIds;

    /**
     * 医院id
     */
    @NotEmpty(message = "医院编号不可为空")
    private String hospitalId;

    /**
     * 设置类型 1 特别关注设置 2 查看数据设置 可选默认1
     */
    private Integer type=1;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getKeyIds() {
        return keyIds;
    }

    public void setKeyIds(String keyIds) {
        this.keyIds = keyIds;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
