package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;

/**
 * 知识挑战-用户课程表(MemberCourseBarrierPO)实体类
 *
 * @author makejava
 * @since 2021-12-30 10:22:38
 */
public class MemberCourseBarrierPO implements Serializable {
    private static final long serialVersionUID = -86163370345444855L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 用户id
     */
    private String memberId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 是否有效(1有效0无效)
     */
    private Integer isValid;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
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
