package com.comvee.cdms.member.vo;

import java.io.Serializable;

/**
 * 患者住院视图实体
 */
public class InHospitalMemberVO implements Serializable {
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

    private String positionName;

    private Integer inHospitalDay;//住院时间


    public Integer getRemoteConsultationStatus() {
        return remoteConsultationStatus;
    }

    public void setRemoteConsultationStatus(Integer remoteConsultationStatus) {
        this.remoteConsultationStatus = remoteConsultationStatus;
    }

    private Integer isVirtualWard; //是否是虚拟病区患者 1:是 0:否

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

    public void setParamLevel(String paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getParamLevel() {
        return paramLevel;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getUseMachine() {
        return useMachine;
    }

    public void setUseMachine(String useMachine) {
        this.useMachine = useMachine;
    }

    public Integer getIsVirtualWard() {
        return isVirtualWard;
    }

    public void setIsVirtualWard(Integer isVirtualWard) {
        this.isVirtualWard = isVirtualWard;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(Integer inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }
}
