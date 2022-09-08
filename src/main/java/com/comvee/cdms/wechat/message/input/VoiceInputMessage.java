package com.comvee.cdms.wechat.message.input;


import com.comvee.cdms.wechat.message.MsgType;

public class VoiceInputMessage extends InputMessage {

    //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;
    //语音格式，如amr，speex等
    private String format;
    //语音识别结果，使用UTF8编码
    private String recognition;
    
    @Override
    public String getMsgType() {
        return MsgType.Voice.toString();
    }
    
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
