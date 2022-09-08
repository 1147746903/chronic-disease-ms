package com.comvee.cdms.sign.vo;

/**
 * @Author linr
 * @Date 2021/7/29
 */
public class BloodSugarListVO {

    private String sid;

    /**
     * 血糖code
     * param_code
     */
    private String paramCode;

    /**
     * 血糖值
     * param_value
     */
    private String paramValue;

    /**
     * 血糖等级
     * param_level
     */
    private Integer paramLevel;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;

    /**
     * 备注
     * remark
     */
    private String remark;

    /**
     * 来源
     * origin
     */
    private Integer origin;

    /**
     * member_id
     */
    private String memberId;

    /**
     * operator_id
     */
    private String operatorName;

    private String rangeMin;
    private String rangeMax;
    private int isValid;
    private String strongCode;//强化时段

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(String rangeMin) {
        this.rangeMin = rangeMin;
    }

    public String getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(String rangeMax) {
        this.rangeMax = rangeMax;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getStrongCode() {
        return strongCode;
    }

    public void setStrongCode(String strongCode) {
        this.strongCode = strongCode;
    }
}
