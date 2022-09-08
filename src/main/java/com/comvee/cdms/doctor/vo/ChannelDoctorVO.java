package com.comvee.cdms.doctor.vo;
/**
 * 
 * @author chenhb
 *
 */
public class ChannelDoctorVO {
    private String doctorId;

    private String name;

    private String departId;

    private String departName;

    private String sex;

    private String brief;

    private String positionId;

    private String positionName;

    private String workNo;

    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;

    private String picUrl;

    private String mobilePhone;

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

    /**
     * 医生二维码
     */
    private String wechatUrl;

    /**
     * 数据权限：  1个人   2全院   3科室
     */
    private String dataAuth;

    private String remark; //备注

    private String lastLoginDt; //最终登录时间

    private String userInsertDt; //账号插入时间

    /**
     * 用户状态 1 正常 2 禁用
     */
    private Integer userStatus;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 管理病种 1:糖尿病 2:高血压
     */
    private String entityType;

    /**
     * 是否可以切换医院 0 否 1 是
     */
    private Integer switchHospital;

    private String openId;

    private Integer isVirtual; //'是否是虚拟病区医生 1:虚拟病区 2普通科室'


    //订单数
    private Integer orderNum;
    //复购率
    private Float repurchaseRate;
    //当前患者
    private Integer currentPatient;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Float getRepurchaseRate() {
        return repurchaseRate;
    }

    public void setRepurchaseRate(Float repurchaseRate) {
        this.repurchaseRate = repurchaseRate;
    }

    public Integer getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Integer currentPatient) {
        this.currentPatient = currentPatient;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }


    public String getUserInsertDt() {
        return userInsertDt;
    }

    public void setUserInsertDt(String userInsertDt) {
        this.userInsertDt = userInsertDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    @Override
    public String toString() {
        return "DoctorPO{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                ", sex='" + sex + '\'' +
                ", brief='" + brief + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionName='" + positionName + '\'' +
                ", workNo='" + workNo + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", showInPatient='" + showInPatient + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", skilled='" + skilled + '\'' +
                ", wechatUrl='" + wechatUrl + '\'' +
                ", dataAuth='" + dataAuth + '\'' +
                ", remark='" + remark + '\'' +
                ", lastLoginDt='" + lastLoginDt + '\'' +
                ", userInsertDt='" + userInsertDt + '\'' +
                ", userStatus=" + userStatus +
                ", uid='" + uid + '\'' +
                ", entityType='" + entityType + '\'' +
                ", switchHospital=" + switchHospital +'\'' +
                ", isVirtual=" + isVirtual +
                '}';
    }
}
