package com.comvee.cdms.common.utils;

import com.comvee.cdms.defender.common.cfg.ConstantErrorMessage;
import com.comvee.cdms.defender.common.exception.DiabetesMsException;
import com.comvee.cdms.common.cfg.ConstantMessageKey;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 李左河
 *
 */
public class ValidateTool {
//	public static boolean isMobileNO(String mobiles){
//		if (mobiles == null ) {
//			return false;
//		}
//		//手机号码
//		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
//		Matcher m = p.matcher(mobiles);
//		if (m.matches()) {
//			return true;
//		}
//		else {
//			//固定电话号码
//			//Pattern p1 = Pattern.compile("^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$");
//			//m = p1.matcher(mobiles);
//			
//			if (mobiles.trim().length() < 11 || mobiles.trim().length() > 12) {
//				return false;
//			} else {
//				return true;
//			}
//		}
//		//return m.matches();
//	}
	
	public static boolean isMobileNO(String mobiles){
//		if (mobiles == null ) {
//			return false;
//		}
//		else {
//			return true;
//		}
		//手机号码
		String re = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(mobiles);
		if (m.matches()) {
			return true;
		} else {
			//固定电话号码
			String ree = "^([0-9]{3,5})?[0-9]{5,10}$";
			Pattern p1 = Pattern.compile(ree);
			String reee = "^([0-9]{3,5}-)?[0-9]{5,10}$";
			Pattern p2 = Pattern.compile(reee);
			String str = "-";
			if(mobiles.indexOf(str)==-1){
				m = p1.matcher(mobiles);
			}else {
				m = p2.matcher(mobiles);
			}
		}
		return m.matches();
	}
	public static boolean isTelephone(String tel){
		if (tel == null ) {
			return false;
		} else{
			return true;
		}
	}
	
	public static boolean isTelephoneByNum(String tel){
		if (tel == null) {
			return false;
		}
		String re = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(tel);
		return m.matches();
	}
	
	
//	public static boolean isTelephone(String tel){
//		if (tel == null ) {
//			return false;
//		}
//		Pattern p = Pattern.compile("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
//		Matcher m = p.matcher(tel);
//		return m.matches();
//	}
	
