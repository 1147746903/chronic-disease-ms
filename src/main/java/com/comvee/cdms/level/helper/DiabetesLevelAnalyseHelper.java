package com.comvee.cdms.level.helper;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.complication.constant.EyesConstant;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.google.common.base.Joiner;
import org.apache.shiro.crypto.hash.Hash;

import java.text.MessageFormat;
import java.util.*;

/**
 * 糖尿病级别分析工具（分标）
 */
public class DiabetesLevelAnalyseHelper {

    /**
     * 执行分析
     * @param param
     * @return
     */
    public static Integer analyseLevel(LevelAnalyseParam param){
        AnalyseResult result = doAnalyse(param);
        if(result == null){
            return null;
        }
        return result.getLevel();
    }

    /**
     * 分析问题
     * @param param
     * @return
     */
    public static String analyseProblem(LevelAnalyseParam param){
        AnalyseResult result = doAnalyse(param);
        if(result == null){
            return null;
        }
        return result.getProblemText();
    }

    private static AnalyseResult doAnalyse(LevelAnalyseParam param){
        if(param.getHba1c() == null){
            return null;
        }
        AnalyseResult result = redLevelAnalyse(param);
        if(result == null){
            result = yellowLevelAnalyse(param);
            if(result == null){
                result = greenLevelAnalyse(param);
            }
        }
        return result;
    }

    public static String getAnalyseText(int currentLevel ,LevelAnalyseParam currentParam ,int lastLevel ,LevelAnalyseParam lastParam){
        //相同
        if(currentLevel == lastLevel){
            return "当前患者的分标未改变，请鼓励患者继续保持良好的自我监测行为哦！";
        }
        String textTemplate = "";
        //变好
        if(currentLevel > lastLevel){
            textTemplate = "当前患者的分标由{0}转至{1}，对比上次，患者的{2}说明这段时间针对患者的管理良好，请鼓励患者继续保持良好的自我监测行为哦！";
        }
        //变差
        else{
            textTemplate = "当前患者的分标由{0}转至{1}，对比上次，患者的{2}说明这段时间针对该患者的管理不佳，请针对患者的具体情况及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
        }
        return MessageFormat.format(textTemplate ,MemberLevelConstant.diabetesLevelText(lastLevel)
                ,MemberLevelConstant.diabetesLevelText(currentLevel) ,getChangeText(currentParam ,lastParam));
    }

