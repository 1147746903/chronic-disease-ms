package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

public class CheckoutDetailVO implements Serializable {
    private String sid;
    /**
     * 检验报告id
     */
    private String checkoutId;
    /**
     * 检验子项code
     */
    private String checkoutCode;
    /**
     * 检验子项名称
     */
    private String checkoutName;
    /**
     * 检验结果（定量）
     */
    private String checkoutResult;
    /**
     * 检验结果单位
     */
    private String resultUnit;
    /**
     * 定性结果
     */
    private String qualitativeResult;
    /**
     * 参考范围
     */
    private String referenceRange;
    /**
     * 异常标志
     */
    private String abnormalSign;
    /**
     * 子项缩写
     */
    private String acronym;
    /**
     * 上一次检查结果
     */
    private String checkoutResultLast;
    /**
     * 检验时间(yyyy-MM-dd hh:mm:ss)
     */
    private String checkoutDatetime;

    private String hospitalId;

    private Integer recordOrigin;
    private String checkoutResultLastStr;

    public Integer getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(Integer recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getCheckoutId() {
        return checkoutId;
    }
    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }
    public String getCheckoutCode() {
        return checkoutCode;
    }
    public void setCheckoutCode(String checkoutCode) {
        this.checkoutCode = checkoutCode;
    }
    public String getCheckoutName() {
        return checkoutName;
    }
    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }
    public String getCheckoutResult() {
        return checkoutResult;
    }
    public void setCheckoutResult(String checkoutResult) {
        this.checkoutResult = checkoutResult;
    }
    public String getResultUnit() {
        return resultUnit;
    }
    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }
    public String getQualitativeResult() {
        return qualitativeResult;
    }
    public void setQualitativeResult(String qualitativeResult) {
        this.qualitativeResult = qualitativeResult;
    }
    public String getReferenceRange() {
        return referenceRange;
    }
    public void setReferenceRange(String referenceRange) {
        this.referenceRange = referenceRange;
    }
    public String getAbnormalSign() {
        return abnormalSign;
    }
    public void setAbnormalSign(String abnormalSign) {
        this.abnormalSign = abnormalSign;
    }
    public String getAcronym() {
        return acronym;
    }
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    public String getCheckoutResultLast() {
        return checkoutResultLast;
    }
    public void setCheckoutResultLast(String checkoutResultLast) {
        this.checkoutResultLast = checkoutResultLast;
    }
    public String getCheckoutDatetime() {
        return checkoutDatetime;
    }
    public void setCheckoutDatetime(String checkoutDatetime) {
        this.checkoutDatetime = checkoutDatetime;
    }

    public void setCheckoutResultLastStr(String checkoutResultLastStr) {
        this.checkoutResultLastStr = checkoutResultLastStr;
    }

    public String getCheckoutResultLastStr() {
        return checkoutResultLastStr;
    }
}
