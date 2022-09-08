package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PagerSensorOfShareDTO implements Serializable {

    private int page = 1;
    private int rows = 10;
    @NotEmpty(message = "患者编号（memberId）不可为空")
    private String memberId;
    @NotNull(message = "查询类型（shareType）不可为空(1:分享给我,2:我分享的)")
    private Integer shareType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }
}
