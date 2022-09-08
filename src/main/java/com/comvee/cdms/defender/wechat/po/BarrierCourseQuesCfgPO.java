package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;

/**
 * 知识挑战-题库配置表(BarrierCourseQuesCfgPO)实体类
 *
 * @author makejava
 * @since 2021-12-03 15:50:55
 */
public class BarrierCourseQuesCfgPO implements Serializable {
    private static final long serialVersionUID = -26749916022738453L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 题目id
     */
    private String quesId;
    /**
     * 是否有效(1有效0无效)
     */
    private Object isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public Object getIsValid() {
        return isValid;
    }

    public void setIsValid(Object isValid) {
        this.isValid = isValid;
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

}