    /**
     * 红标分析
     * @param param
     * @return
     */
    private static AnalyseResult redLevelAnalyse(LevelAnalyseParam param){
        List<String> problemList = new ArrayList<>();
        boolean checkFlag = false;
        if(param.getHba1c() != null && param.getHba1c() > 8D){
            checkFlag = true;
            problemList.add("糖化血红蛋白:"  + param.getHba1c());
        }
        if(param.getLowBloodSugarTimes() >= 2){
            checkFlag = true;
            problemList.add("近一周低血糖次数:" + param.getLowBloodSugarTimes());
        }
        if("SB01".equals(param.getDiabeticNephropathy())
            && ("LX04".equals(param.getDiabeticNephropathyType()) || "LX05".equals(param.getDiabeticNephropathyType()))
            ){
            checkFlag = true;
            problemList.add("糖尿病肾病:" + ("LX04".equals(param.getDiabeticNephropathyType()) ? "临床糖尿病肾病期" : "肾衰竭期"));
        }
        if("TNBZ01".equals(param.getDiabeticFoot())
            && ("CD03".equals(param.getDiabeticFootSymptom())
                || "CD04".equals(param.getDiabeticFootSymptom())
                || "CD05".equals(param.getDiabeticFootSymptom())
                || "CD06".equals(param.getDiabeticFootSymptom()))
            ){
            checkFlag = true;
            problemList.add("糖尿病足:" + diabeticFootSymptomMap.get(param.getDiabeticFootSymptom()));
        }
        boolean leftEyeAbnormal = param.getLeftEyePathology() != null && param.getLeftEyePathology() >= EyesConstant.EYE_LEVEL_3;
        boolean rightEyeAbnormal = param.getRightEyePathology() != null && param.getRightEyePathology() >= EyesConstant.EYE_LEVEL_3;
        if(leftEyeAbnormal || rightEyeAbnormal){
            checkFlag = true;
            StringJoiner eyeText = new StringJoiner(",");
            if(leftEyeAbnormal){
                eyeText.add("左眼" + EyesConstant.EYE_LEVEL_NAME.get(param.getLeftEyePathology()));
            }
            if(rightEyeAbnormal){
                eyeText.add("右眼" + EyesConstant.EYE_LEVEL_NAME.get(param.getRightEyePathology()));
            }
            problemList.add("眼底检查:" + eyeText);
        }
        boolean leftVptAbnormal = param.getLeftVpt() != null && param.getLeftVpt() > 25D;
        boolean rightVptAbnormal = param.getRightVpt() != null && param.getRightVpt() > 25D;
        if(leftVptAbnormal || rightVptAbnormal){
            checkFlag = true;
            StringJoiner vptText = new StringJoiner(",");
            if(leftVptAbnormal){
                vptText.add("左脚" + param.getLeftVpt() + "V");
            }
            if(rightVptAbnormal){
                vptText.add("右脚" + param.getRightVpt() + "V");
            }
            problemList.add("感觉震动阈值（VPT）:" + vptText);
        }
        boolean rightAbiAbnormal = param.getRightAbi() != null && (param.getRightAbi() < 0.4d || param.getRightAbi() > 1.3);
        boolean leftAbiAbnormal = param.getLeftAbi() != null && (param.getLeftAbi() < 0.4d || param.getLeftAbi() > 1.3);
        if(rightAbiAbnormal || leftAbiAbnormal){
            checkFlag = true;
            StringJoiner abiText = new StringJoiner(",");
            if(leftAbiAbnormal){
                abiText.add("左脚" + param.getLeftAbi());
            }
            if(rightAbiAbnormal){
                abiText.add("左脚" + param.getRightAbi());
            }
            problemList.add("踝肱指数（ABI）：" + abiText);
        }
        if(checkFlag){
            AnalyseResult result = new AnalyseResult();
            result.setLevel(MemberLevelConstant.DIABETES_LEVEL_RED);
            result.setProblemText(Joiner.on("、").join(problemList));
            return result;
        }
        return null;
    }

