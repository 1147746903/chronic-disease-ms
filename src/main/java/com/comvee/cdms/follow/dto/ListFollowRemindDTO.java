package com.comvee.cdms.follow.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wangxy
 * @date: 2018/11/10
 */
public class ListFollowRemindDTO implements Serializable {

    private String doctorId;

    private String memberName;

    private String type; //1 定期随访提醒 2 电话随访提醒 3 随访完成提醒 4 其他提醒

    private List<String> doctorIdList;//医生编号列表

    private String followType;  //随访类型

    private String templateId;  //模板id

    private List<String> typeList;  //提醒类型列表

    private List<String> followTypeList; //随访类型

    private String authority; //随访类型下拉列表(权限)

    private String hospitalId; //医院id
    private String status; //状态 0 待完成 1已完成
    private List<String> statusList;
    private String nextDt; //预约随访时间

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getFollowTypeList() {
        return followTypeList;
    }

    public void setFollowTypeList(List<String> followTypeList) {
        this.followTypeList = followTypeList;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    @Override
    public String toString() {
        return "ListFollowRemindDTO{" +
                "doctorId='" + doctorId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", type='" + type + '\'' +
                ", doctorIdList=" + doctorIdList +
                ", followType='" + followType + '\'' +
                ", templateId='" + templateId + '\'' +
                ", typeList=" + typeList +
                ", followTypeList='" + followTypeList + '\'' +
                ", authority='" + authority + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
