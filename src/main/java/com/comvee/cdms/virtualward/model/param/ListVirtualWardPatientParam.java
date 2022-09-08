package com.comvee.cdms.virtualward.model.param;

import java.util.List;

public class ListVirtualWardPatientParam {

    private String keyword;
    private String departmentId;
    private Integer transferStatus;
    List<Integer> transferStatusList;
    private Integer useInsulinPumpStatus;
    private String statStartDt;
    private String statEndDt;
    private Integer applyStatus;
    private String hospitalId;
    private List<String> departIdList;
    private String doctorId;
    private Integer applyType; //申请类型 1:转入 2:转出
    private String doctorName; //登陆医生姓名
    private Integer operation; //来源 1:嵌入页虚拟病区-历史记录 2:虚拟病区历史通知-历史记录
    private List<Integer> applyTypeList; //申请类型 1:转入 2:转出 3:直接出院

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getStatStartDt() {
        return statStartDt;
    }

    public void setStatStartDt(String statStartDt) {
        this.statStartDt = statStartDt;
    }

    public String getStatEndDt() {
        return statEndDt;
    }

    public void setStatEndDt(String statEndDt) {
        this.statEndDt = statEndDt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Integer getUseInsulinPumpStatus() {
        return useInsulinPumpStatus;
    }

    public void setUseInsulinPumpStatus(Integer useInsulinPumpStatus) {
        this.useInsulinPumpStatus = useInsulinPumpStatus;
    }

    public List<String> getDepartIdList() {
        return departIdList;
    }

    public void setDepartIdList(List<String> departIdList) {
        this.departIdList = departIdList;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public List<Integer> getTransferStatusList() {
        return transferStatusList;
    }

    public void setTransferStatusList(List<Integer> transferStatusList) {
        this.transferStatusList = transferStatusList;
    }

    public List<Integer> getApplyTypeList() {
        return applyTypeList;
    }

    public void setApplyTypeList(List<Integer> applyTypeList) {
        this.applyTypeList = applyTypeList;
    }

    @Override
    public String toString() {
        return "ListVirtualWardPatientParam{" +
                "keyword='" + keyword + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", transferStatus=" + transferStatus +
                ", transferStatusList=" + transferStatusList +
                ", useInsulinPumpStatus=" + useInsulinPumpStatus +
                ", statStartDt='" + statStartDt + '\'' +
                ", statEndDt='" + statEndDt + '\'' +
                ", applyStatus=" + applyStatus +
                ", hospitalId='" + hospitalId + '\'' +
                ", departIdList=" + departIdList +
                ", doctorId='" + doctorId + '\'' +
                ", applyType=" + applyType +
                ", doctorName='" + doctorName + '\'' +
                ", operation=" + operation +
                ", applyTypeList=" + applyTypeList +
                '}';
    }
}
