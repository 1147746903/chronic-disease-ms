package com.comvee.cdms.doctor.dto;

/**
 * @author: 李左河
 * @date: 2018/3/30 14:28.
 */
public class DoctorDTO {

    private String param;
    private String photoUrl;
    private String doctorName;
    private String phoneNo;
    private String birthday;
    private Integer sex;
    private Integer showInPatient;
    private String provinceId;
    private String cityId;
    private String hospitalId;
    private String hospitalName;
    private String departId;
    private String departName;
    private String positionId;
    private String positionName;
    private String skilled;
    private String brief;
    private String currentLeaderId;
    private String doctorId;
    /**
     * 助手医生id字符串，用逗号分隔
     */
    private String doctorIds;

    /**
     * 权限类型
     * 1全院，2个人
     */
    private Integer authorityType;
    /**
     * 权限科室ids
     */
    private String authorityDepartmentIds;

    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 0未审核，1审核通过，2审核不通过
     */
    private Integer verify;

    /**
     * 主键
     */
    private String sid;

    /**
     * 来源：1 his导入， 2 医生注册
     */
    private Integer source;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getShowInPatient() {
        return showInPatient;
    }

    public void setShowInPatient(Integer showInPatient) {
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

    public String getSkilled() {
        return skilled;
    }

    public void setSkilled(String skilled) {
        this.skilled = skilled;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCurrentLeaderId() {
        return currentLeaderId;
    }

    public void setCurrentLeaderId(String currentLeaderId) {
        this.currentLeaderId = currentLeaderId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(String doctorIds) {
        this.doctorIds = doctorIds;
    }

    public Integer getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(Integer authorityType) {
        this.authorityType = authorityType;
    }

    public String getAuthorityDepartmentIds() {
        return authorityDepartmentIds;
    }

    public void setAuthorityDepartmentIds(String authorityDepartmentIds) {
        this.authorityDepartmentIds = authorityDepartmentIds;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "param='" + param + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", showInPatient=" + showInPatient +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionName='" + positionName + '\'' +
                ", skilled='" + skilled + '\'' +
                ", brief='" + brief + '\'' +
                ", currentLeaderId='" + currentLeaderId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorIds='" + doctorIds + '\'' +
                ", authorityType=" + authorityType +
                ", authorityDepartmentIds='" + authorityDepartmentIds + '\'' +
                '}';
    }
}
