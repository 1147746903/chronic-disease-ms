package com.comvee.cdms.records.model.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DietRecordVO {

    private String memberId;
    private Integer timeType;
    private String recordDt;
    private String recordTime;
    private Double totalHeal;
    private String insertDt;
    private String modifyDt;
    private List<JSONObject> foodList = new ArrayList<>();

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Double getTotalHeal() {
        return totalHeal;
    }

    public void setTotalHeal(Double totalHeal) {
        this.totalHeal = totalHeal;
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


    public List<JSONObject> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<JSONObject> foodList) {
        this.foodList = foodList;
    }
}
