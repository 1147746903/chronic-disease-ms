package com.comvee.cdms.user.po;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public class UserRelationPO implements Serializable{

    private String sid;
    private String uid;
    private String foreignId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

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
