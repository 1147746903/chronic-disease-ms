package com.comvee.cdms.level.bo;

import com.comvee.cdms.level.vo.DiabetesLevelAssessDataBloodSugarItemVO;

import java.util.List;

public class DiabetesLevelDataBO {

    private Integer lowBloodSugarTimes;
    private List<DiabetesLevelAssessDataBloodSugarItemVO> bloodSugarList;
    private String gfrCheckDt;
    private Double gfr;
    private Integer gfrStages;
    private String acrCheckDt;
    private Double acr;
    private Integer acrStages;
    private String hba1cCheckDt;
    private Double hba1c;

    private String diabeticNephropathyCheckDt;
    /**
     * 是否确诊糖尿病肾病
     */
    private String diabeticNephropathy;
    /**
     * 糖尿病肾病类型
     */
    private String diabeticNephropathyType;
    private String diabeticNephropathyTypeOther;

    private String diabeticFootCheckDt;
    /**、
     * 是否确诊糖尿病足
     */
    private String diabeticFoot;
    /**
     * 糖尿病足症状
     */
    private String diabeticFootSymptom;
    private String diabeticFootSymptomOther;

    private String eyesCheckDt;
    /**
     * 左眼病变情况
     */
    private Integer leftEyePathology;
    /**
     * 右眼病变情况
     */
    private Integer rightEyePathology;

    private String abiCheckDt;
    private Double leftAbi;
    private Double rightAbi;
    private Integer abiLevel;

    private String vptCheckDt;
    private Double leftVpt;
    private Double rightVpt;

    public String getGfrCheckDt() {
        return gfrCheckDt;
    }

    public void setGfrCheckDt(String gfrCheckDt) {
        this.gfrCheckDt = gfrCheckDt;
    }

    public String getAcrCheckDt() {
        return acrCheckDt;
    }

    public void setAcrCheckDt(String acrCheckDt) {
        this.acrCheckDt = acrCheckDt;
    }

    public Integer getLowBloodSugarTimes() {
        return lowBloodSugarTimes;
    }

    public void setLowBloodSugarTimes(Integer lowBloodSugarTimes) {
        this.lowBloodSugarTimes = lowBloodSugarTimes;
    }

    public List<DiabetesLevelAssessDataBloodSugarItemVO> getBloodSugarList() {
        return bloodSugarList;
    }

    public void setBloodSugarList(List<DiabetesLevelAssessDataBloodSugarItemVO> bloodSugarList) {
        this.bloodSugarList = bloodSugarList;
    }

    public Double getGfr() {
        return gfr;
    }

    public void setGfr(Double gfr) {
        this.gfr = gfr;
    }

    public Integer getGfrStages() {
        return gfrStages;
    }

    public void setGfrStages(Integer gfrStages) {
        this.gfrStages = gfrStages;
    }

    public Double getAcr() {
        return acr;
    }

    public void setAcr(Double acr) {
        this.acr = acr;
    }

    public Integer getAcrStages() {
        return acrStages;
    }

    public void setAcrStages(Integer acrStages) {
        this.acrStages = acrStages;
    }

    public String getHba1cCheckDt() {
        return hba1cCheckDt;
    }

    public void setHba1cCheckDt(String hba1cCheckDt) {
        this.hba1cCheckDt = hba1cCheckDt;
    }

    public Double getHba1c() {
        return hba1c;
    }

    public void setHba1c(Double hba1c) {
        this.hba1c = hba1c;
    }

    public String getDiabeticNephropathyCheckDt() {
        return diabeticNephropathyCheckDt;
    }

    public void setDiabeticNephropathyCheckDt(String diabeticNephropathyCheckDt) {
        this.diabeticNephropathyCheckDt = diabeticNephropathyCheckDt;
    }

    public String getDiabeticNephropathy() {
        return diabeticNephropathy;
    }

    public void setDiabeticNephropathy(String diabeticNephropathy) {
        this.diabeticNephropathy = diabeticNephropathy;
    }

    public String getDiabeticNephropathyType() {
        return diabeticNephropathyType;
    }

    public void setDiabeticNephropathyType(String diabeticNephropathyType) {
        this.diabeticNephropathyType = diabeticNephropathyType;
    }

    public String getDiabeticNephropathyTypeOther() {
        return diabeticNephropathyTypeOther;
    }

    public void setDiabeticNephropathyTypeOther(String diabeticNephropathyTypeOther) {
        this.diabeticNephropathyTypeOther = diabeticNephropathyTypeOther;
    }

    public String getDiabeticFootCheckDt() {
        return diabeticFootCheckDt;
    }

    public void setDiabeticFootCheckDt(String diabeticFootCheckDt) {
        this.diabeticFootCheckDt = diabeticFootCheckDt;
    }

    public String getDiabeticFoot() {
        return diabeticFoot;
    }

    public void setDiabeticFoot(String diabeticFoot) {
        this.diabeticFoot = diabeticFoot;
    }

    public String getDiabeticFootSymptom() {
        return diabeticFootSymptom;
    }

    public void setDiabeticFootSymptom(String diabeticFootSymptom) {
        this.diabeticFootSymptom = diabeticFootSymptom;
    }

    public String getDiabeticFootSymptomOther() {
        return diabeticFootSymptomOther;
    }

    public void setDiabeticFootSymptomOther(String diabeticFootSymptomOther) {
        this.diabeticFootSymptomOther = diabeticFootSymptomOther;
    }

    public String getEyesCheckDt() {
        return eyesCheckDt;
    }

    public void setEyesCheckDt(String eyesCheckDt) {
        this.eyesCheckDt = eyesCheckDt;
    }

    public Integer getLeftEyePathology() {
        return leftEyePathology;
    }

    public void setLeftEyePathology(Integer leftEyePathology) {
        this.leftEyePathology = leftEyePathology;
    }

    public Integer getRightEyePathology() {
        return rightEyePathology;
    }

    public void setRightEyePathology(Integer rightEyePathology) {
        this.rightEyePathology = rightEyePathology;
    }

    public String getAbiCheckDt() {
        return abiCheckDt;
    }

    public void setAbiCheckDt(String abiCheckDt) {
        this.abiCheckDt = abiCheckDt;
    }

    public Double getLeftAbi() {
        return leftAbi;
    }

    public void setLeftAbi(Double leftAbi) {
        this.leftAbi = leftAbi;
    }

    public Double getRightAbi() {
        return rightAbi;
    }

    public void setRightAbi(Double rightAbi) {
        this.rightAbi = rightAbi;
    }

    public Integer getAbiLevel() {
        return abiLevel;
    }

    public void setAbiLevel(Integer abiLevel) {
        this.abiLevel = abiLevel;
    }

    public String getVptCheckDt() {
        return vptCheckDt;
    }

    public void setVptCheckDt(String vptCheckDt) {
        this.vptCheckDt = vptCheckDt;
    }

    public Double getLeftVpt() {
        return leftVpt;
    }

    public void setLeftVpt(Double leftVpt) {
        this.leftVpt = leftVpt;
    }

    public Double getRightVpt() {
        return rightVpt;
    }

    public void setRightVpt(Double rightVpt) {
        this.rightVpt = rightVpt;
    }
}
