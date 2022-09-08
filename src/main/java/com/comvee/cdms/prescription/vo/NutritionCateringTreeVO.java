package com.comvee.cdms.prescription.vo;

import java.io.Serializable;
import java.util.List;

public class NutritionCateringTreeVO implements Serializable {
    /**
     * 食品热量份数关系编号
     */
    private String recipesCaloricId;
    /**
     *  食谱热量
     */
    private String caloriesLevel;
    /**
     * 食品总份数
     */
    private String foodNum;
    /**
     * 谷类份数
     */
    private String cerealNum;
    /**
     * 肉类份数
     */
    private String meatNum;
    /**
     * 蔬菜份数
     */
    private String vegetablesNum;
    /**
     * 油脂份数
     */
    private String greaseNum;
    /**
     * 水果份数
     */
    private String fruitsNum;
    /**
     * 豆乳份数
     */
    private String soymilkNum;
    /**
     * 蛋类份数
     */
    private String eggNum;
    /**
     * 豆类分数
     */
    private String beansNum;
    /**
     * 食谱列表
     */
    private List<NutritionCateringItemVO> nutritionCateringItems;

    /**
     * 成分统计 按分类，单位g json
     */
    private String ingredientStat;


    public String getIngredientStat() {
        return ingredientStat;
    }

    public void setIngredientStat(String ingredientStat) {
        this.ingredientStat = ingredientStat;
    }

    public String getRecipesCaloricId() {
        return recipesCaloricId;
    }

    public void setRecipesCaloricId(String recipesCaloricId) {
        this.recipesCaloricId = recipesCaloricId;
    }

    public String getCaloriesLevel() {
        return caloriesLevel;
    }

    public void setCaloriesLevel(String caloriesLevel) {
        this.caloriesLevel = caloriesLevel;
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
    }

    public String getCerealNum() {
        return cerealNum;
    }

    public void setCerealNum(String cerealNum) {
        this.cerealNum = cerealNum;
    }

    public String getMeatNum() {
        return meatNum;
    }

    public void setMeatNum(String meatNum) {
        this.meatNum = meatNum;
    }

    public String getVegetablesNum() {
        return vegetablesNum;
    }

    public void setVegetablesNum(String vegetablesNum) {
        this.vegetablesNum = vegetablesNum;
    }

    public String getGreaseNum() {
        return greaseNum;
    }

    public void setGreaseNum(String greaseNum) {
        this.greaseNum = greaseNum;
    }

    public String getFruitsNum() {
        return fruitsNum;
    }

    public void setFruitsNum(String fruitsNum) {
        this.fruitsNum = fruitsNum;
    }

    public String getSoymilkNum() {
        return soymilkNum;
    }

    public void setSoymilkNum(String soymilkNum) {
        this.soymilkNum = soymilkNum;
    }

    public List<NutritionCateringItemVO> getNutritionCateringItems() {
        return nutritionCateringItems;
    }

    public void setNutritionCateringItems(List<NutritionCateringItemVO> nutritionCateringItems) {
        this.nutritionCateringItems = nutritionCateringItems;
    }

    public String getEggNum() {
        return eggNum;
    }

    public void setEggNum(String eggNum) {
        this.eggNum = eggNum;
    }

    public String getBeansNum() {
        return beansNum;
    }

    public void setBeansNum(String beansNum) {
        this.beansNum = beansNum;
    }
}
