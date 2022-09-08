package com.comvee.cdms.packages.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_member_package_service
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddMemberPackageServiceMapperDTO {
    /**
     * sid
     */
    private String sid;

    /**
     * member_package_id
     */
    private String memberPackageId;

    /**
     * service_name
     */
    private String serviceName;

    /**
     * remain_time
     */
    private Integer remainTime;

    /**
     * 总次数
     * total_time
     */
    private Integer totalTime;

    /**
     * service_type
     */
    private Integer serviceType;

    private String serviceCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.sid
     *
     * @return the value of t_member_package_service.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.sid
     *
     * @param sid the value for t_member_package_service.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.member_package_id
     *
     * @return the value of t_member_package_service.member_package_id
     *
     * @mbggenerated
     */
    public String getMemberPackageId() {
        return memberPackageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.member_package_id
     *
     * @param memberPackageId the value for t_member_package_service.member_package_id
     *
     * @mbggenerated
     */
    public void setMemberPackageId(String memberPackageId) {
        this.memberPackageId = memberPackageId == null ? null : memberPackageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.service_name
     *
     * @return the value of t_member_package_service.service_name
     *
     * @mbggenerated
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.service_name
     *
     * @param serviceName the value for t_member_package_service.service_name
     *
     * @mbggenerated
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.remain_time
     *
     * @return the value of t_member_package_service.remain_time
     *
     * @mbggenerated
     */
    public Integer getRemainTime() {
        return remainTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.remain_time
     *
     * @param remainTime the value for t_member_package_service.remain_time
     *
     * @mbggenerated
     */
    public void setRemainTime(Integer remainTime) {
        this.remainTime = remainTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.total_time
     *
     * @return the value of t_member_package_service.total_time
     *
     * @mbggenerated
     */
    public Integer getTotalTime() {
        return totalTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.total_time
     *
     * @param totalTime the value for t_member_package_service.total_time
     *
     * @mbggenerated
     */
    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_package_service.service_type
     *
     * @return the value of t_member_package_service.service_type
     *
     * @mbggenerated
     */
    public Integer getServiceType() {
        return serviceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_package_service.service_type
     *
     * @param serviceType the value for t_member_package_service.service_type
     *
     * @mbggenerated
     */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}