package com.comvee.cdms.other.service;


import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.wechat.message.event.*;
import com.comvee.cdms.wechat.message.output.OutputMessage;

/**
 * 接收事件推送
 *
 * @author qsyang
 * @version 1.0
 */
public interface EventMessageService {

    /**
     * 关注事件
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage subscribe(SubscribeEventMessage msg);

    /**
     * 取消关注事件
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage unSubscribe(UnSubscribeEventMessage msg);

    /**
     * 用户未关注，扫描带参数二维码事件
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage qrsceneSubscribe(QrsceneSubscribeEventMessage msg);

    /**
     * 用户已关注，扫描带参数二维码事件
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage qrsceneScan(QrsceneScanEventMessage msg);


    /**
     * 上报地理位置事件
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage location(LocationEventMessage msg);

    /**
     * 自定义菜单事件,点击菜单拉取消息时的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage click(ClickEventMessage msg);

    /**
     * 自定义菜单事件,点击菜单跳转链接时的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage view(ViewEventMessage msg);

    /**
     * 自定义菜单事件,扫码推事件的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage scanCodePush(ScanCodePushEventMessage msg);

    /**
     * 自定义菜单事件,扫码推事件的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage scanCodeWaitMsg(ScanCodeWaitMsgEventMessage msg);

    /**
     * 自定义菜单事件,弹出系统拍照发图的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage picSysPhoto(PicSysPhotoEventMessage msg);

    /**
     * 自定义菜单事件,弹出拍照或者相册发图的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage picPhotoOrAlbum(PicPhotoOrAlbumEventMessage msg);

    /**
     * 自定义菜单事件,弹出微信相册发图器的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage picWeixin(PicWeixinEventMessage msg);

    /**
     * 自定义菜单事件,弹出地理位置选择器的事件推送
     *
     * @param msg 接受消息对象
     * @return 输出消息对象
     */
    public OutputMessage locationSelect(LocationSelectEventMessage msg);

    void bindBloodSugarShareHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg);

    void productQrCodeHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg);

    void sensorQrCodeHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg);
}
