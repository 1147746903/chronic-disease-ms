package com.comvee.cdms.common.utils;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 字符串工具类
 * 
 * @author antelop 
 */
public class StringUtils {

    private final static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 判断字符串是否为空串
     * 
     * @param source
     *            字符串
     * 
     * @return 是否为空串
     */
    public static boolean isNullOrEmpty(String source) {
        return ((source == null) || (source.length() <= 0));
    }

    public static boolean isIgnoreContains(String source, String str) {
        return source.toUpperCase().contains(str.toUpperCase());
    }

    /**
     * 参数转字符串（为空则返回null）
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-2
     * @param obj
     * @return
     */
    public static String converParamToString(Object obj){
        return obj == null ? null : obj.toString();
    }
    
    /**
     * 字符串转码UTF-8
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-2
     * @param obj
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException 
     */
    public static String urlEncode(Object obj) throws BusinessException {
        try {
			return obj == null ? null :  URLEncoder.encode(obj.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("", "");
		}
    }
    
   
    
    /**
     * 判断字符串是否为空
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-5-26
     * @param s
     * @return true:空，false:非空
     */
    public  static boolean isBlank(String s){
        if(s == null){
            return true;
        }
        if("".equals(s)){
            return true;
        }
        if("".equals(s.trim())){
            return true;
        }
        return false;
    }
    
    /**
     * json字符串转化对象
     * 字符串为空则返回null
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-3
     * @param jsonText
     * @param t
     * @return
     */
    public static <T>T parseJSONStringToObject(String jsonText,Class<T> t){
        if(StringUtils.isBlank(jsonText)){
            return null;
        }
        return JSON.parseObject(jsonText, t);
    }
    
    /**
     * json字符串转化List
     * 字符串为空则返回null
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-3
     * @param jsonText
     * @param t
     * @return
     */
    public static <T> List<T> parseJSONStringToList(String jsonText,Class<T> t){
        if(StringUtils.isBlank(jsonText)){
            return new ArrayList<T>();
        }
        return JSON.parseArray(jsonText, t);
    }
    
    /**
     * List转成分割字符串
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-3
     * @param list
     * @param split 分隔符，默认,
     * @return
     */
    public static String convertListToSpiltString(List list,String split){
        if(list == null || list.size() == 0){
            return "";
        }
        if(isBlank(split)){
            split = DEFAULT_SPLIT_STRING;
        }
        StringBuilder resultAppend = new StringBuilder();
        for(Object obj : list){
            resultAppend.append(obj + split);
        }
        String result = resultAppend.toString();
        return result.substring(0, result.length() - 1);
    }
    
    /**
     * 默认分割符
     */
    private static final String DEFAULT_SPLIT_STRING = ","; 
    
    /**
     * 将String参数转化成Long型（用于预处理SQL语句填入的参数）
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-24
     * @param param
     * @return
     */
    public static Long parseStringToLong(String param) throws BusinessException {
        if(isBlank(param)){
            return null;
        }
        try {
            return Long.parseLong(param.trim());
        } catch (NumberFormatException e) {
            logger.error("parse String param to number for SQL error!param:" + param, e);
            throw new BusinessException("", "");
        }
    }
    
    /**
     * 将字符串切割成数组
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-6-25
     * @param param
     * @param regex
     * @return
     */
    public static List<String> converStringToList(String param,String regex){
    	// 为空返回null
        if(isBlank(param)){ 
            return null;
        }
      //默认切割符
        if(isBlank(regex)){ 
            regex = ",";
        }
        String[] arr =  param.split(regex);
        List<String> resultList = Arrays.asList(arr);
        return resultList;
    }
    
    /**
     * 处理字符串SQL参数,用于in查询
     * 功能说明:   
     * author：Suyz 
     * 创建时间：2015-12-22
     * @param param
     * @param spilt
     * @return
     */
    public static String sqlStringParamSpiltHandler(String param,String spilt){
        if(isBlank(param)){
            return "";
        }
        if(isBlank(spilt)){
            spilt = ",";
        }
        StringBuilder resultStr = new StringBuilder();
        StringTokenizer st = new StringTokenizer(param,spilt);
        while(st.hasMoreElements()){
            resultStr.append("'" + st.nextToken() + "',");
        }
        return resultStr.deleteCharAt(resultStr.lastIndexOf(",")).toString();
    }
    
    
    /**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param num
	 * @return
	 */
	public static String doubleTrans(double num) {
		if (num % 1.0 == 0) {
			return String.valueOf((long) num);
		}
		return String.valueOf(num);
	}

    /**
     *
     * @param obj
     * @return
     * @author antelop
     * @throws BusinessException
     * @date 2016年9月13日
     */
    public static Double converParamToDouble(Object obj) throws BusinessException {
        String str = StringUtils.converParamToString(obj);
        return converParamToDouble(str);
    }

    public static Double converParamToDouble(String str) throws BusinessException {
        try {
            return StringUtils.isBlank(str) ? null : Double.parseDouble(str);
        } catch (NumberFormatException e) {
            logger.error("parse String param to number!str:" + str, e);
            throw new BusinessException("", "");
        }
    }

    /**
     * 判断字符串能否转换为数字
     * @return
     */
    public static boolean checkParam(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 姓名脱敏
     */
    public static String protectedName(String userName){
        try {
            Integer RESULT_NAME_LENGTH =2;
            userName = userName.trim();
            char[] r = userName.toCharArray();
            String resultName = "";
            if(r.length == RESULT_NAME_LENGTH){
                resultName =  r[0]+"*";
            }
            if (r.length > RESULT_NAME_LENGTH) {
                String star = "";
                for (int i = 0; i < r.length-RESULT_NAME_LENGTH; i++) {
                    star=star+"*";
                }
                resultName = r[0]+star+r[r.length-1];
            }
            return resultName;
        } catch (Exception e) {
            return userName;
        }
    }

}
