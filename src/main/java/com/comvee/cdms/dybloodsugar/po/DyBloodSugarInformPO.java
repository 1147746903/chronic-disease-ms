package com.comvee.cdms.dybloodsugar.po;

public class DyBloodSugarInformPO {
    private String sid;
    private String memberId; //患者id
    private String memberName; //患者名字
    private String sensorNo; //探头号
    private Integer bindType; //探头绑定类型
    private String insertDt; //插入时间
    private String modifyDt; //更新时间
    private Integer isValid; //是否有效 1:有效 0:无效
    /**
     * 是否住院
     */
    private Integer inHos;
    private String departmentId;
    private String doctorId;
    /**
     * 近24小时血糖概况
     */
    private String survey;

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Integer getBindType() {
        return bindType;
    }

    public void setBindType(Integer bindType) {
        this.bindType = bindType;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "DyBloodSugarInformPo{" +
                "sid='" + sid + '\'' +
                "memberId='" + memberId + '\'' +
                "memberName='" + memberName + '\'' +
                ", sensorNo='" + sensorNo + '\'' +
                ", bindType=" + bindType +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
