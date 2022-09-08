package com.comvee.cdms.packages.bo;

/**
 * @author: suyz
 * @date: 2018/12/17
 */
public class ExpirePackageRemindDialogueData {

    private String packageName;
    private String packageId;
    private String price;
    private String date;
    private String memberPackageId;
    private String endDt;

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getMemberPackageId() {
        return memberPackageId;
    }

    public void setMemberPackageId(String memberPackageId) {
        this.memberPackageId = memberPackageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
