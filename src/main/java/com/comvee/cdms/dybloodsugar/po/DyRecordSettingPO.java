package com.comvee.cdms.dybloodsugar.po;

public class DyRecordSettingPO {
    private String sid;
    private String memberId; //患者id
    private String sensorNo; //探头序列号
    private String insertDt; //插入时间
    private String modifyDt; //修改时间
    private String breakfastDt; //早餐时间
    private String lunchDt; //午餐时间
    private String dinnerDt; //晚餐时间
    private String sleepDt; //入睡时间
    private Double bloodSugarMin; //血糖目标范围最小值
    private Double bloodSugarMax; //血糖目标范围最大值
    private Double bloodSugarMinAfter; //餐后血糖目标范围最小值
    private Double bloodSugarMaxAfter; //餐后血糖目标范围最大值
    private Integer bloodSugarNorm; //血糖目标范围时间占比标准
    private Integer bloodSugarNormThan; //高于目标范围时间占比标准
    private Integer bloodSugarNormLess; //低于目标范围时间占比标准
    private Double bloodTimeRatioMax; //血糖时间占比最大值
    private Double bloodTimeRatioMin; //血糖时间占比最小值
    private Integer bloodSugarNormRatioMax; //血糖最大值的时间占比标准
    private Integer bloodSugarNormRatioMin; //血糖最小值的时间占比标准
    private Double medianTarget; //中位数目标
    private String glucoseMin; //低葡萄糖限值
    private Double hbA1cMax; //预估糖化血红蛋白
    private Integer isValid; //是否有效 1:有效 0:无效

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

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
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

    public String getSleepDt() {
        return sleepDt;
    }

    public void setSleepDt(String sleepDt) {
        this.sleepDt = sleepDt;
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

    public Integer getBloodSugarNorm() {
        return bloodSugarNorm;
    }

    public void setBloodSugarNorm(Integer bloodSugarNorm) {
        this.bloodSugarNorm = bloodSugarNorm;
    }

    public Integer getBloodSugarNormThan() {
        return bloodSugarNormThan;
    }

    public void setBloodSugarNormThan(Integer bloodSugarNormThan) {
        this.bloodSugarNormThan = bloodSugarNormThan;
    }

    public Integer getBloodSugarNormLess() {
        return bloodSugarNormLess;
    }

    public void setBloodSugarNormLess(Integer bloodSugarNormLess) {
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

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

    @Override
    public String toString() {
        return "DyRecordSettingPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", sensorNo='" + sensorNo + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", breakfastDt='" + breakfastDt + '\'' +
                ", lunchDt='" + lunchDt + '\'' +
                ", dinnerDt='" + dinnerDt + '\'' +
                ", sleepDt='" + sleepDt + '\'' +
                ", bloodSugarMin=" + bloodSugarMin +
                ", bloodSugarMax=" + bloodSugarMax +
                ", bloodSugarMinAfter=" + bloodSugarMinAfter +
                ", bloodSugarMaxAfter=" + bloodSugarMaxAfter +
                ", bloodSugarNorm=" + bloodSugarNorm +
                ", bloodSugarNormThan=" + bloodSugarNormThan +
                ", bloodSugarNormLess=" + bloodSugarNormLess +
                ", bloodTimeRatioMax=" + bloodTimeRatioMax +
                ", bloodTimeRatioMin=" + bloodTimeRatioMin +
                ", bloodSugarNormRatioMax=" + bloodSugarNormRatioMax +
                ", bloodSugarNormRatioMin=" + bloodSugarNormRatioMin +
                ", medianTarget=" + medianTarget +
                ", glucoseMin='" + glucoseMin + '\'' +
                ", hbA1cMax=" + hbA1cMax +
                ", isValid=" + isValid +
                '}';
    }
}
