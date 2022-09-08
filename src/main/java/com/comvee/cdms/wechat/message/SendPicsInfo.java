package com.comvee.cdms.wechat.message;

import java.util.List;

public class SendPicsInfo {
	
    //发送的图片数量
    private int count;
    //图片列表
    private List<PicList> picList;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<PicList> getPicList() {
		return picList;
	}
	public void setPicList(List<PicList> picList) {
		this.picList = picList;
	}
}
