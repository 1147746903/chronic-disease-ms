package com.comvee.cdms.dybloodsugar.constant;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenStarPlanConstant {
    public static final int PLAN_RECORDS = 1;
    public static final int PLAN_LEARN = 2;
    public static final int PLAN_UPDATE_DY_DATE = 3;
    public static final int PLAN_BEHAVIOR = 4;
    public static final int PLAN_DY_BS_EXCEPTION = 5;

    public static final Map<Integer, String> PLAN_NAME = new HashMap<Integer, String>(){
        {
            put(PLAN_RECORDS, "健康档案");
            put(PLAN_LEARN, "学习计划");
            put(PLAN_UPDATE_DY_DATE, "获取传感器数据");
            put(PLAN_BEHAVIOR, "行为方式");
            put(PLAN_DY_BS_EXCEPTION, "血糖异常检测");
        }
    };

    public static final String PLAN_BEHAVIOR_DIET = "DIET";
    public static final String PLAN_BEHAVIOR_SPORT = "SPORT";
    public static final String PLAN_DY_BS_EXCEPTION_BREAKFAST = "BREAKFAST";
    public static final String PLAN_DY_BS_EXCEPTION_LUNCH = "LUNCH";
    public static final String PLAN_DY_BS_EXCEPTION_DINNER = "DINNER";
    public static final String PLAN_UPDATE_DY_DATE_SUB = "UPDATE";
    public static final String PLAN_RECORDS_SUB = "RECORDS";


    //记一记类型 1 饮食 2 运动 3 用药
    public static final Integer REMEMBER_TYPE_DIET = 1;
    public static final Integer REMEMBER_TYPE_SPORT = 2;
    public static final Integer REMEMBER_TYPE_PHARMACY = 3;
    public static final Integer REMEMBER_TYPE_OTHER = 4;
    //时间类型 1：早餐 2：午餐 3 ：晚餐 4：其他

    public static final Integer REMEMBER_TIME_TYPE_DIET = 1;
    public static final Integer REMEMBER_TIME_TYPE_SPORT = 2;
    public static final Integer REMEMBER_TIME_TYPE_PHARMACY = 3;
    public static final Integer REMEMBER_TYPE_TIME_OTHER = 4;


    public static final Map<Integer, List<JSONObject>> DEFAULT_BEHAVIOR_PLAN = new HashMap<Integer, List<JSONObject>>(){
        {
            put(PLAN_BEHAVIOR, new ArrayList<JSONObject>() {
                {
                    add(new JSONObject() {
                        {
                            put("type", PLAN_BEHAVIOR_DIET);
                            put("value", "记录详细饮食习惯");
                        }
                    });
                    add(new JSONObject() {
                        {
                            put("type", PLAN_BEHAVIOR_SPORT);
                            put("value", "记录运动情况");
                        }
                    });
                }
            });
        }
    };

    public static final Map<Integer, List<JSONObject>> DEFAULT_UPDATE_DY_DATE_PLAN = new HashMap<Integer, List<JSONObject>>(){
        {
            put(PLAN_UPDATE_DY_DATE, new ArrayList<JSONObject>() {
                {
                    add(new JSONObject() {
                        {
                            put("type", PLAN_UPDATE_DY_DATE_SUB);
                            put("value", "上传一次传感器数据");
                        }
                    });
                }
            });
        }
    };

    public static final Map<Integer, List<JSONObject>> DEFAULT_PLAN_RECORDS_PLAN = new HashMap<Integer, List<JSONObject>>(){
        {
            put(PLAN_RECORDS, new ArrayList<JSONObject>() {
                {
                    add(new JSONObject() {
                        {
                            put("type", PLAN_RECORDS_SUB);
                            put("value", "完善健康档案");
                        }
                    });
                }
            });
        }
    };
}
