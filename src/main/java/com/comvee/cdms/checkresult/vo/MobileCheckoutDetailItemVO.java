package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileCheckoutDetailItemVO implements Serializable {
	/**
	 *  编号
	 */
    private String sid;
    /**
     *  项目
     */
    private String name;
    /**
     *  结果
     */
    private String result;
    /**
     *  单位
     */
    private String unit;
    /**
     *  参考值
     */
    private String reference;
    /**
     * 上次结果
     */
    private String lastResult;

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
