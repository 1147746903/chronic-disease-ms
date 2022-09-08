package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author wyc
 * @date 2019/12/20 13:41
 */
public class ListMemberHbalcDTO {

    @NotEmpty
    private String memberId;
    private String startDt;
    private String endDt;

    private String codeDt;//1、今日  2、三天  3、七天 4、一个月（30天） 5,三个月 6 一年

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getCodeDt() {
        return codeDt;
    }

    public void setCodeDt(String codeDt) {
        this.codeDt = codeDt;
    }
}
