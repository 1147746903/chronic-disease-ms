package com.comvee.cdms.sign.po;

/**
 * @author wyc
 * @date 2020/3/5 17:27
 */
public class MemberParamValuePO {

    private String sid;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 血糖值JSON
     */
    private String paramValueJson;
    private Double highValue;
    private Double lowValue;
    private Double highRate;
    private Double lowRate;
    private Integer treeTimesHigh;
    private Double standardValue;
    private String paramDt;
    private Integer paramLevel;
    private Integer paramType;
    private Integer isLook; //是否已查看 1:已查看 0:未查看
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParamValueJson() {
        return paramValueJson;
    }

    public void setParamValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getHighRate() {
        return highRate;
    }

    public void setHighRate(Double highRate) {
        this.highRate = highRate;
    }

    public Double getLowRate() {
        return lowRate;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Integer getTreeTimesHigh() {
        return treeTimesHigh;
    }

    public void setTreeTimesHigh(Integer treeTimesHigh) {
        this.treeTimesHigh = treeTimesHigh;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public Integer getIsLook() {
        return isLook;
    }

    public void setIsLook(Integer isLook) {
        this.isLook = isLook;
    }

}
