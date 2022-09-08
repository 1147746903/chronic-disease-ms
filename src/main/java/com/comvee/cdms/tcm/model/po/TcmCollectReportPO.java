package com.comvee.cdms.tcm.model.po;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.tcm.model.vo.TcmHealthElementVO;

public class TcmCollectReportPO {
    private String sid;
    private String taskId;
    private String figureDesc;
    private String comingDisease;
    private String healthStatus;
    private JSONArray healthList;
    private String healthJson;
    private String elementJson;
    private TcmHealthElementVO elementObj;
    private String constitutionDesc;
    private String diseaseRisk;
    private String featureDesc;
    private String birthFortuneJson;
    private JSONObject birthObj;
    private String curFortuneJson;
    private JSONObject currentObj;
    private String teaNurse;
    private String musicNurse;
    private String porridgeNurse;
    private String dietNurse;
    private String drinkNurse;
    private String sportNurse;
    private String finishDt;
    private Integer valid;
    private String insertDt;
    private String modifyDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFigureDesc() {
        return figureDesc;
    }

    public void setFigureDesc(String figureDesc) {
        this.figureDesc = figureDesc;
    }


    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthJson() {
        return healthJson;
    }

    public void setHealthJson(String healthJson) {
        this.healthJson = healthJson;
    }

    public String getElementJson() {
        return elementJson;
    }

    public void setElementJson(String elementJson) {
        this.elementJson = elementJson;
    }

    public String getConstitutionDesc() {
        return constitutionDesc;
    }

    public void setConstitutionDesc(String constitutionDesc) {
        this.constitutionDesc = constitutionDesc;
    }

    public String getDiseaseRisk() {
        return diseaseRisk;
    }

    public void setDiseaseRisk(String diseaseRisk) {
        this.diseaseRisk = diseaseRisk;
    }

    public String getBirthFortuneJson() {
        return birthFortuneJson;
    }

    public void setBirthFortuneJson(String birthFortuneJson) {
        this.birthFortuneJson = birthFortuneJson;
    }

    public String getCurFortuneJson() {
        return curFortuneJson;
    }

    public void setCurFortuneJson(String curFortuneJson) {
        this.curFortuneJson = curFortuneJson;
    }


    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
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


    public String getFeatureDesc() {
        return featureDesc;
    }

    public void setFeatureDesc(String featureDesc) {
        this.featureDesc = featureDesc;
    }

    public JSONObject getBirthObj() {
        return birthObj;
    }

    public void setBirthObj(JSONObject birthObj) {
        this.birthObj = birthObj;
    }

    public JSONObject getCurrentObj() {
        return currentObj;
    }

    public void setCurrentObj(JSONObject currentObj) {
        this.currentObj = currentObj;
    }

    public String getTeaNurse() {
        return teaNurse;
    }

    public void setTeaNurse(String teaNurse) {
        this.teaNurse = teaNurse;
    }

    public String getPorridgeNurse() {
        return porridgeNurse;
    }

    public void setPorridgeNurse(String porridgeNurse) {
        this.porridgeNurse = porridgeNurse;
    }

    public String getDietNurse() {
        return dietNurse;
    }

    public void setDietNurse(String dietNurse) {
        this.dietNurse = dietNurse;
    }

    public String getDrinkNurse() {
        return drinkNurse;
    }

    public void setDrinkNurse(String drinkNurse) {
        this.drinkNurse = drinkNurse;
    }

    public String getSportNurse() {
        return sportNurse;
    }

    public void setSportNurse(String sportNurse) {
        this.sportNurse = sportNurse;
    }

    public TcmHealthElementVO getElementObj() {
        return elementObj;
    }

    public void setElementObj(TcmHealthElementVO elementObj) {
        this.elementObj = elementObj;
    }

    public String getComingDisease() {
        return comingDisease;
    }

    public void setComingDisease(String comingDisease) {
        this.comingDisease = comingDisease;
    }


    public String getFinishDt() {
        return finishDt;
    }

    public void setFinishDt(String finishDt) {
        this.finishDt = finishDt;
    }


    public JSONArray getHealthList() {
        return healthList;
    }

    public void setHealthList(JSONArray healthList) {
        this.healthList = healthList;
    }

    public String getMusicNurse() {
        return musicNurse;
    }

    public void setMusicNurse(String musicNurse) {
        this.musicNurse = musicNurse;
    }


}
