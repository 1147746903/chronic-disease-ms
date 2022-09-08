package com.comvee.cdms.member.po;

/**医生备注信息
 * @author wyc
 * @date 2019/4/12 9:08
 */
public class DoctorMemberRemarkPO {
    //主键id
    private String remarkId;
    //患者id
    private String memberId;
    //医生id
    private String doctorId;
    //备注信息
    private String remark;
    //添加时间
    private String insertDt;
    //更新时间
    private String updateDt;

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
