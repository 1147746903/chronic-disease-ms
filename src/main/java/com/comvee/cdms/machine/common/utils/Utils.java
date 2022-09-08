package com.comvee.cdms.machine.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 得到"11日12:00" 这个时间前一小时的时间"11日11:00"
 * 调用：Date time = new Date();
 *	   String yy = timePlus(time);
 */
public class Utils {
	
	private static Log log = LogFactory.getLog(Utils.class);
	
	public final static String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	
	public final static String TIME_FORMAT_DEFAULT = "HH:mm:ss";
	
	//得到"11日12:00" 这个时间前一小时的时间"11日11:00" 
	public static String gettimePlus(Date time){
		Calendar cal=Calendar.getInstance();
		cal.setTime(time);//日历为今天
                long tm,tm1;
                tm=cal.getTimeInMillis();//得到当前时间与1970年1月1日0点相距的毫秒数
                tm1=tm-(60*60*1000);//得到昨天与1970年1月1日0点相距的毫秒数
	 	Date time1=new Date(tm1);
	 	SimpleDateFormat sdf=new SimpleDateFormat("dd日HH:mm");
	 	String tm2=sdf.format(time1);//tm就是昨天的日期的字符串表示
		return tm2;
	}
	public static Long[] ObjArrayToLongArray(Object[] obj) throws Exception {
		Long[] longs = null;
		if (obj != null && obj.length > 0) {
			int size = obj.length;
			longs = new Long[size];
			for (int i = 0; i < size; i++) {
				longs[i] = Long.parseLong(obj[i].toString());
			}
		}
		return longs;
	}

	public static Long[] ObjArrayToLongArray(Object[] obj, Object[] clazzObj)
			throws Exception {
		int size = obj.length;
		// Class[] clazz = (Class[]) clazzObj.getClass().newInstance();
		Long[] longs = new Long[size];
		for (int i = 0; i < size; i++) {
			longs[i] = Long.parseLong(obj[i].toString());
		}
		return longs;
	}

	public static String[] ObjArrayToStringArray(Object[] obj) throws Exception {
		String[] str = null;
		if (obj != null && obj.length > 0) {
			int size = obj.length;
			str = new String[size];
			for (int i = 0; i < size; i++) {
				str[i] = String.valueOf(obj[i].toString());
			}
		}
		return str;
	}

	/**
	 * 返回指定格式的时间字符串
	 * 
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String getDateTime(String format) {
		String strDateTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		strDateTime = sdf.format(new Date());
		return strDateTime;
	}

	
	/**
	 * 得到小时
	 * @param date
	 * @param addHour
	 * @return
	 */
	public static int getHour(Date date,int addHour){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, addHour);
		
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 得到添加天数后的时间
	 * @param date
	 * @param addHour
	 * @return
	 */
	public static Date getDateAndDay(Date date,int addDay){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, addDay);
		
