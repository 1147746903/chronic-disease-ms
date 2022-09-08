package com.comvee.cdms.dialogue.vo;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 15:07 create
 */
public class AddDialogueReturnVO {

    private String sid;
    private Long returnTimeStamp;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Long getReturnTimeStamp() {
        return returnTimeStamp;
    }

    public void setReturnTimeStamp(Long returnTimeStamp) {
        this.returnTimeStamp = returnTimeStamp;
    }

    @Override
    public String toString() {
        return "AddDialogueReturnVO{" +
                "sid='" + sid + '\'' +
                ", returnTimeStamp=" + returnTimeStamp +
                '}';
    }
}
