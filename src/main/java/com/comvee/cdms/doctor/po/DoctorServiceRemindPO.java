package com.comvee.cdms.doctor.po;

/**
 * @author wyc
 * @date 2019/12/20 13:29
 */
public class DoctorServiceRemindPO {

    /**
     * 主键id
     */
    private String sid;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 提示信息
     */
    private String remindInfo;

    /**
     * 开启状态   0 关闭 1 开启
     */
    private Integer openStatus;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String updateDt;

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

    public String getRemindInfo() {
        return remindInfo;
    }

    public void setRemindInfo(String remindInfo) {
        this.remindInfo = remindInfo;
    }

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }
}