    /**
     * 黄标分析
     * @param param
     * @return
     */
    private static AnalyseResult yellowLevelAnalyse(LevelAnalyseParam param){
        List<String> problemList = new ArrayList<>();
        boolean checkFlag = false;
        if(param.getAge() != null && param.getHba1c() != null){
            if(param.getAge() < 65 && param.getHba1c() >= 6.5d && param.getHba1c() <= 8d){
                checkFlag = true;
                problemList.add("年龄:"  + param.getAge());
                problemList.add("糖化血红蛋白:"  + param.getHba1c());
            }else if(param.getHba1c() >= 7d && param.getHba1c() <= 8d){
                checkFlag = true;
                problemList.add("年龄:"  + param.getAge());
                problemList.add("糖化血红蛋白:"  + param.getHba1c());
            }
        }
        if(!checkFlag && param.getLowBloodSugarTimes() == 1){
            checkFlag = true;
            problemList.add("近一周低血糖次数:" + param.getLowBloodSugarTimes());
        }
        if("SB01".equals(param.getDiabeticNephropathy()) ){
            checkFlag = true;
            problemList.add("确诊糖尿病肾病");
        }
        if("TNBZ01".equals(param.getDiabeticFoot())
                && "CD02".equals(param.getDiabeticFootSymptom())
            ){
            checkFlag = true;
            problemList.add("糖尿病足:" + diabeticFootSymptomMap.get(param.getDiabeticFootSymptom()));
        }
        boolean leftEyeAbnormal = param.getLeftEyePathology() != null && param.getLeftEyePathology() == EyesConstant.EYE_LEVEL_2;
        boolean rightEyeAbnormal = param.getRightEyePathology() != null && param.getRightEyePathology() == EyesConstant.EYE_LEVEL_2;
        if(leftEyeAbnormal || rightEyeAbnormal){
            checkFlag = true;
            StringJoiner eyeText = new StringJoiner(",");
            if(leftEyeAbnormal){
                eyeText.add("左眼" + EyesConstant.EYE_LEVEL_NAME.get(param.getLeftEyePathology()));
            }
            if(rightEyeAbnormal){
                eyeText.add("右眼" + EyesConstant.EYE_LEVEL_NAME.get(param.getRightEyePathology()));
            }
            problemList.add("眼底检查:" + eyeText);
        }
        boolean leftVptAbnormal = param.getLeftVpt() != null && param.getLeftVpt() > 15D;
        boolean rightVptAbnormal = param.getRightVpt() != null && param.getRightVpt() > 15D;
        if(leftVptAbnormal || rightVptAbnormal){
            checkFlag = true;
            StringJoiner vptText = new StringJoiner(",");
            if(leftVptAbnormal){
                vptText.add("左脚" + param.getLeftVpt() + "V");
            }
            if(rightVptAbnormal){
                vptText.add("右脚" + param.getRightVpt() + "V");
            }
            problemList.add("感觉震动阈值（VPT）:" + vptText);
        }
        boolean rightAbiAbnormal = param.getRightAbi() != null && param.getRightAbi() >= 0.4d && param.getRightAbi() <= 0.9d;
        boolean leftAbiAbnormal = param.getLeftAbi() != null && param.getLeftAbi() >= 0.4d && param.getLeftAbi() <= 0.9d;
        if(rightAbiAbnormal || leftAbiAbnormal){
            checkFlag = true;
            StringJoiner abiText = new StringJoiner(",");
            if(leftAbiAbnormal){
                abiText.add("左脚" + param.getLeftAbi());
            }
            if(rightAbiAbnormal){
                abiText.add("左脚" + param.getRightAbi());
            }
            problemList.add("踝肱指数（ABI）：" + abiText);
        }
        if(checkFlag){
            AnalyseResult result = new AnalyseResult();
            result.setLevel(MemberLevelConstant.DIABETES_LEVEL_YELLOW);
            result.setProblemText(Joiner.on("、").join(problemList));
            return result;
        }
        return null;
    }

    /**
     * 绿标分析
     * @param param
     * @return
     */
    private static AnalyseResult greenLevelAnalyse(LevelAnalyseParam param){
        boolean checkFlag = false;
        if(param.getHba1c() != null && param.getHba1c() >= 6.5d){
            checkFlag = true;
        }else if(param.getLowBloodSugarTimes() == 0){
            checkFlag = true;
        }else if("SB02".equals(param.getDiabeticNephropathy()) ){
            checkFlag = true;
        }else if("TNBZ01".equals(param.getDiabeticFoot())
                || "TNBZ02".equals(param.getDiabeticFoot())
                || "CD01".equals(param.getDiabeticFootSymptom())
            ){
            checkFlag = true;
        }else if((param.getLeftEyePathology() != null && param.getLeftEyePathology().equals("1"))
                || (param.getRightEyePathology() != null && param.getRightEyePathology().equals("1"))){
            checkFlag = true;
        }else if((param.getLeftVpt() != null && param.getLeftVpt() <= 15D)
                || (param.getRightVpt() != null && param.getRightVpt() <= 15D)){
            checkFlag = true;
        }else if((param.getRightAbi() != null && param.getRightAbi() > 0.9d && param.getRightAbi() <= 1.3d)
                || (param.getLeftAbi() != null && param.getLeftAbi() > 0.9d && param.getLeftAbi() <= 1.3d)){
            checkFlag = true;
        }
        if(checkFlag){
            AnalyseResult result = new AnalyseResult();
            result.setLevel(MemberLevelConstant.DIABETES_LEVEL_GREEN);
            return result;
        }
        return null;
    }

