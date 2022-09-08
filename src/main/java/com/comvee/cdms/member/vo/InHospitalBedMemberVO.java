package com.comvee.cdms.member.vo;

import java.io.Serializable;

/**
 * @Author linr
 * @Date 2021/7/27
 */
public class InHospitalBedMemberVO implements Serializable {
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 入院时间
     */
    private String inHospitalDt;
    /**
     * 床位号
     */
    private String bedNo;
    /**
     * 最近一次血糖时间
     */
    private String paramRecordDt;
    /**
     * 最近一次血糖时间段
     */
    private String paramCode;
    /**
     * 最近一次血糖值
     */
    private String paramValue;
    /**
     * 患者唯一编号
     */
    private String memberId;
    /**
     * 是否关注
     */
    private Integer concernStatus;
    /**
     * 患病类型 A 1型糖尿病 B 2型糖尿病 C 妊娠糖尿病 D 高血压 AD 1型糖尿病&高血压 BD 2型糖尿病&高血压 CD 妊娠糖尿病&高血压
     *         O 其它
     */
    private String diseaseType;
    /**
     * 最近测量血糖次数
     */
    private Integer paramTestNum;
    /**
     * 头像地址
     * pic_url
     */
    private String picUrl;
    /**
     * 最近一次血糖值等级
     */
    private String paramLevel;

    private String height;

    private String weight;

    private String departmentId;

    private String departmentName;
    private String birthday;
    private String mobilePhone;

    /**
     * 使用的设备，多种用，隔开
     */
    private String useMachine;

    private String sid;
    private Integer checkinStatus;

    private String doctorZg;
    private String doctorZgCode;

    /**
     * 远程会诊状态 1 正在会诊
     */
    private Integer remoteConsultationStatus;

    private Integer sensor; //是否有探头 1有0没有
    private Integer sensorValid;//探头是否在有效期内 1在0不在
    private Integer insulinPump;//是否有胰岛素泵1有0没有
    private Integer insulinPumpValid;//是否在有效期内 1在0不在
    private String positionName;//医生职称
    private Integer isVirtual;//是否虚拟病区
    private Integer inHospitalDay;//住院时间
    private Integer diabetesLever;
    private Integer hypLayer;
    private Integer hypLever;
    private Integer dynamicBloodPressureMachine;//是否有绑定中的动态血压设备 1有0没有
    private Integer isJoin;//是否慢病管理

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getInHospitalDt() {
        return inHospitalDt;
    }

    public void setInHospitalDt(String inHospitalDt) {
        this.inHospitalDt = inHospitalDt;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getParamRecordDt() {
        return paramRecordDt;
    }

    public void setParamRecordDt(String paramRecordDt) {
        this.paramRecordDt = paramRecordDt;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public Integer getParamTestNum() {
        return paramTestNum;
    }

    public void setParamTestNum(Integer paramTestNum) {
        this.paramTestNum = paramTestNum;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(String paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUseMachine() {
        return useMachine;
    }

    public void setUseMachine(String useMachine) {
        this.useMachine = useMachine;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(Integer checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getDoctorZg() {
        return doctorZg;
    }

    public void setDoctorZg(String doctorZg) {
        this.doctorZg = doctorZg;
    }

    public String getDoctorZgCode() {
        return doctorZgCode;
    }

    public void setDoctorZgCode(String doctorZgCode) {
        this.doctorZgCode = doctorZgCode;
    }

    public Integer getRemoteConsultationStatus() {
        return remoteConsultationStatus;
    }

    public void setRemoteConsultationStatus(Integer remoteConsultationStatus) {
        this.remoteConsultationStatus = remoteConsultationStatus;
    }

    public Integer getSensor() {
        return sensor;
    }

    public void setSensor(Integer sensor) {
        this.sensor = sensor;
    }

    public Integer getSensorValid() {
        return sensorValid;
    }

    public void setSensorValid(Integer sensorValid) {
        this.sensorValid = sensorValid;
    }

    public Integer getInsulinPump() {
        return insulinPump;
    }

    public void setInsulinPump(Integer insulinPump) {
        this.insulinPump = insulinPump;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getInsulinPumpValid() {
        return insulinPumpValid;
    }

    public void setInsulinPumpValid(Integer insulinPumpValid) {
        this.insulinPumpValid = insulinPumpValid;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Integer getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(Integer inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    public Integer getDiabetesLever() {
        return diabetesLever;
    }

    public void setDiabetesLever(Integer diabetesLever) {
        this.diabetesLever = diabetesLever;
    }

    public Integer getHypLayer() {
        return hypLayer;
    }

    public void setHypLayer(Integer hypLayer) {
        this.hypLayer = hypLayer;
    }

    public Integer getHypLever() {
        return hypLever;
    }

    public void setHypLever(Integer hypLever) {
        this.hypLever = hypLever;
    }

    public Integer getDynamicBloodPressureMachine() {
        return dynamicBloodPressureMachine;
    }

    public void setDynamicBloodPressureMachine(Integer dynamicBloodPressureMachine) {
        this.dynamicBloodPressureMachine = dynamicBloodPressureMachine;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }
}
