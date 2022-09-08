package com.comvee.cdms.insulinpump.model.bo;

import com.comvee.cdms.insulinpump.constant.InsulinPumpConstant;
import com.comvee.cdms.insulinpump.tool.InsulinPumpTool;

public class InsulinPumpTimeSlot {

    private String startTime;
    private String endTime;
    private Integer minuteDiff;


    public InsulinPumpTimeSlot(String timeSlot){
        String[] arr = timeSlot.split(InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT);
        this.startTime = arr[0];
        this.endTime = arr[1];
        this.minuteDiff = InsulinPumpTool.getMinuteDiffInSameDay(this.startTime ,this.endTime);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getMinuteDiff() {
        return minuteDiff;
    }

}
