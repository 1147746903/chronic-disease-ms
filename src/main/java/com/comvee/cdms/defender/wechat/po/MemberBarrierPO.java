package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者关卡表(MemberBarrierPO)实体类
 *
 * @author makejava
 * @since 2021-12-03 10:32:48
 */
public class MemberBarrierPO implements Serializable {
    private static final long serialVersionUID = 380837457986806268L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 关卡类型 1试炼场2普通关卡
     */
    private Integer barrierType;
    /**
     * 1进行中2已完成3待解锁4重新闯关
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 分数
     */
    private String score;
    /**
     * 是否有效(1有效0无效)
     */
    private Integer isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private Date modifyDt;


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

    public Integer getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(Integer barrierType) {
        this.barrierType = barrierType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

}
