package com.comvee.cdms.sign.dto;

public class BloodGlucoseRecordPrintingDTO {
    private String operationId; //操作者id
    private String startDt; //监测开始时间
    private String endDt; //监测结束时间
    private String departmentId; //科室id
    private String departmentName; //科室
    private String hospitalId; //医院id
    private String type; //1:默认取全部患者,2取住院患者,3取虚拟病区患者
    private Integer paramLevel; //血糖情况 1:低,5:高
    private String authority; //权限 (住院和虚拟病区)

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String toString() {
        return "BloodGlucoseRecordPrintingDTO{" +
                "operationId='" + operationId + '\'' +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", type='" + type + '\'' +
                ", paramLevel=" + paramLevel +
                ", authority='" + authority + '\'' +
                '}';
    }
}
