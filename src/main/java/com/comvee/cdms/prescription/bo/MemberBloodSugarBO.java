package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/3 15:05.
 */
public class MemberBloodSugarBO {
    @Override
    public String toString() {
        return "MemberBloodSugarBO{}";
    }

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 开始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String endDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
}
