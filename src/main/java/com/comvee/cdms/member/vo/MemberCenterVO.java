package com.comvee.cdms.member.vo;

import com.comvee.cdms.member.po.MemberPO;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
public class MemberCenterVO {

    private MemberPO member;
    private Long bloodSugarHigh;
    private Long bloodSugarLow;
    private Long bloodSugarNormal;

    public MemberPO getMember() {
        return member;
    }

    public void setMember(MemberPO member) {
        this.member = member;
    }

    public Long getBloodSugarHigh() {
        return bloodSugarHigh;
    }

    public void setBloodSugarHigh(Long bloodSugarHigh) {
        this.bloodSugarHigh = bloodSugarHigh;
    }

    public Long getBloodSugarLow() {
        return bloodSugarLow;
    }

    public void setBloodSugarLow(Long bloodSugarLow) {
        this.bloodSugarLow = bloodSugarLow;
    }

    public Long getBloodSugarNormal() {
        return bloodSugarNormal;
    }

    public void setBloodSugarNormal(Long bloodSugarNormal) {
        this.bloodSugarNormal = bloodSugarNormal;
    }
}
