package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

/**
 * 目前饮食情况分析参数
 */
public class DietOutPutDTO implements Serializable {
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 一天摄入总热量
     */
    private Double totalHeat;
    /**
     * 全天碳水化合物类食物摄入份数
     */
    private Double totalCarbohydrates;
    /**
     * 全日蔬菜份数
     */
    private Double totalVegetables;
    /**
     * 全天蛋白质类食物摄入份数
     */
    private Double totalProteins;
    /**
     * 全天脂肪类食物摄入份数
     */
    private Double totalFats;
    /**
     * 全天水果类食物摄入份数
     */
    private Double totalFruit;
    /**
     * 全日酒精摄入量
     */
    private Double totalDrinks;
    /**
     * 推荐热量上限
     */
    private Double preTotalHeatMin;
    /**
     * 推荐热量下限
     */
    private Double preTotalHeatMax;
    /**
     * 推荐饮食方案中的谷薯类份数
     */
    private Double preCereal;
    /**
     *  推荐饮食方案中的肉类+蛋类的总份数
     */
    private Double preMeatAndAge;
    /**
     * 推荐饮食方案中的油脂类的总份数
     */
    private Double preMilk;
    /**
     * 推荐饮食方案中的油脂类的总份数
     */
    private Double preProteins;
    /**
     * 血糖是否异常(餐后血糖或空腹血糖是否不达标即偏高或者偏低)
     */
    private Boolean isPathoglycemia;
    /**
     * 喝酒频率
     */
    private String drinkTime;
    /**
     * 早餐主食份数
     */
    private String breakfastCount;
    /**
     * 午餐主食份数
     */
    private String lunchCount;
    /**
     * 晚餐主食份数
     */
    private String dinnerCount;
    /**
     * 是否吸烟 1:抽烟 2:不抽
     */
    private Integer hasSmoke;
    /**
     * 抽烟数量 X根/天
     */
    private Integer smokeNum;
    /**
     * YJQK02
     */
    private String drinkRate;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Double getTotalHeat() {
        return totalHeat;
    }

    public void setTotalHeat(Double totalHeat) {
        this.totalHeat = totalHeat;
    }

    public Double getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(Double totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public Double getTotalVegetables() {
        return totalVegetables;
    }

    public void setTotalVegetables(Double totalVegetables) {
        this.totalVegetables = totalVegetables;
    }

    public Double getTotalProteins() {
        return totalProteins;
    }

    public void setTotalProteins(Double totalProteins) {
        this.totalProteins = totalProteins;
    }

    public Double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(Double totalFats) {
        this.totalFats = totalFats;
    }

    public Double getTotalFruit() {
        return totalFruit;
    }

    public void setTotalFruit(Double totalFruit) {
        this.totalFruit = totalFruit;
    }

    public Double getTotalDrinks() {
        return totalDrinks;
    }

    public void setTotalDrinks(Double totalDrinks) {
        this.totalDrinks = totalDrinks;
    }

    public Double getPreTotalHeatMin() {
        return preTotalHeatMin;
    }

    public void setPreTotalHeatMin(Double preTotalHeatMin) {
        this.preTotalHeatMin = preTotalHeatMin;
    }

    public Double getPreTotalHeatMax() {
        return preTotalHeatMax;
    }

    public void setPreTotalHeatMax(Double preTotalHeatMax) {
        this.preTotalHeatMax = preTotalHeatMax;
    }

    public Double getPreCereal() {
        return preCereal;
    }

    public void setPreCereal(Double preCereal) {
        this.preCereal = preCereal;
    }

    public Double getPreMeatAndAge() {
        return preMeatAndAge;
    }

    public void setPreMeatAndAge(Double preMeatAndAge) {
        this.preMeatAndAge = preMeatAndAge;
    }

    public Double getPreMilk() {
        return preMilk;
    }

    public void setPreMilk(Double preMilk) {
        this.preMilk = preMilk;
    }

    public Double getPreProteins() {
        return preProteins;
    }

    public void setPreProteins(Double preProteins) {
        this.preProteins = preProteins;
    }

    public Boolean getPathoglycemia() {
        return isPathoglycemia;
    }

    public void setPathoglycemia(Boolean pathoglycemia) {
        isPathoglycemia = pathoglycemia;
    }

    public String getDrinkTime() {
        return drinkTime;
    }

    public void setDrinkTime(String drinkTime) {
        this.drinkTime = drinkTime;
    }

    public String getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(String breakfastCount) {
        this.breakfastCount = breakfastCount;
    }

    public String getLunchCount() {
        return lunchCount;
    }

    public void setLunchCount(String lunchCount) {
        this.lunchCount = lunchCount;
    }

    public String getDinnerCount() {
        return dinnerCount;
    }

    public void setDinnerCount(String dinnerCount) {
        this.dinnerCount = dinnerCount;
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

    public String getDrinkRate() {
        return drinkRate;
    }

    public void setDrinkRate(String drinkRate) {
        this.drinkRate = drinkRate;
    }

    @Override
    public String toString() {
        return "DietOutPutDTO{" +
                "sex=" + sex +
                ", totalHeat=" + totalHeat +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", totalVegetables=" + totalVegetables +
                ", totalProteins=" + totalProteins +
                ", totalFats=" + totalFats +
                ", totalFruit=" + totalFruit +
                ", totalDrinks=" + totalDrinks +
                ", preTotalHeatMin=" + preTotalHeatMin +
                ", preTotalHeatMax=" + preTotalHeatMax +
                ", preCereal=" + preCereal +
                ", preMeatAndAge=" + preMeatAndAge +
                ", preMilk=" + preMilk +
                ", preProteins=" + preProteins +
                ", isPathoglycemia=" + isPathoglycemia +
                ", drinkTime='" + drinkTime + '\'' +
                ", breakfastCount='" + breakfastCount + '\'' +
                ", lunchCount='" + lunchCount + '\'' +
                ", dinnerCount='" + dinnerCount + '\'' +
                ", hasSmoke=" + hasSmoke +
                ", smokeNum=" + smokeNum +
                ", drinkRate='" + drinkRate + '\'' +
                '}';
    }
}
