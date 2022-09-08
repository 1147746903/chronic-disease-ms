package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class AddMemberDTO implements Serializable {

    @NotEmpty(message = "身份证不能为空")
    private String idCard;
    private String visitNo;
    @NotEmpty(message = "姓名不能为空")
    private String memberName;
    @NotEmpty(message = "生日不能为空")
    private String birthday;
    @NotNull(message = "性别不能为空")
    private Integer sex;
    private String diabetesType;
    private String height;
    private String weight;
//    @NotBlank(message = "手机号不能为空")
    private String mobilePhone;
    private String diabetesDate;
    private String picUrl;

    /**
     * 有无高血压 :1有、2无
     */
    private Integer essentialHyp;
    /**
     * 是否有糖尿病 1:是 2:否
     */
    private String isDiabetes;

    /**
     * 患者来源 1 web端医生添加  2 APP医生添加 3  小程序添加 4 筛查系统同步患者
     */
    private Integer memberSource;

    /**
     * 医患关系创建方式 1 web端添加患者  2 APP添加患者 3 扫描二维码 4 小程序患者申请添加 5 筛查系统同步 6转诊添加医患关系
     */
    private Integer relationWay;

    /**
     * 高血压类型 :原发性高血压:HYP_TYPE_001  继发性高血压:HYP_TYPE_002 其他:HYP_TYPE_003
     */
    private String hypType;

    /**
     * 高血压确诊日期
     * diabetes_date
     */
    private String hypDate;

    private String hospitalId;

    private Integer fat;

    /**
     * 用户标签
     */
    private String memberTag;
    @NotBlank(message = "社保卡号不允许为空")
    private String socialCard;//社保卡

    private String committeeId;//社区id
    private String committeeName;

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
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

    public Integer getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(Integer essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public Integer getMemberSource() {
        return memberSource;
    }

    public void setMemberSource(Integer memberSource) {
        this.memberSource = memberSource;
    }

    public Integer getRelationWay() {
        return relationWay;
    }

    public void setRelationWay(Integer relationWay) {
        this.relationWay = relationWay;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMemberTag() {
        return memberTag;
    }

    public void setMemberTag(String memberTag) {
        this.memberTag = memberTag;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard;
    }

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
