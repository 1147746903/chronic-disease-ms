package com.comvee.cdms.glu.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhiGe
 * @description
 * @date 2018/4/24 10:09 create
 */
public class IdDTO {

    @NotEmpty(message = "id不能为空!")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
