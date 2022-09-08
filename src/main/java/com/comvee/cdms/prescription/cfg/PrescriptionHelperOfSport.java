/**
 * @File name:   EohHelperOfSport.java    管理处方运动逻辑处理
 * @Create on:   2017年3月10日
 * @Author   :  占铃树
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/
package com.comvee.cdms.prescription.cfg;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.bo.BmiRangeSetBO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 占铃树
 */
public class PrescriptionHelperOfSport {
    /**
     * 禁忌症运动建议(非妊娠)
     */
    public final static String FORBID_SPORT_ADVICE ="建议您暂时不宜运动，建议待病情稳定后，再咨询专科医师后再选择适宜运动。";
    /**
     * 相对禁忌症运动建议(妊娠)
     */
    public final static String RSXD_FORBID_SPORT_ADVICE ="建议您需请专业的内分泌、产科医生或运动治疗师评估整体综合情况，在这些情况稳定后，在选择科学适宜、个体化的运动方法。";
    /**
     * 绝对禁忌症运动建议(妊娠) 有禁忌症
     */
    public final static String RSJD_FORBID_SPORT_ADVICE ="建议您待病情控制稳定后，咨询教育者或医生再逐步恢复运动。";

    /**
     * 高血压处方 绝对禁忌症 输出
     */
    public final static String HYP_FORBID_SPORT_ADVICE = "由于您目前存在禁忌症类型1、禁忌症类型2、禁忌症类型3，暂时不宜进行运动。建议您待病情控制稳定后，咨询专科医生再逐步恢复运动。";


    /**
     * 获取运动输出内容
     * 
     * @param bmi
     * @param bsSport
     *            是否有运动习惯 有1 无0
     * @param bsSportRequency
     *            运动频率 <3次/周 YDPL03;3-4次/周 YDPL05
     * @param bsSportLong
     *            运动时长 20~30 分钟/次 BSYDSJ04;<20 分钟/次BSYDSJ01
     * @param bsBgSportTime
     *            运动时机 空腹 SJ01
     * @param healthRangeSet
     *            控制目标
     * 
     * @return
     * @author 占铃树
     * @date 2017年3月10日
     */
    public static List<Map<String, Object>> getSportOutPut(String bmi,
            String bsSport, String bsSportRequency, String bsSportLong,
            String bsBgSportTime, BmiRangeSetBO healthRangeSet,Integer eohType) throws Exception {

        List<String> problem = new ArrayList<String>();
        List<String> suggestion = new ArrayList<String>();
        List<String> description = new ArrayList<String>();
        List<String> code = new ArrayList<String>();

        // 一天摄入总热量逻辑处理
        List<Map<String, Object>> dataList = sportHeatHandler(problem, suggestion, description, bsSport, bsSportRequency, bmi, healthRangeSet, bsSportLong, bsBgSportTime, code,eohType);
        
        return dataList;
    }

