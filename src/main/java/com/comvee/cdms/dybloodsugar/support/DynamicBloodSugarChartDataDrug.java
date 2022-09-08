package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDataDrugVO;

import java.util.List;

public interface DynamicBloodSugarChartDataDrug extends DynamicBloodSugarChartDataBase{

    void setDrugList(List<DynamicBloodChartDataDrugVO> drugList);
}
