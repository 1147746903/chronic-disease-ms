package com.comvee.cdms.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证工具类
 * 
 * @author antelop
 */
public abstract class AbstractIdcardTool {

    private final static String IDCARD_REGEX = "^(\\d{10}((01(0[1-9]|[1-2]\\d|3[0-1]))|(02(0[1-9]|[1-2]\\d))|(0[3-9](0[1-9]|[1-2]\\d|3[0-1]))|(1[0-2](0[1-9]|[1-2]\\d|3[0-1])))\\d{3}[0-9Xx]{1})$";

    /**
     * 校验身份证号码
     * 
     * @param idcard
     * @return
     */
    public static boolean check(String idcard) {
        Pattern p = Pattern.compile(IDCARD_REGEX);
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    /**
     * 获取出生日期 出生日期码(7-14)
     * 
     * @param idcard
     * @return
     */
    public static Date getBirthday(String idcard) {
        Integer year = Integer.valueOf(idcard.substring(6, 10));
        Integer month = Integer.valueOf(idcard.substring(10, 12));
        Integer date = Integer.valueOf(idcard.substring(12, 14));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return calendar.getTime();
    }
    
    /**
     * @TODO 获取出生日期
     * 
     * @param idcard
     * @return 返回格式为yyyy-MM-dd 的日期
     * @author antelop
     * @date 2016年4月5日
     */
    public static String getBirthdayOfFormat(String idcard) {
        return getBirthdayOfFormat(idcard, DateHelper.DAY_FORMAT);
    }
    
    /**
     * @TODO 获取出生日期
     * 
     * @param idcard 身份证号码
     * @param format 日期格式
     * @return
     * @author antelop
     * @date 2016年4月5日
     */
    public static String getBirthdayOfFormat(String idcard, String format) {
       Date birthday = getBirthday(idcard);
       return DateHelper.getDate(birthday, format);
    }

    /**
     * 获取性别 顺序码（15-17）,奇数表示男性，偶数表示女性
     * 
     * @param idcard
     * 
     * @return m: 男 f: 女
     */
    public static String getSex(String idcard) {
        Integer sexNum = Integer.valueOf(idcard.substring(14, 17));
        return sexNum % 2 == 0 ? "f" : "m";
    }

    /**
     * 获取性别中文名称
     *      顺序码（15-17）,奇数表示男性，偶数表示女性
     * @param idcard
     * 
     * @return 男  女
     */
    public static String getSexOfChinese(String idcard) {
        String sex = getSex(idcard);
        return sex == "f" ? "女" : "男";
    }

    /**
     * 获取年龄 (周岁)
     * 
     * @param idcard
     * @return
     */
    public static int getAge(String idcard) {
        Date birthday = getBirthday(idcard);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        if (nowCalendar.before(calendar)){
            throw new IllegalArgumentException("the birthday of idcard is illegal");
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int nowYear = nowCalendar.get(Calendar.YEAR);
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        int nowDate = nowCalendar.get(Calendar.DATE);

        int diffMonth = nowMonth - month;
        int diffYear = nowYear - year;
        int diffDate = nowDate - date;

        boolean b = diffMonth > 0 || (diffMonth == 0 && diffDate > 0);
		if (b) {
            return diffYear;
        } else {
            return --diffYear;
        }
    }
}
