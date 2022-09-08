package com.comvee.cdms.wechat.config;

import com.comvee.cdms.common.cfg.Config;

/**
 * Created by Su on 2018/8/2.
 */
public class WechatApiUrl {

    private static String WEIXIN_PROTOCOL;
    static {
        WEIXIN_PROTOCOL = Config.getValueByKey("weixin.protocol");
        if(WEIXIN_PROTOCOL == null || "".endsWith(WEIXIN_PROTOCOL)){
            WEIXIN_PROTOCOL = "https";
        }
    }

    /**
     * 获取access_token
     */
    public static String URL_TOKEN = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/token";

    /**
     * 获取用户信息
     */
    public static String URL_USER_INFO = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/user/info";

    /**
     * 获取ticket
     */
    public static final String URL_GETTICKET = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 换取open_id
     */
    public static final String JSCODETOSESSION_URL = WEIXIN_PROTOCOL + "://api.weixin.qq.com/sns/jscode2session";

    /**
     * 发送模板消息
     */
    public static final String TEMPLATE_SEND_URL = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/message/template/send";

    /**
     * 发送客服消息
     */
    public static final String MESSAGE_URL = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/message/custom/send";

    /**
     * 生成二维码
     */
    public static final String CREATE_QRCODE_URL = WEIXIN_PROTOCOL + "://api.weixin.qq.com/cgi-bin/qrcode/create";

    /**
     *通过code换取网页授权access_token
     */
    public static final String CODE_EXCHANGE_ACCESS_TOKEN = WEIXIN_PROTOCOL + "://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 使用tikcet换取二维码
     */
    public static final String SHOW_QRCODE = WEIXIN_PROTOCOL + "://mp.weixin.qq.com/cgi-bin/showqrcode";

    /**
     * 获取微信小程序二维码
     */
    public static final String CREATE_MINI_QRCODE_URL = WEIXIN_PROTOCOL + "://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    /**
     * 获取用户手机号码（小程序）
     */
    public static final String GET_USER_PHONE_NUMBER = WEIXIN_PROTOCOL + "://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

}


