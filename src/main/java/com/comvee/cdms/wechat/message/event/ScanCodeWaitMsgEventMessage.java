package com.comvee.cdms.wechat.message.event;

import com.comvee.cdms.wechat.message.EventType;
import com.comvee.cdms.wechat.message.ScanCodeInfo;

/**
 * 自定义菜单事件
 *
 * 扫码推事件且弹出“消息接收中”提示框的事件推送
 *
 */
public class ScanCodeWaitMsgEventMessage extends EventMessage {

    //事件KEY值，与自定义菜单接口中KEY值对应
    private String eventKey;
    //扫描信息
    private ScanCodeInfo scanCodeInfo;

    @Override
    public String getEvent() {
        return EventType.Scancode_Waitmsg.toString();
    }

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public ScanCodeInfo getScanCodeInfo() {
		return scanCodeInfo;
	}

	public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
		this.scanCodeInfo = scanCodeInfo;
	}

}
