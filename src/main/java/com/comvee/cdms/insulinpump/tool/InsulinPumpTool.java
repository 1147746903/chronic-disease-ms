package com.comvee.cdms.insulinpump.tool;


import com.comvee.cdms.common.exception.BusinessException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class InsulinPumpTool {

    /**
     * 获取分钟间隔
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getMinuteDiffInSameDay(String startTime ,String endTime){
        String[] startArr = startTime.split(":");
        String[] endArr = endTime.split(":");
        if(startArr.length != 2 || endArr.length != 2){
            throw new BusinessException("错误的开始时间或结束时间");
        }
        return (Integer.parseInt(endArr[0]) * 60 + Integer.parseInt(endArr[1]))
                - (Integer.parseInt(startArr[0]) * 60 + Integer.parseInt(startArr[1]));
    }

    /**
     * 获取加几分钟过后的时间
     * @param time
     * @param addMinutes
     * @return
     */
    public static String getAddMinuteTime(String time ,int addMinutes){
        String[] arr = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY ,Integer.parseInt(arr[0]));
        calendar.set(Calendar.MINUTE ,Integer.parseInt(arr[1]));
        calendar.add(Calendar.MINUTE ,addMinutes);
        String result = new SimpleDateFormat("HH:mm").format(calendar.getTime());
        return result.equals("00:00") ? "24:00" : result;
    }

    /**
     * 除
     * @param sumd
     * @param Divisor
     * @return
     */
    public static Double divide(Double sum ,int divisor){
        return divide(sum ,divisor ,2);
    }

    public static Double divide(Double sum ,int divisor ,int scale){
        if(divisor == 0){
            return 0D;
        }
        return new BigDecimal(sum).divide(new BigDecimal(divisor) ,scale ,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取胰岛素泵每小时基础量表
     * @param total
     * @return
     */
    public static Map<String ,Double> getInsulinPumpEveryHourBasicQuantity(Double total){
        Double avg = total / 24D;
        Map<String ,Double> result = new TreeMap<>();
        result.put("00:00-04:00" ,getBasicQuantityResult(avg * 0.8d));
        result.put("04:00-09:00" ,getBasicQuantityResult(avg * 1.2d));
        result.put("09:00-12:00" ,getBasicQuantityResult(avg * 1.0d));
        result.put("12:00-17:00" ,getBasicQuantityResult(avg + 0.1d));
        result.put("17:00-21:00" ,getBasicQuantityResult(avg * 1.1d));
        result.put("21:00-24:00" ,getBasicQuantityResult(avg * 0.8d));
        return result;
    }

    /**
     * 获取胰岛素泵每小时基础量计算结果
     * @param result
     * @return
     */
    private static Double getBasicQuantityResult(Double result){
        return new BigDecimal(result).setScale(1 ,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
