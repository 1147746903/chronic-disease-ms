package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodSugarSettingsVO {

    private Double bloodSugarMin; //血糖目标范围最小值
    private Double bloodSugarMax; //血糖目标范围最大值
    private Double bloodSugarMinAfter; //餐后血糖目标范围最小值
    private Double bloodSugarMaxAfter; //餐后血糖目标范围最大值
    private Double bloodSugarNorm; //血糖目标范围时间占比标准
    private Double bloodSugarNormThan; //高于目标范围时间占比标准
    private Double bloodSugarNormLess; //低于目标范围时间占比标准
    private Double bloodTimeRatioMax; //血糖时间占比最大值
    private Double bloodTimeRatioMin; //血糖时间占比最小值
    private Integer bloodSugarNormRatioMax; //血糖最大值的时间占比标准
    private Integer bloodSugarNormRatioMin; //血糖最小值的时间占比标准
    private Double medianTarget; //中位数目标
    private String glucoseMin; //低葡萄糖限值
    private Double hbA1cMax; //预估糖化血红蛋白

    public Double getBloodSugarMinAfter() {
        return bloodSugarMinAfter;
    }

    public void setBloodSugarMinAfter(Double bloodSugarMinAfter) {
        this.bloodSugarMinAfter = bloodSugarMinAfter;
    }

    public Double getBloodSugarMaxAfter() {
        return bloodSugarMaxAfter;
    }

    public void setBloodSugarMaxAfter(Double bloodSugarMaxAfter) {
        this.bloodSugarMaxAfter = bloodSugarMaxAfter;
    }

    public Integer getBloodSugarNormRatioMax() {
        return bloodSugarNormRatioMax;
    }

    public void setBloodSugarNormRatioMax(Integer bloodSugarNormRatioMax) {
        this.bloodSugarNormRatioMax = bloodSugarNormRatioMax;
    }

    public Integer getBloodSugarNormRatioMin() {
        return bloodSugarNormRatioMin;
    }

    public void setBloodSugarNormRatioMin(Integer bloodSugarNormRatioMin) {
        this.bloodSugarNormRatioMin = bloodSugarNormRatioMin;
    }

    public Double getMedianTarget() {
        return medianTarget;
    }

    public void setMedianTarget(Double medianTarget) {
        this.medianTarget = medianTarget;
    }

    public String getGlucoseMin() {
        return glucoseMin;
    }

    public void setGlucoseMin(String glucoseMin) {
        this.glucoseMin = glucoseMin;
    }

    public Double getHbA1cMax() {
        return hbA1cMax;
    }

    public void setHbA1cMax(Double hbA1cMax) {
        this.hbA1cMax = hbA1cMax;
    }

    public Double getBloodSugarMin() {
        return bloodSugarMin;
    }

    public void setBloodSugarMin(Double bloodSugarMin) {
        this.bloodSugarMin = bloodSugarMin;
    }

    public Double getBloodSugarMax() {
        return bloodSugarMax;
    }

    public void setBloodSugarMax(Double bloodSugarMax) {
        this.bloodSugarMax = bloodSugarMax;
    }

    public Double getBloodSugarNorm() {
        return bloodSugarNorm;
    }

    public void setBloodSugarNorm(Double bloodSugarNorm) {
        this.bloodSugarNorm = bloodSugarNorm;
    }

    public Double getBloodSugarNormThan() {
        return bloodSugarNormThan;
    }

    public void setBloodSugarNormThan(Double bloodSugarNormThan) {
        this.bloodSugarNormThan = bloodSugarNormThan;
    }

    public Double getBloodSugarNormLess() {
        return bloodSugarNormLess;
    }

    public void setBloodSugarNormLess(Double bloodSugarNormLess) {
        this.bloodSugarNormLess = bloodSugarNormLess;
    }

    public Double getBloodTimeRatioMax() {
        return bloodTimeRatioMax;
    }

    public void setBloodTimeRatioMax(Double bloodTimeRatioMax) {
        this.bloodTimeRatioMax = bloodTimeRatioMax;
    }

    public Double getBloodTimeRatioMin() {
        return bloodTimeRatioMin;
    }

    public void setBloodTimeRatioMin(Double bloodTimeRatioMin) {
        this.bloodTimeRatioMin = bloodTimeRatioMin;
    }
}
