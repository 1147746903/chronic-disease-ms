package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.*;

/** 糖尿病随访表智能建议
 * @author wyc
 * @date 2019/9/29 10:35
 */
public class FollowTnbHelper {
    public static Map<String,Object> outFollowTnb(Map<String, Object> followBodyMap, RangeBO range){
        Map<String, Object> reMap = new HashMap<>();

        List<String> wtList = new ArrayList<>();//问题集合
        List<String> gjList = new ArrayList<>();//改进集合
        List<String> mbList = new ArrayList<>();//目标集合

        Set<Integer> gjSet = new LinkedHashSet<>();//改进去重
        Set<Integer> mbSet = new LinkedHashSet<>();//目标去重
        String sex = "" ;  //性别  男 女
        if (null != followBodyMap.get("sex") && !StringUtils.isBlank(followBodyMap.get("sex").toString())){
            sex = followBodyMap.get("sex").toString();
        }
        //BMI
        if (null != followBodyMap.get("bmi2") && !StringUtils.isBlank(followBodyMap.get("bmi2").toString()) && !"--".equals(followBodyMap.get("bmi2").toString())){
            double bmi = Double.parseDouble(followBodyMap.get("bmi2").toString());
            double lowBmi = Double.parseDouble(range.getLowBmi()); //BMI下限
            double highBmi = Double.parseDouble(range.getHighBmi()); //BMI上限
            if(bmi < lowBmi){
                String wt = "BMI值为："+bmi+"，消瘦；\r\n";
                wtList.add(wt);
                gjSet.add(1);
                mbSet.add(1);
            }else if(highBmi < bmi && bmi < 28){
                String wt = "BMI值为："+bmi+"，超重；\r\n";
                wtList.add(wt);
                gjSet.add(2);
                mbSet.add(2);
            }else if(bmi >= 28){
                String wt = "BMI值为："+bmi+"，肥胖；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                mbSet.add(2);
            }
        }
        //腰围
        if (null != followBodyMap.get("waistline2") && !StringUtils.isBlank(followBodyMap.get("waistline2").toString())){
            double waistline = Double.parseDouble(followBodyMap.get("waistline2").toString());
            if ("男".equals(sex)){
                if (waistline >= 90){
                    String wt = "腰围："+waistline+"cm,中心型肥胖；\r\n";
                    wtList.add(wt);
                    gjSet.add(4);
                    mbSet.add(3);
                }
            }else if ("女".equals(sex)){
                if (waistline >= 85){
                    String wt = "腰围："+waistline+"cm,中心型肥胖；\r\n";
                    wtList.add(wt);
                    gjSet.add(4);
                    mbSet.add(4);
                }
            }
        }
        //血压
        if (null != followBodyMap.get("sbp") && null != followBodyMap.get("dbp") && !StringUtils.isBlank(followBodyMap.get("sbp").toString()) && !StringUtils.isBlank(followBodyMap.get("dbp").toString())){
            double sbp = Double.parseDouble(followBodyMap.get("sbp").toString());
            double dbp = Double.parseDouble(followBodyMap.get("dbp").toString());

            double lowSbp = Double.parseDouble(range.getLowSystolicPress());  //收缩压下限
            double highSbp = Double.parseDouble(range.getHighSystolicPress()); //收缩压上限
            double lowDbp = Double.parseDouble(range.getLowDiastolicPress());  //舒张压下限
            double highDbp = Double.parseDouble(range.getHighDiastolicPress()); //舒张压上限
            if (dbp > highDbp || sbp > highSbp){
                String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏高；\r\n";
                wtList.add(wt);
                gjSet.add(5);
                mbSet.add(5);
            }else if (dbp < lowDbp || sbp < lowSbp){
                String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏低；\r\n";
                wtList.add(wt);
                gjSet.add(6);
                mbSet.add(5);
            }
        }
        //空腹血糖
        if (null !=  followBodyMap.get("mq_fbg2") && !StringUtils.isBlank(followBodyMap.get("mq_fbg2").toString())){
            double mqFbg = Double.parseDouble(followBodyMap.get("mq_fbg2").toString());
            double lowKf = Double.parseDouble(range.getLowBeforeBreakfast());  //空腹血糖下限
            double highKf = Double.parseDouble(range.getHighBeforeBreakfast());  //空腹血糖上限
            if (mqFbg < lowKf){
                String wt = "您目前的空腹血糖为："+mqFbg+"mmol/l，空腹血糖偏低；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                mbSet.add(6);
            }else if (mqFbg > highKf){
                String wt = "您目前的空腹血糖为："+mqFbg+"mmol/l，空腹血糖偏高；\r\n";
                wtList.add(wt);
                gjSet.add(8);
                mbSet.add(6);
            }
        }
        //糖化血红蛋白
        if (null != followBodyMap.get("thxhdb2") && !StringUtils.isBlank(followBodyMap.get("thxhdb2").toString())){
            double labHba = Double.parseDouble(followBodyMap.get("thxhdb2").toString());
            double highTh = Double.parseDouble(range.getHighGlycosylatedVal());  //糖化血红蛋白上限
            if(labHba > highTh){
                String wt = "您目前的糖化血红蛋白值为："+labHba+"%（偏高），血糖控制不佳；\r\n";
                wtList.add(wt);
                gjSet.add(9);
                mbSet.add(7);
            }
        }
        //眼底照相（异常）
        String ydzxLeft = followBodyMap.get("ydzx_type1") == null ? "" : followBodyMap.get("ydzx_type1").toString();
        String ydzxRight = followBodyMap.get("ydzx_type2") == null ? "" : followBodyMap.get("ydzx_type2").toString();
        if ("".equals(ydzxLeft)){
            if ("2".equals(ydzxRight)){
                String wt = "右眼异常；\r\n";
                wtList.add(wt);
                gjSet.add(32);
                mbSet.add(8);
            }
        }else if ("1".equals(ydzxLeft)){
            if ("2".equals(ydzxRight)){
                String wt = "左眼正常,右眼异常；\r\n";
                wtList.add(wt);
                gjSet.add(32);
                mbSet.add(8);
            }
        }else if ("2".equals(ydzxLeft)){
            if("".equals(ydzxRight)){
                String wt = "左眼异常；\r\n";
                wtList.add(wt);
                gjSet.add(31);
                mbSet.add(8);
            }else if ("1".equals(ydzxRight)){
                String wt = "左眼异常,右眼正常；\r\n";
                wtList.add(wt);
                gjSet.add(31);
                mbSet.add(8);
            }else if ("2".equals(ydzxRight)){
                String wt = "眼底照相异常；\r\n";
                wtList.add(wt);
                gjSet.add(10);
                mbSet.add(8);
            }else if ("3".equals(ydzxRight)){
                String wt = "左眼异常,右眼未查；\r\n";
                wtList.add(wt);
                gjSet.add(31);
                mbSet.add(8);
            }
        }else if ("3".equals(ydzxLeft)){
            if ("2".equals(ydzxRight)){
                String wt = "左眼未查,右眼异常；\r\n";
                wtList.add(wt);
                gjSet.add(32);
                mbSet.add(8);
            }
        }

        //ABI
        Double abiLeft = null;
        Double abiRight = null;
        if (null != followBodyMap.get("abi1_l") && !StringUtils.isBlank(followBodyMap.get("abi1_l").toString())){
            abiLeft = Double.parseDouble(followBodyMap.get("abi1_l").toString());
        }
        if (null != followBodyMap.get("abi1_r") && !StringUtils.isBlank(followBodyMap.get("abi1_r").toString())){
            abiRight = Double.parseDouble(followBodyMap.get("abi1_r").toString());
        }
        if (null != abiLeft){
            if (abiLeft > 1.3){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(33);
                    mbSet.add(9);
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(11);
                    mbSet.add(9);
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足动脉钙化,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(34);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足动脉钙化,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(35);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足动脉钙化,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(36);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足动脉钙化,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(37);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足动脉钙化,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(38);
                    mbSet.add(9);
                }
            }else if (abiLeft >= 1 && abiLeft <= 1.3){
                if (null != abiRight){
                    if (abiRight > 1.3){
                        String wt = "ABI检查提示：左足正常,右足动脉钙化；\r\n";
                        wtList.add(wt);
                        gjSet.add(39);
                        mbSet.add(9);
                    }else if (abiRight >= 0.91 && abiRight <= 0.99){
                        String wt = "ABI检查提示：左足正常,右足临界值；\r\n";
                        wtList.add(wt);
                        gjSet.add(40);
                        mbSet.add(9);
                    }else if (abiRight >= 0.71 && abiRight <= 0.9){
                        String wt = "ABI检查提示：左足正常,右足轻度动脉狭窄；\r\n";
                        wtList.add(wt);
                        gjSet.add(41);
                        mbSet.add(9);
                    }else if (abiRight >= 0.41 && abiRight <= 0.7){
                        String wt = "ABI检查提示：左足正常,右足中度动脉狭窄；\r\n";
                        wtList.add(wt);
                        gjSet.add(42);
                        mbSet.add(9);
                    }else if (abiRight >= 0 && abiRight <= 0.4){
                        String wt = "ABI检查提示：左足正常,右足重度病变；\r\n";
                        wtList.add(wt);
                        gjSet.add(43);
                        mbSet.add(9);
                    }
                }
            }else if (abiLeft >= 0.91 && abiLeft <= 0.99){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(44);
                    mbSet.add(9);
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足临界值,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(45);
                    mbSet.add(9);
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足临界值,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(46);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(12);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足临界值,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(47);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足临界值,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(48);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足临界值,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(49);
                    mbSet.add(9);
                }
            }else if (abiLeft >= 0.71 && abiLeft <= 0.9){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(50);
                    mbSet.add(9);
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(51);
                    mbSet.add(9);
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(52);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(53);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(13);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(54);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(55);
                    mbSet.add(9);
                }
            }else if (abiLeft >= 0.41 && abiLeft <= 0.7){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(56);
                    mbSet.add(9);
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(57);
                    mbSet.add(9);
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(58);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(59);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(60);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(14);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(61);
                    mbSet.add(9);
                }
            }else if (abiLeft >= 0 && abiLeft <=0.4){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(62);
                    mbSet.add(9);
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足重度病变,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(63);
                    mbSet.add(9);
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足重度病变,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(64);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足重度病变,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(65);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足重度病变,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(66);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足重度病变,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(67);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(15);
                    mbSet.add(9);
                }
            }
        }else {
            if (null != abiRight){
                if (abiRight > 1.3){
                    String wt = "ABI检查提示：右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(68);
                    mbSet.add(9);
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(69);
                    mbSet.add(9);
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(70);
                    mbSet.add(9);
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(71);
                    mbSet.add(9);
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(72);
                    mbSet.add(9);
                }
            }
        }
        //感觉阈值 vpt
        String vptLeft = "";
        String vptRight = "";
        if(null != followBodyMap.get("gzfz_type1") && !StringUtils.isBlank(followBodyMap.get("gzfz_type1").toString())){
            vptLeft = followBodyMap.get("gzfz_type1").toString();
        }
        if(null != followBodyMap.get("gzfz_type2") && !StringUtils.isBlank(followBodyMap.get("gzfz_type2").toString())){
            vptRight = followBodyMap.get("gzfz_type2").toString();
        }
        if ("".equals(vptLeft)){
            if ("2".equals(vptRight)){
                String wt = "VPT检查提示：右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(73);
                mbSet.add(9);
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(74);
                mbSet.add(9);
            }else if ("4".equals(vptRight)){
                String wt = "VPT检查提示：右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(75);
                mbSet.add(9);
            }
        }else if ("1".equals(vptLeft)){
            if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(76);
                mbSet.add(9);
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(77);
                mbSet.add(9);
            }else if ("4".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(78);
                mbSet.add(9);
            }
        }else if ("2".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(79);
                mbSet.add(9);
            } else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足正常；\r\n";
                wtList.add(wt);
                gjSet.add(80);
                mbSet.add(9);
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：临界值；\r\n";
                wtList.add(wt);
                gjSet.add(16);
                mbSet.add(9);
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(81);
                mbSet.add(9);
            }else if ("4".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(82);
                mbSet.add(9);
            }
        }else if ("3".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(83);
                mbSet.add(9);
            }else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足正常；\r\n";
                wtList.add(wt);
                gjSet.add(84);
                mbSet.add(9);
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(85);
                mbSet.add(9);
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(17);
                mbSet.add(9);
            }else if ("4".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(86);
                mbSet.add(9);
            }
        }else if ("4".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(87);
                mbSet.add(9);
            }else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足正常；\r\n";
                wtList.add(wt);
                gjSet.add(88);
                mbSet.add(9);
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(89);
                mbSet.add(9);
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(90);
                mbSet.add(9);
            }else if ("4".equals(vptRight)){
                String wt = "VPT检查提示：感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(18);
                mbSet.add(9);
            }
        }

        //总胆固醇
        if (null != followBodyMap.get("dgc2") && !StringUtils.isBlank(followBodyMap.get("dgc2").toString())){
            double tc = Double.parseDouble(followBodyMap.get("dgc2").toString());
            double highTc = Double.parseDouble(range.getHighTCholesterol());  //总胆固醇上限
            if(tc > highTc){
                String wt = "总胆固醇升高；\r\n";
                wtList.add(wt);
                gjSet.add(19);
                mbSet.add(10);
            }
        }
        //甘油三酯
        if (null != followBodyMap.get("gysz2") && !StringUtils.isBlank(followBodyMap.get("gysz2").toString())){
            double tg = Double.parseDouble(followBodyMap.get("gysz2").toString());
            double highTg = Double.parseDouble(range.getHighTriglyceride());  //甘油三酯上限
            if(tg > highTg){
                String wt = "甘油三酯升高；\r\n";
                wtList.add(wt);
                gjSet.add(20);
                mbSet.add(11);
            }
        }
        //低密度脂蛋白
        if (null != followBodyMap.get("dmdzdb2") && !StringUtils.isBlank(followBodyMap.get("dmdzdb2").toString())){
            double ldl = Double.parseDouble(followBodyMap.get("dmdzdb2").toString());
            double highLdl = Double.parseDouble(range.getHighLDLCholesterol()); //低密度脂蛋上限
            if(ldl >highLdl){
                String wt = "低密度脂蛋白升高；\r\n";
                wtList.add(wt);
                gjSet.add(22);
                mbSet.add(12);
            }
        }
        //高密度脂蛋白
        if (null != followBodyMap.get("gmdzdb2") && !StringUtils.isBlank(followBodyMap.get("gmdzdb2").toString())){
            double hdl = Double.parseDouble(followBodyMap.get("gmdzdb2").toString());
            double lowHdl = Double.parseDouble(range.getLowHDLCholesterol());//高密度脂蛋白下限
            if(hdl < lowHdl){
                String wt = "高密度脂蛋白降低；\r\n";
                wtList.add(wt);
                gjSet.add(23);
                mbSet.add(13);
            }
        }
        //谷丙转氨酶（ALT）
        if (null != followBodyMap.get("alt1") && !StringUtils.isBlank(followBodyMap.get("alt1").toString())){
            double alt = Double.parseDouble(followBodyMap.get("alt1").toString());
            if (alt >= 41 && alt <= 79){
                String wt = "谷丙转氨酶："+alt+"U/L,偏高；\r\n";
                wtList.add(wt);
                gjSet.add(24);
                mbSet.add(14);
            }else if (alt >= 80){
                String wt = "谷丙转氨酶："+alt+"U/L,升高；\r\n";
                wtList.add(wt);
                gjSet.add(25);
                mbSet.add(14);
            }
        }
        //谷草转氨酶（ALT）
        if (null != followBodyMap.get("ast1") && !StringUtils.isBlank(followBodyMap.get("ast1").toString())){
            double ast = Double.parseDouble(followBodyMap.get("ast1").toString());
            if (ast >= 41 && ast <= 79){
                String wt = "谷草转氨酶："+ast+"U/L,偏高；\r\n";
                wtList.add(wt);
                gjSet.add(26);
                mbSet.add(15);
            }else if (ast >= 80){
                String wt = "谷草转氨酶："+ast+"U/L,升高；\r\n";
                wtList.add(wt);
                gjSet.add(27);
                mbSet.add(15);
            }
        }
        //尿酸
        if (null != followBodyMap.get("nsva2") && !StringUtils.isBlank(followBodyMap.get("nsva2").toString())){
            double nsva = Double.parseDouble(followBodyMap.get("nsva2").toString());
            if ("男".equals(sex)){
                if (nsva > 416){
                    String wt = "尿酸值："+nsva+"umol/L，增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(28);
                    mbSet.add(16);
                }else if (nsva < 149){
                    String wt = "尿酸值："+nsva+"umol/L，降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(29);
                    mbSet.add(16);
                }
            }else if ("女".equals(sex)){
                if (nsva > 357){
                    String wt = "尿酸值："+nsva+"umol/L，增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(28);
                    mbSet.add(17);
                }else if (nsva < 89){
                    String wt = "尿酸值："+nsva+"umol/L，降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(29);
                    mbSet.add(17);
                }
            }
        }
        //尿微量白蛋白/肌酐比值（uACR
        if (null != followBodyMap.get("nacr2") && !StringUtils.isBlank(followBodyMap.get("nacr2").toString())){
            double nacr = Double.parseDouble(followBodyMap.get("nacr2").toString());
            if (nacr >= 30){
                String wt = "尿微量白蛋白/肌酐比值增高；\r\n";
                wtList.add(wt);
                gjSet.add(30);
                mbSet.add(19);
            }

        }


        //去重//////////////////////////
        Map<Integer, String> gjMapping = getGjMapping();
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
        return  reMap;
    }
    /**
     * 主要改进措施文案
     * @param range  控制目标
     * @return
     */
    private static Map<Integer,String> getGjMapping(){
        Map<Integer, String> outMap = new HashMap<>();
        //BMI
        outMap.put(1,"您的体重偏轻，建议适当加强营养，平衡膳食，增强体质；\r\n");
        outMap.put(2,"您的体重已属于超重范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重；\r\n");
        outMap.put(3,"您的体重已属于肥胖范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重；\r\n");
        //腰围
        outMap.put(4,"腰围超范围，建议积极配合专业人员进行个性化的调控，适当减重，控制腰围、腰臀比在合理范围内；\r\n");
        //血压
        outMap.put(5,"您的血压偏高，建议内科就诊明确诊断。平时请用低盐饮食，适当运动，戒烟戒酒，避免情绪紧张，激动等，使血压保持在正常水平；\r\n");
        outMap.put(6,"您的血压偏低，建议专科诊治，日常适当增加体育运动，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤；\r\n");
        //血糖
        outMap.put(7,"您的空腹血糖偏低，注意预防低血糖，建议及时进餐或尝试适当进食点心。日常多监测血糖来减少低血糖的发生；\r\n");
        outMap.put(8,"您目前的空腹血糖高了哦，空腹血糖高的原因有很多，如：晚餐吃得太晚太多、运动量不够、“苏木杰现象”或是“黎明现象”、睡眠问题等等引起，需要逐一排查，根据具体情况进行适宜的调整；\r\n");
        outMap.put(9,"糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，多注意饮食结构及运动，必要时在医生指导下调整治疗方案；\r\n");
        //眼底照相
        outMap.put(10,"眼底照相存在异常，建议及时专科就诊，排除糖尿病视网膜病变的情况，明确病变程度，在医生指导下必要时进行针对性的治疗；\r\n");
        outMap.put(31,"眼底照相左眼存在异常，建议及时专科就诊，排除糖尿病视网膜病变的情况，明确病变程度，在医生指导下必要时进行针对性的治疗；\r\n");
        outMap.put(32,"眼底照相右眼存在异常，建议及时专科就诊，排除糖尿病视网膜病变的情况，明确病变程度，在医生指导下必要时进行针对性的治疗；\r\n");
        //ABI
        outMap.put(11,"存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(12,"有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(13,"下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(14,"下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(15,"足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(33,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(34,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(35,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(36,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(37,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(38,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(39,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(40,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(41,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(42,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(43,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(44,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(45,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(46,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(47,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(48,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(49,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(50,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(51,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(52,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(53,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(54,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(55,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(56,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(57,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(58,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(59,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(60,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(61,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(62,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(63,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(64,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(65,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(66,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(67,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(68,"右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(69,"右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(70,"右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(71,"右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(72,"右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        //VPT
        outMap.put(16,"轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(17,"中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(18,"有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(73,"右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(74,"右足：中度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(75,"右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(76,"左足：感觉良好，发生神经性溃疡为低风险。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(77,"左足：感觉良好，发生神经性溃疡为低风险。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(78,"左足：感觉良好，发生神经性溃疡为低风险。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(79,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(80,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        outMap.put(81,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(82,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(83,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(84,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        outMap.put(85,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(86,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(87,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(88,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        outMap.put(89,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(90,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        //血脂
        outMap.put(19,"建议控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入，提高饮食中膳食纤维比例，坚持规律运动，定期复查，如仍异常，请在专科医生指导下服用调脂药物；\r\n");
        outMap.put(20,"甘油三酯的含量可随膳食的改变而改变。平时请控制膳食总热量，用低脂、少糖、高纤维素膳食，坚持运动；\r\n");
//        outMap.put(21,"调整饮食控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入。坚持有规律的体力活动，建议定期复查，必要时在医生指导下服用调脂药；\r\n");
        outMap.put(22,"建议认真调整饮食控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入。坚持规律运动，定期复查，必要时在医生指导下配合服用调脂药，将血脂控制在理想范围内；\r\n");
        outMap.put(23,"高密度脂蛋白胆固醇减低是动脉硬化的危险指标，建议关注变化，日常注意控制膳食总热量，劳逸结合，半年复查；\r\n");
        //谷丙转氨酶
        outMap.put(24,"肝功能异常，建议调整作息，避免伤肝药物，建议三个月内复查；\r\n");
        outMap.put(25,"肝功能异常，建议调整作息，避免伤肝药物，近期复查后若结果仍异常，请专科诊治；\r\n");
        //谷草转氨酶
        outMap.put(26,"肝功能异常，建议定期复查观察变化，若出现如恶心、乏力、肝区疼痛等等，请专科诊治；\r\n");
        outMap.put(27,"肝功能异常升高，请专科诊治，并注意复查；\r\n");
        //尿酸
        outMap.put(28,"尿酸偏高，建议日常注意低嘌呤膳食，如减少动物内脏、沙丁鱼、虾皮、浓肉汤、啤酒及肉类等含有高嘌呤食物；\r\n");
        outMap.put(29,"尿酸偏低，临床意义不大，如降低明显可以请专科结合临床进行综合分析，定期复查观察变化；\r\n");
        //尿微量白蛋白/肌酐比值
        outMap.put(30,"尿微量白蛋白/肌酐比值（uACR）是诊断早期糖尿病肾病的一项敏感而可靠的指标，有异常升高，建议及时到专科进行诊治；\r\n");
        // outMap  key 90
        return outMap;
    }

    /**
     * 预期达到目标文案
     * @param range  控制目标
     * @return
     */
    private static Map<Integer, String> getMbMapping(RangeBO range) {
        Map<Integer, String> outMap = new HashMap<>();
        //BMI
        outMap.put(1,"增加体重，直至BMI控制在"+range.getLowBmi()+"～"+range.getHighBmi()+"之间；\r\n");
        outMap.put(2,"减轻体重，直至BMI控制在"+range.getLowBmi()+"～"+range.getHighBmi()+"之间；\r\n");
        //腰围
        outMap.put(3,"控制腰围＜90cm；\r\n");  //男
        outMap.put(4,"控制腰围＜85cm；\r\n");  //女
        //血压
        outMap.put(5,"控制血压，直至舒张压："+range.getLowDiastolicPress()+"～"+range.getHighDiastolicPress()+"mmHg，收缩压： "+range.getLowSystolicPress()+"～"+range.getHighSystolicPress()+"mmHg；\r\n");
        //血糖
        outMap.put(6,"控制空腹血糖，达到"+range.getLowBeforeBreakfast()+"-"+range.getHighBeforeBreakfast()+"mmol/L；\r\n");
        outMap.put(7,"控制糖化血红蛋白，≤"+range.getHighGlycosylatedVal()+"%；\r\n");
        //眼底照相
        outMap.put(8,"眼底病变得到控制好转；\r\n");
        //ABI,VPT
        outMap.put(9,"积极治疗，使肢体血管和神经功能、供血状态逐渐改善；\r\n");
        //血脂
        outMap.put(10,"控制胆固醇，≤"+range.getHighTCholesterol()+"mmol/L；\r\n");
        outMap.put(11,"控制甘油三酯，≤"+range.getHighTriglyceride()+"mmol/L；\r\n");
        outMap.put(12,"控制低密度脂蛋白≤"+range.getHighLDLCholesterol()+"mmol/L；\r\n");
        outMap.put(13,"控制高密度脂蛋白≥"+range.getLowHDLCholesterol()+"mmol/L；\r\n");
        //谷丙转氨酶
        outMap.put(14,"控制谷丙转氨酶，达到0～40u/L；\r\n");
        //谷草转氨酶
        outMap.put(15,"控制谷草转氨酶，达到0-40u/L；\r\n");
        //尿酸
        outMap.put(16,"控制尿酸，达到149～416umol/L；\r\n"); //男
        outMap.put(17,"控制尿酸，达到89～357umol/L；\r\n");  //女
        //尿微量白蛋白/肌酐比值
//        outMap.put(18,"控制uACR＜22mg/g；\r\n");
        outMap.put(19,"控制uACR＜30mg/g；\r\n");
        return outMap;
    }
}
