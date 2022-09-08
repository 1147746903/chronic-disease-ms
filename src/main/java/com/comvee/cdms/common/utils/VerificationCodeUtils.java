package com.comvee.cdms.common.utils;

import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.exception.BusinessException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VerificationCodeUtils {

    private final static Logger log = LoggerFactory.getLogger(VerificationCodeUtils.class);

    private static final String DEFAULT_TEXT = "本次验证码{0},有效期5分钟";
    private static final String DEFAULT_CODE = "123456";
    private static final boolean STATUS;
    static {
        if("1".equals(Config.getValueByKey("sms.verification.code.open"))){
            STATUS = true;
        }else{
            STATUS = false;
        }
    }

    /**
     * 发送短信验证码
     * @param mobile
     * @param businessCode
     * @param ip
     */
    public static void sendVerificationCode(String mobile ,String businessCode ,String ip){
        sendVerificationCode(mobile ,businessCode ,ip ,DEFAULT_TEXT);
    }

    /**
     * 发送短信验证码
     * @param mobile
     * @param businessCode
     * @param ip
     * @param content  短信内容
     */
    public synchronized static void sendVerificationCode(String mobile ,String businessCode ,String ip ,String content){
        String key = mobile + "|" + businessCode;
        VerificationCode verificationCode = cache.getIfPresent(key);
        if(verificationCode != null){
            return;
        }
        String code = getVerificationCode();
        log.info("开始发送短信验证码 --> 手机号:{},验证码:{},业务类型:{},短信验证码开启状态:{}" ,mobile ,code ,businessCode ,STATUS);
        if(STATUS){
            boolean sendResult = SmsUtils.doSend(MessageFormat.format(content ,code),mobile ,ip);
            if(!sendResult){
                throw new BusinessException("验证码下发失败,请重试");
            }
        }
        verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setSendTimestamp(System.currentTimeMillis());
        cache.put(key ,verificationCode);
    }
    /**
     * 发送短信验证码
     * @param mobile
     * @param businessCode
     * @param ip
     */
    public static void sendVerificationCodeV2(String mobile ,String businessCode ,String ip){
        sendVerificationCodeV2(mobile ,businessCode ,ip ,DEFAULT_TEXT);
    }

    /**
     * 发送短信验证码
     * @param mobile
     * @param businessCode
     * @param ip
     * @param content  短信内容
     */
    public synchronized static void sendVerificationCodeV2(String mobile ,String businessCode ,String ip ,String content){
        String key = mobile + "|" + businessCode;
        VerificationCode verificationCode = cache.getIfPresent(key);
        if(verificationCode != null){
            return;
        }
        String code = DEFAULT_CODE;
        log.info("开始发送短信验证码 --> 手机号:{},验证码:{},业务类型:{},短信验证码开启状态:{}" ,mobile ,code ,businessCode , false);
        verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setSendTimestamp(System.currentTimeMillis());
        cache.put(key ,verificationCode);
    }
    /**
     * 验证验证码
     * @param mobile
     * @param businessCode
     * @param code
     * @return 正确返回true
     */
    public static boolean checkVerificationCode(String mobile ,String businessCode ,String code){
        String key = mobile + "|" + businessCode;
        VerificationCode verificationCode = cache.getIfPresent(key);
        if(verificationCode == null){
            return false;
        }
        if(!verificationCode.getCode().equals(code)){
            return false;
        }
        cache.invalidate(key);
        return true;
    }

    private static String getVerificationCode(){
        if(!STATUS){
            return DEFAULT_CODE;
        }
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000 ,999999));
    }

    private static final Cache<String ,VerificationCode> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5 , TimeUnit.MINUTES)
            .maximumSize(5000)
            .initialCapacity(20)
            .build();

    private static class VerificationCode {

        private String code;
        private long sendTimestamp;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getSendTimestamp() {
            return sendTimestamp;
        }

        public void setSendTimestamp(long sendTimestamp) {
            this.sendTimestamp = sendTimestamp;
        }
    }
}
