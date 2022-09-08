package com.comvee.cdms.packages.po;

/**
 * @author: suyz
 * @date: 2018/12/27
 */
public class MemberOrderPackagePO extends MemberPackagePO  {

    private String doctorName;
    private Integer sex;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
