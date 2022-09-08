package com.comvee.cdms.doctor.model;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class GroupInfoModel implements Serializable{
    private String sid;
    private String name;
    private Integer number;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return number;
    }

    public void setNum(Integer num) {
        this.number = num;
    }
}
