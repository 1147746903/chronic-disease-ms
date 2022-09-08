package com.comvee.cdms.member.bo;

/**
 * @author: suyz
 * @date: 2018/11/8
 */
public class RangeDialogueDataBO {

    private String highAfterMeal;
    private String highBeforeBreakfast;
    private String lowAfterMeal;
    private String lowBeforeBreakfast;

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

    public String getHighAfterMeal() {
        return highAfterMeal;
    }

    public void setHighAfterMeal(String highAfterMeal) {
        this.highAfterMeal = highAfterMeal;
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

    public String getLowBeforeBreakfast() {
        return lowBeforeBreakfast;
    }

    public void setLowBeforeBreakfast(String lowBeforeBreakfast) {
        this.lowBeforeBreakfast = lowBeforeBreakfast;
    }
}
