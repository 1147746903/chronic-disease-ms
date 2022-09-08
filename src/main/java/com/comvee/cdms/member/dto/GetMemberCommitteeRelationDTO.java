package com.comvee.cdms.member.dto;

/**
 * @Author linr
 * @Date 2022/8/18
 */
public class GetMemberCommitteeRelationDTO {

    private String sid;
    /**
     * 医生id
     */
    private String memberId;
    /**
     * 社区id
     */
    private String committeeId;
    /**
     * 社区名
     */
    private String committeeName;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;

    private Integer isValid;
    private String hospitalId;

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

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
