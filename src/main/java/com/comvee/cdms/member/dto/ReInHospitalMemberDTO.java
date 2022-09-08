package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2021/7/28
 */
public class ReInHospitalMemberDTO {

    @NotBlank
    private String sid;
    @NotEmpty
    private String memberId;
    @NotEmpty
    private String inHospitalDate;
    @NotEmpty
    private String hospitalNo;

    private String idCard;
    @NotEmpty(message = "姓名不能为空")
    private String memberName;
    @NotEmpty(message = "生日不能为空")
    private String birthday;
    @NotNull(message = "性别不能为空")
    private Integer sex;
    private String diabetesType;
    private String height;
    private String weight;
    private String mobilePhone;
    private String diabetesDate;
    /**
     * 有无高血压 :1有、2无
     */
    private String essentialHyp;
    /**
     * 是否有糖尿病 1:是 2:否
     */
    private String isDiabetes;
    private String fat; //1有0否

    /**
     *高血压类型 :原发性高血压:HYP_TYPE_001  继发性高血压:HYP_TYPE_002 其他:HYP_TYPE_003
     */
    private String hypType;

    /**
     * 高血压确诊日期
     * diabetes_date
     */
    private String hypDate;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

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

    public String getHypType() {
        return hypType;
    }

    public void setHypType(String hypType) {
        this.hypType = hypType;
    }

    public String getHypDate() {
        return hypDate;
    }

    public void setHypDate(String hypDate) {
        this.hypDate = hypDate;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
