package com.comvee.cdms.sign.po;

/**
 * @author wyc
 * @date 2020/3/4 15:49
 */
public class MemberBloodSugarPO {

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
     * 是否高血压 1是 0否
     */
    private String essentialHyp;

    /**
     * 是否糖尿病 1是 0否
     */
    private String isDiabetes;

    /**
     * 糖尿病类型
     * SUGAR_TYPE_001 1型糖尿病
     * SUGAR_TYPE_002 2型糖尿病
     * SUGAR_TYPE_003 妊娠糖尿病
     * SUGAR_TYPE_004 其他
     * SUGAR_TYPE_005 非糖尿病
     * SUGAR_TYPE_006 特殊糖尿病
     */
    private String diabetesType;

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

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
}
