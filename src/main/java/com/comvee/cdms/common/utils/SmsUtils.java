package com.comvee.cdms.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.other.mapper.SmsHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SmsUtils {

    private final static Logger log = LoggerFactory.getLogger(SmsUtils.class);

    private final static String DEFAULT_SIGN = "【政和慢病管理】";

    private final static boolean STATUS;
    static {
        String openStatus = Config.getValueByKey("sms.open.status");
        if("1".equals(openStatus)){
            STATUS = true;
        }else{
            STATUS = false;
        }
    }

    public static boolean doSend(String content ,String mobile ,String ip){
        return doSend(content ,mobile ,ip ,DEFAULT_SIGN);
    }

    public static boolean doSend(String content ,String mobile ,String ip ,String sign){
        checkParam(content ,mobile);
        String seq = UUID.randomUUID().toString();
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("sendContent", content + sign);
        paramMap.put("destAddr", mobile);
        paramMap.put("srcAddr", ip);
        log.info("[{}] 开始执行短信发送,当前短信开关状态为:{}，请求参数:{}" , seq ,STATUS ,JSON.toJSONString(paramMap));
        if(!STATUS){
            return true;
        }
        String res = HttpUtils.doGet("http://sms.zhangkong.me/sms/submitMsgFromUni.do" ,paramMap);
        log.info("[{}] 执行短信发送请求成功，请求响应结果:{}" ,seq ,res);
        if(StringUtils.isBlank(res)){
            log.warn("[{}] 短信发送请求响应结果为空，发送失败" ,seq);
            return false;
        }
        smsHistoryResolve(mobile ,content ,ip ,res);
        JSONObject jsonObject = null;
        try{
            jsonObject = JSONObject.parseObject(res);
        }catch (Exception ex){
            log.warn("[{}] 短信发送请求响应结果不正确，发送失败" ,seq);
            return false;
        }
        Boolean result = jsonObject.getBoolean("success");
        if(result != null && result){
            return true;
        }else{
            log.warn("[{}] 短信发送失败,错误信息:{}" ,seq ,jsonObject.getString("msg"));
            return false;
        }
    }

    private static void checkParam(String content ,String mobile){
        if(StringUtils.isBlank(content)){
            throw new IllegalArgumentException("短信内容不能为空");
        }
        if(StringUtils.isBlank(mobile)){
            throw new IllegalArgumentException("接收短信的手机号码不能为空");
        }
        if(mobile.length() != 11 || !mobile.startsWith("1")){
            throw new IllegalArgumentException("非法的手机号码:" + mobile);
        }
    }

    private static void smsHistoryResolve(String mobilePhone ,String content ,String ip ,String responseText){
        SmsHistoryMapper smsHistoryMapper = ApplicationBeanUtils.getBean(SmsHistoryMapper.class);
        Map<String ,Object> paramMap = new HashMap<>();
        paramMap.put("mobilePhone" ,mobilePhone);
        paramMap.put("content" ,content);
        paramMap.put("ip" ,ip);
        paramMap.put("responseText" ,responseText);
        smsHistoryMapper.addSmsHistory(paramMap);
    }


}
