package com.comvee.cdms.common.utils;

import com.comvee.cdms.common.exception.BusinessException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wyc
 * @date 2019/4/26 17:51
 */
public class PhoneUtil {

    private final static  String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    /**
     * 校验手机号
     * @param phone
     */
    public static void isPhone(String phone) {
        if (phone.length() != 11) {
            throw new BusinessException("手机号应为11位数");
        } else {
            Pattern p = Pattern.compile(PHONE_REGEX);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                throw new BusinessException("请填入正确的手机号");
            }
        }
    }
}
