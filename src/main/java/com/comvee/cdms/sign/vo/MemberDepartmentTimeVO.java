package com.comvee.cdms.sign.vo;

public class MemberDepartmentTimeVO {
    private String memberId;
    private String departmentName;
    private String departmentId;
    private String babNo;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getBabNo() {
        return babNo;
    }

    public void setBabNo(String babNo) {
        this.babNo = babNo;
    }

    @Override
    public String toString() {
        return "MemberDepartmentTimeVO{" +
                "memberId='" + memberId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", babNo='" + babNo + '\'' +
                '}';
    }
}
