package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * 获取医院所有可配置的关注项目
 */
public class ListHospitalAllKeyNoteDTO {

    @NotEmpty(message = "医院编号不可为空")
    private String hospitalId;

    /**
     * 显示名称or匹配名称or代码
     */
    private String keyWord;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