		return c.getTime();
	}
	
	
	/**
	 * 得到添加小时后的时间
	 * @param date
	 * @param addHour
	 * @return
	 */
	public static Date getDateAndHour(Date date,int addHour){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, addHour);
		
		return c.getTime();
	}
	
	/**
	 * 得到添加秒后的时间
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date getDateAndSeconds(Date date,int seconds){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		
		return c.getTime();
	}
	
	/**
	 * 得到添加分钟后的时间
	 * @param date
	 * @param addHour
	 * @return
	 */
	public static Date getDateAndMin(Date date,int addMin){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, addMin);
		
		return c.getTime();
	}
	
	
	
	/**
	 * 通过日期字符串得到时间
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getDateFromStr(String dateStr,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDateFromStr(String dateStr,String format,Locale locale){
		SimpleDateFormat sdf = new SimpleDateFormat(format,locale);
		
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回指定格式的时间字符串
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String getDateTime(Date date, String format) {
		String strDateTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		strDateTime = sdf.format(date);
		return strDateTime;
	}
	
	/**
	 * 获取两个时间间隔的天数
	 * @param one
	 * @param two
	 * @return
	 */
	public static long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }

	/**
	 * 判断是否是移动手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern pattern = Pattern
				.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?(13|15|18)\\d{9}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断是否是移动手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String[] str) {
		for (String temp : str) {
			if (!isMobile(temp)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是URL
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUrl(String str) {
		Pattern pattern = Pattern
				.compile("^http:\\/\\/[A-Za-z0-9]+\\.[A-Za-z0-9]+[\\/=\\?%\\-&_~`@[\\\\]\\':+!]*([^<>\\\"\\\"])*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().equals("");
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(Object str) {
		return str == null;
	}

	/**
	 * 字符串按照指定长度截断，返回字符串数组
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String[] subStringToArray(String str, int length) {
		int size = str.length() % length == 0 ? str.length() / length : str
				.length()
				/ length + 1;
		String[] array = new String[size];
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				array[i] = str.substring(i * length, i * length + length);
			} else {
				array[i] = str.substring(i * length);
			}
		}
		return array;
	}

	/**
	 * 字符串数组连接成字符串
	 * 
	 * @param str
	 * @param link
	 * @return
	 */
	public static String arrayToString(String[] array, String link) {
		StringBuffer sb = new StringBuffer();
		for (String temp : array) {
			if (temp != null && !temp.equals("")) {
				if (sb.length() > 0) {
					sb.append(link);
				}
				sb.append(temp);
			}
		}
		return sb.toString();
	}

	/**
	 * 压缩数组，删除null值和空字符串值
	 * 
	 * @param array
	 *            数组
	 * @param split
	 *            连接符
	 * @param mergerSize
	 *            合并大小
	 * @return
	 */
	public static String[] compressSizeArray(String[] array, String split,
			int mergerSize) {
		List<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			String temp = array[i];
			if (temp != null && !temp.equals("")) {
				if (sb.length() > 0) {
					sb.append(split);
				}
				sb.append(temp);
				count++;
			}
			if ((count % mergerSize == 0 && count != 0)
					|| i == array.length - 1) {
				result.add(sb.toString());
				sb = new StringBuffer();
			}
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * 产生随机验证码
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomCode(int count) {
		Random rand = new Random();
		String randNum = String.valueOf(Math.abs(rand.nextInt()));
		while (randNum.length() != count) {
			if (randNum.length() > count)
				randNum = randNum.substring(0, randNum.length() - 1);
			else
				randNum = randNum + "0";
		}
		return randNum;
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }  

	/**
	 * 字符串转换为日期
	 * 
	 * @param strDate
	 *            日期的字符串形式
	 * @param format
	 *            转换格式
	 * @return String 转换后的日期字符串
	 */
	public static Date strToDate(String strDate, String format)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		date = dateFormat.parse(strDate);
		return date;
	}

	/**
	 * 字符内码转换 ISO TO 制定编码
	 */
	public static String getISOtoEncode(String para, String encode) {
		if (para == null || "".equals(para)) {
			return "";
		}
		para = para.trim();
		String strtmp = "";
		try {
			strtmp = new String(para.getBytes("iso-8859-1"), encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strtmp;
	}

	public static String lastSubstring(String source, int length) {
		String no = source.substring(source.length() - length);
		return source.substring(0, source.length() - no.length());
	}

	public static boolean stringHasLength(String str) {
		return str != null && !"".equals(str);
	}

	
	/**
	 * 得到年份
	 * 
	 * @return
	 */
	public static int getYear(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 得到年份
	 * 
	 * @return
	 */
	public static int getYear(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 得到小时
	 * 
	 * @return
	 */
	public static int getHour() {
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 得到月份
	 * 
	 * @return
	 */
	public static int getMonth(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 得到月份
	 * 
	 * @return
	 */
	public static int getMonth(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static String getMonthStr(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		int m = calendar.get(Calendar.MONTH) + 1;
		String sm = "";
		if(m<10){
			sm = "0" + String.valueOf(m);
		}else{
			sm = String.valueOf(m);
		}
		return sm;
	}

	/**
	 * 得到天数
	 * 
	 * @return
	 */
	public static int getDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 得到天数
	 * 
	 * @return
	 */
	public static int getDay(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到周
	 * 
	 * @return
	 */
	public static int getWeek(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	private static String[] weekStr = {"日","一","二","三","四","五","六"};
	/**
	 * 得到周
	 * 
	 * @return
	 */
	public static String getWeekOfChina(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return weekStr[calendar.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	
	public static String getDayStr(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		String sdate = "";
		if(d<10){
			sdate = "0" + String.valueOf(d);
		}else{
			sdate = String.valueOf(d);
		}
		return sdate;
	}

	@SuppressWarnings("unchecked")
	public static Map getAreaName() {

		Map areaMap = new TreeMap();
		areaMap.put("590", "全省");
		areaMap.put("591", "福州");
		areaMap.put("592", "厦门");
		areaMap.put("593", "宁德");
		areaMap.put("594", "莆田");
		areaMap.put("595", "泉州");
		areaMap.put("596", "漳州");
		areaMap.put("597", "龙岩");
		areaMap.put("598", "三明");
		areaMap.put("599", "南平");
		areaMap.put("502", "晋江");
		areaMap.put("0590", "全省");
		areaMap.put("0591", "福州");
		areaMap.put("0592", "厦门");
		areaMap.put("0593", "宁德");
		areaMap.put("0594", "莆田");
		areaMap.put("0595", "泉州");
		areaMap.put("0596", "漳州");
		areaMap.put("0597", "龙岩");
		areaMap.put("0598", "三明");
		areaMap.put("0599", "南平");

		return areaMap;
	}

	public static Double roundDouble(double val, int precision) {
		Double ret = null;
		try {
			double factor = Math.pow(10, precision);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String urlTool(String str, String encode) {

		String _s = null;

		try {

			_s = java.net.URLEncoder.encode(str, encode);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}

		return _s;

	}
	
	/**
	 * 从服务器得到数据
	 * 
	 * @param urlStr
	 * @param argStr 参数串
	 * @return
	 */
	public static String getFromHttp(String urlStr, String argStr,String character) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			
			int len = argStr==null ? 0 : argStr.getBytes(character).length;
			conn.setRequestProperty("Content-Length", String.valueOf(len));
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0"); 
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			// conn.setConnectTimeout(1000 * timeOut);
			// 这里是关键，表示我们要向链接里输出内容
			conn.setDoOutput(true);
			
			
			// 获得连接输出流
			OutputStream out = conn.getOutputStream();

			// 把数据写入
			out.write(argStr.getBytes(character));
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(),character));
			String line = null;
			
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			//log.info("从http返回的数据为:" + sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 从服务器得到数据
	 * 
	 * @param urlStr
	 * @param argStr 参数串
	 * @return
	 */
	public static String getFromHttp(String urlStr,String character) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0"); 
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(),character));
			String line = null;
			
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			//log.info("从http返回的数据为:" + sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private final static String CHIN_NUM = "一二三四五六七八九十";
	/**
	 * 通过中文数字得到阿拉伯数字
	 * @param str
	 * @return
	 */
	public static int getIntByChin(String str){
		return CHIN_NUM.indexOf(str) + 1;
	}
	
	public static void ce() throws UnsupportedEncodingException{
		String encodeStr = java.net.URLEncoder.encode("夏威夷","UTF-8");
		  System.out.println("编码后:"+encodeStr);
		  String decodeStr = java.net.URLDecoder.decode(encodeStr, "UTF-8");
		  System.out.println("解码后："+decodeStr);  
		  System.out.println("------------------------------------------");
		  String enc = "编码的是这里";
		  enc = new String(enc.getBytes(),"iso-8859-1");
		  System.out.println("enc="+enc);
		  String dec = new String(enc.getBytes("iso-8859-1"),"GBK");
		  System.out.println("dec="+dec);
		  
		  System.out.println(getWeek(0));
		  System.out.println(getWeekOfChina(0));
	}
	
	@SuppressWarnings("unchecked")
	public static String getStrFromMap(Map map,String key){
		if(map.get(key) == null){
			if(map.keySet().contains(key)){
				return "";
			}
			
			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
				Object keyobj = (Object) iterator.next();
				if(keyobj instanceof String){
					String keyStr = (String)keyobj;
					if(keyStr.equalsIgnoreCase(key)){
						return map.get(keyobj) == null ? "" : String.valueOf(map.get(keyobj));
					}
				}
			}
			return "";
		}else{
			return String.valueOf(map.get(key));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String getStrFromMap(Map map,String key,String defaultValue){
		//key = key.toUpperCase();
		if(map.get(key) == null){
			return defaultValue;
		}else{
			return String.valueOf(map.get(key));
		}
	}
	
	public static int getIntFromMap(Map map,String key,int defaultValue){
		//key = key.toUpperCase();
		if(map.get(key) == null){
			return defaultValue;
		}else{
			try {
				return Integer.valueOf(String.valueOf(map.get(key)));
			} catch (Exception e) {
				return defaultValue;
			}
		}
	}
	
	public static double getDoubleFromMap(Map map,String key,double defaultValue){
		String strVal = getStrFromMap(map, key);
		try {
			return Double.valueOf(strVal);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static long getLongFromMap(Map map,String key,long defaultValue){
		String strVal = getStrFromMap(map, key);
		try {
			return Long.valueOf(strVal);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static Boolean getBooleanFromMap(Map map,String key,Boolean defaultValue){
		String strVal = getStrFromMap(map, key);
		try {
			return Boolean.valueOf(strVal);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	/**
	 * 通过用户号码得到用户的类型
	 * 2012-10-08 添加用户号码类型区分
	 * @param customer
	 * @return 0-固话,1-小灵通,2-移动,3-ADSL,4-智能公话, 6-翼支付
	 */
	public static int getCustomerType(String customer){
		int type = 2;
		
		if(customer.startsWith("1") && customer.length()==11){
			type = 2;
		}else if(customer.startsWith("059")){
			type = 0;
		}
		
		return type;
	}
	
	public static void main(String[] args) {
		//System.out.println(getDay(7));
		try {
			//
			//String s = java.net.URLDecoder.decode("100.00%7C%D5%E3%BD%AD%D2%C6%B6%AF100%D4%AA_%D6%B1%B3%E5%C1%AA%B5%F7%B1%A6%B1%B4%7C%D6%D0%B9%FA%D2%C6%B6%AF", "GBK");
			//ce();
			
//			String artStr = "j_username=kyfeytsg&j_password=kyfeytsg&x=42&y=15";
//			String s = getFromHttp("http://www.sinomed.ac.cn/j_spring_security_check", artStr, "utf-8");
//			System.out.println(s);
			
			System.out.println(daysBetween(getDateAndDay(new Date(), -1),new Date()));
			System.out.println(getRandomCode(6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
