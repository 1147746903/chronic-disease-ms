


/**
 * @File name:  CfgTagModel.java  CfgTagModel  Model信息
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


public class CfgTagModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 标签id,(字段类型:bigint) **/
	private Long id; 
	/** 标签内容,(字段类型:varchar) **/
	private String lable; 
	/** 标签编码,(字段类型:varchar) **/
	private String code; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String updateDt; 
	/** 是否有效(1用效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	/** 标题拼音,(字段类型:varchar) **/
    private String py;
    /** 分类数量(字段类型:int) **/
    private int num;
    /** 标题拼音,(字段类型:varchar) **/
    private String classify;
    /** 排序,(字段类型:varchar) **/
    private String orderBy;
    
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public String getLable(){
		 return this.lable;
	}
	public void setLable(String lable){
		 this.lable=lable;
	}
	public String getCode(){
		 return this.code;
	}
	public void setCode(String code){
		 this.code=code;
	}
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public String getUpdateDt(){
		 return this.updateDt;
	}
	public void setUpdateDt(String updateDt){
		 this.updateDt=updateDt;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
    public String getPy() {
        return py;
    }
    public void setPy(String py) {
        this.py = py;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}