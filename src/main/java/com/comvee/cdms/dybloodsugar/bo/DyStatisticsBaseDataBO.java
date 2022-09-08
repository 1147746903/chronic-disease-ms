package com.comvee.cdms.dybloodsugar.bo;

public class DyStatisticsBaseDataBO {

    private Integer lowEventCount = 0;
    private Integer highEventCount = 0;
    private Integer normalEventCount = 0;
    //低血糖，高血糖，正常血糖，低于3.9血糖;低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
    private Boolean hasLowItem = false;
    private Boolean hasHighItem = false;
    private Boolean hasNormalItem = false;
    private Boolean hasLogItemOf3_9 = false;
    private Boolean hasLogItemOf4_0 = false;
    private Boolean hasLogItemOf13_9 = false;
    //低血糖，高血糖，正常血糖，低于3.9血糖;低于4.0血糖，低于13.9血糖事件开始时间标志
    private String lowEventLogSDt = null;
    private String highEventLogSDt = null;
    private String normalEventLogSDt = null;
    private String eventLogSDtOf3_9 = null;
    private String eventLogSDtOf4_0 = null;
    private String eventLogSDtOf13_9 = null;
    //低血糖，高血糖，正常血糖，低于3.9血糖;低于4.0血糖，低于13.9血糖事件结束时间标志
    private String lowEventLogEDt = null;
    private String highEventLogEDt = null;
    private String normalEventLogEDt = null;
    private String eventLogEDtOf3_9 = null;
    private String eventLogEDtOf4_0 = null;
    private String eventLogEDtOf13_9 = null;
    //低血糖，高血糖，正常血糖，低于3.9血糖;低于4.0血糖，低于13.9血糖事件总持续时间
    private Long lowEventDtCount = 0L;
    private Long highEventDtCount = 0L;
    private Long normalEventDtCount = 0L;
    private Long eventDtCountOf3_9 = 0L;
    private Long eventDtCountOf4_0 = 0L;
    private Long eventDtCountOf13_9 = 0L;
    //低血糖范围持续时间占比
    private String awiTimeRateOfLow = "0.0";
    //高血糖范围持续时间占比
    private String awiTimeRateOfHigh = "0.0";
    //正常血糖范围持续时间占比（3.9-10）
    private String awiTimeRateOfNormal = "0.0";
    //低3.9,低4.0,低13.9占比
    private String awiTimeRateOf3_9 = "0.0";
    private String awiTimeRateOf4_0 = "0.0";
    private String awiTimeRateOf13_9 = "0.0";

    public Integer getLowEventCount() {
        return lowEventCount;
    }

    public void setLowEventCount(Integer lowEventCount) {
        this.lowEventCount = lowEventCount;
    }

    public Integer getHighEventCount() {
        return highEventCount;
    }

    public void setHighEventCount(Integer highEventCount) {
        this.highEventCount = highEventCount;
    }

    public Integer getNormalEventCount() {
        return normalEventCount;
    }

    public void setNormalEventCount(Integer normalEventCount) {
        this.normalEventCount = normalEventCount;
    }

    public Boolean getHasLowItem() {
        return hasLowItem;
    }

    public void setHasLowItem(Boolean hasLowItem) {
        this.hasLowItem = hasLowItem;
    }

    public Boolean getHasHighItem() {
        return hasHighItem;
    }

    public void setHasHighItem(Boolean hasHighItem) {
        this.hasHighItem = hasHighItem;
    }

    public Boolean getHasNormalItem() {
        return hasNormalItem;
    }

    public void setHasNormalItem(Boolean hasNormalItem) {
        this.hasNormalItem = hasNormalItem;
    }

    public Boolean getHasLogItemOf3_9() {
        return hasLogItemOf3_9;
    }

    public void setHasLogItemOf3_9(Boolean hasLogItemOf3_9) {
        this.hasLogItemOf3_9 = hasLogItemOf3_9;
    }

    public Boolean getHasLogItemOf4_0() {
        return hasLogItemOf4_0;
    }

    public void setHasLogItemOf4_0(Boolean hasLogItemOf4_0) {
        this.hasLogItemOf4_0 = hasLogItemOf4_0;
    }

    public Boolean getHasLogItemOf13_9() {
        return hasLogItemOf13_9;
    }

    public void setHasLogItemOf13_9(Boolean hasLogItemOf13_9) {
        this.hasLogItemOf13_9 = hasLogItemOf13_9;
    }

    public String getLowEventLogSDt() {
        return lowEventLogSDt;
    }

    public void setLowEventLogSDt(String lowEventLogSDt) {
        this.lowEventLogSDt = lowEventLogSDt;
    }

    public String getHighEventLogSDt() {
        return highEventLogSDt;
    }

    public void setHighEventLogSDt(String highEventLogSDt) {
        this.highEventLogSDt = highEventLogSDt;
    }

    public String getNormalEventLogSDt() {
        return normalEventLogSDt;
    }

    public void setNormalEventLogSDt(String normalEventLogSDt) {
        this.normalEventLogSDt = normalEventLogSDt;
    }

    public String getEventLogSDtOf3_9() {
        return eventLogSDtOf3_9;
    }

