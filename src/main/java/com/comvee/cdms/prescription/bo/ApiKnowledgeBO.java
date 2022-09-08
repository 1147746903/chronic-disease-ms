package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiKnowledgeBO implements Serializable {
    /** 知识点id,(字段类型:bigint) **/
    private Long id;
    /** 添加时间,(字段类型:datetime) **/
    private String insertDt;
    /** 修改时间,(字段类型:timestamp) **/
    private String modifyDt;
    /** 是否有效(1有效0无效),(字段类型:tinyint) **/
    private Integer isValid;
    /** 知识点标题,(字段类型:varchar) **/
    private String name;
    /** 知识点父级id,(字段类型:bigint) **/
    private Long pid;
    /** 是否有子级(1有子级0无子级),(字段类型:tinyint) **/
    private Integer hasChild;
    /** 知识点排序,(字段类型:int) **/
    private Integer sort;
    /** 层级,(字段类型:tinyint) **/
    private Integer zindex;
    /** 操作者id,(字段类型:varchar) **/
    private String operateId;
    /** 关键字，多个关键字逗号隔开,(字段类型:varchar) **/
    private String keyId;
    /** 关键字内容，多个关键字逗号隔开,(字段类型:varchar) **/
    private String keyName;
    /** 标题拼音,(字段类型:varchar) **/
    private String py;
    /**     排序字段              **/
    private String orderBy;
    /**     规则总类版本id              **/
    private String categoryId;
    /**
     * 课程分类
     */
    private Integer eohType;

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getZindex() {
        return zindex;
    }

    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Integer getHasChild() {
        return hasChild;
    }

    public void setHasChild(Integer hasChild) {
        this.hasChild = hasChild;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
