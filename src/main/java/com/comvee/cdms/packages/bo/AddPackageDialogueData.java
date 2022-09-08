package com.comvee.cdms.packages.bo;

/**
 * @author: suyz
 * @date: 2018/12/18
 */
public class AddPackageDialogueData {

    private String packageName;
    private String price;
    private String date;
    private String memberPackageId;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
