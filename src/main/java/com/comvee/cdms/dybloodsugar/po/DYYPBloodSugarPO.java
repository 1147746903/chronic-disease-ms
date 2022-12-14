package com.comvee.cdms.dybloodsugar.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class DYYPBloodSugarPO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.sid
     *
     * @mbg.generated
     */
    private String sid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.batch_id
     *
     * @mbg.generated
     */
    private String batchId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.value
     *
     * @mbg.generated
     */
    private BigDecimal value;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.record_dt
     *
     * @mbg.generated
     */
    @JSONField(format="yyyy-MM-dd")
    private Date recordDt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.record_time
     *
     * @mbg.generated
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.param_code
     *
     * @mbg.generated
     */
    private String paramCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.record_origin
     *
     * @mbg.generated
     */
    private Byte recordOrigin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.level
     *
     * @mbg.generated
     */
    private Byte level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.is_valid
     *
     * @mbg.generated
     */
    private Byte isValid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.insert_dt
     *
     * @mbg.generated
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date insertDt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.modify_dt
     *
     * @mbg.generated
     */
    private Date modifyDt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.sensor_no
     *
     * @mbg.generated
     */
    private String sensorNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_yp_blood_sugar.machine_no
     *
     * @mbg.generated
     */
    private String machineNo;


    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.sid
     *
     * @return the value of dy_yp_blood_sugar.sid
     *
     * @mbg.generated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.sid
     *
     * @param sid the value for dy_yp_blood_sugar.sid
     *
     * @mbg.generated
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.batch_id
     *
     * @return the value of dy_yp_blood_sugar.batch_id
     *
     * @mbg.generated
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.batch_id
     *
     * @param batchId the value for dy_yp_blood_sugar.batch_id
     *
     * @mbg.generated
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.value
     *
     * @return the value of dy_yp_blood_sugar.value
     *
     * @mbg.generated
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.value
     *
     * @param value the value for dy_yp_blood_sugar.value
     *
     * @mbg.generated
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.record_dt
     *
     * @return the value of dy_yp_blood_sugar.record_dt
     *
     * @mbg.generated
     */
    public Date getRecordDt() {
        return recordDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.record_dt
     *
     * @param recordDt the value for dy_yp_blood_sugar.record_dt
     *
     * @mbg.generated
     */
    public void setRecordDt(Date recordDt) {
        this.recordDt = recordDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.record_time
     *
     * @return the value of dy_yp_blood_sugar.record_time
     *
     * @mbg.generated
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.record_time
     *
     * @param recordTime the value for dy_yp_blood_sugar.record_time
     *
     * @mbg.generated
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.param_code
     *
     * @return the value of dy_yp_blood_sugar.param_code
     *
     * @mbg.generated
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.param_code
     *
     * @param paramCode the value for dy_yp_blood_sugar.param_code
     *
     * @mbg.generated
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.record_origin
     *
     * @return the value of dy_yp_blood_sugar.record_origin
     *
     * @mbg.generated
     */
    public Byte getRecordOrigin() {
        return recordOrigin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.record_origin
     *
     * @param recordOrigin the value for dy_yp_blood_sugar.record_origin
     *
     * @mbg.generated
     */
    public void setRecordOrigin(Byte recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.level
     *
     * @return the value of dy_yp_blood_sugar.level
     *
     * @mbg.generated
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.level
     *
     * @param level the value for dy_yp_blood_sugar.level
     *
     * @mbg.generated
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.remark
     *
     * @return the value of dy_yp_blood_sugar.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.remark
     *
     * @param remark the value for dy_yp_blood_sugar.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.is_valid
     *
     * @return the value of dy_yp_blood_sugar.is_valid
     *
     * @mbg.generated
     */
    public Byte getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.is_valid
     *
     * @param isValid the value for dy_yp_blood_sugar.is_valid
     *
     * @mbg.generated
     */
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.insert_dt
     *
     * @return the value of dy_yp_blood_sugar.insert_dt
     *
     * @mbg.generated
     */
    public Date getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.insert_dt
     *
     * @param insertDt the value for dy_yp_blood_sugar.insert_dt
     *
     * @mbg.generated
     */
    public void setInsertDt(Date insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.modify_dt
     *
     * @return the value of dy_yp_blood_sugar.modify_dt
     *
     * @mbg.generated
     */
    public Date getModifyDt() {
        return modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.modify_dt
     *
     * @param modifyDt the value for dy_yp_blood_sugar.modify_dt
     *
     * @mbg.generated
     */
    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.sensor_no
     *
     * @return the value of dy_yp_blood_sugar.sensor_no
     *
     * @mbg.generated
     */
    public String getSensorNo() {
        return sensorNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.sensor_no
     *
     * @param sensorNo the value for dy_yp_blood_sugar.sensor_no
     *
     * @mbg.generated
     */
    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo == null ? null : sensorNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dy_yp_blood_sugar.machine_no
     *
     * @return the value of dy_yp_blood_sugar.machine_no
     *
     * @mbg.generated
     */
    public String getMachineNo() {
        return machineNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dy_yp_blood_sugar.machine_no
     *
     * @param machineNo the value for dy_yp_blood_sugar.machine_no
     *
     * @mbg.generated
     */
    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo == null ? null : machineNo.trim();
    }

    @Override
    public String toString() {
        return "DYYPBloodSugarPO{" +
                "sid='" + sid + '\'' +
                ", batchId='" + batchId + '\'' +
                ", value=" + value +
                ", recordDt=" + recordDt +
                ", recordTime=" + recordTime +
                ", paramCode='" + paramCode + '\'' +
                ", recordOrigin=" + recordOrigin +
                ", level=" + level +
                ", remark='" + remark + '\'' +
                ", isValid=" + isValid +
                ", insertDt=" + insertDt +
                ", modifyDt=" + modifyDt +
                ", sensorNo='" + sensorNo + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}