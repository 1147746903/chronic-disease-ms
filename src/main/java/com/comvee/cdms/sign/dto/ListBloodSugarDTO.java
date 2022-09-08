package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public class ListBloodSugarDTO {

    private String memberId;
    private String startDt;
    private String endDt;
    /**
     * 是否住院记录
     */
    private Integer inHos;

    /**
     * 血糖code
     * param_code
     */
    private String paramCode;

    /**
     * 血糖等级
     * param_level
     */
    private String paramLevel;

    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;

    /**
     * 来源
     * origin
     */
    private Integer origin;

    /**
     * 操作者类型 1 用户 2 医护
     * operator_type
     */
    private Integer operatorType;

    /**
     * operator_id
     */
    private String operatorId;

    private List<String> paramCodeList;//血糖codeList

    private String codeDt;//1、今日  2、三天  3、七天 4、一个月（30天）
    private String[] sidArray;
    private String orderASC;
    private String refreshTime;

    private Integer recordType;//血糖记录类型
    private Integer loadAll;//1查询所有血糖类型
    private String hospitalId;//医院id
    private List<String> hospitalIdList;//医院ids

    public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public String getCodeDt() {
        return codeDt;
    }

    public void setCodeDt(String codeDt) {
        this.codeDt = codeDt;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(String paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public void setSidArray(String[] sidArray) {
        this.sidArray = sidArray;
    }

    public String[] getSidArray() {
        return sidArray;
    }

    public void setOrderASC(String orderASC) {
        this.orderASC = orderASC;
    }

    public String getOrderASC() {
        return orderASC;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getLoadAll() {
        return loadAll;
    }

    public void setLoadAll(Integer loadAll) {
        this.loadAll = loadAll;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }
}
