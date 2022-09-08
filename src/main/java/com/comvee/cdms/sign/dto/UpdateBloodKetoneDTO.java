package com.comvee.cdms.sign.dto;


/**
 * @author: 罗伟雄
 * @date: 2020/1/20
 */
public class UpdateBloodKetoneDTO {

    private String sid;
    private String memberId;
    private String startDt;
    private String endDt;
    /**
     * 血酮值
     * param_value
     */
    private String paramValue;
    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;


    /**
     * 操作者类型 1 用户 2 医护
     * operator_type
     */
    private Integer operatorType;

    /**
     * operator_id
     */
    private String operatorId;

    /**
     * 来源 1：手动记录 2：设备 3：his--同步的his数据
     */
    private Integer origin;
    //1偏低 2偏高
    private Integer ketoneStatus;

    //是否是最新数据 1是 2否
    private Integer latestType;

    public Integer getLatestType() {
        return latestType;
    }

    public void setLatestType(Integer latestType) {
        this.latestType = latestType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getKetoneStatus() {
        return ketoneStatus;
    }

    public void setKetoneStatus(Integer ketoneStatus) {
        this.ketoneStatus = ketoneStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
