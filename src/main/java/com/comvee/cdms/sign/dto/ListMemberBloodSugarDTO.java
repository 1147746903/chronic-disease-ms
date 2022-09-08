package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public class ListMemberBloodSugarDTO {

    @NotEmpty(message = "患者编号不能为空")
    private String memberId;
    private String startDt;
    private String endDt;

    private String paramCode;//血糖code

    private String codeDt;//1、今日  2、三天  3、七天 4、一个月（30天）

    private Integer inHos;

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

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

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getCodeDt() {
        return codeDt;
    }

    public void setCodeDt(String codeDt) {
        this.codeDt = codeDt;
    }
}
