package com.comvee.cdms.wechat.message;

public class Miniprogram {

	
//	miniprogrampage.put("title", title);
//	miniprogrampage.put("appid", WechatConfig.getValueByKey("wechat.oauth.appid"));
//	miniprogrampage.put("pagepath", pagepath);
//	miniprogrampage.put("thumb_media_id", "mdiXWiA61sjN4RW6exF4ndmWEoQjNJztcLyQbwX9GUc");
	
	private String title;
	private String appid;
	private String pagepath;
	private String thumb_media_id;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPagepath() {
		return pagepath;
	}
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	
	
	
}
