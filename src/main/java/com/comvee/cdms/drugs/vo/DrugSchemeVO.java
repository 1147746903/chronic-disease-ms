package com.comvee.cdms.drugs.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class DrugSchemeVO {

    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 剂量/规格
     */
    private String dose;
    /**
     * 1-降糖药，2-降脂药，3-降压药，4-胰岛素，5-常用药（用户添加）
     */
    private String drugType;
    /**
     * 每日次数
     */
    private String dayTime;
    /**
     * 用药周期 - 开始时间
     */
    private String startDt;
    /**
     * 用药周期 - 结束时间
     */
    private String endDt;
    /**
     * 备注
     */
    private String remark;
    /**
     * 药品单位
     */
    private String unit;

    /**
     * 服药周期  0 自定义 1 一周 2 二周 3 一个月
     */
    private Integer cycle;
    /**
     * 时间设置
     */
    private List<DrugSchemeTimeVO> timeList = new ArrayList<DrugSchemeTimeVO>();

    public String getDrugName() {
        return drugName;
    }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
    public String getStartDt() {
        return startDt;
    }
    public String getDayTime() {
        return dayTime;
    }
    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public List<DrugSchemeTimeVO> getTimeList() {
        return timeList;
    }
    public void setTimeList(List<DrugSchemeTimeVO> timeList) {
        this.timeList = timeList;
    }

    public Integer getCycle() {
        return cycle;
    }
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}