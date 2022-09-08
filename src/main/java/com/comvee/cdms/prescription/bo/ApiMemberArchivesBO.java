package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiMemberArchivesBO implements Serializable {
    /**
     * member_id
     */
    private String memberId;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * modify_dt
     */
    private String modifyDt;

    /**
     * 糖尿病现状
     * diabetes_status
     */
    private String diabetesStatus;

    /**
     * 日常习惯
     * daily_habits
     */
    private String dailyHabits;

    /**
     * 糖尿病并发症
     * diabetes_complication
     */
    private String diabetesComplication;

    /**
     * 低血糖
     * hypoglycemia
     */
    private String hypoglycemia;

    /**
     * 教育情况
     * education_situation
     */
    private String educationSituation;

    /** 基本信息 **/
    private String basic;
    /**
     * 档案信息
     * archivesJson
     */
    private String archivesJson;
    private String anamnesis;

    private String signjson;

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
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

    public String getDiabetesStatus() {
        return diabetesStatus;
    }

    public void setDiabetesStatus(String diabetesStatus) {
        this.diabetesStatus = diabetesStatus;
    }

    public String getDailyHabits() {
        return dailyHabits;
    }

    public void setDailyHabits(String dailyHabits) {
        this.dailyHabits = dailyHabits;
    }

    public String getDiabetesComplication() {
        return diabetesComplication;
    }

    public void setDiabetesComplication(String diabetesComplication) {
        this.diabetesComplication = diabetesComplication;
    }

    public String getHypoglycemia() {
        return hypoglycemia;
    }

    public void setHypoglycemia(String hypoglycemia) {
        this.hypoglycemia = hypoglycemia;
    }

    public String getEducationSituation() {
        return educationSituation;
    }

    public void setEducationSituation(String educationSituation) {
        this.educationSituation = educationSituation;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getSignjson() {
        return signjson;
    }

    public void setSignjson(String signjson) {
        this.signjson = signjson;
    }
}
