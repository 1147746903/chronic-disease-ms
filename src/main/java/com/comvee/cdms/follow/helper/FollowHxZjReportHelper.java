package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyc
 * @date 2019/10/17 15:38
 */
public class FollowHxZjReportHelper {

    /**
     * 判断是否有过一天累计出现三次血糖偏高
     * @param list
     * @return true 是  false 否
     */
    public static boolean checkEveryDay(List<Map<String, Object>> list){
        if (null != list && list.size() > 0){
            for (Map<String, Object> map : list) {
                if (null != map.get("highCount") && !StringUtils.isBlank(map.get("highCount").toString())){
                    double highCount = Double.parseDouble(map.get("highCount").toString());
                    if (highCount >= 3){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有过连续三天早/中/晚餐前,餐后血糖偏高
     * @param list
     * @return true 是  false 否
     */
    public static boolean checkBloodSugar(List<Map<String, Object>> list){
        if (null != list && list.size() > 1){
            int count = 0; //记录连续天数
            for (int i = 1; i < list.size(); i++) {
                double upCount = 0;
                double downCount = 0;
                String upDate = "";
                String downDate = "";
                Map<String, Object> upMap = list.get(i - 1);
                if (null != upMap.get("highCount") && !StringUtils.isBlank(upMap.get("highCount").toString())){
                    upCount =  Double.parseDouble(upMap.get("highCount").toString());
                }
                if (null != upMap.get("dateTime") && !StringUtils.isBlank(upMap.get("dateTime").toString())){
                    upDate =  upMap.get("dateTime").toString();
                }
                Map<String, Object> downMap = list.get(i);
                if (null != downMap.get("highCount") && !StringUtils.isBlank(downMap.get("highCount").toString())){
                    downCount =  Double.parseDouble(downMap.get("highCount").toString());
                }
                if (null != downMap.get("dateTime") && !StringUtils.isBlank(downMap.get("dateTime").toString())){
                    downDate =  downMap.get("dateTime").toString();
                }
                int day = DateHelper.dateCompareGetDay(upDate, downDate);
                if (downCount >0 && upCount >0 && day == 1){
                    count ++;
                }else {
                    count = 0;
                }
                if (count ==  2){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 处理近一周血糖数据
     * @param sugarList
     * @return
     */
    public static List<Map<String,Object>> dealBloodSugarData(List<Map<String, Object>> sugarList){
        List<Map<String, Object>> result = new ArrayList<>();
        if (null != sugarList && sugarList.size() > 0){
            for (Map<String, Object> map : sugarList) {
                Map<String, Object> dealMap = new HashMap<>();
                dealMap.put("day",map.get("day"));
                dealMap.put("afterBreakfast",getValue(map.get("afterBreakfast")));
                dealMap.put("afterBreakfast1h",getValue(map.get("afterBreakfast1h")));
                dealMap.put("afterDinner",getValue(map.get("afterDinner")));
                dealMap.put("afterDinner1h",getValue(map.get("afterDinner1h")));
                dealMap.put("afterLunch",getValue(map.get("afterLunch")));
                dealMap.put("afterLunch1h",getValue(map.get("afterLunch1h")));
                dealMap.put("beforeBreakfast",getValue(map.get("beforeBreakfast")));
                dealMap.put("beforeDinner",getValue(map.get("beforeDinner")));
                dealMap.put("beforeLunch",getValue(map.get("beforeLunch")));
                dealMap.put("beforeSleep",getValue(map.get("beforeSleep")));
                dealMap.put("beforedawn",getValue(map.get("beforedawn")));
                result.add(dealMap);
            }
        }
        return result;
    }
    private static String getValue(Object obj){
        String value = "";
        if (null != obj && !StringUtils.isBlank(com.comvee.cdms.common.utils.JsonSerializer.objectToJson(obj))){
            Map<String, Object> map = com.comvee.cdms.common.utils.JsonSerializer.jsonToMap(com.comvee.cdms.common.utils.JsonSerializer.objectToJson(obj));
            if (null != map.get("paramValue") && !StringUtils.isBlank(map.get("paramValue").toString())){
                value = map.get("paramValue").toString();
            }
        }
        return value;
    }

}
