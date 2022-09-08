package com.comvee.cdms.common.utils;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * request工具
 * @author 李左河
 * 
 */
public class RequestTool {

	public static final String PARAMETER_MAP_ATTRIBUTE_KEY = RequestTool.class.getName() + "_PARAMETER_MAP_ATTRIBUTE_KEY";

	private static final Logger logger = LoggerFactory.getLogger(RequestTool.class);
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return 请求路径
	 */
	public static String getRequestPath(HttpServletRequest request) {
	    String servletPath = request.getServletPath();
	    return servletPath;
	}
	
	/**
	 * 获得请求IP
	 * 
	 * @param request
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		String unknown = "unknown";
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip != null && ip.indexOf(",") >= 0){
			ip = ip.split(",")[0].trim();
		}
		return ip;
	}
	/**
	 * 获得请求的全路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getReuqestAllPath(HttpServletRequest request){
		Map properties = getParameterMap(request);
		return request.getRequestURI().concat(JSON.toJSONString(properties));
	}

	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取请求接口
	 * @return
	 */
	public static String getRequestInterface(){
		HttpServletRequest request = getRequest();
		if(request == null){
			return "不能正确获取request对象，请确认使用环境";
		}
		return request.getRequestURI();
	}

	/**
	 * 获取请求参数
	 * @return
	 */
	public static String getRequestInterfaceParams(){
		HttpServletRequest request = getRequest();
		if(request == null){
			return "不能正确获取request对象，请确认使用环境";
		}
		String paramsJson = JSON.toJSONString(request.getParameterMap());
		if(paramsJson.length() > MAX_PARAMS_LENGTH){
			paramsJson = paramsJson.substring(0, MAX_PARAMS_LENGTH).concat("...");
		}
		return paramsJson;
	}

	private final static int MAX_PARAMS_LENGTH = 10000;
	
	public static Object getSession(String attribute){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession().getAttribute(attribute);
	}

	public static String getIpAddr() {
		return getIpAddr(getRequest());
	}

	public static Map<String, String[]> getParameterMap(HttpServletRequest request){
		Map<String, String[]> result = request.getParameterMap();
		if(!result.isEmpty()){
			request.setAttribute(PARAMETER_MAP_ATTRIBUTE_KEY ,result);
			return result;
		}
		Object attribute = request.getAttribute(PARAMETER_MAP_ATTRIBUTE_KEY);
		if(attribute == null){
			return Collections.emptyMap();
		}
		return (Map<String, String[]>) attribute;
	}

	/**
	 * 查找指定cookie
	 * @param cookies
	 * @param name
	 * @return
	 */
	public static String findCookie(Cookie[] cookies , String name){
		if(cookies == null || cookies.length == 0){
			return null;
		}
		for(Cookie cookie : cookies){
			if(name.equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}
}
