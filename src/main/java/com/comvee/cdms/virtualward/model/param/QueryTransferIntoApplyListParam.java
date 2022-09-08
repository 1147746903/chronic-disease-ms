package com.comvee.cdms.virtualward.model.param;

import javax.validation.constraints.NotNull;
import java.util.List;

public class QueryTransferIntoApplyListParam {

    private String moreDate;
    private String departmentId;
    private String keyword;
    @NotNull(message = "来源不可为空")
    private Integer origin;
    private String hospitalId;
    private String doctorId;
    private List<String> departIdList;
    private String applyType; // 1:转入 2:转出
    private String departId; //登陆医生的所在科室
    private Integer type; //转入通知 1:待确认 3:已出院

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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getDepartIdList() {
        return departIdList;
    }

    public void setDepartIdList(List<String> departIdList) {
        this.departIdList = departIdList;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
