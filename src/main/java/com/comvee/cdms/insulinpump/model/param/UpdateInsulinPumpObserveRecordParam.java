package com.comvee.cdms.insulinpump.model.param;

import javax.validation.constraints.NotEmpty;

public class UpdateInsulinPumpObserveRecordParam {

    /**
     * sid
     */
    @NotEmpty(message = "主键sid不能为空")
    private String sid;

    /**
     * 签名
     * sign
     */
    private String sign;

    /**
     * 记录日期
     * record_dt
     */
    private String recordDt;

    /**
     * 诊断结果
     * diagnosis
     */
    private String diagnosis;

    /**
     * 数据json
     * data_json
     */
    private String dataJson;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
