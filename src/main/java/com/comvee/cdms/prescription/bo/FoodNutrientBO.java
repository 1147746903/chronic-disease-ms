package com.comvee.cdms.prescription.bo;

/**
 * Created with IntelliJ IDEA.
 * User: xuds
 * Date: 2021/1/26
 * Time: 10:59
 * Description: No Description
 **/
public class FoodNutrientBO {
    private String id; // 主键
    private String name;   // 营养素名称
    private String unit;   // 单位
    private String heat; // 能量
    private String protein; // 蛋白质
    private String totalfats; // 脂肪
    private String carbohydrates; // 碳水化合物
    private String na;    // 纳
    private String isValid; // 是否有效 1 有效 0 无效
    private String insertDt;  // 添加时间
    private String modifyDt; // 修改时间

    private Integer num; // 数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getTotalfats() {
        return totalfats;
    }

    public void setTotalfats(String totalfats) {
        this.totalfats = totalfats;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
