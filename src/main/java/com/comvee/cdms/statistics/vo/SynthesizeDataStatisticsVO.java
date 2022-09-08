package com.comvee.cdms.statistics.vo;

/**
 * @author wyc
 * @date 2019/4/16 17:28
 */
public class SynthesizeDataStatisticsVO {
    private String dateStr; //时间段
    private long countMember;  //新增患者人数
    private long countABI;  //新增检查ABI人数
    private long countVPT;   //新增检查VPT人数
    private long countPackage;  //套餐新开通次数
    private long countPrescription;  //新增处方数
    private long countFollow;  //新增随访数
    private long countBloodPeople;  //血糖监测人数
    private long countBlood;  //血糖监测次数



    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public long getCountMember() {
        return countMember;
    }

    public void setCountMember(long countMember) {
        this.countMember = countMember;
    }

    public long getCountABI() {
        return countABI;
    }

    public void setCountABI(long countABI) {
        this.countABI = countABI;
    }

    public long getCountVPT() {
        return countVPT;
    }

    public void setCountVPT(long countVPT) {
        this.countVPT = countVPT;
    }

    public long getCountPackage() {
        return countPackage;
    }

    public void setCountPackage(long countPackage) {
        this.countPackage = countPackage;
    }

    public long getCountPrescription() {
        return countPrescription;
    }

    public void setCountPrescription(long countPrescription) {
        this.countPrescription = countPrescription;
    }

    public long getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(long countFollow) {
        this.countFollow = countFollow;
    }

    public long getCountBloodPeople() {
        return countBloodPeople;
    }

    public void setCountBloodPeople(long countBloodPeople) {
        this.countBloodPeople = countBloodPeople;
    }

    public long getCountBlood() {
        return countBlood;
    }

    public void setCountBlood(long countBlood) {
        this.countBlood = countBlood;
    }

}
