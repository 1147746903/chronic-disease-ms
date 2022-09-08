package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public class ListBloodPressureDTO {

    private String memberId;
    private String startDt;
    private String endDt;

    private String codeDt;//1、今日  2、三天  3、七天 4、一个月（30天）
    private Integer recordLevel;//1正常0异常
    private String hospitalId;
    private List<String> hospitalIdList;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getCodeDt() {
        return codeDt;
    }

    public void setCodeDt(String codeDt) {
        this.codeDt = codeDt;
    }

    public Integer getRecordLevel() {
        return recordLevel;
    }

    public void setRecordLevel(Integer recordLevel) {
        this.recordLevel = recordLevel;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }
}
