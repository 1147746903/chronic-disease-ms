package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2020/3/5 17:09
 */
public class MemberParamValueDTO {
    /**
     * 筛查参数
     */
    private String keyValueJson;
    /**
     * 科室id串
     */
    private String departmentIdStr;

    private Double highValue;
    private Double lowValue;
    private Double highRate;
    private Double lowRate;
    private Integer treeTimesHigh;
    private Double standardValue;
    /**
     * 在院状态 0非在院 ，1在院
     */
    private Integer checkinStatus;

    private List<String> departmentIdList;

    /**
     * 获取类型 0异常 1偏低 2偏高 3正常 4全部;
     */
    private Integer bloodType;

    private String doctorId;

    private String paramDt;  //记录日期

    private String hospitalId;

    private Integer isLook; //是否已查看

    private Integer virtualWardAuthority; //是否有虚拟病区大模块权限 1:有 0:没有

    private Integer isLoadVirtualWard; //是否加载虚拟病区患者 1:是  0:否

    private Integer transferStatus; //'转移状态 1 已转入 2 已转出

    private Integer applyStatus; //申请状态  1:未申请 2:已申请转出

    private Boolean loadVirtualWard;
    private List<String> virtualWardDepartmentIdList;

    private Boolean dealIsLook;

    private String moreModifyDt;

    public String getMoreModifyDt() {
        return moreModifyDt;
    }

    public void setMoreModifyDt(String moreModifyDt) {
        this.moreModifyDt = moreModifyDt;
    }

    public Boolean getDealIsLook() {
        return dealIsLook;
    }

    public void setDealIsLook(Boolean dealIsLook) {
        this.dealIsLook = dealIsLook;
    }

    public Boolean getLoadVirtualWard() {
        return loadVirtualWard;
    }

    public void setLoadVirtualWard(Boolean loadVirtualWard) {
        this.loadVirtualWard = loadVirtualWard;
    }

    public List<String> getVirtualWardDepartmentIdList() {
        return virtualWardDepartmentIdList;
    }

    public void setVirtualWardDepartmentIdList(List<String> virtualWardDepartmentIdList) {
        this.virtualWardDepartmentIdList = virtualWardDepartmentIdList;
    }

    public String getKeyValueJson() {
        return keyValueJson;
    }

    public void setKeyValueJson(String keyValueJson) {
        this.keyValueJson = keyValueJson;
    }

    public String getDepartmentIdStr() {
        return departmentIdStr;
    }

    public void setDepartmentIdStr(String departmentIdStr) {
        this.departmentIdStr = departmentIdStr;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getHighRate() {
        return highRate;
    }

    public void setHighRate(Double highRate) {
        this.highRate = highRate;
    }

    public Double getLowRate() {
        return lowRate;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Integer getTreeTimesHigh() {
        return treeTimesHigh;
    }

    public void setTreeTimesHigh(Integer treeTimesHigh) {
        this.treeTimesHigh = treeTimesHigh;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public Integer getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(Integer checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public Integer getBloodType() {
        return bloodType;
    }

    public void setBloodType(Integer bloodType) {
        this.bloodType = bloodType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getIsLook() {
        return isLook;
    }

    public void setIsLook(Integer isLook) {
        this.isLook = isLook;
    }

    public Integer getVirtualWardAuthority() {
        return virtualWardAuthority;
    }

    public void setVirtualWardAuthority(Integer virtualWardAuthority) {
        this.virtualWardAuthority = virtualWardAuthority;
    }

    public Integer getIsLoadVirtualWard() {
        return isLoadVirtualWard;
    }

    public void setIsLoadVirtualWard(Integer isLoadVirtualWard) {
        this.isLoadVirtualWard = isLoadVirtualWard;
    }

    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Override
    public String toString() {
        return "MemberParamValueDTO{" +
                "keyValueJson='" + keyValueJson + '\'' +
                ", departmentIdStr='" + departmentIdStr + '\'' +
                ", highValue=" + highValue +
                ", lowValue=" + lowValue +
                ", highRate=" + highRate +
                ", lowRate=" + lowRate +
                ", treeTimesHigh=" + treeTimesHigh +
                ", standardValue=" + standardValue +
                ", checkinStatus=" + checkinStatus +
                ", departmentIdList=" + departmentIdList +
                ", bloodType=" + bloodType +
                ", doctorId='" + doctorId + '\'' +
                ", paramDt='" + paramDt + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", isLook=" + isLook +
                ", virtualWardAuthority=" + virtualWardAuthority +
                ", isLoadVirtualWard=" + isLoadVirtualWard +
                ", transferStatus=" + transferStatus +
                '}';
    }
}
