package com.comvee.cdms.follow.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/** 智能推荐参数
 * @author wyc
 * @date 2019/10/11 15:12
 */
public class OutFollowDTO {
    /**
     * 随访信息
     */
    @NotEmpty(message = "followBody不可为空")
    private String followBody;
    /**
     * 患者id
     */
    @NotEmpty(message = "患者编号不可为空")
    private String memberId;

    @NotNull(message = "type不可为空")
    private Integer type;
    /**
     * 来源类型 日常随访智能推荐的时候需要传 1:普通 2:华西
     */
    private Integer comeType;
    /**
     * 性别 1男2女
     */
    private Integer sex;
    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     */
    private String chd;
    /**
     * 糖尿病类型：1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)
     */
    private String diabetesType;
    /**
     * 高血压:1有、2无
     */
    private String essentialHyp;
    /**
     * 生日
     */
    private String birthday;

    private String doctorId;

    /**
     * 糖尿病:1有、2无
     */
    private String isDiabetes;

    private String height;


    public String getFollowBody() {
        return followBody;
    }

    public void setFollowBody(String followBody) {
        this.followBody = followBody;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getComeType() {
        return comeType;
    }

    public void setComeType(Integer comeType) {
        this.comeType = comeType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
