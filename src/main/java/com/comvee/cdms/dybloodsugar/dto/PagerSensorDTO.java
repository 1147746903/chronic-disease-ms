package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class PagerSensorDTO implements Serializable {

    private int page = 1;
    private int rows = 10;
    @NotEmpty(message = "患者编号（memberId）不可为空")
    private String memberId;
    /**
     * 来源 1 web 2 患者小程序
     */
    private Integer origin;

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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
