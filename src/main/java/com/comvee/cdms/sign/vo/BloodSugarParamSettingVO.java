package com.comvee.cdms.sign.vo;

public class BloodSugarParamSettingVO {

    private String paramCode;
    private String paramCodeDesc;
    private String startTime;
    private String endTime;

    public String getParamCodeDesc() {
        return paramCodeDesc;
    }

    public void setParamCodeDesc(String paramCodeDesc) {
        this.paramCodeDesc = paramCodeDesc;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
