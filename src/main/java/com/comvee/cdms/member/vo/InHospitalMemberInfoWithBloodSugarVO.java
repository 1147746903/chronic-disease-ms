package com.comvee.cdms.member.vo;

public class InHospitalMemberInfoWithBloodSugarVO {

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 患者姓名
     * member_name
     */
    private String memberName;

    /**
     * 部门名称
     * department_name
     */
    private String departmentName;

    /**
     * 部门id
     * department_id
     */
    private String departmentId;

    /**
     * 病床号
     * bed_no
     */
    private String bedNo;

    private String recordDate;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
