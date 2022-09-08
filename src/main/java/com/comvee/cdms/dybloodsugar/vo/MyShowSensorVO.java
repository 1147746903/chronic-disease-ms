package com.comvee.cdms.dybloodsugar.vo;

public class MyShowSensorVO {

    private String sid;

    private String sensorNo;

    private String memberId;

    private String monitoringTime;

    private String modifyDt;

    private String insertDt;

    private Integer isValid;

    private Integer runStatus;

    private Integer operationType;

    private String operationId;

    /**
     *绑定者姓名
     */
    private String bindMemberName;
    /**
     * 监测开始时间
     */
    private String startDt;
    /**
     * 监测结束时间
     */
    private String endDt;

    /**
     * 分享类型 1:动态血糖
     */
    private Integer type;

    /**
     * 接收者姓名
     */
    private String receiveMemberName;

    private Integer bindType;

    public Integer getBindType() {
        return bindType;
    }

    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMonitoringTime() {
        return monitoringTime;
    }

    public void setMonitoringTime(String monitoringTime) {
        this.monitoringTime = monitoringTime;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getBindMemberName() {
        return bindMemberName;
    }

    public void setBindMemberName(String bindMemberName) {
        this.bindMemberName = bindMemberName;
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

    public String getReceiveMemberName() {
        return receiveMemberName;
    }

    public void setReceiveMemberName(String receiveMemberName) {
        this.receiveMemberName = receiveMemberName;
    }
}
