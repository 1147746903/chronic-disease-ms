package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author linr
 * @Date 2022/3/17
 */
public class BatchAddCheckoutDTO {

    @NotBlank(message = "患者编号不可为空")
    private String memberId;
    @NotBlank(message = "就诊号不可为空，无填'-1'")
    private String visitNo;
    private String yzId;
    @NotNull(message = "来源不可为空")
    private Integer recordOrigin;//4处方5随访6手动录入
    @NotBlank(message = "listStr不允许为空")
    private String listStr;
    private List<BaseAddCheckoutDTO> list;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getYzId() {
        return yzId;
    }

    public void setYzId(String yzId) {
        this.yzId = yzId;
    }

    public Integer getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(Integer recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public String getListStr() {
        return listStr;
    }

    public void setListStr(String listStr) {
        this.listStr = listStr;
    }

    public List<BaseAddCheckoutDTO> getList() {
        return list;
    }

    public void setList(List<BaseAddCheckoutDTO> list) {
        this.list = list;
    }
}
