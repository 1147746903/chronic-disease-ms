package com.comvee.cdms.dybloodsugar.dto;

import java.util.List;

public class DyMemberSensorDTO {
    private String memberId;
    private String sensorNo;
    private Integer valid;
    private List<Integer> runStatusList;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public List<Integer> getRunStatusList() {
        return runStatusList;
    }

    public void setRunStatusList(List<Integer> runStatusList) {
        this.runStatusList = runStatusList;
    }
}
