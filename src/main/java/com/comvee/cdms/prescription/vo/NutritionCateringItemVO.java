package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class NutritionCateringItemVO implements Serializable {

    /**
     * 营养配餐编号
     */
    private String nutritionCateringId;
    /**
     * 早餐（旧版包含所有餐点信息）
     */
    private String breakfastFoodJson;
    /**
     * 早加餐
     */
    private String breakfastFoodAddJson;
    /**
     * 午餐
     */
    private String lunchFoodJson;
    /**
     * 午加餐
     */
    private String lunchFoodAddJson;
    /**
     * 晚餐
     */
    private String dinnerFoodJson;
    /**
     * 晚加餐
     */
    private String dinnerFoodAddJson;
    /**
     * 版本 1旧版 所有餐点信息存在早餐字段 2 新版 餐点信息分开存储
     */
    private Integer version;
    /**
     * 食谱所含碳水化合物
     */
    private double carbohydrates;
    /**
     * 食谱所含脂肪
     */
    private double totalfats;
    /**
     * 食谱所含蛋白质
     */
    private double protein;

    /**
     * 食谱名称 demo:食谱1   (1=index 食谱所在列表的位置+1)
     */
    private String name;

    /**
     * 膳食模式  膳食模式：空 0 无模式，1平衡膳食，2限能量平衡膳食，3高蛋白膳食，4轻断食（5+2）模式
     */
    private String mealsModel;

    public String getNutritionCateringId() {
        return nutritionCateringId;
    }

    public void setNutritionCateringId(String nutritionCateringId) {
        this.nutritionCateringId = nutritionCateringId;
    }

    public String getBreakfastFoodJson() {
        return breakfastFoodJson;
    }

    public void setBreakfastFoodJson(String breakfastFoodJson) {
        this.breakfastFoodJson = breakfastFoodJson;
    }

    public String getBreakfastFoodAddJson() {
        return breakfastFoodAddJson;
    }

    public void setBreakfastFoodAddJson(String breakfastFoodAddJson) {
        this.breakfastFoodAddJson = breakfastFoodAddJson;
    }

    public String getLunchFoodJson() {
        return lunchFoodJson;
    }

    public void setLunchFoodJson(String lunchFoodJson) {
        this.lunchFoodJson = lunchFoodJson;
    }

    public String getLunchFoodAddJson() {
        return lunchFoodAddJson;
    }

    public void setLunchFoodAddJson(String lunchFoodAddJson) {
        this.lunchFoodAddJson = lunchFoodAddJson;
    }

    public String getDinnerFoodJson() {
        return dinnerFoodJson;
    }

    public void setDinnerFoodJson(String dinnerFoodJson) {
        this.dinnerFoodJson = dinnerFoodJson;
    }

    public String getDinnerFoodAddJson() {
        return dinnerFoodAddJson;
    }

    public void setDinnerFoodAddJson(String dinnerFoodAddJson) {
        this.dinnerFoodAddJson = dinnerFoodAddJson;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setTotalfats(double totalfats) {
        this.totalfats = totalfats;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getTotalfats() {
        return totalfats;
    }

    public double getProtein() {
        return protein;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMealsModel() {
        return mealsModel;
    }

    public void setMealsModel(String mealsModel) {
        this.mealsModel = mealsModel;
    }
}
