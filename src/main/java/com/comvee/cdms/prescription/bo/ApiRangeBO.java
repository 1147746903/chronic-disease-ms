package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiRangeBO implements Serializable {
    /**
     * 成员id
     */
    private String memberId;
    /**
     * 凌晨低
     */
    private String lowBeforedawn;
    /**
     * 凌晨高
     */
    private String highBeforedawn;
    /**
     * 空腹低
     */
    private String lowBeforeBreakfast;
    /**
     * 空腹高
     */
    private String highBeforeBreakfast;
    /**
     * 餐后低
     */
    private String lowAfterMeal;
    /**
     *  餐后高
     */
    private String highAfterMeal;

    /**
     * 餐后(一小时)低
     * low_after_meal
     */
    private String lowAfterMealOneHour;

    /**
     * 餐后(一小时)高
     * high_after_meal
     */
    private String highAfterMealOneHour;

    /**
     * 餐前低
     */
    private String lowBeforeMeal;
    /**
     * 餐前高
     */
    private String highBeforeMeal;
    /**
     * 睡前低
     */
    private String lowBeforeSleep;
    /**
     *  睡前高
     */
    private String highBeforeSleep;

    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     *  修改时间
     */
    private String modifyDt;
    /**
     * 收缩压上限
     */
    private String highSystolicPress;
    /**
     * 收缩压下限
     */
    private String lowSystolicPress;
    /**
     * 舒张压上限
     */
    private String highDiastolicPress;
    /**
     * 舒张压下限
     */
    private String lowDiastolicPress;
    /**
     * 糖化血红蛋白下限
     */
    private String lowGlycosylatedVal;
    /**
     * 糖化血红蛋白上限
     */
    private String highGlycosylatedVal;
    /**
     * 胆固醇下限
     */
    private String lowTCholesterol;
    /**
     * 胆固醇上限
     */
    private String highTCholesterol;
    /**
     * 甘油三酯下限
     */
    private String lowTriglyceride;
    /**
     * 甘油三酯上限
     */
    private String highTriglyceride;

    /**
     * 低密度脂蛋白下限
     */
    private String lowLDLCholesterol;
    /**
     * 低密度脂蛋白上限
     */
    private String highLDLCholesterol;
    /**
     * 高密度脂蛋白下限
     */
    private String lowHDLCholesterol;
    /**
     * 高密度脂蛋白上限
     */
    private String highHDLCholesterol;
    private String doctorId;
    private String currentLeaderId;

    public String getLowBeforedawn() {
        return lowBeforedawn;
    }

    public void setLowBeforedawn(String lowBeforedawn) {
        this.lowBeforedawn = lowBeforedawn;
    }

    public String getHighBeforedawn() {
        return highBeforedawn;
    }

    public void setHighBeforedawn(String highBeforedawn) {
        this.highBeforedawn = highBeforedawn;
    }

    public String getLowBeforeMeal() {
        return lowBeforeMeal;
    }

    public void setLowBeforeMeal(String lowBeforeMeal) {
        this.lowBeforeMeal = lowBeforeMeal;
    }

    public String getHighBeforeMeal() {
        return highBeforeMeal;
    }

    public void setHighBeforeMeal(String highBeforeMeal) {
        this.highBeforeMeal = highBeforeMeal;
    }

    public String getLowBeforeSleep() {
        return lowBeforeSleep;
    }

    public void setLowBeforeSleep(String lowBeforeSleep) {
        this.lowBeforeSleep = lowBeforeSleep;
    }

    public String getHighBeforeSleep() {
        return highBeforeSleep;
    }

    public void setHighBeforeSleep(String highBeforeSleep) {
        this.highBeforeSleep = highBeforeSleep;
    }

    public String getLowAfterMealOneHour() {
        return lowAfterMealOneHour;
    }

    public void setLowAfterMealOneHour(String lowAfterMealOneHour) {
        this.lowAfterMealOneHour = lowAfterMealOneHour;
    }

    public String getHighAfterMealOneHour() {
        return highAfterMealOneHour;
    }

    public void setHighAfterMealOneHour(String highAfterMealOneHour) {
        this.highAfterMealOneHour = highAfterMealOneHour;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLowBeforeBreakfast() {
        return lowBeforeBreakfast;
    }

    public void setLowBeforeBreakfast(String lowBeforeBreakfast) {
        this.lowBeforeBreakfast = lowBeforeBreakfast;
    }

    public String getHighBeforeBreakfast() {
        return highBeforeBreakfast;
    }

    public void setHighBeforeBreakfast(String highBeforeBreakfast) {
        this.highBeforeBreakfast = highBeforeBreakfast;
    }

    public String getLowAfterMeal() {
        return lowAfterMeal;
    }

    public void setLowAfterMeal(String lowAfterMeal) {
        this.lowAfterMeal = lowAfterMeal;
    }

    public String getHighAfterMeal() {
        return highAfterMeal;
    }

    public void setHighAfterMeal(String highAfterMeal) {
        this.highAfterMeal = highAfterMeal;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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

    public String getHighSystolicPress() {
        return highSystolicPress;
    }

    public void setHighSystolicPress(String highSystolicPress) {
        this.highSystolicPress = highSystolicPress;
    }

    public String getLowSystolicPress() {
        return lowSystolicPress;
    }

    public void setLowSystolicPress(String lowSystolicPress) {
        this.lowSystolicPress = lowSystolicPress;
    }

    public String getHighDiastolicPress() {
        return highDiastolicPress;
    }

    public void setHighDiastolicPress(String highDiastolicPress) {
        this.highDiastolicPress = highDiastolicPress;
    }

    public String getLowDiastolicPress() {
        return lowDiastolicPress;
    }

    public void setLowDiastolicPress(String lowDiastolicPress) {
        this.lowDiastolicPress = lowDiastolicPress;
    }

    public String getLowGlycosylatedVal() {
        return lowGlycosylatedVal;
    }

    public void setLowGlycosylatedVal(String lowGlycosylatedVal) {
        this.lowGlycosylatedVal = lowGlycosylatedVal;
    }

    public String getLowTCholesterol() {
        return lowTCholesterol;
    }

    public void setLowTCholesterol(String lowTCholesterol) {
        this.lowTCholesterol = lowTCholesterol;
    }

    public String getHighTCholesterol() {
        return highTCholesterol;
    }

    public void setHighTCholesterol(String highTCholesterol) {
        this.highTCholesterol = highTCholesterol;
    }

    public String getLowTriglyceride() {
        return lowTriglyceride;
    }

    public void setLowTriglyceride(String lowTriglyceride) {
        this.lowTriglyceride = lowTriglyceride;
    }

    public String getHighTriglyceride() {
        return highTriglyceride;
    }

    public void setHighTriglyceride(String highTriglyceride) {
        this.highTriglyceride = highTriglyceride;
    }

    public String getLowLDLCholesterol() {
        return lowLDLCholesterol;
    }

    public void setLowLDLCholesterol(String lowLDLCholesterol) {
        this.lowLDLCholesterol = lowLDLCholesterol;
    }

    public String getLowHDLCholesterol() {
        return lowHDLCholesterol;
    }

    public void setLowHDLCholesterol(String lowHDLCholesterol) {
        this.lowHDLCholesterol = lowHDLCholesterol;
    }

    public String getHighHDLCholesterol() {
        return highHDLCholesterol;
    }

    public void setHighHDLCholesterol(String highHDLCholesterol) {
        this.highHDLCholesterol = highHDLCholesterol;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCurrentLeaderId() {
        return currentLeaderId;
    }

    public void setCurrentLeaderId(String currentLeaderId) {
        this.currentLeaderId = currentLeaderId;
    }

    public String getHighLDLCholesterol() {
        return highLDLCholesterol;
    }

    public void setHighLDLCholesterol(String highLDLCholesterol) {
        this.highLDLCholesterol = highLDLCholesterol;
    }

    public String getHighGlycosylatedVal() {
        return highGlycosylatedVal;
    }

    public void setHighGlycosylatedVal(String highGlycosylatedVal) {
        this.highGlycosylatedVal = highGlycosylatedVal;
    }
}
