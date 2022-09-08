package com.comvee.cdms.dybloodsugar.dto;

/**
 * @author hbj
 * @description
 * @date 2021/6/8 16:37
 */
public class PageFoodItemDTO {

    private String foodName;

    private Integer page = 1;

    private Integer size = 10;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
