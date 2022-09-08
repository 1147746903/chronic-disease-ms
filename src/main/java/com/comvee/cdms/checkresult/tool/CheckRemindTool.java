package com.comvee.cdms.checkresult.tool;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.CheckRemindItem;
import com.comvee.cdms.checkresult.constant.CheckoutDict;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.complication.constant.EyesConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/7/4
 */
public class CheckRemindTool {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private CheckRemindItem item;
    private Object data;
    private LocalDateTime recordDt;
    private PatientInfo patientInfo;
    private long plusDays;

    private CheckRemindTool(){}

    private CheckRemindTool(CheckRemindItem item ,Object data ,LocalDateTime recordDt ,PatientInfo patientInfo){
        this.item = item;
        this.data = data;
        this.recordDt = recordDt;
        this.patientInfo = patientInfo;
    }

    public static CheckRemindTool of(CheckRemindItem item ,Object data ,String recordDt ,PatientInfo patientInfo){
        return new CheckRemindTool(item ,data ,parseDate(recordDt) ,patientInfo);
    }

    /**
     * 评估检查提醒结果
     * @param checkItem
     * @param checkData
     * @param recordDt
     * @param patientPO
     * @return
     */
    public String access(){
        try{
            doAssess();
            return getReviewDt(plusDays);
        }catch (Exception e){
            log.warn("处理检查提醒异常,异常的项目:{}" , item.name() ,e);
            return null;
        }
    }

    /**
     * 开始评估
     * @param assessCheckRemindVO
     * @param checkItem
     * @param checkData
     * @param recordDt
     * @param patientPO
     */
    private void doAssess(){
        switch (item){
            case hba1c:
                hba1cHandler();
                break;
            case urineSugar:
            case urinaryKetone:
            case urineProtein:
            case urinaryLeukocyte:
                urinalysisHandler();
                break;
            case tc:
                tcHandler();
                break;
            case ldl:
                ldlHandler();
                break;
            case hdl:
                hdlHandler();
                break;
            case triglyceride:
                triglycerideHandler();
                break;
            case aib:
                aibHandler();
                break;
            case nbdb:
                nephAcrHandler();
                break;
            case scr:
                serumHandler();
                break;
            case dun:
                dunHandler();
                break;
            case tbil:
                tbilHandler();
                break;
            case alt:
                altHandler();
                break;
            case ast:
                astHandler();
                break;
            case ggt:
                ggtHandler();
                break;
            case eyes:
                eyesHandler();
                break;
            case abi:
                abiHandler();
                break;
            case vpt:
                vptHandler();
                break;
            case pwv:
                pwvHandler();
                break;
            default:
                break;
        }
    }

    /**
     * 糖化血红蛋白处理
     */
    private void hba1cHandler(){
        plusDays = 180;
        Float hba1c = getFloatValue();
        if(hba1c >= 7F){
            plusDays = 90;
        }
    }

    /**
     * 尿常规处理
     */
    private void urinalysisHandler(){
        plusDays = 180;
        String value = data.toString();
        if(value.indexOf("阳") > 0 || value.indexOf("+") > 0){
            plusDays = 90;
        }
    }

    /**
     * 总胆固醇
     */
    private void tcHandler(){
        plusDays = 365;
        Float tc = getFloatValue();
        if(tc >= 4.5f || tc < 2.8f){
            plusDays = 90;
        }
    }

    /**
     * 高密度脂蛋白胆固醇
     */
    private void hdlHandler(){
        plusDays = 365;
        Float hdl = getFloatValue();
        Integer sex = patientInfo.getSex();
        if(sex == null){
            return;
        }
        if((sex == 1 && hdl <= 1.0f)
            || (sex == 2 && hdl <= 1.3f)){
            plusDays = 90;
        }
    }

    /**
     * 低密度脂蛋白胆固醇
     */
    private void ldlHandler(){
        plusDays = 365;
        Float ldl = getFloatValue();
        Integer chd = patientInfo.getChd();
        if(chd == null){
            return;
        }
        if((chd == 0 && ldl >= 2.6f)
            || (chd == 1 && ldl >= 1.8f)){
            plusDays = 90;
        }
    }

    /**
     * 甘油三酯
     */
    private void triglycerideHandler(){
        plusDays = 365;
        Float triglyceride = getFloatValue();
        if(triglyceride >= 1.7f){
            plusDays = 90;
        }
    }

    /**
     * 尿微量白蛋白
     */
    private void aibHandler(){
        plusDays = 365;
        Float aib = getFloatValue();
        if(aib >= 20f){
            plusDays = 90;
        }
    }

    /**
     * 尿白蛋白/尿肌酐 *
     */
    private void nephAcrHandler(){
        plusDays = 365;
        Float nephAcr = getFloatValue();
        if(nephAcr >= 30f){
            plusDays = 90;
        }
    }

