package com.comvee.cdms.common.cfg;

import com.comvee.cdms.common.utils.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author 李左河
 *
 */
public class ParamLogConstant {

	/**
	 * 随机血糖下限
	 */
    public static final float RANGE_RANDOM_LOW = 4.4f;
    /**
     * 随机血糖上限
     */
    public static final float RANGE_RANDOM_HIGH = 10;
    /**
     * kf 空腹  tnb糖尿病 :低
     */
    public static float RANGE_KF_TNB_STANDARD_LOW =4.4f;
    /**
     * fkf 非空腹  tnb糖尿病 :低
     */
    public static float RANGE_FKF_TNB_STANDARD_LOW =4.4f;
    /**
     * kf 空腹 tnb糖尿病 :理想
     */
    public static float RANGE_KF_TNB_PERFECT=7.0f;
    /**
     * Fkf 非空腹  ftnb非糖尿病 :理想
     */
    public static float RANGE_FKF_TNB_PERFECT=10.0f;
    /**
     * BMI高值
     */
    public static Float RANGE_BMI_STANDARD_HIGH =23.9f;
    /**
     * BMI低值
     */
	public static Float RANGE_BMI_STANDARD_LOW =18.5f;
	/**
	 * 收缩压下限
	 */
	public static Float RANGE_SYSTOLIC_PRESS_LOW = 90.0f;
	/**
	 * 收缩压上限
	 */
	public static Float RANGE_SYSTOLIC_PRESS_HIGN = 140.0f;
	/**
	 * 舒张压下限
	 */
	public static Float RANGE_DIASTOLIC_PRESS_LOW = 60.0f;
	/**
	 * 舒张压上限
	 */
	public static Float RANGE_DIASTOLIC_PRESS_HIGN = 80.0f;

	/**
	 * 3点
	 */
    public static String PARAM_CODE_3AM = "3am";              
    /**
     * 凌晨 0点
     */
    public static String PARAM_CODE_BEFOREDAWN = "beforedawn";              
    /**
     * 空腹
     */
    public static String PARAM_CODE_BEFOREBREAKFAST = "beforeBreakfast";    
    /**
     * 早餐后
     */
    public static String PARAM_CODE_AFTERBREAKFAST = "afterBreakfast";

    /**
     * 早餐后一小时
     */
    public static String PARAM_CODE_AFTERBREAKFAST_ONE_HOUR = "afterBreakfast1h";

    /**
     * 午餐前
     */
    public static String PARAM_CODE_BEFORELUNCH = "beforeLunch";            
    /**
     * 午餐后
     */
    public static String PARAM_CODE_AFTERLUNCH = "afterLunch";
    /**
     * 午餐后一小时
     */
    public static String PARAM_CODE_AFTERLUNCH_ONE_HOUR = "afterLunch1h";
    /**
     * 晚餐前
     */
    public static String PARAM_CODE_BEFOREDINNER = "beforeDinner";          
    /**
     * 晚餐后
     */
    public static String PARAM_CODE_AFTERDINNER = "afterDinner";
    /**
     * 晚餐后一小时
     */
    public static String PARAM_CODE_AFTERDINNER_ONE_HOUR = "afterDinner1h";
    /**
     *  睡觉前
     */
    public static String PARAM_CODE_BEFORESLEEP = "beforeSleep";           
    /**
     *  随机
     */
    public static String PARAM_CODE_RANDOMTIME = "randomtime";

    private final static int TIME_UNIT = 60;


    /**
     * 获取所有糖尿病code
     */
    public static List<String> getDiabetesCode(){
        List<String> list = new ArrayList<String>();
      //凌晨
        list.add(PARAM_CODE_BEFOREDAWN);
      //3点
        list.add(PARAM_CODE_3AM);
      //早餐前
        list.add(PARAM_CODE_BEFOREBREAKFAST);
      //早餐后
        list.add(PARAM_CODE_AFTERBREAKFAST);
      //午餐前
        list.add(PARAM_CODE_BEFORELUNCH);
      //午餐后
        list.add(PARAM_CODE_AFTERLUNCH);
      //晚餐前
        list.add(PARAM_CODE_BEFOREDINNER);
      //晚餐后
        list.add(PARAM_CODE_AFTERDINNER);
      //睡前
        list.add(PARAM_CODE_BEFORESLEEP);
      //随机
        list.add(PARAM_CODE_RANDOMTIME);
        //早餐后1h
        list.add(PARAM_CODE_AFTERBREAKFAST_ONE_HOUR);
        //午餐后1h
        list.add(PARAM_CODE_AFTERLUNCH_ONE_HOUR);
        //晚餐后 1h
        list.add(PARAM_CODE_AFTERDINNER_ONE_HOUR);
        return list;
    }

