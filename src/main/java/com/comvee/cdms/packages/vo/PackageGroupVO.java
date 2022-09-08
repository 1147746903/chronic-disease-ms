package com.comvee.cdms.packages.vo;

public class PackageGroupVO {

    private String packageId;
    private String packageName;
    private long peopleNumber;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(long peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
