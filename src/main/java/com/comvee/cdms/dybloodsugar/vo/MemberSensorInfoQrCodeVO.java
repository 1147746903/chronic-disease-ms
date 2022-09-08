package com.comvee.cdms.dybloodsugar.vo;

import java.io.Serializable;

public class MemberSensorInfoQrCodeVO implements Serializable {
    private String qrcodeUrl;
    private String dataUrl;
    private String invalidDt;

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setInvalidDt(String invalidDt) {
        this.invalidDt = invalidDt;
    }

    public String getInvalidDt() {
        return invalidDt;
    }
}
