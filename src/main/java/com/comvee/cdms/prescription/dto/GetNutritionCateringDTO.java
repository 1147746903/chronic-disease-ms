package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 获取食谱列表DTO
 */
public class GetNutritionCateringDTO implements Serializable {

    /**
     * 食谱内容编号
     */
    private String nid;
    /**
     * 推荐食谱热量上限
     */
    private String calHigh;
    /**
     * 推荐食谱热量下限
     */
    private String calLow;
    /**
     * 0:糖尿病（非妊娠） 1:妊娠糖尿病
     */
    private Integer eohType;

    /**
     * 膳食模式
     */
    private String mealsModel;

    /**
     * 医生id
     * @return
     */
    private List<String> doctorIdList;

    private String caloricType;

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public void setCalHigh(String calHigh) {
        this.calHigh = calHigh;
    }

    public void setCalLow(String calLow) {
        this.calLow = calLow;
    }

    public String getCalHigh() {
        return calHigh;
    }

    public String getCalLow() {
        return calLow;
    }

    public String getMealsModel() {
        return mealsModel;
    }

    public void setMealsModel(String mealsModel) {
        this.mealsModel = mealsModel;
    }

    public String getCaloricType() {
        return caloricType;
    }

    public void setCaloricType(String caloricType) {
        this.caloricType = caloricType;
    }
}
