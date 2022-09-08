package com.comvee.cdms.defender.wechat.vo;

import java.util.Objects;

/**
 * @Author linr
 * @Date 2021/11/25
 */
public class ListCourseVO {
    private String sid;
    private String img;
    private String surfaceImg;
    private String thumbnail;
    private String name;
    private String chapter;
    private String chapterMemo;
    private String memo;
    private Integer isLearn;
    private String patientCourseId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSurfaceImg() {
        return surfaceImg;
    }

    public void setSurfaceImg(String surfaceImg) {
        this.surfaceImg = surfaceImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getChapterMemo() {
        return chapterMemo;
    }

    public void setChapterMemo(String chapterMemo) {
        this.chapterMemo = chapterMemo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getIsLearn() {
        return isLearn;
    }

    public void setIsLearn(Integer isLearn) {
        this.isLearn = isLearn;
    }

    public String getPatientCourseId() {
        return patientCourseId;
    }

    public void setPatientCourseId(String patientCourseId) {
        this.patientCourseId = patientCourseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListCourseVO that = (ListCourseVO) o;
        return Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid);
    }
}