    public static String getDateByParamCode(String paramCode){
        String timeStr = "";
        if (PARAM_CODE_BEFOREDAWN.equals(paramCode)){
            timeStr = " 01:30:00";
        }else if (PARAM_CODE_3AM.equals(paramCode)){
            timeStr = " 03:30:00";
        }else if(PARAM_CODE_BEFOREBREAKFAST.equals(paramCode)){
            timeStr = " 06:30:00";
        }else if(PARAM_CODE_AFTERBREAKFAST.equals(paramCode)){
            timeStr = " 09:00:00";
        }else if(PARAM_CODE_BEFORELUNCH.equals(paramCode)){
            timeStr = " 11:00:00";
        }else if(PARAM_CODE_AFTERLUNCH.equals(paramCode)){
            timeStr = " 14:00:00";
        }else if(PARAM_CODE_BEFOREDINNER.equals(paramCode)){
            timeStr = " 17:00:00";
        }else if(PARAM_CODE_AFTERDINNER.equals(paramCode)){
            timeStr = " 19:00:00";
        }else if(PARAM_CODE_BEFORESLEEP.equals(paramCode)){
            timeStr = " 22:30:00";
        }
        return timeStr;
    }

    public static String getParamCodeTimeByTime(String time){
        String paramCodeTime = "";
        String[] timeStr = time.split(" ")[1].split(":");
        int hour = Integer.valueOf(timeStr[0]);
        int min = Integer.valueOf(timeStr[1]);

        int totalTime = 0;
        int num0 = 0;
        int num1 = 1;
        int num2 = 2;
        int num3 = 3;
        int num8 = 8;
        int num10 = 10;
        int num12 = 12;
        int num16 = 16;
        int num18 = 18;
        int num20 = 20;
        int num23 = 23;
        int num59 = 59;

        totalTime = hour*TIME_UNIT + min;
        if (totalTime >= num0 && totalTime<num1*TIME_UNIT ){
            paramCodeTime = PARAM_CODE_BEFOREDAWN;
        }else if (totalTime >=num2 && totalTime<num3*TIME_UNIT ){
            paramCodeTime = PARAM_CODE_3AM;
        }else if (totalTime >= num3*TIME_UNIT && totalTime < num8*TIME_UNIT){
            paramCodeTime = PARAM_CODE_BEFOREBREAKFAST;
        }else if (totalTime >= num8*TIME_UNIT && totalTime < num10*TIME_UNIT){
            paramCodeTime = PARAM_CODE_AFTERBREAKFAST;
        }else if (totalTime >= num10*TIME_UNIT && totalTime < num12*TIME_UNIT){
            paramCodeTime = PARAM_CODE_BEFORELUNCH;
        }else if (totalTime >= num12*TIME_UNIT && totalTime < num16*TIME_UNIT){
            paramCodeTime = PARAM_CODE_AFTERLUNCH;
        }else if (totalTime >= num16*TIME_UNIT && totalTime < num18*TIME_UNIT){
            paramCodeTime = PARAM_CODE_BEFOREDINNER;
        }else if (totalTime >= num18*TIME_UNIT && totalTime < num20*TIME_UNIT){
            paramCodeTime = PARAM_CODE_AFTERDINNER;
        }else if (totalTime >= num20*TIME_UNIT && totalTime < num23*TIME_UNIT+num59){
            paramCodeTime = PARAM_CODE_BEFORESLEEP;
        }
        return paramCodeTime;
    }

    public static String getParamCodeBySystemTime(){
        String paramCode = "";
        long totalTime = 0;
        String nowHourMin = DateHelper.getDate(new Date(),DateHelper.TIME_FORMAT);
        String[] nowHourMinArr = nowHourMin.split(":");
        Long hour = Long.valueOf(nowHourMinArr[0]);
        Long min = Long.valueOf(nowHourMinArr[1]);
        int num0 = 0;
        int num1 = 1;
        int num2 = 2;
        int num3 = 3;
        int num8 = 8;
        int num10 = 10;
        int num12 = 12;
        int num16 = 16;
        int num18 = 18;
        int num20 = 20;
        int num23 = 23;
        int num59 = 59;
        totalTime = hour*TIME_UNIT + min;
        if (totalTime >= num0 && totalTime<num2*TIME_UNIT ){
            paramCode = ParamLogConstant.PARAM_CODE_BEFOREDAWN;
        }else if (totalTime >= num2 && totalTime<num3*TIME_UNIT ){
            paramCode = ParamLogConstant.PARAM_CODE_3AM;
        }else if (totalTime >= num3*TIME_UNIT && totalTime < num8*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST;
        }else if (totalTime >= num8*TIME_UNIT && totalTime < num10*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_AFTERBREAKFAST;
        }else if (totalTime >= num10*TIME_UNIT && totalTime < num12*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_BEFORELUNCH;
        }else if (totalTime >= num12*TIME_UNIT && totalTime < num16*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_AFTERLUNCH;
        }else if (totalTime >= num16*TIME_UNIT && totalTime < num18*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_BEFOREDINNER;
        }else if (totalTime >= num18*TIME_UNIT && totalTime < num20*TIME_UNIT){
            paramCode = ParamLogConstant.PARAM_CODE_AFTERDINNER;
        }else if (totalTime >= num20*TIME_UNIT && totalTime < num23*TIME_UNIT+num59){
            paramCode = ParamLogConstant.PARAM_CODE_BEFORESLEEP;
        }
        return paramCode;
    }



