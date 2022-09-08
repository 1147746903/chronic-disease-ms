


/**
 * @File name:  FeatureModel.java  FeatureModel  Model信息
 * @Create on:  2016-12-28 16:03:24
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/

package com.comvee.cdms.knowledge.model;
import java.io.Serializable;


public class FeatureModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 画像特征id,(字段类型:bigint) **/
	private Long id; 
	/** 标题,(字段类型:varchar) **/
	private String title; 
	/** 题目编码,(字段类型:varchar) **/
	private String code; 
	/** 题目编号,(字段类型:varchar) **/
	private String num; 
	/** 题目的类型1、日期填空，2单选题，3多选题，4数值填空题，5文本填空，6阅读型,(字段类型:tinyint) **/
	private Integer type; 
	/** 单位,(字段类型:varchar) **/
	private String unit; 
	/** 最大值,(字段类型:varchar) **/
	private String maxv; 
	/** 最小值,(字段类型:varchar) **/
	private String minv; 
	/** 默认值,(字段类型:varchar) **/
	private String defv; 
	/** 题目排序,(字段类型:int) **/
	private Integer seq; 
	/** 题目子类型,(字段类型:tinyint) **/
	private Integer sonType; 
	/** 帮助文案,(字段类型:varchar) **/
	private String help; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	
	/** 是否排序因素,(字段类型:tinyint) **/
	private String sortElement;
    
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public String getTitle(){
		 return this.title;
	}
	public void setTitle(String title){
		 this.title=title;
	}
	public String getCode(){
		 return this.code;
	}
	public void setCode(String code){
		 this.code=code;
	}
	public String getNum(){
		 return this.num;
	}
	public void setNum(String num){
		 this.num=num;
	}
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
	}
	public String getUnit(){
		 return this.unit;
	}
	public void setUnit(String unit){
		 this.unit=unit;
	}
	public String getMaxv(){
		 return this.maxv;
	}
	public void setMaxv(String maxv){
		 this.maxv=maxv;
	}
	public String getMinv(){
		 return this.minv;
	}
	public void setMinv(String minv){
		 this.minv=minv;
	}
	public String getDefv(){
		 return this.defv;
	}
	public void setDefv(String defv){
		 this.defv=defv;
	}
	public Integer getSeq(){
		 return this.seq;
	}
	public void setSeq(Integer seq){
		 this.seq=seq;
	}
	public Integer getSonType(){
		 return this.sonType;
	}
	public void setSonType(Integer sonType){
		 this.sonType=sonType;
	}
	public String getHelp(){
		 return this.help;
	}
	public void setHelp(String help){
		 this.help=help;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
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
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
    public String getSortElement() {
        return sortElement;
    }
    public void setSortElement(String sortElement) {
        this.sortElement = sortElement;
    }
	
	
}