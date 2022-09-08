package com.comvee.cdms.education.model.po;

import java.io.Serializable;

/**
 * 远程教育课程分类(EduCourseClassPO)实体类
 *
 * @author makejava
 * @since 2022-01-27 11:49:43
 */
public class EduCourseClassPO implements Serializable {
    private static final long serialVersionUID = -25504484016710907L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 分类名
     */
    private String className;
    /**
     * 操作者id
     */
    private String operatorId;
    /**
     * 是否有效
     */
    private Integer isValid;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 添加时间
     */
    private String insertDt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

}
