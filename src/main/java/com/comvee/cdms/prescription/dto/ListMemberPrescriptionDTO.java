package com.comvee.cdms.prescription.dto;

/**
 * @author: suyz
 * @date: 2018/11/9
 */
public class ListMemberPrescriptionDTO {

    private String memberId;
    private Integer schedule;
    private Integer handDown;
    private String  moduleString;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getHandDown() {
        return handDown;
    }

    public void setHandDown(Integer handDown) {
        this.handDown = handDown;
    }

    public String getModuleString() {
        return moduleString;
    }

    public void setModuleString(String moduleString) {
        this.moduleString = moduleString;
    }
}
