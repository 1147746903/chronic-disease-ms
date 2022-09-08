package com.comvee.cdms.member.vo;

import com.comvee.cdms.member.bo.MemberDataBO;

/**
 * @author wyc
 * @date 2019/4/16 12:30
 */
public class MemberDataVO {
    private MemberDataBO memberDataBO;
    private String  firstTime;
    private Long packageCount;
    private Long bloodCount;


    public MemberDataBO getMemberDataBO() {
        return memberDataBO;
    }

    public void setMemberDataBO(MemberDataBO memberDataBO) {
        this.memberDataBO = memberDataBO;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public Long getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Long packageCount) {
        this.packageCount = packageCount;
    }

    public Long getBloodCount() {
        return bloodCount;
    }

    public void setBloodCount(Long bloodCount) {
        this.bloodCount = bloodCount;
    }
}
