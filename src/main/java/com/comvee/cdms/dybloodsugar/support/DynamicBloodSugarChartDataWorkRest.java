package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDataDietVO;

public interface DynamicBloodSugarChartDataWorkRest extends DynamicBloodSugarChartDataBase{

    void setSleepTime(DynamicBloodChartDataDietVO sleepTime);

    void setBreakfastTime(DynamicBloodChartDataDietVO dietVO);

    void setLunchTime(DynamicBloodChartDataDietVO dietVO);

    void setDinnerTime(DynamicBloodChartDataDietVO dietVO);

}
