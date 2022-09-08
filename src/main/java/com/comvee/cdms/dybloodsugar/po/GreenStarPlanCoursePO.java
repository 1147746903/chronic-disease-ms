package com.comvee.cdms.dybloodsugar.po;

public class GreenStarPlanCoursePO {
    private String sid;
    private String title;
    private Integer courseType;//课程类型 1:长图 2：图文 3：视频 4：图文/视频 5：其他'
    private Integer courseIndex;
    private Integer dateIndex;
    private String jumpUrl;
    private String insertDt;
    private String modifyDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public Integer getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(Integer courseIndex) {
        this.courseIndex = courseIndex;
    }

    public Integer getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(Integer dateIndex) {
        this.dateIndex = dateIndex;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
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
