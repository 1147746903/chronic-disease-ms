package com.comvee.cdms.virtualward.model.param;

import java.util.List;

public class GetVirtualWardParam {

    private String memberId;
    private String departmentId;
    /**
     * 转移状态 1 已转入 2 已转出
     * transfer_status
     */
    private Integer transferStatus;

    private String id;
    private String hospitalNo;
    private String hospitalId;
    private String intoDate;
    private List<Integer> transferStatusList;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getIntoDate() {
        return intoDate;
    }

    public void setIntoDate(String intoDate) {
        this.intoDate = intoDate;
    }

    public List<Integer> getTransferStatusList() {
        return transferStatusList;
    }

    public void setTransferStatusList(List<Integer> transferStatusList) {
        this.transferStatusList = transferStatusList;
    }

    @Override
    public String toString() {
        return "GetVirtualWardParam{" +
                "memberId='" + memberId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", transferStatus=" + transferStatus +
                ", id='" + id + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", intoDate='" + intoDate + '\'' +
                '}';
    }
}
