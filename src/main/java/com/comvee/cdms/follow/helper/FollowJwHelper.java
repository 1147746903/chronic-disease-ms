package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.follow.bo.HypJwFollowBodyBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.google.common.base.Joiner;

import java.text.MessageFormat;
import java.util.*;

/**2型糖尿病随访总结
 * @author wyc
 * @date 2019/8/19 15:12
 */
public class FollowJwHelper {

    //2型糖尿病随访总结（存在问题分析逻辑、改进措施逻辑、预期达到目标逻辑）
    public static Map<String, Object> outFollowDiabetes(Map<String, Object> followBodyMap, RangeBO range) {
        Map<String, Object> reMap = new HashMap<>();

        List<String> wtList = new ArrayList<>();//问题集合
        List<String> gjList = new ArrayList<>();//改进集合
        List<String> mbList = new ArrayList<>();//目标集合

        Set<Integer> gjSet = new HashSet<>();//改进去重
        Set<Integer> mbSet = new HashSet<>();//目标去重
        //症状
        if (null != followBodyMap.get("symptom") && !StringUtils.isBlank(followBodyMap.get("symptom").toString())) {
            String zzStr = "";
            String symptom = followBodyMap.get("symptom").toString();
            if (!symptom.equals("ZZ01")) {
                if (symptom.contains("ZZ02")) {
                    gjSet.add(1);
                    zzStr += "多饮、";
                }
                if (symptom.contains("ZZ03")) {
                    gjSet.add(2);
                    zzStr += "多食、";
                }
                if (symptom.contains("ZZ04")) {
                    gjSet.add(3);
                    zzStr += "多尿、";
                }
                if (symptom.contains("ZZ05")) {
                    gjSet.add(4);
                    zzStr += "视力模糊、";
                }
                if (symptom.contains("ZZ06")) {
                    gjSet.add(5);
                    zzStr += "感染、";
                }
                if (symptom.contains("ZZ07")) {
                    gjSet.add(6);
                    zzStr += "手脚麻木、";
                }
                if (symptom.contains("ZZ08")) {
                    gjSet.add(7);
                    zzStr += "下肢浮肿、";
                }
                if (symptom.contains("ZZ09")) {
                    gjSet.add(8);
                    zzStr += "体重明显下降、";
                }
                if (symptom.contains("ZZ010")) {
                    //其他症状
                    if (null != followBodyMap.get("symptom_other") && !StringUtils.isBlank(followBodyMap.get("symptom_other").toString())) {
                        String symptomOther = followBodyMap.get("symptom_other").toString();
                        zzStr += symptomOther + "、";
                    }
                }
                if (!StringUtils.isBlank(zzStr)) {
                    String str = zzStr.substring(0, zzStr.length() - 1);
                    String wtResultStr = "有“" + str + "”症状；\r\n";
                    String mbResultStr = "“" + str + "”症状逐渐好转；\r\n";
                    wtList.add(wtResultStr);
                    mbList.add(mbResultStr);
                }

            }

        }
        //体征
        if (null != followBodyMap.get("essential_hyp") && !StringUtils.isBlank(followBodyMap.get("essential_hyp").toString())) {
            String essentialHyp = followBodyMap.get("essential_hyp").toString();
            if (null != followBodyMap.get("sbp") && null != followBodyMap.get("dbp") && !StringUtils.isBlank(followBodyMap.get("sbp").toString()) && !StringUtils.isBlank(followBodyMap.get("dbp").toString())) {
                double sbp = Double.parseDouble(followBodyMap.get("sbp").toString());  //收缩压
                double dbp = Double.parseDouble(followBodyMap.get("dbp").toString());  //舒张压

                double lowSbp = Double.parseDouble(range.getLowSystolicPress());  //收缩压下限
                double highSbp = Double.parseDouble(range.getHighSystolicPress()); //收缩压上限
                double lowDbp = Double.parseDouble(range.getLowDiastolicPress());  //舒张压下限
                double highDbp = Double.parseDouble(range.getHighDiastolicPress()); //舒张压上限

                //有高血压病史
                if (!checkXY(sbp, dbp, lowSbp, highSbp, lowDbp, highDbp)) {
                    if (sbp > highSbp || dbp > highDbp) {
                        wtList.add("您目前血压（收缩压/舒张压）：" + sbp + "/" + dbp + "mmHg，血压偏高；\r\n");
                        gjSet.add(9);
                        mbSet.add(1);
                    } else if (sbp < lowSbp || dbp < lowDbp) {
                        wtList.add("您目前血压（收缩压/舒张压）：" + sbp + "/" + dbp + "mmHg，血压偏高；\r\n");
                        gjSet.add(11);
                        mbSet.add(1);
                    }
                }
            }
        }
        //体质指数BMI
        if (null != followBodyMap.get("bmi") && !StringUtils.isBlank(followBodyMap.get("bmi").toString())) {
            double bmi = Double.parseDouble(followBodyMap.get("bmi").toString());
            double lowBmi = Double.parseDouble(range.getLowBmi()); //BMI下限
            double highBmi = Double.parseDouble(range.getHighBmi()); //BMI上限
            if (bmi < lowBmi) {
                wtList.add("BMI值为：" + bmi + "，（消瘦）；\r\n");
                gjSet.add(14);
                if (null != followBodyMap.get("next_bmi") && !StringUtils.isBlank(followBodyMap.get("next_bmi").toString())) {
                    String nextBmi = followBodyMap.get("next_bmi").toString();
                    mbList.add("下次随访目标体质指数（BMI）为：" + nextBmi + "；\r\n");
                } else {
                    mbSet.add(4);
                }
                if (null != followBodyMap.get("next_weight") && !StringUtils.isBlank(followBodyMap.get("next_weight").toString())) {
                    String nextWeight = followBodyMap.get("next_weight").toString();
                    mbList.add("下次随访目标体重为：" + nextWeight + "kg；\r\n");
                } else {
                    mbSet.add(4);
                }
            } else if (bmi > highBmi && bmi < 28) {
                wtList.add("BMI值为：" + bmi + "，（超重）；\r\n");
                gjSet.add(15);

                if (null != followBodyMap.get("next_bmi") && !StringUtils.isBlank(followBodyMap.get("next_bmi").toString())) {
                    String nextBmi = followBodyMap.get("next_bmi").toString();
                    mbList.add("下次随访目标体质指数（BMI）为：" + nextBmi + "；\r\n");
                } else {
                    mbSet.add(5);
                }
                if (null != followBodyMap.get("next_weight") && !StringUtils.isBlank(followBodyMap.get("next_weight").toString())) {
                    String nextWeight = followBodyMap.get("next_weight").toString();
                    mbList.add("下次随访目标体重为：" + nextWeight + "kg；\r\n");
                } else {
                    mbSet.add(5);
                }
            } else if (bmi >= 28) {
                wtList.add("BMI值为：" + bmi + "，（肥胖）；\r\n");
                gjSet.add(16);
                if (null != followBodyMap.get("next_bmi") && !StringUtils.isBlank(followBodyMap.get("next_bmi").toString())) {
                    String nextBmi = followBodyMap.get("next_bmi").toString();
                    mbList.add("下次随访目标体质指数（BMI）为：" + nextBmi + "；\r\n");
                } else {
                    mbSet.add(5);
                }
                if (null != followBodyMap.get("next_weight") && !StringUtils.isBlank(followBodyMap.get("next_weight").toString())) {
                    String nextWeight = followBodyMap.get("next_weight").toString();
                    mbList.add("下次随访目标体重为：" + nextWeight + "kg；\r\n");
                } else {
                    mbSet.add(5);
                }
            }
        }
        //足背动脉搏动
        if (null != followBodyMap.get("zbdm") && !StringUtils.isBlank(followBodyMap.get("zbdm").toString())) {
            String zbdm = followBodyMap.get("zbdm").toString();
            String str = "";
            if ("ZB02".equals(zbdm) || "ZB05".equals(zbdm)) {
                str = "双侧";
            } else if ("ZB03".equals(zbdm) || "ZB06".equals(zbdm)) {
                str = "左侧";
            } else if ("ZB04".equals(zbdm) || "ZB07".equals(zbdm)) {
                str = "右侧";
            }
            if ("ZB02".equals(zbdm) || "ZB03".equals(zbdm) || "ZB04".equals(zbdm)) {
                wtList.add("足背动脉搏动：" + str + "减弱；\r\n");
                gjSet.add(17);
                mbSet.add(6);
            } else if ("ZB05".equals(zbdm) || "ZB06".equals(zbdm) || "ZB07".equals(zbdm)) {
                wtList.add("足背动脉搏动：" + str + "消失；\r\n");
                gjSet.add(18);
                mbSet.add(7);
            }
        }
        //生活方式
        //主食
        //吸烟
        if (null != followBodyMap.get("next_zs") && !StringUtils.isBlank(followBodyMap.get("next_zs").toString())) {
            String nextZs = followBodyMap.get("next_zs").toString();
            mbList.add("下次随访目标主食量为：" + nextZs + "克/天；\r\n");
        }
        if (null != followBodyMap.get("smoke_num") && !StringUtils.isBlank(followBodyMap.get("smoke_num").toString()) && !followBodyMap.get("smoke_num").toString().equals("0")) {
            wtList.add("有吸烟不良习惯；\r\n");
            gjSet.add(19);
            if (null != followBodyMap.get("next_smoke_num") && !StringUtils.isBlank(followBodyMap.get("next_smoke_num").toString())) {
                String nextSmokeNum = followBodyMap.get("next_smoke_num").toString();
                mbList.add("下次随访目标吸烟量为：" + nextSmokeNum + "支/天；\r\n");
            } else {
                mbSet.add(8);
            }
        }
        //饮酒
        if (null != followBodyMap.get("drink_num") && !StringUtils.isBlank(followBodyMap.get("drink_num").toString()) && !followBodyMap.get("drink_num").toString().equals("0")) {
            wtList.add("有喝酒不良习惯；\r\n");
            gjSet.add(20);
            if (null != followBodyMap.get("next_drink_num") && !StringUtils.isBlank(followBodyMap.get("next_drink_num").toString())) {
                String nextDrinkNum = followBodyMap.get("next_drink_num").toString();
                mbList.add("下次随访目标饮酒量：" + nextDrinkNum + "两/天；\r\n");
            } else {
                mbSet.add(9);
            }
        }
        //心里调整
        if (null != followBodyMap.get("xltz") && !StringUtils.isBlank(followBodyMap.get("xltz").toString())) {
            String xltz = followBodyMap.get("xltz").toString();
            if ("JG02".equals(xltz)) {
                wtList.add("心里调整一般；\r\n");
                gjSet.add(25);
                mbSet.add(14);
            } else if ("JG03".equals(xltz)) {
                wtList.add("心里调整差；\r\n");
                gjSet.add(25);
                mbSet.add(14);
            }
        }
        //遵医行为
        if (null != followBodyMap.get("zyxw") && !StringUtils.isBlank(followBodyMap.get("zyxw").toString())) {
            String zyxw = followBodyMap.get("zyxw").toString();
            if ("JG02".equals(zyxw)) {
                wtList.add("遵医行为一般；\r\n");
                gjSet.add(26);
                mbSet.add(15);
            } else if ("JG03".equals(zyxw)) {
                wtList.add("遵医行为差；\r\n");
                gjSet.add(26);
                mbSet.add(15);
            }
        }
        //运动习惯
        if ((null != followBodyMap.get("sport_num") && !StringUtils.isBlank(followBodyMap.get("sport_num").toString()) && !followBodyMap.get("sport_num").toString().equals("0")) || (null != followBodyMap.get("sport_time") && !StringUtils.isBlank(followBodyMap.get("sport_time").toString())) && !followBodyMap.get("sport_time").toString().equals("0")) {
            //有运动习惯
            //运动频率
            if (null != followBodyMap.get("sport_num") && !StringUtils.isBlank(followBodyMap.get("sport_num").toString())) {
                double sportNum = Double.parseDouble(followBodyMap.get("sport_num").toString());
                if (sportNum < 3) {
                    wtList.add("运动频率低（目前运动频率<3次/周）；\r\n");
                    gjSet.add(27);
                    if (null != followBodyMap.get("next_sport_num") && !StringUtils.isBlank(followBodyMap.get("next_sport_num").toString())) {
                        String nextSportNum = followBodyMap.get("next_sport_num").toString();
                        mbList.add("下次随访目标运动频率为：" + nextSportNum + "次/周；\r\n");
                    } else {
                        mbSet.add(16);
                    }
                } else if (sportNum >= 3 && sportNum <= 4) {
                    wtList.add("运动频率较低（目前运动频率3~4次/周）；\r\n");
                    gjSet.add(28);
                    if (null != followBodyMap.get("next_sport_num") && !StringUtils.isBlank(followBodyMap.get("next_sport_num").toString())) {
                        String nextSportNum = followBodyMap.get("next_sport_num").toString();
                        mbList.add("下次随访目标运动频率为：" + nextSportNum + "次/周；\r\n");
                    } else {
                        mbSet.add(16);
                    }
                }
            }
            //运动时长
            if (null != followBodyMap.get("sport_time") && !StringUtils.isBlank(followBodyMap.get("sport_time").toString())) {
                //运动频率
                double sportTime = Double.parseDouble(followBodyMap.get("sport_time").toString());
                if (sportTime < 20) {
                    wtList.add("运动时长不足（目前运动时长<20 分钟/次）；\r\n");
                    gjSet.add(29);
                    if (null != followBodyMap.get("next_sport_time") && !StringUtils.isBlank(followBodyMap.get("next_sport_time").toString())) {
                        String nextSportTime = followBodyMap.get("next_sport_time").toString();
                        mbList.add("下次随访目标运动时间为：" + nextSportTime + "分钟/次；\r\n");
                    } else {
                        mbSet.add(17);
                    }
                } else if (sportTime >= 20 && sportTime <= 30) {
                    wtList.add("运动时长不足（目前运动时长20~30 分钟/次）；\r\n");
                    gjSet.add(29);
                    if (null != followBodyMap.get("next_sport_time") && !StringUtils.isBlank(followBodyMap.get("next_sport_time").toString())) {
                        String nextSportTime = followBodyMap.get("next_sport_time").toString();
                        mbList.add("下次随访目标运动时间为：" + nextSportTime + "分钟/次；\r\n");
                    } else {
                        mbSet.add(17);
                    }
                }
            }
        } else {
            //无运动习惯
            wtList.add("无运动习惯；\r\n");
            gjSet.add(21);
            mbSet.add(10);
        }
        //辅助检查
        //空腹血糖
        if (null != followBodyMap.get("mq_fbg") && !StringUtils.isBlank(followBodyMap.get("mq_fbg").toString())) {
            double mqFbg = Double.parseDouble(followBodyMap.get("mq_fbg").toString());
            double lowKf = Double.parseDouble(range.getLowBeforeBreakfast()); //空腹血糖下限
            double highKf = Double.parseDouble(range.getHighBeforeBreakfast()); //空腹血糖上限
            if (mqFbg < lowKf) {
                wtList.add("空腹血糖偏低；\r\n");
                gjSet.add(30);
                mbSet.add(18);
            } else if (mqFbg > highKf) {
                wtList.add("空腹血糖偏高；\r\n");
                gjSet.add(31);
                mbSet.add(18);
            }
        }
        //糖化血红蛋白
        if (null != followBodyMap.get("lab_hba") && !StringUtils.isBlank(followBodyMap.get("lab_hba").toString())) {
            double labHba = Double.parseDouble(followBodyMap.get("lab_hba").toString());
            double highLabHba = Double.parseDouble(range.getHighGlycosylatedVal());  //糖化血红蛋白上限
            if (labHba > highLabHba) {
                wtList.add("您目前的糖化血红蛋白为："+labHba+"%血糖控制不佳；\r\n");
                gjSet.add(34);
                mbSet.add(19);
            }
        }
        //服药依从性
        if (null != followBodyMap.get("fyycx") && !StringUtils.isBlank(followBodyMap.get("fyycx").toString())) {
            String fyycx = followBodyMap.get("fyycx").toString();
            if ("YCX02".equals(fyycx) || "YCX03".equals(fyycx)) {
                wtList.add("间断不规范服药；\r\n");
                gjSet.add(35);
                mbSet.add(20);
            }
        }
        //药物不良反应
        if (null != followBodyMap.get("blfy") && !StringUtils.isBlank(followBodyMap.get("blfy").toString())) {
            String blfy = followBodyMap.get("blfy").toString();
            if ("1".equals(blfy)) {
                wtList.add("出现药物不良反应；\r\n");
                gjSet.add(36);
                mbSet.add(21);
            }
        }
        //低血糖反应
        if (null != followBodyMap.get("dxtfy") && !StringUtils.isBlank(followBodyMap.get("dxtfy").toString())) {
            String dxtfy = followBodyMap.get("dxtfy").toString();
            if ("2".equals(dxtfy) || "3".equals(dxtfy)) {
                wtList.add("出现低血糖反应；\r\n");
                gjSet.add(37);
                mbSet.add(22);
            }
        }

        //去重//////////////////////////
        Map<Integer, String> gjMapping = getGjMapping(range);
        for (Integer code : gjSet) {
            gjList.add(gjMapping.get(code));
        }
        Map<Integer, String> mbMapping = getMbMapping(range);
        for (Integer code : mbSet) {
            mbList.add(mbMapping.get(code));
        }

        reMap.put("wtList", wtList);
        reMap.put("gjList", gjList);
        reMap.put("mbList", mbList);
        return reMap;
    }


