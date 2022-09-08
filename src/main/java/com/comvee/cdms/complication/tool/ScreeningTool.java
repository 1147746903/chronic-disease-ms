package com.comvee.cdms.complication.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/7
 */
public class ScreeningTool {

    public static final Map<Integer,String> REPORT_TEXT = new HashMap<>();
    static {
        REPORT_TEXT.put(1, "糖尿病下肢血管病变筛查");
        REPORT_TEXT.put(2, "糖尿病周围神经病变筛查");
        REPORT_TEXT.put(3, "眼底筛查");
    }

    /**
     * 根据类型获取报告名称
     * @param ScreeningType
     * @return
     */
    public static String getReportNameByType(int ScreeningType){
        return REPORT_TEXT.getOrDefault(ScreeningType , "其他");
    }
}
