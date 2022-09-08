package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

/**
 * 只能在管理处方中使用
 * 管理处方内的患者基本信息
 *
 */
public class PrescriptionMemberVO implements Serializable {

    private String memberId;
    private String memberName;
    private String height;
    private String weight;
    private String diabetesType;
    private String sex;
    private String birthday;
    private String chd;
    private Integer hyp;
    private String bmi;
    private String labourIntensity;
    /**
     * 糖化血红蛋白
     */
    private String glycosylatedVal;
    private String yqWeight;  //孕前体重
    private String yqBmi;  //孕前BMI
    private String gestationalWeeks;  //孕周
    private String bsSport;  //是否有运动习惯
    private String sportFrequency;  //运动频率
    private String smoke;  //吸烟
    private String drink; //饮酒
    private String waistline; // 腰围
    private String bodyType; // 体型

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

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
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

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public Integer getHyp() {
        return hyp;
    }

    public void setHyp(Integer hyp) {
        this.hyp = hyp;
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

    public String getGlycosylatedVal() {
        return glycosylatedVal;
    }

    public void setGlycosylatedVal(String glycosylatedVal) {
        this.glycosylatedVal = glycosylatedVal;
    }

    public String getYqWeight() {
        return yqWeight;
    }

    public void setYqWeight(String yqWeight) {
        this.yqWeight = yqWeight;
    }

    public String getYqBmi() {
        return yqBmi;
    }

    public void setYqBmi(String yqBmi) {
        this.yqBmi = yqBmi;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getBsSport() {
        return bsSport;
    }

    public void setBsSport(String bsSport) {
        this.bsSport = bsSport;
    }

    public String getSportFrequency() {
        return sportFrequency;
    }

    public void setSportFrequency(String sportFrequency) {
        this.sportFrequency = sportFrequency;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
}
