package com.comvee.cdms.foot.bo;

/**
 * @author: suyz
 * @date: 2019/4/23
 */
public class FootPrescriptionQrCodeBO {

    private String qrCodeData;
    private String qrCodeInvalidDt;

    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public String getQrCodeInvalidDt() {
        return qrCodeInvalidDt;
    }

    public void setQrCodeInvalidDt(String qrCodeInvalidDt) {
        this.qrCodeInvalidDt = qrCodeInvalidDt;
    }
}
