package com.comvee.cdms.complication.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EyesConstant {

    /**
     * 眼底筛查分级
     * 0 无法判断
     * 1 无明显视网膜病变
     * 2 轻度非增值期视网膜病变
     * 3 中度非增值期视网膜病变
     * 4 重度非增值期视网膜病变
     * 5 增值期视网膜病变
     */
    public static final int EYE_LEVEL_0 = 0;
    public static final int EYE_LEVEL_1 = 1;
    public static final int EYE_LEVEL_2 = 2;
    public static final int EYE_LEVEL_3 = 3;
    public static final int EYE_LEVEL_4 = 4;
    public static final int EYE_LEVEL_5 = 5;

    public static final Map<Integer,String> EYE_LEVEL_NAME = new HashMap<>();
    static {
        EYE_LEVEL_NAME.put(EYE_LEVEL_0, "无法判断");
        EYE_LEVEL_NAME.put(EYE_LEVEL_1, "无明显视网膜病变");
        EYE_LEVEL_NAME.put(EYE_LEVEL_2, "轻度非增值期视网膜病变");
        EYE_LEVEL_NAME.put(EYE_LEVEL_3, "中度非增值期视网膜病变");
        EYE_LEVEL_NAME.put(EYE_LEVEL_4, "重度非增值期视网膜病变");
        EYE_LEVEL_NAME.put(EYE_LEVEL_5, "增值期视网膜病变");
    }
}
