package com.comvee.cdms.virtualward.model.vo;

import com.comvee.cdms.virtualward.model.po.VirtualWardTransferApplyPO;

public class TransferApplyDetailVO extends VirtualWardTransferApplyPO {

    private String memberName;
    private Integer sex;
    private String birthday;
    private String applyDoctorName;

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

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }
}
