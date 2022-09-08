package com.comvee.cdms.defender.wechat.utils;

import com.comvee.cdms.defender.common.cfg.Constant;
import com.comvee.cdms.defender.common.cfg.InterveneConstant;
import com.comvee.cdms.common.utils.CodecTools;

public class UrlUtil {
	public static String jumpUrl(String url,String pid){
		if(url.indexOf("?") != -1){
			return url.concat("&param="+getParam(pid));
		}else{
			return url.concat("?param="+getParam(pid));
		}
	}
	
	public static String getParam(String pid){
		String temp = CodecTools.encriptDES3(pid);
		String param = temp.concat(Constant.PID_TEMP).concat(System.currentTimeMillis()+"");
		return param;
	}
	
	/**
	 * 获取干预地址
	 * @param interveneId
	 * @param pid
	 * @return
	 */
	public static String getInterveneUrl(Long interveneId,String pid){
		String url = InterveneConstant.JUMP_URL_INTERVENE+"?interveneId="+interveneId+"&pid="+pid+"&tid=&value=";
		url = UrlUtil.jumpUrl(url, pid);
		return url; 
	}
	
	public static String getTurnUrl(String url){
		return url;
	}
}
