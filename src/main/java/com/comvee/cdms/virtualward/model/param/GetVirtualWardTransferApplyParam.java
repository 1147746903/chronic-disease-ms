package com.comvee.cdms.virtualward.model.param;

import javax.validation.constraints.NotEmpty;

import java.util.List;

public class GetVirtualWardTransferApplyParam {

    @NotEmpty(message = "患者id不能为空")
    private String memberId;
    private String departmentId;
    private Integer applyType;
    private String id;
    private Integer applyStatus;
    private String hospitalId;
    private String hospitalNo;
    private List<Integer> applyTypeList;
    private String keyword;
    private String foreignId;

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

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

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public List<Integer> getApplyTypeList() {
        return applyTypeList;
    }

    public void setApplyTypeList(List<Integer> applyTypeList) {
        this.applyTypeList = applyTypeList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "GetVirtualWardTransferApplyParam{" +
                "memberId='" + memberId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", applyType=" + applyType +
                ", id='" + id + '\'' +
                ", applyStatus=" + applyStatus +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", applyTypeList=" + applyTypeList +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
