package com.comvee.cdms.statistics.vo;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public class NumberStatisticsVO implements Serializable {

    private static final long serialVersionUID = -7321217068420871396L;

    //管理处方数量
    private Long prescriptionNumber;
    //随访数量
    private Long followNumber;
    //订单总金额
    private Long orderTotalNumber;
    //患者总数
    private Long memberTotalNumber;
    //付费患者统计
    private Long payMemberNumber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(Long prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public Long getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(Long followNumber) {
        this.followNumber = followNumber;
    }

    public Long getOrderTotalNumber() {
        return orderTotalNumber;
    }

    public void setOrderTotalNumber(Long orderTotalNumber) {
        this.orderTotalNumber = orderTotalNumber;
    }

    public Long getMemberTotalNumber() {
        return memberTotalNumber;
    }

    public void setMemberTotalNumber(Long memberTotalNumber) {
        this.memberTotalNumber = memberTotalNumber;
    }

    public Long getPayMemberNumber() {
        return payMemberNumber;
    }

    public void setPayMemberNumber(Long payMemberNumber) {
        this.payMemberNumber = payMemberNumber;
    }
}
