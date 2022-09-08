package com.comvee.cdms.wechat.message.event;


import com.comvee.cdms.wechat.message.EventType;
import com.comvee.cdms.wechat.message.SendPicsInfo;
/**
 * 自定义菜单事件
 *
 * 弹出拍照或者相册发图的事件推送
 */
public class PicPhotoOrAlbumEventMessage extends EventMessage {
	
    //事件KEY值，与自定义菜单接口中KEY值对应
    private String eventKey;
    //发送的图片信息
    private SendPicsInfo SendPicsInfo;

    @Override
    public String getEvent() {
        return EventType.Pic_Sysphoto.toString();
    }

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public SendPicsInfo getSendPicsInfo() {
		return SendPicsInfo;
	}

	public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
		SendPicsInfo = sendPicsInfo;
	}
}
