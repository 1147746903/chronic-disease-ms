package com.comvee.cdms.member.po;

import java.io.Serializable;

/**
 * @author: wangxy
 * @date: 2018/10/8
 */
public class MemberArchivesPO implements Serializable{

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
     * 档案信息
     * archivesJson
     */
    private String archivesJson;

    /**
     * 医院id
     */
    private String hospitalId;

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

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
