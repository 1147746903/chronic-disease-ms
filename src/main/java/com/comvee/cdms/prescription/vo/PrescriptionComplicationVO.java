package com.comvee.cdms.prescription.vo;

/**
 * @author 李左河
 * @date 2018/8/3 11:23.
 */
public class PrescriptionComplicationVO {
    @Override
    public String toString() {
        return "PrescriptionComplicationVO{}";
    }

    /**
     * 糖尿病肾病
     */
    private String hasNephropathy;
    /**
     * 糖尿病视网膜病变
     */
    private String hasRetinopathy;
    /**
     * 糖尿病下肢血管病变
     */
    private String haslowerExt;
    /**
     * 糖尿病足
     */
    private String hasDisFoot;
    /**
     * 周围神经病变
     */
    private String hasPNP;
    /**
     * 冠心病
     */
    private String hasChd;
    /**
     * 高血压
     */
    private String hasHyp;
    /**
     * 自主神经病变
     */
    private String hasAutoNerve;

    public String getHasNephropathy() {
        return hasNephropathy;
    }

    public void setHasNephropathy(String hasNephropathy) {
        this.hasNephropathy = hasNephropathy;
    }

    public String getHasRetinopathy() {
        return hasRetinopathy;
    }

    public void setHasRetinopathy(String hasRetinopathy) {
        this.hasRetinopathy = hasRetinopathy;
    }

    public String getHaslowerExt() {
        return haslowerExt;
    }

    public void setHaslowerExt(String haslowerExt) {
        this.haslowerExt = haslowerExt;
    }

    public String getHasDisFoot() {
        return hasDisFoot;
    }

    public void setHasDisFoot(String hasDisFoot) {
        this.hasDisFoot = hasDisFoot;
    }

    public String getHasPNP() {
        return hasPNP;
    }

    public void setHasPNP(String hasPNP) {
        this.hasPNP = hasPNP;
    }

    public String getHasChd() {
        return hasChd;
    }

    public void setHasChd(String hasChd) {
        this.hasChd = hasChd;
    }

    public String getHasHyp() {
        return hasHyp;
    }

    public void setHasHyp(String hasHyp) {
        this.hasHyp = hasHyp;
    }

    public String getHasAutoNerve() {
        return hasAutoNerve;
    }

    public void setHasAutoNerve(String hasAutoNerve) {
        this.hasAutoNerve = hasAutoNerve;
    }
}
