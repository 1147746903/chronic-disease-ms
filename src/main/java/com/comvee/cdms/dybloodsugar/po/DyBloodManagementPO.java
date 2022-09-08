package com.comvee.cdms.dybloodsugar.po;


public class DyBloodManagementPO {

    private String sid;
    private String hospitalId; //医院id
    private String hospitalName; //医院名称
    private String equipmentNo; //设备号
    private Integer equipmentType;//设备类型：1、小白盒 2、一体机
    private String outBoundDt; //出库日期
    private String lastUseDt; //最新使用时间
    private String remark; //备注
    private String insertDt; //插入时间
    private String modifyDt; //更新时间
    private String recordDt; //记录时间
    private Integer isValid; //是否有效

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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "DyBloodManagementPO{" +
                "sid='" + sid + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", outBoundDt='" + outBoundDt + '\'' +
                ", lastUseDt='" + lastUseDt + '\'' +
                ", remark='" + remark + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", isValid=" + isValid +
                '}';
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }
}
