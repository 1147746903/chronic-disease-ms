package com.comvee.cdms.hospital.model.dto;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public class ListHospitalDTO {

    private String keyword;
    private String areaId;
    private Integer level;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
