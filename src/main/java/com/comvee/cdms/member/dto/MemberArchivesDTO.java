package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: wangxy
 * @date: 2018/10/15
 */
public class MemberArchivesDTO {

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String archivesJson;

    private String drugListJson;
    private String doctorId;

    /**
     * 来源 1web 2his 3app
     */
    private String origin = "1";
    private boolean saveDrug = false;

    /**
     * 用药记录来源1 通用 2南京鼓楼
     */
    private Integer dType;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getDrugListJson() {
        return drugListJson;
    }

    public void setDrugListJson(String drugListJson) {
        this.drugListJson = drugListJson;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public boolean getSaveDrug() {
        return saveDrug;
    }

    public boolean isSaveDrug() {
        return saveDrug;
    }

    public void setSaveDrug(boolean saveDrug) {
        this.saveDrug = saveDrug;
    }

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }
}
