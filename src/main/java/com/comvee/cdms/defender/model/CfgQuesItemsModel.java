


/**
 * @File name:  CfgQuesItemsModel.java  CfgQuesItemsModel  Model信息
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/

package com.comvee.cdms.defender.model;
import java.io.Serializable;

public class CfgQuesItemsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** ,(字段类型:bigint) **/
	private String sid;
	/** 题目id,(字段类型:bigint) **/
	private String qid;
	/** 选项顺序,(字段类型:int) **/
	private Integer seq; 
	/** 选项值,(字段类型:varchar) **/
	private String value; 
	/** 选项内容,(字段类型:varchar) **/
	private String con; 
	/** 帮助说明,(字段类型:varchar) **/
	private String help; 
	/** 转化规则(0选项,1数值,2时间－年3时间-月4时间-天5时间-小时,6时间-分钟),(字段类型:tinyint) **/
	private Integer type; 
	/** 计算规则上限值,(字段类型:varchar) **/
	private String maxRule; 
	/** 计算规则下限值,(字段类型:varchar) **/
	private String minRule; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 选项附加事件(0表示不起作用,1查看血糖2记录血糖,3设置控制目标),(字段类型:varchar) **/
	private String motions;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public Integer getSeq(){
		 return this.seq;
	}
	public void setSeq(Integer seq){
		 this.seq=seq;
	}
	public String getValue(){
		 return this.value;
	}
	public void setValue(String value){
		 this.value=value;
	}
	public String getCon(){
		 return this.con;
	}
	public void setCon(String con){
		 this.con=con;
	}
	public String getHelp(){
		 return this.help;
	}
	public void setHelp(String help){
		 this.help=help;
	}
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
	}
	public String getMaxRule(){
		 return this.maxRule;
	}
	public void setMaxRule(String maxRule){
		 this.maxRule=maxRule;
	}
	public String getMinRule(){
		 return this.minRule;
	}
	public void setMinRule(String minRule){
		 this.minRule=minRule;
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
	public String getMotions(){
		 return this.motions;
	}
	public void setMotions(String motions){
		 this.motions=motions;
	}
}