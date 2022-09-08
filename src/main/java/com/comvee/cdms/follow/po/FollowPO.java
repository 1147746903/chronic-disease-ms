package com.comvee.cdms.follow.po;

import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeWeek;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public class FollowPO {
	/**
	 * 唯一标识
	 */
    private String sid;
    /**
     * 下次随访时间
     */
    private String nextDt;
    /**
     * 随访时间
     */
    private String followDt;
    /**
     * 随访类型：1首诊  2日常随访 3自我行为问卷 4糖尿病足随访表 5糖尿病随访表
     */
    private Integer followType;
    /**
     * 随访对象-患者编号
     */
    private String memberId;
    /**
     * 工作室首席（领导）编号
     */
    private String leaderId;
    /**
     * 医生编号
     */
    private String doctorId;

    private String insertDt;

    private String modifyDt;

    private Integer isValid;
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

    private String doctorName;

    private Integer dealStatus;

    private String memberName;

    private String levelJson;
    /**
     * 知识
     */
    List<PrescriptionKnowledgePO> knowledgeList;

    /**
     * 知识周
     */
    List<KnowledgeWeek> knowledgeWeekList;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
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

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getDrugListJson() {
        return drugListJson;
    }

    public void setDrugListJson(String drugListJson) {
        this.drugListJson = drugListJson;
    }

    public String getLevelJson() {
        return levelJson;
    }

    public void setLevelJson(String levelJson) {
        this.levelJson = levelJson;
    }

    public List<PrescriptionKnowledgePO> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<PrescriptionKnowledgePO> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public List<KnowledgeWeek> getKnowledgeWeekList() {
        return knowledgeWeekList;
    }

    public void setKnowledgeWeekList(List<KnowledgeWeek> knowledgeWeekList) {
        this.knowledgeWeekList = knowledgeWeekList;
    }
}