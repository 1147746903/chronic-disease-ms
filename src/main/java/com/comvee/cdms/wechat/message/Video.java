package com.comvee.cdms.wechat.message;

/**
 * 回复视频消息中的视频对象
 *
 * <p>提供了获取视频Id<code>getMediaId()</code>等主要方法.</p>
 *
 */
public class Video implements java.io.Serializable {
	
    private String mediaId;     //通过上传多媒体文件，得到的id
    private String title;       //视频消息的标题
    private String description; //视频消息的描述
    
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
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
}