    public void setEventLogSDtOf3_9(String eventLogSDtOf3_9) {
        this.eventLogSDtOf3_9 = eventLogSDtOf3_9;
    }

    public String getEventLogSDtOf4_0() {
        return eventLogSDtOf4_0;
    }

    public void setEventLogSDtOf4_0(String eventLogSDtOf4_0) {
        this.eventLogSDtOf4_0 = eventLogSDtOf4_0;
    }

    public String getEventLogSDtOf13_9() {
        return eventLogSDtOf13_9;
    }

    public void setEventLogSDtOf13_9(String eventLogSDtOf13_9) {
        this.eventLogSDtOf13_9 = eventLogSDtOf13_9;
    }

    public String getLowEventLogEDt() {
        return lowEventLogEDt;
    }

    public void setLowEventLogEDt(String lowEventLogEDt) {
        this.lowEventLogEDt = lowEventLogEDt;
    }

    public String getHighEventLogEDt() {
        return highEventLogEDt;
    }

    public void setHighEventLogEDt(String highEventLogEDt) {
        this.highEventLogEDt = highEventLogEDt;
    }

    public String getNormalEventLogEDt() {
        return normalEventLogEDt;
    }

    public void setNormalEventLogEDt(String normalEventLogEDt) {
        this.normalEventLogEDt = normalEventLogEDt;
    }

    public String getEventLogEDtOf3_9() {
        return eventLogEDtOf3_9;
    }

    public void setEventLogEDtOf3_9(String eventLogEDtOf3_9) {
        this.eventLogEDtOf3_9 = eventLogEDtOf3_9;
    }

    public String getEventLogEDtOf4_0() {
        return eventLogEDtOf4_0;
    }

    public void setEventLogEDtOf4_0(String eventLogEDtOf4_0) {
        this.eventLogEDtOf4_0 = eventLogEDtOf4_0;
    }

    public String getEventLogEDtOf13_9() {
        return eventLogEDtOf13_9;
    }

    public void setEventLogEDtOf13_9(String eventLogEDtOf13_9) {
        this.eventLogEDtOf13_9 = eventLogEDtOf13_9;
    }

    public Long getLowEventDtCount() {
        return lowEventDtCount;
    }

    public void setLowEventDtCount(Long lowEventDtCount) {
        this.lowEventDtCount = lowEventDtCount;
    }

    public Long getHighEventDtCount() {
        return highEventDtCount;
    }

    public void setHighEventDtCount(Long highEventDtCount) {
        this.highEventDtCount = highEventDtCount;
    }

    public Long getNormalEventDtCount() {
        return normalEventDtCount;
    }

    public void setNormalEventDtCount(Long normalEventDtCount) {
        this.normalEventDtCount = normalEventDtCount;
    }

    public Long getEventDtCountOf3_9() {
        return eventDtCountOf3_9;
    }

    public void setEventDtCountOf3_9(Long eventDtCountOf3_9) {
        this.eventDtCountOf3_9 = eventDtCountOf3_9;
    }

    public Long getEventDtCountOf4_0() {
        return eventDtCountOf4_0;
    }

    public void setEventDtCountOf4_0(Long eventDtCountOf4_0) {
        this.eventDtCountOf4_0 = eventDtCountOf4_0;
    }

    public Long getEventDtCountOf13_9() {
        return eventDtCountOf13_9;
    }

    public void setEventDtCountOf13_9(Long eventDtCountOf13_9) {
        this.eventDtCountOf13_9 = eventDtCountOf13_9;
    }

    public String getAwiTimeRateOfLow() {
        return awiTimeRateOfLow;
    }

    public void setAwiTimeRateOfLow(String awiTimeRateOfLow) {
        this.awiTimeRateOfLow = awiTimeRateOfLow;
    }

    public String getAwiTimeRateOfHigh() {
        return awiTimeRateOfHigh;
    }

    public void setAwiTimeRateOfHigh(String awiTimeRateOfHigh) {
        this.awiTimeRateOfHigh = awiTimeRateOfHigh;
    }

    public String getAwiTimeRateOfNormal() {
        return awiTimeRateOfNormal;
    }

    public void setAwiTimeRateOfNormal(String awiTimeRateOfNormal) {
        this.awiTimeRateOfNormal = awiTimeRateOfNormal;
    }

    public String getAwiTimeRateOf3_9() {
        return awiTimeRateOf3_9;
    }

    public void setAwiTimeRateOf3_9(String awiTimeRateOf3_9) {
        this.awiTimeRateOf3_9 = awiTimeRateOf3_9;
    }

    public String getAwiTimeRateOf4_0() {
        return awiTimeRateOf4_0;
    }

    public void setAwiTimeRateOf4_0(String awiTimeRateOf4_0) {
        this.awiTimeRateOf4_0 = awiTimeRateOf4_0;
    }

    public String getAwiTimeRateOf13_9() {
        return awiTimeRateOf13_9;
    }

    public void setAwiTimeRateOf13_9(String awiTimeRateOf13_9) {
        this.awiTimeRateOf13_9 = awiTimeRateOf13_9;
    }
}
