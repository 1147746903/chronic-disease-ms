package com.comvee.cdms.virtualward.model.po;

public class InHospitalPatientListPO {

    /**
     * sid
     */
    private String sid;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 住院号
     * hospital_no
     */
    private String hospitalNo;

    /**
     * 床号
     * bed_no
     */
    private String bedNo;

    /**
     * 转入科室id
     * department_id
     */
    private String departmentId;

    /**
     * 转入科室名称
     * department_name
     */
    private String departmentName;

    /**
     * 入院日期
     * in_hospital_date
     */
    private String inHospitalDate;

    private String memberName;
    private Integer sex;
    private String birthday;

    private Integer applyStatus;

    private String virtualWardId;

    public String getVirtualWardId() {
        return virtualWardId;
    }

    public void setVirtualWardId(String virtualWardId) {
        this.virtualWardId = virtualWardId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }
}

