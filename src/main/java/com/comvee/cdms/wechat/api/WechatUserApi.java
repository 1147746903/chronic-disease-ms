package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.wechat.config.WechatApiUrl;
import com.comvee.cdms.wechat.model.WechatSession;
import com.comvee.cdms.wechat.model.WechatUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/31
 */
public class WechatUserApi extends WechatApi {

    /**
     * 默认语言
     */
    private final static String DEFAULT_LANG = "zh_CN";

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public static WechatUser getUserInfo(String accessToken, String openId){
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("lang", DEFAULT_LANG);
        queryParam.put("access_token", accessToken);
        queryParam.put("openid", openId);
        System.out.println("getUserInfo queryPram:\n" + JSON.toJSONString(queryParam));
        String res = HttpUtils.doGet(WechatApiUrl.URL_USER_INFO, queryParam);
        System.out.println("getUserInfo response:\n" + res);
        JSONObject jsonObject = resultHandler(res);
        WechatUser wechatUser = new WechatUser();
        wechatUser.setAvatarUrl(jsonObject.getString("headimgurl"));
        wechatUser.setGender(jsonObject.getInteger("sex"));
        wechatUser.setNickName(jsonObject.getString("nickname"));
        wechatUser.setProvince(jsonObject.getString("province"));
        wechatUser.setRemark(jsonObject.getString("remark"));
        wechatUser.setUnionid(jsonObject.getString("unionid"));
        wechatUser.setOpenid(jsonObject.getString("openid"));
        return wechatUser;
    }

    /**
     * 微信登录
     * @param appId
     * @param secret
     * @param jsCode
     * @return
     */
    public static WechatSession wxLogin(String appId, String secret,String jsCode){
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("appid", appId);
        queryParam.put("secret", secret);
        queryParam.put("js_code", jsCode);
        queryParam.put("grant_type", "authorization_code");
        String res = HttpUtils.doGet(WechatApiUrl.JSCODETOSESSION_URL, queryParam);
        JSONObject jsonObject = resultHandler(res);
        WechatSession wechatSession = new WechatSession();
        wechatSession.setOpenId(jsonObject.getString("openid"));
        wechatSession.setSessionKey(jsonObject.getString("session_key"));
        wechatSession.setUnionId(jsonObject.getString("unionid"));
        return wechatSession;
    }
}
