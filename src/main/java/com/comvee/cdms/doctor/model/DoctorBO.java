package com.comvee.cdms.doctor.model;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * @author 李左河
 *
 */
public class DoctorBO {
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
    private String currentLeaderId;
    private String userId;
    private String sessionToken;
    private String hospital;
    private String birthday;

    /**
     * 首席的医院id
     */
    private String hospitalId;
    /**
     * 当前登录医生的医院编号
     */
    private String currentHospitalId;
    /**
     * 首席医院名称
     */
    private String hospitalName;
    /**
     * 当前登录医生的医院名称
     */
    private String currentHospitalName;

    /**
     * 重新登录认证token
     */
    private AuthenticationToken authenticationToken;
    /**
     * APP重新登录账号密码
     */
    private String userPassword;
    private String userNo;

    /**
     * 角色名称
     */
    private String description;
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

    /**
     * 医生微信二维码
     */
    private String wechatCode;

    /**
     * 审核状态：0未审核，1审核通过，2审核不通过
     */
    private Integer verify;

    public String getCurrentHospitalId() {
        return currentHospitalId;
    }

    public void setCurrentHospitalId(String currentHospitalId) {
        this.currentHospitalId = currentHospitalId;
    }

    public String getCurrentHospitalName() {
        return currentHospitalName;
    }

    public void setCurrentHospitalName(String currentHospitalName) {
        this.currentHospitalName = currentHospitalName;
    }

    public void setAuthenticationToken(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCurrentLeaderId() {
        return currentLeaderId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setCurrentLeaderId(String currentLeaderId) {
        this.currentLeaderId = currentLeaderId;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserNo() {
        return userNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }
}
