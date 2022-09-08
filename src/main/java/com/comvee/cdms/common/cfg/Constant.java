package com.comvee.cdms.common.cfg;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 李左河
 *
 */
public class Constant {

    /**
     * 页面的服务器地址
     */
    public static final String PAGE_HOST = Config.getValueByKey("page.host");


    /**
     * 普通常量String-根据业务代表不同意思
     */
    public final static String CONST_NUM_00 = "0";
    public final static String CONST_NUM_01 = "1";
    public final static String CONST_NUM_02 = "2";
    public final static String CONST_NUM_03 = "3";
    public final static String CONST_NUM_04 = "4";
    public final static String CONST_NUM_05 = "5";
    public final static String CONST_NUM_06 = "6";
    public final static String CONST_NUM_07 = "7";
    public final static String CONST_NUM_08 = "8";
    public final static String CONST_NUM_09 = "9";
    public final static String CONST_NUM_10 = "10";

    /**
     *
     */
    public static String APP_WEB_INFO_PATH   = "";
	public static String FILE_SEPARATOR      = "";

	public final static String DATE_FORMAT        = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT_TIME   = "HH:mm:ss";
	public final static String DATE_FORMAT_DAY    = "yyyy-MM-dd";


    /**
     * 客户端类型（来源） 1 管理员web后台 2 医生web 3 患者微信小程序端 4 医生h5 5、渠道端
     */
    public static final int ORIGIN_ADMIN_WEB = 1;
    public static final int ORIGIN_DOCTOR_WEB = 2;
    public static final int ORIGIN_PATIENT_WX_MINI = 3;
    public static final int ORIGIN_DOCTOR_H5 = 4;
    public static final int ORIGIN_CHANNEL = 5;

    /**
     * 用户身份识别 1 患者 2 医生 3 管理员 4 系统
     */
    public static final int IDENTIFY_PATIENT = 1;
    public static final int IDENTIFY_DOCTOR = 2;
    public static final int IDENTIFY_ADMIN = 3;
    public static final int IDENTIFY_SYSTEM = 4;



    public final static String START_HOURS = " 00:00:00";
    public final static String END_HOURS = " 23:59:59";

    /**
     * 请求成功响应
     */
    public final static String REQUEST_SUCCESS_CODE = "0";
    public final static String REQUEST_SUCCESS_MSG = "成功";

    public final static String BLOOD_SUGAR_WEB_ORIGIN = "1";
    public final static String BLOOD_SUGAR_XTY_ORIGIN = "2";
    public final static String BLOOD_SUGAR_QT_ORIGIN = "3";
    public final static String BLOOD_SUGAR_APP_ORIGIN = "4";
    public final static Map<String,String> BLOOD_SUGAR_ORIGIN_MAP = new HashMap<String, String>(4);
    static {
        BLOOD_SUGAR_ORIGIN_MAP.put(BLOOD_SUGAR_WEB_ORIGIN,"网页");
        BLOOD_SUGAR_ORIGIN_MAP.put(BLOOD_SUGAR_XTY_ORIGIN,"血糖仪");
        BLOOD_SUGAR_ORIGIN_MAP.put(BLOOD_SUGAR_QT_ORIGIN,"其他");
        BLOOD_SUGAR_ORIGIN_MAP.put(BLOOD_SUGAR_QT_ORIGIN,"APP");
    }


    /**随访名称映射**/
    public final static Map<Integer,String> FOLLOW_NAME_MAP = new HashMap<Integer,String>(13);
    static {
        FOLLOW_NAME_MAP.put(1, "糖尿病首诊");
        FOLLOW_NAME_MAP.put(2, "日常随访");
        FOLLOW_NAME_MAP.put(3, "自我行为评估");
        FOLLOW_NAME_MAP.put(4, "糖尿病足随访表");
        FOLLOW_NAME_MAP.put(5, "糖尿病随访表");
        FOLLOW_NAME_MAP.put(7, "2型糖尿病随访表");
        FOLLOW_NAME_MAP.put(9, "南京鼓楼医院糖尿病随访");
        FOLLOW_NAME_MAP.put(8, "健康评估");//江苏南京需求
        FOLLOW_NAME_MAP.put(10, "高血压首诊");
        FOLLOW_NAME_MAP.put(11, "高血压随访");
        FOLLOW_NAME_MAP.put(12, "糖尿病风险评估");
        FOLLOW_NAME_MAP.put(15, "糖尿病病人问卷调查");
        FOLLOW_NAME_MAP.put(16, "V3三个月随访");
        FOLLOW_NAME_MAP.put(17, "V4半年随访");
        FOLLOW_NAME_MAP.put(29, "焦虑抑郁症状自评量表");
    }

    /**
     * 默认外键
     */
    public static final String DEFAULT_FOREIGN_ID  = "-1";

    /** 机构类型映射 **/
    public final static Map<Integer,String> DICT_PARENT_CODE_HOSPITAL_SORT_MAP = new HashMap<Integer,String>();
    static {
        DICT_PARENT_CODE_HOSPITAL_SORT_MAP.put(1, "综合性医院");
        DICT_PARENT_CODE_HOSPITAL_SORT_MAP.put(2, "专科医院");
        DICT_PARENT_CODE_HOSPITAL_SORT_MAP.put(3, "中医综合医院");
        DICT_PARENT_CODE_HOSPITAL_SORT_MAP.put(4, "社区医院");
        DICT_PARENT_CODE_HOSPITAL_SORT_MAP.put(5, "其他");
    }

