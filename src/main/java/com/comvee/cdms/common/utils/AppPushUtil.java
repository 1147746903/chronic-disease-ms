package com.comvee.cdms.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/1/4
 */
public class AppPushUtil {

    private final static String PLATFORM_CODE = "110";

    private final static String PUSH_URL = "http://push.izhangkong.com/push/sendMsg.do";

    private final static String PUSH_TITLE = "掌控健康";

    private final static Map<String,Object> HEADER_MAP = new HashMap<>();
    static {
        HEADER_MAP.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    }

    /**
     * 发送推送信息
     * @param pushInfo
     * @return
     */
    public static String sendPush(PushInfo pushInfo){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("platcode", pushInfo.getChannel());
        paramMap.put("title", PUSH_TITLE);
        paramMap.put("msg",pushInfo.getPushMessage());
        paramMap.put("token",pushInfo.getPushToken());
        paramMap.put("customInfo", pushInfo.getCustomInfo());
        paramMap.put("shownum", String.valueOf(pushInfo.getShowNum()));
        return HttpUtils.doPost(PUSH_URL ,paramMap , HEADER_MAP);
    }

    /**
     * 获取推送信息
     * @return
     */
    public static PushInfo getPushInfo(){
        return new AppPushUtil().new PushInfo();
    }

    public class PushInfo{
        private String pushMessage;
        private String pushToken;
        private Integer deviceType;
        private String channel;
        private String customInfo;
        private int showNum;


        public String getPushMessage() {
            return pushMessage;
        }

        public void setPushMessage(String pushMessage) {
            this.pushMessage = pushMessage;
        }

        public String getPushToken() {
            return pushToken;
        }

        public void setPushToken(String pushToken) {
            this.pushToken = pushToken;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getCustomInfo() {
            return customInfo;
        }

        public void setCustomInfo(String customInfo) {
            this.customInfo = customInfo;
        }

        public int getShowNum() {
            return showNum;
        }

        public void setShowNum(int showNum) {
            this.showNum = showNum;
        }
    }
}