    public static String getParamCodeStr(){
        return "beforedawn,beforeBreakfast,afterBreakfast,beforeLunch,afterLunch,beforeDinner,afterDinner,beforeSleep,randomtime";
    }

    /**
     * 计算平均值
     * @param floats
     * @return
     */
    public static float getAverage(List<Float> floats){
        float sum = 0f;
        for(int i = 0;i < floats.size();i++){
            sum += floats.get(i);
        }
        return (sum / floats.size());
    }

    /**
     * 计算标准差
     * @return
     */
    public static float getStandardDevition(Float valueAverage,List<Float> floats){
        float sum = 0;
        for(int i = 0;i < floats.size();i++){
            sum += (floats.get(i) - valueAverage) * (floats.get(i) -valueAverage);
        }
        if (sum == 0 && (floats.size() - 1) == 0){
            return 0;
        }else{
            return (float) Math.sqrt(sum / (floats.size() - 1));
        }
    }

    public static int getPositionByParamCode(String paramCode)throws Exception{
        int position = 0;
        if (PARAM_CODE_BEFOREDAWN.equals(paramCode)){
            position = 0;
        }else if (PARAM_CODE_3AM.equals(paramCode)){
            position = 1;
        }else if(PARAM_CODE_BEFOREBREAKFAST.equals(paramCode)){
            position = 2;
        }else if(PARAM_CODE_AFTERBREAKFAST.equals(paramCode)){
            position = 3;
        }else if(PARAM_CODE_BEFORELUNCH.equals(paramCode)){
            position = 4;
        }else if(PARAM_CODE_AFTERLUNCH.equals(paramCode)){
            position = 5;
        }else if(PARAM_CODE_BEFOREDINNER.equals(paramCode)){
            position = 6;
        }else if(PARAM_CODE_AFTERDINNER.equals(paramCode)){
            position = 7;
        }else if(PARAM_CODE_BEFORESLEEP.equals(paramCode)){
            position = 8;
        }else if(PARAM_CODE_RANDOMTIME.equals(paramCode)){
            position = 9;
        }
        return position;
    }
    
    public static String getParamcodeByParamcodeNumber(String paramcodeNumber) {
    	String paramcode = "";
    	switch (paramcodeNumber) {
		case Constant.CONST_NUM_00:
			paramcode = PARAM_CODE_BEFOREDAWN;
			break;
		case Constant.CONST_NUM_01:
			paramcode = PARAM_CODE_3AM;
			break;
		case Constant.CONST_NUM_02:
			paramcode = PARAM_CODE_BEFOREBREAKFAST;
			break;
		case Constant.CONST_NUM_03:
			paramcode = PARAM_CODE_AFTERBREAKFAST;
			break;
		case Constant.CONST_NUM_04:
			paramcode = PARAM_CODE_BEFORELUNCH;
			break;
		case Constant.CONST_NUM_05:
			paramcode = PARAM_CODE_AFTERLUNCH;
			break;
		case Constant.CONST_NUM_06:
			paramcode = PARAM_CODE_BEFOREDINNER;
			break;
		case Constant.CONST_NUM_07:
			paramcode = PARAM_CODE_AFTERDINNER;
			break;
		case Constant.CONST_NUM_08:
			paramcode = PARAM_CODE_BEFORESLEEP;
			break;
		case Constant.CONST_NUM_09:
			paramcode = PARAM_CODE_RANDOMTIME;
			break;
		default:
			break;
		}
    	return paramcode;
    }

    /**
     * 计算bmi
     * @param height
     * @param weight
     * @return
     */
    public static String getMemberBmi(double height,double weight){
       double h = height/100;
       double bmi = weight/h/h;
       return String.format("%.1f",bmi);
    }
}
