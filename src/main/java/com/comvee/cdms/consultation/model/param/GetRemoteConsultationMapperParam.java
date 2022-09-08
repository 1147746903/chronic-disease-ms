package com.comvee.cdms.consultation.model.param;

import java.util.List;

public class GetRemoteConsultationMapperParam {

    private String sid;
    private String memberId;
    private List<Integer> consultationStatusList;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<Integer> getConsultationStatusList() {
        return consultationStatusList;
    }

    public void setConsultationStatusList(List<Integer> consultationStatusList) {
        this.consultationStatusList = consultationStatusList;
    }
}
