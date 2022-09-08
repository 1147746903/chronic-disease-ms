package com.comvee.cdms.machine.common.utils;

import com.comvee.cdms.common.cfg.ConstantMessageKey;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.MessageTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.machine.common.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

;


public class ResponseTool {
	private static String DATA_TYPE = "application/json;charset=UTF-8";
	private final static Log log = LogFactory.getLog(ResponseTool.class);
	public static void responseJsonFormStr(String str,HttpServletRequest request, HttpServletResponse response){
		responseJsonFormStr(str, DATA_TYPE, request, response);
	}
	
	public static void responseStrFormStr(String str,HttpServletRequest request,HttpServletResponse response){
		responseJsonFormStr(str, "text/html;charset=UTF-8", request, response);
	}
	
	public static void responseJson(Object obj,HttpServletRequest request, HttpServletResponse response){
		String json = getJson(obj);
		responseJsonFormStr(json, request, response);
	}
	
	public static void responseStr(Object obj,HttpServletRequest request, HttpServletResponse response){
		String json = getJson(obj);
		responseStrFormStr(json, request, response);
	}

	public  static void exceptionReturnNew(DiabeteslsException e, HttpServletRequest request, HttpServletResponse response){
		if (e.getMessage().isEmpty()) {
			log.warn(e.getMsg());
		} else {
			log.error(e.getMessage(), e);
		}
		Result obj = new Result(e.getCode(),e.getMsg(), false);
		ResponseTool.responseJson(obj, request, response);
	}
	
	
	public static void print(String message, OutputStream os) throws Exception {
		OutputStreamWriter out = new OutputStreamWriter(os, "utf-8");
		out.write(message);
		out.flush();
		out.close();
	}
	
	 /**
	  * 返回值
	  * @param object
	  * @param dataType
	  */
	 public static String getJson(Object object) {
	     return JsonSerializer.objectToJson(object);
		/*
		 TODO:使用json-lib的 json序列化时间对象的时候需要设置时间的处理器,否则会将时间序列化成:
		{"date":8,"day":5,"hours":10,"minutes":34,"month":0,"nanos":489000000,"seconds":14,"time":1452220454489,"timezoneOffset":-480,"year":116},
		的格式.查看:http://blog.csdn.net/without0815/article/details/7793851
		 */
//		JsonConfig jf = new JsonConfig();
//		// 时间返回格式
//		jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		JSONArray jsonArray = JSONArray.fromObject(object, jf);
//		String json = "";
//		json = jsonArray.toString();
//		if (json.indexOf("[")==0) {
//			json = json.substring(1,json.length()-1);
//		}
//		return json;
	}
	
	/**
	 * 将json数据传到前台
	 */
	 private static void responseJsonFormStr(String str,String dataType,HttpServletRequest request, HttpServletResponse response){
			if (dataType.length()<=0) {
				//dataType = "text/json;charset=UTF-8";
				dataType = DATA_TYPE;//"application/json;charset=UTF-8";
			}
			String cb = request.getParameter("callback");
			if (cb!=null) {
				StringBuffer sb = new StringBuffer(cb);
				sb.append("(");
				sb.append(str);
				sb.append(")");
				str = sb.toString();				
			}
//			log.info("手机客户端请求返回值　：".concat(str));
			//log.info(str);
			response.setContentType(dataType);
			try {
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.getWriter().write(str);
			} catch (IOException e) {
				log.error("responseJson failed:",e);
			}catch (Exception e) {
				log.error("responseJson failed:",e);
			}
	 }


	public static void responseFailJson(HttpServletRequest request, HttpServletResponse response, Exception e) {
//		LogUtils.error(e);
	    log.error("exception:<<");
	    log.error(e);
	    log.error("exception:>>");
		String code = MessageTool.getMessage(ConstantMessageKey.COMMON_SYSTEM_ERROR_CODE);
		String msg = MessageTool.getMessage(ConstantMessageKey.COMMON_SYSTEM_ERROR);
		if(e instanceof DiabeteslsException) {
			DiabeteslsException wde = (DiabeteslsException)e;
			code = wde.getCode();
			msg = wde.getMsg();
		}
		responseFailJson(request, response, code, msg);
	}
	
	
	public static void responseFailJson(HttpServletRequest request, HttpServletResponse response, String errorCode, String errorMsg) {
		Result result = new Result();
		result.setSuccess(false);
		result.setCode(MessageTool.getMessage(ConstantMessageKey.COMMON_SYSTEM_ERROR_CODE));
		result.setMessage(MessageTool.getMessage(ConstantMessageKey.COMMON_SYSTEM_ERROR));
		if(errorCode != null && !"".equals(errorCode))
			result.setCode(errorCode);
		if(errorMsg != null && !"".equals(errorMsg))
			result.setMessage(errorMsg);
		
		responseJson(result, request, response);
	}
}