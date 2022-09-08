/**
 * @File name:  ClientBehaviorModel.java  ClientBehaviorModel  Model信息
 * @Create on:  2018-9-18 14:33:51
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/

package com.comvee.cdms.defender.wechat.model;
import java.io.Serializable;

public class ClientBehaviorModel implements Serializable {
    private static final long serialVersionUID = 1L;
	/** 主键id,(字段类型:bigint) **/
	private Long sid; 
	/** 浏览器类型,(字段类型:varchar) **/
	private String agent; 
	/** cookie_id,(字段类型:varchar) **/
	private String device; 
	/** 访问的页面url(不带参数),(字段类型:varchar) **/
	private String url; 
	/** 页面参数,(字段类型:varchar) **/
	private String arg; 
	/** 开始访问时间,(字段类型:datetime) **/
	private Long visitTm; 
	/** 页面停驻时间（单位秒）,(字段类型:int) **/
	private Integer time; 
	/** 页面编号,(字段类型:varchar) **/
	private String pageId; 
	/** 患者微信simuid,(字段类型:varchar) **/
	private String simuid; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 当前流量的来源参考页,(字段类型:varchar) **/
	private String referer; 
	
	/** cookieId**/
	private String cookieId;
    
	public String getAgent(){
		 return this.agent;
	}
	public void setAgent(String agent){
		 this.agent=agent;
	}
	public String getDevice(){
		 return this.device;
	}
	public void setDevice(String device){
		 this.device=device;
	}
	public String getUrl(){
		 return this.url;
	}
	public void setUrl(String url){
		 this.url=url;
	}
	public String getArg(){
		 return this.arg;
	}
	public void setArg(String arg){
		 this.arg=arg;
	}
	public Long getVisitTm(){
		 return this.visitTm;
	}
	public void setVisitTm(Long visitTm){
		 this.visitTm=visitTm;
	}
	public Integer getTime(){
		 return this.time;
	}
	public void setTime(Integer time){
		 this.time=time;
	}
	public String getPageId(){
		 return this.pageId;
	}
	public void setPageId(String pageId){
		 this.pageId=pageId;
	}
	public String getSimuid(){
		 return this.simuid;
	}
	public void setSimuid(String simuid){
		 this.simuid=simuid;
	}
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public String getModifyDt(){
		 return this.modifyDt;
	}
	public void setModifyDt(String modifyDt){
		 this.modifyDt=modifyDt;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
	}
	public String getReferer(){
		 return this.referer;
	}
	public void setReferer(String referer){
		 this.referer=referer;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getCookieId() {
		return cookieId;
	}
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}
}