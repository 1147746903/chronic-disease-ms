


/**
 * @File name:  ClientEventModel.java  ClientEventModel  Model信息
 * @Create on:  2018-9-18 14:20:31
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

public class ClientEventModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private Long sid; 
	/** 所在的页面url(不带参),(字段类型:varchar) **/
	private String url; 
	/** 事件触发时间,(字段类型:datetime) **/
	private String tm; 
	/** 事件名字编码,(字段类型:varchar) **/
	private String name; 
	/** 患者微信simuid,(字段类型:varchar) **/
	private String simuid; 
	/** 事件处理过程所附带的参数,(字段类型:varchar) **/
	private String param; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
    
	public Long getSid(){
		 return this.sid;
	}
	public void setSid(Long sid){
		 this.sid=sid;
	}
	public String getUrl(){
		 return this.url;
	}
	public void setUrl(String url){
		 this.url=url;
	}
	public String getTm(){
		 return this.tm;
	}
	public void setTm(String tm){
		 this.tm=tm;
	}
	public String getName(){
		 return this.name;
	}
	public void setName(String name){
		 this.name=name;
	}
	public String getSimuid(){
		 return this.simuid;
	}
	public void setSimuid(String simuid){
		 this.simuid=simuid;
	}
	public String getParam(){
		 return this.param;
	}
	public void setParam(String param){
		 this.param=param;
	}
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
	}
	public String getModifyDt(){
		 return this.modifyDt;
	}
	public void setModifyDt(String modifyDt){
		 this.modifyDt=modifyDt;
	}
}