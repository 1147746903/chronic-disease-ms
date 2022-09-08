package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.dto.GetNutritionCateringDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.NutritionCateringPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/16
 */
@Service("prescriptionOfDietCache")
public class DietCacheImpl implements DietCacheI {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    /**
     * 获取管理处方食谱列表
     * @param dto
     * @return
     */
    @Override
//    @Cacheable(key = "T(String).valueOf(#dto.nid).concat(T(String).valueOf(#dto.calLow)).concat(T(String).valueOf(#dto.calHigh))", value = "prescriptionResources")
//    @Cacheable(value = "prescriptionResources")
    public List<NutritionCateringPO> loadNutritionCateringList(GetNutritionCateringDTO dto){
        return this.prescriptionMapper.loadNutritionCateringList(dto);
    }

    /**
     * 获取食谱
     * @param md5
     * @return
     */
    @Override
    @Cacheable(key = "'nutritionCatering' + #md5" , value = "prescriptionResources")
    public List<NutritionCateringPO> getNCateringsByMD5(String md5){
        return this.prescriptionMapper.getNCateringsByMD5(md5);
    }

    /**
     * 获取食谱份量关系
     * @param md5
     * @return
     */
    @Override
    @Cacheable(key = "'nutritionCatering' + #md5" , value = "prescriptionResources")
    public List<NutritionCateringPO> getRecipesCaloricByMD5(String md5,Integer eohType){
        return this.prescriptionMapper.getRecipesCaloricByMD5(md5,eohType);
    }

    /**
     * 食材交换份数
     * @param ingredientsItemId
     * @return
     */
    @Override
//    @Cacheable(key = "'ingredientsItems:'" , value = "prescriptionResources")
    public List<FoodItemBO> listEohIngredientsItemByIds(List<String> ingredientsItemId){
        return this.prescriptionMapper.listEohIngredientsItemByIds(ingredientsItemId);
    }

    /**
     * 获取食材 新版
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "'ingredientsItem:' + #id + '(version=2)'" , value = "prescriptionResources")
    public FoodItemBO getEohFoodItemById(String id){
        return this.prescriptionMapper.getEohFoodItemById(id);
    }

    /**
     * 获取食材 旧版
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "'ingredientsItem:' + #id + '(version=1)'" , value = "prescriptionResources")
    public FoodItemBO getEohIngredientsItemById(String id){
        return this.prescriptionMapper.getEohIngredientsItemById(id);
    }
}
