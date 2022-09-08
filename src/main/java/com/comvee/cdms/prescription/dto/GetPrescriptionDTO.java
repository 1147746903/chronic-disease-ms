package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class GetPrescriptionDTO implements Serializable {
    /**
     * 主键唯一标示
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;

    private String memberName;
    /**
     * 专科医生id
     */
    private String doctorId;
    /**
     * 团队id
     */
    private String teamId;
    /**
     * 医院编号
     */
    private String hospitalId;
    /**
     * 是否有效数据 1有效 0无效
     */
    private Integer isValid = 1;
    /**
     * 管理处方状态(1待制定 2保存草稿 3已完成)
     */
    private Integer schedule;
    /**
     * 下发状态 1已下发 0未下发
     */
    private Integer handDown;
    /**
     * 唯一标示列表
     */
    private List<String> sidList;

    /**
     * 是否完成
     */
    private Boolean complete;
    /**
     * 医生编号列表
     */
    private List<String> doctorIdList;
    /**
     * 患者编号列表
     */
    private List<String> memberIdList;
    private List<String> hospitalIdList;

    /**
     * 处方类型
     */
    private Integer eohType;

    private String operatorId;

    private Integer type; //管理处方类型

    private String modifyDt;
    private String startDt;
    private String endDt;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getHandDown() {
        return handDown;
    }

    public void setHandDown(Integer handDown) {
        this.handDown = handDown;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
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

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }
}
