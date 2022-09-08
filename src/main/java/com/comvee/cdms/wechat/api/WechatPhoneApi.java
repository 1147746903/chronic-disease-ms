package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.wechat.config.WechatApiUrl;
import com.comvee.cdms.wechat.model.WechatUserPhone;

import java.util.Collections;

public class WechatPhoneApi extends WechatApi {

    /**
     * 获取用户手机号码
     * @param accessToken
     * @param code
     * @return
     */
    public static WechatUserPhone getUserPhone(String accessToken , String code){
        String responseStr = HttpUtils.doPost(WechatApiUrl.GET_USER_PHONE_NUMBER + accessToken
                ,JSON.toJSONString(Collections.singletonMap("code" ,code)));
        JSONObject jsonObject = resultHandler(responseStr);
        return jsonObject.getObject("phone_info" ,WechatUserPhone.class);
    }

}
