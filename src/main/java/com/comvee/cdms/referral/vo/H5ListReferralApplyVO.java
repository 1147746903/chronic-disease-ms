package com.comvee.cdms.referral.vo;

/**
 * @Author linr
 * @Date 2022/7/28
 */
public class H5ListReferralApplyVO {
    private String sid;
    private String sex;
    private String age;
    private String birthday;

    /**
     * 转诊对象编号
     */
    private String memberId;

    /**
     * 转诊对象名称
     */
    private String memberName;

    /**
     * 转诊医院编号
     */
    private String hospitalId;

    /**
     * 转诊医院名称
     */
    private String hospitalName;



    /**
     * 申请医院编号
     */
    private String applyHospitalId;

    /**
     * 申请医院名称
     */
    private String applyHospitalName;



    /**
     * 申请时间
     */
    private String applyDt;



    /**
     * 糖尿病 1有2无 空无
     */
    private Integer isDiabetes;

    /**
     * 高血压 1有2无  空无
     */
    private String essentialHyp;

    /**
     * 患者手机号
     */
    private String mobilePhone;
    private String townName;
    private String committeeName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }


    public String getApplyHospitalId() {
        return applyHospitalId;
    }

    public void setApplyHospitalId(String applyHospitalId) {
        this.applyHospitalId = applyHospitalId;
    }

    public String getApplyHospitalName() {
        return applyHospitalName;
    }

    public void setApplyHospitalName(String applyHospitalName) {
        this.applyHospitalName = applyHospitalName;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public Integer getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(Integer isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
