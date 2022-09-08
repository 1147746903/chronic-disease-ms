package com.comvee.cdms.differentlevels.dto;

import java.io.Serializable;

/**
 * 获取分层分级记录列表的条件实体
 * @author 林雨堆
 * @date 2019-10-26
 */
public class ListDiffLevelsDTO implements Serializable {

    /**
     * 分层情况
     */
    private Integer layer;
    /**
     * 开始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String endDt;
    /**
     * 患者名称
     */
    private String memberName;
    /**
     * 患者名称拼音
     */
    private String memberNamePy;

    /**
     * 来源编号（医院，医生）
     */
    private String originId;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
    }
}