    private static String getChangeText(LevelAnalyseParam currentParam ,LevelAnalyseParam lastParam){
        StringBuilder signText = new StringBuilder();
        if(currentParam.getHba1c() != null && lastParam.getHba1c() != null && currentParam.getHba1c().doubleValue() != lastParam.getHba1c().doubleValue()){
            signText.append("糖化血红蛋白");
            double diff = lastParam.getHba1c().doubleValue() - currentParam.getHba1c().doubleValue();
            if(diff < 0d){
                signText.append("上升了");
            }else{
                signText.append("下降了");
            }
            signText.append(Math.abs(diff)).append("%，");
        }
        if(currentParam.getLowBloodSugarTimes().intValue() != lastParam.getLowBloodSugarTimes().intValue()){
            signText.append("近一周低血糖发生情况");
            int diff = lastParam.getLowBloodSugarTimes().intValue() - currentParam.getLowBloodSugarTimes().intValue();
            if(diff < 0d){
                signText.append("增加");
            }else{
                signText.append("减少");
            }
            signText.append(Math.abs(diff)).append("次，");
        }
        List<String> situationText = new ArrayList<>();
        if(checkAllNotEmpty(currentParam.getDiabeticNephropathy() ,lastParam.getDiabeticNephropathy())){
            if(checkIsDifferent(currentParam.getDiabeticNephropathy() ,lastParam.getDiabeticNephropathy())){
                situationText.add("糖尿病肾病");
            }else if(checkIsDifferent(currentParam.getDiabeticNephropathyType() ,lastParam.getDiabeticNephropathyType())){
                situationText.add("糖尿病肾病");
            }
        }
        if(checkAllNotEmpty(currentParam.getDiabeticFoot() ,lastParam.getDiabeticFoot())){
            if(checkIsDifferent(currentParam.getDiabeticFoot() ,lastParam.getDiabeticFoot())){
                situationText.add("糖尿病足");
            }else if(checkIsDifferent(currentParam.getDiabeticFootSymptom() ,lastParam.getDiabeticFootSymptom())){
                situationText.add("糖尿病足");
            }
        }
        if(checkIsDifferent(currentParam.getLeftEyePathology() ,lastParam.getLeftEyePathology())
            || checkIsDifferent(currentParam.getRightEyePathology() ,lastParam.getRightEyePathology())){
            situationText.add("眼底筛查");
        }
        if((checkAllNotEmpty(currentParam.getRightVpt() ,lastParam.getRightVpt())
                && currentParam.getRightVpt().doubleValue() != lastParam.getRightVpt().doubleValue())
                || (checkAllNotEmpty(currentParam.getLeftVpt() ,lastParam.getLeftVpt())
                && currentParam.getLeftVpt().doubleValue() != lastParam.getLeftVpt().doubleValue())){
            situationText.add("VPT检查");
        }
        if((checkAllNotEmpty(currentParam.getRightAbi() ,lastParam.getRightAbi())
                && currentParam.getRightAbi().doubleValue() != lastParam.getRightAbi().doubleValue())
            || (checkAllNotEmpty(currentParam.getLeftAbi() ,lastParam.getLeftAbi())
                && currentParam.getLeftAbi().doubleValue() != lastParam.getLeftAbi().doubleValue())){
            situationText.add("ABI检查");
        }

        return signText.append(Joiner.on("、").join(situationText)).toString();
    }

    private static boolean checkAllNotEmpty(Object val1 ,Object val2){
        if(val1 instanceof String && val2 instanceof String){
            return !StringUtils.isBlank((String) val1) && !StringUtils.isBlank((String) val2);
        }
        return val1 != null && val2 != null;
    }

    private static boolean checkIsDifferent(Integer val1 ,Integer val2){
        if(!checkAllNotEmpty(val1 ,val2)){
            return false;
        }
        return val1.intValue() != val1.intValue();
    }

    private static boolean checkIsDifferent(String val1 ,String val2){
        if(!checkAllNotEmpty(val1 ,val2)){
            return false;
        }
        return !val1.equals(val2);
    }

