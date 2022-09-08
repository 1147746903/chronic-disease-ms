


/**
 * @File name:  CategoryRuleModel.java  CategoryRuleModel  Model信息
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


public class CategoryRuleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private Long id; 
	/** 规则内容,(字段类型:text) **/
	private String rule; 
	/** 规则总类版本id,(字段类型:bigint) **/
	private Long categoryId; 
	/** 规则json,(字段类型:text) **/
	private String ruleJson; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 推荐的知识点,(字段类型:text) **/
	private String outId; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	/** 突出权重,(字段类型:int)  **/
	private Integer salience;
	
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public String getRule(){
		 return this.rule;
	}
	public void setRule(String rule){
		 this.rule=rule;
	}
	public Long getCategoryId(){
		 return this.categoryId;
	}
	public void setCategoryId(Long categoryId){
		 this.categoryId=categoryId;
	}
	public String getRuleJson(){
		 return this.ruleJson;
	}
	public void setRuleJson(String ruleJson){
		 this.ruleJson=ruleJson;
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
	public String getOutId(){
		 return this.outId;
	}
	public void setOutId(String outId){
		 this.outId=outId;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
    public Integer getSalience() {
        return salience;
    }
    public void setSalience(Integer salience) {
        this.salience = salience;
    }
	
}