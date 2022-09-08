package com.comvee.cdms.prescription.bo;

import com.comvee.cdms.prescription.vo.*;

import java.io.Serializable;
import java.util.List;

/**
 * 饮食模块detaiJson-model;
 */
public class PrescriptionDetailOfDietBO implements Serializable{

    /**
     * 目前血糖情况
     */
    private CurrentBloodSugarVO currentBloodSugarVO;
    /**
     * 日常生活习惯
     */
    private CurrentDietVO currentDietVO;
    /**
     * 目前饮食内容
     */
    private CurrentLifeDailyVO currentLifeDailyVO;

    /**
     * 日常饮食-分析
     */
    private List<ProblemSuggestionVO> problemSuggestions;
    /**
     * 日常饮食-情况
     */
    private FoodAllocationVO dailyFoodAllocation;
    /**
     * 推荐饮食热量
     */
    private HeatMaxAndMinOfDietBO heatMaxAndMinOfDietBO;
    /**
     * 食物交换法则
     */
    private List<FoodItemBO> ingredientsItemList;
    /**
     * 推荐食谱样例编号
     */
    private String nutritionCateringId;
    /**
     * 选择附录
     */
    private String prescriptionAppendix;
    /**
     * 饮食贴士
     */
    private List<String> dietTips;

    private String info;

    private String weightLoss;

    private String isTreat;

    /**
     * 肥胖
     */
    private String nutritionCateringFatId;
    /**
     * 当前饮食分析（按克）
     */
    private CurrentDietAnalyseVO currentDietAnalyse;

    public CurrentDietAnalyseVO getCurrentDietAnalyse() {
        return currentDietAnalyse;
    }

    public void setCurrentDietAnalyse(CurrentDietAnalyseVO currentDietAnalyse) {
        this.currentDietAnalyse = currentDietAnalyse;
    }

    public List<ProblemSuggestionVO> getProblemSuggestions() {
        return problemSuggestions;
    }

    public void setProblemSuggestions(List<ProblemSuggestionVO> problemSuggestions) {
        this.problemSuggestions = problemSuggestions;
    }

    public FoodAllocationVO getDailyFoodAllocation() {
        return dailyFoodAllocation;
    }

    public void setDailyFoodAllocation(FoodAllocationVO dailyFoodAllocation) {
        this.dailyFoodAllocation = dailyFoodAllocation;
    }

    public HeatMaxAndMinOfDietBO getHeatMaxAndMinOfDietBO() {
        return heatMaxAndMinOfDietBO;
    }

    public void setHeatMaxAndMinOfDietBO(HeatMaxAndMinOfDietBO heatMaxAndMinOfDietBO) {
        this.heatMaxAndMinOfDietBO = heatMaxAndMinOfDietBO;
    }

    public String getNutritionCateringId() {
        return nutritionCateringId;
    }

    public void setNutritionCateringId(String nutritionCateringId) {
        this.nutritionCateringId = nutritionCateringId;
    }

    public List<FoodItemBO> getIngredientsItemList() {
        return ingredientsItemList;
    }

    public void setIngredientsItemList(List<FoodItemBO> ingredientsItemList) {
        this.ingredientsItemList = ingredientsItemList;
    }

    public CurrentBloodSugarVO getCurrentBloodSugarVO() {
        return currentBloodSugarVO;
    }

    public void setCurrentBloodSugarVO(CurrentBloodSugarVO currentBloodSugarVO) {
        this.currentBloodSugarVO = currentBloodSugarVO;
    }

    public CurrentDietVO getCurrentDietVO() {
        return currentDietVO;
    }

    public void setCurrentDietVO(CurrentDietVO currentDietVO) {
        this.currentDietVO = currentDietVO;
    }

    public CurrentLifeDailyVO getCurrentLifeDailyVO() {
        return currentLifeDailyVO;
    }

    public void setCurrentLifeDailyVO(CurrentLifeDailyVO currentLifeDailyVO) {
        this.currentLifeDailyVO = currentLifeDailyVO;
    }

    public void setPrescriptionAppendix(String prescriptionAppendix) {
        this.prescriptionAppendix = prescriptionAppendix;
    }

    public void setDietTips(List<String> dietTips) {
        this.dietTips = dietTips;
    }

    public String getPrescriptionAppendix() {
        return prescriptionAppendix;
    }

    public List<String> getDietTips() {
        return dietTips;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(String weightLoss) {
        this.weightLoss = weightLoss;
    }

    public String getIsTreat() {
        return isTreat;
    }

    public void setIsTreat(String isTreat) {
        this.isTreat = isTreat;
    }

    public String getNutritionCateringFatId() {
        return nutritionCateringFatId;
    }

    public void setNutritionCateringFatId(String nutritionCateringFatId) {
        this.nutritionCateringFatId = nutritionCateringFatId;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailOfDietBO{" +
                "currentBloodSugarVO=" + currentBloodSugarVO +
                ", currentDietVO=" + currentDietVO +
                ", currentLifeDailyVO=" + currentLifeDailyVO +
                ", problemSuggestions=" + problemSuggestions +
                ", dailyFoodAllocation=" + dailyFoodAllocation +
                ", heatMaxAndMinOfDietBO=" + heatMaxAndMinOfDietBO +
                ", ingredientsItemList=" + ingredientsItemList +
                ", nutritionCateringId='" + nutritionCateringId + '\'' +
                ", prescriptionAppendix='" + prescriptionAppendix + '\'' +
                ", dietTips=" + dietTips +
                '}';
    }
}
