package com.comvee.cdms.wechat.message;

/**
 * 回复语音消息中的语音对象
 *
 * <p>提供了获取语音Id<code>getMediaId()</code>等主要方法.</p>
 */

public class Voice implements java.io.Serializable {
	
    private String mediaId;     //通过上传多媒体文件，得到的id

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}