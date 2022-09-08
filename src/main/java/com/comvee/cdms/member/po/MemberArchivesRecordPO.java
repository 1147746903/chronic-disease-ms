package com.comvee.cdms.member.po;

import java.io.Serializable;

/**
 * @author: wangxy
 * @date: 2018/10/8
 */
public class MemberArchivesRecordPO implements Serializable{

    private String sid;
    /**
     * 患者ID
     */
    private String memberId;

    /**
     * 添加时间
     */
    private String insertDt;

    /**
     * 修改时间
     */
    private String modifyDt;

    /**
     * 修改档案信息之前
     * archivesJsonBefore
     */
    private String archivesJsonBefore;

    /**
     * 修改档案信息之后
     * archivesJsonBefore
     */
    private String archivesJsonAfter;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 操作者id
     */
    private String operationId;

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

    public String getArchivesJsonBefore() {
        return archivesJsonBefore;
    }

    public void setArchivesJsonBefore(String archivesJsonBefore) {
        this.archivesJsonBefore = archivesJsonBefore;
    }

    public String getArchivesJsonAfter() {
        return archivesJsonAfter;
    }

    public void setArchivesJsonAfter(String archivesJsonAfter) {
        this.archivesJsonAfter = archivesJsonAfter;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

}
