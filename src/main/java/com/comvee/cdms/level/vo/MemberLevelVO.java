package com.comvee.cdms.level.vo;

/**
 * @author wyc
 * @date 2019/11/19 20:57
 */
public class MemberLevelVO {
    /**
     * 主键
     */
    private String sid;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 患者名称
     */
    private String memberName;

    /**
     * 患者名称拼音
     */
    private String memberNamePy;

    /**
     * 性别 1:男 2:女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 改变日期
     */
    private String changeDate;

    /**
     * 改变日期时间
     */
    private String changeDt;

    /**
     * 分级 1 一级支持 2 二级支持 3 三级支持 4其他
     */
    private Integer memberLevel;

    /**
     * 分层 1 高危 2 中危 3 低危
     */
    private Integer memberLayer;

    /**
     * 是否有效 0:否 1:是
     */
    private Integer isValid;

    /**
     * 入库时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 来源 1系统 2医生手动调整
     */
    private Integer origin;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 是否确认 0 未确认 1已确认
     */
    private Integer confirm;
    private String confirmDt;

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

    /**
     * 患者上次分级
     */
    private Integer lastMemberLevel;

    /**
     * 患者上次分层
     */
    private Integer lastMemberLayer;

    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 是否住院 1是0否
     */
    private Integer inHos;

    private String departmentId;

    private String diabetesType;//疾病类型
    private String changeReason;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLastMemberLevel() {
        return lastMemberLevel;
    }

    public void setLastMemberLevel(Integer lastMemberLevel) {
        this.lastMemberLevel = lastMemberLevel;
    }

    public Integer getLastMemberLayer() {
        return lastMemberLayer;
    }

    public void setLastMemberLayer(Integer lastMemberLayer) {
        this.lastMemberLayer = lastMemberLayer;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeDt() {
        return changeDt;
    }

    public void setChangeDt(String changeDt) {
        this.changeDt = changeDt;
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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public Integer getInHos() {
        return inHos;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(String confirmDt) {
        this.confirmDt = confirmDt;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}
