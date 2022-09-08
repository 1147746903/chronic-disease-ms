package com.comvee.cdms.cdms.app.doctorapp.vo;

import java.io.Serializable;

public class UserLoginParamReqVO implements Serializable{
	
	private static final long serialVersionUID = 123456789L;
	
	/**用户登录的手机号*/
	private String mobilePhone;
	
	/**用户登录密码*/
	private String pwd;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	

}
