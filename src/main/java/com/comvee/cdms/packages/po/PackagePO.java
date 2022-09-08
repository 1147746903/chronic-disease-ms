package com.comvee.cdms.packages.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_package
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class PackagePO {
    /**
     * package_id
     */
    private String packageId;

    /**
     * 套餐名称
     * package_name
     */
    private String packageName;

    /**
     * 价格（分）
     * price
     */
    private String price;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 时间单位 Y:年 M:月 D:天
     * time_unit
     */
    private String timeUnit;

    /**
     * 时间长度
     * time_num
     */
    private Integer timeNum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.package_id
     *
     * @return the value of t_package.package_id
     *
     * @mbggenerated
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.package_id
     *
     * @param packageId the value for t_package.package_id
     *
     * @mbggenerated
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.package_name
     *
     * @return the value of t_package.package_name
     *
     * @mbggenerated
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.package_name
     *
     * @param packageName the value for t_package.package_name
     *
     * @mbggenerated
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.price
     *
     * @return the value of t_package.price
     *
     * @mbggenerated
     */
    public String getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.price
     *
     * @param price the value for t_package.price
     *
     * @mbggenerated
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.insert_dt
     *
     * @return the value of t_package.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.insert_dt
     *
     * @param insertDt the value for t_package.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.update_dt
     *
     * @return the value of t_package.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.update_dt
     *
     * @param updateDt the value for t_package.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.time_unit
     *
     * @return the value of t_package.time_unit
     *
     * @mbggenerated
     */
    public String getTimeUnit() {
        return timeUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.time_unit
     *
     * @param timeUnit the value for t_package.time_unit
     *
     * @mbggenerated
     */
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit == null ? null : timeUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_package.time_num
     *
     * @return the value of t_package.time_num
     *
     * @mbggenerated
     */
    public Integer getTimeNum() {
        return timeNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_package.time_num
     *
     * @param timeNum the value for t_package.time_num
     *
     * @mbggenerated
     */
    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }
}