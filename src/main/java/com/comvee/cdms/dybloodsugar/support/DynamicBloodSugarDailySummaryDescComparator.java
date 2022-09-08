package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDailySummaryVO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DynamicBloodSugarDailySummaryDescComparator implements Comparator<DynamicBloodSugarDailySummaryVO> {

    private static final DynamicBloodSugarDailySummaryDescComparator instance = new DynamicBloodSugarDailySummaryDescComparator();

    @Override
    public int compare(DynamicBloodSugarDailySummaryVO o1, DynamicBloodSugarDailySummaryVO o2) {
        return o2.getRecordDt().compareTo(o1.getRecordDt());
    }

    public static void sort(List<DynamicBloodSugarDailySummaryVO> list){
        Collections.sort(list ,instance);
    }
}
