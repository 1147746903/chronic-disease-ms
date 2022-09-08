package com.comvee.cdms.follow.vo;

/**
 * @Author linr
 * @Date 2022/8/5
 */
public class ListFollowPlatformRecordVO {

    private String sid;
    private String memberId;
    private String memberName;
    private String foreignId;
    private String diseaseType;
    private String type;//随访类型
    private String followType;//随访方式  1 门诊 2 家庭 3 电话
    private String doctorId;
    private String doctorName;
    private String xltz;//心理调整
    private String zyxw;//遵医行为
    private String followDt;//随访日期
    private String followInfo;//随访json

    private String mobilePhone;
    private String sex;
    private String birthday;
    private Integer age;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
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

    public String getXltz() {
        return xltz;
    }

    public void setXltz(String xltz) {
        this.xltz = xltz;
    }

    public String getZyxw() {
        return zyxw;
    }

    public void setZyxw(String zyxw) {
        this.zyxw = zyxw;
    }

    public String getFollowDt() {
        return followDt;
    }

    public void setFollowDt(String followDt) {
        this.followDt = followDt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
