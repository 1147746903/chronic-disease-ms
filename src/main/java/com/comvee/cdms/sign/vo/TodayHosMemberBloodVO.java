package com.comvee.cdms.sign.vo;

/**
 * @author wyc
 * @date 2020/3/5 16:53
 */
public class TodayHosMemberBloodVO {

    /**
     * sid
     */
    private String sid;

    /**
     * 血糖code
     * param_code
     */
    private String paramCode;

    /**
     * 血糖值
     * param_value
     */
    private String paramValue;

    /**
     * 血糖等级
     * param_level
     */
    private Integer paramLevel;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;

    /**
     * 备注
     * remark
     */
    private String remark;

    /**
     * 来源
     * origin
     */
    private Integer origin;

    /**
     * member_id
     */
    private String memberId;

    /**
     * 操作者类型 1 用户 2 医护
     * operator_type
     */
    private Integer operatorType;

    /**
     * operator_id
     */
    private String operatorId;

    private String rangeMin;
    private String rangeMax;

    private String doctorName;

    private String memberName;

    private String bedNo;
    private String departmentId;
    private String departmentName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(String rangeMin) {
        this.rangeMin = rangeMin;
    }

    public String getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(String rangeMax) {
        this.rangeMax = rangeMax;
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

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
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

    @Override
    public String toString() {
        return "TodayHosMemberBloodVO{" +
                "sid='" + sid + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramLevel=" + paramLevel +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", remark='" + remark + '\'' +
                ", origin=" + origin +
                ", memberId='" + memberId + '\'' +
                ", operatorType=" + operatorType +
                ", operatorId='" + operatorId + '\'' +
                ", rangeMin='" + rangeMin + '\'' +
                ", rangeMax='" + rangeMax + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
