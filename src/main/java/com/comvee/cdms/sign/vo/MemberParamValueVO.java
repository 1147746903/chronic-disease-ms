package com.comvee.cdms.sign.vo;

import com.comvee.cdms.member.bo.RangeBO;

/**
 * @author wyc
 * @date 2020/3/5 17:10
 */
public class MemberParamValueVO {

    private String sid;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 血糖值JSON
     */
    private String paramValueJson;
    private Double highValue;
    private Double lowValue;
    private Double highRate;
    private Double lowRate;
    private Integer treeTimesHigh;
    private Double standardValue;
    private String paramDt;
    private Integer paramLevel;
    private Integer paramType;
    private String memberName;
    private String bedNo;  //床号
    private String hospitalNo;  //住院号
    private String inHospitalDate;  //入院时间
    private Double abnomalRate; //今日血糖占比

    /**
     * 统计数量存放变量
     */
    private Integer num;

    private Integer allTestNum;
    private Integer totalTestNum;
    private String departmentId;

    private Double highBloodSugarValue; //最高血糖测量
    private Double lowBloodSugarValue; //最低血糖测量

    private String highValueDt; //最高血糖测量时间
    private String lowValueDt; //最低血糖测量时间

    private String insertDt;

    private RangeBO rangeBO; //控制目标对象

    private String paramHighCode; //高血糖code
    private String paramLowCode; //低血糖code
    private String hospitalId;

    private Integer highLevel;
    private Integer lowLevel;

    public Integer getHighLevel() {
        return highLevel;
    }

    public void setHighLevel(Integer highLevel) {
        this.highLevel = highLevel;
    }

    public Integer getLowLevel() {
        return lowLevel;
    }

    public void setLowLevel(Integer lowLevel) {
        this.lowLevel = lowLevel;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public String getParamValueJson() {
        return paramValueJson;
    }

    public void setParamValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getHighRate() {
        return highRate;
    }

    public void setHighRate(Double highRate) {
        this.highRate = highRate;
    }

    public Double getLowRate() {
        return lowRate;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Integer getTreeTimesHigh() {
        return treeTimesHigh;
    }

    public void setTreeTimesHigh(Integer treeTimesHigh) {
        this.treeTimesHigh = treeTimesHigh;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
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

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public Double getAbnomalRate() {
        return abnomalRate;
    }

    public void setAbnomalRate(Double abnomalRate) {
        this.abnomalRate = abnomalRate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAllTestNum() {
        return allTestNum;
    }

    public void setAllTestNum(Integer allTestNum) {
        this.allTestNum = allTestNum;
    }

    public Integer getTotalTestNum() {
        return totalTestNum;
    }

    public void setTotalTestNum(Integer totalTestNum) {
        this.totalTestNum = totalTestNum;
    }

    public String getHighValueDt() {
        return highValueDt;
    }

    public void setHighValueDt(String highValueDt) {
        this.highValueDt = highValueDt;
    }

    public String getLowValueDt() {
        return lowValueDt;
    }

    public void setLowValueDt(String lowValueDt) {
        this.lowValueDt = lowValueDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Double getHighBloodSugarValue() {
        return highBloodSugarValue;
    }

    public void setHighBloodSugarValue(Double highBloodSugarValue) {
        this.highBloodSugarValue = highBloodSugarValue;
    }

    public Double getLowBloodSugarValue() {
        return lowBloodSugarValue;
    }

    public void setLowBloodSugarValue(Double lowBloodSugarValue) {
        this.lowBloodSugarValue = lowBloodSugarValue;
    }

    public RangeBO getRangeBO() {
        return rangeBO;
    }

    public void setRangeBO(RangeBO rangeBO) {
        this.rangeBO = rangeBO;
    }

    public String getParamHighCode() {
        return paramHighCode;
    }

    public void setParamHighCode(String paramHighCode) {
        this.paramHighCode = paramHighCode;
    }

    public String getParamLowCode() {
        return paramLowCode;
    }

    public void setParamLowCode(String paramLowCode) {
        this.paramLowCode = paramLowCode;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "MemberParamValueVO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", paramValueJson='" + paramValueJson + '\'' +
                ", highValue=" + highValue +
                ", lowValue=" + lowValue +
                ", highRate=" + highRate +
                ", lowRate=" + lowRate +
                ", treeTimesHigh=" + treeTimesHigh +
                ", standardValue=" + standardValue +
                ", paramDt='" + paramDt + '\'' +
                ", paramLevel=" + paramLevel +
                ", paramType=" + paramType +
                ", memberName='" + memberName + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", inHospitalDate='" + inHospitalDate + '\'' +
                ", abnomalRate=" + abnomalRate +
                ", num=" + num +
                ", allTestNum=" + allTestNum +
                ", totalTestNum=" + totalTestNum +
                ", departmentId='" + departmentId + '\'' +
                ", highBloodSugarValue=" + highBloodSugarValue +
                ", lowBloodSugarValue=" + lowBloodSugarValue +
                ", highValueDt='" + highValueDt + '\'' +
                ", lowValueDt='" + lowValueDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", rangeBO=" + rangeBO +
                ", paramHighCode='" + paramHighCode + '\'' +
                ", paramLowCode='" + paramLowCode + '\'' +
                '}';
    }
}
