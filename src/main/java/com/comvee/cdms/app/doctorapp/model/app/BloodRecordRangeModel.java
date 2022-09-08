package com.comvee.cdms.app.doctorapp.model.app;

public class BloodRecordRangeModel {

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
    
    
    
}
