package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/29 9:33.
 */
public class MemberParamBO {
    @Override
    public String toString() {
        return "MemberParamBO{}";
    }

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 血糖记录code
     */
    private String paramCode;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }
}
