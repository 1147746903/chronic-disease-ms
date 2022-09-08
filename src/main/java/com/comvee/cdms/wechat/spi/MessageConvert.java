package com.comvee.cdms.wechat.spi;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.wechat.message.event.*;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
public class MessageConvert {

    /**
     * 通用参数处理
     * @param document
     * @param eventMessage
     */
    private static void commonParamHandler(JSONObject jsonObject, EventMessage eventMessage){
        eventMessage.setCreateTime(jsonObject.getLong("CreateTime"));
        eventMessage.setFromUserName(jsonObject.getString("FromUserName"));
        eventMessage.setToUserName(jsonObject.getString("ToUserName"));
    }

    /**
     * 获取关注消息
     * @param document
     * @return
     */
    public static SubscribeEventMessage getSubscribeEventMessage(JSONObject jsonObject){
        SubscribeEventMessage subscribeEventMessage = new SubscribeEventMessage();
        commonParamHandler(jsonObject, subscribeEventMessage);
        return subscribeEventMessage;
    }

    /**
     * 获取扫码关注消息
     * @param document
     * @return
     */
    public static QrsceneSubscribeEventMessage getQrsceneSubscribeEventMessage(JSONObject jsonObject){
        QrsceneSubscribeEventMessage qrsceneSubscribeEventMessage = new QrsceneSubscribeEventMessage();
        commonParamHandler(jsonObject, qrsceneSubscribeEventMessage);
        qrsceneSubscribeEventMessage.setEventKey(jsonObject.getString("EventKey"));
        qrsceneSubscribeEventMessage.setTicket(jsonObject.getString("Ticket"));
        return qrsceneSubscribeEventMessage;
    }

    /**
     * 获取关注扫码数据
     * @param document
     * @return
     */
    public static QrsceneScanEventMessage getQrsceneScanEventMessage(JSONObject jsonObject){
        QrsceneScanEventMessage qrsceneScanEventMessage = new QrsceneScanEventMessage();
        commonParamHandler(jsonObject, qrsceneScanEventMessage);
        qrsceneScanEventMessage.setEventKey(jsonObject.getString("EventKey"));
        qrsceneScanEventMessage.setTicket(jsonObject.getString("Ticket"));
        return qrsceneScanEventMessage;
    }

    /**
     * 获取自定义菜单点击数据
     * @param jsonObject
     * @return
     */
    public static ClickEventMessage getClickEventMessage(JSONObject jsonObject){
        ClickEventMessage clickEventMessage = new ClickEventMessage();
        commonParamHandler(jsonObject, clickEventMessage);
        clickEventMessage.setEventKey(jsonObject.getString("EventKey"));
        return clickEventMessage;
    }
}
