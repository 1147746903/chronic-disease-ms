package com.comvee.cdms.other.constant;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public class WechatQrCodeConstant {

    /**
     * 微信二维码业务类型 1 医生二维码 2 筛查报告 3 智能看护(礼来) 4 足部处方
     * 5、患者动态血糖传感器绑定编号
     * 6 s281血糖仪二维码
     * 7 销售产品二维码
     * 8 渠道邀请好友二维码
     * 9 探头二维码
     * 10 微信公众号普通文本消息（比如发送控糖米的问卷）
     */
    public static final int QR_CODE_BUSINESS_TYPE_DOCTOR = 1;
    public static final int QR_CODE_BUSINESS_TYPE_SCREENING_REPORT = 2;
    public static final int QR_CODE_BUSINESS_TYPE_INTELLIGENT_NURSING = 3;
    public static final int QR_CODE_BUSINESS_TYPE_FOOT_PRESCRIPTION = 4;
    public static final int QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID = 5;
    public static final int QR_CODE_BUSINESS_TYPE_S281 = 6;
    public static final int QR_CODE_BUSINESS_TYPE_PRODUCT = 7;
    public static final int QR_CODE_BUSINESS_TYPE_INVITE_FRIENDS = 8;
    public static final int QR_CODE_BUSINESS_TYPE_SENSOR = 9;
    public static final int QR_CODE_BUSINESS_TYPE_WX_TEXT_MESSAGE = 10;


    /**
     * 二维码期限类型 1 长期 2临时
     */
    public static final int QR_CODE_TYPE_FOREVER = 1;
    public static final int QR_CODE_TYPE_TEMPORARY = 2;

    /**
     * 最大过期时间 30天
     */
    public static final long MAX_EXPIRE_SECONDS = 2592000L;

    /**
     * 二维码来源 1控糖有方患者端 2渠道端
     */
    public static final int QR_CODE_ORIGIN_ONE = 1;
    public static final int QR_CODE_ORIGIN_TWO = 2;


}
