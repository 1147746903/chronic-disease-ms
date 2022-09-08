package com.comvee.cdms.insulinpump.model.param;

import javax.validation.constraints.NotEmpty;

public class AddInsulinPumpNurseRecordHttpParam {

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
     * 型号
     * model
     */
    @NotEmpty(message = "model不能为空")
    private String model;

    /**
     * 交班时间
     * shift_over_dt
     */
    @NotEmpty(message = "shiftOverDt不能为空")
    private String shiftOverDt;

    /**
     * 上泵时间
     * pump_up_dt
     */
    @NotEmpty(message = "pumpUpDt不能为空")
    private String pumpUpDt;

    /**
     * 下泵时间
     * pump_down_dt
     */
    private String pumpDownDt;

    /**
     * 电量
     * electricity_quantity
     */
    private String electricityQuantity;

    /**
     * 耗材
     * consumables
     */
    private String consumables;

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

    private String isLowBloodSugar; //是都发生低血糖

    public String getIsLowBloodSugar() {
        return isLowBloodSugar;
    }

    public void setIsLowBloodSugar(String isLowBloodSugar) {
        this.isLowBloodSugar = isLowBloodSugar;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getShiftOverDt() {
        return shiftOverDt;
    }

    public void setShiftOverDt(String shiftOverDt) {
        this.shiftOverDt = shiftOverDt;
    }

    public String getPumpUpDt() {
        return pumpUpDt;
    }

    public void setPumpUpDt(String pumpUpDt) {
        this.pumpUpDt = pumpUpDt;
    }

    public String getPumpDownDt() {
        return pumpDownDt;
    }

    public void setPumpDownDt(String pumpDownDt) {
        this.pumpDownDt = pumpDownDt;
    }

    public String getElectricityQuantity() {
        return electricityQuantity;
    }

    public void setElectricityQuantity(String electricityQuantity) {
        this.electricityQuantity = electricityQuantity;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
