package com.comvee.cdms.insulinpump.model.vo;

import java.util.List;

public class InsulinPumpUsageVO {

    /**
     * 每日使用情况
     */
    private List<InsulinPumpDayUsageVO> dayUsageList;

    public List<InsulinPumpDayUsageVO> getDayUsageList() {
        return dayUsageList;
    }

    public void setDayUsageList(List<InsulinPumpDayUsageVO> dayUsageList) {
        this.dayUsageList = dayUsageList;
    }

    @Override
    public String toString() {
        return "InsulinPumpUsageVO{" +
                "dayUsageList=" + dayUsageList +
                '}';
    }
}
