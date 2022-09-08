package com.comvee.cdms.dybloodsugar.vo;

import java.io.Serializable;
import java.util.List;

public class YPCharDataOfXYVO implements Serializable {
    private List<String> xArea;
    private Object dataSource;

    public List<String> getXArea() {
        return xArea;
    }

    public void setXArea(List<String> xArea) {
        this.xArea = xArea;
    }

    public Object getDataSource() {
        return dataSource;
    }

    public void setDataSource(Object dataSource) {
        this.dataSource = dataSource;
    }
}
