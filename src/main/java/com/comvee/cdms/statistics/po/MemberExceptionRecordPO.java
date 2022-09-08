package com.comvee.cdms.statistics.po;

import java.io.Serializable;

/**
 * 患者异常信息记录(MemberExceptionRecordPO)实体类
 *
 * @author makejava
 * @since 2022-02-18 13:58:53
 */
public class MemberExceptionRecordPO implements Serializable {
    private static final long serialVersionUID = -90422992621714466L;

    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 异常详细
     */
    //private String detail;
    private String dataJson;
    /**
     * 记录日期
     */
    private String recordDt;
    /**
     * 入库时间
     */
    private String insertDt;
    /**
     * 更新时间
     */
    private String modifyDt;
    /**
     * 是否有效 0:否 1:是
     */
    private Integer isValid;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
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

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
