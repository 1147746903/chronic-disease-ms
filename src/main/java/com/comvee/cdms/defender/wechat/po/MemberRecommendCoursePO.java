package com.comvee.cdms.defender.wechat.po;

/**
 * @Author linr
 * @Date 2021/11/26
 */
public class MemberRecommendCoursePO {
    private String sid;
    private String memberId;
    private String courseId;
    private Integer sort;
    private Integer isValid;
    private String insertDt;
    private String modifyDt;
    private String resetDt;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getResetDt() {
        return resetDt;
    }

    public void setResetDt(String resetDt) {
        this.resetDt = resetDt;
    }
}