    /**
     * 获取高血压 运动问题建议输出
     * @param bsSport 是否有运动习惯
     * @param bsSportRequency 运动频率
     * @param bsSportLong 运动时长
     * @param bsBgSportTime 运动时机
     * @return
     */
    public static List<Map<String, Object>> getHypSportOutPut(String bsSport ,String bsSportRequency ,String bsSportLong ,String bsBgSportTime){
        List<Map<String, Object>>  list = new ArrayList<>();
        if(!"1".equals(bsSport)){
            return list;
        }
        if(!StringUtils.isBlank(bsSportRequency)){
            /**
             * 运动频率	<3次/周
             */
            if("YDPL03".equals(bsSportRequency)){
                Map<String, Object> map = new HashMap<>();
                map.put("problem" , "运动频率低（目前运动频率<3次/周）");
                map.put("suggestion" , "建议您每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天");
                map.put("description" , "每周运动频率达到5天以上，两次运动的间隔时间不超过2天");
                list.add(map);
            }
            /**
             * 运动频率	3~4次/周
             */
            else if("YDPL05".equals(bsSportRequency)){
                Map<String, Object> map = new HashMap<>();
                map.put("problem" , "运动频率较低（目前运动频率3~4次/周）");
                map.put("suggestion" , "建议您每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天");
                map.put("description" , "每周运动频率达到5天以上，两次运动的间隔时间不超过2天");
                list.add(map);
            }
        }
        if(!StringUtils.isBlank(bsSportLong)){
            /**
             * 运动时长	<20 分钟/次
             */
            if("BSYDSJ01".equals(bsSportLong)){
                Map<String, Object> map = new HashMap<>();
                map.put("problem" , "运动时长不足（目前运动时长<20 分钟/次）");
                map.put("suggestion" , "建议每次运动的时长增加15分钟，直到每次运动30-60分钟，或增加运动频次，保证一周进行至少150分钟的主动运动");
                map.put("description" , "保证一周进行至少150分钟的主动运动");
                list.add(map);
            }
            /**
             * 运动时长	20~30 分钟/次
             */
            else if("BSYDSJ04".equals(bsSportLong)){
                Map<String, Object> map = new HashMap<>();
                map.put("problem" , "运动时长不足（目前运动时长20~30 分钟/次）");
                map.put("suggestion" , "建议每次运动的时长增加15分钟，直到每次运动30-60分钟，或增加运动频次，保证一周进行至少150分钟的主动运动");
                map.put("description" , "保证一周进行至少150分钟的主动运动");
                list.add(map);
            }
        }
        if(!StringUtils.isBlank(bsBgSportTime)){
            /**
             * 运动时机	空腹
             */
            if("SJ01".equals(bsBgSportTime)){
                Map<String, Object> map = new HashMap<>();
                map.put("problem" , "空腹运动（运动习惯：空腹运动）");
                map.put("suggestion" , "避免空腹运动，运动时间最好安排在饭后30-60分钟进行，运动效果最好");
                map.put("description" , "避免空腹运动");
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 运动逻辑code常量
     */
    private final static String YDPL03 = "YDPL03";
    private final static String YDPL05 = "YDPL05";
    private final static String BSYDSJ01 = "BSYDSJ01";
    private final static String BSYDSJ04 = "BSYDSJ04";
    private final static String SJ01 = "SJ01";
    //妊娠
    private final static String RSYDPL01 = "RSYDPL01";  //<3次每周
    private final static String RSYDPL02 = "RSYDPL02";  //>=3次每周
    private final static String RSBSYDSJ01 = "RSBSYDSJ01"; //15分钟
    private final static String RSBSYDSJ02 = "RSBSYDSJ02";  //15`30分钟
    private final static String RSBSYDSJ03 = "RSBSYDSJ03";  //30`45分钟

    /**
     * 运动逻辑输出
     *
     * @param problem
     * @param suggestion
     * @param description
     * @param bsSport 是否有运动习惯 有1 无0
     * @param bsSportRequency  运动频率 <3次/周 YDPL03 ; 3-4次/周 YDPL05
     * @param bmi
     * @param healthRangeSet
     * @param bsSportLong 运动时长 20~30 分钟/次 BSYDSJ04;<20 分钟/次BSYDSJ01
     * @author 占铃树
     * @param bsBgSportTime 运动时机 空腹 SJ01
     * @param code 
     * @return
     * @date 2017年3月10日
     */
    private static List<Map<String, Object>> sportHeatHandler(List<String> problem,
                                                              List<String> suggestion, List<String> description, String bsSport,
                                                              String bsSportRequency, String bmi,
                                                              BmiRangeSetBO healthRangeSet, String bsSportLong, String bsBgSportTime, List<String> code,Integer eohType) throws Exception {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        if (eohType == 1){  //妊娠
            /**
             *  0
             *判断逻辑：是否有运动习惯:无
             *
             * 问题0：缺乏主动运动
             * 描述0：无运动习惯
             */
            if(Constant.CONST_NUM_00.equals(bsSport)) {
                Map<String, Object> data = new HashMap<String, Object>(4);
                data.put("problem", "缺乏主动运动");
                data.put("description", "无运动习惯");
                data.put("suggestion", "建议您选择适合、喜欢的运动，养成自主规律的运动习惯，可以降低胰岛素抵抗使血糖水平趋于正常，有利于胎儿的发育。");
                data.put("code", "1001");
                dataList.add(data);

                problem.add("缺乏主动运动");
                description.add("无运动习惯");
                suggestion.add("建议您选择适合、喜欢的运动，养成自主规律的运动习惯，可以降低胰岛素抵抗使血糖水平趋于正常，有利于胎儿的发育。");
                code.add("1001");
            }else{
                /**
                 * 1
                 *判断逻辑：是否有运动习惯:有
                 *运动频率   <3次/周
                 * 问题0：运动频率低
                 * 描述0：目前运动频率<3次/周
                 */
                if(Constant.CONST_NUM_01.equals(bsSport) && RSYDPL01.equalsIgnoreCase(bsSportRequency)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率<3次/周");
                    data.put("suggestion", "建议以循序渐进的方式进行，开始时一般每周3次，逐步增加到每周4-5次，每次30-45 分钟，两次运动的间隔时间不超过2天");
                    data.put("code", "1002");
                    dataList.add(data);

                    problem.add("运动频率低");
                    description.add("目前运动频率<3次/周");
                    suggestion.add("建议以循序渐进的方式进行，开始时一般每周3次，逐步增加到每周4-5次，每次30-45 分钟，两次运动的间隔时间不超过2天");
                    code.add("1002");
                }
                /**
                 *  2
                 *判断逻辑：是否有运动习惯:有
                 *运动时长   <15分钟/次
                 * 问题0：运动时长不足
                 * 描述0：目前运动时长<15 分钟/次
                 */
                 if(Constant.CONST_NUM_01.equals(bsSport) && RSBSYDSJ01.equalsIgnoreCase(bsSportLong)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动时长不足");
                    data.put("description", "目前运动时长<15 分钟/次");
                    data.put("suggestion", "建议运动以循序渐进的方式进行，保持每次运动的时长15分钟，并逐渐增加到每次运动30-45分钟，或增加运动频次，保证一周总时长至少150分钟左右的运动");
                    data.put("code", "1003");
                    dataList.add(data);

                    problem.add("运动时长不足");
                    description.add("目前运动时长<15 分钟/次");
                    suggestion.add("建议运动以循序渐进的方式进行，保持每次运动的时长15分钟，并逐渐增加到每次运动30-45分钟，或增加运动频次，保证一周总时长至少150分钟左右的运动");
                    code.add("1003");
                }
                /**
                 *  3
                 *判断逻辑：是否有运动习惯:有
                 *运动时长   15~30 分钟/次
                 * 问题0：运动时长稍少
                 * 描述0：目前运动时长15-30分钟/次
                 */
                 if(Constant.CONST_NUM_01.equals(bsSport) && RSBSYDSJ02.equalsIgnoreCase(bsSportLong)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动时长稍少");
                    data.put("description", "目前运动时长15-30分钟/次");
                    data.put("suggestion", "建议以循序渐进的方式进行，每次运动时长适当增加，直到每次运动30-45分钟，或增加运动频次，保证一周总时长至少150分钟左右的运动");
                    data.put("code", "1004");
                    dataList.add(data);

                    problem.add("运动时长稍少");
                    description.add("目前运动时长15-30分钟/次");
                    suggestion.add("建议以循序渐进的方式进行，每次运动时长适当增加，直到每次运动30-45分钟，或增加运动频次，保证一周总时长至少150分钟左右的运动");
                    code.add("1004");
                }
                /**
                 *  4
                 *判断逻辑：是否有运动习惯:有
                 *运动时机   空腹
                 * 问题0：空腹运动
                 * 描述0：
                 */
                if(Constant.CONST_NUM_01.equals(bsSport) && SJ01.equalsIgnoreCase(bsBgSportTime)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "空腹运动");
                    data.put("description", "");
                    data.put("suggestion", "避免空腹运动，运动时间最好安排在饭后30-60分钟，因为此时血糖浓度达到高峰，运动效果最好");
                    data.put("code", "1005");
                    dataList.add(data);

                    problem.add("空腹运动");
                    description.add("");
                    suggestion.add("避免空腹运动，运动时间最好安排在饭后30-60分钟，因为此时血糖浓度达到高峰，运动效果最好");
                    code.add("1005");
                }
            }

        }else{  //非妊娠

            double dNum27d9 = 27.9d;
            /**
             * 逻辑0:
             * 判断逻辑：是否有运动习惯:无
             *
             * 问题0：缺乏主动运动
             * 描述0：无运动习惯
             * 建议0：建议您除日常活动（如家务活）之外，增加主动运动，选择适合、喜欢的运动，养成自主规律的运动习惯
             */
            if(Constant.CONST_NUM_00.equals(bsSport)) {
                Map<String, Object> data = new HashMap<String, Object>(4);
                data.put("problem", "缺乏主动运动");
                data.put("description", "无运动习惯");
                data.put("suggestion", "建议您除日常活动（如家务活）之外，增加主动运动，选择适合、喜欢的运动，养成自主规律的运动习惯");
                data.put("code", "1001");
                dataList.add(data);

                problem.add("缺乏主动运动");
                description.add("无运动习惯");
                suggestion.add("建议您除日常活动（如家务活）之外，增加主动运动，选择适合、喜欢的运动，养成自主规律的运动习惯");
                code.add("1001");
            }
            else {
                /**
                 * 逻辑1:
                 * 判断逻辑：是否有运动习惯:有;运动频率:<3次/周;BMI:18.5=<BMI=<23.9
                 *
                 * 问题1：运动频率低
                 * 描述1：目前运动频率<3次/周
                 * 建议1：由于您目前体重正常，建议您维持现在的体重，并每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。
                 */
                Double bmiD = StringUtils.converParamToDouble(bmi);
                Double lowBmi = StringUtils.converParamToDouble(healthRangeSet.getLowBmi());
                Double highBmi = StringUtils.converParamToDouble(healthRangeSet.getHighBmi());
                if(Constant.CONST_NUM_01.equals(bsSport) && YDPL03.equalsIgnoreCase(bsSportRequency) && bmiD != null && lowBmi !=null && highBmi != null && bmiD >= lowBmi && bmiD <= highBmi) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率<3次/周");
                    data.put("suggestion", "由于您目前体重正常，建议您维持现在的体重，并每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
                    data.put("code", "1002");
                    dataList.add(data);

                    problem.add("运动频率低");
                    description.add("目前运动频率<3次/周");
                    suggestion.add("由于您目前体重正常，建议您维持现在的体重，并每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
                    code.add("1002");
                }
                /**
                 * 逻辑2:
                 * 判断逻辑：是否有运动习惯 有；运动频率    <3次/周；BMI 24.0=<BMI=<27.9

                 *
                 * 问题2：运动频率低
                 * 描述2：目前运动频率<3次/周
                 * 建议2：由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。
                 */
                else if(Constant.CONST_NUM_01.equals(bsSport) && YDPL03.equalsIgnoreCase(bsSportRequency) && bmiD != null && highBmi != null && bmiD > highBmi && bmiD <= dNum27d9) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率<3次/周");
                    data.put("suggestion", "由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    data.put("code", "1003");
                    dataList.add(data);

                    problem.add("运动频率低");
                    description.add("目前运动频率<3次/周");
                    suggestion.add("由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    code.add("1003");
                }
                /**
                 * 逻辑3:
                 * 判断逻辑：是否有运动习惯 有；运动频率    <3次/周；BMI BMI>=28
                 *
                 * 问题3：运动频率低
                 * 描述3：目前运动频率<3次/周
                 * 建议3：由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。
                 */
                else if(Constant.CONST_NUM_01.equals(bsSport) && YDPL03.equalsIgnoreCase(bsSportRequency) && bmiD != null && bmiD > dNum27d9) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率<3次/周");
                    data.put("suggestion", "由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    data.put("code", "1004");
                    dataList.add(data);

                    problem.add("运动频率低");
                    description.add("目前运动频率<3次/周");
                    suggestion.add("由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加2天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    code.add("1004");
                }
                /**
                 * 逻辑4:
                 * 判断逻辑：是否有运动习惯 有；运动频率    3~4次/周；BMI 18.5=<BMI=<23.9
                 *
                 * 问题4：运动频率较低
                 * 描述4：目前运动频率3~4次/周
                 * 建议4：由于您目前体重正常，建议您维持现在的体重，并每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。
                 */
                else if(Constant.CONST_NUM_01.equals(bsSport) && YDPL05.equalsIgnoreCase(bsSportRequency) && bmiD != null && lowBmi !=null && highBmi != null && bmiD >= lowBmi && bmiD <= highBmi) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率3~4次/周");
                    data.put("suggestion", "由于您目前体重正常，建议您维持现在的体重，并每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
                    data.put("code", "1005");
                    dataList.add(data);

                    problem.add("运动频率较低");
                    description.add("目前运动频率3~4次/周");
                    suggestion.add("由于您目前体重正常，建议您维持现在的体重，并每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
                    code.add("1005");
                }
                /**
                 * 逻辑5:
                 * 判断逻辑：是否有运动习惯 有；运动频率    3~4次/周；BMI 24.0=<BMI=<27.9
                 *
                 * 问题5：运动频率较低
                 * 描述5：目前运动频率3~4次/周
                 * 建议5：由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。
                 */
                else if(Constant.CONST_NUM_01.equals(bsSport) && YDPL05.equalsIgnoreCase(bsSportRequency) && bmiD != null && highBmi != null && bmiD > highBmi && bmiD <= dNum27d9) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率3~4次/周");
                    data.put("suggestion", "由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    data.put("code", "1006");
                    dataList.add(data);

                    problem.add("运动频率较低");
                    description.add("目前运动频率3~4次/周");
                    suggestion.add("由于您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    code.add("1006");
                }
                /**
                 * 逻辑6:
                 * 判断逻辑：是否有运动习惯 有；运动频率    3~4次/周；BMI BMI>=28
                 *
                 * 问题6：运动频率较低
                 * 描述6：目前运动频率3~4次/周
                 * 建议6：由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。
                 */
                else if(Constant.CONST_NUM_01.equals(bsSport) && YDPL05.equalsIgnoreCase(bsSportRequency) && bmiD != null && bmiD > dNum27d9) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动频率低");
                    data.put("description", "目前运动频率3~4次/周");
                    data.put("suggestion", "由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    data.put("code", "1007");
                    dataList.add(data);

                    problem.add("运动频率较低");
                    description.add("目前运动频率3~4次/周");
                    suggestion.add("由于您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，建议您每周增加1天的运动，每天进行中等强度运动1~1.5小时，或低等强度运动2~3小时，直到每周运动频率达到5天以上，两次运动的间隔时间不超过2天。");
                    code.add("1007");
                }

                /**
                 * 逻辑7:
                 * 判断逻辑：运动时长    <20 分钟/次
                 *
                 * 问题7：运动时长不足
                 * 描述7：目前运动时长<20 分钟/次
                 * 建议7：建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动
                 */
                if(BSYDSJ01.equalsIgnoreCase(bsSportLong)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动时长不足");
                    data.put("description", "目前运动时长<20 分钟/次");
                    data.put("suggestion", "建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
                    data.put("code", "1008");
                    dataList.add(data);

                    problem.add("运动时长不足");
                    description.add("目前运动时长<20 分钟/次");
                    suggestion.add("建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
                    code.add("1008");
                }
                /**
                 * 逻辑8:
                 * 判断逻辑：运动时长    20~30 分钟/次
                 *
                 * 问题8：运动时长不足
                 * 描述8：目前运动时长20~30 分钟/次
                 * 建议8：建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动
                 */
                else if(BSYDSJ04.equalsIgnoreCase(bsSportLong)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "运动时长不足");
                    data.put("description", "目前运动时长20~30 分钟/次");
                    data.put("suggestion", "建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
                    data.put("code", "1009");
                    dataList.add(data);

                    problem.add("运动时长不足");
                    description.add("目前运动时长20~30 分钟/次");
                    suggestion.add("建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
                    code.add("1009");
                }

                /**
                 * 逻辑9:
                 * 判断逻辑：运动时机 空腹
                 *
                 * 问题9：空腹运动
                 * 描述9：运动习惯：空腹运动
                 * 建议9：避免空腹运动，运动时间最好安排在饭后30-60分钟，因为此时血糖浓度达到高峰，运动效果最好
                 */
                if(SJ01.equalsIgnoreCase(bsBgSportTime)) {
                    Map<String, Object> data = new HashMap<String, Object>(4);
                    data.put("problem", "空腹运动");
                    data.put("description", "");
                    data.put("suggestion", "避免空腹运动，运动时间最好安排在饭后30-60分钟，因为此时血糖浓度达到高峰，运动效果最好");
                    data.put("code", "1010");
                    dataList.add(data);

                    problem.add("空腹运动");
                    description.add("");
                    suggestion.add("避免空腹运动，运动时间最好安排在饭后30-60分钟，因为此时血糖浓度达到高峰，运动效果最好");
                    code.add("1010");
                }
            }


        }


        return dataList;
    }

    /**
     * 运动规则
     *
     * @param bmi
     * @param weight
     * @param healthRangeSet
     * @return
     * @author 占铃树
     * @throws HealthException 
     * @date 2017年3月14日
     */
    public static String getSportRule(String bmi, String weight,
            BmiRangeSetBO healthRangeSet,String gestationalWeeks,Integer eohType) throws Exception {
        String sportRule = "";
        if (eohType == 1){  //妊娠
            sportRule = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运动疗法是妊娠期糖尿病的治疗措施之一，可降低妊娠期基础的胰岛素抵抗，对维持血糖水平的稳定、减少降糖药物的使用等有重要作用。";
            String suggestionOfBmi = getSuggestionOfBmi(StringUtils.converParamToDouble(bmi), null, null, weight,gestationalWeeks,eohType);
            if(!StringUtils.isBlank(suggestionOfBmi)) {
                sportRule = sportRule.concat("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + suggestionOfBmi);
            }
        }else{  //非妊娠
            sportRule = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;糖尿病不仅是慢性疾病，同时也是进展性疾病。运动能改善糖尿病发病的危险因素，同样也能改善葡萄糖耐量减低和空腹血糖受损状态，并可改善2型糖尿病个体胰岛素敏感性，从而预防和治疗糖尿病并发症。";

            Double lowBmi = StringUtils.converParamToDouble(healthRangeSet.getLowBmi());
            Double highBmi = StringUtils.converParamToDouble(healthRangeSet.getHighBmi());
            String suggestionOfBmi = getSuggestionOfBmi(StringUtils.converParamToDouble(bmi), lowBmi, highBmi, weight,gestationalWeeks,eohType);
            if(!StringUtils.isBlank(suggestionOfBmi)) {
                sportRule = sportRule.concat("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + suggestionOfBmi);
            }
        }


        return sportRule;
    }
    
    public static String getSuggestionOfBmi(Double bmi, Double lowBmi, Double highBmi, String weight,String gestationalWeeks,Integer eohType) throws Exception {
        String suggestion = null;
        if (eohType == 1){  //妊娠
            Integer week = Integer.parseInt(gestationalWeeks);
            double num18d5 = 18.5;
            double num24d9 = 24.9;
            double num25d0 = 25.0;
            double num29d9 = 29.9;
            double num30d0 = 30.0;
            if (bmi < num18d5){
                if (week >= 0 && week <=12){ //早期
                    suggestion = "您孕前体重偏低，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕早期阶段让体重增加约0.5-2Kg，有助于优生优育和血糖的控制。";
                }else if (week >=13 && week <= 27){  //中期
                    suggestion = "您孕前体重偏低，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕中期阶段让每周体重增长0.44~0.58Kg，整个孕期体重增长在12.5~18Kg范围，有助于优生优育和血糖的控制。";
                }else if (week >= 28){ //后期
                    suggestion = "您孕前体重偏低，建议在专科医生指导下，保持科学的运动治疗方案，合理搭配饮食，在目前孕晚期阶段让每周体重增长0.44~0.58Kg，整个孕期体重增长在12.5~18Kg范围，有助于优生优育和血糖的控制。";
                }
            }else if (bmi >= num18d5 && bmi <= num24d9){
                if (week >= 0 && week <=12){ //早期
                    suggestion = "您孕前体重正常，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕早期阶段让体重增加约0.5-2Kg，有助于优生优育和血糖的控制。";
                }else if (week >=13 && week <= 27){  //中期
                    suggestion = "您孕前体重正常，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕中期阶段让每周体重增长0.35~0.50Kg，整个孕期体重增长在11.5~16Kg范围，有助于优生优育和血糖的控制。";
                }else if (week >= 28){ //后期
                    suggestion = "您孕前体重正常，建议在专科医生指导下进行每周运动总时长不少于150分钟的运动计划，并合理搭配饮食，把孕期体重增长控制在12.5~18Kg范围，约每周体重增长0.44~0.58Kg，有助于优生优育和血糖的控制。";
                }
            }else if (bmi >= num25d0 && bmi <= num29d9){
                if (week >= 0 && week <=12){ //早期
                    suggestion = "您孕前体重超重，建议保持科学的运动方案，合理搭配饮食，在目前孕早期阶段让体重增加约0.5-2Kg，有助于优生优育和血糖的控制。";
                }else if (week >=13 && week <= 27){  //中期
                    suggestion = "您孕前体重超重，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕中期阶段让每周体重增长0.23~0.33Kg，整个孕期体重增长在7~11.5Kg范围，有助于优生优育和血糖的控制。";
                }else if (week >= 28){ //后期
                    suggestion = "您孕前体重超重，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕晚期阶段让每周体重增长0.23~0.33Kg，整个孕期体重增长在7~11.5Kg范围，有助于优生优育和血糖的控制。";
                }
            }else if (bmi >= num30d0){
                if (week >= 0 && week <=12){ //早期
                    suggestion = "您孕前体重肥胖，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕早期阶段让体重增加约0.5-2Kg，有助于优生优育和血糖的控制。";
                }else if (week >=13 && week <= 27){  //中期
                    suggestion = "您孕前体重肥胖，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕中期阶段让每周体重增长0.17~0.27Kg，整个孕期体重增长在5~9Kg范围，有助于优生优育和血糖的控制。";
                }else if (week >= 28){ //后期
                    suggestion = "您孕前体重肥胖，建议在专科医生指导下，保持科学的运动方案，合理搭配饮食，在目前孕晚期阶段让每周体重增长0.17~0.27Kg，整个孕期体重增长在5~9Kg范围，有助于优生优育和血糖的控制。";
                }
            }
        }else{  //非妊娠
            Double weightD = StringUtils.converParamToDouble(weight);
            if(bmi != null && weight != null) {
                double x = weightD - 2;
                double dNum28 = 28;
                if(bmi > dNum28) {
                    // 体重变化 减重到 当前体重 - 2 kg
                    suggestion = "您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，您需要在3-6个月内减重5%-10%。建议您遵循以下营养与运动治疗方案，一个月后体重将减轻到" + x + "kg。但减重速度不宜过快，建议以0.5～1kg/周的速率进行。";
                } else if(bmi > highBmi && bmi < dNum28) {
                    suggestion = "您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，您需要在3-6个月内减重5%-10%。建议您遵循以下运动方案，并搭配合理饮食，一个月后体重将减轻到" + x + "kg。但减重速度不宜过快，建议以0.5～1kg/周的速率进行。";
                } else if(bmi > lowBmi && bmi < highBmi) {
                    suggestion = "您目前体重正常，希望您坚持规律锻炼，合理搭配饮食，将体重保持在正常范围内。";
                } else if(bmi < lowBmi) {
                    suggestion = "您目前的体重低于正常水平，建议您在专业营养师和运动治疗师的指导下进行每周运动总时长不少于150分钟的运动计划。";
                }
            }
        }

        return suggestion;
    }

    /**
     * 运动强度
     *妊娠早期：低强度运动
     * 妊娠中、晚期 ：中强度运动
     * @return
     */
    public static Integer getSportintensity(String bsGestationalWeeks){
        int week = Integer.parseInt(bsGestationalWeeks);
        if (week >= 0 && week <=12){
            return 0;  //低强度运动
        }else if (week >= 13){
            return 1;  //中强度运动
        }
        return null;
    }

    /**  运动有效心率范围
     * 逻辑输出：
     * 20岁以下孕妇为140~155次/分钟，
     * 20~29岁孕妇为135~150次/分钟，
     * 30~39岁为130~145次/分钟，
     * 40岁或以上为125~140次/分钟;
     *肥胖或超重的孕妇运动强度的目标心率为：
     * 20~29岁孕妇102~124次/分钟，
     * 30~39岁101~120次/分钟,
     * 40岁或以上为＜100次/分钟;
     * @param birthday
     * @return
     */
    public static String getHeartRate(String bmi,String birthday){
        String str = "";
        Double bmiD = StringUtils.converParamToDouble(bmi);
        int age = DateHelper.getAge(birthday);
        double num25d0 = 25.0;
        if (bmiD >= num25d0){
          if (age >= 20 && age <=29){
                str = "102~124";
            }else if (age >= 30 && age <=39){
                str = "101~120";
            }else if (age >= 40){
              str = "＜100";
          }
        }else{
            if (age >= 0 && age < 20){
                str = "140~155";
            }else if (age >= 20 && age <=29){
                str = "135~150";
            }else if (age >= 30 && age <=39){
                str = "130~145";
            }else if (age >= 40){
                str = "125~140";
            }
        }
        return str;
    }

    /**
     * 运动编码对应的图片地址映射
     *
     * @return
     * @author 占铃树
     * @date 2017年3月14日
     */
    public static Map<String, String> getSportMethodMapping() {
        Map<String, String> pictureMapping = new HashMap<String, String>(34);
        //散步(3km/h)
        pictureMapping.put("YDFS05", "/prescription/img/sport/1609252104341047.png");
        //散步(6km/h)
        pictureMapping.put("YDFS06", "/prescription/img/sport/1609252106096094.png");
        //慢跑
        pictureMapping.put("YDFS07", "/prescription/img/sport/1609252105378787.png");
        //跑步
        pictureMapping.put("YDFS08", "/prescription/img/sport/1706291742013079.png");
        //游泳(放松)
        pictureMapping.put("YDFS09", "/prescription/img/sport/1609252111221760.png");
        //骑自行车
        pictureMapping.put("YDFS10", "/prescription/img/sport/1609252113011827.png");
        //坐
        pictureMapping.put("YDFS11", "/prescription/img/sport/1706291731138953.png");
        //有氧健身
        pictureMapping.put("YDFS12", "/prescription/img/sport/1706291739593425.png");
        //瑜伽
        pictureMapping.put("YDFS13", "/prescription/img/sport/1706291738370738.png");
        //舞蹈
        pictureMapping.put("YDFS14", "/prescription/img/sport/1706291740446805.png");
        //羽毛球
        pictureMapping.put("YDFS15", "/prescription/img/sport/1609252111448105.png");
        //跳绳
        pictureMapping.put("YDFS16", "/prescription/img/sport/1609252110476915.png");
        //篮球
        pictureMapping.put("YDFS17", "/prescription/img/sport/1609252112056058.png");
        //健美操
        pictureMapping.put("YDFS18", "/prescription/img/sport/1702131518080824.png");
        //太极拳
        pictureMapping.put("YDFS19", "/prescription/img/sport/1609252112424811.png");
        //体操
        pictureMapping.put("YDFS20", "/prescription/img/sport/1609252113291450.png");
        //做家务
        pictureMapping.put("YDFS21", "/prescription/img/sport/1706291735342879.png");
        //淋浴
        pictureMapping.put("YDFS22", "/prescription/img/sport/1706291733556579.png");
        //乒乓球
        pictureMapping.put("YDFS24", "/prescription/img/sport/1706291736058022.png");
        //遛狗
        pictureMapping.put("YDFS25", "/prescription/img/sport/1706291736251763.png");
        //下楼梯
        pictureMapping.put("YDFS26", "/prescription/img/sport/1706291736406079.png");
        //钓鱼
        pictureMapping.put("YDFS27", "/prescription/img/sport/1706291736575194.png");
        //打麻将
        pictureMapping.put("YDFS28", "/prescription/img/sport/1706291734407120.png");
        //气功
        pictureMapping.put("YDFS29", "/prescription/img/sport/1706291737429582.png");
        //洗碗
        pictureMapping.put("YDFS30", "/prescription/img/sport/1706291733297705.png");
        //足球
        pictureMapping.put("YDFS31", "/prescription/img/sport/1706291738180322.png");
        //开车
        pictureMapping.put("YDFS32", "/prescription/img/sport/1706291735123557.png");
        //打高尔夫球
        pictureMapping.put("YDFS33", "/prescription/img/sport/1706291732194118.png");
        //游泳比赛
        pictureMapping.put("YDFS34", "/prescription/img/sport/1706291739316681.png");
        //爬山
        pictureMapping.put("YDFS35", "/prescription/img/sport/1706291740063323.png");
        //俯卧撑
        pictureMapping.put("YDFS36", "/prescription/img/sport/1706291741186502.png");
        //洗衣服
        pictureMapping.put("YDFS37", "/prescription/img/sport/1706291732471957.png");
        //爬楼梯
        pictureMapping.put("YDFS38", "/prescription/img/sport/1706291741213648.png");
        //仰卧起坐
        pictureMapping.put("YDFS39", "/prescription/img/sport/1706291740398197.png");
        //步行
        pictureMapping.put("YDFS40", "/prescription/img/sport/1907261335530167.png");
        //疾步
        pictureMapping.put("YDFS41", "/prescription/img/sport/1907261336531213.png");
        //骑固定自行车
        pictureMapping.put("YDFS42", "/prescription/img/sport/1907261338199558.png");
        //轻度的家务活动
        pictureMapping.put("YDFS43", "/prescription/img/sport/1907261338573799.png");
        //上肢运动
        pictureMapping.put("YDFS44", "/prescription/img/sport/1907261340290657.png");
        //孕妇体操
        pictureMapping.put("YDFS45", "/prescription/img/sport/1907261341011682.png");

        return pictureMapping;
    }

    /**
     * 运动规则
     *
     * @param bmi
     * @param weight
     * @param healthRangeSet
     * @return
     * @author 占铃树
     * @throws HealthException
     * @date 2017年3月14日
     */
    public static String getHypSportRule(String bmi ,String weight) {
        String sportRule = "科学的运动可以减轻身体应激反应，稳定情绪，抑制身心紧张，消除焦虑状态，改善机体的血压适应能力，有助于稳定血压水平\n";
        if(StringUtils.isBlank(bmi)){
            return sportRule;
        }
        float bmiFloat = Float.parseFloat(bmi);
        Float weightInt = 0f;
        if(!StringUtils.isBlank(weight)){
            //减重到XXkg=当前体重-2kg
            weightInt = Float.parseFloat(weight) - 2;
        }
        if(bmiFloat < 18.5f){
            sportRule += "您目前的体重低于正常水平，建议您在专业营养师和运动治疗师的指导下进行每周运动总时长不少于150分钟的运动计划。";
        }else if(bmiFloat >= 18.5f && bmiFloat <= 23.9f){
            sportRule += "您目前体重正常，希望您坚持规律锻炼，合理搭配饮食，将体重保持在正常范围内。";
        }else if(bmiFloat >= 24f && bmiFloat <= 27.9f){
            sportRule += "您目前体重超重，为了高血压控制更平稳，预防相关并发症的发生与发展，您需要一年内体重减少初始体重的5%~10%。建议您遵循以下运动方案，并搭配合理饮食，1~3个月内体重将减轻到" + weightInt + "kg。但减重速度不宜过快，建议以0.5～1kg/2周的速率进行。";
        }else if(bmiFloat >= 28f){
            sportRule += "您目前体重肥胖，为了更好地控制血压，预防并发症的发生与发展，您需要一年内体重减少初始体重的5%~10%。建议您遵循科学的营养与运动治疗方案，1~3个月后体重将减轻到"  + weightInt +  "kg。但减重速度不宜过快，建议以0.5～1kg/2周的速率进行。";
        }
        return sportRule;
    }
}
