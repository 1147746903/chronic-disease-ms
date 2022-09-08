package com.comvee.cdms.follow.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2019/5/14 9:12
 */
public class AddFollowCustomTemplateDTO {

    private String sid;


    @NotEmpty
    private String doctorId; //医生id

    private String creatorId; //创建人id
    @NotNull
    private Integer permission;  //权限 1:全院 2:个人
    @NotEmpty
    private String followName;  //随访名称

    private String hospitalId;  //医院id

    private String archivesJson;   //档案内容Json

    private String drugJson;  //用药情况Json

    private String footJson; //足有关Json

    private String  questionType; //问卷类型

    private String addJson;  //新增问题json

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getAddJson() {
        return addJson;
    }

    public void setAddJson(String addJson) {
        this.addJson = addJson;
    }

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


    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getDrugJson() {
        return drugJson;
    }

    public void setDrugJson(String drugJson) {
        this.drugJson = drugJson;
    }

    public String getFootJson() {
        return footJson;
    }

    public void setFootJson(String footJson) {
        this.footJson = footJson;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
