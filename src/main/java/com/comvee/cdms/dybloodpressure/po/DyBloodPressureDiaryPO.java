package com.comvee.cdms.dybloodpressure.po;

import java.io.Serializable;

/**
 * 患者动态血压指标设置(DyBloodPressureDiaryPO)实体类
 *
 * @author makejava
 * @since 2021-11-04 10:43:14
 */
public class DyBloodPressureDiaryPO implements Serializable {
    private static final Long serialVersionUID = -79380402441083489L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 操作者id
     */
    private String operationId;
    /**
     * 臂围
     */
    private String armLine;
    /**
     * 佩戴手臂
     */
    private Integer armsOn;
    /**
     * 袖带
     */
    private Integer cuff;
    /**
     * 工作是否倒班1是2否
     */
    private Integer workShift;
    /**
     * 午睡开始时间
     */
    private String noonSleepStart;
    /**
     * 午睡结束时间
     */
    private String noonSleepEnd;
    /**
     * 夜间睡眠开始时间
     */
    private String nightSleepStart;
    /**
     * 起床时间（清晨觉醒时间）
     */
    private String bedDt;
    /**
     * 早餐时间
     */
    private String breakfastDt;
    /**
     * 午餐时间
     */
    private String lunchDt;
    /**
     * 晚餐时间
     */
    private String dinnerDt;
    /**
     * 精神紧张信息
     */
    private String nervous;
    /**
     * 出现的症状
     */
    private String symptom;

    private String remark;

    private String startDt;//起始时间

    private String endDt;//结束时间

    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否有效 1有效,0无效
     */
    private Integer isValid;

    private String machineNo;//设备号

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

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getArmLine() {
        return armLine;
    }

    public void setArmLine(String armLine) {
        this.armLine = armLine;
    }

    public Integer getArmsOn() {
        return armsOn;
    }

    public void setArmsOn(Integer armsOn) {
        this.armsOn = armsOn;
    }

    public Integer getCuff() {
        return cuff;
    }

    public void setCuff(Integer cuff) {
        this.cuff = cuff;
    }

    public Integer getWorkShift() {
        return workShift;
    }

    public void setWorkShift(Integer workShift) {
        this.workShift = workShift;
    }

    public String getNoonSleepStart() {
        return noonSleepStart;
    }

    public void setNoonSleepStart(String noonSleepStart) {
        this.noonSleepStart = noonSleepStart;
    }

    public String getNoonSleepEnd() {
        return noonSleepEnd;
    }

    public void setNoonSleepEnd(String noonSleepEnd) {
        this.noonSleepEnd = noonSleepEnd;
    }

    public String getNightSleepStart() {
        return nightSleepStart;
    }

    public void setNightSleepStart(String nightSleepStart) {
        this.nightSleepStart = nightSleepStart;
    }

    public String getBedDt() {
        return bedDt;
    }

    public void setBedDt(String bedDt) {
        this.bedDt = bedDt;
    }

    public String getBreakfastDt() {
        return breakfastDt;
    }

    public void setBreakfastDt(String breakfastDt) {
        this.breakfastDt = breakfastDt;
    }

    public String getLunchDt() {
        return lunchDt;
    }

    public void setLunchDt(String lunchDt) {
        this.lunchDt = lunchDt;
    }

    public String getDinnerDt() {
        return dinnerDt;
    }

    public void setDinnerDt(String dinnerDt) {
        this.dinnerDt = dinnerDt;
    }

    public String getNervous() {
        return nervous;
    }

    public void setNervous(String nervous) {
        this.nervous = nervous;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }
}
