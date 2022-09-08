package com.comvee.cdms.defender.wechat.constant;

/**
 * @Author linr
 * @Date 2021/12/3
 */
public class MemberBarrierConstant {

    //关卡barrier_type
    public final static int BARRIER_TYPE_TRIALYARD = 1;//试炼场
    public final static int BARRIER_TYPE_COMMON = 2;//普通关卡


    //关卡status
    public final static int BARRIER_STATUS_ONGOING = 1;//进行中
    public final static int BARRIER_STATUS_FINISHED = 2;//已完成
    public final static int BARRIER_STATUS_LOCKED = 3;//待解锁
    public final static int BARRIER_STATUS_RETRY = 4;//重新闯关
    public final static int BARRIER_STATUS_COMING_SOON = 5;//敬请期待（仅前端展示）



    public final static int BARRIER_RESULT_SUCCESS = 1;//闯关成功
    public final static int BARRIER_RESULT_FAILED = 0;//闯关失败



    //闯关结果tip 小助点评

    public final static String BARRIER_RESULT_TITLE_100 = "棒棒哒，恭喜本次闯关成功！";
    public final static String BARRIER_RESULT_TITLE_80 = "有一点儿遗憾，错了一题。";
    public final static String BARRIER_RESULT_TITLE_60 = "刚及格，同志还需努力！";
    public final static String BARRIER_RESULT_TITLE_40 = "您是不是开小差了？";
    public final static String BARRIER_RESULT_TITLE_20 = "您是不是开小差了？";
    public final static String BARRIER_RESULT_TITLE_0 = "您是不是开小差了？";


    public final static String BARRIER_RESULT_100 = "您超强的学习和应对能力，让控糖一帆风顺。欢迎您继续闯关，争取成为最强糖友！";
    public final static String BARRIER_RESULT_80 = "小助建议您认真过一遍错题解析，扎实的糖尿病知识是控糖最佳武器！下次再接再厉，争取满分！";
    public final static String BARRIER_RESULT_60 = "小助建议您细细温故一遍课程，夯实糖尿病知识会让你的控糖之路顺顺利利！";
    public final static String BARRIER_RESULT_40 = "小助建议您静下心来重学一遍课程，掌握糖尿病知识才能助您轻松应对糖尿病！加油！";
    public final static String BARRIER_RESULT_20 = "小助建议您静下心来重学一遍课程，掌握糖尿病知识才能助您轻松应对糖尿病！加油！";
    public final static String BARRIER_RESULT_0 = "小助建议您静下心来重学一遍课程，掌握糖尿病知识才能助您轻松应对糖尿病！加油！";


}
