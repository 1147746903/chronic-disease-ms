package com.comvee.cdms.follow.po;

/**
 * Created with IntelliJ IDEA.
 * User: xuds
 * Date: 2020/8/18
 * Time: 10:53
 * Description: No Description
 **/
public class FollowMmcVisitPO {

    private String sid; // 主键ID
    private String memberInfo; // 患者信息JSON
    private String followInfo; // 随访信息JSON
    private String drugInfo;   // 用药信息JSON
    private String diseasesInfo;  // 疾病信息JSON
    private String operationInfo; // 手术信息JSON
    private String otherDrug;      // 其他用药信息JSON
    private String drinkingInfo;  // 喝酒信息JSON
    private String isValid;    // 是否有效 1有效 0无效
    private String insertDt;   // 添加时间
    private String modifyDt;   // 更新时间
    private String memberId;   // 患者ID
    private String followType;  // 随访类型（15、糖尿病病人问卷调查；16、三个月随访；17、半年随访）
    private Integer dealStatus; //处理状态 0 未完成 1已完成 3患者已填写(未完成）

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getDiseasesInfo() {
        return diseasesInfo;
    }

    public void setDiseasesInfo(String diseasesInfo) {
        this.diseasesInfo = diseasesInfo;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOtherDrug() {
        return otherDrug;
    }

    public void setOtherDrug(String otherDrug) {
        this.otherDrug = otherDrug;
    }

    public String getDrinkingInfo() {
        return drinkingInfo;
    }

    public void setDrinkingInfo(String drinkingInfo) {
        this.drinkingInfo = drinkingInfo;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }
}
