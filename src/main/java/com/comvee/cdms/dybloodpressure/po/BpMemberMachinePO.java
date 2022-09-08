package com.comvee.cdms.dybloodpressure.po;

import java.io.Serializable;

/**
 * 动态血压患者设备绑定表(TBpMemberMachine)实体类
 *
 * @author makejava
 * @since 2021-10-26 09:59:08
 */
public class BpMemberMachinePO implements Serializable {
    private static final long serialVersionUID = 161974989625725400L;

    private String sid;
    /**
     * 设备编号
     */
    private String machineNo;

    private String memberId;
    /**
     * 更新时间
     */
    private String modifyDt;
    /**
     * 入库时间
     */
    private String insertDt;
    /**
     * 是否有效 1 有效 0无效
     */
    private Integer isValid;
    /**
     * 操作者类型 1患者用户 2医护用户
     */
    private Integer operationType;
    /**
     * 操作者编号
     */
    private String operationId;

    private Integer machineType;//设备类型 1门诊2居家
    private String planStartDt;
    private String planEndDt;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMachineNo() {
        return machineNo;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getPlanStartDt() {
        return planStartDt;
    }

    public void setPlanStartDt(String planStartDt) {
        this.planStartDt = planStartDt;
    }

    public String getPlanEndDt() {
        return planEndDt;
    }

    public void setPlanEndDt(String planEndDt) {
        this.planEndDt = planEndDt;
    }
}
