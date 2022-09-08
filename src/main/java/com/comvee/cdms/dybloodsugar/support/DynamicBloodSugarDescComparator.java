package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DynamicBloodSugarDescComparator implements Comparator<DYYPBloodSugarPO> {

    private final static DynamicBloodSugarDescComparator instance = new DynamicBloodSugarDescComparator();

    @Override
    public int compare(DYYPBloodSugarPO o1, DYYPBloodSugarPO o2) {
        return o2.getRecordTime().compareTo(o1.getRecordTime());
    }

    public static void sort(List<DYYPBloodSugarPO> list){
        Collections.sort(list ,instance);
    }
}