    /**
     * 血肌酐
     */
    private void serumHandler(){
        plusDays = 365;
        Float serum = getFloatValue();
        Integer sex = patientInfo.getSex();
        if(sex == null){
            return;
        }
        if((sex == 1 && (serum < 54f || serum > 106f))
            || (sex == 2 && (serum < 44f || serum > 97f))){
            plusDays = 90;
        }
    }

    /**
     * 血尿素氮
     */
    private void dunHandler(){
        plusDays = 365;
        if(getFloatValue() > 7.1f){
            plusDays = 90;
        }
    }

    /**
     * 总胆红素
     */
    private void tbilHandler(){
        plusDays = 365;
        if(getFloatValue() > 22.0f){
            plusDays = 90;
        }
    }

    /**
     * 谷丙转氨酶（ALT）
     */
    private void altHandler(){
        plusDays = 365;
        if(getFloatValue() > 40f){
            plusDays = 90;
        }
    }

    /**
     * 谷草转氨酶（AST）
     */
    private void astHandler(){
        plusDays = 365;
        if(getFloatValue() > 40f){
            plusDays = 90;
        }
    }

    /**
     * γ-谷氨酰转移酶
     */
    private void ggtHandler(){
        plusDays = 365;
        if(getFloatValue() > 55f){
            plusDays = 90;
        }
    }

    /**
     * 眼底检查
     */
    private void eyesHandler(){
        plusDays = 365;
        JSONObject eyesData = (JSONObject) data;
        int eyeLevel = 0;
        Integer leftEye = eyesData.getInteger("leftEyesResult");
        Integer rightEye = eyesData.getInteger("rightEyesResult");
        if(leftEye == null){
            leftEye = EyesConstant.EYE_LEVEL_0;
        }
        if(rightEye == null){
            rightEye = EyesConstant.EYE_LEVEL_0;
        }
        if(leftEye.intValue() > rightEye.intValue()){
            eyeLevel = leftEye;
        }else{
            eyeLevel = rightEye;
        }
        if(eyeLevel == EyesConstant.EYE_LEVEL_5){
            plusDays = 30;
        }else if(eyeLevel == EyesConstant.EYE_LEVEL_4){
            plusDays = 90;
        }else if(eyeLevel == EyesConstant.EYE_LEVEL_3){
            plusDays = 90;
        }else if(eyeLevel == EyesConstant.EYE_LEVEL_2){
            plusDays = 365;
        }
    }


    /**
     * 踝肱指数（ABI）检查 *
     */
    private void abiHandler(){
        plusDays = 90;
        JSONObject jsonData = (JSONObject) data;
        Float leftAbi = jsonData.getFloat("leftAbi");
        Float rightAbi = jsonData.getFloat("rightAbi");
        if((leftAbi != null && (leftAbi > 1.3f || leftAbi < 1.0f))
            || (rightAbi != null && (rightAbi > 1.3f || rightAbi < 1.0f))){
            plusDays = 28;
        }
    }

    /**
     * 足部震动感觉阈值（VPT）检查 *
     */
    private void vptHandler(){
        plusDays = 90;
        JSONObject jsonData = (JSONObject) data;
        Float leftVpt = jsonData.getFloat("leftVpt");
        Float rightVpt = jsonData.getFloat("rightVpt");
        if((leftVpt != null && leftVpt >= 10f)
            || (rightVpt != null && rightVpt >= 10f)){
            plusDays = 28;
        }
    }

    /**
     * 脉搏波传导速度（PWV）检查
     */
    private void pwvHandler(){
        plusDays = 365;
        JSONObject jsonData = (JSONObject) data;
        Float leftPwv = jsonData.getFloat("leftPwv");
        Float rightPwv = jsonData.getFloat("rightPwv");
        if((leftPwv != null && leftPwv > 14f)
                || (rightPwv != null && rightPwv > 14f)){
            plusDays = 180;
        }
    }

    /**
     * 转化日期
     * @param recordDt
     * @return
     */
    private static LocalDateTime parseDate(String recordDt){
        if(StringUtils.isBlank(recordDt)){
            return null;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(recordDt);
        } catch (ParseException e) {
            throw new BusinessException("不正确的日期格式");
        }
        return LocalDateTime.ofInstant(date.toInstant() , ZoneId.systemDefault());
    }

    /**
     * 获取复诊时间
     * @param plusDay
     * @return
     */
    private String getReviewDt(long plusDay){
        if(recordDt == null){
            return null;
        }
        return recordDt.plusDays(plusDay).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private Float getFloatValue(){
        return Float.parseFloat(data.toString());
    }

    /**
     * 创建患者基础信息
     * @return
     */
    public static PatientInfo createPatientInfo(){
        return new CheckRemindTool().new PatientInfo();
    }


    /**
     * 检查提醒需要的患者信息
     */
    public class PatientInfo{
        private Integer sex;
        /**
         * 冠心病 1 有合并冠心病 0 未合并冠心病
         */
        private Integer chd;

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public Integer getChd() {
            return chd;
        }

        public void setChd(Integer chd) {
            this.chd = chd;
        }
    }

}
