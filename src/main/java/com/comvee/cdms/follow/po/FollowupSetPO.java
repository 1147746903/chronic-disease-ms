package com.comvee.cdms.follow.po;

/**
 * @author wyc
 * @date 2019/12/20 13:03
 */
public class FollowupSetPO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 随访方式 1提醒医生随访 2自动下发患者填写
     */
    private Integer followType;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 是否有效 0: 否  1 :是
     */
    private Integer isValid;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String updateDt;

    /**
     * 随访设置规则
     */
    private String setRule;
    private String hospitalId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getSetRule() {
        return setRule;
    }

    public void setSetRule(String setRule) {
        this.setRule = setRule;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
