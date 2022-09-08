package com.comvee.cdms.statistics.dto;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GetStatisticsDTO implements Serializable {

    /**
     * 统计页面类型 1订单统计 2患者统计 3BMI分布 4血压达标率
     * 5血脂达标率 6HbA1c分布 7血糖分析 8并发症筛查 9药品使用
     * 10 BMI分布&HbA1c分布 11 血压达标率&出院血压达标率 12 虚拟病区统计
     */
    private String pageType = "1";

    /**
     * 1：当日 2：本周 3：三个月 4：半年 5：一年
     */
    private String dtCode;

    /**
     * 开始时间
     */
    private String startDt;

    /**
     * 结束时间
     */
    private String endDt;

    /**
     * 类型 1住院（默认） 2门诊&居家
     */
    private String visitType = "1";

    /**
     * 医院编号
     */
    private String hospitalId;

    /**
     * 年份
     */
    private String year;

    /**
     * 随访状态
     */
    private String followStatus;

    /**
     * 医护关系参数(可选)
     */
    private String memberId;
    private String doctorId;
    private String concernStatus;
    private String isAttend;
    private String groupId;
    private String payStatus;
    /**
     * 医护关系参数end
     */

    /**
     * 套餐状态
     */
    private Integer packageStatus;

    /**
     * 管理处方状态
     */
    private Integer schedule;

    /**
     * 患者性别
     */
    private Integer sex;

    /**
     * 血脂CODE
     */
    private String fatCode;
    /**
     * 并发症类型
     */
    private String modular;
    /**
     * 并发症结果状态
     */
    private int abnormalSign;

    private String birthdayMax;

    private String birthdayMin;
    /**
     * 科室编号串
     */
    private List<String> departIdList;

    /**
     * 入院 1 出院 0
     */
    private Integer inHos;
    /**
     * 检验项名称
     */
    private String fatName;
    /**
     * 糖尿病类型
     */
    private String diabetesType;
    /**
     * 大于等于几岁
     */
    private Integer gtEqAge;
    /**
     * 是否是糖尿病 1是 0否
     */
    private Integer isDiabetes;
    /**
     * 医护人员编号串
     */
    private List<String> doctorIdList;
    private boolean statistics;

    /**
     * 课题组id
     */
    private String researchGroupId;

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    private List<String> doctorList;

    public String getBirthdayMax() {
        return birthdayMax;
    }

    public void setBirthdayMax(String birthdayMax) {
        this.birthdayMax = birthdayMax;
    }

    public String getBirthdayMin() {
        return birthdayMin;
    }

    public void setBirthdayMin(String birthdayMin) {
        this.birthdayMin = birthdayMin;
    }

    public String getFatCode() {
        return fatCode;
    }

    public void setFatCode(String fatCode) {
        this.fatCode = fatCode;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
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

    public String getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(String concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(String isAttend) {
        this.isAttend = isAttend;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getYear() {
        if(StringUtils.isBlank(this.year)){
            if(!StringUtils.isBlank(this.endDt)){
                this.year = DateHelper.getDate(DateHelper.getDate(this.endDt,DateHelper.DAY_FORMAT),"yyyy");
            } else {
                this.year = DateHelper.getDate(new Date(),"yyyy");
            }
        }
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getDtCode() {
        return dtCode;
    }

    public void setDtCode(String dtCode) {
        this.dtCode = dtCode;
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

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getModular() {
        return modular;
    }

    public void setAbnormalSign(int abnormalSign) {
        this.abnormalSign = abnormalSign;
    }

    public int getAbnormalSign() {
        return abnormalSign;
    }

    public void setDepartIdList(List<String> departIdList) {
        this.departIdList = departIdList;
    }

    public List<String> getDepartIdList() {
        return departIdList;
    }

    public void setFatName(String fatName) {
        this.fatName = fatName;
    }

    public String getFatName() {
        return fatName;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setGtEqAge(Integer gtEqAge) {
        this.gtEqAge = gtEqAge;
    }

    public Integer getGtEqAge() {
        return gtEqAge;
    }

    public void setIsDiabetes(Integer isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public Integer getIsDiabetes() {
        return isDiabetes;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }

    public void setStatistics(boolean statistics) {
        this.statistics = statistics;
    }

    public boolean isStatistics() {
        return statistics;
    }

    public String getResearchGroupId() {
        return researchGroupId;
    }

    public void setResearchGroupId(String researchGroupId) {
        this.researchGroupId = researchGroupId;
    }
}
