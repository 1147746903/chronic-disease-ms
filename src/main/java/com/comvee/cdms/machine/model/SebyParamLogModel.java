package com.comvee.cdms.machine.model;

public class SebyParamLogModel {
	private String sid;//主键
	private String machineId;//sn码
	private String machineCode;//机器码
	private String machineOrigin;//机器厂家
	private String insertDt;//插入时间
	private String paramCode;//血糖code
	private String paramValue;//血糖值
	private String paramId;//血糖id
	private String recordDt;//记录时间
	private String isFinish;//crm是否处理1已处理0未处理
	private String paramLevel;//血糖等级
	private String finishDt;//crm处理完成时间
	private String finishMsg;//crm处理内容
	private String finishPerson;//crm处理人
	private String startRecordDt;//查询血糖记录开始时间
	private String endRecordDt;//查询血糖记录结束时间
	private String startFinishDt;//查询处理开始时间
	private String endFinishDt;//查询处理结束时间
	private String paramMsg;//返回crm的文案展示
	private String isValid;
	private String machineType;//机器类型01血压仪02血糖仪
	private String type;//1:2型糖尿病 2:妊娠糖尿病
	private String producerCode;//血糖仪厂家类型
	
	
	public String getProducerCode()
    {
        return producerCode;
    }
    public void setProducerCode(String producerCode)
    {
        this.producerCode = producerCode;
    }
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
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMachineOrigin() {
		return machineOrigin;
	}
	public void setMachineOrigin(String machineOrigin) {
		this.machineOrigin = machineOrigin;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getRecordDt() {
		return recordDt;
	}
	public void setRecordDt(String recordDt) {
		this.recordDt = recordDt;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public String getParamLevel() {
		return paramLevel;
	}
	public void setParamLevel(String paramLevel) {
		this.paramLevel = paramLevel;
	}
	public String getFinishDt() {
		return finishDt;
	}
	public void setFinishDt(String finishDt) {
		this.finishDt = finishDt;
	}
	public String getFinishMsg() {
		return finishMsg;
	}
	public void setFinishMsg(String finishMsg) {
		this.finishMsg = finishMsg;
	}
	public String getStartRecordDt() {
		return startRecordDt;
	}
	public void setStartRecordDt(String startRecordDt) {
		this.startRecordDt = startRecordDt;
	}
	public String getEndRecordDt() {
		return endRecordDt;
	}
	public void setEndRecordDt(String endRecordDt) {
		this.endRecordDt = endRecordDt;
	}
	public String getStartFinishDt() {
		return startFinishDt;
	}
	public void setStartFinishDt(String startFinishDt) {
		this.startFinishDt = startFinishDt;
	}
	public String getEndFinishDt() {
		return endFinishDt;
	}
	public void setEndFinishDt(String endFinishDt) {
		this.endFinishDt = endFinishDt;
	}
	public String getParamMsg() {
		return paramMsg;
	}
	public void setParamMsg(String paramMsg) {
		this.paramMsg = paramMsg;
	}
	public String getFinishPerson() {
		return finishPerson;
	}
	public void setFinishPerson(String finishPerson) {
		this.finishPerson = finishPerson;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
