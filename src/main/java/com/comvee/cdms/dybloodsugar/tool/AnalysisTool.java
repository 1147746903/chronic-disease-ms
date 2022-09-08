package com.comvee.cdms.dybloodsugar.tool;

import com.comvee.cdms.common.utils.DateHelper;

public class AnalysisTool {

    /**
     * 获取用餐时间类型 1：早餐 2：午餐 3：晚餐 4：加餐
     * @param time 格式：HH:mm
     * @return
     */
    public static Integer getEatingTimeType(String time){
        if (DateHelper.compareTimeHM(time, "06:00") >= 0
                && DateHelper.compareTimeHM("09:00", time) >= 0) {
            return 1;
        }else if (DateHelper.compareTimeHM(time, "11:00") >= 0
                && DateHelper.compareTimeHM("14:00", time) >= 0) {
            return 2;
        }else if (DateHelper.compareTimeHM(time, "17:00") >= 0
                && DateHelper.compareTimeHM("20:00", time) >= 0) {
            return 3;
        }else {
            return 4;
        }
    }
}
