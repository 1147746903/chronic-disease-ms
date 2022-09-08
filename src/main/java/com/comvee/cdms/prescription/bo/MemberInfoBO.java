package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/15 10:45.
 */
public class MemberInfoBO {
    @Override
    public String toString() {
        return "MemberInfoBO{}";
    }

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 患者姓名
     */
    private String memberName;
    /**
     * 身高
     */
    private String height;
    /**
     * 体重
     */
    private String weight;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 糖尿病类型
     */
    private String diabetesType;
    /**
     * 高血压
     */
    private String hyp;
    /**
     * 冠心病
     */
    private String chd;
    private String bmi;
    /**
     * 劳动强度
     */
    private String labourIntensity;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getHyp() {
        return hyp;
    }

    public void setHyp(String hyp) {
        this.hyp = hyp;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getLabourIntensity() {
        return labourIntensity;
    }

    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity;
    }
}
