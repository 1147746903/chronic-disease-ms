package com.comvee.cdms.dybloodsugar.dto;


public class DyBloodManagementDTO {

    private String sid;
    private String hospitalId; //医院id
    private String hospitalName; //医院id
    private String equipmentNo; //设备号
    private Integer equipmentType;//设备类型：1、小白盒 2、一体机
    private String outBoundDt; //出库日期
    private String lastUseDt; //最新使用时间
    private String remark; //备注
    private String description; //描述
    private String whileList; //ip白名单
    private String pushUrl; //推送接口地址

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getOutBoundDt() {
        return outBoundDt;
    }

    public void setOutBoundDt(String outBoundDt) {
        this.outBoundDt = outBoundDt;
    }

    public String getLastUseDt() {
        return lastUseDt;
    }

    public void setLastUseDt(String lastUseDt) {
        this.lastUseDt = lastUseDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhileList() {
        return whileList;
    }

    public void setWhileList(String whileList) {
        this.whileList = whileList;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    @Override
    public String toString() {
        return "DyBloodManagementDTO{" +
                "sid='" + sid + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", outBoundDt='" + outBoundDt + '\'' +
                ", lastUseDt='" + lastUseDt + '\'' +
                ", remark='" + remark + '\'' +
                ", description='" + description + '\'' +
                ", whileList='" + whileList + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                '}';
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }
}
