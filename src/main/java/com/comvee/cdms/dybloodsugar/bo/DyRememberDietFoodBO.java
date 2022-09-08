package com.comvee.cdms.dybloodsugar.bo;

public class DyRememberDietFoodBO {
    private String multipleFoodId; //多选食材id
    private String multipleFood; //多选食材
    private Double total;//摄入量
    private String unit;//单位
    private String sid;//单条记录id

    public String getMultipleFoodId() {
        return multipleFoodId;
    }

    public void setMultipleFoodId(String multipleFoodId) {
        this.multipleFoodId = multipleFoodId;
    }

    public String getMultipleFood() {
        return multipleFood;
    }

    public void setMultipleFood(String multipleFood) {
        this.multipleFood = multipleFood;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
