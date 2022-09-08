package com.comvee.cdms.user.vo;

public class CreateImageVerifyCodeVO {

    private String id;
    private String imageBase64;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
