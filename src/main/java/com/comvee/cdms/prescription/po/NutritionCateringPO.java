package com.comvee.cdms.prescription.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.MD5Util;
import com.comvee.cdms.common.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 营养配餐 模型
 * @author linyd
 *
 */
public class NutritionCateringPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食品热量份数关系编号
     */
    private String recipesCaloricId;
    /**
     * 营养配餐编号
     */
    private String nutritionCateringId;
    /**
     *  膳食模式  膳食模式：空 0 无模式，1平衡膳食，2限能量平衡膳食，3高蛋白膳食，4轻断食（5+2）模式
     */
    private String mealsModel;
    /**
     *  食谱名称
     */
    private String recipeName;
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
     * 早餐饮食内容（旧版把所有餐点内容存在这个字段）
     */
    private String breakfastFoodJson;
    /**
     * 早加餐饮食内容
     */
    private String breakfastFoodAddJson;
    /**
     * 午餐饮食内容
     */
    private String lunchFoodJson;
    /**
     * 午加餐饮食内容
     */
    private String lunchFoodAddJson;
    /**
     * 晚餐饮食内容
     */
    private String dinnerFoodJson;
    /**
     * 晚加餐饮食内容
     */
    private String dinnerFoodAddJson;
    /**
     * 食谱版本号 1旧版本 所有餐点的饮食内容存在一个字段(breakfastFoodJson)
     *          2新版本 各个餐点的内容分开存储
     */
    private Integer version;

    private Integer eohType;//0:糖尿病（非妊娠） 1:妊娠糖尿病

    /**
     * 成分统计 按分类，单位g json
     */
    private String ingredientStat;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIngredientStat() {
        return ingredientStat;
    }

    public void setIngredientStat(String ingredientStat) {
        this.ingredientStat = ingredientStat;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {

        this.version = version;

        if(version!=null && StringUtils.isBlank(this.programmeJson)){
            if(version==1 && !StringUtils.isBlank(this.breakfastFoodJson)){

                this.setProgrammeJson(this.breakfastFoodJson);

            } else if(version==2) {
                Map<String, Object> recMap = new HashMap<>(3);
                List<Map> breakfastFoodList = new ArrayList<Map>();
                List<Map> lunchFoodList = new ArrayList<Map>();
                List<Map> dinnerFoodList = new ArrayList<Map>();

                if(!StringUtils.isBlank(breakfastFoodJson)) {
                    breakfastFoodList = JSONArray.parseArray(breakfastFoodJson,Map.class);
                }
                if(!StringUtils.isBlank(breakfastFoodAddJson)) {
                    List<Map> tempbreakfastFoodList = JSONArray.parseArray(breakfastFoodAddJson,Map.class);;
                    breakfastFoodList.addAll(tempbreakfastFoodList);
                }
                if(breakfastFoodList!=null && breakfastFoodList.size()>0){
                    recMap.put("breakfastFoodList", breakfastFoodList);
                }

                if(!StringUtils.isBlank(lunchFoodJson)) {
                    lunchFoodList = JSONArray.parseArray(lunchFoodJson,Map.class);
                }
                if(!StringUtils.isBlank(lunchFoodAddJson)) {
                    List<Map> templunchFoodList = JSONArray.parseArray(lunchFoodAddJson,Map.class);
                    lunchFoodList.addAll(templunchFoodList);
                }
                if(lunchFoodList!=null&&lunchFoodList.size()>0){
                    recMap.put("lunchFoodList", lunchFoodList);
                }

                if(!StringUtils.isBlank(dinnerFoodJson)) {
                    dinnerFoodList = JSONArray.parseArray(dinnerFoodJson,Map.class);
                }
                if(!StringUtils.isBlank(dinnerFoodAddJson)) {
                    List<Map> tempdinnerFoodList = JSONArray.parseArray(dinnerFoodAddJson,Map.class);
                    dinnerFoodList.addAll(tempdinnerFoodList);
                }
                if(dinnerFoodList!=null && dinnerFoodList.size()>0){
                    recMap.put("dinnerFoodList", dinnerFoodList);
                }

                this.setProgrammeJson(JSONObject.toJSONString(recMap));
            }
        }
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

    public void setRecipesCaloricRelationMD5(String recipesCaloricRelationMD5) {
        this.recipesCaloricRelationMD5 = recipesCaloricRelationMD5;
    }

    public void setNutritionCateringMD5(String nutritionCateringMD5) {
        this.nutritionCateringMD5 = nutritionCateringMD5;
    }

    public void setProgrammeMap(Map programmeMap) {
        this.programmeMap = programmeMap;
    }

    public void setBreakfastFoodList(List<Map<String, Object>> breakfastFoodList) {
        this.breakfastFoodList = breakfastFoodList;
    }

    public void setLunchFoodList(List<Map<String, Object>> lunchFoodList) {
        this.lunchFoodList = lunchFoodList;
    }

    public void setDinnerFoodList(List<Map<String, Object>> dinnerFoodList) {
        this.dinnerFoodList = dinnerFoodList;
    }

    public String getRecipesCaloricId() {
        return recipesCaloricId;
    }

    public void setRecipesCaloricId(String recipesCaloricId) {
        this.recipesCaloricId = recipesCaloricId;
    }

    public String getNutritionCateringId() {
        return nutritionCateringId;
    }

    public void setNutritionCateringId(String nutritionCateringId) {
        this.nutritionCateringId = nutritionCateringId;
    }

    public String getCaloriesLevel() {
        return caloriesLevel;
    }

    public void setCaloriesLevel(String caloriesLevel) {
        this.caloriesLevel = caloriesLevel;
        this.recipesCaloricRelationMD5();
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
        this.recipesCaloricRelationMD5();
    }

    public String getCerealNum() {
        return cerealNum;
    }

    public void setCerealNum(String cerealNum) {
        this.cerealNum = cerealNum;
        this.recipesCaloricRelationMD5();
    }

    public String getProgrammeJson() {
        return programmeJson;
    }

    @SuppressWarnings("unchecked")
    public void setProgrammeJson(String programmeJson) {
        this.programmeJson = programmeJson;
        if(!StringUtils.isBlank(this.programmeJson)){
            this.programmeMap = JSON.parseObject(this.programmeJson.trim(),Map.class);
            if(this.programmeMap != null) {
                Object tempObject = this.programmeMap.get("breakfastFoodList");
                if(tempObject!=null) {
                    this.breakfastFoodList = (List<Map<String, Object>>)tempObject;
                } else {
                    this.breakfastFoodList = new ArrayList<>();
                }

                tempObject = this.programmeMap.get("lunchFoodList");
                if(tempObject!=null) {
                    this.lunchFoodList = (List<Map<String, Object>>)tempObject;
                } else {
                    this.lunchFoodList = new ArrayList<>();
                }

                tempObject = this.programmeMap.get("dinnerFoodList");
                if(tempObject!=null) {
                    this.dinnerFoodList = (List<Map<String, Object>>)tempObject;
                } else {
                    this.dinnerFoodList = new ArrayList<>();
                }
                this.setNutritionCateringMD5();
            }
        }
    }

	/*--------不可设置参数-----------*/
    /**
     * 食品热量份数关系唯一MD5
     */
    private String recipesCaloricRelationMD5;
    /**
     * 营养配餐唯一MD5
     */
    private String nutritionCateringMD5;
    /**
     * 计划Map
     */
    @SuppressWarnings("rawtypes")
    private Map programmeMap;
    /**
     * 计划JSON
     */
    private String programmeJson;
    /**
     * 早餐食物List
     */
    private List<Map<String, Object>> breakfastFoodList;
    /**
     * 午餐食物List
     */
    private List<Map<String, Object>> lunchFoodList;
    /**
     * 晚餐食物List
     */
    private List<Map<String, Object>> dinnerFoodList;

    public String getRecipesCaloricRelationMD5() {
        return recipesCaloricRelationMD5;
    }

    public String getNutritionCateringMD5() {
        return nutritionCateringMD5;
    }

    public List<Map<String, Object>> getBreakfastFoodList() {
        return breakfastFoodList;
    }

    public List<Map<String, Object>> getLunchFoodList() {
        return lunchFoodList;
    }

    public List<Map<String, Object>> getDinnerFoodList() {
        return dinnerFoodList;
    }

    @SuppressWarnings("rawtypes")
    public Map getProgrammeMap() {
        return programmeMap;
    }

	/*私有方法*/
    /**
     * 设置食品热量份数关系MD5
     * @author 李左河
     * @date 2018年3月21日 上午11:34:27
     */
    private void recipesCaloricRelationMD5() {
        // 拼接
        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isBlank(this.caloriesLevel)) {
            sb.append(this.caloriesLevel+"&");
        }
        if(!StringUtils.isBlank(this.foodNum)) {
            sb.append(this.foodNum+"&");
        }
        if(!StringUtils.isBlank(this.greaseNum)) {
            sb.append(this.greaseNum+"&");
        }
        if(!StringUtils.isBlank(this.fruitsNum)) {
            sb.append(this.fruitsNum+"&");
        }
        if(!StringUtils.isBlank(this.meatNum)) {
            sb.append(this.meatNum+"&");
        }
        if(!StringUtils.isBlank(this.vegetablesNum)) {
            sb.append(this.vegetablesNum+"&");
        }
        if(!StringUtils.isBlank(this.soymilkNum)) {
            sb.append(this.soymilkNum+"&");
        }
        if(!StringUtils.isBlank(this.cerealNum)) {
            sb.append(this.cerealNum+"&");
        }

        // 编码
        String str = sb.toString();
        if(!StringUtils.isBlank(str)) {

            this.recipesCaloricRelationMD5 = MD5Util.md5(str);
        }
    }
    /**
     * 设置营养配餐MD5
     * @author 李左河
     * @date 2018年3月21日 上午11:34:18
     */
    @SuppressWarnings("rawtypes")
    private void setNutritionCateringMD5(){
        // 取值
        List<Map> codeList = new ArrayList<>();
        for(int i = 0; i < this.breakfastFoodList.size(); i++){
            Map map=(Map)this.breakfastFoodList.get(i);
            codeList.add(map);
        }
        for(int i = 0; i < this.lunchFoodList.size(); i++){
            Map map=(Map)this.lunchFoodList.get(i);
            codeList.add(map);
        }
        for(int i = 0; i < this.dinnerFoodList.size(); i++){
            Map map=(Map)this.dinnerFoodList.get(i);
            codeList.add(map);
        }

        // 排序
        int size = codeList.size();
        for(int i = 0; i < size ; i++) {
            for(int j = i + 1; j < size; j++) {
                Map tempMap1 = codeList.get(i);
                Object tempObject1 = tempMap1.get("id");
                if(tempObject1!=null) {
                    Long tempLong1 = Long.parseLong(tempObject1.toString());
                    Map tempMap2 = codeList.get(j);
                    Object tempObject2 = tempMap2.get("id");
                    if(tempObject2!=null) {
                        Long tempLong2 = Long.parseLong(tempObject2.toString());
                        if(tempLong2 < tempLong1) {
                            codeList.set(i, tempMap2);
                            codeList.set(j, tempMap1);
                        }
                    }
                }
            }
        }

        // 拼接
        StringBuilder sb = new StringBuilder();
        for(Map map : codeList) {
            String tempStr = map.get("id")+","+map.get("fnum")+"&";
            sb.append(tempStr);
        }

        // 编码
        String str = sb.toString();
        if(!StringUtils.isBlank(str)) {
            this.nutritionCateringMD5 = MD5Util.md5(str);
        }
    }

    public String getMealsModel() {
        return mealsModel;
    }

    public void setMealsModel(String mealsModel) {
        this.mealsModel = mealsModel;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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
