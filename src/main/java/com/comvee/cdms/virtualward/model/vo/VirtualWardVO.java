package com.comvee.cdms.virtualward.model.vo;

public class VirtualWardVO {
    private String departmentName; //科室
    private String bedNo; //床位
    private String memberName; //患者
    private String hospitalNo; //住院号
    private String intoDt; //转入时间
    private String outDt; //转出时间
    private String applyDoctorName; //申请医生名称
    private Integer transferStatus; //状态
    private String applyDoctorId; //申请医生id
    private Integer applyStatus; //申请状态 1:转入 2:转出 3:转入未确认 4:转出未确认
    private String insertDt;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getIntoDt() {
        return intoDt;
    }

    public void setIntoDt(String intoDt) {
        this.intoDt = intoDt;
    }

    public String getOutDt() {
        return outDt;
    }

    public void setOutDt(String outDt) {
        this.outDt = outDt;
    }


    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getApplyDoctorId() {
        return applyDoctorId;
    }

    public void setApplyDoctorId(String applyDoctorId) {
        this.applyDoctorId = applyDoctorId;
    }

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    @Override
    public String toString() {
        return "VirtualWardVO{" +
                "departmentName='" + departmentName + '\'' +
                ", badNo='" + bedNo + '\'' +
                ", memberName='" + memberName + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", intoDt='" + intoDt + '\'' +
                ", outDt='" + outDt + '\'' +
                ", transferStatus='" + transferStatus + '\'' +
                ", applyDoctorId='" + applyDoctorId + '\'' +
                ", applyDoctorName='" + applyDoctorName + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                '}';
    }
}
