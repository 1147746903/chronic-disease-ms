package com.comvee.cdms.education.model.po;

import java.io.Serializable;

/**
 * 远程教育课程观看历史(EduViewHistoryPO)实体类
 *
 * @author makejava
 * @since 2022-01-27 09:59:14
 */
public class EduViewHistoryPO implements Serializable {
    private static final long serialVersionUID = 727873094068035364L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 患者id
     */
    private String doctorId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 来源 1web2h5
     */
    private Integer origin;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
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
