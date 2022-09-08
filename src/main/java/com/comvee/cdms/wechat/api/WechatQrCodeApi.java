package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.upload.tool.UploadUtils;
import com.comvee.cdms.wechat.config.WechatApiUrl;
import com.comvee.cdms.wechat.model.WechatQrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

/**
 * @author: suyz
 * @date: 2019/3/5
 */
public class WechatQrCodeApi extends WechatApi {

    private final static Logger log = LoggerFactory.getLogger(WechatQrCodeApi.class);

    //临时的整型参数值
    public static final String ACTION_NAME_QR_SCENE = "QR_SCENE";
    //临时的字符串参数值
    public static final String ACTION_NAME_QR_STR_SCENE = "QR_STR_SCENE";
    //永久的整型参数值
    public static final String ACTION_NAME_QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    //永久的字符串参数值
    public static final String ACTION_NAME_QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

    /**
     * 生成临时的字符串型二维码
     * @param accessToken
     * @param sceneStr
     * @param expireSeconds
     * @return
     */
    public static WechatQrCode createTemporaryStrQrCode(String accessToken ,String sceneStr, long expireSeconds){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("expire_seconds" ,expireSeconds);
        jsonObject.put("action_name" ,ACTION_NAME_QR_STR_SCENE);
        JSONObject actionInfo = new JSONObject();
        actionInfo.put("scene" , Collections.singletonMap("scene_str" ,sceneStr));
        jsonObject.put("action_info" ,actionInfo);
        return createQrCode(accessToken ,jsonObject);
    }

    /**
     * 生成永久的字符串二维码
     * @param accessToken
     * @param sceneStr
     * @return
     */
    public static WechatQrCode createForeverStrQrCode(String accessToken ,String sceneStr){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action_name" ,ACTION_NAME_QR_LIMIT_STR_SCENE);
        JSONObject actionInfo = new JSONObject();
        actionInfo.put("scene" , Collections.singletonMap("scene_str" ,sceneStr));
        jsonObject.put("action_info" ,actionInfo);
        return createQrCode(accessToken ,jsonObject);
    }

    /**
     * 生成临小程序二维码-type-B
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static WechatQrCode createTemporaryStrMiniQrCode(String accessToken ,JSONObject jsonObject) throws Exception {
        String scene = jsonObject.getString("scene");
        if (scene==null || scene.length()>32)
        {
            throw new IllegalStateException("scene字符串值不能超过32位");
        }
        return createMiniWechatQrCode(accessToken ,jsonObject);
    }

    /**
     * 生成二维码
     * @param accessToken
     * @param postJson
     * @return
     */
    private static WechatQrCode createQrCode(String accessToken ,JSONObject postJson){
        String result = HttpUtils.doPost(WechatApiUrl.CREATE_QRCODE_URL, Collections.singletonMap("access_token" ,accessToken), JSON.toJSONString(postJson));
        log.info("createQrCode result:{}" ,result);
        return JSON.parseObject(result ,WechatQrCode.class);
    }

    private static WechatQrCode createMiniWechatQrCode(String accessToken,JSONObject postJson) throws Exception {
        String url = WechatApiUrl.CREATE_MINI_QRCODE_URL+accessToken;
        byte[] bytes = HttpUtils.doPostAsBytes(url ,null ,Collections.singletonMap("content-type" , MediaType.APPLICATION_JSON_VALUE)
                ,null ,postJson.toJSONString());
        JSONObject resultJson = null;
        try{
            resultJson = JSONObject.parseObject(new String(bytes ,"utf-8"));
        }catch (Exception e){}
        if(resultJson != null){
            throw new BusinessException(resultJson.getString("errcode") ,resultJson.getString("errmsg"));
        }
        WechatQrCode wechatQrCode = new WechatQrCode();
        String qrUrl = UploadUtils.uploadFile(bytes ,"jpg");
        wechatQrCode.setUrl(qrUrl);
        wechatQrCode.setTicket(Constant.DEFAULT_FOREIGN_ID);
        return wechatQrCode;
    }

    /**
     * 换取二维码
     * @param ticket
     * @return
     */
    public static byte[] showQrCode(String ticket){
        return HttpUtils.doGetAsBytes(WechatApiUrl.SHOW_QRCODE ,null ,Collections.singletonMap("ticket" , urlEncode(ticket)));
    }

    /**
     * encode编码
     * @param str
     * @return
     */
    private static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url编码失败" ,e);
            throw new BusinessException("url编码失败" , e);
        }
    }

}
