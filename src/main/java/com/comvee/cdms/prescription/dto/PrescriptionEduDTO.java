package com.comvee.cdms.prescription.dto;

/**
 * @author 李左河
 * @date 2018/8/13 15:30.
 * 管理处方 知识模块
 */
public class PrescriptionEduDTO {
    @Override
    public String toString() {
        return "PrescriptionEduDTO{}";
    }
    /**
     * 知识点父级id
     */
    private String pid;
    /**
     * 类型(1知识点分类2知识点)
     */
    private String type;
    /**
     * 查询条件
     */
    private String param;

    private Integer eohType;

    private String memberId;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
