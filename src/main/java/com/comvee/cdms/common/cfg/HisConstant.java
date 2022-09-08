package com.comvee.cdms.common.cfg;

import java.util.HashMap;
import java.util.Map;

public class HisConstant {
    /**
     * 子项医嘱默认类型
     */
    public final static Integer YZ_ITEM_TYPE_OTHER = 4;
    /**
     * 临时医嘱默认值
     */
    public final static Integer NORM = 2;
    /**
     * 出院医嘱-今日出院
     */
    public final static String ZT001846="ZT001846";
    /**
     * 出院医嘱-自动出院
     */
    public final static String WFYZT001260="WFYZT001260";
    /**
     * 出院医嘱-宣告死亡
     */
    public final static String WFYZT001018="WFYZT001018";
    /**
     * 需要处理的医嘱 1 用药 2 护理 3 检验检查 4 其他 -护理、检验、其他、用药（西药、中草药、中成药）
     */
    public final static Map<String,Integer> YZ_ITEM_TYPE_MAP = new HashMap<String,Integer>();
    static {
        YZ_ITEM_TYPE_MAP.put("XY", 1);
        YZ_ITEM_TYPE_MAP.put("ZCY", 1);
        YZ_ITEM_TYPE_MAP.put("ZCYP", 1);
        YZ_ITEM_TYPE_MAP.put("HL", 2);
        //检验
        YZ_ITEM_TYPE_MAP.put("JY", 3);
        //执行检查
        YZ_ITEM_TYPE_MAP.put("ZXJC", 3);
        //一般检查
        YZ_ITEM_TYPE_MAP.put("YBJC", 3);
        YZ_ITEM_TYPE_MAP.put("QT", YZ_ITEM_TYPE_OTHER);
    }
    /**
     * 需要处理的医嘱 1 用药 2 护理 3 检验检查 4 其他 -护理、检验、其他、用药（西药、中草药、中成药）
     */
    public final static Map<Integer,String> YZ_ITEM_TYPE_MAP_CHINESE = new HashMap<Integer,String>();
    static {
        YZ_ITEM_TYPE_MAP_CHINESE.put(1,"用药");
        YZ_ITEM_TYPE_MAP_CHINESE.put(2,"护理");
        YZ_ITEM_TYPE_MAP_CHINESE.put(3,"检验检查");
        YZ_ITEM_TYPE_MAP_CHINESE.put(YZ_ITEM_TYPE_OTHER,"其他");
    }

    /**
     * 医嘱类型 1 长期医嘱 2 临时医嘱  -S长期医嘱/NORM临时医嘱
     */
    public final static Map<String,Integer> YZ_TYPE_MAP = new HashMap<String,Integer>();
    static {
        YZ_TYPE_MAP.put("S", 1);
        YZ_TYPE_MAP.put("NORM", 2);
    }
}
