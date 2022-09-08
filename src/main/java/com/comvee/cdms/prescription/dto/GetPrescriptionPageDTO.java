package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class GetPrescriptionPageDTO implements Serializable {
    /**
     * 行数
     */
    private int rows = 1;
    /**
     * 页码
     */
    private int page = 10;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 患者名称
     */
    private String memberName;
    /**
     * 患者名称拼音
     */
    private String memberNamePy;
    /**
     * 患者手机
     */
    private String mobilePhone;
    /**
     * 是否完成提交
     */
    private Boolean submit;
    /**
     * 当前医生编号
     */
    private String doctorId;
    /**
     * 当前医生的医院编号
     */
    private String hospitalId;

    /**
     * 查找来源  1工作台查找
     */
    private Integer findOrigin;

    private Integer eohType;

    private String operatorId;


    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getFindOrigin() {
        return findOrigin;
    }

    public void setFindOrigin(Integer findOrigin) {
        this.findOrigin = findOrigin;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
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

    @Override
    public String toString() {
        return "GetPrescriptionPageDTO{" +
                "rows=" + rows +
                ", page=" + page +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberNamePy='" + memberNamePy + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", submit='" + submit + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
