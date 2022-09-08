package com.comvee.cdms.level.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyc
 * @date 2019/11/20 19:57
 */
public class MemberLevelConstant {

    /**
     * 是否确认 0:否 1:是 2 忽略
     */
    public static final Integer NO = 0;
    public static final Integer YES = 1;
    public static final Integer IGNORE = 2;

    /**
     * 1 :系统 2:调整
     */
    public static final Integer ORIGIN_SYS = 1;
    public static final Integer ORIGIN_HAND = 2;


    /**
     * 疾病类型 1 高血压 2 糖尿病
     */
    public static final Integer GXY_TYPE = 1;
    public static final Integer TNB_TYPE = 2;


    /**
     * 高血压分级 0其他 1 一级支持 2 二级支持 3 三级支持
     */
    public static final int GXY_LEVEL_ONE = 1;
    public static final int GXY_LEVEL_TWO = 2;
    public static final int GXY_LEVEL_THREE = 3;

    /**
     * 高血压分层 1 低危 2 中危 3 高危
     */
    public static final int GXY_LAYER_LOW= 1;
    public static final int GXY_LAYER_MID = 2;
    public static final int GXY_LAYER_HIGH = 3;

    static final Map<Integer ,String> GXY_LEVEL_TEXT_MAP = new HashMap<>();
    static final Map<Integer ,String> GXY_LAYER_TEXT_MAP = new HashMap<>();
    static {
        GXY_LEVEL_TEXT_MAP.put(GXY_LEVEL_ONE ,"1级");
        GXY_LEVEL_TEXT_MAP.put(GXY_LEVEL_TWO ,"2级");
        GXY_LEVEL_TEXT_MAP.put(GXY_LEVEL_THREE ,"3级");

        GXY_LAYER_TEXT_MAP.put(GXY_LAYER_LOW ,"低危");
        GXY_LAYER_TEXT_MAP.put(GXY_LAYER_MID ,"中危");
        GXY_LAYER_TEXT_MAP.put(GXY_LAYER_HIGH ,"高危");
    }

    public static String getGxyLevelAndLayerText(Integer level ,Integer layer){
        return GXY_LEVEL_TEXT_MAP.get(level) + "/" + GXY_LAYER_TEXT_MAP.get(layer);
    }


    /**
     * 糖尿病分级分标 1 红标 2 黄标 3 绿标
     */
    public static final int DIABETES_LEVEL_RED = 1;
    public static final int DIABETES_LEVEL_YELLOW = 2;
    public static final int DIABETES_LEVEL_GREEN = 3;
    static final Map<Integer ,String> DIABETES_LEVEL_TEXT_MAP = new HashMap<>();
    static {
        DIABETES_LEVEL_TEXT_MAP.put(DIABETES_LEVEL_RED, "红标");
        DIABETES_LEVEL_TEXT_MAP.put(DIABETES_LEVEL_YELLOW, "黄标");
        DIABETES_LEVEL_TEXT_MAP.put(DIABETES_LEVEL_GREEN, "绿标");
    }

    public static String diabetesLevelText(int level){
        return DIABETES_LEVEL_TEXT_MAP.get(level);
    }
}
