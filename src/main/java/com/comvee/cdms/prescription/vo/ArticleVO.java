package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

/**
 * @File name:   ArticlePo.java   TODO文章详情
 * @Create on:   2018年2月27日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public class ArticleVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;//文章内容
	
	private String title;  //文章标题
	
	private String pushDt; //推送时间

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushDt() {
		return pushDt;
	}

	public void setPushDt(String pushDt) {
		this.pushDt = pushDt;
	}

}
