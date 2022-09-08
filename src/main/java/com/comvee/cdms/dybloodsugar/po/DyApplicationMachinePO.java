package com.comvee.cdms.dybloodsugar.po;

public class DyApplicationMachinePO {

    private String sid;
    private String appId; //关联应用id
    private String machineNo; //设备号(目前是:imei)
    private String insertDt; //插入时间
    private String updateDt; //更新时间
    private Integer isValid; //是否有效 1:有效 0:无效

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "DyApplicationMachinePO{" +
                "sid='" + sid + '\'' +
                ", appId='" + appId + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
