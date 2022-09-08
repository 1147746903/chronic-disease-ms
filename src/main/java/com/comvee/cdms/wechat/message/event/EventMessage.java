package com.comvee.cdms.wechat.message.event;

/**
 * 事件消息
 *
 * @author qsyang
 * @version 1.0
 */
public abstract class EventMessage {

    //开发者微信号
    private String toUserName;
    
    //发送方帐号（一个OpenID）
    private String fromUserName;
    
    //消息创建时间 （整型）
    private Long createTime;
    
    //消息类型，event
    private String msgType = "event";
    
    private String ip ;

    /**
     * 获取 事件类型
     *
     * @return 事件类型
     */
    
    public abstract String getEvent();

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}    
	
}
