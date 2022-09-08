package com.comvee.cdms.wechat.message.event;


import com.comvee.cdms.wechat.message.EventType;
import com.comvee.cdms.wechat.message.SendLocationInfo;

/**
 * 自定义菜单事件
 *
 * 弹出地理位置选择器的事件推送
 */
public class LocationSelectEventMessage extends EventMessage {
    //事件KEY值，与自定义菜单接口中KEY值对应
    private String eventKey;
    //发送的位置信息
    private SendLocationInfo sendLocationInfo;
    
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

	public SendLocationInfo getSendLocationInfo() {
		return sendLocationInfo;
	}

	public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
		this.sendLocationInfo = sendLocationInfo;
	}
}
