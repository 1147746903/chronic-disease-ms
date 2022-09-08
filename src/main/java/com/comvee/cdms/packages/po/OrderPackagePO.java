package com.comvee.cdms.packages.po;

/**
 * @author: suyz
 * @date: 2018/12/13
 */
public class OrderPackagePO extends MemberPackagePO {

    private String memberName;

    private Integer sex;

    private String doctorName;

    /**
     * 是否住院 1是 0否
     */
    private Integer inHos;

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
