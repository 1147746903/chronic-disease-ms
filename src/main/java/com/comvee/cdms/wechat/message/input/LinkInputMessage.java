package com.comvee.cdms.wechat.message.input;


import com.comvee.cdms.wechat.message.MsgType;

public class LinkInputMessage extends InputMessage {

    //消息标题
    private String title;
    //消息描述
    private String description;
    //消息链接
    private String url;
    
    @Override
    public String getMsgType() {
        return MsgType.Link.toString();
    }
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
