package com.comvee.cdms.department.model.dto;

import javax.validation.constraints.NotEmpty;

public class UpdateDepartmentAndBedDTO {

    @NotEmpty
    private String departmentId;
    private String departmentName;
    @NotEmpty
    private String hospitalId;
    private String addBedData;
    private String updateBedData;
    private String deleteBedData;
    private String machineId;
    private String machineSn;
    private String machineCode;
    private String bindFlag; //设备类型 02:血糖仪
    private String bid; //住院表主键id

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(String bindFlag) {
        this.bindFlag = bindFlag;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAddBedData() {
        return addBedData;
    }

    public void setAddBedData(String addBedData) {
        this.addBedData = addBedData;
    }

    public String getUpdateBedData() {
        return updateBedData;
    }

    public void setUpdateBedData(String updateBedData) {
        this.updateBedData = updateBedData;
    }

    public String getDeleteBedData() {
        return deleteBedData;
    }

    public void setDeleteBedData(String deleteBedData) {
        this.deleteBedData = deleteBedData;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
