package com.comvee.cdms.prescription.vo;

/**
 * @author 李左河
 * @date 2018/8/2 11:13.
 */
public class PrescriptionTargetVO {
    @Override
    public String toString() {
        return "PrescriptionTargetVO{}";
    }

    /**
     * bmi
     */
    private String bmi;
    private String lowBmi;
    private String highBmi;
    /**
     * 空腹血糖
     */
    private String beforeBreakfast;
    private String lowBeforeBreakfast;
    private String highBeforeBreakfast;
    /**
     * 非空腹血糖
     */
    private String afterMeal;
    private String lowAfterMeal;
    private String highAfterMeal;
    /**
     * 糖化血红蛋白 当前值不使用，使用memberInfoJson
     */
    private String glycosylatedVal;
    private String lowGlycosylatedVal;
    private String highGlycosylatedVal;
    /**
     * 收缩压
     */
    private String systolicPress;
    private String lowSystolicPress;
    private String highSystolicPress;
    /**
     * 舒张压
     */
    private String diastolicPress;
    private String lowDiastolicPress;
    private String highDiastolicPress;
    /**
     * 总胆固醇
     */
    private String currentTCholesterol;
    private String lowTCholesterol;
    private String highTCholesterol;
    /**
     * 甘油三酯
     */
    private String triglyceride;
    private String lowTriglyceride;
    private String highTriglyceride;
    /**
     * 高密度酯蛋白
     */
    private String currentHDLCholesterol;
    private String lowHDLCholesterol;
    private String highHDLCholesterol;
    /**
     * 低密度酯蛋白
     */
    private String currentLDLCholesterol;
    private String lowLDLCholesterol;
    private String highLDLCholesterol;

    /**
     * 尿白蛋白/肌酐
     */
    private String acr;

    /**
     * 肾小球滤过滤
     */
    private String egfr;

    // 4.3.0 新增字段（妊娠管理处方用）
    private String beforeMealInfo;
    private String lowBeforeMeal;
    private String highBeforeMeal;

    private String afterMealOneHour;
    private String lowAfterMealOneHour;
    private String highAfterMealOneHour;

    private String beforeSleep;
    private String lowBeforeSleep;
    private String highBeforeSleep;

    private String beforedawnInfo;
    private String lowBeforedawnInfo;
    private String highBeforedawnInfo;



    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getLowBmi() {
        return lowBmi;
    }

    public void setLowBmi(String lowBmi) {
        this.lowBmi = lowBmi;
    }

    public String getHighBmi() {
        return highBmi;
    }

    public void setHighBmi(String highBmi) {
        this.highBmi = highBmi;
    }

    public String getBeforeBreakfast() {
        return beforeBreakfast;
    }

    public void setBeforeBreakfast(String beforeBreakfast) {
        this.beforeBreakfast = beforeBreakfast;
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

    public String getAfterMeal() {
        return afterMeal;
    }

    public void setAfterMeal(String afterMeal) {
        this.afterMeal = afterMeal;
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

    public String getGlycosylatedVal() {
        return glycosylatedVal;
    }

    public void setGlycosylatedVal(String glycosylatedVal) {
        this.glycosylatedVal = glycosylatedVal;
    }

    public String getLowGlycosylatedVal() {
        return lowGlycosylatedVal;
    }

    public void setLowGlycosylatedVal(String lowGlycosylatedVal) {
        this.lowGlycosylatedVal = lowGlycosylatedVal;
    }

    public String getHighGlycosylatedVal() {
        return highGlycosylatedVal;
    }

    public void setHighGlycosylatedVal(String highGlycosylatedVal) {
        this.highGlycosylatedVal = highGlycosylatedVal;
    }

    public String getSystolicPress() {
        return systolicPress;
    }

    public void setSystolicPress(String systolicPress) {
        this.systolicPress = systolicPress;
    }

    public String getLowSystolicPress() {
        return lowSystolicPress;
    }

    public void setLowSystolicPress(String lowSystolicPress) {
        this.lowSystolicPress = lowSystolicPress;
    }

    public String getHighSystolicPress() {
        return highSystolicPress;
    }

    public void setHighSystolicPress(String highSystolicPress) {
        this.highSystolicPress = highSystolicPress;
    }

    public String getDiastolicPress() {
        return diastolicPress;
    }

    public void setDiastolicPress(String diastolicPress) {
        this.diastolicPress = diastolicPress;
    }

    public String getLowDiastolicPress() {
        return lowDiastolicPress;
    }

    public void setLowDiastolicPress(String lowDiastolicPress) {
        this.lowDiastolicPress = lowDiastolicPress;
    }

    public String getHighDiastolicPress() {
        return highDiastolicPress;
    }

    public void setHighDiastolicPress(String highDiastolicPress) {
        this.highDiastolicPress = highDiastolicPress;
    }

    public String getCurrentTCholesterol() {
        return currentTCholesterol;
    }

    public void setCurrentTCholesterol(String currentTCholesterol) {
        this.currentTCholesterol = currentTCholesterol;
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

    public String getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(String triglyceride) {
        this.triglyceride = triglyceride;
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

    public String getCurrentHDLCholesterol() {
        return currentHDLCholesterol;
    }

    public void setCurrentHDLCholesterol(String currentHDLCholesterol) {
        this.currentHDLCholesterol = currentHDLCholesterol;
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

    public String getCurrentLDLCholesterol() {
        return currentLDLCholesterol;
    }

    public void setCurrentLDLCholesterol(String currentLDLCholesterol) {
        this.currentLDLCholesterol = currentLDLCholesterol;
    }

    public String getLowLDLCholesterol() {
        return lowLDLCholesterol;
    }

    public void setLowLDLCholesterol(String lowLDLCholesterol) {
        this.lowLDLCholesterol = lowLDLCholesterol;
    }

    public String getHighLDLCholesterol() {
        return highLDLCholesterol;
    }

    public void setHighLDLCholesterol(String highLDLCholesterol) {
        this.highLDLCholesterol = highLDLCholesterol;
    }

    public String getAcr() {
        return acr;
    }

    public void setAcr(String acr) {
        this.acr = acr;
    }

    public String getEgfr() {
        return egfr;
    }

    public void setEgfr(String egfr) {
        this.egfr = egfr;
    }

    public String getBeforeMealInfo() {
        return beforeMealInfo;
    }

    public void setBeforeMealInfo(String beforeMealInfo) {
        this.beforeMealInfo = beforeMealInfo;
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

    public String getAfterMealOneHour() {
        return afterMealOneHour;
    }

    public void setAfterMealOneHour(String afterMealOneHour) {
        this.afterMealOneHour = afterMealOneHour;
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

    public String getBeforeSleep() {
        return beforeSleep;
    }

    public void setBeforeSleep(String beforeSleep) {
        this.beforeSleep = beforeSleep;
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

    public String getBeforedawnInfo() {
        return beforedawnInfo;
    }

    public void setBeforedawnInfo(String beforedawnInfo) {
        this.beforedawnInfo = beforedawnInfo;
    }

    public String getLowBeforedawnInfo() {
        return lowBeforedawnInfo;
    }

    public void setLowBeforedawnInfo(String lowBeforedawnInfo) {
        this.lowBeforedawnInfo = lowBeforedawnInfo;
    }

    public String getHighBeforedawnInfo() {
        return highBeforedawnInfo;
    }

    public void setHighBeforedawnInfo(String highBeforedawnInfo) {
        this.highBeforedawnInfo = highBeforedawnInfo;
    }
}
