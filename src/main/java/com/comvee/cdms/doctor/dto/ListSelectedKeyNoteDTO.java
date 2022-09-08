package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wyc
 * @date 2020/3/6 16:56
 */
public class ListSelectedKeyNoteDTO implements Serializable {

    private String memberId;

    @NotEmpty(message = "医护人员编号不可为空")
    private String doctorId;

    @NotEmpty(message = "医院编号不可为空")
    private String hospitalId;

    @NotNull(message = "是否住院关注项目不可为空")
    private Integer inHos;

    /**
     * 设置类型 1 特别关注设置 2 查看数据设置 可选默认1
     */
    private Integer type = 1;
    private String cacheKey;

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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }
}
