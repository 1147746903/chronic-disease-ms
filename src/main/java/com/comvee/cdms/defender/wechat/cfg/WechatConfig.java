package com.comvee.cdms.defender.wechat.cfg;

import java.util.ResourceBundle;

public class WechatConfig {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config/wechat");
	
	public static String getValueByKey(String key){
		return bundle.getString(key);
	}
	
	public static int getConnectionTimeout(){
		return Integer.parseInt(bundle.getString("wechat.http.connectionTimeout"));
	}
	
	public static int getReadTimeout(){
		return Integer.parseInt(bundle.getString("wechat.http.readTimeout"));
	}
	
}
