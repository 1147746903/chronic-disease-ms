package com.comvee.cdms.dybloodsugar.vo;

/**
 * @author wyc
 * @date 2020/6/16 16:19
 */
public class ShowSensorVO {
    private String sid;
    private String memberSensorSid;
    private Integer isValid;
    private String inserDt;
    private String modifyDt;
    private String memberId;
    /**
     * 接收者姓名
     */
    private String receiveMemberName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberSensorSid() {
        return memberSensorSid;
    }

    public void setMemberSensorSid(String memberSensorSid) {
        this.memberSensorSid = memberSensorSid;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInserDt() {
        return inserDt;
    }

    public void setInserDt(String inserDt) {
        this.inserDt = inserDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReceiveMemberName() {
        return receiveMemberName;
    }

    public void setReceiveMemberName(String receiveMemberName) {
        this.receiveMemberName = receiveMemberName;
    }
}
