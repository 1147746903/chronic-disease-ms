package com.comvee.cdms.sign.vo;

public class DoctorMemberVo {

    private String departName;
    private int count;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DoctorMemberVo{" +
                "departName='" + departName + '\'' +
                ", count=" + count +
                '}';
    }
}
