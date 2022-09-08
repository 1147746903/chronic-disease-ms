package com.comvee.cdms.prescription.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 李左河
 * @date 2018/8/2 9:32.
 */
public class PrescriptionDTO {

    @Override
    public String toString() {
        return "PrescriptionDTO{}";
    }

    /**
     * 管理处方id
     */
    @NotBlank(message = "管理处方id，不能为空", groups = {PrescriptionGetGroup.class, PrescriptionModifyGroup.class, PrescriptionIdGroup.class})
    private String prescriptionId;
    /**
     * 1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    @NotNull(message = "选择模块不能为空", groups = {PrescriptionGetGroup.class, PrescriptionGetIntelligentGroup.class, PrescriptionModifyGroup.class})
    private Integer type;

    /**
     * 智能推荐的基本json参数
     */
    @NotBlank(message = "智能推荐的基本json参数，不能为空", groups = {PrescriptionGetIntelligentGroup.class})
    private String baseJson;
    /**
     * 患者id
     */
    @NotBlank(message = "患者id不能为空", groups = {PrescriptionInsertGroup.class})
    private String memberId;
    /**
     * 处方信息
     */
    @NotBlank(message = "处方信息不能为空", groups = {PrescriptionInsertGroup.class})
    private String module;
    /**
     * 处方周期
     */
    private Integer eduCycle;
    /**
     * 糖尿病类型
     */
    @NotNull(message = "糖尿病类型不能为空", groups = {PrescriptionInsertGroup.class})
    private Integer eohType;
    /**
     * 患者基本信息
     */
    @NotBlank(message = "患者基本信息不能为空", groups = {PrescriptionInsertGroup.class})
    private String memberInfoJson;
    /**
     * 版本号
     */
    @NotNull(message = "版本号不能为空", groups = {PrescriptionInsertGroup.class})
    private Integer version;
    /**
     * 详情
     */
    @NotBlank(message = "详情不能为空", groups = {PrescriptionModifyGroup.class})
    private String detailJson;
    /**
     * 处方保存状态 1待制定 2保存草稿 3完成
     */
    @NotNull(message = "保存状态不能为空", groups = {PrescriptionModifyGroup.class})
    private Integer schedule;

    /**
     * 自定义食谱
     */
    private String customNutritionCateringItem;

    /**
     * 自定义食谱feipang
     */
    private String customNutritionCateringFatItem;

    /**
     * 知识建议json
     */
    private String knowledgeListJson;

    /**
     * 助理医生编号
     */
    private String doctorId;
    /**
     * 首席医生编号
     */
    private String teamId;
    /**
     * 食谱类型  1、自定义 2、修改处方食谱 3、预设
     */
    private String caloricType;

    private String nid;

    private String startMonitorDt; //开始监测时间
    private String endMonitorDt; //结束监测时间
    private Integer OperationType; //数据来源 1:管理处方 2:医生Web 3:医生H5
    private String monitorPlanTye; //监测方案类型
    private String hospitalId; //医院id
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBaseJson() {
        return baseJson;
    }

    public void setBaseJson(String baseJson) {
        this.baseJson = baseJson;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getEduCycle() {
        return eduCycle;
    }

    public void setEduCycle(Integer eduCycle) {
        this.eduCycle = eduCycle;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getMemberInfoJson() {
        return memberInfoJson;
    }

    public void setMemberInfoJson(String memberInfoJson) {
        this.memberInfoJson = memberInfoJson;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDetailJson() {
        return detailJson;
    }

    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public String getKnowledgeListJson() {
        return knowledgeListJson;
    }

    public void setKnowledgeListJson(String knowledgeListJson) {
        this.knowledgeListJson = knowledgeListJson;
    }

    public String getCustomNutritionCateringItem() {
        return customNutritionCateringItem;
    }

    public void setCustomNutritionCateringItem(String customNutritionCateringItem) {
        this.customNutritionCateringItem = customNutritionCateringItem;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getStartMonitorDt() {
        return startMonitorDt;
    }

    public void setStartMonitorDt(String startMonitorDt) {
        this.startMonitorDt = startMonitorDt;
    }

    public String getEndMonitorDt() {
        return endMonitorDt;
    }

    public void setEndMonitorDt(String endMonitorDt) {
        this.endMonitorDt = endMonitorDt;
    }

    public Integer getOperationType() {
        return OperationType;
    }

    public void setOperationType(Integer operationType) {
        OperationType = operationType;
    }

    public String getMonitorPlanTye() {
        return monitorPlanTye;
    }

    public void setMonitorPlanTye(String monitorPlanTye) {
        this.monitorPlanTye = monitorPlanTye;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCaloricType() {
        return caloricType;
    }

    public void setCaloricType(String caloricType) {
        this.caloricType = caloricType;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getCustomNutritionCateringFatItem() {
        return customNutritionCateringFatItem;
    }

    public void setCustomNutritionCateringFatItem(String customNutritionCateringFatItem) {
        this.customNutritionCateringFatItem = customNutritionCateringFatItem;
    }
}
