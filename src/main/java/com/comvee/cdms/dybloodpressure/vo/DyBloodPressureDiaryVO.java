package com.comvee.cdms.dybloodpressure.vo;



import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.model.po.SportRecordPO;

import java.util.List;

/**
 * @Author linr
 * @Date 2021/11/8
 */
public class DyBloodPressureDiaryVO {
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;

    private String memberName;

    private String sex;

    private String age;

    private String mobilePhone;
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

    private String machineNo;//设备号

    private List<SportRecordPO> sportList;//运动列表
    private List<List<DrugsRecordPO>> drugList;//用药列表

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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public List<SportRecordPO> getSportList() {
        return sportList;
    }

    public void setSportList(List<SportRecordPO> sportList) {
        this.sportList = sportList;
    }

    public List<List<DrugsRecordPO>> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<List<DrugsRecordPO>> drugList) {
        this.drugList = drugList;
    }
}
