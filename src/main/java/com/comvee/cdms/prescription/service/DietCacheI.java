package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.dto.GetNutritionCateringDTO;
import com.comvee.cdms.prescription.po.NutritionCateringPO;

import java.util.List;

public interface DietCacheI {

    List<NutritionCateringPO> loadNutritionCateringList(GetNutritionCateringDTO dto);
    /**
     * 获取食谱
     * @param md5
     * @return
     */
    List<NutritionCateringPO> getNCateringsByMD5(String md5);

    /**
     * 获取食谱份量关系
     * @param md5
     * @return
     */
    List<NutritionCateringPO> getRecipesCaloricByMD5(String md5,Integer eohType);

    /**
     * 食材交换份数
     * @param ingredientsItemId
     * @return
     */
    List<FoodItemBO> listEohIngredientsItemByIds(List<String> ingredientsItemId);

    /**
     * 获取食材 新版
     * @param id
     * @return
     */
    FoodItemBO getEohFoodItemById(String id);

    /**
     * 获取食材 旧版
     * @param id
     * @return
     */
    FoodItemBO getEohIngredientsItemById(String id);
}
