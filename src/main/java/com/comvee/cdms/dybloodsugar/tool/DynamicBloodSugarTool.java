package com.comvee.cdms.dybloodsugar.tool;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.dybloodsugar.bo.DynamicBloodSugarMAGEItemBO;
import com.comvee.cdms.dybloodsugar.bo.DynamicBloodSugarSummaryAvgAndAreaBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DynamicBloodSugarTool {

    private final static Logger log = LoggerFactory.getLogger(DynamicBloodSugarTool.class);

    /**
     * 计算 平均值
     * @param sum
     * @param divisor
     * @param scale
     * @return
     */
    public static Double avg(Double sum ,Integer divisor ,int scale){
        if(sum == null || divisor == null){
            throw new BusinessException("总和或除数不能为空");
        }
        if(divisor == 0){
            return 0D;
        }
        return new BigDecimal(sum)
                .divide(new BigDecimal(divisor) ,scale ,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 计算 （SDBG）血糖水平标准差
     * @param valueList
     * @param avg
     * @return
     */
    public static Double sdbg(List<Double> valueList ,Double avg){
        if(valueList.size() <= 1){
            return 0D;
        }
        Double sum = 0D;
        for(Double d : valueList){
            sum += Math.pow((d.doubleValue() - avg) ,2);
        }
        Double standardValue = new BigDecimal(sum.toString())
                .divide(new BigDecimal(valueList.size() - 1) ,2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return new BigDecimal(Math.sqrt(standardValue))
                .setScale(1 ,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 计算（CV）血糖变异系数  %
     * @param sdbg
     * @param avg
     * @return
     */
    public static Double cv(Double sdbg ,Double avg){
        return new BigDecimal(sdbg * 100)
                .divide(new BigDecimal(avg) ,2 ,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 计算 预估糖化血红蛋白
     * @param avg
     * @return
     */
    public static Double ghb(Double avg){
        return new BigDecimal(avg + 2.59D)
                .divide(new BigDecimal("1.59") ,1 ,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 计算 时间占比 %
     * @param total
     * @param rate
     * @return
     */
    public static Double tir(Integer rate ,Integer total){
        if(total == 0){
            return 0D;
        }
        return new BigDecimal(rate * 100)
                .divide(new BigDecimal(total) ,2 ,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 动态血糖连续的时间戳间隔 15 分钟 * 60秒 *  1000
     */
    private final static int CONTINUITY_INTERVAL_TIMESTAMP = 900000;

    /**
     * 获取平均血糖波动幅度
     * @param SDBG
     * @param array
     * @return mage
     */
    public static Double getMAGE(Double sdbg,List<DynamicBloodSugarMAGEItemBO> list){
        //少于2个点不计算
        if(list == null || list.size() <= 1){
            return 0D;
        }
        //将数据分段，一个连续时间为一段,只有连续的血糖记录才算波动幅度
        List<List<Double>> valueList = new ArrayList<>();
        List<Double> sectionValueList = null;
        for(int i = 0; i < list.size() ; i ++){
            if(sectionValueList == null){
                sectionValueList = new ArrayList<>();
            }
            sectionValueList.add(list.get(i).getValue());
            //最后一个点判断和前一个点是否是连续的
            if(i == list.size() - 1){
                if((list.get(i).getTimestamp() - list.get(i - 1).getTimestamp()) == CONTINUITY_INTERVAL_TIMESTAMP){
                    valueList.add(sectionValueList);
                }
            }
            //每个点跟后一个点相比，判断是否是连续的,如果不是则截断
            else if((list.get(i + 1).getTimestamp() - list.get(i).getTimestamp())  != CONTINUITY_INTERVAL_TIMESTAMP){
                valueList.add(sectionValueList);
                sectionValueList = new ArrayList<>();
            }
        }
        //有效波动次数
        int effectiveVolatility = 0;
        //差值总和
        double sum = 0D;
        for(List<Double> array : valueList){
            if(array.size() <= 1){
                continue;
            }
            //只有2个点的时候直接计算
            if(array.size() == 2){
                double diff = Math.abs(array.get(1) - array.get(0));
                if(diff >= sdbg){
                    sum ++ ;
                    effectiveVolatility ++;
                    continue;
                }
            }
            //获取曲线方向 1 上升 -1 下降 0 平线
            int curveDirection = getCurveDirection(array.get(0) ,array.get(1));
            //峰值
            double peak = array.get(0);
            //上一个点的值
            double tmp = array.get(1);
            for(int i = 2; i < array.size() ; i ++){
                double currentValue = array.get(i);
                int curveDirectionTmp = getCurveDirection(tmp ,currentValue);
                //曲线方向发生变化
                if(curveDirectionTmp != 0 && curveDirection != curveDirectionTmp){
                    double diff = Math.abs(tmp - peak);
                    peak = tmp;
                    if(diff >= sdbg){
                        sum += diff;
                        effectiveVolatility ++;
                    }
                }
                tmp = currentValue;
                curveDirection = curveDirectionTmp;
                //最后一个点也作为峰值计算
                if(i == (array.size() - 1)){
                    double diff = Math.abs(currentValue - peak);
                    if(diff >= sdbg){
                        sum += diff;
                        effectiveVolatility ++;
                    }
                }
            }
        }
        if(effectiveVolatility == 0){
            return 0D;
        }
        return avg(sum ,effectiveVolatility ,2);
    }

    /**
     * 获取曲线方向
     * @param val1
     * @param val2
     * @return 1 上升 -1 下降 0 平线
     */
    private static int getCurveDirection(double val1 ,double val2){
        if(val1 == val2){
            return 0;
        }
        return (val2 - val1) > 0 ? 1 : -1;
    }

    /**
     * 根据血糖录入的时间计算x轴位置
     * @param date
     * @return
     */
    public static int getLineDataIndex(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 4 + (minute / 15);
    }

    /**
     * 根据动态血糖录入的时间计算x轴位置 3点-3点
     * @param date
     * @return
     */
    public static int getLineDataIndexFor3(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //修改为3点到3点
        int time = (hour * 4 + (minute / 15)) - 12;
        if (time < 0){
            return 96 + time;
        }
        return time;
    }

    /**
     * 计算 分位数
     * @param valueList 数据列表
     * @param position 位置
     * @param divisor 划分为几分位
     * @return
     */
    public static double getQuantile(List<Double> valueList ,int position ,int divisor){
        int intervalSize = valueList.size() - 1;
        double interval = avg(new Double(intervalSize) ,divisor ,2);
        double positionResult = 1 + (interval * position);
        double decimals = decimalsHandler(positionResult);
        Double startIndex = Math.floor(positionResult) - 1;
        Double endIndex = Math.ceil(positionResult) - 1;
        double result = valueList.get(startIndex.intValue()) + (decimals * (valueList.get(endIndex.intValue()) - valueList.get(startIndex.intValue())));
        return new BigDecimal(result + "").setScale(2 ,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取小数值
     * @param d
     * @return
     */
    private static double decimalsHandler(double d){
        String s = String.valueOf(d);
        String[] strings = s.split("\\.");
        if(strings.length > 1){
            return Double.parseDouble("0." + strings[1]);
        }
        return 0D;
    }

    /**
     * 餐前餐后动态血糖平均值以及曲线下面积处理
     * @param useMealTime
     * @param targetMin
     * @param targetMax
     * @param dataList
     * @return
     */
    public static DynamicBloodSugarSummaryAvgAndAreaBO summaryAvgAndAreaHandler(String useMealTime
            ,Double targetMin ,Double targetMax ,List<DYYPBloodSugarPO> dataList){
        dataList.sort(Comparator.comparing(DYYPBloodSugarPO::getRecordDt));

        DynamicBloodSugarSummaryAvgAndAreaBO result = new DynamicBloodSugarSummaryAvgAndAreaBO();
        int index = getClosestUseMealTimeIndex(useMealTime ,dataList);
        if(index < 0){
            return result;
        }
        //开始处理餐前一小时
        beforeMealOneHourHandler(result ,dataList ,targetMin ,targetMax ,index);
        //餐后2小时计算
        afterMealTwoHourHandler(result ,dataList ,targetMin ,targetMax ,index);
        //餐后3小时计算
        afterMealThreeHourHandler(result ,dataList ,targetMin ,targetMax ,index);
        return result;
    }


    /**
     * 获取最接近用餐时间的血糖记录的下标
     * @param useMealTime
     * @param dataList
     * @return
     */
    private static int getClosestUseMealTimeIndex(String useMealTime ,List<DYYPBloodSugarPO> dataList){
        Date useMealDt = castUseMealTime(useMealTime);
        //动态血糖记录时间间隔的一半
        long halfOfRecordTimeInterval = DynamicBloodSugarConstant.RECORD_TIME_INTERVAL_MINUTER * 60 * 1000 / 2;
        //计算出最接近用餐时间的血糖位置
        for(int i = 0 ,size = dataList.size() ; i < size ; i ++){
            if(Math.abs(dataList.get(i).getRecordTime().getTime() - useMealDt.getTime()) <= halfOfRecordTimeInterval){
                return i;
            }
        }
        return -1;
    }

    /**
     * 转化用餐时间
     * @param useMealTime
     * @return
     */
    private static Date castUseMealTime(String useMealTime){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(useMealTime);
        } catch (ParseException e) {
            log.error("用餐日期转换异常" ,e);
            throw new RuntimeException("用餐日期转换异常");
        }
    }

    /**
     * 餐前一小时处理
     * @param bo
     * @param dataList
     * @param targetMin
     * @param targetMax
     * @param index
     */
    private static void beforeMealOneHourHandler(DynamicBloodSugarSummaryAvgAndAreaBO bo ,List<DYYPBloodSugarPO> dataList
            ,Double targetMin ,Double targetMax ,int index){
        int offset = - 4;
        if(!checkDataAccordWithAreaCalculate(dataList ,index ,offset)){
            return;
        }
        double areaSum = 0d;
        double valueSum = 0d;
        DYYPBloodSugarPO value = null;
        DYYPBloodSugarPO nextValue = null;
        int count = 0;
        for(int i = index ,limit = index + offset; i >= limit ; i --){
            value = dataList.get(i);
            valueSum += value.getValue().doubleValue();
            count ++;
            if(i != limit){
                nextValue = dataList.get(i - 1);
                if(nextValue != null){
                    areaSum += calculateUnderCurveArea(value.getValue().doubleValue() ,nextValue.getValue().doubleValue() ,targetMin ,targetMax);
                }
            }
        }
        bo.setBeforeMealArea1H(new BigDecimal(areaSum).setScale(2 ,BigDecimal.ROUND_HALF_UP).doubleValue());
        bo.setBeforeMealAvg1H(avg(valueSum ,count ,2));
    }

    /**
     * 餐后2小时处理
     * @param bo
     * @param dataList
     * @param targetMin
     * @param targetMax
     * @param index
     */
    private static void afterMealTwoHourHandler(DynamicBloodSugarSummaryAvgAndAreaBO bo ,List<DYYPBloodSugarPO> dataList
            ,Double targetMin ,Double targetMax ,int index){
        int offset = 8;
        if(!checkDataAccordWithAreaCalculate(dataList ,index ,offset)){
            return;
        }
        double areaSum = 0d;
        double valueSum = 0d;
        DYYPBloodSugarPO value = null;
        DYYPBloodSugarPO nextValue = null;
        int count = 0;
        for(int i = index ,limit = index + offset; i <= limit; i ++){
            value = dataList.get(i);
            valueSum += value.getValue().doubleValue();
            count ++;
            if(i != limit){
                nextValue = dataList.get(i + 1);
                if(nextValue != null){
                    areaSum += calculateUnderCurveArea(value.getValue().doubleValue() ,nextValue.getValue().doubleValue() ,targetMin ,targetMax);
                }
            }
        }
        bo.setAfterMealArea2H(new BigDecimal(areaSum).setScale(2 ,BigDecimal.ROUND_HALF_UP).doubleValue());
        bo.setAfterMealAvg2H(avg(valueSum ,count ,2));
    }

    /**
     * 餐后3小时处理
     * @param bo
     * @param dataList
     * @param targetMin
     * @param targetMax
     * @param index
     */
    private static void afterMealThreeHourHandler(DynamicBloodSugarSummaryAvgAndAreaBO bo ,List<DYYPBloodSugarPO> dataList
            ,Double targetMin ,Double targetMax ,int index){
        int offset = 12;
        if(!checkDataAccordWithAreaCalculate(dataList ,index ,offset)){
            return;
        }
        double areaSum = 0d;
        double valueSum = 0d;
        DYYPBloodSugarPO value = null;
        DYYPBloodSugarPO nextValue = null;
        int count = 0;
        for(int i = index ,limit = index + offset; i <= limit ; i ++){
            value = dataList.get(i);
            valueSum += value.getValue().doubleValue();
            count ++;
            if(limit != i){
                nextValue = dataList.get(i + 1);
                if(nextValue != null){
                    areaSum += calculateUnderCurveArea(value.getValue().doubleValue() ,nextValue.getValue().doubleValue() ,targetMin ,targetMax);
                }
            }
        }
        bo.setAfterMealArea3H(new BigDecimal(areaSum).setScale(2 ,BigDecimal.ROUND_HALF_UP).doubleValue());
        bo.setAfterMealAvg3H(avg(valueSum ,count ,2));
    }
    /**
     * 判断数据是否符合面积计算的要求
     * @param dataList
     * @param useMealIndex
     * @param offset
     * @return
     */
    private static boolean checkDataAccordWithAreaCalculate(List<DYYPBloodSugarPO> dataList ,int useMealIndex ,int offset){
        int targetIndex = useMealIndex + offset;
        if(targetIndex < 0){
            return false;
        }
        if(targetIndex >= dataList.size()){
            return false;
        }
        DYYPBloodSugarPO targetBloodSugar = dataList.get(targetIndex);
        if(targetBloodSugar == null){
            return false;
        }
        DYYPBloodSugarPO useMealBloodSugar = dataList.get(useMealIndex);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(useMealBloodSugar.getRecordTime());
        calendar.add(Calendar.MINUTE ,offset * 15);
        return targetBloodSugar.getRecordTime().equals(calendar.getTime());
    }

    /**
     * 计算曲线下面积
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static Double calculateUnderCurveArea(double value1 ,double value2 ,double targetMin ,double targetMax){
        AreaType areaType = getAreaType(value1, value2, targetMin, targetMax);
        if(areaType == null){
            return 0D;
        }
        Double area = 0D;
        switch (areaType){
            case SQUARE:
                area = squareAreaCalculation(value1, value2, targetMin, targetMax);
                break;
            case TRAPEZOID:
                area = trapezoidAreaCalculation(value1, value2, targetMin, targetMax);
                break;
            case ONE_TRIANGLE:
                area = oneTriangleAreaCalculation(value1, value2, targetMin, targetMax);
                break;
            case TWO_TRIANGLE:
                area = twoTriangleCalculation(value1, value2, targetMin, targetMax);
                break;
            default:
                break;
        }
        return area;
    }

    /**
     * 获取面积图形类型
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static AreaType getAreaType(double value1 ,double value2 ,double targetMin ,double targetMax){
        if((value1 > targetMax && value2 > targetMax) || (value1 < targetMin && value2 < targetMin)){
            if(value1 == value2){
                return AreaType.SQUARE;
            }else{
                return AreaType.TRAPEZOID;
            }
        }else if(value1 > targetMax){
            if(value2 <= targetMax && value2 >= targetMin){
                return AreaType.ONE_TRIANGLE;
            }else if(value2 < targetMin){
                return AreaType.TWO_TRIANGLE;
            }
        }else if(value1 < targetMin){
            if(value2 >= targetMin && value2 < targetMax){
                return AreaType.ONE_TRIANGLE;
            }else if(value2 > targetMax){
                return AreaType.TWO_TRIANGLE;
            }
        }else if(value1 < targetMax){
            if(value2 > targetMax){
                return AreaType.ONE_TRIANGLE;
            }
        }
        return null;
    }

    /**
     * 计算曲线面积最小单位为小时，动态血糖监测间隔15分钟，所以面积计算的基础长度是0.25d
     */
    private static final double BASE_SIZE = 0.25d;

    /**
     * 方形面积计算
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static Double squareAreaCalculation(double value1 ,double value2 ,double targetMin ,double targetMax){
        if(value1 > targetMax){
            return (value1 - targetMax) * BASE_SIZE;
        }else{
            return (targetMin - value1) * BASE_SIZE;
        }
    }

    /**
     * 梯形面积计算
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static Double trapezoidAreaCalculation(double value1 ,double value2 ,double targetMin ,double targetMax){
        if(value1 > targetMax){
            return (value1 - targetMax) * (value2 - targetMax) * BASE_SIZE / 2;
        }else{
            return (targetMin - value1) * (targetMin - value2) * BASE_SIZE / 2;
        }
    }

    /**
     * 一个三角形面积计算
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static Double oneTriangleAreaCalculation(double value1 ,double value2 ,double targetMin ,double targetMax){
        double height = 0D;
        if(value1 > targetMax){
            height = value1 - targetMax;
        }else if(value2 > targetMax){
            height = value2 - targetMax;
        }else if(value1 < targetMin){
            height = targetMin - value1;
        }else if(value2 < targetMin){
            height = targetMin - value2;
        }
        return height / Math.abs(value1 - value2) * BASE_SIZE * height / 2;
    }

    /**
     * 2个三角形面积计算
     * @param value1
     * @param value2
     * @param targetMin
     * @param targetMax
     * @return
     */
    private static Double twoTriangleCalculation(double value1 ,double value2 ,double targetMin ,double targetMax){
        double height1 = 0D;
        double height2 = 0D;
        if(value1 > targetMax){
            height1 = value1 - targetMax;
            height2 = targetMin - value2;
        }else{
            height1 = targetMin - value1;
            height2 = value2 - targetMax;
        }
        double range = Math.abs(value1 - value2);
        double area1 = height1 / range * BASE_SIZE * height1 / 2;
        double area2 = height2 / range * BASE_SIZE * height2 / 2;
        return area1 + area2;
    }

    enum AreaType{
        /**
         * 面积类型 梯形 ，方形 ， 一个三角形， 两个三角形
         */
        TRAPEZOID,
        SQUARE,
        ONE_TRIANGLE,
        TWO_TRIANGLE;
    }
}
