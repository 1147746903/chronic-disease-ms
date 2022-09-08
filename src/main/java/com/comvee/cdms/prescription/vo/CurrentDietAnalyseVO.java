package com.comvee.cdms.prescription.vo;

/**
 * 当前饮食情况分析  碳水化合物/ 脂肪/蛋白质/盐  总量
 */
public class CurrentDietAnalyseVO {

    /**
     * 重量单位为  g（克）
     */

    /**
     * 食物总重量
     */
    private Float totalFoodWeight;
    /**
     * 碳水化合物重量
     */
    private Float carbohydrateWeight;
    /**
     * 脂肪总重量
     */
    private Float fatWeight;
    /**
     * 蛋白质总重量
     */
    private Float proteinWeight;
    /**
     * 盐总重量
     */
    private Float saltWeight;

    /**
     * 碳水化合物热量
     */
    private Float carbohydrateQuantityHeat;
    /**
     * 脂肪总热量
     */
    private Float fatQuantityHeat;
    /**
     * 蛋白质总热量
     */
    private Float proteinQuantityHeat;

    public Float getTotalFoodWeight() {
        return totalFoodWeight;
    }

    public void setTotalFoodWeight(Float totalFoodWeight) {
        this.totalFoodWeight = totalFoodWeight;
    }

    public Float getCarbohydrateWeight() {
        return carbohydrateWeight;
    }

    public void setCarbohydrateWeight(Float carbohydrateWeight) {
        this.carbohydrateWeight = carbohydrateWeight;
    }

    public Float getFatWeight() {
        return fatWeight;
    }

    public void setFatWeight(Float fatWeight) {
        this.fatWeight = fatWeight;
    }

    public Float getProteinWeight() {
        return proteinWeight;
    }

    public void setProteinWeight(Float proteinWeight) {
        this.proteinWeight = proteinWeight;
    }

    public Float getSaltWeight() {
        return saltWeight;
    }

    public void setSaltWeight(Float saltWeight) {
        this.saltWeight = saltWeight;
    }

    public Float getCarbohydrateQuantityHeat() {
        return carbohydrateQuantityHeat;
    }

    public void setCarbohydrateQuantityHeat(Float carbohydrateQuantityHeat) {
        this.carbohydrateQuantityHeat = carbohydrateQuantityHeat;
    }

    public Float getFatQuantityHeat() {
        return fatQuantityHeat;
    }

    public void setFatQuantityHeat(Float fatQuantityHeat) {
        this.fatQuantityHeat = fatQuantityHeat;
    }

    public Float getProteinQuantityHeat() {
        return proteinQuantityHeat;
    }

    public void setProteinQuantityHeat(Float proteinQuantityHeat) {
        this.proteinQuantityHeat = proteinQuantityHeat;
    }

}
