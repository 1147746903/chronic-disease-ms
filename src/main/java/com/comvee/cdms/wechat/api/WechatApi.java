package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.wechat.exception.WechatApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Su on 2018/8/2.
 */
public class WechatApi {

    private static final Logger log = LoggerFactory.getLogger(WechatApi.class);

    /**
     * 处理返回结果
     * @param result
     * @return
     */
    public static JSONObject resultHandler(String result){
        JSONObject jsonObject = JSON.parseObject(result);
        String errCode = jsonObject.getString("errcode");
        if(errCode != null && !"".equals(errCode)
                && !"0".equals(errCode)){
            log.warn("微信api调用异常,返回消息:" + result);
            throw new WechatApiException(errCode);
        }
        return jsonObject;
    }
}
