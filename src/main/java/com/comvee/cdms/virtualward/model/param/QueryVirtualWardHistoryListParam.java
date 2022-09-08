package com.comvee.cdms.virtualward.model.param;

public class QueryVirtualWardHistoryListParam {

    private String departmentId;
    private String keyword;
    private Integer transferStatus;
    private Integer useInsulinPumpStatus;


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
