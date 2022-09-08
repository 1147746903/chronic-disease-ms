package com.comvee.cdms.level.helper;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.level.po.MemberLevelPO;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 分层分级对比分析
 *
 * @author wyc
 * @date 2019/11/22 20:20
 */
public class LevelAnalyzeHelper {
    public static String getAnalyze(MemberLevelPO nowPO, MemberLevelPO lastPO) {
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat df2 = new DecimalFormat("######0.00");

        Integer nowLevel = nowPO.getMemberLevel();
        Integer nowLayer = nowPO.getMemberLayer();
        Integer lastLevel = lastPO.getMemberLevel();
        Integer lastLayer = lastPO.getMemberLayer();

        String nowJson = nowPO.getDataJson();
        Map<String, Object> nowMap = JsonSerializer.jsonToMap(nowJson);
        String lastJson = lastPO.getDataJson();
        Map<String, Object> lastMap = JsonSerializer.jsonToMap(lastJson);

        Integer nowAge = nowPO.getAge();  //年龄
        String nowSbp = nowMap.get("sbp").toString();  //收缩压
        String nowDbp = nowMap.get("dbp").toString();  //舒张压
        String nowBmi = nowMap.get("bmi").toString();  //BMI
        String nowTc = nowMap.get("tc").toString();  //总胆固醇
        String nowSmoke = nowMap.get("smoke").toString();  //是否吸烟

        Integer lastAge = lastPO.getAge();  //年龄
        String lastSbp = lastMap.get("sbp").toString();  //收缩压
        String lastDbp = lastMap.get("dbp").toString();  //舒张压
        String lastBmi = lastMap.get("bmi").toString();  //BMI
        String lastTc = lastMap.get("tc").toString();  //总胆固醇
        String lastSmoke = lastMap.get("smoke").toString();  //是否吸烟

        Integer differAge = differTwoIntVal(nowAge, lastAge);  //年龄差值
        Double differSbp = diffTwoVal(nowSbp, lastSbp); //收缩压差值
        Double differDbp = diffTwoVal(nowDbp, lastDbp); //舒张压差值
        Double differBmi = diffTwoVal(nowBmi, lastBmi);  //BMI差值
        Double differTc = diffTwoVal(nowTc, lastTc);  //总胆固醇差值


        Integer differLevel = differTwoIntVal(nowLevel, lastLevel);  //等级差值 1 一级 2二级 3 三级 4 其他  >0 高转低
        Integer differLayer = differTwoIntVal(nowLayer, lastLayer);  //层级差值 1 低危 2 中危 3 高危  >0 高转低

        String result = "当前患者的";
        //上次分级是医生调整  并且前后两次比较的值都相等
        if (lastPO.getOrigin() == 2 && differAge == 0 && differSbp == 0.0 && differDbp == 0.0
                && differBmi == 0.0 && differTc == 0.0 && nowSbp.equals(lastSmoke)) {
            result += "层级由" + getLevel(lastLevel) + "，" + getLayer(lastLayer) + "转至" + getLevel(nowLevel) + "，" + getLayer(nowLayer) + "，";
            if (differLevel > 0 && differLayer > 0) {
                result += "请针对患者的具体情况及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
            } else {
                result += "请鼓励患者继续保持良好的自我监测行为哦！";
            }

        } else {
            String change = "";
            if (differAge != 0) {
                change += "年龄、";
            }
            if (!nowSmoke.equals(lastSmoke)) {
                change += "吸烟、";
            }
            if (differSbp != 0.0) {
                change += "收缩压、";
            }
            if (differDbp != 0.0) {
                change += "舒张压、";
            }
            if (differBmi != 0.0) {
                change += "BMI、";
            }
            if (differTc != 0.0) {
                change += "总胆固醇、";
            }

            if (!StringUtils.isBlank(change)) {
                change = change.substring(0, change.length() - 1);
                result += change + "有所改变，";
            }
            result += "层级由" + getLevel(lastLevel) + "，" + getLayer(lastLayer) + "转至" + getLevel(nowLevel) + "，" + getLayer(nowLayer) + "，";
            if (!StringUtils.isBlank(change) && (differSbp != 0.0 || differDbp != 0.0 || differBmi != 0.0 || differTc != 0.0)){
                result += "对比上次：患者的";
                if (differSbp > 0){
                    result  += "收缩压上升了"+df.format(differSbp)+"mmhg,";
                }else if (differSbp < 0){
                    result  += "收缩压下降了"+df.format(-differSbp)+"mmhg,";
                }

                if (differDbp > 0){
                    result  += "舒张压上升了"+df.format(differDbp)+"mmhg,";
                }else if (differDbp < 0){
                    result  += "舒张压下降了"+df.format(-differDbp)+"mmhg,";
                }

                if (differBmi > 0){
                    result  += "BMI上升了"+df2.format(differBmi) + ",";
                }else if (differBmi < 0){
                    result  += "BMI下降了"+df2.format(-differBmi) + ",";
                }

                if (differTc > 0){
                    result  += "总胆固醇上升了"+df2.format(differTc)+"mmol/l,";
                }else if (differTc < 0){
                    result  += "总胆固醇下降了"+df2.format(-differTc)+"mmol/l,";
                }
                if (differLevel >0 ){
                    result += "说明这段时间针对该患者的管理不佳，请针对患者的具体情况及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
                }else if (differLevel == 0){
                    if (differLayer > 0){
                        result += "说明这段时间针对该患者的管理不佳，请针对患者的具体情况及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
                    }else {
                        result += "说明这段时间针对患者的管理良好，请鼓励患者继续保持良好的自我监测行为哦！";
                    }
                }else {
                    result += "说明这段时间针对患者的管理良好，请鼓励患者继续保持良好的自我监测行为哦！";
                }

            }else {
                result += "请鼓励患者继续保持良好的自我监测行为哦！";

            }
        }


        return result;

    }

    /**
     * 分层文案
     *
     * @param layer
     * @return
     */
    public static String getLevel(Integer level) {
        if (null != level) {
            if (level == 1) {
                return "一级";
            } else if (level == 2) {
                return "二级";
            } else if (level == 3) {
                return "三级";
            } else if (level == 0) {
                return "其他";
            }
        }
        return "暂无";
    }

    public static String getLevelNumber(Integer level) {
        if (null != level) {
            if (level == 1) {
                return "1级";
            } else if (level == 2) {
                return "2级";
            } else if (level == 3) {
                return "3级";
            } else if (level == 0) {
                return "其他";
            }
        }
        return "暂无";
    }

    /**
     * 分层文案
     *
     * @param layer
     * @return
     */
    public static String getLayer(Integer layer) {
        if (null != layer) {
            if (layer == 1) {
                return "低危";
            } else if (layer == 2) {
                return "中危";
            } else if (layer == 3) {
                return "高危";
            }
        }
        return "暂无";
    }

    private static Double diffTwoVal(String str1, String str2) {
        if (StringUtils.isBlank(str1)) {
            str1 = "0.0";
        }
        if (StringUtils.isBlank(str2)) {
            str2 = "0.0";
        }
        Double val1 = Double.parseDouble(str1);
        Double val2 = Double.parseDouble(str2);
        return val1 - val2;
    }

    private static Integer differTwoIntVal(Integer now, Integer last) {
        if (now == null) {
            now = 0;
        }
        if (last == null) {
            last = 0;
        }
        return now - last;
    }
}