    /** 机构性质映射 **/
    public final static Map<Integer,String> DICT_PARENT_CODE_HOSPITAL_NATURE_MAP = new HashMap<Integer,String>();
    static {
        DICT_PARENT_CODE_HOSPITAL_NATURE_MAP.put(1, "非盈利（政府办）");
        DICT_PARENT_CODE_HOSPITAL_NATURE_MAP.put(2, "非盈利（非政府办）");
        DICT_PARENT_CODE_HOSPITAL_NATURE_MAP.put(3, "私营");
        DICT_PARENT_CODE_HOSPITAL_NATURE_MAP.put(4, "其他");
    }

    /** 医生职称映射 **/
    public final static Map<Integer,String> DICT_PARENT_CODE_POSITION_MAP = new HashMap<Integer,String>();
    static {
        DICT_PARENT_CODE_POSITION_MAP.put(1,"不详");
        DICT_PARENT_CODE_POSITION_MAP.put(2,"副教授");
        DICT_PARENT_CODE_POSITION_MAP.put(3,"副主任护师");
        DICT_PARENT_CODE_POSITION_MAP.put(4,"副主任技师");
        DICT_PARENT_CODE_POSITION_MAP.put(5,"副主任药师");
        DICT_PARENT_CODE_POSITION_MAP.put(6,"副主任医师");
        DICT_PARENT_CODE_POSITION_MAP.put(7,"副主任医师、副教授");
        DICT_PARENT_CODE_POSITION_MAP.put(8,"副主任中医师");
        DICT_PARENT_CODE_POSITION_MAP.put(9,"高级早教顾问");
        DICT_PARENT_CODE_POSITION_MAP.put(10,"护师");
        DICT_PARENT_CODE_POSITION_MAP.put(11,"教授");
        DICT_PARENT_CODE_POSITION_MAP.put(12,"评审中");
        DICT_PARENT_CODE_POSITION_MAP.put(13,"医师");
        DICT_PARENT_CODE_POSITION_MAP.put(14,"营养师");
        DICT_PARENT_CODE_POSITION_MAP.put(15,"主任护师");
        DICT_PARENT_CODE_POSITION_MAP.put(16,"主任医师");
        DICT_PARENT_CODE_POSITION_MAP.put(17,"主任医师、教授");
        DICT_PARENT_CODE_POSITION_MAP.put(18,"主任中医师");
        DICT_PARENT_CODE_POSITION_MAP.put(19,"主治医师");
        DICT_PARENT_CODE_POSITION_MAP.put(20,"住院医师");
        DICT_PARENT_CODE_POSITION_MAP.put(21,"主管技师");
        DICT_PARENT_CODE_POSITION_MAP.put(22,"其他");
        DICT_PARENT_CODE_POSITION_MAP.put(23,"技师");
        DICT_PARENT_CODE_POSITION_MAP.put(24,"主管护师");
        DICT_PARENT_CODE_POSITION_MAP.put(25,"主管医师");
        DICT_PARENT_CODE_POSITION_MAP.put(26,"主任技师");
        DICT_PARENT_CODE_POSITION_MAP.put(27,"主任药师");
        DICT_PARENT_CODE_POSITION_MAP.put(28,"药师");
        DICT_PARENT_CODE_POSITION_MAP.put(29,"主任医师副教授");
        DICT_PARENT_CODE_POSITION_MAP.put(30,"副主任医师教授");
        DICT_PARENT_CODE_POSITION_MAP.put(31,"主管药师");
        DICT_PARENT_CODE_POSITION_MAP.put(32,"讲师");
        DICT_PARENT_CODE_POSITION_MAP.put(33,"助教");
        DICT_PARENT_CODE_POSITION_MAP.put(34,"副主任技师副教授");
        DICT_PARENT_CODE_POSITION_MAP.put(35,"副主任医师助教");
        DICT_PARENT_CODE_POSITION_MAP.put(36,"主任医师讲师");
        DICT_PARENT_CODE_POSITION_MAP.put(37,"主任技师教授");
        DICT_PARENT_CODE_POSITION_MAP.put(38,"副主任技师助教");
        DICT_PARENT_CODE_POSITION_MAP.put(39,"主任医师助教");
        DICT_PARENT_CODE_POSITION_MAP.put(40,"副主任医师讲师");
        DICT_PARENT_CODE_POSITION_MAP.put(41,"主任药师教授");
        DICT_PARENT_CODE_POSITION_MAP.put(42,"主管技师教授");
        DICT_PARENT_CODE_POSITION_MAP.put(43,"执业医师");
        DICT_PARENT_CODE_POSITION_MAP.put(44,"普通医师");
        DICT_PARENT_CODE_POSITION_MAP.put(46,"掌控健康助理");
        DICT_PARENT_CODE_POSITION_MAP.put(45,"首席健康管理师");
        DICT_PARENT_CODE_POSITION_MAP.put(48,"执业药师");

    }


    //微信授权信息来源：1患者端绑定,2渠道端绑定
    public static final int WECHAT_AUTHORIZED_SOURCE_PATIENT=1;

    public static final String WECHAT_PUBLIC_NAME_PATIENT = "政和县慢病管理中心";


}
