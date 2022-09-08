package com.comvee.cdms.sign.vo;


public class BloodGlucoseRecordPrintingVO {

    private String memberName; //患者名称
    private String departmentId; //科室id
    private String departmentName; //科室
    private String bedNo; //床位
    /**
     * 凌晨血糖
     */
    private String beforedawnSugar;
    /**
     * 空腹血糖
     */
    private String beforeBreakfastSugar;
    /**
     * 早餐后血糖
     */
    private String afterBreakfastSugar;
    /**
     * 午餐前血糖
     */
    private String beforeLunchSugar;
    /**
     * 午餐后血糖
     */
    private String afterLunchSugar;
    /**
     * 晚餐前血糖
     */
    private String beforeDinnerSugar;
    /**
     * 晚餐后血糖
     */
    private String afterDinnerSugar;
    /**
     * 睡前血糖
     */
    private String beforeSleepSugar;
    /**
     * 随机血糖
     */
    private String randomSugar;

    /**
     * 凌晨血糖
     */
    private Integer beforedawnSugarLevel;
    /**
     * 空腹血糖
     */
    private Integer beforeBreakfastSugarLevel;
    /**
     * 早餐后血糖
     */
    private Integer afterBreakfastSugarLevel;
    /**
     * 午餐前血糖
     */
    private Integer beforeLunchSugarLevel;
    /**
     * 午餐后血糖
     */
    private Integer afterLunchSugarLevel;
    /**
     * 晚餐前血糖
     */
    private Integer beforeDinnerSugarLevel;
    /**
     * 晚餐后血糖
     */
    private Integer afterDinnerSugarLevel;
    /**
     * 睡前血糖
     */
    private Integer beforeSleepSugarLevel;
    /**
     * 随机血糖
     */
    private Integer randomSugarLevel;

    private String recordDt; //记录日期

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getBeforedawnSugar() {
        return beforedawnSugar;
    }

    public void setBeforedawnSugar(String beforedawnSugar) {
        this.beforedawnSugar = beforedawnSugar;
    }

    public String getBeforeBreakfastSugar() {
        return beforeBreakfastSugar;
    }

    public void setBeforeBreakfastSugar(String beforeBreakfastSugar) {
        this.beforeBreakfastSugar = beforeBreakfastSugar;
    }

    public String getAfterBreakfastSugar() {
        return afterBreakfastSugar;
    }

    public void setAfterBreakfastSugar(String afterBreakfastSugar) {
        this.afterBreakfastSugar = afterBreakfastSugar;
    }

    public String getBeforeLunchSugar() {
        return beforeLunchSugar;
    }

    public void setBeforeLunchSugar(String beforeLunchSugar) {
        this.beforeLunchSugar = beforeLunchSugar;
    }

    public String getAfterLunchSugar() {
        return afterLunchSugar;
    }

    public void setAfterLunchSugar(String afterLunchSugar) {
        this.afterLunchSugar = afterLunchSugar;
    }

    public String getBeforeDinnerSugar() {
        return beforeDinnerSugar;
    }

    public void setBeforeDinnerSugar(String beforeDinnerSugar) {
        this.beforeDinnerSugar = beforeDinnerSugar;
    }

    public String getAfterDinnerSugar() {
        return afterDinnerSugar;
    }

    public void setAfterDinnerSugar(String afterDinnerSugar) {
        this.afterDinnerSugar = afterDinnerSugar;
    }

    public String getBeforeSleepSugar() {
        return beforeSleepSugar;
    }

    public void setBeforeSleepSugar(String beforeSleepSugar) {
        this.beforeSleepSugar = beforeSleepSugar;
    }

    public String getRandomSugar() {
        return randomSugar;
    }

    public void setRandomSugar(String randomSugar) {
        this.randomSugar = randomSugar;
    }

    public Integer getBeforedawnSugarLevel() {
        return beforedawnSugarLevel;
    }

    public void setBeforedawnSugarLevel(Integer beforedawnSugarLevel) {
        this.beforedawnSugarLevel = beforedawnSugarLevel;
    }

    public Integer getBeforeBreakfastSugarLevel() {
        return beforeBreakfastSugarLevel;
    }

    public void setBeforeBreakfastSugarLevel(Integer beforeBreakfastSugarLevel) {
        this.beforeBreakfastSugarLevel = beforeBreakfastSugarLevel;
    }

    public Integer getAfterBreakfastSugarLevel() {
        return afterBreakfastSugarLevel;
    }

    public void setAfterBreakfastSugarLevel(Integer afterBreakfastSugarLevel) {
        this.afterBreakfastSugarLevel = afterBreakfastSugarLevel;
    }

    public Integer getBeforeLunchSugarLevel() {
        return beforeLunchSugarLevel;
    }

    public void setBeforeLunchSugarLevel(Integer beforeLunchSugarLevel) {
        this.beforeLunchSugarLevel = beforeLunchSugarLevel;
    }

    public Integer getAfterLunchSugarLevel() {
        return afterLunchSugarLevel;
    }

    public void setAfterLunchSugarLevel(Integer afterLunchSugarLevel) {
        this.afterLunchSugarLevel = afterLunchSugarLevel;
    }

    public Integer getBeforeDinnerSugarLevel() {
        return beforeDinnerSugarLevel;
    }

    public void setBeforeDinnerSugarLevel(Integer beforeDinnerSugarLevel) {
        this.beforeDinnerSugarLevel = beforeDinnerSugarLevel;
    }

    public Integer getAfterDinnerSugarLevel() {
        return afterDinnerSugarLevel;
    }

    public void setAfterDinnerSugarLevel(Integer afterDinnerSugarLevel) {
        this.afterDinnerSugarLevel = afterDinnerSugarLevel;
    }

    public Integer getBeforeSleepSugarLevel() {
        return beforeSleepSugarLevel;
    }

    public void setBeforeSleepSugarLevel(Integer beforeSleepSugarLevel) {
        this.beforeSleepSugarLevel = beforeSleepSugarLevel;
    }

    public Integer getRandomSugarLevel() {
        return randomSugarLevel;
    }

    public void setRandomSugarLevel(Integer randomSugarLevel) {
        this.randomSugarLevel = randomSugarLevel;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }
}
