package com.comvee.cdms.packages.vo;

/**
 * @author: suyz
 * @date: 2018/11/22
 */
public class MemberPackageServiceVO {

    private String serviceName;
    private String serviceTime;
    private String serviceCode;
    private Integer serviceType;
    private Integer remainTime;
    private Integer totalTime;
    private String remainTimeText;

    public String getRemainTimeText() {
        return remainTimeText;
    }

    public void setRemainTimeText(String remainTimeText) {
        this.remainTimeText = remainTimeText;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Integer remainTime) {
        this.remainTime = remainTime;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
