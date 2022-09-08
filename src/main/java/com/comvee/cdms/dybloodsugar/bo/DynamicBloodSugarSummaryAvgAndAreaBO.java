package com.comvee.cdms.dybloodsugar.bo;

/**
 * 动态血糖总结餐前餐后血糖平均值及曲线下面积对象
 */
public class DynamicBloodSugarSummaryAvgAndAreaBO {

    private Double beforeMealAvg1H;
    private Double beforeMealArea1H;
    private Double afterMealAvg2H;
    private Double afterMealArea2H;
    private Double afterMealAvg3H;
    private Double afterMealArea3H;

    public Double getBeforeMealAvg1H() {
        return beforeMealAvg1H;
    }

    public void setBeforeMealAvg1H(Double beforeMealAvg1H) {
        this.beforeMealAvg1H = beforeMealAvg1H;
    }

    public Double getBeforeMealArea1H() {
        return beforeMealArea1H;
    }

    public void setBeforeMealArea1H(Double beforeMealArea1H) {
        this.beforeMealArea1H = beforeMealArea1H;
    }

    public Double getAfterMealAvg2H() {
        return afterMealAvg2H;
    }

    public void setAfterMealAvg2H(Double afterMealAvg2H) {
        this.afterMealAvg2H = afterMealAvg2H;
    }

    public Double getAfterMealArea2H() {
        return afterMealArea2H;
    }

    public void setAfterMealArea2H(Double afterMealArea2H) {
        this.afterMealArea2H = afterMealArea2H;
    }

    public Double getAfterMealAvg3H() {
        return afterMealAvg3H;
    }

    public void setAfterMealAvg3H(Double afterMealAvg3H) {
        this.afterMealAvg3H = afterMealAvg3H;
    }

    public Double getAfterMealArea3H() {
        return afterMealArea3H;
    }

    public void setAfterMealArea3H(Double afterMealArea3H) {
        this.afterMealArea3H = afterMealArea3H;
    }
}

