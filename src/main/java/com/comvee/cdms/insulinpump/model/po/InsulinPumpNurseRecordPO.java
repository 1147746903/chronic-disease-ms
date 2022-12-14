package com.comvee.cdms.insulinpump.model.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_insulin_pump_nurse_record
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class InsulinPumpNurseRecordPO {
    /**
     * sid
     */
    private String sid;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 住院号
     * hospital_no
     */
    private String hospitalNo;

    /**
     * 操作者id
     * operator_id
     */
    private String operatorId;

    /**
     * 签名
     * sign
     */
    private String sign;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * valid
     */
    private Integer valid;

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

    /**
     * 虚拟病区记录id
     */
    private String virtualWardId;

    private Integer isLowBloodSugar; //是都发生低血糖

    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getIsLowBloodSugar() {
        return isLowBloodSugar;
    }

    public void setIsLowBloodSugar(Integer isLowBloodSugar) {
        this.isLowBloodSugar = isLowBloodSugar;
    }

    public String getVirtualWardId() {
        return virtualWardId;
    }

    public void setVirtualWardId(String virtualWardId) {
        this.virtualWardId = virtualWardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.sid
     *
     * @return the value of t_insulin_pump_nurse_record.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.sid
     *
     * @param sid the value for t_insulin_pump_nurse_record.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.member_id
     *
     * @return the value of t_insulin_pump_nurse_record.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.member_id
     *
     * @param memberId the value for t_insulin_pump_nurse_record.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.hospital_no
     *
     * @return the value of t_insulin_pump_nurse_record.hospital_no
     *
     * @mbggenerated
     */
    public String getHospitalNo() {
        return hospitalNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.hospital_no
     *
     * @param hospitalNo the value for t_insulin_pump_nurse_record.hospital_no
     *
     * @mbggenerated
     */
    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.operator_id
     *
     * @return the value of t_insulin_pump_nurse_record.operator_id
     *
     * @mbggenerated
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.operator_id
     *
     * @param operatorId the value for t_insulin_pump_nurse_record.operator_id
     *
     * @mbggenerated
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.sign
     *
     * @return the value of t_insulin_pump_nurse_record.sign
     *
     * @mbggenerated
     */
    public String getSign() {
        return sign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.sign
     *
     * @param sign the value for t_insulin_pump_nurse_record.sign
     *
     * @mbggenerated
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.insert_dt
     *
     * @return the value of t_insulin_pump_nurse_record.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.insert_dt
     *
     * @param insertDt the value for t_insulin_pump_nurse_record.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.update_dt
     *
     * @return the value of t_insulin_pump_nurse_record.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.update_dt
     *
     * @param updateDt the value for t_insulin_pump_nurse_record.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.valid
     *
     * @return the value of t_insulin_pump_nurse_record.valid
     *
     * @mbggenerated
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.valid
     *
     * @param valid the value for t_insulin_pump_nurse_record.valid
     *
     * @mbggenerated
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.model
     *
     * @return the value of t_insulin_pump_nurse_record.model
     *
     * @mbggenerated
     */
    public String getModel() {
        return model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.model
     *
     * @param model the value for t_insulin_pump_nurse_record.model
     *
     * @mbggenerated
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.shift_over_dt
     *
     * @return the value of t_insulin_pump_nurse_record.shift_over_dt
     *
     * @mbggenerated
     */
    public String getShiftOverDt() {
        return shiftOverDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.shift_over_dt
     *
     * @param shiftOverDt the value for t_insulin_pump_nurse_record.shift_over_dt
     *
     * @mbggenerated
     */
    public void setShiftOverDt(String shiftOverDt) {
        this.shiftOverDt = shiftOverDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.pump_up_dt
     *
     * @return the value of t_insulin_pump_nurse_record.pump_up_dt
     *
     * @mbggenerated
     */
    public String getPumpUpDt() {
        return pumpUpDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.pump_up_dt
     *
     * @param pumpUpDt the value for t_insulin_pump_nurse_record.pump_up_dt
     *
     * @mbggenerated
     */
    public void setPumpUpDt(String pumpUpDt) {
        this.pumpUpDt = pumpUpDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.pump_down_dt
     *
     * @return the value of t_insulin_pump_nurse_record.pump_down_dt
     *
     * @mbggenerated
     */
    public String getPumpDownDt() {
        return pumpDownDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.pump_down_dt
     *
     * @param pumpDownDt the value for t_insulin_pump_nurse_record.pump_down_dt
     *
     * @mbggenerated
     */
    public void setPumpDownDt(String pumpDownDt) {
        this.pumpDownDt = pumpDownDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.electricity_quantity
     *
     * @return the value of t_insulin_pump_nurse_record.electricity_quantity
     *
     * @mbggenerated
     */
    public String getElectricityQuantity() {
        return electricityQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.electricity_quantity
     *
     * @param electricityQuantity the value for t_insulin_pump_nurse_record.electricity_quantity
     *
     * @mbggenerated
     */
    public void setElectricityQuantity(String electricityQuantity) {
        this.electricityQuantity = electricityQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.consumables
     *
     * @return the value of t_insulin_pump_nurse_record.consumables
     *
     * @mbggenerated
     */
    public String getConsumables() {
        return consumables;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.consumables
     *
     * @param consumables the value for t_insulin_pump_nurse_record.consumables
     *
     * @mbggenerated
     */
    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_insulin_pump_nurse_record.data_json
     *
     * @return the value of t_insulin_pump_nurse_record.data_json
     *
     * @mbggenerated
     */
    public String getDataJson() {
        return dataJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_insulin_pump_nurse_record.data_json
     *
     * @param dataJson the value for t_insulin_pump_nurse_record.data_json
     *
     * @mbggenerated
     */
    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    @Override
    public String toString() {
        return "InsulinPumpNurseRecordPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", sign='" + sign + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", valid=" + valid +
                ", model='" + model + '\'' +
                ", shiftOverDt='" + shiftOverDt + '\'' +
                ", pumpUpDt='" + pumpUpDt + '\'' +
                ", pumpDownDt='" + pumpDownDt + '\'' +
                ", electricityQuantity='" + electricityQuantity + '\'' +
                ", consumables='" + consumables + '\'' +
                ", dataJson='" + dataJson + '\'' +
                ", virtualWardId='" + virtualWardId + '\'' +
                ", isLowBloodSugar=" + isLowBloodSugar +
                '}';
    }
}