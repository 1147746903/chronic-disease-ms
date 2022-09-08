package com.comvee.cdms.level.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2019/11/20 11:02
 */
public class ListMemberLevelDTO {

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 开始日期
     */
    private String startDt;
    /**
     * 结束日期
     */
    private String endDt;
    /**
     * 1近一个月月 2 近三个月
     */
    private String code;

    /**
     * 分级类型 1 高血压
     */
    private Integer levelType;

    /**
     * 医院id
     */
    private String hospitalId;

    private List<String> doctorIdList;

    /**
     * 患者姓名
     */
    private String memberName;

    /**
     * 分级级别
     */
    private Integer memberLevel;

    /**
     * 分层级别
     */
    private Integer memberLayer;

    private Integer confirm;

    private List<String> members;
    private List<String> hospitalIdList;

    private String keyword;
    private String mobilePhone;
    private Integer otherLevelLayer;//其他分层分级  1

    private String changeDate;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getMemberLayer() {
        return memberLayer;
    }

    public void setMemberLayer(Integer memberLayer) {
        this.memberLayer = memberLayer;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getOtherLevelLayer() {
        return otherLevelLayer;
    }

    public void setOtherLevelLayer(Integer otherLevelLayer) {
        this.otherLevelLayer = otherLevelLayer;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
}
