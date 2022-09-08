package com.comvee.cdms.member.vo;

import java.util.List;

public class WxMemberRegisterLoadMembersVO {

    private String mobilePhone;
    private List<WxMemberRegisterLoadMembersItemVO> memberList;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public List<WxMemberRegisterLoadMembersItemVO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<WxMemberRegisterLoadMembersItemVO> memberList) {
        this.memberList = memberList;
    }
}
