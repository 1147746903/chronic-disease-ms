package com.comvee.cdms.clinicaldiagnosis.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class ListYzRecordDTO implements Serializable {
    @NotEmpty(message = "患者编号不可为空")
    private String memberId;
    /**
     * 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他
     */
    private Integer yzItemType;

    /**
     * 医嘱类型 1 长期医嘱 2 临时医嘱
     */
    private Integer yzType;
    private String startDt;
    private String endDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getYzItemType() {
        return yzItemType;
    }

    public void setYzItemType(Integer yzItemType) {
        this.yzItemType = yzItemType;
    }

    public Integer getYzType() {
        return yzType;
    }

    public void setYzType(Integer yzType) {
        this.yzType = yzType;
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
}
