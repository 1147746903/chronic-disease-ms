package com.comvee.cdms.dybloodsugar.dto;

public class GreenStarRememberDTO {
    private String memberId;
    private Integer rememberType;//记一记类型 1 饮食 2 运动 3 用药
    private  Integer timeType;//时间类型 1：早餐 2：午餐 3 ：晚餐 4：其他
    private String rememberDate; //记一记日期格式 yyyy-MM-dd
    private String begin;
    private String end;
    private Integer isSearchAll;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getRememberType() {
        return rememberType;
    }

    public void setRememberType(Integer rememberType) {
        this.rememberType = rememberType;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getRememberDate() {
        return rememberDate;
    }

    public void setRememberDate(String rememberDate) {
        this.rememberDate = rememberDate;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getIsSearchAll() {
        return isSearchAll;
    }

    public void setIsSearchAll(Integer isSearchAll) {
        this.isSearchAll = isSearchAll;
    }
}
