package com.comvee.cdms.follow.po;

/**
 * Created with IntelliJ IDEA.
 * User: xuds
 * Date: 2020/8/18
 * Time: 11:09
 * Description: 用药数据字典表
 **/
public class FollowDrugPO {

    private String drugId; // 药品ID
    private String drugName;  // 药品名
    private String pid;  // 父类ID
    private String drugType;  // 药品类型（1、降糖药 2、其他药品）

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }
}
