package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyStaticsDTO implements Serializable {
    @NotBlank(message = "开始时间（startDt:yyyy-MM-dd），不可为空")
    private String startDt;

    @NotBlank(message = "结束时间（endDt:yyyy-MM-dd），不可为空")
    private String endDt;

    private Integer type;

    @NotBlank(message = "探头编号（sensorNo），不可为空")
    private String sensorNo;

    //来源 1 web  2 微信
    private Integer origin;

    //勾选日期集合
    private String dateList;

    //最新起始日期
    private String latestStartDate;
    //最新起始日期
    private String latestEndDate;

    public String getLatestStartDate() {
        return latestStartDate;
    }

    public void setLatestStartDate(String latestStartDate) {
        this.latestStartDate = latestStartDate;
    }

    public String getLatestEndDate() {
        return latestEndDate;
    }

    public void setLatestEndDate(String latestEndDate) {
        this.latestEndDate = latestEndDate;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getDateList() {
        return dateList;
    }

    public void setDateList(String dateList) {
        this.dateList = dateList;
    }
}
