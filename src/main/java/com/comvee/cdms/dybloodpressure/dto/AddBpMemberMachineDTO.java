package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2021/10/26
 */
public class AddBpMemberMachineDTO {

    /**
     * 主键
     */
    private String sid;
    /**
     * 设备编号
     */
    @NotBlank(message = "machineNo不允许为空")
    private String machineNo;

    @NotBlank(message = "memberId不允许为空")
    private String memberId;

    /**
     * 操作者类型 1患者用户 2医护用户
     */
    private Integer operationType;
    /**
     * 操作者编号
     */
    private String operationId;

    @NotNull(message = "machineType不允许为空")
    private Integer machineType;//设备类型

    private String hospitalId;

    @NotBlank(message = "autoStopMinutes不允许为空")
    private String autoStopMinutes;//自动停止时间


    public String getMachineNo() {
        return machineNo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAutoStopMinutes() {
        return autoStopMinutes;
    }

    public void setAutoStopMinutes(String autoStopMinutes) {
        this.autoStopMinutes = autoStopMinutes;
    }
}
