package com.comvee.cdms.sign.vo;

import java.util.Map;

/**
 * @author wyc
 * @date 2020/3/4 15:12
 */
public class MemberBloodSugarVO {
    /**
     * 是否关注 1关注 0未关注
     */
    private Integer concernStatus;
    /**
     * 科室名称
     */
    private String departmentName;
    /**
     * 床号
     */
    private String bedNo;
    /**
     * 患者名称
     */
    private String  memberName;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 科室编号
     */
    private String departmentId;

    /**
     * 患者血糖
     */
    private Map<String,Object> memberSugar;

    /**
     * 今日血糖监测计划
     */
    private String todaySchema;
    private String diabetesType;

    private String departId; //虚拟病区科室id
    private String departName; //虚拟病区科室名称
    private String doctorName; //责任医生
    private String memberStatus; //1我的患者
    private String planId;

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Map<String, Object> getMemberSugar() {
        return memberSugar;
    }

    public void setMemberSugar(Map<String, Object> memberSugar) {
        this.memberSugar = memberSugar;
    }

    public String getTodaySchema() {
        return todaySchema;
    }

    public void setTodaySchema(String todaySchema) {
        this.todaySchema = todaySchema;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "MemberBloodSugarVO{" +
                "concernStatus=" + concernStatus +
                ", departmentName='" + departmentName + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", memberSugar=" + memberSugar +
                ", todaySchema='" + todaySchema + '\'' +
                ", diabetesType='" + diabetesType + '\'' +
                ", departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                '}';
    }
}
