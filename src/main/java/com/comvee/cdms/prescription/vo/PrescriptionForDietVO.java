package com.comvee.cdms.prescription.vo;

import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.HeatMaxAndMinOfDietBO;

import java.io.Serializable;
import java.util.List;

public class PrescriptionForDietVO implements Serializable {

    /**
     * 模块主键
     */
    private String sid;
    /**
     * 饮食治疗信息
     */
    private String dietInfo;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState = 0;

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
     * 推荐饮食列表源
     */
    private List<NutritionCateringTreeVO> nutritionCateringTreeVOS;
    /**
     * 推荐食谱样例
     */
    private NutritionCateringTreeVO nutritionCateringTreeVO;

    private NutritionCateringTreeVO nutritionCateringTreeFatVO;
    /**
     * 食物交换法则
     */
    private List<FoodItemBO> ingredientsItemList;
    private String prescriptionAppendix;
    private List<String> dietTips;
    private String info;  //

    /**
     * 当前饮食分析 （按克）
     */
    private CurrentDietAnalyseVO currentDietAnalyse;

    /**
     *  目标减轻体重
     */
    private String weightLoss;
    private String isTreat;

    public CurrentDietAnalyseVO getCurrentDietAnalyse() {
        return currentDietAnalyse;
    }

    public void setCurrentDietAnalyse(CurrentDietAnalyseVO currentDietAnalyse) {
        this.currentDietAnalyse = currentDietAnalyse;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDietInfo() {
        return dietInfo;
    }

    public void setDietInfo(String dietInfo) {
        this.dietInfo = dietInfo;
    }

    public List<ProblemSuggestionVO> getProblemSuggestions() {
        return problemSuggestions;
    }

    public void setProblemSuggestions(List<ProblemSuggestionVO> problemSuggestions) {
        this.problemSuggestions = problemSuggestions;
    }

    public void setDailyFoodAllocation(FoodAllocationVO dailyFoodAllocation) {
        this.dailyFoodAllocation = dailyFoodAllocation;
    }

    public void setHeatMaxAndMinOfDietBO(HeatMaxAndMinOfDietBO heatMaxAndMinOfDietBO) {
        this.heatMaxAndMinOfDietBO = heatMaxAndMinOfDietBO;
    }

    public void setNutritionCateringTreeVOS(List<NutritionCateringTreeVO> nutritionCateringTreeVOS) {
        this.nutritionCateringTreeVOS = nutritionCateringTreeVOS;
    }

    public void setNutritionCateringTreeVO(NutritionCateringTreeVO nutritionCateringTreeVO) {
        this.nutritionCateringTreeVO = nutritionCateringTreeVO;
    }

    public void setIngredientsItemList(List<FoodItemBO> ingredientsItemList) {
        this.ingredientsItemList = ingredientsItemList;
    }

	public Integer getSaveState() {
		return saveState;
	}

	public void setSaveState(Integer saveState) {
		this.saveState = saveState;
	}
    

    public FoodAllocationVO getDailyFoodAllocation() {
        return dailyFoodAllocation;
    }

    public HeatMaxAndMinOfDietBO getHeatMaxAndMinOfDietBO() {
        return heatMaxAndMinOfDietBO;
    }

    public List<NutritionCateringTreeVO> getNutritionCateringTreeVOS() {
        return nutritionCateringTreeVOS;
    }

    public NutritionCateringTreeVO getNutritionCateringTreeVO() {
        return nutritionCateringTreeVO;
    }

    public List<FoodItemBO> getIngredientsItemList() {
        return ingredientsItemList;
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

    public NutritionCateringTreeVO getNutritionCateringTreeFatVO() {
        return nutritionCateringTreeFatVO;
    }

    public void setNutritionCateringTreeFatVO(NutritionCateringTreeVO nutritionCateringTreeFatVO) {
        this.nutritionCateringTreeFatVO = nutritionCateringTreeFatVO;
    }

    @Override
    public String toString() {
        return "PrescriptionForDietVO{" +
                "sid='" + sid + '\'' +
                ", dietInfo='" + dietInfo + '\'' +
                ", saveState=" + saveState +
                ", currentBloodSugarVO=" + currentBloodSugarVO +
                ", currentDietVO=" + currentDietVO +
                ", currentLifeDailyVO=" + currentLifeDailyVO +
                ", problemSuggestions=" + problemSuggestions +
                ", dailyFoodAllocation=" + dailyFoodAllocation +
                ", heatMaxAndMinOfDietBO=" + heatMaxAndMinOfDietBO +
                ", nutritionCateringTreeVOS=" + nutritionCateringTreeVOS +
                ", nutritionCateringTreeVO=" + nutritionCateringTreeVO +
                ", ingredientsItemList=" + ingredientsItemList +
                ", prescriptionAppendix='" + prescriptionAppendix + '\'' +
                ", dietTips=" + dietTips +
                '}';
    }
}
