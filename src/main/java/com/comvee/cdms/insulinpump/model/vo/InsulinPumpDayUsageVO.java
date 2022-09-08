package com.comvee.cdms.insulinpump.model.vo;

import java.util.Map;

public class InsulinPumpDayUsageVO {

    /**
     * 日期  yyyy-Mm-dd
     */
    private String date;
    /**
     * 每日总量
     */
    private Double dayTotal;
    /**
     * 基础总量
     */
    private Double basalTotal;
    /**
     * 追加总量
     */
    private Double addTotal;

    /**
     * 追加的时间段用量
     */
    private Map<String ,Double> addDosage;
    /**
     *  基础时间段用量
     */
    private Map<String ,Double> baseDosage;
    /**
     * 详细时间段用量
     */
    private Map<String ,Double> detailDosage;


    /**
     * 预计总量
     */
    private Double estimateTotal;
    /**
     * 预计基础总量
     */
    private Double estimateBaseTotal;
    /**
     * 临时追加总量
     */
    private Double temporaryAddTotal;

    /**
     * 临时追加剂量详情
     */
    private Map<String ,Double> temporaryAddDosage;

    /**
     *  预计基础剂量详情
     */
    private Map<String ,Double> estimateBaseDosage;

    /**
     * 是否进行过剂量调整 1 是 0 不是
     */
    private Integer dosageChange;
    /**
     * 胰岛素名
     */
    private String insulinName;

    /**
     * 预计追加剂量详情
     */
    private Map<String ,Double> estimateAddDosage;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }

    public Double getBasalTotal() {
        return basalTotal;
    }

    public void setBasalTotal(Double basalTotal) {
        this.basalTotal = basalTotal;
    }

    public Double getAddTotal() {
        return addTotal;
    }

    public void setAddTotal(Double addTotal) {
        this.addTotal = addTotal;
    }

    public Map<String, Double> getAddDosage() {
        return addDosage;
    }

    public void setAddDosage(Map<String, Double> addDosage) {
        this.addDosage = addDosage;
    }

    public Map<String, Double> getBaseDosage() {
        return baseDosage;
    }

    public void setBaseDosage(Map<String, Double> baseDosage) {
        this.baseDosage = baseDosage;
    }

    public Map<String, Double> getDetailDosage() {
        return detailDosage;
    }

    public void setDetailDosage(Map<String, Double> detailDosage) {
        this.detailDosage = detailDosage;
    }

    public Double getEstimateTotal() {
        return estimateTotal;
    }

    public void setEstimateTotal(Double estimateTotal) {
        this.estimateTotal = estimateTotal;
    }

    public Double getEstimateBaseTotal() {
        return estimateBaseTotal;
    }

    public void setEstimateBaseTotal(Double estimateBaseTotal) {
        this.estimateBaseTotal = estimateBaseTotal;
    }

    public Double getTemporaryAddTotal() {
        return temporaryAddTotal;
    }

    public void setTemporaryAddTotal(Double temporaryAddTotal) {
        this.temporaryAddTotal = temporaryAddTotal;
    }

    public Map<String, Double> getTemporaryAddDosage() {
        return temporaryAddDosage;
    }

    public void setTemporaryAddDosage(Map<String, Double> temporaryAddDosage) {
        this.temporaryAddDosage = temporaryAddDosage;
    }

    public Map<String, Double> getEstimateBaseDosage() {
        return estimateBaseDosage;
    }

    public void setEstimateBaseDosage(Map<String, Double> estimateBaseDosage) {
        this.estimateBaseDosage = estimateBaseDosage;
    }

    public Integer getDosageChange() {
        return dosageChange;
    }

    public void setDosageChange(Integer dosageChange) {
        this.dosageChange = dosageChange;
    }

    public String getInsulinName() {
        return insulinName;
    }

    public void setInsulinName(String insulinName) {
        this.insulinName = insulinName;
    }

    public Map<String, Double> getEstimateAddDosage() {
        return estimateAddDosage;
    }

    public void setEstimateAddDosage(Map<String, Double> estimateAddDosage) {
        this.estimateAddDosage = estimateAddDosage;
    }

    @Override
    public String toString() {
        return "InsulinPumpDayUsageVO{" +
                "date='" + date + '\'' +
                ", dayTotal=" + dayTotal +
                ", basalTotal=" + basalTotal +
                ", addTotal=" + addTotal +
                ", addDosage=" + addDosage +
                ", baseDosage=" + baseDosage +
                ", detailDosage=" + detailDosage +
                ", estimateTotal=" + estimateTotal +
                ", estimateBaseTotal=" + estimateBaseTotal +
                ", temporaryAddTotal=" + temporaryAddTotal +
                ", temporaryAddDosage=" + temporaryAddDosage +
                ", estimateBaseDosage=" + estimateBaseDosage +
                ", dosageChange=" + dosageChange +
                ", insulinName='" + insulinName + '\'' +
                ", estimateAddDosage=" + estimateAddDosage +
                '}';
    }
}
