package com.comvee.cdms.wechat.message.event;


import com.comvee.cdms.wechat.message.EventType;

/**
 * 自定义菜单事件
 * 
 * 点击菜单拉取消息时的事件推送
 *
 */

public class ClickEventMessage extends EventMessage {
	
    private String eventKey;
    
    @Override
    public String getEvent() {
        return EventType.CLICK.toString();
    }
    
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
