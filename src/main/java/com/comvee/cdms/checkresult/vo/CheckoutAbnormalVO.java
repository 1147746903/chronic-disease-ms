package com.comvee.cdms.checkresult.vo;

import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public class CheckoutAbnormalVO {

	private CheckoutPO checkout;
	private List<CheckoutDetailPO> checkoutDetailList;
	public CheckoutPO getCheckout() {
		return checkout;
	}
	public void setCheckout(CheckoutPO checkout) {
		this.checkout = checkout;
	}
	public List<CheckoutDetailPO> getCheckoutDetailList() {
		return checkoutDetailList;
	}
	public void setCheckoutDetailList(List<CheckoutDetailPO> checkoutDetailList) {
		this.checkoutDetailList = checkoutDetailList;
	}
	
	
}
