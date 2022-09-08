package com.comvee.cdms.consultation.model.param;

public class ListRemoteConsultationMessageParam {

    private String consultationId;
    private Long newTimeStamp;
    private Long oldTimeStamp;
    private String orderSort;
    private Integer size;

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public Long getNewTimeStamp() {
        return newTimeStamp;
    }

    public void setNewTimeStamp(Long newTimeStamp) {
        this.newTimeStamp = newTimeStamp;
    }

    public Long getOldTimeStamp() {
        return oldTimeStamp;
    }

    public void setOldTimeStamp(Long oldTimeStamp) {
        this.oldTimeStamp = oldTimeStamp;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
