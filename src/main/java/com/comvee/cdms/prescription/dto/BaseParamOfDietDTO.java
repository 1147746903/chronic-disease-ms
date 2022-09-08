package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

/**
 * 饮食智能推荐基础信息 - 条件
 */
public class BaseParamOfDietDTO implements Serializable {
    /**
     * 处方编号
     */
    private String prescriptionId;

    /**
     * 患者信息（身高，体重，性别，bmi，劳动强度）
     */
    private String memberId;
    private Double height;
    private Double weight;
    private Integer sex;
    private Double bmi;
    private String labourIntensity;
    private Double progestationalWeight; //孕前体重
    private Integer gestationalWeeks;  //孕周

    private Integer eohType;//0:糖尿病（非妊娠） 1:妊娠糖尿病
    /**
     * 当前一天的血糖
     */
    /**
     * 凌晨血糖
     */
    private Double beforedawnSugar;
    /**
     * 空腹血糖
     */
    private Double beforeBreakfastSugar;
    /**
     * 早餐后血糖
     */
    private Double afterBreakfastSugar;
    /**
     * 午餐前血糖
     */
    private Double beforeLunchSugar;
    /**
     * 午餐后血糖
     */
    private Double afterLunchSugar;
    /**
     * 晚餐前血糖
     */
    private Double beforeDinnerSugar;
    /**
     * 晚餐后血糖
     */
    private Double afterDinnerSugar;
    /**
     * 睡前血糖
     */
    private Double beforeSleepSugar;
    /**
     * 是否吸烟 1:抽烟 2:不抽
     */
    private Integer hasSmoke;
    /**
     * 抽烟数量 X根/天
     */
    private Integer smokeNum;
    /**
     * 烟龄
     */
    private Double somkeYear;
    /**
     * 喝酒频率
     */
    private String drinkTime;
    /**
     * YJQK02
     */
    private String drinkRate;
    /**
     * 红酒
     */
    private Double redWineE;
    /**
     * 啤酒
     */
    private Double beerE;
    /**
     * 黄酒
     */
    private Double yellowWineE;
    /**
     * 高度白酒
     */
    private Double whiteSpiritHighE;
    /**
     * 白酒
     */
    private Double whiteSpiritE;
    /**
     * 目前饮食
     */
    private String currentDietInfo;

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Double getProgestationalWeight() {
        return progestationalWeight;
    }

    public void setProgestationalWeight(Double progestationalWeight) {
        this.progestationalWeight = progestationalWeight;
    }

    public Integer getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(Integer gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public String getLabourIntensity() {
        return labourIntensity;
    }

    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity;
    }

    public Double getBeforedawnSugar() {
        return beforedawnSugar;
    }

    public void setBeforedawnSugar(Double beforedawnSugar) {
        this.beforedawnSugar = beforedawnSugar;
    }

    public Double getBeforeBreakfastSugar() {
        return beforeBreakfastSugar;
    }

    public void setBeforeBreakfastSugar(Double beforeBreakfastSugar) {
        this.beforeBreakfastSugar = beforeBreakfastSugar;
    }

    public Double getAfterBreakfastSugar() {
        return afterBreakfastSugar;
    }

    public void setAfterBreakfastSugar(Double afterBreakfastSugar) {
        this.afterBreakfastSugar = afterBreakfastSugar;
    }

    public Double getBeforeLunchSugar() {
        return beforeLunchSugar;
    }

    public void setBeforeLunchSugar(Double beforeLunchSugar) {
        this.beforeLunchSugar = beforeLunchSugar;
    }

    public Double getAfterLunchSugar() {
        return afterLunchSugar;
    }

    public void setAfterLunchSugar(Double afterLunchSugar) {
        this.afterLunchSugar = afterLunchSugar;
    }

    public Double getBeforeDinnerSugar() {
        return beforeDinnerSugar;
    }

    public void setBeforeDinnerSugar(Double beforeDinnerSugar) {
        this.beforeDinnerSugar = beforeDinnerSugar;
    }

    public Double getAfterDinnerSugar() {
        return afterDinnerSugar;
    }

    public void setAfterDinnerSugar(Double afterDinnerSugar) {
        this.afterDinnerSugar = afterDinnerSugar;
    }

    public Double getBeforeSleepSugar() {
        return beforeSleepSugar;
    }

    public void setBeforeSleepSugar(Double beforeSleepSugar) {
        this.beforeSleepSugar = beforeSleepSugar;
    }

    public Integer getHasSmoke() {
        return hasSmoke;
    }

    public void setHasSmoke(Integer hasSmoke) {
        this.hasSmoke = hasSmoke;
    }

    public Integer getSmokeNum() {
        return smokeNum;
    }

    public void setSmokeNum(Integer smokeNum) {
        this.smokeNum = smokeNum;
    }

    public Double getSomkeYear() {
        return somkeYear;
    }

    public void setSomkeYear(Double somkeYear) {
        this.somkeYear = somkeYear;
    }

    public String getDrinkTime() {
        return drinkTime;
    }

    public void setDrinkTime(String drinkTime) {
        this.drinkTime = drinkTime;
    }

    public String getDrinkRate() {
        return drinkRate;
    }

    public void setDrinkRate(String drinkRate) {
        this.drinkRate = drinkRate;
    }

    public Double getRedWineE() {
        return redWineE;
    }

    public void setRedWineE(Double redWineE) {
        this.redWineE = redWineE;
    }

    public Double getBeerE() {
        return beerE;
    }

    public void setBeerE(Double beerE) {
        this.beerE = beerE;
    }

    public Double getYellowWineE() {
        return yellowWineE;
    }

    public void setYellowWineE(Double yellowWineE) {
        this.yellowWineE = yellowWineE;
    }

    public Double getWhiteSpiritHighE() {
        return whiteSpiritHighE;
    }

    public void setWhiteSpiritHighE(Double whiteSpiritHighE) {
        this.whiteSpiritHighE = whiteSpiritHighE;
    }

    public Double getWhiteSpiritE() {
        return whiteSpiritE;
    }

    public void setWhiteSpiritE(Double whiteSpiritE) {
        this.whiteSpiritE = whiteSpiritE;
    }

    public String getCurrentDietInfo() {
        return currentDietInfo;
    }

    public void setCurrentDietInfo(String currentDietInfo) {
        this.currentDietInfo = currentDietInfo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "BaseParamOfDietDTO{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", sex=" + sex +
                ", bmi=" + bmi +
                ", labourIntensity='" + labourIntensity + '\'' +
                ", beforedawnSugar=" + beforedawnSugar +
                ", beforeBreakfastSugar=" + beforeBreakfastSugar +
                ", afterBreakfastSugar=" + afterBreakfastSugar +
                ", beforeLunchSugar=" + beforeLunchSugar +
                ", afterLunchSugar=" + afterLunchSugar +
                ", beforeDinnerSugar=" + beforeDinnerSugar +
                ", afterDinnerSugar=" + afterDinnerSugar +
                ", beforeSleepSugar=" + beforeSleepSugar +
                ", hasSmoke=" + hasSmoke +
                ", smokeNum=" + smokeNum +
                ", somkeYear=" + somkeYear +
                ", drinkTime='" + drinkTime + '\'' +
                ", drinkRate='" + drinkRate + '\'' +
                ", redWineE=" + redWineE +
                ", beerE=" + beerE +
                ", yellowWineE=" + yellowWineE +
                ", whiteSpiritHighE=" + whiteSpiritHighE +
                ", whiteSpiritE=" + whiteSpiritE +
                ", currentDietInfo='" + currentDietInfo + '\'' +
                '}';
    }
}
