package com.comvee.cdms.defender.common.cfg;

import com.comvee.cdms.common.cfg.Config;

public class Constant {
	public static String APP_WEB_INFO_PATH   = "";                        
	public static String FILE_SEPARATOR      = "";     
	
	public static Boolean RELEASE     = "1".equals(Config.getValueByKey("release"));//是否正式地址
	
	
	public final static String MYSQL_DATE_FORMAT ="%Y-%m-%d %H:%i:%s";
	public final static String MYSQL_DATE_FORMAT_Y_M_D ="%Y-%m-%d";
	
	public final static String DATE_FORMAT        = "yyyy-MM-dd HH:mm:ss";   
	public final static String DATE_FORMAT_TIME   = "HH:mm:ss";                                      
	public final static String DATE_FORMAT_DAY    = "yyyy-MM-dd";  
	
	//登录用户类型 1管理员 2患者 3医生
	public final static int THC_USER_USER_TYPE_ADMIN   = 1;
	public final static int THC_USER_USER_TYPE_PATIENT = 2;
	public final static int THC_USER_USER_TYPE_DOCTOR  = 3;
	
	/** 自定义httpsession **/
    public final static String DEFINED_SESSION_CACHE_NAME = "defined_session";
    /** 自定义httpsession前缀 **/
    public final static String DEFINED_SESSION_PREFIX = "DEFINED_SESSION_PREFIX_";
    /** 自定义httpsession id参数名 **/
    public final static String DEFINED_SESSION_ID_KEY = "DEFINED_SESSION_ID";


    public final static String CACHE_NAME_TOKEN = "cache_name_token";
    
    public final static String CACHE_PATIENT = "cache_patient";
    /**
     * 血糖仪自动上传
     */
    public final static String RECORD_ORIGIN_XTY_AUTO = "XTY_ORIGIN";
    /**
     * 血糖仪手动录入
     */
    public final static String RECORD_ORIGIN_XTY_MANUAL = "XTY_ORIGIN_MANUAL";

    public final static String ABNORMAL_LEVEL_HIGH = "high";
    public final static String ABNORMAL_LEVEL_LOW = "low";

    public final static String BED_CHECK_IN_TRUE = "1";
    public final static String BED_CHECK_IN_FALSE = "0";

    public final static String MEMBER_CHECK_IN_TRUE = "1";
    public final static String MEMBER_CHECK_IN_FALSE = "0";

    public final static String PLACEHOLDER = "-1";

    public final static String START_HOURS = " 00:00:00";
    public final static String END_HOURS = " 23:59:59";

    public final static String MACHINE_TYPE__GLUCOMETER = "1";
    public final static String MACHINE_TYPE_PUMP = "2";

    /**
     * 用户患者关注状态--已关注
     */
    public final static String USER_MEMBER_CONCERN_STATUS_TRUE = "1";
    /**
     * 用户患者关注状态--未关注
     */
    public final static String USER_MEMBER_CONCERN_STATUS_UN = "0";

    /**
     * 添加血糖状态 1-正常 2-拒绝监测 3-测量失败
     */
    public final static String ADD_PARAM_LOG_STATUS_FOR_SUCCEES = "1";
    public final static String ADD_PARAM_LOG_STATUS_FOR_REFUSE = "2";
    public final static String ADD_PARAM_LOG_STATUS_FOR_ERROR = "3";

    public final static String QC_LIQUID_LEVEL_LOW = "1";
    public final static String QC_LIQUID_LEVEL_NOR = "2";
    public final static String QC_LIQUID_LEVEL_HIGH = "3";

    public final static String QC_LIQUID_LEVEL_NOR_LOW = "5.4";
    public final static String QC_LIQUID_LEVEL_NOR_HIGH = "7.2";

    public final static String QC_LIQUID_LEVEL_HIGH_LOW = "9.7";
    public final static String QC_LIQUID_LEVEL_HIGH_HIGH = "13.1";

    /**
     * 患者记录血糖统计状态
     */
    public final static String RECORD_STATUS_SUCCESS = "1";
    public final static String RECORD_STATUS_FAILE = "0";


    public final static String DICT_PARENT_CODE_HOSPITAL_RANK = "hospital_rank"; //医院级别父code
    public final static String DICT_PARENT_CODE_HOSPITAL_SORT = "hospital_sort"; //医院类别父code
    public final static String DICT_PARENT_CODE_HOSPITAL_NATURE = "hospital_nature"; //医院性质父code
    
    //地区字典表 tc_area
    public final static int DICT_AREA_RANK_PROVINCE = 1; // 省
    public final static int DICT_AREA_RANK_CITY = 2; // 市
    public final static int DICT_AREA_RANK_DISTRICT = 3; // 区

    //请求成功响应
    public final static String request_success_code = "0000";
    public final static String request_success_msg = "成功";
    
    //用户信息session
    public final static String REQUEST_SESSION_ATTRIBUTE_PATIENT = "patient";
    
    
	public static final String WECHAT_SEND_TEXT = "text";
	public static final String WECHAT_SEND_IMAGE = "image";
	public static final String WECHAT_SEND_VOICE = "voice";	

	public static final String FILE_VOICE = "voice";            //音频文件夹
	public static final String FILE_IMAGE = "image";			   //图片文件夹
	public static final String FILE_VIDEO = "video";			   //视频文件夹
	
	
	public static final String WECHAT_USER_STATE_SUBSCRIBE="1";
	public static final String WECHAT_USER_STATE_UNSUBSCRIBE="2";

	/**调用微信统一平台接口 成功返回码**/
	public static final String WECHAT_WECHATPORT_SUCCESS_CODE = "0000";
	
	public static String PID_TEMP="_comvee_@";

}
