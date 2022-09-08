package com.comvee.cdms.virtualward.model.param;

import java.util.List;

public class QueryTransferApplyListParam {

    private String moreDate;
    private String departmentId;
    private String keyword;
    private String remindDateLess;
    private Integer applyType;
    private Integer applyStatus;
    private String hospitalId;
    private List<String> departIdList;
    private Integer isDirectDischarge;
    private String remindDateMore;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMoreDate() {
        return moreDate;
    }

    public void setMoreDate(String moreDate) {
        this.moreDate = moreDate;
    }

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

    public String getRemindDateLess() {
        return remindDateLess;
    }

    public void setRemindDateLess(String remindDateLess) {
        this.remindDateLess = remindDateLess;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public List<String> getDepartIdList() {
        return departIdList;
    }

    public void setDepartIdList(List<String> departIdList) {
        this.departIdList = departIdList;
    }

    public Integer getIsDirectDischarge() {
        return isDirectDischarge;
    }

    public void setIsDirectDischarge(Integer isDirectDischarge) {
        this.isDirectDischarge = isDirectDischarge;
    }

    public String getRemindDateMore() {
        return remindDateMore;
    }

    public void setRemindDateMore(String remindDateMore) {
        this.remindDateMore = remindDateMore;
    }

    @Override
    public String toString() {
        return "QueryTransferApplyListParam{" +
                "moreDate='" + moreDate + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", keyword='" + keyword + '\'' +
                ", remindDateLess='" + remindDateLess + '\'' +
                ", applyType=" + applyType +
                ", applyStatus=" + applyStatus +
                ", hospitalId='" + hospitalId + '\'' +
                ", departIdList=" + departIdList +
                '}';
    }
}
