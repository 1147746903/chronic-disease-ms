package com.comvee.cdms.member.vo;

import java.io.Serializable;

/**
 * @Author linr
 * @Date 2021/7/27
 */
public class OutHospitalListMemberVO implements Serializable {
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
     * 患者唯一编号
     */
    private String memberId;
    /**
     * 患病类型 A 1型糖尿病 B 2型糖尿病 C 妊娠糖尿病 D 高血压 AD 1型糖尿病&高血压 BD 2型糖尿病&高血压 CD 妊娠糖尿病&高血压
     *         O 其它
     */
    private String diseaseType;
    /**
     * 测量血糖次数
     */
    private Integer paramTestNum;


    private String departmentId;

    private String departmentName;

    private String sid;

    private String doctorZg;
    private String doctorZgCode;


    private Integer sensor; //是否有探头 1有0没有
    private Integer sensorValid;//探头是否在有效期内 1在0不在
    private Integer insulinPump;//是否有胰岛素泵1有0没有
    private Integer insulinPumpValid;//胰岛素泵过期  1在0不在
    private String positionName;//医生职称

    private String outHospitalDt;//出院时间
    private String inHospitalDay;//住院天数

    private String idCard;
    private String birthday;
    private String fat;
    private String essential_hyp;
    private String is_diabetes;

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getOutHospitalDt() {
        return outHospitalDt;
    }

    public void setOutHospitalDt(String outHospitalDt) {
        this.outHospitalDt = outHospitalDt;
    }

    public String getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(String inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getEssential_hyp() {
        return essential_hyp;
    }

    public void setEssential_hyp(String essential_hyp) {
        this.essential_hyp = essential_hyp;
    }

    public String getIs_diabetes() {
        return is_diabetes;
    }

    public void setIs_diabetes(String is_diabetes) {
        this.is_diabetes = is_diabetes;
    }

    public Integer getInsulinPumpValid() {
        return insulinPumpValid;
    }

    public void setInsulinPumpValid(Integer insulinPumpValid) {
        this.insulinPumpValid = insulinPumpValid;
    }
}
