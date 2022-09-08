package com.comvee.cdms.glu.model;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class QualityControlModel implements Serializable {
	private static final long serialVersionUID = 4290687960724679445L;
	private String sid;
    private String machineId;
    private String qcPaperNo;
    private String qcLiquidNo;
    private String qcLiquidLevel;
    private String value;
    private String passStatus;
    private String optionUserId;
    private String isValid;
    private String insertDt;
    private String modifyDt;
    private String qcLiquidLow;
    private String qcLiquidHigh;
    private String optionUserName;
    private String machineSn;
    private String recordDt;
    private String explainText;
    private String machineStatus;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getQcPaperNo() {
        return qcPaperNo;
    }

    public void setQcPaperNo(String qcPaperNo) {
        this.qcPaperNo = qcPaperNo;
    }

    public String getQcLiquidNo() {
        return qcLiquidNo;
    }

    public void setQcLiquidNo(String qcLiquidNo) {
        this.qcLiquidNo = qcLiquidNo;
    }

    public String getQcLiquidLevel() {
        return qcLiquidLevel;
    }

    public void setQcLiquidLevel(String qcLiquidLevel) {
        this.qcLiquidLevel = qcLiquidLevel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public String getOptionUserId() {
        return optionUserId;
    }

    public void setOptionUserId(String optionUserId) {
        this.optionUserId = optionUserId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getQcLiquidLow() {
        return qcLiquidLow;
    }

    public void setQcLiquidLow(String qcLiquidLow) {
        this.qcLiquidLow = qcLiquidLow;
    }

    public String getQcLiquidHigh() {
        return qcLiquidHigh;
    }

    public void setQcLiquidHigh(String qcLiquidHigh) {
        this.qcLiquidHigh = qcLiquidHigh;
    }

    public String getOptionUserName() {
        return optionUserName;
    }

    public void setOptionUserName(String optionUserName) {
        this.optionUserName = optionUserName;
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getExplainText() {
        return explainText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }
}
