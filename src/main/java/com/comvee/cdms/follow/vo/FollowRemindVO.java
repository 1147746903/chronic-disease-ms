package com.comvee.cdms.follow.vo;

/**
 * @author wyc
 * @date 2019/12/20 12:51
 */
public class FollowRemindVO {
    /**
     * 唯一标识
     */
    private String id;
    private String title; //随访任务详情
    /** 类型 1、首诊完成提醒 2、日常随访完成提醒  3、15天提醒 4、45天提醒 5、75天提醒
     6 、定期随访提醒 7、门诊随访提醒 8、电话随访提醒  9、自我行为问卷*/
    private String type;
    private String followId; //外键
    private String memberId; //患者id
    private String doctorId; //医生id
    private String memberName; //患者姓名
    private String isDo; //是否处理 1是 0否
    private String isValid; //是否有效 1有效 0无效
    private String insertDt; //添加时间
    private String modifyDt; //更新时间
    private String doctorName; //医生姓名
    private String beforeDay; //提前的天数
    private String followName; //随访名称
    private String templateId; //自定义随访模板id
    private String followStatus;   //填写状态
    private String followInsertDt; //随访下发时间
    private String fillFormBy;  //填写人 1 医生 2 患者
    /**
     * 是否住院
     */
    private Integer inHos;
    private String departmentId;
    private String nextDt;


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIsDo() {
        return isDo;
    }

    public void setIsDo(String isDo) {
        this.isDo = isDo;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(String beforeDay) {
        this.beforeDay = beforeDay;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getFollowInsertDt() {
        return followInsertDt;
    }

    public void setFollowInsertDt(String followInsertDt) {
        this.followInsertDt = followInsertDt;
    }

    public String getFillFormBy() {
        return fillFormBy;
    }

    public void setFillFormBy(String fillFormBy) {
        this.fillFormBy = fillFormBy;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }
}
