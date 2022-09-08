package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author linr
 * @Date 2021/10/18
 */
public class GetDynBloodPressureDataDTO {
    @NotBlank(message = "memberId不允许为空")
    private String memberId;
    @NotBlank(message = "startDt不允许为空")
    private String startDt;
    @NotBlank(message = "endDt不允许为空")
    private String endDt;

    private List<Integer> typeList;//timeType list

    private Integer isValid; //1有效2无效


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

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
