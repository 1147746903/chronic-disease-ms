package com.comvee.cdms.member.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/7/30
 */
public class MemberSugarHospitalListVO {

    private String sid;
    private String memberId;

    private String recordDt;
    private String hospitalNo;
    private String bedNo;
    private String doctorId;
    private String doctorName;
    private String memberName;
    private Integer isValid;
    private String insertDt;
    private String modifyDt;
    private String departmentId;
    private String memberStatus; //1我的2住院3出院

    //随机
    private Map randomtime;
    private List<Map> randomtimeList;

    //凌晨
    private Map beforedawn;
    private List<Map> beforedawnList;

    //空腹
    private Map beforeBreakfast;
    private List<Map> beforeBreakfastList;

    //早餐后
    private Map afterBreakfast;
    private List<Map> afterBreakfastList;

    //午餐前
    private Map beforeLunch;
    private List<Map> beforeLunchList;

    //午餐后
    private Map afterLunch;
    private List<Map> afterLunchList;

    //晚餐前
    private Map beforeDinner;
    private List<Map> beforeDinnerList;

    //晚餐后
    private Map afterDinner;
    private List<Map> afterDinnerList;

    //睡前
    private Map beforeSleep;
    private List<Map> beforeSleepList;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    public Map getRandomtime() {
        return randomtime;
    }

    public void setRandomtime(Map randomtime) {
        this.randomtime = randomtime;
    }

    public List<Map> getRandomtimeList() {
        return randomtimeList;
    }

    public void setRandomtimeList(List<Map> randomtimeList) {
        this.randomtimeList = randomtimeList;
    }

    public Map getBeforedawn() {
        return beforedawn;
    }

    public void setBeforedawn(Map beforedawn) {
        this.beforedawn = beforedawn;
    }

    public List<Map> getBeforedawnList() {
        return beforedawnList;
    }

    public void setBeforedawnList(List<Map> beforedawnList) {
        this.beforedawnList = beforedawnList;
    }

    public Map getBeforeBreakfast() {
        return beforeBreakfast;
    }

    public void setBeforeBreakfast(Map beforeBreakfast) {
        this.beforeBreakfast = beforeBreakfast;
    }

    public List<Map> getBeforeBreakfastList() {
        return beforeBreakfastList;
    }

    public void setBeforeBreakfastList(List<Map> beforeBreakfastList) {
        this.beforeBreakfastList = beforeBreakfastList;
    }

    public Map getAfterBreakfast() {
        return afterBreakfast;
    }

    public void setAfterBreakfast(Map afterBreakfast) {
        this.afterBreakfast = afterBreakfast;
    }

    public List<Map> getAfterBreakfastList() {
        return afterBreakfastList;
    }

    public void setAfterBreakfastList(List<Map> afterBreakfastList) {
        this.afterBreakfastList = afterBreakfastList;
    }

    public Map getBeforeLunch() {
        return beforeLunch;
    }

    public void setBeforeLunch(Map beforeLunch) {
        this.beforeLunch = beforeLunch;
    }

    public List<Map> getBeforeLunchList() {
        return beforeLunchList;
    }

    public void setBeforeLunchList(List<Map> beforeLunchList) {
        this.beforeLunchList = beforeLunchList;
    }

    public Map getAfterLunch() {
        return afterLunch;
    }

    public void setAfterLunch(Map afterLunch) {
        this.afterLunch = afterLunch;
    }

    public List<Map> getAfterLunchList() {
        return afterLunchList;
    }

    public void setAfterLunchList(List<Map> afterLunchList) {
        this.afterLunchList = afterLunchList;
    }

    public Map getBeforeDinner() {
        return beforeDinner;
    }

    public void setBeforeDinner(Map beforeDinner) {
        this.beforeDinner = beforeDinner;
    }

    public List<Map> getBeforeDinnerList() {
        return beforeDinnerList;
    }

    public void setBeforeDinnerList(List<Map> beforeDinnerList) {
        this.beforeDinnerList = beforeDinnerList;
    }

    public Map getAfterDinner() {
        return afterDinner;
    }

    public void setAfterDinner(Map afterDinner) {
        this.afterDinner = afterDinner;
    }

    public List<Map> getAfterDinnerList() {
        return afterDinnerList;
    }

    public void setAfterDinnerList(List<Map> afterDinnerList) {
        this.afterDinnerList = afterDinnerList;
    }

    public Map getBeforeSleep() {
        return beforeSleep;
    }

    public void setBeforeSleep(Map beforeSleep) {
        this.beforeSleep = beforeSleep;
    }

    public List<Map> getBeforeSleepList() {
        return beforeSleepList;
    }

    public void setBeforeSleepList(List<Map> beforeSleepList) {
        this.beforeSleepList = beforeSleepList;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
}
