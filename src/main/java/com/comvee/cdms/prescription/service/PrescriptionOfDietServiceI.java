package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.FoodNutrientBO;
import com.comvee.cdms.prescription.dto.GetFoodItemDTO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.vo.FoodAllocationVO;
import com.comvee.cdms.prescription.vo.PrescriptionForDietVO;

import java.util.List;
import java.util.Map;

public interface PrescriptionOfDietServiceI {

    /**
     * 根据基础信息-智能推荐饮食
     * @param baseJson
     * @return
     */
    PrescriptionForDietVO intelligentRecommendationOfDiet(String baseJson);

    /**
     * 根据管理处方编号获取饮食方案
     * @param prescriptionId
     * @return
     */
    PrescriptionForDietVO getPrescriptionDiet(String prescriptionId);

    /**
     * 保存管理处方饮食
     * @param prescriptionDTO
     */
    void modifyPrescriptionDiet(PrescriptionDTO prescriptionDTO);

    /**
     * 食材库
     * @param dto
     * @return
     */
    List<FoodItemBO> listIngredientsItem(GetFoodItemDTO dto);

    /**
     * 获取食材分类信息
     * @return
     */
    List<FoodItemBO> listIngredientsTypeOfFood(GetFoodItemDTO dto);

    /**
     * 填充食材信息
     * @return
     */
    void fillEohIngredientsItem(List<FoodItemBO> foodItemBOList, Integer foodTableVersion);

    /**
     * 目前饮食情况
     * @param currentDietInfo  饮食情况信息
     * @return
     */
    FoodAllocationVO currentDiet(String currentDietInfo,Integer version);

    /** v6.7.0
     * 保存自定义食谱
     * @return
     */
    String insertCustomRecipesDiet(String nutritionCateringJson,Integer foodVersion,String doctorId,Integer eohType);

    public List<FoodNutrientBO> listFoodNutrient(String id,String name);

    public  Map<String,Object> getFoodcookbook(String labourIntensity, String bodyType, String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas,Integer type);

}
