package com.comvee.cdms.wechat.message.event;


import com.comvee.cdms.wechat.message.EventType;

/**
 * 自定义菜单事件
 *
 * 点击菜单跳转链接时的事件推送
 */
public class ViewEventMessage extends EventMessage {

    //事件KEY值，设置的跳转URL
    private String eventKey;

    @Override
    public String getEvent() {
        return EventType.View.toString();
    }

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
