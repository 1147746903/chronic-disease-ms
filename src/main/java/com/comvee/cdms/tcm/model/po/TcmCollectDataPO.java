package com.comvee.cdms.tcm.model.po;

public class TcmCollectDataPO {
    private String sid;
    private String taskId;//采集单主键
    private String memberId;//患者id
    private String memberName;//患者姓名
    private String mobilePhone;//手机号
    private String sex;//性别 1男 2女
    private String birthday;//出生日期 yyyy-MM-dd
    private String idCard;//身份证号
    private String visitNo;//就诊卡号
    private String maritalStatus;//婚姻状况
    private String profession;// 职业
    private String address;//常住地
    private String medicalHistory;//既往病史
    private Integer constitution;//中医体制 1：和平质:2：阴虚质、3：气郁质、4:湿热质、5:气虚质、6:阳虚质:7:痰湿质、8:血瘀质:9:特禀质
    private String height;//身高
    private String weight;//体重
    private String bmi;
    private String bmiMeasureDt;//bmi测量时间
    private Integer feature;//脸型 1:火行人、2：金行人、3：土行人、4：水行人、5：木行人
    private Integer complexion;//脸色 1：黑色、2：青色、3：红色、4：白色、5：黄色
    private Integer tongueBody;//舌质 1：适中、2：胖大、3：瘦薄、4：齿痕、5：裂纹、6：老、7：嫩、8：肿胀、9：点刺
    private Integer tongueColor;//舌色 1：黑舌、2：绛舌、3：暗舌、4：淡舌、5：紫舌、6：红舌、7：白舌、8：淡紫舌
    private Integer tongueFur;//苔质 1；薄、2：厚、3：腐、4：腻、5：燥、6：焦、7：积粉、8：滑、9：剥、10：类剥
    private Integer coatingColor;//苔色 1：黑苔、2：黑白相间苔、3：燥苔、4：染苔、5：灰黑苔、6：润苔、6：焦黄苔、7：厚苔、8：薄苔、9：白苔、10：黄苔、11：淡黄苔、12：黄白相间苔
    private Integer tongueCondition;//舌态 1：弄舌、2：舌痿、3；舌短、4：舌强、5：舌颤 6:吐舌 7:舌歪
    private String featureImage;//脸型图片
    private String tongueImage;//舌诊图片
    private String leftPulseImage;//左手脉象图
    private String rightPulseImage;//右手脉象图
    private String questionnaireJson;//问卷填写数据JSON
    private Integer currentStep;
    private String insertDt;
    private String updateDt;
    private Integer valid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public void setConstitution(Integer constitution) {
        this.constitution = constitution;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBmiMeasureDt() {
        return bmiMeasureDt;
    }

    public void setBmiMeasureDt(String bmiMeasureDt) {
        this.bmiMeasureDt = bmiMeasureDt;
    }

    public Integer getFeature() {
        return feature;
    }

    public void setFeature(Integer feature) {
        this.feature = feature;
    }

    public Integer getComplexion() {
        return complexion;
    }

    public void setComplexion(Integer complexion) {
        this.complexion = complexion;
    }

    public Integer getTongueBody() {
        return tongueBody;
    }

    public void setTongueBody(Integer tongueBody) {
        this.tongueBody = tongueBody;
    }

    public Integer getTongueColor() {
        return tongueColor;
    }

    public void setTongueColor(Integer tongueColor) {
        this.tongueColor = tongueColor;
    }

    public Integer getTongueFur() {
        return tongueFur;
    }

    public void setTongueFur(Integer tongueFur) {
        this.tongueFur = tongueFur;
    }

    public Integer getCoatingColor() {
        return coatingColor;
    }

    public void setCoatingColor(Integer coatingColor) {
        this.coatingColor = coatingColor;
    }

    public Integer getTongueCondition() {
        return tongueCondition;
    }

    public void setTongueCondition(Integer tongueCondition) {
        this.tongueCondition = tongueCondition;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public String getTongueImage() {
        return tongueImage;
    }

    public void setTongueImage(String tongueImage) {
        this.tongueImage = tongueImage;
    }

    public String getLeftPulseImage() {
        return leftPulseImage;
    }

    public void setLeftPulseImage(String leftPulseImage) {
        this.leftPulseImage = leftPulseImage;
    }

    public String getRightPulseImage() {
        return rightPulseImage;
    }

    public void setRightPulseImage(String rightPulseImage) {
        this.rightPulseImage = rightPulseImage;
    }

    public String getQuestionnaireJson() {
        return questionnaireJson;
    }

    public void setQuestionnaireJson(String questionnaireJson) {
        this.questionnaireJson = questionnaireJson;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
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

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }
}
