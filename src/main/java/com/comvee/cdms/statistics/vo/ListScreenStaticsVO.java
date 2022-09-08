package com.comvee.cdms.statistics.vo;

/**
 * @Author linr
 * @Date 2022/7/18
 */
public class ListScreenStaticsVO {

    private String memberId;
    private String memberName;
    private String birthday;
    private String sex;
    private String age;
    private String diabetesType;
    /**
     * 居委会
     */
    private String committeeName;
    /**
     * 血糖
     */
    private String sugarValue;
    /**
     * 血压
     */
    private String pressureValue;
    /**
     * 当前糖尿病分标
     */
    private String currentDiabetesLevel;
    /**
     * 建议糖尿病分标
     */
    private String suggestDiabetesLevel;

    /**
     * 当前高血压分级
     */
    private String currentHypLevel;
    /**
     * 当前高血压分层
     */
    private String currentHypLayer;
    /**
     * 建议高血压分级
     */
    private String suggestHypLevel;
    /**
     * 建议高血压分层
     */
    private String suggestHypLayer;


    private String sbpValueLevel;//收缩压等级1低3正常5高
    private String dbpValueLevel;//舒张压等级1低3正常5高
    private String sugarValueLevel;//血糖等级1低3正常5高

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getSugarValue() {
        return sugarValue;
    }

    public void setSugarValue(String sugarValue) {
        this.sugarValue = sugarValue;
    }

    public String getPressureValue() {
        return pressureValue;
    }

    public void setPressureValue(String pressureValue) {
        this.pressureValue = pressureValue;
    }

    public String getCurrentDiabetesLevel() {
        return currentDiabetesLevel;
    }

    public void setCurrentDiabetesLevel(String currentDiabetesLevel) {
        this.currentDiabetesLevel = currentDiabetesLevel;
    }

    public String getSuggestDiabetesLevel() {
        return suggestDiabetesLevel;
    }

    public void setSuggestDiabetesLevel(String suggestDiabetesLevel) {
        this.suggestDiabetesLevel = suggestDiabetesLevel;
    }

    public String getCurrentHypLevel() {
        return currentHypLevel;
    }

    public void setCurrentHypLevel(String currentHypLevel) {
        this.currentHypLevel = currentHypLevel;
    }

    public String getCurrentHypLayer() {
        return currentHypLayer;
    }

    public void setCurrentHypLayer(String currentHypLayer) {
        this.currentHypLayer = currentHypLayer;
    }

    public String getSuggestHypLevel() {
        return suggestHypLevel;
    }

    public void setSuggestHypLevel(String suggestHypLevel) {
        this.suggestHypLevel = suggestHypLevel;
    }

    public String getSuggestHypLayer() {
        return suggestHypLayer;
    }

    public void setSuggestHypLayer(String suggestHypLayer) {
        this.suggestHypLayer = suggestHypLayer;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSbpValueLevel() {
        return sbpValueLevel;
    }

    public void setSbpValueLevel(String sbpValueLevel) {
        this.sbpValueLevel = sbpValueLevel;
    }

    public String getDbpValueLevel() {
        return dbpValueLevel;
    }

    public void setDbpValueLevel(String dbpValueLevel) {
        this.dbpValueLevel = dbpValueLevel;
    }

    public String getSugarValueLevel() {
        return sugarValueLevel;
    }

    public void setSugarValueLevel(String sugarValueLevel) {
        this.sugarValueLevel = sugarValueLevel;
    }
}
