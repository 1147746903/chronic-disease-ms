package com.comvee.cdms.user.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public class UserRelationDTO implements Serializable{

    private String uid;
    private String foreignId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }
}
