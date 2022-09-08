


/**
 * @File name:  CfgQuesModel.java  CfgQuesModel  Model信息
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
import java.util.List;
import java.util.Map;

public class CfgQuesModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** ,(字段类型:bigint) **/
	private String qid;
	/** 题目标题,(字段类型:varchar) **/
	private String title; 
	/** 题目编号,(字段类型:varchar) **/
	private String keyword; 
	/** 题目的类型1、日期填空，2单选题，3多选题，4数值填空题，5文本填空，6阅读型,(字段类型:tinyint) **/
	private Integer type; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 对应的题目编码,(字段类型:varchar) **/
	private String code; 
	/** 单位,(字段类型:varchar) **/
	private String unit; 
	/** 最大值,(字段类型:varchar) **/
	private String maxVal; 
	/** 最小值,(字段类型:varchar) **/
	private String minVal; 
	/** 默认值,(字段类型:varchar) **/
	private String defaultVal; 
	/** 是否支持小数(0表示整数，1表示一位小数2表示两位小数),(字段类型:tinyint) **/
	private Integer iffloat; 
	/** 排序,(字段类型:int) **/
	private Integer seq; 
	/** 子类类型括展(1数值型转日期),(字段类型:tinyint) **/
	private String sonType; 
	/** 是否医学库档案,(字段类型:tinyint) **/
	private Integer filesCode; 
	/** 帮助文案,(字段类型:varchar) **/
	private String help; 
	/** 题目分组标签,(字段类型:varchar) **/
	private String label; 
	
	private String answer;//答案
	
	private List<Map<String,Object>> items;//选项
	/** 题目分组标签,(字段类型:varchar) **/
	private String labelName;
    
	
	private String tid;
	
	
//	private List<CfgQuesItemsModel> it;


	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTitle(){
		 return this.title;
	}
	public void setTitle(String title){
		 this.title=title;
	}
	public String getKeyword(){
		 return this.keyword;
	}
	public void setKeyword(String keyword){
		 this.keyword=keyword;
	}
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
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
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public String getCode(){
		 return this.code;
	}
	public void setCode(String code){
		 this.code=code;
	}
	public String getUnit(){
		 return this.unit;
	}
	public void setUnit(String unit){
		 this.unit=unit;
	}
	public String getMaxVal(){
		 return this.maxVal;
	}
	public void setMaxVal(String maxVal){
		 this.maxVal=maxVal;
	}
	public String getMinVal(){
		 return this.minVal;
	}
	public void setMinVal(String minVal){
		 this.minVal=minVal;
	}
	public String getDefaultVal(){
		 return this.defaultVal;
	}
	public void setDefaultVal(String defaultVal){
		 this.defaultVal=defaultVal;
	}
	public Integer getIffloat(){
		 return this.iffloat;
	}
	public void setIffloat(Integer iffloat){
		 this.iffloat=iffloat;
	}
	public Integer getSeq(){
		 return this.seq;
	}
	public void setSeq(Integer seq){
		 this.seq=seq;
	}
	public String getSonType(){
		 return this.sonType;
	}
	public void setSonType(String sonType){
		 this.sonType=sonType;
	}
	public Integer getFilesCode(){
		 return this.filesCode;
	}
	public void setFilesCode(Integer filesCode){
		 this.filesCode=filesCode;
	}
	public String getHelp(){
		 return this.help;
	}
	public void setHelp(String help){
		 this.help=help;
	}
	public String getLabel(){
		 return this.label;
	}
	public void setLabel(String label){
		 this.label=label;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<Map<String, Object>> getItems() {
		return items;
	}
	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}
	
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

//	public List<CfgQuesItemsModel> getIt() {
//		return it;
//	}
//	public void setIt(List<CfgQuesItemsModel> it) {
//		this.it = it;
//	}
	
	
	
	
	
}