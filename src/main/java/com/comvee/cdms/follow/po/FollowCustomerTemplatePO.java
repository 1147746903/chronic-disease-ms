package com.comvee.cdms.follow.po;

/**
 * @author wyc
 * @date 2019/5/14 9:05
 */
public class FollowCustomerTemplatePO {
    private String sid;

    private String doctorId; //医生id

    private String creatorId; //创建者id

    private String insertDt;  //插入时间

    private String updateDt;  //更新时间

    private Integer permission;  //权限 1:全院 2:个人

    private String followName;  //随访名称

    private Integer isValid;   //是否有效 0:否 1:是

    private String hospitalId;  //医院id

    private String archivesJson;   //档案内容Json

    private String drugJson;  //用药情况Json

    private String footJson; //足有关Json

    private String  questionType; //问卷类型

    private String addJson;  //新增问题json

    private String creatorName; //创建人姓名
    private String doctorName;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAddJson() {
        return addJson;
    }

    public void setAddJson(String addJson) {
        this.addJson = addJson;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getDrugJson() {
        return drugJson;
    }

    public void setDrugJson(String drugJson) {
        this.drugJson = drugJson;
    }

    public String getFootJson() {
        return footJson;
    }

    public void setFootJson(String footJson) {
        this.footJson = footJson;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    @Override
    public String toString() {
        return "FollowCustomerTemplatePO{" +
                "sid='" + sid + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", permission=" + permission +
                ", followName='" + followName + '\'' +
                ", isValid=" + isValid +
                ", hospitalId='" + hospitalId + '\'' +
                ", archivesJson='" + archivesJson + '\'' +
                ", drugJson='" + drugJson + '\'' +
                ", footJson='" + footJson + '\'' +
                ", questionType='" + questionType + '\'' +
                ", addJson='" + addJson + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                '}';
    }
}
