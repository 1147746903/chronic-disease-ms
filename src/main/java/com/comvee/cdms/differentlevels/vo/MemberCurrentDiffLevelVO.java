package com.comvee.cdms.differentlevels.vo;

import java.io.Serializable;

public class MemberCurrentDiffLevelVO implements Serializable {
    /**
     * 主键
     */
    private String sid;

    /**
     * 患者编号
     */
    private String memberId;

    /**
     * 改变日期
     */
    private String changeDate;

    /**
     * 分级 1 一级支持 2 二级支持 3 三级支持
     */
    private Integer level;

    /**
     * 分层 1 高危层 2 中危层 3 平稳层
     */
    private Integer layer;

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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }
}
