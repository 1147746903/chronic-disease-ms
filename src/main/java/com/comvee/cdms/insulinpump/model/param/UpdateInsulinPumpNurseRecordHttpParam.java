package com.comvee.cdms.insulinpump.model.param;

import javax.validation.constraints.NotEmpty;

public class UpdateInsulinPumpNurseRecordHttpParam {

    /**
     * sid
     */
    @NotEmpty(message = "主键不能为空")
    private String sid;


    /**
     * 签名
     * sign
     */
    private String sign;

    /**
     * 型号
     * model
     */
    private String model;

    /**
     * 交班时间
     * shift_over_dt
     */
    private String shiftOverDt;

    /**
     * 上泵时间
     * pump_up_dt
     */
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

    private Integer isLowBloodSugar; //是否发生低血糖

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

    public Integer getIsLowBloodSugar() {
        return isLowBloodSugar;
    }

    public void setIsLowBloodSugar(Integer isLowBloodSugar) {
        this.isLowBloodSugar = isLowBloodSugar;
    }
}
