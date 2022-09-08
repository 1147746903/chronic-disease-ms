package com.comvee.cdms.dybloodsugar.po;

public class GsMemberArchivesPO {

    private String sid;
    private String memberId;//患者id
    private Integer isSmoke;//是否吸烟 1：是 0 ：否
    private Integer isDrink;//是否喝酒 1：是 0： 否
    private Integer dietaryHabit;//饮食习惯 1、素食主义 2、无肉不欢 3、重口味 4、常喝牛奶 5、常吃蔬菜
    private Integer exerciseIntensity;//运动强度 1、久坐族/无运动习惯 2、轻体力劳动/1-3天 3.中体力劳动/3-5天 4、重体力劳动/6-7天 5 纯体力劳动/每天两次
    private String breakfastTime;//早餐时间 格式HH:mm
    private String lunchTime;//午餐时间 格式HH:mm
    private String dinnerTime;//晚餐时间 格式HH:mm
    private Integer isValid;//是否有效 1：是 0： 否
    private Integer agreement;//是否同意绿星计划 0： 不同意 1 ：同意
    private String insertDt;
    private String modifyDt;

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

    public Integer getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(Integer isSmoke) {
        this.isSmoke = isSmoke;
    }

    public Integer getIsDrink() {
        return isDrink;
    }

    public void setIsDrink(Integer isDrink) {
        this.isDrink = isDrink;
    }

    public Integer getDietaryHabit() {
        return dietaryHabit;
    }

    public void setDietaryHabit(Integer dietaryHabit) {
        this.dietaryHabit = dietaryHabit;
    }

    public Integer getExerciseIntensity() {
        return exerciseIntensity;
    }

    public void setExerciseIntensity(Integer exerciseIntensity) {
        this.exerciseIntensity = exerciseIntensity;
    }

    public String getBreakfastTime() {
        return breakfastTime;
    }

    public void setBreakfastTime(String breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public String getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(String dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getAgreement() {
        return agreement;
    }

    public void setAgreement(Integer agreement) {
        this.agreement = agreement;
    }
}
