package com.comvee.cdms.follow.model;

import java.io.Serializable;

/**
 * 随访参数表
 * @author 林雨堆
 * @date 2018-03-27 10:30
 */
public class FollowDTO implements Serializable {
    private String sid;
    private String nextDt;
    private String followDt;
    private Integer followType;
    //随访类型 1默认 2华西随访
    private String fType;
    //华西分层分级内容
    private String levelJson;
    /**
     * 档案信息
     */
    private String archivesJson;

    /**
     * 胰岛素使用是否规范(1、是2、预混胰岛素使用前未正确摇匀3、胰岛素注射部位不正确及未更换注射部位4、胰岛素注射时未每次更换针头5、胰岛素保存方法不正确（储存温度及有效期）)',
     */
    private String rightToUseInsulin;

    /**
     * 是否用药 1:有 2: 无 默认无
     */
    private String hasDrug;
    /**
     * 用药列表
     */
    private String drugListJson;
    /**
     * 下次糖化检查时间
     */
    private String hbalcDate;
    /**
     * 目前主要问题
     */
    private String mqzywt;
    /**
     * 主要改进措施
     */
    private String zygjcs;
    /**
     * 预期达到目标
     */
    private String yqddmb;

    //状态 0 未完成 1已完成 2患者已填写(未完成）
    private Integer dealStatus;

    private String memberName;

    private String memberInfo;

    private String followInfo;

    private String drugInfo;

    /**
     * 填表人：1 医生 2 患者
     */
    private Integer fillFormBy;

    /**
     * 最终填表人
     */
    private Integer finalFillFormBy;

    /**
     * 下发人 医生编号
     */
    private String doctorId;

    /**
     * 下发人 领导编号
     */
    private String leaderId;

    /**
     * 入库时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 是否有效数据 1有效 0无效
     */
    private Integer isValid;

    /**
     * 随访内容外建
     */
    private String foreignId;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 随访教育者
     */
    private String doctorName;

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 套餐名称
     */
    private String packageName;
    /**
     * 购买日期
     */
    private String buyDt;

    /**
     * 联系方式
     */
    private String telephone;
    /**
     * 下发患者填写 1是  0否
     */
    private String pushMember;

    private String followName;  //自定义随访名称

    private String footJson;   //足有关Json

    private String addJson;  //新增问题json

    private String questionType;  //模板中问卷类型

    private String templateId;  //模板id

    private Integer beforeDay;  //提前的天数
    /**
     * 来源 1web 2his 3app 4小程序
     */
    private String origin = "1";

    private boolean saveDrug = false;

    private Integer followWay;  //2型糖尿病随访方式1 门诊 2 家庭 3 电话

    /**
     * 用药记录来源1 通用 2南京鼓楼
     */
    private Integer dType;

    /**
     * 高血压分层分级参数
     */
    private String gxyLevelDataJson;

    private String hospitalId;

    /**
     * 知识建议json
     */
    private String knowledgeListJson;

    /*
     * *  以下为新添加的随访信息需要的数据
     *    followInfo2  ， diseasesInfo  ，operationInfo ，otherDrug ，drinkingInfo
     * */
    /*
     *   followInfo2 随访信息JSON，存数据字典表
     * */
    private String followInfo2;

    /*
     *   diseasesInfo 其他疾病JSON信息:diseasesName(疾病名称)，diseasesTime(确诊时间），hospitalName（医院名称）
     * */
    private String diseasesInfo;

    /*
     *  operationInfo  手术信息JSON包含三个键值对；operativeSite(手术部位），operationName(手术名称），operationTime(手术时间）
     * */
    private String operationInfo;

    /*
     *  otherDrug   其他用药信息JSON:drugID,totaldose(每日总剂量），usage（用法用量），starttime（开始日期），
     * adjust（用药调整：是，否），adjustTime(最近用药调整日期），endtime(结束日期），note(备注）。
     * */
    private String otherDrug;

    /*
     *   drinkingInfo
     * 饮酒信息JSON:wineName(饮酒类型),degree(度数），quantity（饮酒量）,frequency(饮酒频率），alcohol（酒精）。*/
    private String drinkingInfo;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getGxyLevelDataJson() {
        return gxyLevelDataJson;
    }

    public void setGxyLevelDataJson(String gxyLevelDataJson) {
        this.gxyLevelDataJson = gxyLevelDataJson;
    }

    public Integer getFollowWay() {
        return followWay;
    }

    public void setFollowWay(Integer followWay) {
        this.followWay = followWay;
    }

    public boolean getSaveDrug() {
        return saveDrug;
    }

    public void setSaveDrug(boolean saveDrug) {
        this.saveDrug = saveDrug;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(Integer beforeDay) {
        this.beforeDay = beforeDay;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getFootJson() {
        return footJson;
    }

    public void setFootJson(String footJson) {
        this.footJson = footJson;
    }

    public String getAddJson() {
        return addJson;
    }

    public void setAddJson(String addJson) {
        this.addJson = addJson;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }

    public String getFollowDt() {
        return followDt;
    }

    public void setFollowDt(String followDt) {
        this.followDt = followDt;
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

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public Integer getFillFormBy() {
        return fillFormBy;
    }

    public void setFillFormBy(Integer fillFormBy) {
        this.fillFormBy = fillFormBy;
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

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getRightToUseInsulin() {
        return rightToUseInsulin;
    }

    public void setRightToUseInsulin(String rightToUseInsulin) {
        this.rightToUseInsulin = rightToUseInsulin;
    }

    public String getHasDrug() {
        return hasDrug;
    }

    public void setHasDrug(String hasDrug) {
        this.hasDrug = hasDrug;
    }

    public String getHbalcDate() {
        return hbalcDate;
    }

    public void setHbalcDate(String hbalcDate) {
        this.hbalcDate = hbalcDate;
    }

    public String getMqzywt() {
        return mqzywt;
    }

    public void setMqzywt(String mqzywt) {
        this.mqzywt = mqzywt;
    }

    public String getZygjcs() {
        return zygjcs;
    }

    public void setZygjcs(String zygjcs) {
        this.zygjcs = zygjcs;
    }

    public String getYqddmb() {
        return yqddmb;
    }

    public void setYqddmb(String yqddmb) {
        this.yqddmb = yqddmb;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public Integer getFinalFillFormBy() {
        return finalFillFormBy;
    }

    public void setFinalFillFormBy(Integer finalFillFormBy) {
        this.finalFillFormBy = finalFillFormBy;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getBuyDt() {
        return buyDt;
    }

    public void setBuyDt(String buyDt) {
        this.buyDt = buyDt;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDrugListJson() {
        return drugListJson;
    }

    public void setDrugListJson(String drugListJson) {
        this.drugListJson = drugListJson;
    }

    public String getPushMember() {
        return pushMember;
    }

    public void setPushMember(String pushMember) {
        this.pushMember = pushMember;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getLevelJson() {
        return levelJson;
    }

    public void setLevelJson(String levelJson) {
        this.levelJson = levelJson;
    }

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }

    public String getKnowledgeListJson() {
        return knowledgeListJson;
    }

    public void setKnowledgeListJson(String knowledgeListJson) {
        this.knowledgeListJson = knowledgeListJson;
    }

    public String getFollowInfo2() {
        return followInfo2;
    }

    public void setFollowInfo2(String followInfo2) {
        this.followInfo2 = followInfo2;
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

}
