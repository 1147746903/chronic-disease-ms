package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.CacheUtils;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.wechat.config.WechatApiUrl;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Su on 2018/8/2.
 */
public class WechatTokenApi extends WechatApi{

    private static Logger log = LoggerFactory.getLogger(WechatTokenApi.class);

    /**
     * 获取token
     * @param appName
     * @return
     */
    public static String getAccessToken(String appName){
        return CacheUtils.get(CacheUtils.tokenCache , appName);
    }

    /**
     * 申请token
     * @param appName
     * @return
     */
    public static String applyAccessToken(String appName){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(appName);
        if(wechatAppConfig == null || "".equals(wechatAppConfig)){
            throw new RuntimeException("找不到对应的微信配置,appName:" + appName);
        }
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("grant_type", "client_credential");
        queryParam.put("secret", wechatAppConfig.getSecret());

        // online-正式
        queryParam.put("appid", wechatAppConfig.getAppId());
        String result = HttpUtils.doGet(WechatApiUrl.URL_TOKEN, queryParam);
        JSONObject jsonObject = resultHandler(result);
        return jsonObject.getString("access_token");
/*        // dev-开发
        String result = HttpUtils.doGet("http://dm.izhangkong.com/wechat/getAccessToken");
        JSONObject jsonObject = JSONObject.parseObject(result);
        return CodecTools.decriptDES3(jsonObject.getString("obj"));*/
    }

    /**
     * 获取微信公众号授权信息
     * @param code
     * @return
     */
    public static JSONObject getWechatPublicAuthorization(String code){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.GONG_ZHONG_HAO);
        return getJsonObject(code, wechatAppConfig);
    }

    /**
     * 获取渠道微信公众号授权信息
     * @param code
     * @return
     */
    public static JSONObject getChannelWechatPublicAuthorization(String code){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.CHANNEL_GONG_ZHONG_HAO);
        return getJsonObject(code, wechatAppConfig);
    }

    private static JSONObject getJsonObject(String code, WechatAppConfig wechatAppConfig) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("grant_type", "authorization_code");
        queryParam.put("appid", wechatAppConfig.getAppId());
        queryParam.put("secret", wechatAppConfig.getSecret());
        queryParam.put("code", code);
        String result = HttpUtils.doGet(WechatApiUrl.CODE_EXCHANGE_ACCESS_TOKEN, queryParam);
        return resultHandler(result);
    }
}
