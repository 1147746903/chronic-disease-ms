package com.comvee.cdms.virtualward.model.vo;

import com.comvee.cdms.virtualward.model.po.VirtualWardTransferApplyPO;

public class VirtualWardTransferApplyListVO extends VirtualWardTransferApplyPO {

    private String memberName;
    private String applyName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }
}
