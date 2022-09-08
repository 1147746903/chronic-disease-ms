package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/7 9:20.
 */
public class PrescriptionBO {
    @Override
    public String toString() {
        return "PrescriptionBO{}";
    }

    /**
     * 处方主表编号
     */
    private String prescriptionId;
    /**
     * 管理处方模块：1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    private Integer type;
    /**
     * 详情
     */
    private String detailJson;
    /**
     * 处方保存状态 1待制定 2保存草稿 3完成
     */
    private Integer schedule;
    /**
     * 管理处方患者信息
     */
    private String memberInfoJson;

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

    public String getMemberInfoJson() {
        return memberInfoJson;
    }

    public void setMemberInfoJson(String memberInfoJson) {
        this.memberInfoJson = memberInfoJson;
    }
}
