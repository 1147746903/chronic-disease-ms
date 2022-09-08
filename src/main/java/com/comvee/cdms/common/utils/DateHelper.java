package com.comvee.cdms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public class DateHelper {
    private static final Logger logger = LoggerFactory.getLogger(DateHelper.class);
    /**yyyy-MM-dd HH:mm:ss**/
    public static String DATETIME_FORMAT        = "yyyy-MM-dd HH:mm:ss";

	/**yyyy-MM-dd HH:mm**/
	public static String DATETIME        = "yyyy-MM-dd HH:mm";
    /**yyyy-MM-dd**/
    public static String DAY_FORMAT        = "yyyy-MM-dd";
    /**yyyyMMddHHmmss**/
    public static String DATETIME_FORMAT_ONE = "yyyyMMddHHmmss";
    /**yyyyMMdd**/
    public static String DATETIME_FORMAT_DAY = "yyyyMMdd";
    /** 00:00:00**/
    public static String DEFUALT_TIME_START  =" 00:00:00";
    /** 23:59:59**/
    public static String DEFUALT_TIME_END  =" 23:59:59";
    /**yyyy年MM月dd日HH时mm分ss秒**/
    public static String DATETIME_FORMAT_ZH        = "yyyy年MM月dd日HH时mm分ss秒";
    /**HH:mm**/
    public static String TIME_FORMAT        = "HH:mm"; 
    /**yyyy-MM**/
    public static String DAY_FORMAT_MONTH        = "yyyy-MM";
    /**MM-dd**/
    public static String FORMAT_MONTH_DAY        = "MM-dd"; 
    /**yyyy年MM月dd日**/
    public static String DAY_FORMAT_ZH        = "yyyy年MM月dd日";
	/**HH:mm:ss**/
	public static String TIME_SECOND_FORMAT        = "HH:mm:ss";

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	/**
	 * 取得时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date,String format){
		DateFormat toDateFormat = new SimpleDateFormat(format);
		String d = toDateFormat.format(date);
		return d;
	}
	/**
	 * 获取当前日期
	 * 
	 * @TODO
	 * @return 返回 格式日期格式为 yyyy-MM-dd
	 * @author antelop
	 * @date 2016年4月1日
	 */
    public static String getToday(){
        return getDate(new Date(), DAY_FORMAT);
    }

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getNowDate(){
    	return getDate(new Date(), DATETIME_FORMAT);
	}
	/**
	 * 取得时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getDate(String dateStr,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算指定两日期相隔的天数,精确到天
	 * @param endDate 结束时间
	 * @return bea 相隔天数
	 */
	public static int dateCompareGetDay(String startDate, String endDate) {
		int bea = 0;
		SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
		Date dCur;
		Date dRol;
		try {
			dCur = sdfD.parse(startDate);
			dRol = sdfD.parse(endDate);
			bea = (int) ((dCur.getTime() - dRol.getTime()) / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
		}
		return bea;
	}
	
	/**
	 * 根据生日记算年龄
	 * @param dateStr  生日
	 * @return
	 */
	public static int getAge(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
//			throw new IllegalArgumentException("dateStr 参数异常");
			return 0;
		}
		DateFormat format1 = new SimpleDateFormat(DAY_FORMAT);
		Date date1 = null;
		try {
			date1 = format1.parse(dateStr);
			Date sysdate = new Date();
			Long time = sysdate.getTime() - date1.getTime();
			Double d1 = time / new Double(1000 * 60 * 60 * 24) - 1;
			String i = new DecimalFormat("#0.00").format((d1 / 365f));
			String[] age = i.split("\\.");
			return Integer.parseInt(age[0].replace("", "").length() < 1 ? "0": age[0]);
		} catch (ParseException e) {
			return 0;
		}
	}
	
	/**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param days 天数
	 * @return
	 * @throws ParseException
	 */
    public static String plusDate(String startdate,int days,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(startdate);
			long ltime = date.getTime();
			long ldays = days * (1000L * 60L * 60L * 24L);
			Date newDay =  new Date(ltime + ldays);
			return getDate(newDay, format);
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param days 天数
	 * @return
	 * @throws ParseException
	 */
    public static String plusDate(String startdate,int days){
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		try {
			Date date = sdf.parse(startdate);
			long ltime = date.getTime();
			long ldays = days * (1000L * 60L * 60L * 24L);
			Date newDay =  new Date(ltime + ldays);
			return getDate(newDay, DAY_FORMAT);
		} catch (ParseException e) {
			return null;
		}
    }

	public static String plusDate2(String startdate,int days){
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			Date date = sdf.parse(startdate);
			long ltime = date.getTime();
			long ldays = days * (1000L * 60L * 60L * 24L);
			Date newDay =  new Date(ltime + ldays);
			return getDate(newDay, DATETIME_FORMAT);
		} catch (ParseException e) {
			return null;
		}
	}

    
    /**
	 * 时间加减
	 * @param startDate
	 * @param type  1年，２月，３日，４时，５分，６秒
	 * @param num
	 * @param format
	 * @return
	 */
	public static String plusDate(String startDate, int type, int num,
			String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(getDate(startDate, format));
		int int1 = 1;
		if (type == int1) {
			c.add(Calendar.YEAR, num);
		} else {
			int int2 = 2;
			if (type == int2) {
				c.add(Calendar.MONTH, num);
			} else {
				int int3 = 3;
				if (type == int3) {
					c.add(Calendar.DAY_OF_YEAR, num);
				} else {
					int int4 = 4;
					if (type == int4) {
						c.add(Calendar.HOUR_OF_DAY, num);
					} else {
						int int5 = 5;
						if (type == int5) {
							c.add(Calendar.MINUTE, num);
						} else {
							int int6 = 6;
							if (type == int6) {
								c.add(Calendar.SECOND, num);
							}
						}
					}
				}
			}
		}
		return f.format(c.getTime());
	}
    
    
    /**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param times  格式时间截
	 * @return
	 */
    public static String addDatetime(Date startdate,long times){
		long ltime = startdate.getTime();
		Date newDay =  new Date(ltime + times);
		return getDate(newDay, DAY_FORMAT);
    }
    
    /**
     * 获取res随机码
     * @param size
     * @return
     */
    public static String createResNum(int size) {
		int num = 2;
		int int14 = 14;
		if(size > int14){
			num = size - int14;
		}
		String str = DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT_ONE).concat(new RandomGenerator().nextNumberString(num)) ;
		return str ;
	}
    
    /**
	 * 不同时间格式化
	 * 
	 * @param date
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 */
	public static String changeTimeFormat(String date, String fromFormat,
			String toFormat) {
		DateFormat format1 = new SimpleDateFormat(fromFormat);
		Date date1 = null;
		try {
			date1 = format1.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("date 参数异常");
		}
		DateFormat toDateFormat = new SimpleDateFormat(toFormat);
		try {
			return toDateFormat.format(date1);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("toFormat 参数异常");
		}
	}
	
	/**
	 * 获取随机时间
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getRadomDateTime(int begin,int end){
		RandomGenerator random = new RandomGenerator();
		int num = random.nextNumber(begin, end);
		Date sysdate = new Date();
		long aaa = num * (1000L * 60L);
		Long time = sysdate.getTime() - aaa;
		Date newDay =  new Date(time);
		return getDate(newDay, DATETIME_FORMAT);
	}
	
	public static String getMonthFirst(String day) {
		SimpleDateFormat f = new SimpleDateFormat(DAY_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, DAY_FORMAT_MONTH));
		cal.set(Calendar.DAY_OF_MONTH,   1);	
		return f.format(cal.getTime());
	}
	

	public static String getMonthLast(String day) {
		SimpleDateFormat f = new SimpleDateFormat(DAY_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, DAY_FORMAT_MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return f.format(cal.getTime());
	}
	
	/**
	 * 获取随机时间
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getRadomDateHour(int begin,int end){
		RandomGenerator random = new RandomGenerator();
		int num = random.nextNumber(begin, end);
		Date sysdate = new Date();
		long aaa = num * (1000L * 60L * 60L);
		Long time = sysdate.getTime() - random.nextNumber(50, 3600)*1147 - aaa;
		Date newDay =  new Date(time);
		return getDate(newDay, DATETIME_FORMAT);
	}

	//时间差距，两个时间相差多少天
	public static long getDistanceTimes(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);  // 天
			//hour = (diff / (60 * 60 * 1000) - day * 24);  // 时
			//min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  // 分
			//sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  // 秒
		} catch (Exception e) {
			e.printStackTrace();
		}
		//long[] times = {day, hour, min, sec};
		return day;
//      String today = DateHelper.getDate(new Date(),"yyyy-MM-dd HH:mm:ss");
//      long min=getDistanceTimes(buyDt, today);
//      if(min <= 5){
//        return re;
//      }
	}

	//时间差距，两个时间相差多少分钟
	public static long getDistanceTimesMin(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			//day = diff / (24 * 60 * 60 * 1000);  // 天
			//hour = (diff / (60 * 60 * 1000) - day * 24);  // 时
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  // 分
			//sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  // 秒
		} catch (Exception e) {
			e.printStackTrace();
		}
		//long[] times = {day, hour, min, sec};
		return min;
//      String today = DateHelper.getDate(new Date(),"yyyy-MM-dd HH:mm:ss");
//      long min=getDistanceTimes(buyDt, today);
//      if(min <= 5){
//        return re;
//      }
	}

	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(DATETIME_FORMAT);
		return df.format(date);
	}

    /**
     * 日期比较
     *
     * @param date1
     * @param dateFormat1
     * @param date2
     * @param dateFormat2
     * @return 返回null, 表示时间格式有误;返回 true: date1 时间在date2之后;返回 false: date1 时间在date2之前
     * @author antelop
     * @date 2016年9月22日
     */
    public static Boolean dateAfter(String date1, String dateFormat1, String date2, String dateFormat2) {
        Date d1 = getDate(date1, dateFormat1);
        Date d2 = getDate(date2, dateFormat2);
        
        if(d1 != null && d2 != null) {
            return d1.after(d2);
        }

        return null;
    }

	/**
	 * 根据当前日期获得所在周的日期区间（周一到周日日期）
	 *
	 * @return 
	 * @author hej
	 */
	public static String getTimeInterval(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateHelper.DAY_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		String weekDateStr = imptimeBegin;
		int int6 = 6;
		for (int i = 1; i <= int6; i++) {
			weekDateStr += "," + plusDate(imptimeBegin,i,DAY_FORMAT);
		}

		return weekDateStr;
	}

	/**
	 * 获取星期几的数值 从星期一 ~ 星期日
	 * @author 苏友智
	 * @time：2018-1-13 下午1:23:51
	 */
	public static int getWeekCode(){
        Calendar c = Calendar.getInstance();
        int weekCode = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekCode <= 0){
            return 7;
        }
        return weekCode;
	}
	//传入时间的周几
	public static int getWeekCodeByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int weekCode = c.get(Calendar.DAY_OF_WEEK) - 1;
		if(weekCode <= 0){
			return 7;
		}
		return weekCode;
	}

	/**
	 * 比较当前时间
	 * 小于当前时间返回ture
	 * @param dateString
	 * @return
	 */
	public static boolean compareNow(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().after(date);
	}

	/**
	 * 获取加减过的时间
	 * @param field
	 * @param amount
	 * @param format
	 * @return
	 */
	public static String getAddDateFormat(int field, int amount, String format){
		Calendar calendar = Calendar.getInstance();
		calendar.add(field ,amount);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 计算一段时间内有多少周
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDistanceWeek(String start,String end){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long result = 0;
		try {
			long from = df.parse(start).getTime();
			long to = df.parse(end).getTime();
			long between = (to - from) / (1000 * 3600 * 24);
			Double days=Double.parseDouble(String.valueOf(between));
			if ((days/7)>0 && (days/7<=1)){
				result = 1;
			}else if (days/7>1){
				int day=days.intValue();
				if (day%7>0){
					result = day/7+1;
				}else{
					result = day/7;
				}
			}else if ((days/7)==0){
				result = 1;
			}
		} catch (ParseException e) {
			logger.error("",e);

		}
		return result;
	}
	/**
	 * 获取当前时间
	 * 
	 * @TODO
	 * @return 返回 格式日期格式为 yyyy-MM-dd
	 * @author antelop
	 * @date 2016年4月1日
	 */
    public static String getTime(){
        return getDate(new Date(), DATETIME_FORMAT);
    } 
    
    /**
	 * 得到添加天数后的时间
	 * @param date
	 * @param addHour
	 * @return
	 */
	public static Date getDateAddDay(Date date,int addDay){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, addDay);
		
		return c.getTime();
	}
	
	public static Date getDateAddWeek(Date date,int addWeek){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//c.add(Calendar.DAY_OF_WEEK, addWeek);
		c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+(addWeek*7));
		
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周一
	 * @param date
	 * @return
	 */
	public static Date getMondayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周二
	 * @param date
	 * @return
	 */
	public static Date geTuesdayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 2);
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周三
	 * @param date
	 * @return
	 */
	public static Date geWednesdayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 3);
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周四
	 * @param date
	 * @return
	 */
	public static Date geThursdayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 4);
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周五
	 * @param date
	 * @return
	 */
	public static Date geFridayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 5);
		System.out.println(c.getTime());
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周六
	 * @param date
	 * @return
	 */
	public static Date geSaturdayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 6);
		System.out.println(c.getTime());
		return c.getTime();
	}
	
	/**
	 * 得到传入时间这一周的周天
	 * @param date
	 * @return
	 */
	public static Date getSundayOfDateWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return c.getTime();
	}

	/**
	 * 判断两个时间的大小
	 * @param date1
	 * @param date2
	 * @return  true date1 > date2   false  date1 < date2
	 */
	public static boolean checkBigSmall(String date1,String date2){
		boolean flag = false;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			if(sdf.parse(date1).getTime()>sdf.parse(date2).getTime()){//转成long类型比较
				flag =  true;
			}else if(sdf.parse(date1).getTime()<=sdf.parse(date2).getTime()){
				flag =  false;
			}
		}catch (ParseException e){
			logger.error("",e);
		}
		return flag;
	}

	/**
	 * 判断两个时间的大小
	 * @param date1
	 * @param date2
	 * @return  1 date1 > date2   -1  date1 < date2 0 date1 = date2 -2 error
	 */
	public static int compareTimeHM(String date1,String date2){
		int flag = -2;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = sdf.format(new Date()).substring(0, 11);
		date1 = dt +date1 + ":00";
		date2 = dt + date2 + ":00";
		try{
			//转成long类型比较
			flag = Long.compare(sdf.parse(date1).getTime(), sdf.parse(date2).getTime());
		}catch (ParseException ignored){
		}
		return flag;
	}

	/**
	 *
	 * @param nowTime   当前时间
	 * @param startTime	开始时间
	 * @param endTime   结束时间
	 * @return
	 * @author sunran   判断当前时间在时间区间内
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEffectiveDateStr(String nowDt, String startDt, String endDt) {
		Date nowTime = null;
		Date startTime = null;
		Date endTime = null;
		DateFormat df=new SimpleDateFormat(DATETIME_FORMAT);

		try {
		 nowTime = df.parse(nowDt);
		 startTime = df.parse(startDt);
		 endTime = df.parse(endDt);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * string类型转date类型
	 * @return
	 */
	public static Date stringToDate(String dateTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将Date类型时间转换为字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formater.format(date);
		return time;
	}

	/**
	 * 判断数值是否连续
	 * @param arr
	 * @return
	 */
	public static boolean IsContinuous(int[] arr){
		boolean flaga = false;
		for (int i = 1; i< arr.length - 1;i++){
			if (arr[i]*2 != arr[i+1]+arr[i-1]){
				flaga = false;
				break;
			}
			if (Math.abs(arr[i+1]-arr[i])!= 1){
				flaga  = false;
				break;
			}
			if ((arr[i+1]-arr[i]) != (arr[i]-arr[i-1])){
				flaga  = false;
				break;
			}
			flaga = true;
			continue;
		}
		return flaga ;
	}


	//获取周对应的数值
	public static String getWeek(){
//		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//		String week = sdf.format(date);
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar=Calendar.getInstance();
		return weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1];
	}

	/**
	 * string类型转date类型
	 * @return
	 */
	public static Date dateTime(String dateTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME);
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * string类型转date类型
	 * @return
	 */
	public static Date date(String dateTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 比较当前时间
	 * 小于当前时间返回ture
	 * @param dateString
	 * @return
	 */
	public static boolean compareHHmm(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(DATETIME);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().after(date);
	}

	public static boolean compareDATETIME(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().after(date);
	}

	/**
	 * 比较当前时间
	 * 小于当前时间返回ture
	 * @param dateString
	 * @return
	 */
	public static boolean compareDate(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().after(date);
	}


	public static List<String> findDates(String dBegin, String dEnd) throws ParseException, ParseException {
		//日期工具类准备
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		//设置开始时间
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(format.parse(dBegin));

		//设置结束时间
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(format.parse(dEnd));

		//装返回的日期集合容器
		List<String> Datelist = new ArrayList<String>();
		//将第一个月添加里面去
		Datelist.add(format.format(calBegin.getTime()));
		// 每次循环给calBegin日期加一天，直到calBegin.getTime()时间等于dEnd
		while (format.parse(dEnd).after(calBegin.getTime()))  {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			Datelist.add(format.format(calBegin.getTime()));
		}

		return Datelist;
	}


	/**
	 * 根据指定日期计算时间周期后的日期
	 * @param dateString
	 * @return
	 */
	public static String cycleMonth(String dateString,int month) {
		SimpleDateFormat sidFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sidFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar  =   Calendar.getInstance();
		calendar.setTime(date); //需要将date数据转移到Calender对象中操作
		calendar.add(calendar.MONTH, month);//把日期往后增加n月.正数往后推,负数往前移动
		date=calendar.getTime();
		String foString = sidFormat.format(date);
		return foString;
	}

	/**
	 * 指定日期，偏移天数
	 * @param date
	 * @param OffsetDay
	 * @return
	 */
	public static Date getOffsetDate(Date date,Integer OffsetDay){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, OffsetDay);
		Date date1 = new Date(calendar.getTimeInMillis());
		return date1;
	}


	/**
	 * 获取当天0点
	 * @return date
	 */
	public static Date getTodayStartTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 时间比较
	 */
	public static boolean hourAfter(String date1, String date2) {
		String today = new SimpleDateFormat(DAY_FORMAT).format(new Date());
		Date d1 = getDate(today + " " + date1, DATETIME);
		Date d2 = getDate(today + " " + date2, DATETIME);

		if(d1 != null && d2 != null) {
			return d2.after(d1);
		}
		return false;
	}

	public  static String getHourAddHour(String hour, int offSetHour){
		String today = new SimpleDateFormat(DAY_FORMAT).format(new Date());
		Date d1 = getDate(today + " " + hour, DATETIME);

		if(d1 !=null && offSetHour != 0){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d1);
			calendar.add(Calendar.HOUR_OF_DAY, offSetHour);
			d1 = calendar.getTime();
		}
		return getDate(d1, TIME_FORMAT);
	}


	/**
	 * 获取系统当前时间
	 * 功能说明:
	 * author：Suyz
	 * 创建时间：2015-6-9
	 * @return
	 */
	public static String getSystemDate(){
		return sdf.format(new Date());
	}

	//根据当前时间获取日期({1:近一周}，{2：近2周}，{3：近一个月}，{4：近半年}，{5：全部},{6:近2个月},{7：近3个月})
	public static String getNeedStartDateTime(int timeType){
		Calendar c = Calendar.getInstance();
		switch(timeType){
			case 1:
				c.add(Calendar.DAY_OF_YEAR, -6);
				break;
			case 2:
				c.add(Calendar.DAY_OF_YEAR, -14);
				break;
			case 3:
				c.add(Calendar.MONTH, -1);
				break;
			case 4:
				c.add(Calendar.MONTH, -6);
				break;
			case 5:
				c.add(Calendar.YEAR, -30);
				break;
			case 6:
				c.add(Calendar.MONTH, -2);
				break;
			case 7:
				c.add(Calendar.MONTH, -3);
				break;
			case 8:
/*			c.add(Calendar.DATE, 1);
			break;*/
		}
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return sdf.format(c.getTime());
	}


	/**
	 * 比较这个时刻是否在这个时段内
	 *
	 * @param strTime 时段 "xx:xx:xx-xx:xx:xx"(可以跨天)
	 * @param nowDate 被比较的时刻 HH:mm:ss
	 * @return
	 */
	public static boolean comparisonTime(String strTime,String nowDate) {
		try {
			if (strTime.equals("") || strTime == null) {
				return false;
			}
			String[] all = strTime.split("-");
			String[] start = all[0].split(":");
			String[] end = all[1].split(":");
			//Log.e("TAG",all[0] + " " +all[1]);
			if (all[0].equals(all[1])) {
				return false;
			}
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			Date date = sdf2.parse(nowDate);
			long dt = date.getTime();
			//开始时间刻度(ms)
			long dts = new Date(date.getYear(),
					date.getMonth(),
					date.getDate(),
					Integer.parseInt(start[0]),
					Integer.parseInt(start[1]),
					0).getTime();
			//结束时间刻度(ms)
			long dte = new Date(date.getYear(),
					date.getMonth(),
					date.getDate(),
					Integer.parseInt(end[0]),
					Integer.parseInt(end[1]),
					59).getTime();

			if (dts > dte) //跨天
			{
				long Day1 = 24 * 60 * 60 * 1000;//1天的毫秒
				//结束时刻 +1天
				long ndte = dte + Day1;
				if (dts <= dt && dt <= ndte) {
					return true;
				}
				//开始时间 -1天
				long ndts = dts - Day1;
				if (ndts <= dt && dt <= dte) {
					return true;
				}
			} else {
				if (dts <= dt && dt <= dte) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description 根据当前日期获取本季度第一天
	 **/
	public static String getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = null;
		try {
			if (currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth <= 9)
				c.set(Calendar.MONTH, 6);
			else if (currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = shortSdf.format(c.getTime()) + DateHelper.DEFUALT_TIME_START;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * @Description 根据当前日期获取本季度最后一天
	 **/
	public static String getCurrentQuarterEndTime() {
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = null;
		try {
			cal.setTime(longSdf.parse(getCurrentQuarterStartTime()));
			cal.add(Calendar.MONTH, 2);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			date = shortSdf.format(cal.getTime()) + DateHelper.DEFUALT_TIME_END;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	//获取本周开始时间
	public static String getCurrentWeekStartTime() {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date mondayOfDateWeek = getMondayOfDateWeek(new Date());
		String format = shortSdf.format(mondayOfDateWeek);
		return format +DateHelper.DEFUALT_TIME_START;
	}

	//获取本周结束时间
	public static String getCurrentWeekEndTime() {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sundayOfDateWeek = getSundayOfDateWeek(new Date());
		String format = shortSdf.format(sundayOfDateWeek);
		return format + DateHelper.DEFUALT_TIME_END;
	}

}
