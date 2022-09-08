package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: wangxy
 * @date: 2018/10/17
 */
public class AddMemberWebDTO implements Serializable{

    @NotNull
    private String idCard;
    @NotNull
    private String doctorId;
    //操作者ID
    private String operatorId;

    private String visitNo;
    private String memberName;
    private String birthday;
    private Integer sex;
    private String diabetesType;
    private String height;
    private String weight;
    private String mobilePhone;
    /**
     * 患者来源 1 web端医生添加  2 APP医生添加 3  小程序添加 4 筛查系统同步患者
     */
    private Integer memberSource;

    /**
     * 医患关系创建方式 1 web端添加患者  2 APP添加患者 3 扫描二维码 4 小程序患者申请添加 5 筛查系统同步 6转诊添加医患关系
     */
    private Integer relationWay;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getMemberSource() {
        return memberSource;
    }

    public void setMemberSource(Integer memberSource) {
        this.memberSource = memberSource;
    }

    public Integer getRelationWay() {
        return relationWay;
    }

    public void setRelationWay(Integer relationWay) {
        this.relationWay = relationWay;
    }
}
