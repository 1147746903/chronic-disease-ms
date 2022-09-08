package com.comvee.cdms.wechat.exception;


import com.comvee.cdms.wechat.config.WechatApiCode;

/**
 * Created by Su on 2018/8/2.
 */
public class WechatApiException extends RuntimeException{

    private String errCode;
    private String errMsg;

    public WechatApiException(String errCode){
        super("微信api调用异常");
        this.errCode = errCode;
        this.errMsg = WechatApiCode.getErrMessage(errCode);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
