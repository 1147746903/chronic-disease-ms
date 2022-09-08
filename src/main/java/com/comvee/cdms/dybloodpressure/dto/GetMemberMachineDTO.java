package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2021/11/9
 */
public class GetMemberMachineDTO {
    @NotBlank(message = "memberId不允许为空")
    private String memberId;
    private String machineNo;
    private String keyword;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
