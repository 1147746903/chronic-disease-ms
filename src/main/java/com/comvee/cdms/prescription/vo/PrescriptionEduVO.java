package com.comvee.cdms.prescription.vo;

import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeWeek;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/8/3 10:32.
 */
public class PrescriptionEduVO {
    @Override
    public String toString() {
        return "PrescriptionEduVO{}";
    }

    /**
     * 患病日期
     */
    private String diabetesDate;
    /**
     * 监测血糖频率
     */
    private String bloodFrequency;
    /**
     * 糖化血红蛋白
     * 当前值不使用，使用memberInfoJson
     */
    private String glycosylatedVal;
    /**
     * 本月内低血糖
     */
    private String lowBloodMonth;
    /**
     * 运动频率
     */
    private String sportFrequency;
    /**
     * 目前教育情况
     */
    private String knowKnowledge;
    /**
     * 并发症情况
     */
    private PrescriptionComplicationVO prescriptionComplication;
    /**
     * 知识标签
     */
    private List<String> knowledgeTagList;
    /**
     * 知识
     */
    List<PrescriptionKnowledgePO> knowledgeList;

    /**
     * 知识周
     */
    List<KnowledgeWeek> knowledgeWeekList;

    /**
     * 腰围
     */
    private String waistline;
    /**
     * 腰臀比
     */
    private String whr;
    /**
     * 是否具有运动习惯
     */
    private String bsSport;
    /**
     * 是否有抽烟
     */
    private String smoke;
    /**
     * 是否有喝酒
     */
    private String drink;
    /**
     * 用药
     */
    private String drugType;

    /**
     * 监测血糖频率其他
     */
    private String mujcxtpl;

    /**
     * 用药来源 1 通用 2 南京
     */
    private String drugOrgin;

    public String getDrugOrgin() {
        return drugOrgin;
    }

    public void setDrugOrgin(String drugOrgin) {
        this.drugOrgin = drugOrgin;
    }

    public String getMujcxtpl() {
        return mujcxtpl;
    }

    public void setMujcxtpl(String mujcxtpl) {
        this.mujcxtpl = mujcxtpl;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

    public String getBloodFrequency() {
        return bloodFrequency;
    }

    public void setBloodFrequency(String bloodFrequency) {
        this.bloodFrequency = bloodFrequency;
    }

    public String getGlycosylatedVal() {
        return glycosylatedVal;
    }

    public void setGlycosylatedVal(String glycosylatedVal) {
        this.glycosylatedVal = glycosylatedVal;
    }

    public String getLowBloodMonth() {
        return lowBloodMonth;
    }

    public void setLowBloodMonth(String lowBloodMonth) {
        this.lowBloodMonth = lowBloodMonth;
    }

    public String getSportFrequency() {
        return sportFrequency;
    }

    public void setSportFrequency(String sportFrequency) {
        this.sportFrequency = sportFrequency;
    }

    public String getKnowKnowledge() {
        return knowKnowledge;
    }

    public void setKnowKnowledge(String knowKnowledge) {
        this.knowKnowledge = knowKnowledge;
    }

    public PrescriptionComplicationVO getPrescriptionComplication() {
        return prescriptionComplication;
    }

    public void setPrescriptionComplication(PrescriptionComplicationVO prescriptionComplication) {
        this.prescriptionComplication = prescriptionComplication;
    }

    public List<String> getKnowledgeTagList() {
        return knowledgeTagList;
    }

    public void setKnowledgeTagList(List<String> knowledgeTagList) {
        this.knowledgeTagList = knowledgeTagList;
    }

    public List<PrescriptionKnowledgePO> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<PrescriptionKnowledgePO> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public List<KnowledgeWeek> getKnowledgeWeekList() {
        return knowledgeWeekList;
    }

    public void setKnowledgeWeekList(List<KnowledgeWeek> knowledgeWeekList) {
        this.knowledgeWeekList = knowledgeWeekList;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getWhr() {
        return whr;
    }

    public void setWhr(String whr) {
        this.whr = whr;
    }

    public String getBsSport() {
        return bsSport;
    }

    public void setBsSport(String bsSport) {
        this.bsSport = bsSport;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }
}
