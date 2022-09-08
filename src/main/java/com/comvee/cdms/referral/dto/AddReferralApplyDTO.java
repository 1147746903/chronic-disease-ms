package com.comvee.cdms.referral.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 李左河
 * @date: 2018/3/28 9:18.
 */
public class AddReferralApplyDTO implements Serializable {
    /**
     * 转诊医生编号
     */
    private String doctorId;

    /**
     * 转诊医生名称
     */
    private String doctorName;

    /**
     * 转诊对象编号
     */
    @NotBlank(message = "转诊对象编号(memberId)不可为空！")
    private String memberId;

    /**
     * 转诊对象名称
     */
    @NotBlank(message = "转诊对象名称(memberName)不可为空！")
    private String memberName;

    /**
     * 转诊医院编号
     */
    @NotBlank(message = "转诊医院编号(hospitalId)不可为空！")
    private String hospitalId;

    /**
     * 转诊医院名称
     */
    @NotBlank(message = "转诊医院名称(hospitalName)不可为空！")
    private String hospitalName;

    /**
     * 转诊科室编号
     */
    private String departmentId;

    /**
     * 转诊科室名称
     */
    private String departmentName;

    /**
     * 申请医生编号
     */
    private String applyDoctorId;

    /**
     * 申请医生名称
     */
    private String applyDoctorName;

    /**
     * 申请医院编号
     */
    @NotBlank(message = "申请医院编号(applyHospitalId)不可为空！")
    private String applyHospitalId;

    /**
     * 申请医院名称
     */
    @NotBlank(message = "申请医院名称(applyHospitalName)不可为空！")
    private String applyHospitalName;

    /**
     * 申请科室编号
     */
    private String applyDepartmentId;

    /**
     * 申请科室名称
     */
    private String applyDepartmentName;

    /**
     * 申请时间
     */
    @NotBlank(message = "申请时间(applyDt)不可为空！")
    private String applyDt;

    /**
     * 申请（转诊）理由
     */
    @NotBlank(message = "申请（转诊）理由(applyReason)不可为空！")
    private String applyReason;

    /**
     * 转诊对象基本信息JSON（年龄，性别）
     */
    private String memberInfo;

    /**
     * 是否授权转诊医院查看患者问诊记录 1授权 0未授权
     */
    private Integer showVisit;

    /**
     * 是否接收 1已接收 0未接收
     */
    private Integer status;

    /**
     * 是否有效数据
     */
    private Integer isValid;

    /**
     * 入库时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 转诊类型：1上转，2下转
     */
    @NotNull(message = "转诊类型(referralApplyType)不可为空！")
    private Integer referralApplyType;

    /**
     * 病情摘要和处理情况
     */
    private String currentDesc;

    @NotBlank(message = "转诊对象手机号(mobileNo)不可为空！")
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getApplyHospitalId() {
        return applyHospitalId;
    }

    public void setApplyHospitalId(String applyHospitalId) {
        this.applyHospitalId = applyHospitalId;
    }

    public String getApplyHospitalName() {
        return applyHospitalName;
    }

    public void setApplyHospitalName(String applyHospitalName) {
        this.applyHospitalName = applyHospitalName;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public Integer getShowVisit() {
        return showVisit;
    }

    public void setShowVisit(Integer showVisit) {
        this.showVisit = showVisit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getReferralApplyType() {
        return referralApplyType;
    }

    public void setReferralApplyType(Integer referralApplyType) {
        this.referralApplyType = referralApplyType;
    }

    public String getCurrentDesc() {
        return currentDesc;
    }

    public void setCurrentDesc(String currentDesc) {
        this.currentDesc = currentDesc;
    }

    @Override
    public String toString() {
        return "AddReferralApplyDTO{" +
                "doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", applyDoctorId='" + applyDoctorId + '\'' +
                ", applyDoctorName='" + applyDoctorName + '\'' +
                ", applyHospitalId='" + applyHospitalId + '\'' +
                ", applyHospitalName='" + applyHospitalName + '\'' +
                ", applyDepartmentId='" + applyDepartmentId + '\'' +
                ", applyDepartmentName='" + applyDepartmentName + '\'' +
                ", applyDt='" + applyDt + '\'' +
                ", applyReason='" + applyReason + '\'' +
                ", memberInfo='" + memberInfo + '\'' +
                ", showVisit=" + showVisit +
                ", status=" + status +
                ", isValid=" + isValid +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", referralApplyType=" + referralApplyType +
                ", currentDesc='" + currentDesc + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
