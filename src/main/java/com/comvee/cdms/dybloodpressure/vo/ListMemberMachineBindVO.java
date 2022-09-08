package com.comvee.cdms.dybloodpressure.vo;

/**
 * @Author linr
 * @Date 2021/11/9
 */
public class ListMemberMachineBindVO {

    private String sid;
    private String memberId;
    private String machineNo;
    private String machineType;
    private String isValid; //1绑定0已解绑
    private String memberName;
    private String age;
    private String sex;
    private String idCard;
    private String isInHospital;
    private String planStartDt;
    private String planEndDt;

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

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIsInHospital() {
        return isInHospital;
    }

    public void setIsInHospital(String isInHospital) {
        this.isInHospital = isInHospital;
    }

    public String getPlanStartDt() {
        return planStartDt;
    }

    public void setPlanStartDt(String planStartDt) {
        this.planStartDt = planStartDt;
    }

    public String getPlanEndDt() {
        return planEndDt;
    }

    public void setPlanEndDt(String planEndDt) {
        this.planEndDt = planEndDt;
    }
}
