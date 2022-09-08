package com.comvee.cdms.education.model.po;

import java.io.Serializable;

/**
 * 远程教育患者课程收藏(EduDoctorCollectPO)实体类
 *
 * @author makejava
 * @since 2022-01-27 10:03:06
 */
public class EduDoctorCollectPO implements Serializable {
    private static final long serialVersionUID = 754550489064613117L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 医生id
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
