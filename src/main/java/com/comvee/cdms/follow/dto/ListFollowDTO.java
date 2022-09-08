package com.comvee.cdms.follow.dto;

/**
 * @author wyc
 * @date 2019/12/20 13:20
 */
public class ListFollowDTO {

    private Integer dealStatus;

    private Integer fillFormBy; //填写人 1 医生 2 患者

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Integer getFillFormBy() {
        return fillFormBy;
    }

    public void setFillFormBy(Integer fillFormBy) {
        this.fillFormBy = fillFormBy;
    }
}
