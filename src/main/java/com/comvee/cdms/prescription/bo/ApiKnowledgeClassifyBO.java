package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiKnowledgeClassifyBO implements Serializable {

    /** 知识点层级分类id,(字段类型:bigint) **/
    private Long id;
    /** 添加时间,(字段类型:datetime) **/
    private String insertDt;
    /** 修改时间,(字段类型:timestamp) **/
    private String modifyDt;
    /** 是否有效(1有效0无效),(字段类型:tinyint) **/
    private Integer isValid;
    /** 知识点层级分类标题,(字段类型:varchar) **/
    private String name;
    /** 知识点层级分类父级id,(字段类型:bigint) **/
    private Long pid;
    /** 知识点层级分类排序,(字段类型:int) **/
    private Integer sort;
    /** 层级,(字段类型:tinyint) **/
    private Integer zindex;
    /** 操作者id,(字段类型:varchar) **/
    private String operateId;

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
}
