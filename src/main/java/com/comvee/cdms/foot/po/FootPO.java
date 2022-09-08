package com.comvee.cdms.foot.po;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class FootPO {

    private String followId; // 随访主键
    private String memberId; // 患者id
    private String teamId; // 团队id
    private String doctorId; // 医生id
    private String doctorName; // 随访医生
    private String footType; // 足部类型 1默认 2深圳足部
    private String saveType; // 保存状态(0草稿,1完成)
    private String recordDt; // 随诊时间
    private String nextFollowTime; // 下次随诊时间
    private String assessItem; // 评估项目
    private String assistCheck; // 辅助检查
    private String assessResult; // 评估结果
    private String relationReport; // 关联报告
    private String insertDt; //
    private String modifyDt; // 修改时间
    private String isValid; // 是否有效
    //是否关联报告 1 有 0 没有
    private Integer hasRelation;
    private String qrCodeData;
    private String qrCodeInvalidDt;

    private String idCard;

    private Integer abiRelate;
    private Integer vptRelate;
    private Integer eyesRelate;

    private String relateReportIdStr;

    private String createDoctorName;


    public String getCreateDoctorName() {
        return createDoctorName;
    }

    public void setCreateDoctorName(String createDoctorName) {
        this.createDoctorName = createDoctorName;
    }

    public String getRelateReportIdStr() {
        return relateReportIdStr;
    }

    public void setRelateReportIdStr(String relateReportIdStr) {
        this.relateReportIdStr = relateReportIdStr;
    }

    public Integer getAbiRelate() {
        return abiRelate;
    }

    public void setAbiRelate(Integer abiRelate) {
        this.abiRelate = abiRelate;
    }

    public Integer getVptRelate() {
        return vptRelate;
    }

    public void setVptRelate(Integer vptRelate) {
        this.vptRelate = vptRelate;
    }

    public Integer getEyesRelate() {
        return eyesRelate;
    }

    public void setEyesRelate(Integer eyesRelate) {
        this.eyesRelate = eyesRelate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getHasRelation() {
        return hasRelation;
    }

    public void setHasRelation(Integer hasRelation) {
        this.hasRelation = hasRelation;
    }

    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public String getQrCodeInvalidDt() {
        return qrCodeInvalidDt;
    }

    public void setQrCodeInvalidDt(String qrCodeInvalidDt) {
        this.qrCodeInvalidDt = qrCodeInvalidDt;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getNextFollowTime() {
        return nextFollowTime;
    }

    public void setNextFollowTime(String nextFollowTime) {
        this.nextFollowTime = nextFollowTime;
    }

    public String getAssessItem() {
        return assessItem;
    }

    public void setAssessItem(String assessItem) {
        this.assessItem = assessItem;
    }

    public String getAssistCheck() {
        return assistCheck;
    }

    public void setAssistCheck(String assistCheck) {
        this.assistCheck = assistCheck;
    }

    public String getAssessResult() {
        return assessResult;
    }

    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }

    public String getRelationReport() {
        return relationReport;
    }

    public void setRelationReport(String relationReport) {
        this.relationReport = relationReport;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getFootType() {
        return footType;
    }

    public void setFootType(String footType) {
        this.footType = footType;
    }
}