    //主要改进措施文案
    private static Map<Integer, String> getGjMapping(RangeBO range) {
        Map<Integer, String> outMap = new HashMap<>();
        //症状
        outMap.put(1, "糖尿病患者日常机体血糖代谢障碍，可引起血浆渗透压升高刺激口渴中枢，而出现多饮的症状，建议遵医嘱定期复查，严格控制血糖后，症状一般会随之好转；\r\n");
        outMap.put(2, "日常容易饥饿，多食，这跟糖尿病患者血糖高但是不能被有效利用有关，因机体能量不足会本能靠多进吃食物来弥补。建议遵医嘱定期复查，严格控制血糖后，症状一般会随之好转；\r\n");
        outMap.put(3, "糖尿病患者因处于高血糖状态，会直接引起尿糖升高，而出现渗透性利尿，出现多尿的症状。建议遵医嘱定期复查，严格控制血糖后，症状一般会随之好转；\r\n");
        outMap.put(4, "糖尿病容易引发眼部慢性并发症，出现视物模糊症状，需排除视网膜黄斑病变或白内障等的情况。建议及时专科诊治，定期复查观察变化；\r\n");
        outMap.put(5, "糖尿病患者由于血糖浓度高、体内代谢紊乱，抗病能力显著降低，容易引发神经、血管病变，而诱发各种感染，一旦出现，需及时就医，在医生指导下进行积极的抗感染，控糖等对症治疗；\r\n");
        outMap.put(6, "糖尿病患者日常出现手脚麻木、感觉障碍，像穿了袜子、戴了手套一样或伤口不易愈合等症状，需及时就医，排除糖尿病感觉运动性周围神经病变；\r\n");
        outMap.put(7, "糖尿病患者出现下肢浮肿，需积极配合医生进一步检查明确诊断，排除糖尿病肾病、心血管疾病等相关并发症的情况；\r\n");
        outMap.put(8, "糖尿病患者虽机体不能有效利用葡萄糖，机体总处于能量不足的状态，靠分解体内储存的脂肪和蛋白质来提供能量，身体能量储备逐渐消耗，而引起体重下降。建议就医进行综合治疗，期间积极配合合理的饮食和科学的运动；\r\n");

        //体征
        outMap.put(9, "您的血压偏高，建议内科就诊明确诊断。平时请用低盐饮食，适当运动，戒烟戒酒，避免情绪紧张，激动等，使血压保持在正常水平；\r\n");
        outMap.put(11, "您的血压偏低，建议专科诊治，日常适当增加体育运动，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤；\r\n");
        outMap.put(14, "您的体重偏轻，建议适当加强营养，平衡膳食，增强体质；\r\n");
        outMap.put(15, "您的体重已属于超重范围，超重是心血管病，血脂异常、糖尿病等疾病的危险因素，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例。坚持合理健身运动，控制体重；\r\n");
        outMap.put(16, "您的体重已属于肥胖范围，肥胖是心血管病，血脂异常、糖尿病等疾病的危险因素，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例。坚持合理健身运动，控制体重；\r\n");
        outMap.put(17, "足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足背动脉检查有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
        outMap.put(18, "足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足背动脉检查搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");

        //生活方式
        outMap.put(19, "您有日常吸烟习惯，吸烟对人体有百害而无一利，可引起呼吸系统、心血管系统等多种疾病，而对糖尿病人的危害就更大，可以加重糖代谢紊乱，增加糖尿病慢性并发症的发生率。所以对糖尿病来说更是需要绝对禁烟；\r\n");
        outMap.put(20, "您日常有饮酒习惯，关于糖尿病人饮酒，一般是不推荐。若饮酒应计算酒精中所含的热量在每日的饮食总热量中进行扣除。女性每天饮酒的酒精量不超过15g，男性不超过25g(15g酒精相当于450毫升啤酒、150毫升葡萄酒或50毫升低度白酒)，每周不超过2次。另外要注意避免空腹饮酒，预防酒精诱发的低血糖；\r\n");
        outMap.put(21, "运动能改善糖尿病发病的危险因素，同样也能改善葡萄糖耐量减低和空腹血糖受损状态，并可改善2型糖尿病个体胰岛素敏感性，从而预防和治疗糖尿病并发症。建议日常根据病情、体力及客观条件选择适合个人特点和兴趣的运动项目，保持中等强度、有节律性的有氧运动；\r\n");
        outMap.put(22, "早餐摄入过多不利于早餐后血糖的控制，建议您减少早餐热量摄入，直到早餐的主食占全天主食推荐量的1/3或1/5，避免煎炸、太稀的食物作为早餐。或者进行分餐，减少一份主食（一份主食相当于1片35g土司面包或1个35g馒头），放在早午餐之间加餐食用；\r\n");
        outMap.put(23, "午餐摄入过多不利于午餐后血糖的控制，建议您减少午餐热量摄入，直到午餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或75g的熟米饭或3块苏打饼干），放在午晚餐之间加餐食用；\r\n");
        outMap.put(24, "晚餐摄入过多不利于晚餐后血糖的控制，建议您减少晚餐热量摄入，直到晚餐的主食占全天主食推荐量的1/3或2/5。或者减少一份主食（一份主食相当于25g生大米或75g的熟米饭或3块苏打饼干），放在睡前加餐食用；\r\n");
        outMap.put(25, "心理情绪对糖尿病的防治与康复有及其重要的影响，从某种意义上讲，良好的心理胜于药物，对控制糖尿病的进展有着不可估量的作用。面对糖尿病，我们需要冷静思考，安心养病，不要被“终生疾病”所吓倒，积极乐观的情绪更容易控制病情；\r\n");
        outMap.put(26, "糖尿病是终身性疾病，积极配合专业的医护人员，逐渐的掌握科学的控糖方法（五驾马车），一步一个脚印的实现血糖目标，您会发现血糖的综合控制会变的简单、容易；\r\n");

        //运动
        outMap.put(27, "建议您每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天；\r\n");
        outMap.put(28, "建议您每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天；\r\n");
        outMap.put(29, "建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动；\r\n");

        //辅助检查
        outMap.put(30, "血糖偏低，注意预防低血糖，建议及时进餐或尝试适当进食点心。日常多监测血糖来减少低血糖的发生。；\r\n");
        outMap.put(31, "您目前的空腹血糖高了哦，空腹血糖高的原因有很多，如：晚餐吃得太晚太多、运动量不够、“苏木杰现象”或是“黎明现象”、睡眠问题等等引起，需要逐一排查，根据具体情况进行适宜的调整；\r\n");
//        outMap.put(30, "您目前的血糖属于正常范围偏低一点哦，要注意预防低血糖了，建议及时进餐或尝试适当进食点心。日常多监测血糖来减少低血糖的发生；\r\n");
//        outMap.put(32, "您已是血糖高危了哦，要引起重视了，建议及时排查下是否是与饮食用药有关，及时纠正或者就医复诊。期间如有出现三多一少症状加重或伴有恶心、呕吐、嗜睡等症状时要及时就医；\r\n");
//        outMap.put(33, "您目前属于极高危血糖情况，请立即就医治疗，以防出现高血糖高渗综合征（HHS）引起严重脱水和意识障碍昏迷等；\r\n");
        outMap.put(34, "糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，多注意饮食结构及运动，必要时在医生指导下调整治疗方案；\r\n");

        //反应
        outMap.put(35, "糖尿病病作为一种需终身治疗控制的慢性病，服药依从性的好坏直接影响病情的发展和治疗效果，日常多了解糖尿病相关知识，充分认识糖尿病潜在的危害，根据自己的具体状况，在医生指导下个体化选择适宜药物，提高用药依从性，使血糖持续达标；\r\n");
        outMap.put(36, "可向临床医生详细描述情况，医生会根据具体情况必要时调整用药，个体化的选择适宜的药物进行治疗；\r\n");
        outMap.put(37, "低血糖的预防措施：\n" +
                "①、定时定量进餐，如果进餐量减少则相对减少降糖药物剂量，有可能误餐时应提前做好准备，②、药物的使用，应该从小剂量开始，逐渐增加剂量，谨慎调整剂量，运动前应该增加额外的碳水化合物摄入，③、不要空腹饮酒，酒精能直接导致低血糖，应避免醺酒和空腹饮酒，④、严重、反复的低血糖应该调整糖尿病的治疗方案，并适当调整血糖控制目标，⑤、常规随身备用碳水化合物类食品，一旦发生低血糖，立即食用；\r\n");
        return outMap;
    }

    //预期达到目标文案
    private static Map<Integer, String> getMbMapping(RangeBO range) {
        Map<Integer, String> outMap = new HashMap<>();
        //体征
        outMap.put(1, "控制血压，直至舒张压：" + range.getLowDiastolicPress() + "～" + range.getHighDiastolicPress() + "mmHg，收缩压： " + range.getLowSystolicPress() + "～" + range.getHighSystolicPress() + "mmHg；\r\n");
        outMap.put(4, "增加体重，直至BMI控制在 18.5≤BMI＜24；\r\n");
        outMap.put(5, "减轻体重，直至BMI控制在 18.5≤BMI＜24；\r\n");
        outMap.put(6, "明确足背动脉搏动减弱病因，系统治疗，症状好转；\r\n");
        outMap.put(7, "明确足背动脉搏动消失病因，系统治疗，症状好转；\r\n");

        //生活方式
        outMap.put(8, "戒烟；\r\n");
        outMap.put(9, "限酒；\r\n");
        outMap.put(10, "保持科学规律的运动习惯；\r\n");
        outMap.put(11, "早餐的主食占全天主食推荐量的1/3或1/5；\r\n");
        outMap.put(12, "午餐的主食占全天主食推荐量的1/3或2/5；\r\n");
        outMap.put(13, "晚餐的主食占全天主食推荐量的1/3或2/5；\r\n");
        outMap.put(14, "保持积极乐观的情绪；\r\n");
        outMap.put(15, "积极配合医嘱进行个体化治疗、复查；\r\n");

        //运动
        outMap.put(16, "每周运动频率达到5天以上，两次运动的间隔时间不超过2天；\r\n");
        outMap.put(17, "保证一周进行至少150分钟的主动运动；\r\n");

        //辅助检查
        outMap.put(18, "控制空腹血糖，达到" + range.getLowBeforeBreakfast() + "-" + range.getHighBeforeBreakfast() + "mmol/L；\r\n");
        outMap.put(19, "控制糖化血红蛋白，≤" + range.getHighGlycosylatedVal() + "% ；\r\n");

        //反应
        outMap.put(20, "遵医嘱规范用药；\r\n");
        outMap.put(21, "减少不良反应；\r\n");
        outMap.put(22, "避免出现低血糖；\r\n");
        return outMap;
    }

    /**
     * @param sbp     收缩压
     * @param dbq     舒张压
     * @param lowSbp  收缩压下限
     * @param highSbp 收缩压上限
     * @param lowDbp  舒张压下限
     * @param highDbp 舒张压上限
     * @return true 一高一低
     */
    private static boolean checkXY(Double sbp, Double dbq, Double lowSbp, Double highSbp, Double lowDbp, Double highDbp) {
        if ((sbp < lowSbp && dbq > highDbp) || (sbp > highSbp && dbq < lowDbp)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取高血压基位随访输出
     * @param followBodyMap
     * @return
     */
    public static Map<String ,Object> outHypJwFollow(HypJwFollowBodyBO hypJwFollowBody , RangeBO rangeBO){
        Map<String ,Object> result = new HashMap<>();
        //改进措施
        LinkedHashSet<String> gjList = new LinkedHashSet<>();
        //目标
        LinkedHashSet<String> mbList = new LinkedHashSet<>();
        //问题
        LinkedHashSet<String> wtList = new LinkedHashSet<>();
        //症状逻辑输出
        hypJwSymptomOutHandler(gjList ,mbList ,wtList ,hypJwFollowBody);
        //生活习惯
        lifeModelOutHandler(gjList , mbList ,wtList, hypJwFollowBody);
        //辅助检查
        hypFollowAssistOutHandler(gjList, mbList ,wtList ,hypJwFollowBody);
        //体征逻辑输出
        hypJwSignHandler(gjList ,mbList ,wtList ,hypJwFollowBody ,rangeBO);

        result.put("gjList" ,gjList);
        result.put("mbList" ,mbList);
        result.put("wtList" ,wtList);
        return result;
    }

    /**
     * 高血压基位随访 - 体征逻辑数据
     * @param gjList
     * @param mbList
     * @param wtList
     * @param hypJwFollowBodyBO
     * @param rangeBO
     */
    private static void hypJwSignHandler(LinkedHashSet<String> gjList ,LinkedHashSet<String> mbList , LinkedHashSet<String> wtList ,HypJwFollowBodyBO hypJwFollowBodyBO ,RangeBO rangeBO){
        //bmi 逻辑
        if(hypJwFollowBodyBO.getBmi() != null){
            float bmi = hypJwFollowBodyBO.getBmi();
            if(bmi < 18.5f){
                wtList.add("BMI值为：" + hypJwFollowBodyBO.getBmi() +"，消瘦");
                gjList.add("您的体重偏轻，建议适当加强营养，平衡膳食，增强体质。");
                mbList.add("增加体重，直至BMI控制在18.5～23.9之间");
            }else if(bmi >= 24.0f && bmi <= 27.9f){
                wtList.add("BMI值为：" + hypJwFollowBodyBO.getBmi() +"，超重");
                gjList.add("您的体重已属于超重范围，超重是高血压、心血管病，血脂异常、糖尿病等疾病的危险因素，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重。");
                mbList.add("减轻体重，直至BMI控制在18.5～23.9之间");
            }else if(bmi >= 28.0f){
                wtList.add("BMI值为：" + hypJwFollowBodyBO.getBmi() +"，肥胖");
                gjList.add("您的体重已属于肥胖范围，肥胖是高血压、心血管病，血脂异常、糖尿病等疾病的危险因素，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重。");
                mbList.add("减轻体重，直至BMI控制在18.5～23.9之间");
            }
        }
        //心率逻辑
        if(hypJwFollowBodyBO.getHeartRate() != null){
            int rate = hypJwFollowBodyBO.getHeartRate();
            if(rate < 60){
                wtList.add("静息心率："  + rate + "次/分钟，心动过缓");
                gjList.add("心率偏慢，建议专科诊治，进一步检查明确心动过缓病因。");
                mbList.add("安静状态下心率保持在60-100 次/分钟");
            }else if(rate > 100){
                wtList.add("静息心率："  + rate + "次/分钟，心动过速");
                gjList.add("心率偏快，建议专科诊治，进一步检查明确心动过速病因。");
                mbList.add("安静状态下心率保持在60-100 次/分钟");
            }
        }
        //血压
        if(!StringUtils.isBlank(hypJwFollowBodyBO.getHypType())){
            int age = DateHelper.getAge(hypJwFollowBodyBO.getBirthday());
            if(age >= 18){
                /**
                 * "舒张压＜患者控制目标舒张压最小值mmHg
                 * 收缩压＜患者控制目标收缩压最小值mmHg"
                 */
                boolean lowPressure = (hypJwFollowBodyBO.getDbp() != null && hypJwFollowBodyBO.getDbp() < Float.parseFloat(rangeBO.getLowDiastolicPress()))
                        || (hypJwFollowBodyBO.getSbp() != null && hypJwFollowBodyBO.getSbp() < Float.parseFloat(rangeBO.getLowSystolicPress()));
                /**
                 * 年龄＜65岁	"舒张压≥患者控制目标舒张压最大值mmHg
                 *  收缩压≥患者控制目标收缩压最大值mmHg"
                 */
                boolean highPressure = age < 65 && ((hypJwFollowBodyBO.getDbp() != null && hypJwFollowBodyBO.getDbp() > Float.parseFloat(rangeBO.getHighDiastolicPress()))
                        || (hypJwFollowBodyBO.getSbp() != null && hypJwFollowBodyBO.getSbp() > Float.parseFloat(rangeBO.getHighSystolicPress())));
                /**
                 * 年龄＞=65岁：	"舒张压≥90mmHg
                 *    收缩压≥150mmHg"
                 */
                boolean olderHighPressure = age >=65 && ((hypJwFollowBodyBO.getDbp() != null && hypJwFollowBodyBO.getDbp() > 90)
                        || (hypJwFollowBodyBO.getSbp() != null && hypJwFollowBodyBO.getSbp() > 150));
                String nowText = MessageFormat.format("您目前血压（收缩压/舒张压）：{0}/{1}mmHg"
                        , hypJwFollowBodyBO.getSbp() != null ? hypJwFollowBodyBO.getSbp() : "-"
                        , hypJwFollowBodyBO.getDbp() != null ? hypJwFollowBodyBO.getDbp() : "-");
                String targetText = "控制血压，直至{0}mmHg≤舒张压＜{1}mmHg， {2}mmHg≤收缩压＜{3}mmHg";
                String targetText1 = "控制血压，直至{0}mmHg≤舒张压≤{1}mmHg， {2}mmHg≤收缩压≤{3}mmHg";
                if(olderHighPressure){
                    wtList.add(nowText + "，血压偏高");
                    gjList.add("血压偏高，建议内科复诊，调整用药，同时关注血糖、血脂及动脉硬化的防治，控制膳食总热量，用低糖、低脂、低盐、高纤维素饮食，坚持有氧运动，控制体重，戒烟限酒，定期专科复查诊治。");
                    mbList.add(MessageFormat.format(targetText , 60 ,90 ,90 ,140));
                }else if(highPressure){
                    wtList.add(nowText + "，大于理想范围");
                    gjList.add(MessageFormat.format("您本次血压超过理想控制范围（≤{0}/{1}mmHg），建议专科复诊，在日常生活中注意减盐限盐、平衡膳食、戒烟限酒、控制体重、坚持科学运动并保持心态平和，有助于您血压的控制。"
                            ,rangeBO.getHighSystolicPress() ,rangeBO.getHighDiastolicPress()));
                    mbList.add(MessageFormat.format(targetText1 , rangeBO.getLowDiastolicPress() ,rangeBO.getHighDiastolicPress() ,rangeBO.getLowSystolicPress() ,rangeBO.getHighSystolicPress()));
                }else if(lowPressure){
                    wtList.add(nowText + "，血压偏低");
                    gjList.add("血压偏低，建议专科诊治，日常进行科学的体育运动锻炼，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤。");
                    mbList.add(MessageFormat.format(targetText1 , rangeBO.getLowDiastolicPress() ,rangeBO.getHighDiastolicPress() ,rangeBO.getLowSystolicPress() ,rangeBO.getHighSystolicPress()));
                }
            }
        }
    }

    /**
     * 高血压基位随访 -症状输出处理
     * @param gjList
     * @param mbList
     * @param wtList
     * @param hypJwFollowBodyBO
     */
    private static void hypJwSymptomOutHandler(LinkedHashSet<String> gjList ,LinkedHashSet<String> mbList , LinkedHashSet<String> wtList ,HypJwFollowBodyBO hypJwFollowBodyBO){
        String symptom = hypJwFollowBodyBO.getHypSymptom();
        //无症状
        if(StringUtils.isBlank(symptom) || symptom.equals("GXYZZ01")){
            return;
        }
        //症状列表
        List<String> symptomList = new ArrayList<>(Arrays.asList(symptom.split(",")));

        //开始处理分析提醒
        /**
         * 1、头痛头晕
         * 2、眼花耳鸣
         */
        if(symptomList.contains("GXYZZ02") ){
            gjList.add("头痛头晕、眼花耳鸣是高血压患者常见的症状表现，一般在血压控制不佳的时候，症状会比较明显，建议到医院专科复诊，在医生指导下根据具体情况进行适宜调整。");
            wtList.add(getSymptomProblemText("GXYZZ02"));
        }
        if(symptomList.contains("GXYZZ04")){
            gjList.add("头痛头晕、眼花耳鸣是高血压患者常见的症状表现，一般在血压控制不佳的时候，症状会比较明显，建议到医院专科复诊，在医生指导下根据具体情况进行适宜调整。");
            wtList.add(getSymptomProblemText("GXYZZ04"));
        }
        /**
         * 3、恶心呕吐　
         */
        if(symptomList.contains("GXYZZ03")){
            gjList.add("高血压患者日常出现恶心呕吐的不适症状，需要警惕高血压控制不佳，引起心脑血管疾病的情况，建议就医复诊，及时控制好血压。");
            wtList.add(getSymptomProblemText("GXYZZ03"));
        }
        /**
         * 4、呼吸困难　
         * 5、心悸胸闷
         */
        if(symptomList.contains("GXYZZ05")){
            gjList.add("高血压患者日常出现心悸胸闷、或呼吸困难等不适症状，需要警惕心血管疾病的发生，建议及时到医院就诊，明确病因，然后进行针对性治疗。");
            wtList.add(getSymptomProblemText("GXYZZ05"));
        }
        if(symptomList.contains("GXYZZ06")){
            gjList.add("高血压患者日常出现心悸胸闷、或呼吸困难等不适症状，需要警惕心血管疾病的发生，建议及时到医院就诊，明确病因，然后进行针对性治疗。");
            wtList.add(getSymptomProblemText("GXYZZ06"));
        }
        /**
         *6、鼻衄出血不止
         */
        if(symptomList.contains("GXYZZ07")){
            gjList.add("急性鼻出血是高血压病人的常见并发症状之一，日常如出现出血量大、持续不止，可先进行局部冷敷，然后及时就医处理，日常注意控制好血压。");
            wtList.add(getSymptomProblemText("GXYZZ07"));
        }
        /**
         * 7、四肢发麻
         */
        if(symptomList.contains("GXYZZ08")){
            gjList.add("高血压患者日常出现手脚麻木、或者感觉障碍，像穿了袜子、戴了手套一样等症状，需及时就医，排除周围神经病变。");
            wtList.add(getSymptomProblemText("GXYZZ08"));
        }
        /**
         * 8、下肢水肿
         */
        if(symptomList.contains("GXYZZ09")){
            gjList.add("高血压患者出现下肢浮肿，需积极配合医生进一步检查明确诊断，排除肾病、心血管疾病等相关并发症的情况。");
            wtList.add(getSymptomProblemText("GXYZZ09"));
        }
        if(symptomList.contains("GXYZZ10")){
            wtList.add(hypJwFollowBodyBO.getHypSymptomOtherText());
        }
        String symptomText = null;
        //症状文案列表
        List<String> symptomTextList = new ArrayList<>();
        for(String s : symptomList){
            if("GXYZZ10".equals(s)){
                symptomText = hypJwFollowBodyBO.getHypSymptomOtherText();
            }else{
                symptomText = "症状" + SYMPTOM_TEXT.get(s);
            }
            if(StringUtils.isBlank(symptomText)){
                continue;
            }
            symptomTextList.add(symptomText);
        }
        //开始处理预期目标
        if(symptomTextList.size() > 0){
            String target = MessageFormat.format("“{0}”症状逐渐好转" , Joiner.on("、").join(symptomTextList));
            mbList.add(target);
        }
    }

    /**
     * 获取目前症状问题文案
     * @param s
     * @return
     */
    private static String getSymptomProblemText(String s){
        return MessageFormat.format("有“{0}”症状" ,SYMPTOM_TEXT.get(s));
    }

    /**
     * 高血压基位随访 - 生活方式逻辑输出
     * @param gjList
     * @param mbList
     * @param wtList
     * @param hypJwFollowBodyBO
     */
    private static void lifeModelOutHandler(LinkedHashSet<String> gjList ,LinkedHashSet<String> mbList , LinkedHashSet<String> wtList ,HypJwFollowBodyBO hypJwFollowBodyBO){
        //日吸烟量
        if(hypJwFollowBodyBO.getSmokeNum() != null && hypJwFollowBodyBO.getSmokeNum() > 0){
            wtList.add("有吸烟不良习惯");
            gjList.add("您有日常吸烟习惯，吸烟对人体有百害而无一利，对高血压病人的危害更大，可导致血管内皮损害，显著增加高血压患者发生动脉粥样硬化性疾病的风险，是心血管病和癌症的主要危险因素之一，被动吸烟也是一样。所以对高血压来说更是需要绝对禁烟。");
            mbList.add("戒烟");
        }
        //日饮酒量
        if(hypJwFollowBodyBO.getDrinkNum() != null && hypJwFollowBodyBO.getDrinkNum() > 0){
            wtList.add("有喝酒不良习惯");
            mbList.add("限酒");
            gjList.add("您日常有饮酒习惯，关于高血压人饮酒，一般是不推荐。若饮酒应计算酒精中所含的热量在每日的饮食总热量中进行扣除。女性每天饮酒的酒精量不超过15g，男性不超过25g(15g酒精相当于450毫升啤酒、150毫升葡萄酒或50毫升低度白酒)，每周不超过2次。");
        }
        //心理调整
        if("JG02".equals(hypJwFollowBodyBO.getHeartAdjust()) || "JG03".equals(hypJwFollowBodyBO.getHeartAdjust())){
            wtList.add("心里调整一般或差");
            mbList.add("保持积极乐观的情绪");
            gjList.add("心理情绪对高血压的防治与康复有及其重要的影响，从某种意义上讲，良好的心理胜于药物，对血压的稳定控制有着不可估量的作用。面对高血压，我们需要冷静思考，安心养病，不要被“终生疾病”所吓倒，积极乐观的情绪更容易控制病情。");
        }
        //遵医行为
        if("JG02".equals(hypJwFollowBodyBO.getComplianceAction()) || "JG03".equals(hypJwFollowBodyBO.getComplianceAction())){
            wtList.add("遵医行为一般或差");
            mbList.add("积极配合医嘱进行个体化治疗、复查");
            gjList.add("高血压虽然是终身性疾病，日常只要积极配合专业的医护人员，逐渐的掌握科学的治疗方案，一步一个脚印的实现控压目标，您会发现高血压的平稳控制会逐渐变的简单、容易。");
        }
        //运  动
        //无运动习惯
        if(StringUtils.isBlank(hypJwFollowBodyBO.getSportRate()) && StringUtils.isBlank(hypJwFollowBodyBO.getSportTime())){
            wtList.add("无运动习惯");
            mbList.add("保持科学规律的运动习惯");
            gjList.add("规律的有氧运动是高血压患者治疗性生活方式改善的重要途径，能改变体质，调节心情，改善高血压发病的危险因素。建议日常根据病情、体力及客观条件选择适合个人特点和兴趣的运动项目，保持低中等强度、有节律性的有氧运动。");
        }
        //有运动习惯
        else{
            //运动频率
            //3-4次／周
            if("YDPL05".equals(hypJwFollowBodyBO.getSportRate())){
                wtList.add("运动频率较低（目前运动频率3~4次/周）");
                mbList.add("每周运动频率达到5天以上，两次运动的间隔时间不超过2天");
                gjList.add("建议您每周增加1天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
            }
            //<3次/周
            else if("YDPL03".equals(hypJwFollowBodyBO.getSportRate())){
                wtList.add("运动频率低（目前运动频率<3次/周）");
                mbList.add("每周运动频率达到5天以上，两次运动的间隔时间不超过2天");
                gjList.add("建议您每周增加2天的运动，每天至少30分钟的中等强度运动，直到每周运动频率达到5天以上，每周的运动时间不少于150分钟，两次运动的间隔时间不超过2天。");
            }
            //运动时长
            //<20分钟
            if("BSYDSJ01".equals(hypJwFollowBodyBO.getSportTime())){
                wtList.add("运动时长不足（目前运动时长<20 分钟/次）");
                mbList.add("保证一周进行至少150分钟的主动运动");
                gjList.add("建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
            }
            //20~30分钟
            else if("BSYDSJ04".equals(hypJwFollowBodyBO.getSportTime())){
                wtList.add("运动时长不足（目前运动时长20~30 分钟/次）");
                mbList.add("保证一周进行至少150分钟的主动运动");
                gjList.add("建议每次运动的时长增加15分钟，直到每次运动30分钟以上，或增加运动频次，保证一周进行至少150分钟的主动运动");
            }
        }
        //盐的逻辑  重 /中
        if("SYQK03".equals(hypJwFollowBodyBO.getSaltDay()) || "SYQK02".equals(hypJwFollowBodyBO.getSaltDay())){
            wtList.add("摄盐量偏多");
            mbList.add("摄盐食盐摄入量逐步降至＜6g");
            gjList.add("日常减少钠盐的摄入，每人每日食盐摄入量逐步降至＜6g，并适当增加钾的摄入");
        }
    }

    /**
     * 高血压 基位随访输出 - 辅助检查逻辑输出
     * @param gjList
     * @param mbList
     * @param wtList
     * @param hypJwFollowBodyBO
     */
    private static void hypFollowAssistOutHandler(LinkedHashSet<String> gjList ,LinkedHashSet<String> mbList , LinkedHashSet<String> wtList ,HypJwFollowBodyBO hypJwFollowBodyBO){
        //服药依从性
        if("YCX02".equals(hypJwFollowBodyBO.getDrugCompliance()) || "YCX03".equals(hypJwFollowBodyBO.getDrugCompliance()) ){
            wtList.add("服药依从性差");
            mbList.add("提高服药依从性");
            gjList.add("高血压为终身性疾病，需要长期服药，所以良好的服药依从性显得尤为重要，服药依从是药物治疗有效性的基础。而影响服药依从性的因素众多，涉及患者、医务人员、社会、家庭等各方面，需要多方沟通找出原因，及时进行针对性的调整，提高服药依从性");
        }
        //药物不良反应
        if("1".equals(hypJwFollowBodyBO.getSideEffects())){
            wtList.add("有药物不良反应");
            mbList.add("减少药物不良反应");
            gjList.add("服药期间有出现药物不良反应的情况，需及时向您的经治医生进行反映，经综合评估必要时医生会给予相应治疗药物的调整。");
        }
    }

    /**
     * 症状code对应的文案
     */
    public final static Map<String ,String> SYMPTOM_TEXT = new HashMap<>();
    static {
        SYMPTOM_TEXT.put("GXYZZ01" ,"无症状");
        SYMPTOM_TEXT.put("GXYZZ02" ,"头痛头晕");
        SYMPTOM_TEXT.put("GXYZZ03" ,"恶心呕吐");
        SYMPTOM_TEXT.put("GXYZZ04" ,"眼花耳鸣");
        SYMPTOM_TEXT.put("GXYZZ05" ,"呼吸困难");
        SYMPTOM_TEXT.put("GXYZZ06" ,"心悸胸闷");
        SYMPTOM_TEXT.put("GXYZZ07" ,"鼻衄出血不止");
        SYMPTOM_TEXT.put("GXYZZ08" ,"四肢麻木");
        SYMPTOM_TEXT.put("GXYZZ09" ,"下肢水肿");
        SYMPTOM_TEXT.put("GXYZZ10" ,"其他");
    }

}


