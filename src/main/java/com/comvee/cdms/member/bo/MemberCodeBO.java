package com.comvee.cdms.member.bo;

import java.io.Serializable;

/**
 * @author: wangxy
 * @date: 2018/10/8
 */
public class MemberCodeBO implements Serializable{

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 血糖code
     */
    private String code;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;

        if (obj instanceof MemberCodeBO) {
            MemberCodeBO memberCodeModel = (MemberCodeBO) obj;
            flag = this.memberId.equals(memberCodeModel.getMemberId()) && this.code.equals(memberCodeModel.getCode());
        }

        return flag;
    }
}
