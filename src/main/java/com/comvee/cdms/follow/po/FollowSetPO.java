package com.comvee.cdms.follow.po;

/**
 * 
 * @author wangxy
 *
 */
public class FollowSetPO {
	/**
	 * 唯一标识
	 */
    private String sid;
    /**
     * 设置对象 1当前患者 2所有患者
     */
    private String setUser;
    /**
     * 随访方式 1提醒医生随访 2自动下发患者填写
     */
    private String followType;
    /**
     * 管理处方提交后 1是 0否 默认0
     */
    private String preCode;
    /**
     * 管理处方随访规则
     */
    private String preRule;
    /**
     * 首诊完成后 1是 0否 默认0
     */
    private String firCode;
    /**
     * 首诊随访规则
     */
    private String firRule;
    /**
     * 患者加入平台后 1是 0否 默认0
     */
    private String joinCode;
    /**
     * 患者加入随访规则
     */
    private String joinRule;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 医生编号
     */
    private String doctorId;

    private String insertDt;

    private String modifyDt;

    private Integer isValid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSetUser() {
        return setUser;
    }

    public void setSetUser(String setUser) {
        this.setUser = setUser;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getPreCode() {
        return preCode;
    }

    public void setPreCode(String preCode) {
        this.preCode = preCode;
    }

    public String getPreRule() {
        return preRule;
    }

    public void setPreRule(String preRule) {
        this.preRule = preRule;
    }

    public String getFirCode() {
        return firCode;
    }

    public void setFirCode(String firCode) {
        this.firCode = firCode;
    }

    public String getFirRule() {
        return firRule;
    }

    public void setFirRule(String firRule) {
        this.firRule = firRule;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getJoinRule() {
        return joinRule;
    }

    public void setJoinRule(String joinRule) {
        this.joinRule = joinRule;
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

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}