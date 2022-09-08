package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 李左河
 *
 */
public class UpdateDoctorDTO {

    @NotEmpty
    private String doctorId;

    private String doctorName;

    private String departId;

    private String departName;

    private String sex;

    private String brief;

    private String positionId;

    private String positionName;

    private String workNo;

    private String photoUrl;

    private String phoneNo;

    private String birthday;
    /**
     * 展示在患者端: 1 展示，0不展示
     */
    private String showInPatient;
    private String provinceId;
    private String cityId;
    private String hospitalId;
    private String hospitalName;
    /**
     * 擅长
     */
    private String skilled;

    private String userName;

    private String password;

    private String roleList;

    private String relateDoctorList;
    /**
     * 医生二维码
     */
    private String wechatUrl;

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
     * 是否新增账号,角色,团队关系 true是
     */
    private boolean addFlag;

    /**
     * 更新来源 1 后台更新
     */
    private Integer updateOrigin;

    /**
     * 是否可以切换医院 0 否 1 是
     */
    private Integer switchHospital;

    /**
     * 可切换医院id ',' 隔开
     */
    private String hospitalIds;

    private String openId;
    private String unionId;

    private String isVirtual; //是否是虚拟病区医生 1:是 0否

    private Integer isAgentDoctor;//是否合伙人医生 否： 0 是： 1

    private Integer doctorLevel;//医生级别 1县级 2乡镇级 3村级
    private String committeeIds;//关联居委会  逗号隔开

    public boolean isAddFlag() {
        return addFlag;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public boolean getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }

    public String getDataAuth() {
        return dataAuth;
    }

    public void setDataAuth(String dataAuth) {
        this.dataAuth = dataAuth;
    }

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
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

    public Integer getUpdateOrigin() {
        return updateOrigin;
    }

    public void setUpdateOrigin(Integer updateOrigin) {
        this.updateOrigin = updateOrigin;
    }

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    public String toString() {
        return "UpdateDoctorDTO{" +
                "doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                ", sex='" + sex + '\'' +
                ", brief='" + brief + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionName='" + positionName + '\'' +
                ", workNo='" + workNo + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", birthday='" + birthday + '\'' +
                ", showInPatient='" + showInPatient + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", skilled='" + skilled + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roleList='" + roleList + '\'' +
                ", relateDoctorList='" + relateDoctorList + '\'' +
                ", wechatUrl='" + wechatUrl + '\'' +
                ", dataAuth='" + dataAuth + '\'' +
                ", remark='" + remark + '\'' +
                ", entityType='" + entityType + '\'' +
                ", addFlag=" + addFlag +
                ", updateOrigin=" + updateOrigin +
                ", switchHospital=" + switchHospital +
                ", hospitalIds='" + hospitalIds + '\'' +
                ", openId='" + openId + '\'' +
                ", isVirtual='" + isVirtual + '\'' +
                '}';
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
