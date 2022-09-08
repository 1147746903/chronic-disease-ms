package com.comvee.cdms.insulinpump.model.bo;

import java.util.Map;

/**
 * 胰岛素泵用量详情
 */
public class InsulinPumpDosageDetailBO {

    private Double baseTotal;
    private Map<String ,Double> base;
    private Map<String ,Double> add;

    public Double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Map<String, Double> getBase() {
        return base;
    }

    public void setBase(Map<String, Double> base) {
        this.base = base;
    }

    public Map<String, Double> getAdd() {
        return add;
    }

    public void setAdd(Map<String, Double> add) {
        this.add = add;
    }
}
