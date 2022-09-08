package com.comvee.cdms.dybloodsugar.bo;

import java.util.List;

public class DyRememberDietBO {
    private String otherFood; //其他食材
    private List<DyRememberDietFoodBO> dietFoodList; //已经添加的食材

    public String getOtherFood() {
        return otherFood;
    }

    public void setOtherFood(String otherFood) {
        this.otherFood = otherFood;
    }

    public List<DyRememberDietFoodBO> getDietFoodList() {
        return dietFoodList;
    }

    public void setDietFoodList(List<DyRememberDietFoodBO> dietFoodList) {
        this.dietFoodList = dietFoodList;
    }
}
