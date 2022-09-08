package com.comvee.cdms.department.model;

public class MemberBedVO {

    private String bedNo; //床号
    private String machineSn; //设备Id

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn;
    }

    @Override
    public String toString() {
        return "MemberBedVO{" +
                "bedNo='" + bedNo + '\'' +
                ", machineSn='" + machineSn + '\'' +
                '}';
    }
}
