package com.comvee.cdms.packages.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/11
 */
public class AddMemberPackageDTO implements Serializable{

    @NotEmpty
    private String packageId;
    @NotEmpty
    private String memberId;
    @NotEmpty
    private String doctorId;

    private String orderId;

    private Long price;
    /**
     * 服务包数量
     */
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
