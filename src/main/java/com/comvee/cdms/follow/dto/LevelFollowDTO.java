package com.comvee.cdms.follow.dto;

/** gxyLevelDataJson 转换用
 * @author wyc
 * @date 2019/11/19 20:57
 */
public class LevelFollowDTO {

    /**
     * 患者名称
     */
    private String memberName;


    /**
     * 性别 1:男 2:女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 分级 1 一级支持 2 二级支持 3 三级支持 4其他
     */
    private Integer memberLevel;

    /**
     * 分层 1 高危 2 中危 3 低危
     */
    private Integer memberLayer;

    /**
     * 来源 1系统 2医生手动调整
     */
    private Integer origin;

    /**
     * 是否确认 0 未确认 1已确认
     */
    private Integer confirm;

    /**
     * 分级类型 1 高血压
     */
    private Integer levelType;

    /**
     * 数据Json
     */
    private String dataJson;

    /**
     * 对比分析描述/调整原因
     */
    private String contrastAnalyze;

    /**
     * 是否调整 0:否 1:是
     */
    private Integer adjustment;
    private String changeReason;


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getMemberLayer() {
        return memberLayer;
    }

    public void setMemberLayer(Integer memberLayer) {
        this.memberLayer = memberLayer;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public String getContrastAnalyze() {
        return contrastAnalyze;
    }

    public void setContrastAnalyze(String contrastAnalyze) {
        this.contrastAnalyze = contrastAnalyze;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}
