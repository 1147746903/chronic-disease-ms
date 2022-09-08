package com.comvee.cdms.wechat.message.event;


import com.comvee.cdms.wechat.message.EventType;

/**
 * 扫描带参数二维码事件
 *
 * 用户未关注

 */
public class QrsceneSubscribeEventMessage extends EventMessage {
    //事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String eventKey;
    //二维码的ticket，可用来换取二维码图片
    private String ticket;

    @Override
    public String getEvent() {
        return EventType.Subscribe.toString();
    }
    
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
