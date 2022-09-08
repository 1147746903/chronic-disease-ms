package com.comvee.cdms.follow.cfg;

/**
 * @author wyc
 * @date 2019/12/20 13:25
 */
public class FollowRemindConstant {

    /**
     * 类型 1、首诊完成提醒 2、日常随访完成提醒  3、15天提醒 4、45天提醒 5、75天提醒
     * 6 、定期随访提醒 7、门诊随访提醒 8、电话随访提醒9.首诊随访提醒10. 日常随访提醒
     * 11.自我行为随访提醒12.y天后随访提醒 13.自定义随访完成提醒14.自定义随访提醒
     * 15.2型糖尿病随访表完成提醒 16 2型糖尿病随访提醒
     */
    public final static String REMIND_SZWC = "1";  //首诊完成提醒
    public final static String REMIND_RCSFWC = "2";  //日常随访完成提醒
    public final static String REMIND_FIFTEEN = "3";  //15天随访提醒
    public final static String REMIND_FORTY_FIVE = "4";  //45天随访提醒
    public final static String REMIND_SEVENTY_FIVE = "5";  //75天随访提醒
    public final static String REMIND_DQ = "6";   //定期随访提醒
    public final static String REMIND_MZ = "7";   //门诊随访提醒
    public final static String REMIND_DH = "8";   //电话随访提醒
    public final static String REMIND_SZ= "9";   //首诊随访提醒
    public final static String REMIND_RC= "10";   //日常随访提醒
    public final static String REMIND_ZWXW= "11";   //自我行为随访提醒
    public final static String REMIND_TQTX= "12";   //y天后随访提醒
    public final static String REMIND_ZDYWC= "13";   //自定义随访完成提醒
    public final static String REMIND_ZDY= "14";   //自定义随访提醒
    public final static String REMIND_TNBWC= "15";   //2型糖尿病随访表完成提醒
    public final static String REMIND_TNB= "16";   //2型糖尿病随访提醒
    public final static String REMIND_TNBZ= "17";   //糖尿病足随访提醒
    public final static String REMIND_TNBSF= "18";   //糖尿病随访提醒
    public final static String REMIND_TNBZWC= "19";   //糖尿病足随访完成提醒
    public final static String REMIND_TNBSFWC= "20";   //糖尿病随访完成提醒
    public final static String REMIND_GXYSZ= "21";   //高血压首诊随访提醒
    public final static String REMIND_GXYSZWC= "22";   //高血压首诊随访完成提醒
    public final static String REMIND_JKPG= "23";   //健康评估随访提醒
    public final static String REMIND_NJTNBSF= "24";   //南京鼓楼医院糖尿病随访提醒
    public final static String REMIND_GXY_FOLLOW = "25";   //高血压随访完成提醒
    public final static String REMIND_TNB_ASSESS = "26";   //糖尿病风险评估随访提醒
    public final static String REMIND_GXY_FOLLOW_JW = "27";   //高血压随访提醒



    public final static String REMIND_SFWC = "1,2,13,15,22,25,19,20,22";   //随访完成提醒
    public final static String REMIND_OTHER = "3,4,5,7,9,10,11,12,13,14,16,17,18,21,23,24,26,27"; //其他随访提醒

}
