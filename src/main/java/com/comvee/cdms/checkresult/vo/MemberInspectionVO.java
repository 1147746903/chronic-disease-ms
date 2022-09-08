package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

public class MemberInspectionVO implements Serializable {
    /**
     * 检查名称
     */
    private String name;
    /**
     * 检查类型 1普检 2并发症
     */
    private Integer type;
    private Object info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
