package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 李左河
 *
 */
public class AddDoctorAndAccountDTO {

    @NotEmpty
    private String doctorName;

    @NotEmpty
    private String departId;

    @NotEmpty
    private String departName;


    private String sex;

    private String brief;

    @NotEmpty
    private String positionId;

    @NotEmpty
    private String positionName;

    private String workNo;

    private String photoUrl;

    private String phoneNo;

    private String birthday;
    /**
     * 展示在患者端: 1 展示，0不展示
     */
    @NotEmpty
    private String showInPatient;
    @NotEmpty
    private String provinceId;
    @NotEmpty
    private String cityId;
    @NotEmpty
    private String hospitalId;
    @NotEmpty
    private String hospitalName;
    /**
     * 擅长
     */
    private String skilled;

    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;

    private String relateDoctorList;

    private String roleList;

    private String validDt;

    /**
     * 数据权限：  1个人   2全院   3科室
     */
    private String dataAuth;

    private String remark; //备注

    /**
     * 管理病种 1:糖尿病 2:高血压
     */
    private String entityType;

    /**
     * 是否可以切换医院 0 否 1 是
     */
    private Integer switchHospital;

    /**
     * 可切换医院id ',' 隔开
     */
    private String hospitalIds;

    private Integer isVirtual; //是否是虚拟病区医生 1:是 0否

    private Integer isAgentDoctor;//是否合伙人医生 否： 0 是： 1
    private Integer doctorLevel;//医生级别 1县级 2乡镇级 3村级
    private String committeeIds;//关联居委会  逗号隔开

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRelateDoctorList() {
        return relateDoctorList;
    }

    public void setRelateDoctorList(String relateDoctorList) {
        this.relateDoctorList = relateDoctorList;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getShowInPatient() {
        return showInPatient;
    }

    public void setShowInPatient(String showInPatient) {
        this.showInPatient = showInPatient;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getSkilled() {
        return skilled;
    }

    public void setSkilled(String skilled) {
        this.skilled = skilled;
    }

    public String getValidDt() {
        return validDt;
    }

    public void setValidDt(String validDt) {
        this.validDt = validDt;
    }

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    public String getDataAuth() {
        return dataAuth;
    }

    public void setDataAuth(String dataAuth) {
        this.dataAuth = dataAuth;
    }

    public Integer getSwitchHospital() {
        return switchHospital;
    }

    public void setSwitchHospital(Integer switchHospital) {
        this.switchHospital = switchHospital;
    }

    public String getHospitalIds() {
        return hospitalIds;
    }

    public void setHospitalIds(String hospitalIds) {
        this.hospitalIds = hospitalIds;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Integer getIsAgentDoctor() {
        return isAgentDoctor;
    }

    public void setIsAgentDoctor(Integer isAgentDoctor) {
        this.isAgentDoctor = isAgentDoctor;
    }

    public Integer getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(Integer doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public String getCommitteeIds() {
        return committeeIds;
    }

    public void setCommitteeIds(String committeeIds) {
        this.committeeIds = committeeIds;
    }
}
