package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BindSensorDTO implements Serializable {

    @NotEmpty(message = "患者编号（memberId）不可为空")
    private String memberId;
    @NotEmpty(message = "传感器编号（sensorNo）不可为空")
    private String sensorNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_member_sensor.operation_type
     *
     * @mbg.generated
     */
    private Byte operationType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dy_member_sensor.operation_id
     *
     * @mbg.generated
     */
    private String operationId;

    /**
     * 绑定类型 1 居家 2 院内
     */
    @NotNull(message = "绑定分类不能为空")
    private Integer bindType;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Byte getOperationType() {
        return operationType;
    }

    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Integer getBindType() {
        return bindType;
    }

    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }
}
