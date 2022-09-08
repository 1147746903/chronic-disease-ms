
package com.comvee.cdms.wechat.message;

public class Image implements java.io.Serializable {

    private String mediaId; //通过上传多媒体文件，得到的id

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
