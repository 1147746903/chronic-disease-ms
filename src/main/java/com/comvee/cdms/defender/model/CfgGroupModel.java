


/**
 * @File name:  CfgGroupModel.java  CfgGroupModel  Model信息
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

public class CfgGroupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 题目分组列表id,(字段类型:bigint) **/
	private Long sid;
	/** 分组标题说明,(字段类型:varchar) **/
	private String groupName; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 分组类型(1: 极低血糖 2: 单次低血糖 3: 连续低血糖 4: 单次偏低血糖 
	 * 5: 连续偏低血糖 6: 高危血糖干预 7: 连续高血糖 8: 单次高血糖 9: 未监测干预
	 *  10: 复诊干预 11: 用药依从性干预),
	 * (字段类型:varchar) **/
	private String type;

	public Long getSid(){
		 return this.sid;
	}
	public void setSid(Long sid){
		 this.sid=sid;
	}
	public String getGroupName(){
		 return this.groupName;
	}
	public void setGroupName(String groupName){
		 this.groupName=groupName;
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
	public String getType(){
		 return this.type;
	}
	public void setType(String type){
		 this.type=type;
	}
}