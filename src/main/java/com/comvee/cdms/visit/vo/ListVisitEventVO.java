package com.comvee.cdms.visit.vo;

/**
 * @Author linr
 * @Date 2022/2/25
 */
public class ListVisitEventVO {

    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 事件类型
     */
    private Integer eventType;
    /**
     * 事件描述
     */
    private String eventDesc;
    /**
     * 事件内容
     */
    private String eventDetail;
    /**
     * 外键
     */
    private String foreignId;
    /**
     * 操作人id
     */
    private String operatorName;
    /**
     * 添加时间
     */
    private String insertDt;
    private String paramCode;
    private String paramName;
    private String hospitalId;

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

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
