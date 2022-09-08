package com.comvee.cdms.bloodmonitor.po;

public class MemberMonitorTaskCardPO {
    private String sid;
    private String planId; //管理处方细项表关联id
    private String memberId; //患者名称
    private String dateCode; //日期code,长期对应星期几(1-7)
    private String paramCode; //血糖 (1:凌晨,2:空腹,3:早餐后,4:午餐前,5:午餐后,6:晚餐前,7:晚餐后,8:睡前)  血压(9:早期后 10:上午 11:下午  12:晚上)
    private String monitorTime; //监测点(数字坐标,对应param_code字段)
    private Integer monitorType; //监测类型 1:血糖 2:血压 3:脉搏
    private String monitorDt; //任务卡生成时间(年月日时分)
    private String insertDt; //插入时间
    private String modifyDt; //修改时间
    private String isMonitor; //是否已检测 1:已检测 2:未检测
    private Integer isValid; //是否有效, 1:有效 0:无效
    private Integer illnessType; //病种 1:糖尿病 2:高血压
    private String hospitalId; //任务卡生成序列(辅助字段,没有实际意义)
    private String monitorState; //任务卡监测状态 1:检测中 0:已停止
    private String paramValue; //血糖值

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(String isMonitor) {
        this.isMonitor = isMonitor;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public String getMonitorDt() {
        return monitorDt;
    }

    public void setMonitorDt(String monitorDt) {
        this.monitorDt = monitorDt;
    }

    public Integer getIllnessType() {
        return illnessType;
    }

    public void setIllnessType(Integer illnessType) {
        this.illnessType = illnessType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMonitorState() {
        return monitorState;
    }

    public void setMonitorState(String monitorState) {
        this.monitorState = monitorState;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "MemberMonitorTaskCardPO{" +
                "sid='" + sid + '\'' +
                ", planId='" + planId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", dateCode='" + dateCode + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", monitorTime=" + monitorTime +
                ", monitorType=" + monitorType +
                ", monitorDt='" + monitorDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isMonitor='" + isMonitor + '\'' +
                ", isValid=" + isValid +
                ", illnessType=" + illnessType +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
