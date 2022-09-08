package com.comvee.cdms.doctor.dto;


/**
 * @author wyc
 * @date 2020/3/6 15:58
 */
public class AddDoctorKeyNoteDTO {

    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 操作者id
     */
    private String operatorId;
    /**
     * 是否是住院关注项目 0 否 1 是
     */
    private Integer inHos;
    /**
     * 重点关注项目id
     */
    private String keynoteId;
    /**
     * 医院id
     */
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getKeynoteId() {
        return keynoteId;
    }

    public void setKeynoteId(String keynoteId) {
        this.keynoteId = keynoteId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
