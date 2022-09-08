package com.comvee.cdms.statistics.bo;

/**
 * @Author linr
 * @Date 2022/2/21
 */
public class MemberExceptionBaseBO {

    private String memberId;
    private String itemCode;
    private String itemValue;


    public MemberExceptionBaseBO() {
    }

    public MemberExceptionBaseBO(String memberId, String itemCode, String itemValue) {
        this.memberId = memberId;
        this.itemCode = itemCode;
        this.itemValue = itemValue;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
