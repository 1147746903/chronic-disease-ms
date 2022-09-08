package com.comvee.cdms.member.vo;


import com.comvee.cdms.complication.model.bo.PatientSyncBO;

/**
 * @author: suyz
 * @date: 2019/6/26
 */
public class MemberSearchResultVO {

    private Integer resultStatus;
    private PatientSyncBO patient;

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public PatientSyncBO getPatient() {
        return patient;
    }

    public void setPatient(PatientSyncBO patient) {
        this.patient = patient;
    }
}