    public static class LevelAnalyseParam {
        /**
         * 糖化
         */
        private Double hba1c;
        private Integer age;
        /**
         * 近一周低血糖次数
         */
        private Integer lowBloodSugarTimes;
        /**
         * 是否确诊糖尿病肾病
         */
        private String diabeticNephropathy;
        /**
         * 糖尿病肾病类型
         */
        private String diabeticNephropathyType;
        /**、
         * 是否确诊糖尿病足
         */
        private String diabeticFoot;
        /**
         * 糖尿病足症状
         */
        private String diabeticFootSymptom;
        /**
         * 左眼病变情况
         */
        private Integer leftEyePathology;
        /**
         * 右眼病变情况
         */
        private Integer rightEyePathology;
        private Double leftAbi;
        private Double rightAbi;
        private Double leftVpt;
        private Double rightVpt;

        public Double getHba1c() {
            return hba1c;
        }

        public void setHba1c(Double hba1c) {
            this.hba1c = hba1c;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getLowBloodSugarTimes() {
            return lowBloodSugarTimes;
        }

        public void setLowBloodSugarTimes(Integer lowBloodSugarTimes) {
            this.lowBloodSugarTimes = lowBloodSugarTimes;
        }

        public String getDiabeticNephropathy() {
            return diabeticNephropathy;
        }

        public void setDiabeticNephropathy(String diabeticNephropathy) {
            this.diabeticNephropathy = diabeticNephropathy;
        }

        public String getDiabeticNephropathyType() {
            return diabeticNephropathyType;
        }

        public void setDiabeticNephropathyType(String diabeticNephropathyType) {
            this.diabeticNephropathyType = diabeticNephropathyType;
        }

        public String getDiabeticFoot() {
            return diabeticFoot;
        }

        public void setDiabeticFoot(String diabeticFoot) {
            this.diabeticFoot = diabeticFoot;
        }

        public String getDiabeticFootSymptom() {
            return diabeticFootSymptom;
        }

        public void setDiabeticFootSymptom(String diabeticFootSymptom) {
            this.diabeticFootSymptom = diabeticFootSymptom;
        }

        public Integer getLeftEyePathology() {
            return leftEyePathology;
        }

        public void setLeftEyePathology(Integer leftEyePathology) {
            this.leftEyePathology = leftEyePathology;
        }

        public Integer getRightEyePathology() {
            return rightEyePathology;
        }

        public void setRightEyePathology(Integer rightEyePathology) {
            this.rightEyePathology = rightEyePathology;
        }

        public Double getLeftAbi() {
            return leftAbi;
        }

        public void setLeftAbi(Double leftAbi) {
            this.leftAbi = leftAbi;
        }

        public Double getRightAbi() {
            return rightAbi;
        }

        public void setRightAbi(Double rightAbi) {
            this.rightAbi = rightAbi;
        }

        public Double getLeftVpt() {
            return leftVpt;
        }

        public void setLeftVpt(Double leftVpt) {
            this.leftVpt = leftVpt;
        }

        public Double getRightVpt() {
            return rightVpt;
        }

        public void setRightVpt(Double rightVpt) {
            this.rightVpt = rightVpt;
        }

    }

    public static class AnalyseResult {

        private Integer level;
        private String problemText;

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getProblemText() {
            return problemText;
        }

        public void setProblemText(String problemText) {
            this.problemText = problemText;
        }
    }

    private static final Map<String ,String> diabeticFootSymptomMap = new HashMap<>();
    static {
        diabeticFootSymptomMap.put("CD02" ,"1级（表面溃疡，临床无感染）");
        diabeticFootSymptomMap.put("CD03" ,"2级（较深的感染，无脓肿或骨的感染）");
        diabeticFootSymptomMap.put("CD04" ,"3级（深度感染，伴有骨组织病变或脓肿)");
        diabeticFootSymptomMap.put("CD05" ,"4级（趾、足跟或前足背局限性坏疽）");
        diabeticFootSymptomMap.put("CD06" ,"5级（全足坏疽)");
    }

    public static String getLevel(Integer level) {
        if (null != level) {
            if (level == 1){
                return "红标";
            }else if (level == 2){
                return "黄标";
            }else if (level == 3){
                return "绿标";
            }else if (level == 0){
                return "其它";
            }
        }
        return "暂无";
    }
}
