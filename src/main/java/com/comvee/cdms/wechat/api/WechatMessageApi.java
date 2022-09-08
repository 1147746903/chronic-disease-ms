package com.comvee.cdms.wechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.wechat.config.WechatApiUrl;
import com.comvee.cdms.wechat.message.TemplateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/29
 */
public class WechatMessageApi extends WechatApi {

    private final static Logger log = LoggerFactory.getLogger(WechatMessageApi.class);

    /**
     * 发送客服消息
     *
     * @param accessToken
     * @param message
     * @return
     * @throws Exception
     */
    private static String sendMsg(String accessToken, Map<String, Object> message) {
        Map<String,String> req = new HashMap<String,String>();
        req.put("access_token", accessToken);
        //创建请求对象
        String result = HttpUtils.doPost(WechatApiUrl.MESSAGE_URL, req, JSON.toJSONString(message));
        return result;
    }

    /**
     * 发送文本客服消息
     *
     * @param openId
     * @param text
     * @throws Exception
     */
    public static String sendText(String accessToken, String openId, String text) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("content", text);
        json.put("touser", openId);
        json.put("msgtype", "text");
        json.put("text", textObj);

        String result = sendMsg(accessToken, json);
        return result;
    }

    /**
     * 发送图片消息
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @return
     * @throws Exception
     */
    public static String SendImage(String accessToken, String openId, String media_id) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "image");
        json.put("image", textObj);
        String result = sendMsg(accessToken, json);
        return result;
    }

    /**
     * 发送语言回复
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @return
     * @throws Exception
     */
    public static String SendVoice(String accessToken, String openId, String media_id) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "voice");
        json.put("voice", textObj);
        String result = sendMsg(accessToken, json);
        return result;
    }

    /**
     * 发送视频回复
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @param title
     * @param description
     * @return
     * @throws Exception
     */
    public static String SendVideo(String accessToken, String openId, String media_id, String title, String description) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        textObj.put("title", title);
        textObj.put("description", description);

        json.put("touser", openId);
        json.put("msgtype", "video");
        json.put("video", textObj);
        String result = sendMsg(accessToken, json);
        return result;
    }

    /**
     * 发送音乐回复
     *
     * @param accessToken
     * @param openId
     * @param musicurl
     * @param hqmusicurl
     * @param thumb_media_id
     * @param title
     * @param description
     * @return
     * @throws Exception
     */
    public static String SendMusic(String accessToken, String openId, String musicurl, String hqmusicurl, String thumb_media_id, String title, String description) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("musicurl", musicurl);
        textObj.put("hqmusicurl", hqmusicurl);
        textObj.put("thumb_media_id", thumb_media_id);
        textObj.put("title", title);
        textObj.put("description", description);

        json.put("touser", openId);
        json.put("msgtype", "music");
        json.put("music", textObj);
        String result = sendMsg(accessToken, json);
        return result;
    }

    /**
     * 发送模板消息
     * @param accessToken
     * @param data
     * @return
     */
    public static JSONObject templateSend(String accessToken, TemplateData data) {
        String templateDataJson = JSON.toJSONString(data);
        log.info("开始发送微信模板消息，模板消息内容:{}" ,templateDataJson);
        Map<String,String> temp = new HashMap<String,String>();
        temp.put("access_token", accessToken);
        String result = HttpUtils.doPost(WechatApiUrl.TEMPLATE_SEND_URL, temp, templateDataJson);
        JSONObject jsonObject = resultHandler(result);
        return jsonObject;
    }
}
