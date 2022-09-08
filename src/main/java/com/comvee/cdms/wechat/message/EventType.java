package com.comvee.cdms.wechat.message;

public enum EventType {

    /**
     * 订阅
     */
    Subscribe("subscribe"),
    /**
     * 取消订阅
     */
    Unsubscribe("unsubscribe"),
    /**
     * 已关注用户扫描带参数二维码
     */
    SCAN("SCAN"),
    /**
     * 上报地理位置
     */
    Location("location"),
    /**
     * 点击自定义菜单
     */
    CLICK("CLICK"),
    /**
     * 查看菜单
     */
    View("VIEW"),
    /**
     * 扫码推事件
     */
    Scancode_Push("scancode_push"),
    /**
     * 扫码推事件
     */
    Scancode_Waitmsg("scancode_waitmsg"),
    /**
     * 弹出系统拍照发图的事件
     */
    Pic_Sysphoto("pic_sysphoto"),
    /**
     * 弹出拍照或者相册发图的事件
     */
    Pic_Photo_OR_Album("pic_photo_or_album"),
    /**
     * 弹出微信相册发图器的事件
     */
    Pic_Weixin("pic_weixin"),
    /**
     * 弹出地理位置选择器的事件
     */
    Location_Select("location_select");

    
    private String value = "";

    EventType(String value) {
        this.value = value;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return value;
    }
}
