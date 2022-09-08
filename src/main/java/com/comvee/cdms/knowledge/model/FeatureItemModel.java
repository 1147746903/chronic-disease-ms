


/**
 * @File name:  FeatureItemModel.java  FeatureItemModel  Model信息
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


public class FeatureItemModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 画像选项id,(字段类型:bigint) **/
	private Long id; 
	/** 画像特征id,(字段类型:bigint) **/
	private Long fid; 
	/** 选项序号,(字段类型:int) **/
	private Integer seq; 
	/** 选项值,(字段类型:varchar) **/
	private String vals; 
	/** 选项内容,(字段类型:varchar) **/
	private String lable; 
	/** 选项帮助说明,(字段类型:varchar) **/
	private String help; 
	/** 转化规则,(字段类型:tinyint) **/
	private Integer type; 
	/** 转化规则上限,(字段类型:varchar) **/
	private String maxRule; 
	/** 转化规则下限,(字段类型:varchar) **/
	private String minRule; 
	/** 附加事件,(字段类型:tinyint) **/
	private Integer motions; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	
	/** 排序因素(1有效0无效) ,(字段类型:tynyint) **/
	private Integer sortElement; 
	
	   /** 关键字id(多个关键字用逗号隔开) ,(字段类型:varchat) **/
    private String keyId; 
    
    /**档案选项组合关键字**/
    private String code;
	   
//	   alter table tpi_feature_item
//	   add sort_element char(10) comment '排序因素(1有效0无效)';
//
//	alter table tpi_feature_item
//	   add key_id char(10) comment '关键字id(多个关键字用逗号隔开)';

	
    
	public Integer getSortElement() {
        return sortElement;
    }
    public void setSortElement(Integer sortElement) {
        this.sortElement = sortElement;
    }
    public String getKeyId() {
        return keyId;
    }
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public Long getFid(){
		 return this.fid;
	}
	public void setFid(Long fid){
		 this.fid=fid;
	}
	public Integer getSeq(){
		 return this.seq;
	}
	public void setSeq(Integer seq){
		 this.seq=seq;
	}
	public String getVals(){
		 return this.vals;
	}
	public void setVals(String vals){
		 this.vals=vals;
	}
	public String getLable(){
		 return this.lable;
	}
	public void setLable(String lable){
		 this.lable=lable;
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
	public Integer getMotions(){
		 return this.motions;
	}
	public void setMotions(Integer motions){
		 this.motions=motions;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
	
	
	
}