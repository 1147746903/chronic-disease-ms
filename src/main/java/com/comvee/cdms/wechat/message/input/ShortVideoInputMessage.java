package com.comvee.cdms.wechat.message.input;


import com.comvee.cdms.wechat.message.MsgType;

public class ShortVideoInputMessage extends InputMessage {

    //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;
    //视频消息 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
    private String thumbMediaId;
    
    @Override
    public String getMsgType() {
        return MsgType.ShortVideo.toString();
    }
	
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}
