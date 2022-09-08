package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodSugarMonitorVO {
    private Integer countMonitorAll; //动态血糖监测总人数
    private Integer countMonitorHospital; //动态血糖监测住院人数
    private Integer countMonitorHome; //动态血糖监测门诊/居家人数
    private Integer countAbnormityAll; //达标时间占比异常总人数
    private Integer countAbnormityHospital; //达标时间占比异常住院人数
    private Integer countAbnormityHome; //达标时间占比异常门诊/居家人数

    public Integer getCountMonitorAll() {
        return countMonitorAll;
    }

    public void setCountMonitorAll(Integer countMonitorAll) {
        this.countMonitorAll = countMonitorAll;
    }

    public Integer getCountMonitorHospital() {
        return countMonitorHospital;
    }

    public void setCountMonitorHospital(Integer countMonitorHospital) {
        this.countMonitorHospital = countMonitorHospital;
    }

    public Integer getCountMonitorHome() {
        return countMonitorHome;
    }

    public void setCountMonitorHome(Integer countMonitorHome) {
        this.countMonitorHome = countMonitorHome;
    }

    public Integer getCountAbnormityAll() {
        return countAbnormityAll;
    }

    public void setCountAbnormityAll(Integer countAbnormityAll) {
        this.countAbnormityAll = countAbnormityAll;
    }

    public Integer getCountAbnormityHospital() {
        return countAbnormityHospital;
    }

    public void setCountAbnormityHospital(Integer countAbnormityHospital) {
        this.countAbnormityHospital = countAbnormityHospital;
    }

    public Integer getCountAbnormityHome() {
        return countAbnormityHome;
    }

    public void setCountAbnormityHome(Integer countAbnormityHome) {
        this.countAbnormityHome = countAbnormityHome;
    }

    @Override
    public String toString() {
        return "DynamicBloodSugarMonitorBO{" +
                "countMonitorAll=" + countMonitorAll +
                ", countMonitorHospital=" + countMonitorHospital +
                ", countMonitorHome=" + countMonitorHome +
                ", countAbnormityAll=" + countAbnormityAll +
                ", countAbnormityHospital=" + countAbnormityHospital +
                ", countAbnormityHome=" + countAbnormityHome +
                '}';
    }
}
