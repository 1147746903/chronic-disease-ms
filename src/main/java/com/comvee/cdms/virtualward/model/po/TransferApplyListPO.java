package com.comvee.cdms.virtualward.model.po;

public class TransferApplyListPO extends VirtualWardTransferApplyPO{

    private String memberName;
    private Integer sex;
    private String birthday;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
