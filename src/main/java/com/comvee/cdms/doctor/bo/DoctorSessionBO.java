package com.comvee.cdms.doctor.bo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public class DoctorSessionBO implements Serializable{
//    private static final long serialVersionUID = 4585926427837713163L;
    private static final long serialVersionUID = -4157346169704347969L;

    private String doctorId;
    private String doctorName;
    private String departId;
    private String departName;
    private String sex;
    private String workNo;
    private String brief;
    private String positionId;
    private String positionName;
    private String photoUrl;
    private String phoneNo;
    private String uid;
    private String birthday;

    /**
     * 首席的医院id
     */
    private String hospitalId;

    /**
     * 首席医院名称
     */
    private String hospitalName;

    /**
     * 展示在患者端: 1 展示，0不展示
     */
    private String showInPatient;
    private String provinceId;
    private String cityId;
    /**
     * 擅长
     */
    private String skilled;

    private List<String> relateDoctorList;

    /**
     * 管理病种 1:糖尿病 2:高血压
     */
    private String entityType;

    /**
     * 是否可以切换医院 0 否 1 是
     */
    private Integer switchHospital;

    private Integer isVirtual; // 是否有勾选虚拟病区
    private Integer doctorLevel;//医生级别  1县级 2乡镇级 3村级

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public List<String> getRelateDoctorList() {
        return relateDoctorList;
    }

    public void setRelateDoctorList(List<String> relateDoctorList) {
        this.relateDoctorList = relateDoctorList;
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

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getSkilled() {
        return skilled;
    }

    public void setSkilled(String skilled) {
        this.skilled = skilled;
    }

    public Integer getSwitchHospital() {
        return switchHospital;
    }

    public void setSwitchHospital(Integer switchHospital) {
        this.switchHospital = switchHospital;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Integer getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(Integer doctorLevel) {
        this.doctorLevel = doctorLevel;
    }
}
