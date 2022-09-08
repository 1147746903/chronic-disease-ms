package com.comvee.cdms.defender.wechat.model;

import java.io.Serializable;

/**
 * @File name:   ImmediatelyTemplateModel.java   TODO
 * @Create on:   2018年8月31日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public class ImmediatelyTemplateModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sid;//主键id
	private String type;//模板消息类型
	private String json;//,
	private String returnCode;//,
	private String resultMsg;//处理结果
	private String tempId;//模板id
	private String pid;//患者id
	private String simuid;//微信id
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSimuid() {
		return simuid;
	}
	public void setSimuid(String simuid) {
		this.simuid = simuid;
	}
}
