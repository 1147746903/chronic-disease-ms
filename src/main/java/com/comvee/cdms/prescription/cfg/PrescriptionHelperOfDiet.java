package com.comvee.cdms.prescription.cfg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.AlgorithmUtils;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.bo.ApiRangeBO;
import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.FoodNutrientBO;
import com.comvee.cdms.prescription.bo.HeatMaxAndMinOfDietBO;
import com.comvee.cdms.prescription.dto.BaseParamOfDietDTO;
import com.comvee.cdms.prescription.dto.DietOutPutDTO;
import com.comvee.cdms.prescription.po.NutritionCateringPO;
import com.comvee.cdms.prescription.vo.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class PrescriptionHelperOfDiet {

    /**--------------营养治疗 开发逻辑 start -------------------------**/

    /**
     * 全日酒精摄入量
     *
     * @return
     * @author 占铃树
     * @param yellowWine
     * @param beer
     * @param redWine
     * @param whiteSpirit
     * @param drinkRate
     * @param whiteSpiritHigh
     * @throws Exception
     * @date 2017年2月4日
     */
    public static Double getTotalDrinks(String drinkRate, Double whiteSpirit, Double redWine, Double beer, Double yellowWine, Double whiteSpiritHigh) {
        Double totalDrinks = 0d;
        String yJQK02 = "YJQK02";
        if(yJQK02.equalsIgnoreCase(drinkRate)) {
            /**
             * 全日酒精摄入量=（红酒量*0.1+啤酒量*0.03+黄酒量*0.15+低度白酒量*0.40+g高度白酒*0.50）*0.8
             *
             */
            if(redWine != null) {
                totalDrinks += (redWine * 0.1);
            }
            if(beer != null) {
                totalDrinks += (beer * 0.03);
            }
            if(yellowWine != null) {
                totalDrinks += (yellowWine * 0.15);
            }
            if(whiteSpirit != null) {
                totalDrinks += (whiteSpirit * 0.4);
            }
            if(whiteSpiritHigh != null) {
                totalDrinks += (whiteSpiritHigh * 0.5);
            }

            totalDrinks *= 0.8;
        }

        return totalDrinks;
    }

    /**
     * 体力劳动code
     */
    private final static String RCHDQD01 = "RCHDQD01";
    private final static String RCHDQD02 = "RCHDQD02";
    private final static String RCHDQD03 = "RCHDQD03";
    private final static String RCHDQD04 = "RCHDQD04";

    /**
     * 获取摄入量推荐
     *
     * @param height
     * @param weight
     * @param workStrength
     * @return
     * @throws Exception
     * @author 占铃树
     * @date 2017年2月4日
     */
    public static Map<String, Double> getHeatByItem(Double height,
                                                    Double weight, String workStrength) throws Exception {

        if(height!=null&&weight!=null&&!StringUtils.isBlank(workStrength)){
            double dheight = height;
            double dweight = weight;
            // 3.1684
            Double bmi = dweight / ((dheight / 100) * (dheight / 100));
            DecimalFormat ddf1 = new DecimalFormat("######0.0");
            bmi = Double.parseDouble(ddf1.format(bmi));

            // 能量系数(千卡/公斤理想体重/日)
            double heatRatioMin = 0;
            double heatRatioMax = 0;
            // 每日总蛋白系数
            double proteinRatioMin = 0.1;
            double proteinRatioMax = 0.15;
            // 每日总碳水化合物系数
            double carbohyRatioMin = 0.5;
            double carbohyRatioMax = 0.6;
            // 每日总脂肪系数
            double fatRatioMin = 0.3;
            double num18d5 = 18.5d;
            double num23d9 = 23.9d;
            double num24 = 24d;
            boolean b = bmi < num18d5 && (Constant.CONST_NUM_04.equals(workStrength) || RCHDQD04.equals(workStrength));
            if (b) {
            /*
             * 1、体格检查-BMI<18.5 2、基本信息-卧床
             */
                // 能量系数(千卡/公斤理想体重/日)
                heatRatioMin = 20;
                heatRatioMax = 25;
            } else {
                boolean c = bmi < num18d5 && (Constant.CONST_NUM_01.equals(workStrength) || RCHDQD01.equals(workStrength));
                if (c) {
			    /*
			     * 1、体格检查-BMI<18.5 2、基本信息-轻体力
			     */
                    // 能量系数(千卡/公斤理想体重/日)
                    heatRatioMin = 35;
                    heatRatioMax = 35;
                } else {
                    boolean c2 = bmi < num18d5 && (Constant.CONST_NUM_02.equals(workStrength) || RCHDQD02.equals(workStrength));
                    if (c2) {
				    /*
				     * 1、体格检查-BMI<18.5 2、基本信息-中体力
				     */
                        // 能量系数(千卡/公斤理想体重/日)
                        heatRatioMin = 40;
                        heatRatioMax = 40;
                    } else {
                        boolean d = bmi < num18d5 && (Constant.CONST_NUM_03.equals(workStrength) || RCHDQD03.equals(workStrength));
                        if (d) {
					    /*
					     * 1、体格检查-BMI<18.5 2、基本信息-重体力
					     */
                            // 能量系数(千卡/公斤理想体重/日)
                            heatRatioMin = 45;
                            heatRatioMax = 45;
                        } else {
                            boolean d2 = (bmi >= num18d5 && bmi <= num23d9) && (Constant.CONST_NUM_04.equals(workStrength) || RCHDQD04.equals(workStrength));
                            if (d2) {
						    /*
						     * 1、体格检查-BMI-18.5~23.9 2、基本信息-卧床
						     */
                                // 能量系数(千卡/公斤理想体重/日)
                                heatRatioMin = 15;
                                heatRatioMax = 20;
                            } else {
                                boolean e = (bmi >= num18d5 && bmi <= num23d9) && (Constant.CONST_NUM_01.equals(workStrength) || RCHDQD01.equals(workStrength));
                                if (e) {
							    /*
							     * 1、体格检查-BMI-18.5~23.9 2、基本信息-轻体力
							     */
                                    // 能量系数(千卡/公斤理想体重/日)
                                    heatRatioMin = 30;
                                    heatRatioMax = 30;
                                } else {
                                    boolean e2 = (bmi >= num18d5 && bmi <= num23d9) && (Constant.CONST_NUM_02.equals(workStrength) || RCHDQD02.equals(workStrength));
                                    if (e2) {
								    /*
								     * 1、体格检查-BMI-18.5~23.9 2、基本信息-中体力
								     */
                                        // 能量系数(千卡/公斤理想体重/日)
                                        heatRatioMin = 35;
                                        heatRatioMax = 35;
                                    } else {
                                        boolean f = (bmi >= num18d5 && bmi <= num23d9) && (Constant.CONST_NUM_03.equals(workStrength) || RCHDQD03.equals(workStrength));
                                        if (f) {
									    /*
									     * 1、体格检查-BMI-18.5~23.9 2、基本信息-重体力
									     */
                                            // 能量系数(千卡/公斤理想体重/日)
                                            heatRatioMin = 40;
                                            heatRatioMax = 40;
                                        } else {
                                            boolean f2 = (bmi >= num24) && (Constant.CONST_NUM_04.equals(workStrength) || RCHDQD04.equals(workStrength));
                                            if (f2) {
										    /*
										     * 1、体格检查-BMI>=24 2、基本信息-长期卧床-是
										     */
                                                // 能量系数(千卡/公斤理想体重/日)
                                                heatRatioMin = 15;
                                                heatRatioMax = 15;
                                            } else {
                                                boolean g = (bmi >= num24) && (Constant.CONST_NUM_01.equals(workStrength) || RCHDQD01.equals(workStrength));
                                                if (g) {
											    /*
											     * 1、体格检查-BMI>=24 2、基本信息-轻体力
											     */
                                                    // 能量系数(千卡/公斤理想体重/日)
                                                    heatRatioMin = 20;
                                                    heatRatioMax = 25;
                                                } else {
                                                    boolean g2 = (bmi >= num24) && (Constant.CONST_NUM_02.equals(workStrength) || RCHDQD02.equals(workStrength));
                                                    if (g2) {
												    /*
												     * 1、体格检查-BMI>=24 2、基本信息-中体力
												     */
                                                        // 能量系数(千卡/公斤理想体重/日)
                                                        heatRatioMin = 30;
                                                        heatRatioMax = 30;
                                                    } else {
                                                        boolean h = (bmi >= num24) && (Constant.CONST_NUM_03.equals(workStrength) || RCHDQD03.equals(workStrength));
                                                        if (h) {
													    /*
													     * 1、体格检查-BMI>=24 2、基本信息-重体力
													     */
                                                            // 能量系数(千卡/公斤理想体重/日)
                                                            heatRatioMin = 35;
                                                            heatRatioMax = 35;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 热量最小值
            Double heatMin = (dheight - 105) * heatRatioMin;
            // 热量最大值
            Double heatMax = (dheight - 105) * heatRatioMax;
            // 蛋白质最小值
            Double proteinMin = heatMin * proteinRatioMin / 4;
            // 蛋白质最大值
            Double proteinMax = heatMax * proteinRatioMax / 4;
            // 碳水化合物最小值
            Double carbohyMin = heatMin * carbohyRatioMin / 4;
            // 碳水化合物最大值
            Double carbohyMax = heatMax * carbohyRatioMax / 4;
            // 脂肪值
            Double fatMin = heatMax * fatRatioMin / 9;
            // 碳水化合物的每日摄入量(每日所需总热量的平均值*60%)
            double carbohydrates = (heatMin + heatMax) / 2 * 0.6;
            // 蛋白质的每日摄入量(每日所需总热量的平均值*15%)
            double protein = (heatMin + heatMax) / 2 * 0.15;
            // 脂肪的每日摄入量(每日所需总热量的平均值*25%)
            double totalfats = (heatMin + heatMax) / 2 * 0.25;

            DecimalFormat df = new DecimalFormat("######0.00");
            heatMin = Double.parseDouble(df.format(heatMin));
            heatMax = Double.parseDouble(df.format(heatMax));
            proteinMin = Double.parseDouble(df.format(proteinMin));
            proteinMax = Double.parseDouble(df.format(proteinMax));
            carbohyMax = Double.parseDouble(df.format(carbohyMax));
            fatMin = Double.parseDouble(df.format(fatMin));
            carbohydrates = Double.parseDouble(df.format(carbohydrates));
            protein = Double.parseDouble(df.format(protein));
            totalfats = Double.parseDouble(df.format(totalfats));

            Map<String, Double> returnmap = new HashMap<String, Double>(12);
            // 热量最小值 2
            returnmap.put("heatmin", heatMin);
            // 热量最大值 2
            returnmap.put("heatmax", heatMax);
            // 蛋白质最小值 2
            returnmap.put("proteinmin", proteinMin);
            // 蛋白质最大值  2
            returnmap.put("proteinmax", proteinMax);
            // 碳水化合物最小值 2
            returnmap.put("carbohymin", carbohyMin);
            // 碳水化合物最大值 2
            returnmap.put("carbohymax", carbohyMax);
            // 脂肪值 1
            returnmap.put("fatmin", fatMin);
            returnmap.put("bmi", bmi);

            // 三大营养素数据未转换为g（碳:目前/4, 蛋:目前/4, 脂肪:目前/9,）// 每日碳水化合物摄入量
            returnmap.put("carbohydrates", AlgorithmUtils.divide(Constant.CONST_NUM_02, carbohydrates, 4));
            // 每日蛋白质摄入量
            returnmap.put("protein", AlgorithmUtils.divide(Constant.CONST_NUM_02, protein, 4));
            // 脂肪值 1
            returnmap.put("totalfats", AlgorithmUtils.divide(Constant.CONST_NUM_02, totalfats, 9));
            // 每日盐摄入量(不超过)
            returnmap.put("totalsalts", 6d);
            return returnmap;
        }
        return null;
    }

    /**
     * 获取营养治疗的逻辑输出
     *
     * @return List<ProblemSuggestionVO>
     * @author 占铃树
     * @date 2017年2月4日
     */
    public static List<ProblemSuggestionVO> getDietOutPut(DietOutPutDTO dietOutPutDTO) {
        List<ProblemSuggestionVO> dataList = new ArrayList<ProblemSuggestionVO>();
        if(dietOutPutDTO!=null){
            Double totalHeat = dietOutPutDTO.getTotalHeat();
            Double preTotalHeatMin = dietOutPutDTO.getPreTotalHeatMin();
            Double preTotalHeatMax = dietOutPutDTO.getPreTotalHeatMax();
            Double totalCarbohydrates = dietOutPutDTO.getTotalCarbohydrates();
            Double preCereal = dietOutPutDTO.getPreCereal();
            Double totalVegetables = dietOutPutDTO.getTotalVegetables();
            Double preMeatAndAge = dietOutPutDTO.getPreMeatAndAge();
            Double totalProteins = dietOutPutDTO.getTotalProteins();
            Double totalFats = dietOutPutDTO.getTotalFats();
            Double totalFruit = dietOutPutDTO.getTotalFruit();
            Double totalDrinks = dietOutPutDTO.getTotalDrinks();
            Integer hasSmoke = dietOutPutDTO.getHasSmoke();
            Integer smokeNum = dietOutPutDTO.getSmokeNum();
            Double preMilk = dietOutPutDTO.getPreMilk();
            Double preProteins = dietOutPutDTO.getPreProteins();
            String breakfastCount = dietOutPutDTO.getBreakfastCount();
            String lunchCount = dietOutPutDTO.getLunchCount();
            String dinnerCount = dietOutPutDTO.getDinnerCount();
            Integer sex = dietOutPutDTO.getSex();
            Boolean isPathoglycemia = dietOutPutDTO.getPathoglycemia();
            String drinkTime = dietOutPutDTO.getDrinkTime();
            String drinkRate = dietOutPutDTO.getDrinkRate();

            // 一天摄入总热量逻辑处理
            dietHeatHandler(dataList, totalHeat, preTotalHeatMin, preTotalHeatMax);
            // 全天碳水化合物类食物摄入份数逻辑处理
            if(totalCarbohydrates!=null&&totalCarbohydrates>0){
                dietCarbohydrateHandler(dataList, totalCarbohydrates, preTotalHeatMin, preTotalHeatMax, preCereal);
            }
            // 全日蔬菜量逻辑处理
            if(totalVegetables!=null&&totalVegetables>0){
                dietVegetableHandler(dataList, totalVegetables);
            }
            // 全天蛋白质类食物摄入份数逻辑处理
            if(totalProteins!=null&&totalProteins>0){
                dietProteinHandler(dataList, totalProteins, preTotalHeatMin, preTotalHeatMax, preMeatAndAge, preMilk);
            }
            // 全天脂肪类食物摄入份数逻辑处理
            if(totalFats!=null&&totalFats>0){
                dietFatHandler(dataList, totalFats, preTotalHeatMin, preTotalHeatMax, preProteins);
            }
            // 全天水果类食物摄入份数逻辑处理
            if(totalFruit!=null&&totalFruit>0){
                dietFruitHandler(dataList, totalFruit, isPathoglycemia);
            }
            // 全日酒精摄入量逻辑处理
            dietDrinkHandler(dataList, totalDrinks, sex, drinkTime, drinkRate);
            // 三餐分配逻辑处理
            if(preCereal!=null){
                dietThreeMealsHandler(dataList, breakfastCount, lunchCount, dinnerCount, preCereal, totalCarbohydrates);
            }
            // 吸烟习惯逻辑处理
            dietSmokeHandler(dataList, hasSmoke, smokeNum);
        }

        return dataList;
    }

    /**
     *  吸烟习惯逻辑处理
     *
     * @param dataList
     * @param hasSmoke 是否吸烟 1:抽烟 2:不抽
     * @param smokeNum 抽烟数量 X根/天
     * @author 占铃树
     * @throws Exception
     * @date 2017年3月21日
     */
    private static void dietSmokeHandler(List<ProblemSuggestionVO> dataList,
                                         Integer hasSmoke, Integer smokeNum){
        if(null != hasSmoke && hasSmoke == 1 && smokeNum!=null && (StringUtils.converParamToDouble(smokeNum) > 0)) {
            ProblemSuggestionVO vo = new ProblemSuggestionVO();
            vo.setProblem("有吸烟习惯");
            vo.setDescription("每日吸烟" + smokeNum + "根");
            vo.setSuggestion("吸烟与肿瘤、糖尿病大血管病变、糖尿病微血管病变、过早死亡的风险增高相关。研究表明新发2型糖尿病患者戒烟有助于改善代谢指标、降低血压和白蛋白尿。建议您戒烟或烟草类制品，或寻求相关机构帮助戒烟");
            vo.setCode("2014");
            dataList.add(vo);
        }
    }

    /**
     * 三餐分配逻辑
     * @param dataList
     * @param breakfastCount 早餐主食份数
     * @param lunchCount 午餐主食份数
     * @param dinnerCount 晚餐主食份数
     * @param preCereal 全天碳水化合物类食物摄入份数(推荐谷薯类份数)
     * @author 占铃树
     * @throws Exception
     * @date 2017年3月21日
     */
    private static void dietThreeMealsHandler(
            List<ProblemSuggestionVO> dataList, String breakfastCount,
            String lunchCount, String dinnerCount, Double preCereal, Double totalCarbohydrates) {
        Double breakfast = StringUtils.converParamToDouble(breakfastCount);
        Double lunch = StringUtils.converParamToDouble(lunchCount);
        Double dinner = StringUtils.converParamToDouble(dinnerCount);
        /**
         * 判断逻辑: 目前早餐主食份数/推荐的谷薯类总份数>1/3
         并且
         目前早餐主食份数/目前的谷薯类总份数>1/3
         * 问题: 三餐分配不合理，早餐主食摄入过多
         * 描述: 目前早餐摄入主食xx份
         * 建议:  早餐摄入过多不利于早餐后血糖的控制，建议您减少早餐热量摄入，直到早餐的主食占全天主食推荐量的1/3或1/5，避免煎炸、太稀的食物作为早餐。或者进行分餐，减少一份主食（一份主食相当于1片35g土司面包或1个35g馒头），放在早午餐之间加餐食用
         * 编码: 2005
         *
         */
        int num3 = 3;
        if(breakfast != null && (breakfast > preCereal / num3)
                && (breakfast > totalCarbohydrates / num3)) {
            ProblemSuggestionVO vo = new ProblemSuggestionVO();
            vo.setCode("2005");
            vo.setProblem("三餐分配不合理，早餐主食占全天主食摄入量比例较大");
            vo.setSuggestion("早餐摄入过多不利于早餐后血糖的控制，建议您减少早餐热量摄入，直到早餐的主食占全天主食推荐量的1/3或1/5，避免煎炸、太稀的食物作为早餐。或者进行分餐，减少一份主食（一份主食相当于1片35g土司面包或1个35g馒头），放在早午餐之间加餐食用");
            vo.setDescription("目前早餐摄入主食" + formatNum(breakfast) + "份");
            dataList.add(vo);
        }
        /**
         * 判断逻辑: 目前午餐主食份数/推荐的谷薯类份数>2/5
         并且
         目前午餐主食份数/目前的谷薯类总份数>2/5
         * 问题: 三餐分配不合理，午餐主食摄入过多
         * 描述: 目前午餐摄入主食xx份
         * 建议: 午餐摄入过多不利于午餐后血糖的控制，建议您减少午餐热量摄入，直到午餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或55g的熟米饭或3块苏打饼干），放在午晚餐之间加餐食用
         * 编码: 2006
         *
         */
        int num2 = 2;
        int num5 = 5;
        if(lunch != null && (lunch > preCereal * num2 / num5)
                && (lunch > totalCarbohydrates * num2 / num5)) {
            ProblemSuggestionVO vo = new ProblemSuggestionVO();
            vo.setCode("2006");
            vo.setProblem("三餐分配不合理，午餐主食占全天主食摄入量比例较大");
            vo.setSuggestion("午餐摄入过多不利于午餐后血糖的控制，建议您减少午餐热量摄入，直到午餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或55g的熟米饭或3块苏打饼干），放在午晚餐之间加餐食用");
            vo.setDescription(" 目前午餐摄入主食" + formatNum(lunch) + "份");
            dataList.add(vo);
        }
        /**
         * 判断逻辑: 目前晚餐主食份数/推荐的谷薯类份数>2/5
         并且
         目前晚餐主食份数/目前的谷薯类总份数>2/5
         * 问题: 三餐分配不合理，晚餐主食摄入过多
         * 描述: 目前晚餐摄入主食xx份
         * 建议:  晚餐摄入过多不利于晚餐后血糖的控制，建议您减少晚餐热量摄入，直到晚餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或55g的熟米饭或3块苏打饼干），放在睡前加餐食用
         * 编码: 2007
         *
         */
        if(dinner != null && (dinner > preCereal * num2 / num5)
                && (dinner > totalCarbohydrates * num2 / num5)) {
            ProblemSuggestionVO vo = new ProblemSuggestionVO();
            vo.setCode("2007");
            vo.setProblem("三餐分配不合理，晚餐主食占全天主食摄入量比例较大");
            vo.setSuggestion("晚餐摄入过多不利于晚餐后血糖的控制，建议您减少晚餐热量摄入，直到晚餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或55g的熟米饭或3块苏打饼干），放在睡前加餐食用");
            vo.setDescription(" 目前晚餐摄入主食" + formatNum(dinner) + "份");
            dataList.add(vo);
        }

    }

    /**
     * 全日酒精摄入量逻辑处理
     * @param totalDrinks
     * @param sex
     * @param drinkTime
     * @author 占铃树
     * @throws Exception
     * @date 2017年2月4日
     */
    private static void dietDrinkHandler(List<ProblemSuggestionVO> dataList, Double totalDrinks,
                                         Integer sex, String drinkTime, String drinkRate) {
        String yJQK02 = "YJQK02";
        if(totalDrinks != null && yJQK02.equals(drinkRate)) {
            /**
             * 逻辑9:
             * 判断逻辑：
             * 9.1 男 + 平均1天酒精量摄入量>25g/天 或 喝酒频率为3~4次/周、5~7次/周、7次以上
             * 0.2 女 + 平均1天酒精量摄入量15g/天   或 喝酒频率为3~4次/周、5~7次/周、7次以上
             *
             * 问题8：超量饮酒
             * 输出内容8：限制酒精摄入量，禁止空腹饮酒；
             */
            boolean drinkTimeFlag = false;
            String yJ04 = "YJ04";
            String yJ05 = "YJ05";
            String yJ06 = "YJ06";
            if((yJ04.equalsIgnoreCase(drinkTime) || yJ05.equalsIgnoreCase(drinkTime) || yJ06.equalsIgnoreCase(drinkTime))) {
                drinkTimeFlag = true;
            }
            // 男
            int num25 =25;
            int num15 =15;
            boolean b = sex==1 && (drinkTimeFlag || totalDrinks > num25);
            if(b) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2012");
                vo.setProblem("超量饮酒");
                vo.setDescription("摄入酒精量为" + formatNum(totalDrinks) + "g/天或饮酒频率>2次/周");
                vo.setSuggestion("限制酒精摄入量（您一周内的日平均酒精摄入量超过了25g、或饮酒超过2次），建议您一天饮用酒的酒精量不超过25g（25g酒精量相当于啤酒750ml、葡萄酒250ml 、38度白酒1两半或40度以上高度白酒1两），且每周饮酒不超过2次，禁止空腹饮酒因酒精亦能提供热量，建议饮酒时计算酒精中所含的总热量（按7kcal/g酒精计算），并从当日的饮食中扣除该部分热量");
                dataList.add(vo);
            } else {
                boolean c = sex==2 && (drinkTimeFlag || totalDrinks > num15);
                if(c) {
                    ProblemSuggestionVO vo = new ProblemSuggestionVO();
                    vo.setCode("2013");
                    vo.setProblem("超量饮酒");
                    vo.setDescription("摄入酒精量为" + formatNum(totalDrinks) + "g/天或饮酒频率>2次/周");
                    vo.setSuggestion("限制酒精摄入量（您一周内的日平均酒精摄入量超过了15g、或饮酒超过2次），建议您一天饮用酒的酒精量不超过15g（15g酒精量相当于啤酒450ml、葡萄酒150ml 、38度白酒1两或40度以上高度白酒半两），且每周饮酒不超过2次，禁止空腹饮酒因酒精亦能提供热量，建议饮酒时计算酒精中所含的总热量（按7kcal/g酒精计算），并从当日的饮食中扣除该部分热量");
                    dataList.add(vo);
                }
            }
        }
    }

    /**
     * 全天水果类食物摄入份数逻辑处理
     *
     * @param totalFruit
     * @param isPathoglycemia
     * @author 占铃树
     * @date 2017年2月4日
     */
    private static void dietFruitHandler(List<ProblemSuggestionVO> dataList, Double totalFruit,
                                         Boolean isPathoglycemia ) {
        if(totalFruit != null && isPathoglycemia  != null) {
            /**
             * 逻辑8:
             * 判断逻辑：若患者信息中患者餐后血糖或空腹血糖不达标即偏高或者偏低，且水果摄入份数＞0份
             *
             * 问题8：水果摄入不当
             * 输出内容8：血糖不稳定时暂不建议食用水果，待血糖稳定至正常的情况下适量使用中低GI的水果；
             */
            if(isPathoglycemia  && totalFruit > 0) {

                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2011");
                vo.setProblem("水果摄入不当");
                vo.setDescription("血糖未达标");
                vo.setSuggestion("血糖不稳定时暂不建议食用水果，待血糖稳定至达标的情况下适量使用中低GI（如苹果、猕猴桃等）的水果");
                dataList.add(vo);
            }
        }
    }

    /**
     * 全天脂肪类食物摄入份数逻辑处理
     * @param totalFats
     * @param preTotalHeatMin
     * @param preTotalHeatMax
     * @param preProteins
     * @author 占铃树
     * @date 2017年2月4日
     */
    private static void dietFatHandler(List<ProblemSuggestionVO> dataList, Double totalFats,
                                       Double preTotalHeatMin, Double preTotalHeatMax, Double preProteins) {
        if(totalFats != null) {
            /**
             * 逻辑7:
             * 判断逻辑：现全天脂肪类食物摄入量份数>( 推荐摄入总热量的平均值 × 30 % ) / 90kcal  份
             *
             * 问题7：油脂类食物摄入过多
             * 输出内容7：减少烹饪油的用量，少吃煎炸食物，限制饱和脂肪酸与反式脂肪酸的摄入量；
             * 行动计划7（变量说明：X份等于推荐饮食方案中的油脂类的总份数）：每天减少油脂类摄入半份【5g食用油（家用白瓷勺半平勺）或者10g坚果（约10-15颗去壳花生）】直至每日油脂摄入X份
             */
            double preTotalHeat = (preTotalHeatMin + preTotalHeatMax) / 2;
            double num = preTotalHeat * 0.3 / 90;
            if(totalFats > num) {

                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2010");
                vo.setProblem("饮食结构不合理，油脂类食物摄入过多");
                vo.setDescription("目前摄入油脂类" + formatNum(totalFats) + "份");
                vo.setSuggestion("减少烹饪油的用量，少吃煎炸食物，限制饱和脂肪酸与反式脂肪酸的摄入量。建议每天减少油脂类摄入半份【5g食用油（家用白瓷勺半平勺）或者10g坚果（约10-15颗去壳花生）】直至每日油脂摄入" + formatNum(preProteins) + "份；");
                dataList.add(vo);
            }
        }
    }

    /**
     * 全天蛋白质类食物摄入份数逻辑处理
     *
     * @param totalProteins
     * @param preTotalHeatMax
     * @param preTotalHeatMin
     * @author 占铃树
     * @param preMilk
     * @param preMeatAndAge
     * @throws Exception
     * @date 2017年2月4日
     */
    private static void dietProteinHandler(List<ProblemSuggestionVO> dataList, Double totalProteins, Double preTotalHeatMin, Double preTotalHeatMax, Double preMeatAndAge, Double preMilk) {
        if(totalProteins != null && preMeatAndAge != null && preMilk != null) {
            /**
             * 逻辑5:
             * 判断逻辑：现全天蛋白质类食物摄入份数<( 推荐摄入总热量的平均值 × 15% ) / 90kcal  份
             *
             * 问题5：蛋白质类食物摄入不足
             * 输出内容5：增加优质蛋白的食用，如鸡蛋、牛奶、鸡胸肉；
             * 行动计划5（变量说明：X份等于推荐饮食方案中的肉类+蛋类+浆乳类的总份数）：每天增加半份的优质蛋白（1份≈25g大豆或160g奶制品或50g肉蛋类），如大豆、鱼、禽、蛋，直到每天摄入x份；
             */
            double preTotalHeat = (preTotalHeatMin + preTotalHeatMax) / 2;
            double num = preTotalHeat * 0.15 / 90;
            double num2 = preTotalHeat * 0.25 / 90;
            double x = preMeatAndAge + preMilk;
            x =(double) Math.round(x * 100) / 100;
            if(totalProteins < num) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setSuggestion("增加优质蛋白的摄入。建议每天增加半份的优质蛋白类食物（1份≈25g大豆或160g奶制品或50g肉蛋类），如大豆、鱼肉、禽肉、蛋类，直到每天摄入" + formatNum(x) + "份");
                vo.setProblem("饮食结构不合理，蛋白质类食物摄入不足");
                vo.setDescription("目前摄入蛋白质类" + formatNum(totalProteins) + "份");
                vo.setCode("2008");
                dataList.add(vo);
            }

            /**
             * 逻辑6：
             * 判断逻辑：现全天优质蛋白类食物摄入份数>( 推荐摄入总热量的平均值× 25 % ) / 90kcal  份
             *
             * 问题6：蛋白质类食物摄入过多
             * 输出内容6：适当减少肉类食物的摄入，增加膳食纤维丰富的食物，如绿叶蔬菜、粗粮；
             * 行动计划6（变量说明：X份等于推荐饮食方案中的肉类+蛋类+浆乳类的总份数）：每天减少半份的优质蛋白（1份≈25g大豆或160g奶制品或50g肉蛋类），如大豆、鱼、禽、蛋，直到每天摄入x份；
             */
            else if(totalProteins > num2) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setSuggestion("适当减少肉类食物的摄入，增加膳食纤维丰富的食物，如绿叶蔬菜、粗粮。建议每天减少半份的蛋白类食物（1份≈50g肉蛋类），如肉类、蛋类、乳制品，直到每天摄入" + formatNum(x) + "份");
                vo.setProblem("饮食结构不合理，蛋白质类食物摄入过多");
                vo.setDescription("目前摄入蛋白质类" + formatNum(totalProteins) + "份");
                vo.setCode("2009");
                dataList.add(vo);
            }
        }
    }

    /**
     * 全日蔬菜量逻辑处理
     * @param totalVegetables
     * @author 占铃树
     * @date 2017年2月4日
     */
    private static void dietVegetableHandler(List<ProblemSuggestionVO> dataList, Double totalVegetables) {
        if(totalVegetables != null) {
            /**
             * 逻辑4:
             * 判断逻辑：全日蔬菜份数< 1
             *
             * 问题4：蔬菜量摄入不足
             * 输出内容4：增加蔬菜的摄入，一天最少吃1斤的蔬菜，其中绿叶蔬菜占一半以上；
             * 行动计划4：每天增加半份的新鲜蔬菜（1份≈500g），直到每天吃够至少一份的新鲜蔬菜；
             */
            if(totalVegetables < 1) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setSuggestion("增加蔬菜的摄入。建议每天增加半份的新鲜蔬菜（1份≈500g白菜），直到每天吃够至少一份的新鲜蔬菜，其中绿叶蔬菜占一半以上");
                vo.setProblem("蔬菜量摄入不足");
                vo.setDescription("目前摄入蔬菜类" + formatNum(totalVegetables) + "份");
                vo.setCode("2015");
                dataList.add(vo);
            }
        }
    }

    /**
     * 全天碳水化合物类食物摄入份数逻辑处理
     *
     * @param totalCarbohydrates
     * @param preTotalHeatMin
     * @param preTotalHeatMax
     * @param preCereal 推荐饮食方案中的谷薯类份数
     * @author 占铃树
     * @date 2017年2月4日
     */
    private static void dietCarbohydrateHandler(List<ProblemSuggestionVO> dataList,
                                                Double totalCarbohydrates, Double preTotalHeatMin,
                                                Double preTotalHeatMax, Double preCereal) {
        if(totalCarbohydrates != null && preTotalHeatMax != null && preTotalHeatMin != null) {
            /**
             * 逻辑2:
             * 判断逻辑：现全天碳水化合物类食物摄入份数>（推荐摄入总热量的平均值× 60 %）/ 90kcal 份
             *
             * 问题2：淀粉类食物摄入过多
             * 输出内容2：减少精细米面、淀粉类食物的摄入，增加绿叶蔬菜和优质蛋白的摄入；
             * 行动计划2（变量说明：X份主食等于推荐饮食方案中的谷薯类份数）：每天减半两主食，直到每天吃x份主食（主食包括谷物、杂豆、薯类，粗细搭配）；
             */
            double preTotalHeat = (preTotalHeatMin + preTotalHeatMax) / 2;
            double num = preTotalHeat * 0.6 / 90;
            double num2 = preTotalHeat * 0.45 / 90;
            if(totalCarbohydrates > num) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2003");
                vo.setDescription("目前摄入谷薯类" + formatNum(totalCarbohydrates));
                vo.setProblem("饮食结构不合理，全天的淀粉类食物摄入过多");
                vo.setSuggestion("建议减少精细米面、淀粉类食物的摄入。建议每天减半两主食，直到每天吃" + formatNum(preCereal) + "份主食（1份≈55克米饭 ），如谷物、杂豆、薯类，注意粗细搭配，平均分配到三餐，保证三餐营养摄入均衡，并增加绿叶蔬菜和优质蛋白的摄入");
                dataList.add(vo);
            }

            /**
             * 逻辑3:
             * 判断逻辑：现全天碳水化合物类食物摄入份数<（推荐摄入总热量的平均值× 45 %）/ 90kcal 份
             *
             * 问题3：淀粉类食物摄入过少
             * 输出内容3：增加蔬菜、谷薯类食物的摄入，主食粗细搭配，保证碳水化合物的摄入占全天总热量的45%~60%；
             * 行动计划3（变量说明：X份主食等于推荐饮食方案中的谷薯类份数）：每天减半两主食，直到每天吃x份主食（主食包括谷物、杂豆、薯类，粗细搭配）；
             */
            else if(totalCarbohydrates < num2) {
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2004");
                vo.setDescription("目前摄入谷薯类" + formatNum(totalCarbohydrates) + "份");
                vo.setProblem("饮食结构不合理，全天的淀粉类食物摄入过少");
                vo.setSuggestion("增加蔬菜、谷薯类食物的摄入。建议每天增加半两主食，直到每天吃" + formatNum(preCereal) + "份主食（1份≈55克米饭 ），如谷物、杂豆、薯类，注意粗细搭配，平均分配到三餐，保证三餐营养摄入均衡，使主食以及蔬菜的摄入占全天总热量的45%~60%");
                dataList.add(vo);
            }
        }
    }

    /**
     * 一天摄入总热量逻辑处理
     *
     * @param totalHeat
     * @param preTotalHeatMin
     * @param preTotalHeatMax
     * @author 占铃树
     * @date 2017年2月4日
     */
    private static void dietHeatHandler(List<ProblemSuggestionVO> dataList,
                                        Double totalHeat, Double preTotalHeatMin, Double preTotalHeatMax) {
        if(totalHeat != null && totalHeat > 0 && preTotalHeatMax != null && preTotalHeatMin != null) {
            /**
             * 逻辑0:
             * 判断逻辑：现一天摄入总热量>推荐摄入总热量的最大值的110%
             *
             * 问题0：全天总热量摄入过多
             * 输出内容0：根据总摄入热量的推荐，减少热量的摄入，每天定量饮食，保证热量平衡；
             * 行动计划0：每天膳食摄入总热量比原来日常水平减少约40%；
             * (其中行动计划变量部分40%：等于（现一天摄入总热量-推荐摄入总热量）/现一天摄入总热量*100%。按照十位数进行四舍五入，例如把44%四舍五入为40%)
             */
            double dNum1d1 = 1.1d;
            double dNum0d9 = 0.9d;
            if(totalHeat > preTotalHeatMax * dNum1d1) {
                double preTotalHeat = (preTotalHeatMin + preTotalHeatMax) / 2;
                Double x = AlgorithmUtils.multiply(AlgorithmUtils.divide(Constant.CONST_NUM_01, AlgorithmUtils.add(totalHeat, -preTotalHeat), totalHeat), 100);
                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2001");
                vo.setDescription("目前摄入总热量" + totalHeat + "kcal");
                vo.setProblem("全天总热量摄入过多");
                vo.setSuggestion("建议每天膳食摄入总热量比原来日常水平减少约" + x.intValue() + "%，直到达到推荐的全日摄入热量，每天定量饮食，保证热量平衡");
                dataList.add(vo);
            }

            /**
             * 逻辑1
             * 判断逻辑：现一天摄入总热量<推荐摄入总热量最小值的90%
             *
             * 问题1：全天总热量摄入不足
             * 输出内容1：根据总摄入热量的推荐，增加热量的摄入，每天定量饮食，保证热量平衡；
             * 行动计划1:每天膳食摄入总热量比原来日常水平增加约20%；
             * (其中行动计划变量部分20%：等于（推荐摄入总热量-现一天摄入总热量）/现一天摄入总热量*100%。按照十位数进行四舍五入，例如把23%四舍五入为20%)
             */
            else if(totalHeat < preTotalHeatMin * dNum0d9) {
                double preTotalHeat = (preTotalHeatMin + preTotalHeatMax) / 2;
                Double x = AlgorithmUtils.multiply(AlgorithmUtils.divide(Constant.CONST_NUM_01, AlgorithmUtils.add(preTotalHeat, -totalHeat), totalHeat), 100);

                ProblemSuggestionVO vo = new ProblemSuggestionVO();
                vo.setCode("2002");
                vo.setDescription("目前摄入总热量" + totalHeat + "kcal");
                vo.setProblem("全天总热量摄入不足");
                vo.setSuggestion("建议每天膳食摄入总热量比原来日常水平增加约" + x.intValue() + "%，直到达到推荐的全日摄入热量，每天定量饮食，保证热量平衡");
                dataList.add(vo);
            }
        }
    }

    /**
     * 推荐食谱列表转换对应数据结构
     * @param nutritionCateringPOS
     * @return
     */
    public static List<NutritionCateringTreeVO> nCMLConvertToEFAML(List<NutritionCateringPO> nutritionCateringPOS){
        List<NutritionCateringTreeVO> foodAllocationList = new ArrayList<NutritionCateringTreeVO>();
        if(nutritionCateringPOS!=null&&nutritionCateringPOS.size()>0) {
            String caloriesLevel = "NaNNaNNaN";
            NutritionCateringTreeVO vo = null;
            for(NutritionCateringPO po : nutritionCateringPOS) {
                if(!caloriesLevel.equals(po.getCaloriesLevel())){
                    caloriesLevel = po.getCaloriesLevel();
                    vo = new NutritionCateringTreeVO();
                    vo.setRecipesCaloricId(po.getRecipesCaloricId());
                    vo.setCaloriesLevel(po.getCaloriesLevel());
                    vo.setCerealNum(po.getCerealNum());
                    vo.setFoodNum(po.getFoodNum());
                    vo.setFruitsNum(po.getFruitsNum());
                    vo.setGreaseNum(po.getGreaseNum());
                    vo.setMeatNum(po.getMeatNum());
                    vo.setSoymilkNum(po.getSoymilkNum());
                    vo.setVegetablesNum(po.getVegetablesNum());
                    if (!StringUtils.isBlank(po.getEggNum())) {
                        vo.setEggNum(po.getEggNum());
                    }
                    if (!StringUtils.isBlank(po.getBeansNum())) {
                        vo.setBeansNum(po.getBeansNum());
                    }
                    vo.setNutritionCateringItems(new ArrayList<NutritionCateringItemVO>());
                    vo.setIngredientStat(po.getIngredientStat());
                    foodAllocationList.add(vo);
                }

                if(caloriesLevel.equals(po.getCaloriesLevel()) && vo!=null){
                    NutritionCateringItemVO item = new NutritionCateringItemVO();
                    item.setNutritionCateringId(po.getNutritionCateringId());
                    item.setVersion(po.getVersion());
                    item.setBreakfastFoodJson(po.getBreakfastFoodJson());
                    item.setBreakfastFoodAddJson(po.getBreakfastFoodAddJson());
                    item.setLunchFoodJson(po.getLunchFoodJson());
                    item.setLunchFoodAddJson(po.getLunchFoodAddJson());
                    item.setDinnerFoodJson(po.getDinnerFoodJson());
                    item.setDinnerFoodAddJson(po.getDinnerFoodAddJson());
                    item.setMealsModel(po.getMealsModel());
                    item.setName(po.getRecipeName());
                    vo.getNutritionCateringItems().add(item);
                }
            }
        }
        return foodAllocationList;
    }
    /**
     * 获取患者当前推荐食谱
     * @param vos
     * @param heatStr
     * @return
     */
    public static NutritionCateringTreeVO getCurrentNutritionCateringByHeat(List<NutritionCateringTreeVO> vos, String heatStr){
        NutritionCateringTreeVO treeVO = null;
        if(vos!=null&&vos.size()>0 && !StringUtils.isBlank(heatStr)){
            List<NutritionCateringItemVO> oneItem = null;
            double heat = Double.parseDouble(heatStr);
            for(NutritionCateringTreeVO v : vos){
                List<NutritionCateringItemVO> items = v.getNutritionCateringItems();
                if(!StringUtils.isBlank(v.getCaloriesLevel()) && items!=null && items.size()>0){
                    String level = v.getCaloriesLevel();
                    double d = Double.parseDouble(level);
                    if((d-heat)== 0){
                        oneItem = new ArrayList<NutritionCateringItemVO>(1);
                        oneItem.add(items.get(0));
                        treeVO = new NutritionCateringTreeVO();
                        BeanUtils.copyProperties(treeVO,v);
                        treeVO.setNutritionCateringItems(oneItem);
                        break;
                    }
                }
            }

            if(treeVO==null){
                double flag = 100;
                for(NutritionCateringTreeVO v : vos){
                    List<NutritionCateringItemVO> items = v.getNutritionCateringItems();
                    if(!StringUtils.isBlank(v.getCaloriesLevel()) && items!=null && items.size()>0){
                        String level = v.getCaloriesLevel();
                        double d = Double.parseDouble(level);
                        if(Math.abs(d-heat) <= flag){
                            oneItem = new ArrayList<NutritionCateringItemVO>(1);
                            oneItem.add(items.get(0));
                            treeVO = new NutritionCateringTreeVO();
                            BeanUtils.copyProperties(treeVO,v);
                            treeVO.setNutritionCateringItems(oneItem);
                            flag = Math.abs(d-heat);
                        }
                    }
                }
            }
        }
        return treeVO;
    }

    /**
     * 获取食材份数
     *
     * @param breakfastList
     * @return
     * @author 占铃树
     * @date 2017年3月24日
     */
    public static String getEohIngredientsItemCount( List<FoodItemBO> breakfastList) {
        String f1001001 = "1001001";
        double totalCount = 0;
        if(breakfastList!=null){
            for (Iterator<FoodItemBO> iterator = breakfastList.iterator(); iterator.hasNext();) {
                FoodItemBO foodItemBO = iterator.next();
                String num = foodItemBO.getNum();
                // 当克数为0时,不进行逻辑处理
                if(Double.parseDouble(num) == 0) {
                    continue;
                }
                //不为谷薯类 不进行计算
                if(!f1001001.equals(foodItemBO.getIngredientsType())){
                    continue;
                }
                String gram = foodItemBO.getGram();
                double count = Double.parseDouble(num) / Double.parseDouble(gram);
                totalCount += count;
            }
        }

        return totalCount == 0 ? null : totalCount + "";
    }

    /**
     * 获取默认的食材项id串
     *
     * @return
     * @author 占铃树
     * @date 2017年2月17日
     */
    public static List<String> getDefaultEohIngredientsItemIds() {

        List<String> list = new ArrayList<String>();
        list.add("170216101200035");
        list.add("170216101200039");
        list.add("170216101200037");
        list.add("170216101200028");
        list.add("170216101200029");
        list.add("170216101200018");
        list.add("170216101200111");
        list.add("170216101200143");
        list.add("170216101200129");
        list.add("170216101200116");
        list.add("170216101200141");
        list.add("170216101200114");
        list.add("170216101200052");
        list.add("170216101200071");
        list.add("170216101200073");
        list.add("170216101200077");
        list.add("170216101200081");
        list.add("170216101200150");
        list.add("170216101200145");
        list.add("170216101200149");
        list.add("170216101200147");
        list.add("170216101200148");
        list.add("170216101200146");
        list.add("170216101200168");
        list.add("170216101200162");
        list.add("170216101200163");
        list.add("170216101200164");
        list.add("170216101200153");
        list.add("170216101200152");
        return list;

        /*//谷物类
         "170216101200035,170216101200039,170216101200037,170216101200028,170216101200029,170216101200018,"+
                //肉类
                "170216101200111,170216101200143,170216101200129,170216101200116,170216101200141,170216101200114,"+
                //蔬菜类
                "170216101200041,170216101200052,170216101200071,170216101200073,170216101200077,170216101200081,"+
                //浆乳奶类
                "170216101200150,170216101200145,170216101200149,170216101200147,170216101200148,170216101200146,"+
                //油脂类
                "170216101200168,170216101200162,170216101200163,170216101200164,170216101200153,170216101200152";*/
    }

    /**
     * 获取推荐食谱热量范围
     * @param height
     * @param weight
     * @param workStrength
     * @return
     */
    public static HeatMaxAndMinOfDietBO getHeatMaxAndMin(Double height, Double weight, String workStrength){
        // 获取摄入量推荐
        HeatMaxAndMinOfDietBO bo = new HeatMaxAndMinOfDietBO();
        try {
            Map<String, Double>  heatData = getHeatByItem(height, weight, workStrength);
            Double preTotalHeatMin = 2500d;
            Double preTotalHeatMax = 2500d;
            if(heatData != null) {
                preTotalHeatMin = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmin")));
                preTotalHeatMax = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmax")));
            }

            String heatMin = null;
            String heatMax = null;
            if(preTotalHeatMin.compareTo(preTotalHeatMax) == 0) {
                heatMin = Math.floor(preTotalHeatMin / 100) * 100 + "";//-400
                heatMax = Math.ceil(preTotalHeatMax / 100) * 100 + "";//+400
            } else {
                heatMin = Math.round(preTotalHeatMin / 100) * 100 + "";//-400
                heatMax = Math.round(preTotalHeatMax / 100) * 100 + "";//+400
            }
            bo.setHeatMin(heatMin);
            bo.setHeatMax(heatMax);
            bo.setHeatData(heatData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }

    /**
     * 获取目前饮食习惯
     *
     * @param
     * @return
     * @author 占铃树
     * @date 2017年2月21日
     */
    public static FoodAllocationVO getDailyFoodAllocation(
            List<FoodItemBO> itemList,
            List<FoodItemBO> otherItemList){
        double totalHeat = 0;
        double totalCount = 0;
        //标准交换份
        double strandGram = 90;
        Map<String, Double> countMapping = new HashMap<String, Double>();
        Map<String, Double> heatData = new HashMap<String, Double>();
        //根据每日饮食情况进行分析，食物总热量为食物每克热量*克数，份数为 食物总热量/90
        for (Iterator<FoodItemBO> iterator = itemList.iterator(); iterator.hasNext();) {
            FoodItemBO eohIngredientsItem = iterator.next();
            if (!StringUtils.isBlank(eohIngredientsItem.getIngredientsType())){
                String heat = eohIngredientsItem.getHeat();
                String num = eohIngredientsItem.getNum();
                // 当克数为0时,不进行逻辑处理
                if (Double.parseDouble(num) == 0) {
                    continue;
                }
                double heatCount = Double.parseDouble(heat) * Double.parseDouble(num);
                double count = 0;
                count = heatCount / strandGram;
                String type = eohIngredientsItem.getIngredientsType();

                if (countMapping.get(type) != null) {
                    count += countMapping.get(type);
                }
                countMapping.put(type, count);

                if (heatData.get(type) != null) {
                    heatCount += heatData.get(type);
                }
                heatData.put(type, heatCount);
            }
        }
        //分析每日加餐情况，食物总热量为食物每克热量*克数，份数为 食物总热量/90
        Map<String, Double> countExtraMapping = new HashMap<String, Double>();
        for (Iterator<FoodItemBO> iterator = otherItemList.iterator(); iterator.hasNext();) {
            FoodItemBO eohIngredientsItem = iterator.next();
            if (!StringUtils.isBlank(eohIngredientsItem.getIngredientsType())) {
                String num = eohIngredientsItem.getNum();
                String heat = eohIngredientsItem.getHeat();
                // 当克数为0时,不进行逻辑处理
                if (Double.parseDouble(num) == 0) {
                    continue;
                }

                double heatCount = Double.parseDouble(heat) * Double.parseDouble(num);
                double count = 0;
                count = heatCount / strandGram;
                String type = eohIngredientsItem.getIngredientsType();

                if (countExtraMapping.get(type) != null) {
                    count += countExtraMapping.get(type);
                }
                countExtraMapping.put(type, count);

                if (heatData.get(type) != null) {
                    heatCount += heatData.get(type);
                }
                heatData.put(type, heatCount);
            }
        }

        //计算totalHeat,totalCount-林雨堆改（之前计算有漏洞）
        for(String key:countMapping.keySet()){
            totalCount+=countMapping.get(key);
        }
        for(String key:countExtraMapping.keySet()){
            totalCount+=countExtraMapping.get(key);
        }
        for(String key:heatData.keySet()){
            totalHeat+=heatData.get(key);
        }
        //end

        double riceHeat = 0;
        if(countMapping.get("1001001") != null) {
            riceHeat += StringUtils.converParamToDouble(heatData.get("1001001"));
        }
        double vegetableHeat = 0;
        if(countMapping.get("1001003") != null) {
            vegetableHeat += StringUtils.converParamToDouble(heatData.get("1001003"));
        }
        double meatHeat = 0;
        if(countMapping.get("1001007") != null) {
            meatHeat += StringUtils.converParamToDouble(heatData.get("1001007"));
        }
        double proteinHeat = 0;
        if(countMapping.get("1001012") != null) {
            proteinHeat += StringUtils.converParamToDouble(heatData.get("1001012"));
        }
        double fatsHeat = 0;
        if(countMapping.get("1001013") != null) {
            fatsHeat += StringUtils.converParamToDouble(heatData.get("1001013"));
        }
        /*
        if(countMapping.get("1001014") != null) {
            fatsHeat += StringUtils.converParamToDouble(heatData.get("1001014"));
        }*/
        double fruitHeat = 0;
        if(countMapping.get("1001004") != null) {
            fruitHeat += StringUtils.converParamToDouble(heatData.get("1001004"));
        }

        Map<String, Double> heatMapping = new HashMap<String, Double>();
        heatMapping.put("riceHeat", StringUtils.converParamToDouble(riceHeat));//谷薯类
        heatMapping.put("vegetableHeat", StringUtils.converParamToDouble(vegetableHeat));//蔬菜类
        heatMapping.put("meatHeat", StringUtils.converParamToDouble(meatHeat));//肉蛋类
        heatMapping.put("proteinHeat", StringUtils.converParamToDouble(proteinHeat)); //浆乳类
        heatMapping.put("fatsHeat", StringUtils.converParamToDouble(fatsHeat));//油脂类（烹饪油） 油脂类（坚果类）
        heatMapping.put("fruitHeat", StringUtils.converParamToDouble(fruitHeat)); //水果类

        double riceNum = 0;
        if(countMapping.get("1001001") != null) {
            riceNum += countMapping.get("1001001");
        }

        double riceExtraNum = 0;
        if(countExtraMapping.get("1001001") != null) {
            riceExtraNum += countExtraMapping.get("1001001");
        }

        double vegetableNum = 0;
        if(countMapping.get("1001003") != null) {
            vegetableNum += countMapping.get("1001003");
        }

        double vegetableExtraNum = 0;
        if(countExtraMapping.get("1001003") != null) {
            vegetableExtraNum += countExtraMapping.get("1001003");
        }

        double meatNum = 0;
        if(countMapping.get("1001002") != null) {
            meatNum += countMapping.get("1001002");
        }
        if(countMapping.get("1001007") != null) {
            meatNum += countMapping.get("1001007");
        }

        double meatExtraNum = 0;
        if(countExtraMapping.get("1001002") != null) {
            meatExtraNum += countExtraMapping.get("1001002");
        }
        if(countExtraMapping.get("1001007") != null) {
            meatExtraNum += countExtraMapping.get("1001007");
        }

        double proteinNum = 0;
        if(countMapping.get("1001012") != null) {
            proteinNum += countMapping.get("1001012");
        }

        double proteinExtraNum = 0;
        if(countExtraMapping.get("1001012") != null) {
            proteinExtraNum += countExtraMapping.get("1001012");
        }

        double fatsNum = 0;
        if(countMapping.get("1001013") != null) {
            fatsNum += countMapping.get("1001013");
        }
        if(countMapping.get("1001014") != null) {
            fatsNum += countMapping.get("1001014");
        }

        double fatsExtraNum = 0;
        if(countExtraMapping.get("1001013") != null) {
            fatsExtraNum += countExtraMapping.get("1001013");
        }
        if(countExtraMapping.get("1001014") != null) {
            fatsExtraNum += countExtraMapping.get("1001014");
        }

        double fruitNum = 0;
        if(countMapping.get("1001004") != null) {
            fruitNum += countMapping.get("1001004");
        }

        double fruitExtraNum = 0;
        if(countExtraMapping.get("1001004") != null) {
            fruitExtraNum += countExtraMapping.get("1001004");
        }
        Map<String, Object> foodAllocationMap = new HashMap<String, Object>();
        foodAllocationMap.put("riceNum", riceNum);//谷薯类
        foodAllocationMap.put("riceExtraNum", riceExtraNum);//谷薯类加餐
        foodAllocationMap.put("vegetableNum", vegetableNum);//蔬菜类
        foodAllocationMap.put("vegetableExtraNum", vegetableExtraNum);//蔬菜类加餐
        foodAllocationMap.put("meatNum", meatNum);//肉蛋类和豆类
        foodAllocationMap.put("meatExtraNum", meatExtraNum);//肉蛋类和豆类加餐
        foodAllocationMap.put("proteinNum", proteinNum); //酱乳类
        foodAllocationMap.put("proteinExtraNum", proteinExtraNum); //浆乳类加餐
        foodAllocationMap.put("fatsNum", fatsNum);//油脂类（烹饪油） 油脂类（坚果类）
        foodAllocationMap.put("fatsExtraNum", fatsExtraNum);//油脂类（烹饪油） 油脂类（坚果类）
        foodAllocationMap.put("fruitNum", fruitNum); //水果类
        foodAllocationMap.put("fruitExtraNum", fruitExtraNum); //水果类加餐
        foodAllocationMap.put("heatMapping", heatMapping); //热量

        String dailyHeat = formatNum(totalHeat);
        String dailyCount = formatNum(totalCount); // 总份数
        FoodAllocationVO dailyFoodAllocation = new FoodAllocationVO();
        dailyFoodAllocation.setDailyHeat(dailyHeat);
        dailyFoodAllocation.setFoodAllocationMap(foodAllocationMap);
        dailyFoodAllocation.setNum(dailyCount);
        for (Iterator<FoodItemBO> iterator = itemList.iterator(); iterator.hasNext();) {
            FoodItemBO eohIngredientsItem = iterator.next();
            if (StringUtils.isBlank(eohIngredientsItem.getIngredientsType())) {
                if (eohIngredientsItem.getId().equals("10004")) {
                    Integer num = Integer.parseInt(eohIngredientsItem.getNum());
                    Double heat = (num / 100.0) * 1684;
                    Double heatCount = Double.parseDouble(dailyFoodAllocation.getDailyHeat()) + heat;
                    dailyFoodAllocation.setDailyHeat(heatCount + "");
                }
            }
        }
        for (Iterator<FoodItemBO> iterator = otherItemList.iterator(); iterator.hasNext();) {
            FoodItemBO eohIngredientsItem = iterator.next();
            if (StringUtils.isBlank(eohIngredientsItem.getIngredientsType())) {
                if (eohIngredientsItem.getId().equals("10004")) {
                    Integer num = Integer.parseInt(eohIngredientsItem.getNum());
                    Double heat = (num / 100.0) * 1684;
                    Double heatCount = Double.parseDouble(dailyFoodAllocation.getDailyHeat()) + heat;
                    dailyFoodAllocation.setDailyHeat(heatCount + "");
                }
            }
        }
        return dailyFoodAllocation;
    }

    /**
     * 从大档案中获取字段,并转化为list
     *
     * @param archives
     * @param category
     * @param field
     * @param clazz
     * @return
     * @author 占铃树
     * @date 2017年2月23日
     */
    public static <T> List<T> getListForArchives(
            Map<String, Object> archives, String category, String field, Class<T> clazz) {

        String value = getFieldFromArchives(archives, category, field);
        if(StringUtils.isBlank(value)){
            return null;
        }
        return JSONArray.parseArray(value, clazz);

    }

    /**
     * 从大档案中获取字段
     *
     * @param archives
     * @param category
     * @param field
     * @return
     * @author 占铃树
     * @date 2017年2月16日
     */
    @SuppressWarnings("unchecked")
    public static String getFieldFromArchives(Map<String, Object> archives, String category, String field) {
        String value = null;
        if(archives != null) {
            Map<String, Object> basic = null;
            if(archives.get(category) instanceof String){
                basic = JSONObject.parseObject(archives.get(category).toString(),Map.class);
            } else {
                basic = (Map<String, Object>) archives.get(category);
            }

            if(basic != null) {
                value = StringUtils.converParamToString(basic.get(field));
            }
        }

        return value;
    }

    public static double plugNum(double source, Object num) {
        Double d = StringUtils.converParamToDouble(num);
        if(d != null) {
            source += d;
        }

        return source;
    }

    /**
     * 判断血糖是否异常(餐后血糖或空腹血糖是否不达标即偏高或者偏低)
     *
     * @param mqFbg 空腹血糖
     * @param blg 非空腹血糖
     * @param healthRangeSet
     * @return
     * @author 占铃树
     * @throws Exception
     * @date 2017年2月22日
     */
    public static Boolean getIsPathoglycemia(Double mqFbg, Double blg,
                                             ApiRangeBO healthRangeSet) {
        Boolean isPathoglycemia = false;

        if(healthRangeSet==null){
            healthRangeSet = new ApiRangeBO();
            healthRangeSet.setLowBeforeBreakfast("4.4");
            healthRangeSet.setHighBeforeBreakfast("7");
            healthRangeSet.setLowAfterMeal("4.4");
            healthRangeSet.setHighAfterMeal("10");
        }

        // 空腹血糖
        Double emptyDouble  = mqFbg;
        Double lowEmpty = StringUtils.converParamToDouble(healthRangeSet.getLowBeforeBreakfast());
        Double highEmpty = StringUtils.converParamToDouble(healthRangeSet.getHighBeforeBreakfast());
        if(emptyDouble != null && lowEmpty != null && highEmpty != null) {
            if(emptyDouble < lowEmpty) {
                isPathoglycemia = true;
            } else if(emptyDouble > highEmpty) {
                isPathoglycemia = true;
            }
        }

        // 非空腹血糖
        Double fullDouble  = blg;
        Double lowFull = StringUtils.converParamToDouble(healthRangeSet.getLowAfterMeal());
        Double highFull = StringUtils.converParamToDouble(healthRangeSet.getHighAfterMeal());
        if(fullDouble != null && lowFull != null && highFull != null) {
            if(fullDouble < lowFull) {
                isPathoglycemia = true;
            } else if(fullDouble > highFull) {
                isPathoglycemia = true;
            }
        }

        return isPathoglycemia;
    }

    /**
     * 获取选择的食谱
     * @param nutritionCateringTreeVOS
     * @param nutritionCateringId
     */
    public static NutritionCateringTreeVO getCurrentNutritionCateringByNid(List<NutritionCateringTreeVO> nutritionCateringTreeVOS, String nutritionCateringId) {
        NutritionCateringTreeVO treeVO = null;
        if(nutritionCateringTreeVOS!=null && nutritionCateringTreeVOS.size()>0){
            List<NutritionCateringItemVO> oneItem = new ArrayList<NutritionCateringItemVO>(1);
            for(NutritionCateringTreeVO vo : nutritionCateringTreeVOS){
                List<NutritionCateringItemVO> items = vo.getNutritionCateringItems();
                if(items!=null&&items.size()>0){
                    int index = 1;
                    for(NutritionCateringItemVO item : items){
                        if(nutritionCateringId.equals(item.getNutritionCateringId())){
                            if ( StringUtils.isBlank(item.getName())) {
                                item.setName("食谱" + index);
                            }
                            oneItem.add(item);
                            treeVO = new NutritionCateringTreeVO();
                            BeanUtils.copyProperties(treeVO,vo);
                            treeVO.setNutritionCateringItems(oneItem);
                            break;
                        }
                        ++index;
                    }
                }
            }
        }
        return treeVO;
    }

    /**
     * 饮食贴士
     * @return
     */
    public static List<String> getDietTips(){
        List<String> list = new ArrayList<>();
        list.add("1、主食可以根据喜好进行粗细搭配，如大米小米饭、荞麦杂豆饭等，粗粮富含膳食纤维，对控制餐后血糖有一定帮助");
        list.add("2、烹饪油尽量选择植物油，避免饱和脂肪含量较高的猪油、黄油等动物油");
        list.add("3、保证每天至少摄入300~500g的蔬菜，其中绿叶蔬菜占一半");
        list.add("4、每日饮酒不要超过1-2份标准量，切不可空腹饮酒");
        return list;
    }

    /**
     * 获取高血压饮食贴士
     * @return
     */
    public static List<String> getHypDietTips(){
        List<String> list = new ArrayList<>();
        list.add("1、每日食盐摄入量不超过5g（普通啤酒瓶盖去胶垫后一平盖相当于6g，一块 4cm 见方的腐乳含盐量 5g）");
        list.add("2、在食物的选择上，遵循食物多样化及平衡膳食的原则，尽量减少摄入富含油脂和精制糖的食物，如肥肉和动物内脏各类西式糕点、巧克 力派、咖啡伴侣、速食食品等，限量食用烹调油");
        list.add("3、在饮食习惯上，进食应有规律，不宜进食过饱，也不应漏餐");
        list.add("4、少食用或不食用特别辛辣和刺激性食物，也不推荐饮用浓茶和浓咖啡");
        list.add("5、不宜饮用含糖饮料和碳酸饮料，可适量饮用白开水、淡茶水（红茶和绿茶）、矿泉水、低糖或无糖的水果汁和蔬菜汁，保证摄入充足的水分。每日可适量饮用200-300克奶类如牛奶，酸奶");
        list.add("6、尽量避免进食高盐食物和调味品， 如榨菜、咸菜、黄酱、腌菜、腌肉、辣酱等");
        list.add("7、利用蔬菜本身的风味来调味，例如将青椒、蕃茄、洋葱、香菇等和味道清淡的食物一起烹煮，可起到相互协调的作用");
        list.add("8、注意肉蛋分配，每日蛋类食物推荐摄入50克");
        return list;
    }

    /**
     * 获取肥胖饮食贴士
     * @return
     */
    public static List<String> getFatDietTips(){
        List<String> list = new ArrayList<>();
        list.add("1、全天饮水量2000ml");
        list.add("2、全日食盐摄入＜6克");
        list.add("3、作息规律，尽量23：00前休息");
        list.add("4、食谱里所有的重量均指带壳生重");
        list.add("5、主食粗细搭配，粗粮比细粮3：7，粗粮可选高粱、荞麦、绿豆、红豆、玉米碴；薯类可选土豆、红薯、山药、芋头");
        list.add("6、蛋白质食物以动物肉为主，红肉可选猪牛羊驴等，白肉可选鸡鸭鱼虾兔鸽子等。75克纯红瘦肉=112.5克纯白瘦肉=120克纯鱼片=带壳贝类450克=带壳蟹类300克=带壳虾225克=120克鸭血=150克豆腐=30克黄豆=90克香干=30克腐竹");
        list.add("7、蔬菜可选叶菜如菠菜、芹菜、韭菜、上海青、生菜、木耳菜、苋菜等；菇类如木耳、金针菇、香菇、杏鲍菇、海鲜菇、牛肝菌、平菇、茶树菇等其它可选如黄瓜、西红柿、西蓝花、花菜、青椒、洋葱、冬瓜等");
        list.add("8、水果可选苹果、草莓、樱桃、福桔、梨、杏、白火龙果等");
        list.add("9、食用油可选花生油、橄榄油、芝麻油等植物油");
        list.add("10、要求减重期间不吃的食物：");
        list.add("  （1）含糖食物：甜饮料（有糖/无糖）、甜点、饼干、巧克力、蛋糕、奶油面包、糯米类粘食（汤圆、粽子、艾窝窝等）、生活中的烟、酒。");
        list.add("   （2）高油食物：全脂奶、所有坚果、超市包装小食品、油炸油煎食物、各种酱料（蛋黄酱、沙拉酱、面酱、炸酱、芝麻酱等）。");
        list.add("  （3）各种高脂肉类：内脏、荤油、肥肉、排骨、浓肉汤、烧烤、麻辣烫、火腿、香肠、鸡爪、猪蹄、肉皮等。");
        return list;
    }

    /**--------------营养治疗 开发逻辑 end -------------------------**/

    public static String formatNum(Object num){
        String result = null;
        DecimalFormat df = new DecimalFormat("######0.0");
        Double d = StringUtils.converParamToDouble(num);
        if(d != null) {
            try{
                result = df.format(num);
            } catch (Exception e){
                if(num!=null){
                    return num.toString();
                }
                return d+"";
            }

        }
        return result;
    }


    /**
     * 获取推荐食谱热量范围(妊娠)
     * @param height
     * @param weight
     * @param workStrength
     * @return
     */
    public static HeatMaxAndMinOfDietBO getHeatMaxAndMinRS(Double height, Double weight, Integer gestationalWeeks){
        // 获取摄入量推荐
        HeatMaxAndMinOfDietBO bo = new HeatMaxAndMinOfDietBO();
        try {
            Map<String, Double>  heatData = getHeatByItemRS(height, weight, gestationalWeeks);
            Double preTotalHeatMin = 2500d;
            Double preTotalHeatMax = 2500d;
            if(heatData != null) {
                preTotalHeatMin = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmin")));
                preTotalHeatMax = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmax")));
            }

            String heatMin = null;
            String heatMax = null;
            if(preTotalHeatMin.compareTo(preTotalHeatMax) == 0) {
                heatMin = Math.floor(preTotalHeatMin / 100) * 100 + "";//-400
                heatMax = Math.ceil(preTotalHeatMax / 100) * 100 + "";//+400
            } else {
                heatMin = Math.round(preTotalHeatMin / 100) * 100 + "";//-400
                heatMax = Math.round(preTotalHeatMax / 100) * 100 + "";//+400
            }
            bo.setHeatMin(heatMin);
            bo.setHeatMax(heatMax);
            bo.setHeatData(heatData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }

    /**
     * 获取摄入量推荐(妊娠)
     *
     * @param height
     * @param weight
     * @param workStrength
     * @return
     * @throws Exception
     * @author 占铃树
     * @date 2017年2月4日
     */
    public static Map<String, Double> getHeatByItemRS(Double height,
                                                    Double weight, Integer gestationalWeeks) {

        if(height!=null&&weight!=null&&gestationalWeeks!=null){
            double dheight = height;
            double dweight = weight;
            // 3.1684
            Double bmi = dweight / ((dheight / 100) * (dheight / 100));
            DecimalFormat ddf1 = new DecimalFormat("######0.0");
            bmi = Double.parseDouble(ddf1.format(bmi));

            // 能量系数(千卡/公斤理想体重/日)
            double heatRatioMin = 0;
            double heatRatioMax = 0;
            // 每日总蛋白系数
            double proteinRatioMin = 0.1;
            double proteinRatioMax = 0.15;
            // 每日总碳水化合物系数
            double carbohyRatioMin = 0.5;
            double carbohyRatioMax = 0.6;
            // 每日总脂肪系数
            double fatRatioMin = 0.3;

            double num18d5 = 18.5d;
            double num24d9 = 24.9d;
            double num25 = 25d;

            if (bmi < num18d5){
                // 能量系数
                heatRatioMin = 35;
                heatRatioMax = 40;
            }else if (bmi >= num18d5 && bmi <=num24d9){
                // 能量系数
                heatRatioMin = 30;
                heatRatioMax = 35;
            }else if (bmi >=num25){
                // 能量系数
                heatRatioMin = 25;
                heatRatioMax = 30;
            }
            // 热量最小值
            Double heatMin = (dheight - 105) * heatRatioMin;
            // 热量最大值
            Double heatMax = (dheight - 105) * heatRatioMax;
            //若处于妊娠早期，推荐的热量少于1500千卡史，则默认推荐的热量最低值为1500千卡
            //1-12周为妊娠早期 13-27周为妊娠中期  28周以后为妊娠晚期
            if (gestationalWeeks >=1 && gestationalWeeks <=12){
                if(heatMin < 1500d){
                    heatMin = 1500d;
                }
                if(heatMax < 1500d){
                    heatMax = 1500d;
                }

            }
            if (gestationalWeeks >=13 && gestationalWeeks <=27){
                heatMin += 200d;
                heatMax += 200d;
            }
            //若处于妊娠晚期，推荐的热量少于1800千卡，则默认推荐的热量最低值为1800千卡
            if (gestationalWeeks >= 28){
                heatMin += 200d;
                heatMax += 200d;
                if (heatMin < 1800d){
                    heatMin = 1800d;
                }
                if (heatMax < 1800d){
                    heatMax = 1800d;
                }
            }
            // 蛋白质最小值
            Double proteinMin = heatMin * proteinRatioMin / 4;
            // 蛋白质最大值
            Double proteinMax = heatMax * proteinRatioMax / 4;
            // 碳水化合物最小值
            Double carbohyMin = heatMin * carbohyRatioMin / 4;
            // 碳水化合物最大值
            Double carbohyMax = heatMax * carbohyRatioMax / 4;
            // 脂肪值
            Double fatMin = heatMax * fatRatioMin / 9;
            // 碳水化合物的每日摄入量(每日所需总热量的平均值*60%)
            double carbohydrates = (heatMin + heatMax) / 2 * 0.6;
            // 蛋白质的每日摄入量(每日所需总热量的平均值*15%)
            double protein = (heatMin + heatMax) / 2 * 0.15;
            // 脂肪的每日摄入量(每日所需总热量的平均值*25%)
            double totalfats = (heatMin + heatMax) / 2 * 0.25;

            DecimalFormat df = new DecimalFormat("######0.00");
            heatMin = Double.parseDouble(df.format(heatMin));
            heatMax = Double.parseDouble(df.format(heatMax));
            proteinMin = Double.parseDouble(df.format(proteinMin));
            proteinMax = Double.parseDouble(df.format(proteinMax));
            carbohyMax = Double.parseDouble(df.format(carbohyMax));
            fatMin = Double.parseDouble(df.format(fatMin));
            carbohydrates = Double.parseDouble(df.format(carbohydrates));
            protein = Double.parseDouble(df.format(protein));
            totalfats = Double.parseDouble(df.format(totalfats));

            Map<String, Double> returnmap = new HashMap<String, Double>(12);
            // 热量最小值 2
            returnmap.put("heatmin", heatMin);
            // 热量最大值 2
            returnmap.put("heatmax", heatMax);
            // 蛋白质最小值 2
            returnmap.put("proteinmin", proteinMin);
            // 蛋白质最大值  2
            returnmap.put("proteinmax", proteinMax);
            // 碳水化合物最小值 2
            returnmap.put("carbohymin", carbohyMin);
            // 碳水化合物最大值 2
            returnmap.put("carbohymax", carbohyMax);
            // 脂肪值 1
            returnmap.put("fatmin", fatMin);
            returnmap.put("bmi", bmi);

            // 三大营养素数据未转换为g（碳:目前/4, 蛋:目前/4, 脂肪:目前/9,）// 每日碳水化合物摄入量
            returnmap.put("carbohydrates", AlgorithmUtils.divide(Constant.CONST_NUM_02, carbohydrates, 4));
            // 每日蛋白质摄入量
            returnmap.put("protein", AlgorithmUtils.divide(Constant.CONST_NUM_02, protein, 4));
            // 脂肪值 1
            returnmap.put("totalfats", AlgorithmUtils.divide(Constant.CONST_NUM_02, totalfats, 9));
            // 每日盐摄入量(不超过)
            returnmap.put("totalsalts", 6d);
            return returnmap;
        }
        return null;
    }

    /**
     * 获取当前饮食分析情况（按克）
     * @param itemList
     * @param otherItemList
     * @return
     */
    public static CurrentDietAnalyseVO getCurrentDietAnalyse(List<FoodItemBO> itemList ,List<FoodItemBO> otherItemList, List<FoodNutrientBO> bos,Integer eohType ){
        CurrentDietAnalyseVO currentDietAnalyseVO = new CurrentDietAnalyseVO();
        //总食物克数
        Float totalFoodWeight = 0f;
        //碳水化合物克数
        Float carbohydrateWeight = 0f;
        //油脂克数
        Float fatWeight = 0f;
        //蛋白质克数
        Float proteinWeight = 0f;
        //盐
        Float saltWeight = 0f;
        //钠
        Float naNumber = 0f;
        List<FoodItemBO> foodList = new ArrayList<>();
        foodList.addAll(itemList);
        foodList.addAll(otherItemList);

        for(FoodItemBO foodItemBO : foodList){
            if (!StringUtils.isBlank(foodItemBO.getIngredientsType())) {
                Float num = parseNumFloat(foodItemBO.getNum());
                if (num == 0f) {
                    continue;
                }
                totalFoodWeight += num;
                carbohydrateWeight += num * foodItemBO.getCarbohydrates().floatValue();
                fatWeight += num * foodItemBO.getTotalfats().floatValue();
                proteinWeight += num * foodItemBO.getProtein().floatValue();

                if ("精盐".equals(foodItemBO.getName()) || "湖盐（青盐）".equals(foodItemBO.getName()) || "土盐".equals(foodItemBO.getName())) {
                    saltWeight += num;
                } else {
                    naNumber += (num * foodItemBO.getNa().floatValue());
                }
            }else if (eohType == 3){
                for (FoodNutrientBO foodNutrientBO:bos){
                    if (foodNutrientBO.getId().equals("10004") && foodItemBO.getId().equals("10004")){
                        Float num = parseNumFloat(foodItemBO.getNum());
                        if (num == 0f) {
                            continue;
                        }
                        carbohydrateWeight += num * Float.parseFloat(foodNutrientBO.getCarbohydrates()) / 100;
                        fatWeight += num * Float.parseFloat(foodNutrientBO.getTotalfats())/ 100;
                        proteinWeight += num * Float.parseFloat(foodNutrientBO.getProtein())/ 100;
                    }
                }
            }
        }
        saltWeight += naNumber / 400;
        //小数点处理
        totalFoodWeight = radixPointHandler(totalFoodWeight);
        carbohydrateWeight = radixPointHandler(carbohydrateWeight);
        fatWeight = radixPointHandler(fatWeight);
        proteinWeight = radixPointHandler(proteinWeight);
        saltWeight = radixPointHandler(saltWeight);

        currentDietAnalyseVO.setTotalFoodWeight(totalFoodWeight);
        currentDietAnalyseVO.setCarbohydrateWeight(carbohydrateWeight);
        currentDietAnalyseVO.setFatWeight(fatWeight);
        currentDietAnalyseVO.setProteinWeight(proteinWeight);
        currentDietAnalyseVO.setSaltWeight(saltWeight);

        currentDietAnalyseVO.setCarbohydrateQuantityHeat(radixPointHandler(carbohydrateWeight * 4));
        currentDietAnalyseVO.setFatQuantityHeat(radixPointHandler(fatWeight * 9));
        currentDietAnalyseVO.setProteinQuantityHeat(radixPointHandler(proteinWeight * 4));
//        currentDietAnalyseVO.setSaltQuantityHeat();
        return currentDietAnalyseVO;
    }

    /**
     * 转化克数为数值型
     * @param num
     * @return
     */
    private static Float parseNumFloat(String num){
        if(StringUtils.isBlank(num)){
            return 0f;
        }
        try{
            return Float.parseFloat(num);
        }catch (Exception e){
            return 0f;
        }
    }

    /**
     * 小数点处理
     * @param f
     * @return
     */
    private static Float radixPointHandler(Float f){
        if(f == null || f == 0){
            return f;
        }
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 获取高血压饮食问题及建议
     * @param dailyHeat 每日总热量
     * @param heatMaxAndMinOfDiet 推荐热量范围
     * @param baseParamOfDietDTO 患者档案数据
     * @param itemList 正餐饮食
     * @param otherItemList 加餐饮食
     * @return
     */
    public static List<ProblemSuggestionVO> getHypProblemSuggestion(String dailyHeat , HeatMaxAndMinOfDietBO heatMaxAndMinOfDiet
            ,BaseParamOfDietDTO baseParamOfDietDTO ,List<FoodItemBO> itemList ,List<FoodItemBO> otherItemList){
        List<ProblemSuggestionVO> suggestionVOList = new ArrayList<>();
        // 当前饮食情况分析
        HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion = hypDietSituationHandler(itemList ,otherItemList);

        // 一天摄入总热量逻辑处理
        dietHeatHandler(suggestionVOList, Double.parseDouble(dailyHeat), Double.parseDouble(heatMaxAndMinOfDiet.getHeatMin()), Double.parseDouble(heatMaxAndMinOfDiet.getHeatMax()));
        //全天谷类及制品、薯类、淀粉及制品量
        hypSweetPotatoProblemSuggestHandler(suggestionVOList ,hypDietSituationForProblemSuggestion);
        //全天肉蛋类量
        hypMeetEggProblemSuggestHandler(suggestionVOList ,hypDietSituationForProblemSuggestion);
        //高血压 油脂问题及建议
        hypOilProblemSuggestHandler(suggestionVOList ,hypDietSituationForProblemSuggestion);
        //高血压 全天水果及制品量问题及建议
        hypFruitProblemSuggestHandler(suggestionVOList ,hypDietSituationForProblemSuggestion);
        //高血压 全天蔬菜类及制品量问题及建议
        hypVegetablesProblemSuggestHandler(suggestionVOList ,hypDietSituationForProblemSuggestion);
       //酒
        hypAlcoholProblemSuggestHandler(suggestionVOList ,baseParamOfDietDTO);
        //烟
        hypSmokeProblemSuggestHandler(suggestionVOList ,baseParamOfDietDTO);
        return suggestionVOList;
    }

    /**
     * 高血压 全天谷类及制品、薯类、淀粉及制品量 饮食问题建议处理
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypSweetPotatoProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion){
        if(hypDietSituationForProblemSuggestion.getSweetPotatoWeight() == null){
            return;
        }
        ProblemSuggestionVO problemSuggestionVO = null;
        /**
         * 全天谷类及制品+薯类、淀粉及制品
         * 摄入克数＜150克
         */
        if(hypDietSituationForProblemSuggestion.getSweetPotatoWeight() < 150){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("饮食结构不合理，谷类及制品食物摄入不足");
            problemSuggestionVO.setSuggestion("增加全谷类和薯类食物的摄入，粗细搭配，其中1/3至1/2为粗粮和杂粮，少食用或不食用加入钠盐的谷类制品如咸面包，方便面，挂面等");
            problemSuggestionVO.setDescription("目前摄入谷类及制品食物"+hypDietSituationForProblemSuggestion.getSweetPotatoWeight()+"克");
        }else if(hypDietSituationForProblemSuggestion.getSweetPotatoWeight() > 400){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("饮食结构不合理，谷类及制品食物摄入过多");
            problemSuggestionVO.setSuggestion("减少全谷类和薯类食物的摄入，粗细搭配，其中1/3至1/2为粗粮和杂粮，少食用或不食用加入钠盐的谷类制品如咸面包，方便面，挂面等");
            problemSuggestionVO.setDescription("目前摄入谷类及制品食物"+hypDietSituationForProblemSuggestion.getSweetPotatoWeight()+"克");
        }

        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 高血压 全天肉蛋类量
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypMeetEggProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion){
        if(hypDietSituationForProblemSuggestion.getMeatEggWeight() == null){
            return;
        }
        ProblemSuggestionVO problemSuggestionVO = null;
        /**
         * 全天肉蛋类
         * 摄入克数＜100克
         */
        if(hypDietSituationForProblemSuggestion.getMeatEggWeight() < 100f){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("饮食结构不合理，肉蛋类食物摄入不足");
            problemSuggestionVO.setSuggestion("选择鱼虾禽肉蛋和瘦肉类食品，每天不少于100克，少食用或不食用高钠盐，高脂肪，高胆固醇的动物性食品。详细食品见附录B和C");
            problemSuggestionVO.setDescription("目前摄入肉蛋类食物"+hypDietSituationForProblemSuggestion.getMeatEggWeight()+"克");
        }
        /**
         * 全天肉蛋类
         * 摄入克数＞250克
         */
        else if(hypDietSituationForProblemSuggestion.getMeatEggWeight() > 250f){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("饮食结构不合理，肉蛋类食物摄入过多");
            problemSuggestionVO.setSuggestion("选择鱼虾禽肉蛋和瘦肉类食品，每天不超过250克，少食用或不食用高钠盐，高脂肪，高胆固醇的动物性食品。详细食品见附录B和C");
            problemSuggestionVO.setDescription("目前摄入肉蛋类食物"+hypDietSituationForProblemSuggestion.getMeatEggWeight()+"克");
        }

        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 高血压 油脂问题及建议
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypOilProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion){
        if(hypDietSituationForProblemSuggestion.getOilWeight() == null){
            return;
        }
        ProblemSuggestionVO problemSuggestionVO = null;
        /**
         * 全天动物油+植物油摄入克数＞30克
         */
        if(hypDietSituationForProblemSuggestion.getOilWeight() > 30f){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("饮食结构不合理，烹调用油摄入过多");
            problemSuggestionVO.setSuggestion("减少烹饪油的用量，每天烹调用油不要超过30克，优先选择富含单不饱和脂肪酸的橄榄油，菜籽油，茶籽油以及富含多不饱和脂肪酸的大豆油，玉米油，花生油等。尽量不食用动物油，椰子油，棕榈油。推荐交替使用不同种类的植物油，少食用或不食用油炸和富含油脂的食品以及含反式脂肪酸的食品，如蛋糕点心，人造黄油等");
            problemSuggestionVO.setDescription("目前摄入动物油+植物油"+hypDietSituationForProblemSuggestion.getOilWeight()+"克");
        }
        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 高血压 全天水果及制品量问题及建议
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypFruitProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion){
        if(hypDietSituationForProblemSuggestion.getFruitWeight() == null){
            return;
        }
        ProblemSuggestionVO problemSuggestionVO = null;
        /**
         * 全天水果及制品摄入克数<200克
         */
        if(hypDietSituationForProblemSuggestion.getFruitWeight() < 200f){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("水果摄入不当");
            problemSuggestionVO.setSuggestion("水果摄入量至少200克，每天至少一个品种，最好2个品种以上。可选择低糖型或中等含糖的水果，包括苹果、猕猴桃、草莓、梨等");
            problemSuggestionVO.setDescription("目前摄入水果及制品"+hypDietSituationForProblemSuggestion.getFruitWeight()+"克");
        }
        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 高血压 全天蔬菜类及制品量问题及建议
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypVegetablesProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion){
        if(hypDietSituationForProblemSuggestion.getVegetablesWeight() == null){
            return;
        }
        ProblemSuggestionVO problemSuggestionVO = null;
        /**
         * 全天蔬菜类及制品摄入克数<500克
         */
        if(hypDietSituationForProblemSuggestion.getVegetablesWeight() < 500f){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("蔬菜量摄入不足");
            problemSuggestionVO.setSuggestion("每天蔬菜摄入量为500克，至少3个品种，最好5个品种以上，且每天摄入的蔬菜中要有深色蔬菜，叶类蔬菜等。推荐食用富钾蔬菜，例如菠菜、芥兰、莴笋叶、空心菜、苋菜等");
            problemSuggestionVO.setDescription("目前摄入蔬菜类及制品"+hypDietSituationForProblemSuggestion.getVegetablesWeight()+"克");
        }
        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 高血压
     * 红酒量
     * +
     * 啤酒量
     * +
     * 黄酒量
     * +
     * 低度白酒量
     * +
     * 高度白酒量 问题及建议
     * @param suggestionVOList
     * @param hypDietSituationForProblemSuggestion
     */
    private static void hypAlcoholProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,BaseParamOfDietDTO baseParamOfDiet){
        ProblemSuggestionVO problemSuggestionVO = null;
        //每日饮酒总量
        double totalDrink = totalDrinkMl(baseParamOfDiet);
        //是否为男性
        boolean isMan = baseParamOfDiet.getSex() != null && baseParamOfDiet.getSex() == 1;
        //是否饮酒
        boolean hasDrink = "YJQK02".equals(baseParamOfDiet.getDrinkRate());
        //男 是否饮酒超过25g
        boolean manOverMl = totalDrink > 25d;
        //是否 饮酒频次过高
        boolean badDrinkTime = "YJ02".equals(baseParamOfDiet.getDrinkTime()) || "YJ03".equals(baseParamOfDiet.getDrinkTime()) ||"YJ04".equals(baseParamOfDiet.getDrinkTime());
        //是否为 女性
        boolean isWoman = baseParamOfDiet.getSex() != null && baseParamOfDiet.getSex() == 2;
        //女 是否饮酒超过15g
        boolean womanOverMl = totalDrink > 15d;
        /**
         * 男
         * +
         * 平均1天酒精量摄入量>25g/天
         * 或
         * 喝酒频率为3~4次/周、5~7次/周、7次以
         */
        if(isMan && hasDrink && (manOverMl || badDrinkTime)){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("超量饮酒");
            problemSuggestionVO.setSuggestion("过量饮酒显著增加高血压的发病风险，且其风险随着饮酒量的增加而增加，限制饮酒可使血压降低。建议高血压患者不饮酒。如饮酒，应少量并选择低度酒，避免饮用高度烈性酒。每日酒精摄人量不超过25g，每周酒精摄入量不超过140g。白酒、葡萄酒、啤酒摄人量分别少于50、100、300 ml");
            problemSuggestionVO.setDescription("摄入酒精量为" + totalDrink + "g/天");
        }
        /**
         * 女
         * +
         * 平均1天酒精量摄入量15g/天
         * 或
         * 喝酒频率为3~4次/周、5~7次/周、7次以上
         */
        else if(isWoman && hasDrink && (womanOverMl || badDrinkTime)){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("超量饮酒");
            problemSuggestionVO.setSuggestion("过量饮酒显著增加高血压的发病风险，且其风险随着饮酒量的增加而增加，限制饮酒可使血压降低。建议高血压患者不饮酒。如饮酒，应少量并选择低度酒，避免饮用高度烈性酒。每日酒精摄人量不超过 15 g，每周酒精摄入量不超过80 g。白酒、葡萄酒、啤酒摄人量分别少于50、100、300 ml");
            problemSuggestionVO.setDescription("摄入酒精量为" + totalDrink + "g/天");
        }
        if(problemSuggestionVO != null){
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 获取每日饮酒总量 (克数)
     * @param baseParamOfDietDTO
     * @return
     */
    private static Double totalDrinkMl(BaseParamOfDietDTO baseParamOfDietDTO){
        Double total = 0d;
        if(baseParamOfDietDTO.getRedWineE() != null){
            total += baseParamOfDietDTO.getRedWineE() * 0.1d;
        } if(baseParamOfDietDTO.getBeerE() != null){
            total += baseParamOfDietDTO.getBeerE() * 0.03d;
        } if(baseParamOfDietDTO.getYellowWineE() != null){
            total += baseParamOfDietDTO.getYellowWineE() * 0.15d;
        } if(baseParamOfDietDTO.getWhiteSpiritE() != null){
            total += baseParamOfDietDTO.getWhiteSpiritE() * 0.40d;
        } if(baseParamOfDietDTO.getWhiteSpiritHighE() != null){
            total += baseParamOfDietDTO.getWhiteSpiritHighE() * 0.50d;
        }
        BigDecimal bigDecimal = new BigDecimal(total * 0.8d);
        return bigDecimal.setScale(2 , BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 高血压 是否吸烟 饮食问题及建议
     * @param suggestionVOList
     * @param baseParamOfDiet
     */
    private static void hypSmokeProblemSuggestHandler(List<ProblemSuggestionVO> suggestionVOList ,BaseParamOfDietDTO baseParamOfDiet){
        ProblemSuggestionVO problemSuggestionVO = null;
        if(baseParamOfDiet.getHasSmoke() != null && baseParamOfDiet.getHasSmoke() == 1){
            problemSuggestionVO = new ProblemSuggestionVO();
            problemSuggestionVO.setProblem("有吸烟习惯");
            problemSuggestionVO.setSuggestion("吸烟是一种不健康行为，是心血管病和癌症的主要危险因素之一。被动吸烟显著增加心血管疾病风险。戒烟虽不能降低血压，但戒烟可降低心血管疾病风险。 烈建议并督促高血压患者戒烟，必要时，应有医师指导患者应用戒烟药物，减轻戒断症状");
            problemSuggestionVO.setDescription("每日吸烟" + Optional.ofNullable(baseParamOfDiet.getSmokeNum()).orElse(0) +"根");
            suggestionVOList.add(problemSuggestionVO);
        }
    }

    /**
     * 获取当天饮食情况- 用于高血压饮食问题及建议
     * @param itemList
     * @param otherItemList
     * @return
     */
    private static HypDietSituationForProblemSuggestion hypDietSituationHandler(List<FoodItemBO> itemList ,List<FoodItemBO> otherItemList){
        //合并当前饮食内容
        List<FoodItemBO> foodItemBOS = new ArrayList<>();
        foodItemBOS.addAll(itemList);
        foodItemBOS.addAll(otherItemList);
        float sweetPotato = 0f; //谷薯
        float meetEgg = 0f; //肉蛋
        float fruit = 0f; //水果
        float vegetables = 0f; //蔬菜
        float oil = 0f; //油脂
        for(FoodItemBO foodItemBO : foodItemBOS){
            float num = parseNumFloat(foodItemBO.getNum());
            if(num == 0 || StringUtils.isBlank(foodItemBO.getIngredientsType())){
                continue;
            }
            int type = Integer.parseInt(foodItemBO.getIngredientsType());
            if(FoodConstant.FOOD_INGREDIENTS_TYPE_SWEET_POTATO == type){
                sweetPotato += num;
            }else if(FoodConstant.FOOD_INGREDIENTS_TYPE_MEET_EGG == type){
                meetEgg += num;
            }else if(FoodConstant.FOOD_INGREDIENTS_TYPE_FRUIT == type){
                fruit += num;
            }else if(FoodConstant.FOOD_INGREDIENTS_TYPE_VEGETABLES == type){
                vegetables += num;
            }else if(FoodConstant.FOOD_INGREDIENTS_TYPE_OIL == type){
                oil += num;
            }
        }

        HypDietSituationForProblemSuggestion hypDietSituationForProblemSuggestion = new PrescriptionHelperOfDiet().new HypDietSituationForProblemSuggestion();
        hypDietSituationForProblemSuggestion.setFruitWeight(fruit);
        hypDietSituationForProblemSuggestion.setMeatEggWeight(meetEgg);
        hypDietSituationForProblemSuggestion.setOilWeight(oil);
        hypDietSituationForProblemSuggestion.setVegetablesWeight(vegetables);
        hypDietSituationForProblemSuggestion.setSweetPotatoWeight(sweetPotato);
        return hypDietSituationForProblemSuggestion;
    }

    /**
     * 饮食情况 用于高血压饮食问题及建议
     */
     class HypDietSituationForProblemSuggestion{

        /**
         * 全天肉蛋重量
         */
        private Float meatEggWeight;
        /**
         * 全天动物油+植物油量
         */
        private Float oilWeight;
        /**
         * 全天水果及制
         */
        private Float fruitWeight;
        /**
         * 全天蔬菜类及制品量
         */
        private Float VegetablesWeight;

        /**
         * 全天谷类及制品、薯类、淀粉及制品量
         */
        private Float sweetPotatoWeight;

        public Float getSweetPotatoWeight() {
            return sweetPotatoWeight;
        }

        public void setSweetPotatoWeight(Float sweetPotatoWeight) {
            this.sweetPotatoWeight = sweetPotatoWeight;
        }

        public Float getMeatEggWeight() {
            return meatEggWeight;
        }

        public void setMeatEggWeight(Float meatEggWeight) {
            this.meatEggWeight = meatEggWeight;
        }

        public Float getOilWeight() {
            return oilWeight;
        }

        public void setOilWeight(Float oilWeight) {
            this.oilWeight = oilWeight;
        }

        public Float getFruitWeight() {
            return fruitWeight;
        }

        public void setFruitWeight(Float fruitWeight) {
            this.fruitWeight = fruitWeight;
        }

        public Float getVegetablesWeight() {
            return VegetablesWeight;
        }

        public void setVegetablesWeight(Float vegetablesWeight) {
            VegetablesWeight = vegetablesWeight;
        }
    }

    /**
     * 获取推荐食谱热量范围(肥胖)
     * @param height
     * @param weight
     * @param labourIntensity
     * @return
     */
    public static HeatMaxAndMinOfDietBO getHeatMaxAndMinFat(Double height, Double weight,String labourIntensity){
        // 获取摄入量推荐
        HeatMaxAndMinOfDietBO bo = new HeatMaxAndMinOfDietBO();
        try {
            double dbheight = height - 105; // 标椎体重
            double bmi = 0;
            if (height!=0 && weight!=0){
                double lsheight = height/100;
                bmi= weight/(lsheight*lsheight);
            }
            String heatRatioMin = null;  // 能量系数
            String heatRatioMax = null;  // 能量系数
            String heatRatioAVG = null;  // 能量系数
            if (bmi<18.5){
                if (labourIntensity.equals("RCHDQD01")){
                    heatRatioAVG = "35";
                }else if (labourIntensity.equals("RCHDQD02")){
                    heatRatioAVG = "40";
                }else if (labourIntensity.equals("RCHDQD03")){
                    heatRatioAVG = "45";
                }else {
                    heatRatioMin = "20";
                    heatRatioMax = "25";
                }
            }else if (bmi>= 18.5 && bmi <24){
                if (labourIntensity.equals("RCHDQD01")){
                    heatRatioAVG = "30";
                }else if (labourIntensity.equals("RCHDQD02")){
                    heatRatioAVG = "35";
                }else if (labourIntensity.equals("RCHDQD03")){
                    heatRatioAVG = "40";
                }else {
                    heatRatioMin = "15";
                    heatRatioMax = "20";
                }
            }else {
                if (labourIntensity.equals("RCHDQD01")){
                    heatRatioMin = "20";
                    heatRatioMax = "25";
                }else if (labourIntensity.equals("RCHDQD02")){
                    heatRatioAVG = "30";
                }else if (labourIntensity.equals("RCHDQD03")){
                    heatRatioAVG = "35";
                }else {
                    heatRatioAVG = "15";
                }
            }

            String heatMin = null;
            String heatMax = null;
            if (StringUtils.isBlank(heatRatioAVG)){
                heatMin = Double.parseDouble(heatRatioMin) * dbheight + "";// 热量范围小
                heatMax = Double.parseDouble(heatRatioMax) * dbheight + "";//  热量范围大
            }else {
                heatMin = Double.parseDouble(heatRatioAVG) * dbheight*0.9 + "";// 热量范围小
                heatMax = Double.parseDouble(heatRatioAVG) * dbheight*1.1 + "";//  热量范围大
            }
            Map<String, Double>  heatData = new HashMap<>();
            heatData.put("heatmin",Double.parseDouble(heatMin));
            heatData.put("heatmax",Double.parseDouble(heatMax));
            if (StringUtils.isBlank(heatRatioMin)) heatRatioMin = "0";
            if (StringUtils.isBlank(heatRatioMax)) heatRatioMax = "0";
            if (StringUtils.isBlank(heatRatioAVG)) heatRatioAVG = "0";
            heatData.put("proteinmin",Double.parseDouble(heatRatioMin));
            heatData.put("proteinmax",Double.parseDouble(heatRatioMax));
            heatData.put("protein",Double.parseDouble(heatRatioAVG));
            bo.setHeatMin(heatMin);
            bo.setHeatMax(heatMax);
            bo.setHeatData(heatData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }
}
