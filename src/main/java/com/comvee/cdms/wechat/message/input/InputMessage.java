package com.comvee.cdms.wechat.message.input;

public abstract class InputMessage
{
	//开发者微信号
	private String toUserName;
	
	//发送方帐号（一个OpenID）
    private String fromUserName;
    
    //消息创建时间 （整型）
    private Long createTime;
	
    //消息id，64位整型
    private Long msgId;

    public abstract String getMsgType();
    
    
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

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
}