	public static boolean isEmail(String email){
		if (email == null ) {
			return false;
		}
		String re = "^(\\w+\\.*)\\w+@\\w+\\.[a-zA-z]{2,6}$";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	public static boolean checkCharOrNum(String value,int begin,int end){
		if (value == null ) {
			return false;
		}
		String pattern = "^[A-Za-z0-9]{"+begin+","+end+"}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();	
	}
	public static boolean isNum(String value){
		if (StringUtils.isBlank(value)) {
			return false;
		}
		String str = ".";
		if(str.equals(value)){
			return false;
		}
		String pattern = "^[0-9]+$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		boolean flag = m.matches();
		String re = "[0-9]*(\\.?)[0-9]*";
		Pattern pattern1 = Pattern.compile(re);
		Matcher m1 = pattern1.matcher(value);
		boolean flag1 = m1.matches();
		return flag || flag1;
	}	
	
	public static boolean checkCharOrNumOrChinese(String value, int begin,
			int end) {
		if (value == null) {
			return false;
		}
		String pattern = "^([A-Za-z0-9]|[\\u4E00-\\u9FA5]){" + begin + ","+ end + "}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();
	}	
	
	public static boolean check(String str, String pattern) {
		if (str == null) {
			return false;
		}
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean checkLength(String str, int begin, int end) {
		if (str == null) {
			return false;
		}
		if (str.length() <= end && str.length() >= begin) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否数值
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str){
		String re = "([1-9]+[0-9]*|0)(\\.[\\d]+)?";
		return Pattern.compile(re).matcher(str).matches();
	}  
	
	public static boolean isDecimalValid(String str, int start, int end) {
		if (str == null) {
			return false;
		}
		int s = 0;
		try {
			s = Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}

		if (end == start) {
			if (end == s) {
				return true;
			} else {
				return false;
			}
		} else {
			if (s <= end && s >= start) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证是否为日期时间
	 * @author 李左河
	 * @date 2018年3月21日 上午11:30:34
	 * @param s
	 * @return
	 */
	public static boolean isDateTime(String s) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sf.parse(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 验证字符串是否为日期
	 * @author 李左河
	 * @date 2018年3月21日 上午11:30:41
	 * @param s
	 * @return
	 */
	public static boolean isDate(String s) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			sf.parse(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 验证字符串是否为指定日期格式
	 * @author 李左河
	 * @date 2018年3月21日 上午11:30:50
	 * @param s
	 * @param format
	 * @return
	 */
	public static boolean isDateFormat(String s,String format){
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			sf.parse(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证参数是否为空
	 * @param obj
	 * @return 不为空:true,为空false
	 * @throws BusinessException
	 */
	public static boolean checkIsNull(Object obj){
		if(obj==null){
			return false;
		}
		if (obj instanceof String) {
			if ("".equals(((String) obj).trim())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 验证参数是否为空
	 * @param code  参数名称
	 * @param obj   参数值
	 * @throws BusinessException
	 */
	public static boolean checkParameterIsNull(String code,Object obj) throws BusinessException {
		if(obj==null){
			throw new BusinessException("999", code.concat("不能为空"));
		}
		if (obj instanceof String) {
			if ("".equals(((String) obj).trim())) {
				throw new BusinessException("999", code.concat("不能为空"));
			}
		}
		return true;
	}
	
	/**
	 * 验证时间格式 
	 * @param code 参数名称
	 * @param format 时间格式
	 * @param dateTime 参数值
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkDateFormat(String code,String format,String dateTime) throws BusinessException {
		if(dateTime==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR),code));
		}
		if (!isDateFormat(dateTime,format)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_FORMAT_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_FORMAT_ERROR),code));
		}
		return true;
	}
	
	/**
	 * 验证参数长度
	 * @param code 参数名称
	 * @param obj
	 * @param begin
	 * @param end
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkParamLength(String code,Object obj,int begin,int end) throws BusinessException {
		if(obj==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR),code));
		}
		if (obj instanceof String) {
			if (!checkLength((String)obj,begin,end)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_LENGTH_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_LENGTH_ERROR),code));
			}
		}
		return true;
	}
	
	/**
	 * 验证数值
	 * @param code 参数名称
	 * @param obj
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkDecimal(String code,Object obj) throws BusinessException {
		if(obj==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR),code));
		}
		if (obj instanceof String) {
			if (!isDecimal((String)obj)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR),code));
			}
		}
		return true;
	}
	
	/**
	 * 验证数值有效性
	 * @param code 参数名称
	 * @param obj
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkDecimal(String code,Object obj,float start,float end) throws BusinessException {
		if(obj==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR),code));
		}
		if (obj instanceof String) {
			if (!isDecimal((String)obj)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR),code));
			} else {
				float value = Float.parseFloat((String)obj);
				if ( value > end || value < start) {
					throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR),code));
				}
			}
		}
		return true;
	}
	
	/**
	 * 验证数值有效性
	 * @param code 参数名称
	 * @param obj
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkDecimalValid(String code,Object obj,int start,int end) throws BusinessException {
		if(obj==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR),code));
		}
		if (obj instanceof String) {
			if (!isDecimalValid((String)obj,start,end)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_DECIMAL_ERROR),code));
			}
		}
		return true;
	}
	
	/**
	 * 验证valid
	 * @param validExpress
	 * @param valid
	 * @throws BusinessException
	 */
/*	public static void checkValid(String validExpress,String valid) throws BusinessException {
		if (false) {
			if( !DigestTool.md5Hex(validExpress).equalsIgnoreCase(valid)){
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_VALID_ERROR_CODE),MessageTool.getMessage(ConstantMessageKey.COMMON_VALID_ERROR));
			}
		}
	}*/
	
	/**
	 * 验证参数长度
	 * @param obj
	 * @param begin
	 * @param end
	 * @param tishi
	 * @return boolean
	 * @throws BusinessException
	 */
	public static boolean checkParamLength(Object obj,int begin,int end,String tishi,String tishi1)throws BusinessException {
		if(obj==null){
			throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_ERROR_TISHI),tishi1));
		}
		if (obj instanceof String) {
			if (!checkLength((String)obj,begin,end)) {
				throw new BusinessException(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_NULL_ERROR_CODE),MessageTool.format(MessageTool.getMessage(ConstantMessageKey.COMMON_PARAM_ERROR_TISHI),tishi));
			}
		}
		return true;
	}
	
	/**
	 * 验证参数是否为空
	 * @param obj  参数名称
	 * @param tishi   参数值
	 * @throws BusinessException
	 */
	public static void checkParamIsNull(Object obj,String tishi) throws BusinessException {
		if(obj==null){
			throw new BusinessException("3", MessageTool.format("参数{0}不能为空", tishi));
		}
		if (obj instanceof String) {
			if ("".equals(((String) obj).trim())) {
				throw new BusinessException("3", MessageTool.format("参数{0}不能为空", tishi));
			}
		}
	}

	/**
	 * 验证是否为手机号码
	 * @param mobile   参数值
	 * @throws BusinessException
	 */
	public static boolean checkParamIsMobile(String mobile) throws BusinessException {
		if(mobile==null){
			throw new BusinessException(MessageTool.getMessage("COMMON_MOBILE_ERROR_CODE"),MessageTool.getMessage("COMMON_MOBILE_ERROR") );
		}
		if (!isMobileNO(mobile)) {
			throw new BusinessException(MessageTool.getMessage("COMMON_MOBILE_ERROR_CODE"),MessageTool.getMessage("COMMON_MOBILE_ERROR") );
		}
		return true;
	}
	/**
	 * 验证用户信息是否合法
	 * @param patient
	 * @throws DiabetesMsException
	 */
	public static MemberPO checkPatient() throws BusinessException {
		/*
		PatientModel patient=(PatientModel) RequestTool.getSession(Constant.REQUEST_SESSION_ATTRIBUTE_PATIENT);
		if(!checkIsNull(patient)){
			throw new DiabetesMsException(ConstantErrorMessage.PATIENT_NULL_ERROR_CODE,ConstantErrorMessage.PATIENT_NULL_ERROR_MESSAGE,"");
		}
		return patient;
		*/
		MemberPO memberPO = SessionTool.getWechatSession();
		if(!checkIsNull(memberPO)){
			throw new DiabetesMsException(ConstantErrorMessage.PATIENT_NULL_ERROR_CODE,ConstantErrorMessage.PATIENT_NULL_ERROR_MESSAGE,"");
		}
		return memberPO;
	}

	public static String tuoMin(String dest,int tbl,int wbl,String rp){
		if(StringUtils.isBlank(dest)){
			return dest;
		}
		if(StringUtils.isBlank(rp)){
			return dest;
		}
		if(tbl<=0 && wbl<=0){
			return dest;
		}
		if(dest.length()<=tbl+wbl){
			String str = "";
			for(int i=0;i<dest.length();i++){
				str+=rp;
			}
			return str;
		}
		int len = dest.length();
		int rpLen = len-tbl-wbl;
		String blt = dest.substring(0,tbl);
		String blw = dest.substring(len-wbl,len);
		String rpStr = "";
		for(int i=0;i<rpLen;i++){
			rpStr+=rp;
		}
		String reStr = blt+rpStr+blw;
		return reStr;
	}


	//两位小数金额校验
	public static boolean judgeTwoDecimal(Object obj) {
		boolean flag = false;
		try {
			if (obj != null) {
				String source = obj.toString();
				// 判断是否是整数或者是携带一位或者两位的小数
				Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
				if (pattern.matcher(source).matches()) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return flag;
	}

}
