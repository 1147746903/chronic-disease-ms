package com.comvee.cdms.glu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2022/7/28
 */
public class AddGluMemberDTO {

    @NotBlank(message = "身份证不能为空")
    private String idCard;
    @NotBlank(message = "姓名不能为空")
    private String memberName;
    @NotBlank(message = "生日不能为空")
    private String birthday;
    @NotNull(message = "性别不能为空")
    private Integer sex;//1男2女

    private String mobilePhone;
    private String socialCard;//社保卡

    @NotBlank(message = "糖尿病类型不能为空")
    private String diabetesType; //1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)	 0非糖尿病

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }
}
