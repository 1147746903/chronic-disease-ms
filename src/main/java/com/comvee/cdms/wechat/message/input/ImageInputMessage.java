package com.comvee.cdms.wechat.message.input;


import com.comvee.cdms.wechat.message.MsgType;

public class ImageInputMessage extends InputMessage{
    //图片链接
    private String picUrl;
    private String mediaId;
    
    @Override
    public String getMsgType() {
        return MsgType.Image.toString();
    }
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
