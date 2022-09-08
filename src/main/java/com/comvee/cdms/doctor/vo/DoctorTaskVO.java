package com.comvee.cdms.doctor.vo;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/11
 */
public class DoctorTaskVO implements Serializable{

    private Integer prescriptionNum;
    private Integer followNum;
    private Integer addNum;//添加申请
    private Integer msgNum;//消息未读
    private Long abnormalNum;
    private Long packageNum;//订单数量
    private Long orderNum;//套餐数量
    private Integer dlcNum;//分层分级改变数量
    private Long levelNum;  //分层分级提醒次数(高血压)
    /**
     * 虚拟病区转入申请人数
     */
    private Long virtualWardApplyIntoNum;

    public Integer getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(Integer prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public Integer getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }

    public Long getAbnormalNum() {
        return abnormalNum;
    }

    public void setAbnormalNum(Long abnormalNum) {
        this.abnormalNum = abnormalNum;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDlcNum() {
        return dlcNum;
    }

    public void setDlcNum(Integer dlcNum) {
        this.dlcNum = dlcNum;
    }

    public Long getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Long levelNum) {
        this.levelNum = levelNum;
    }

    public Long getVirtualWardApplyIntoNum() {
        return virtualWardApplyIntoNum;
    }

    public void setVirtualWardApplyIntoNum(Long virtualWardApplyIntoNum) {
        this.virtualWardApplyIntoNum = virtualWardApplyIntoNum;
    }

    public Long getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Long packageNum) {
        this.packageNum = packageNum;
    }
}
