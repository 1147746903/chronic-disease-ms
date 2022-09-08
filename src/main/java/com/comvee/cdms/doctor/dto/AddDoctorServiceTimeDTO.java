package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_doctor_service_time_set
 *
 * @mbggenerated do_not_delete_during_merge
 * @author Su
 */
public class AddDoctorServiceTimeDTO {




    /**
     * 星期code
     * week_code
     */
    @NotEmpty
    private String weekCode;

    /**
     * 开始时间
     * start_time
     */
    @NotEmpty
    private String startTime;

    /**
     * 结束时间
     * end_time
     */
    @NotEmpty
    private String endTime;

    public String getWeekCode() {
        return weekCode;
    }

    public void setWeekCode(String weekCode) {
        this.weekCode = weekCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}