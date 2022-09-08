package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDynamicItemVO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DynamicBloodSugarDynamicDescComparator implements Comparator<DynamicBloodSugarDynamicItemVO> {

    private final static DynamicBloodSugarDynamicDescComparator instance = new DynamicBloodSugarDynamicDescComparator();

    @Override
    public int compare(DynamicBloodSugarDynamicItemVO o1, DynamicBloodSugarDynamicItemVO o2) {
        return o2.getRecordDt().compareTo(o1.getRecordDt());
    }

    public static void sort(List<DynamicBloodSugarDynamicItemVO> list){
        Collections.sort(list ,instance);
    }

}
