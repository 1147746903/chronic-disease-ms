package com.comvee.cdms.packages.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_package_service
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class PackageServicePO {
    /**
     * sid
     */
    private String sid;

    /**
     * 服务名称
     * service_name
     */
    private String serviceName;

    /**
     * 服务 code
     * service_code
     */
    private String serviceCode;

    /**
     * 使用类型 1 ： 真实计数 2 ： 持续有效
     * use_type
     */
    private Integer useType;

    /**
     * 套餐id
     * package_id
     */
    private String packageId;

    /**
     * 服务使用次数
     * use_time
     */
    private Integer useTime;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.sid
     *
     * @return the value of t_package_service.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.sid
     *
     * @param sid the value for t_package_service.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.service_name
     *
     * @return the value of t_package_service.service_name
     *
     * @mbggenerated
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.service_name
     *
     * @param serviceName the value for t_package_service.service_name
     *
     * @mbggenerated
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.service_code
     *
     * @return the value of t_package_service.service_code
     *
     * @mbggenerated
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.service_code
     *
     * @param serviceCode the value for t_package_service.service_code
     *
     * @mbggenerated
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode == null ? null : serviceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.use_type
     *
     * @return the value of t_package_service.use_type
     *
     * @mbggenerated
     */
    public Integer getUseType() {
        return useType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.use_type
     *
     * @param useType the value for t_package_service.use_type
     *
     * @mbggenerated
     */
    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.package_id
     *
     * @return the value of t_package_service.package_id
     *
     * @mbggenerated
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.package_id
     *
     * @param packageId the value for t_package_service.package_id
     *
     * @mbggenerated
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.use_time
     *
     * @return the value of t_package_service.use_time
     *
     * @mbggenerated
     */
    public Integer getUseTime() {
        return useTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.use_time
     *
     * @param useTime the value for t_package_service.use_time
     *
     * @mbggenerated
     */
    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.insert_dt
     *
     * @return the value of t_package_service.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.insert_dt
     *
     * @param insertDt the value for t_package_service.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package_service.update_dt
     *
     * @return the value of t_package_service.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package_service.update_dt
     *
     * @param updateDt the value for t_package_service.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }
}