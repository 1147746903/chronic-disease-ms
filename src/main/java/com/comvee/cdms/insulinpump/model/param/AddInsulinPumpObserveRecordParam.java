package com.comvee.cdms.insulinpump.model.param;

import javax.validation.constraints.NotEmpty;

public class AddInsulinPumpObserveRecordParam {


    /**
     * 患者id
     * member_id
     */
    @NotEmpty(message = "memberId不能为空")
    private String memberId;

    /**
     * 住院号
     * hospital_no
     */
    @NotEmpty(message = "hospitalNo不能为空")
    private String hospitalNo;


    /**
     * 签名
     * sign
     */
    @NotEmpty(message = "sign不能为空")
    private String sign;


    /**
     * 记录日期
     * record_dt
     */
    @NotEmpty(message = "recordDt不能为空")
    private String recordDt;

    /**
     * 诊断结果
     * diagnosis
     */
    @NotEmpty(message = "diagnosis不能为空")
    private String diagnosis;

    /**
     * 数据json
     * data_json
     */
    private String dataJson;

    /**
     * 虚拟病区记录id
     */
    @NotEmpty(message = "虚拟病区id不能为空")
    private String virtualWardId;

    @NotEmpty(message = "cardNo 不能为空")
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVirtualWardId() {
        return virtualWardId;
    }

    public void setVirtualWardId(String virtualWardId) {
        this.virtualWardId = virtualWardId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
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
