package com.comvee.cdms.wechat.message.input;


import com.comvee.cdms.wechat.message.MsgType;

public class TextInputMessage extends InputMessage{
	
	//文本消息内容
    private String content;

    @Override
    public String getMsgType() {
        return MsgType.Text.toString();
    }
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
