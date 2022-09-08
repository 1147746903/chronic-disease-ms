package com.comvee.cdms.member.constant;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.tool.EighteenRangeHelper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class ControlTargetConstant {

    /**
     * 随机血糖下限
     */
    public static final float RANGE_RANDOM_LOW = 4.4f;
    /**
     * 随机血糖上限
     */
    public static final float RANGE_RANDOM_HIGH = 9.9f;
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
    public static float RANGE_FKF_TNB_PERFECT=9.9f;
    /**
     * 收缩压下限
     */
    public static float RANGE_SYSTOLIC_PRESS_LOW = 90f;
    /**
     * 收缩压上限
     */
    public static float RANGE_SYSTOLIC_PRESS_HIGH = 129f;
    /**
     *  年龄大于60岁 有高血压 收缩压上限
     */
    public static float RANGE_SYSTOLIC_PRESS_HYP_HIGH = 149f;
    /**
     * 舒张压下限
     */
    public static float RANGE_DIASTOLIC_PRESS_LOW = 60f;
    /**
     * 舒张压上限
     */
    public static float RANGE_DIASTOLIC_PRESS_HIGN = 79f;
    /**
     *  年龄大于60岁 有高血压 舒张压上限
     */
    public static float RANGE_DIASTOLIC_PRESS_HYP_HIGN = 89f;
    /**
     * 糖化血红蛋白控制目标
     */
    public static float RANGE_LOW_GLY_COSYLATED = 6.9f;
    public static float RANGE_HIGH_GLY_COSYLATED = 6.9f;
    /**
     * 糖化血红蛋白控制目标  妊娠糖尿病
     */
    public static float RANGE_LOW_GLY_COSYLATED_RS = 5.4f;
    public static float RANGE_HIGH_GLY_COSYLATED_RS = 5.4f;
    /**
     * 糖化血红蛋白控制目标  仅患高血压
     */
    public static float RANGE_LOW_GLY_ONLY_GXY = 6.0f;
    public static float RANGE_HIGH_GLY_ONLY_GXY = 6.0f;


    /**
     * 总胆固醇 控制目标
     */
    public static float LOW_T_CHOLESTEROL = 2.84f;
    public static float HIGH_T_CHOLESTEROL = 4.49f;

    /**
     * 甘油三酯 控制目标
     */
    public static float LOW_TRIGLYCERIDE = 0.56f;
    public static float HIGH_TRIGLYCERIDE = 1.69f;

    /**
     * 低密度脂蛋白胆固醇 控制目标
     */
    public static float LOW_LDL_CHOLESTEROL = 2.59f;
    public static float HIGH_LDL_CHOLESTEROL_CHD = 1.79f;
    public static float HIGH_LDL_CHOLESTEROL_NO_CHD = 2.59f;

    /**
     * 高密度脂蛋白胆固醇
     */
    public static float LOW_HDL_CHOLESTEROL_MAN = 1.01f;
    public static float LOW_HDL_CHOLESTEROL_WOMAN = 1.31f;
    public static float HIGH_HDL_CHOLESTEROL = 1.76f;


    /**
     * bmi范围
     */
    public static float BMI_HIGH = 23.99f;
    public static float BMI_LOW = 18.5f;

    /**
     * v4.3.0新增 餐后一小时
     */
    public static float AFTER_MEAL_ONE_HOUR_HIGH = 7.8f;
    public static float AFTER_MEAL_ONE_HOUR_LOW = 4.0f;

    /**
     * 凌晨血糖(夜间血糖)  妊娠
     */
    public static float LOW_BEFOREDAWN_RS = 3.3f;
    public static float HIGH_BEFOREDAWN_RS = 5.3f;

    /**
     * 餐后  妊娠
     */
    public static float LOW_AFTER_MEAL_RS = 4.0f;
    public static float HIGH_AFTER_MEAL_RS = 6.7f;
    /**
     * 空腹、餐前、睡前血糖  妊娠
     */
    public static float LOW_KF_SQ_CQ_RS = 4.0f;
    public static float HIGH_KF_SQ_CQ_RS = 5.3f;
    /**
     * 随机血糖 妊娠
     */
    public static float LOW_RANDOM_RS = 4.0f;
    public static float HIGH_RANDOM_RS = 6.7f;

    /**
     * 获取默认控制目标
     * @return
     */
    public static MemberControlTargetBO getDefaultControlTarget(BaseInfo baseInfo){

        //region 基础信息判断
        int age = 0;
        if (!isBlank(baseInfo.getBirthday())){
            age = getAge(baseInfo.getBirthday());
        }
        int sex = baseInfo.getSex();
        //endregion

        MemberControlTargetBO memberControlTargetBO = new MemberControlTargetBO();

        //region bmi控制目标
        memberControlTargetBO.setHighBmi(BMI_HIGH);
        memberControlTargetBO.setLowBmi(BMI_LOW);
        //endregion

        //region 腰围、腰臀比
        if(baseInfo.getSex() != null && baseInfo.getSex() == 1){
            memberControlTargetBO.setHighWaistline(90f);
            memberControlTargetBO.setHighWhr(0.90f);
        }else{
            memberControlTargetBO.setHighWaistline(85f);
            memberControlTargetBO.setHighWhr(0.85f);
        }
        //endregion


        //region 血糖
        // 餐后一小时 (妊娠糖尿病用)
        memberControlTargetBO.setHighAfterMealOneHour(AFTER_MEAL_ONE_HOUR_HIGH);
        memberControlTargetBO.setLowAfterMealOneHour(AFTER_MEAL_ONE_HOUR_LOW);
        //随机血糖
        memberControlTargetBO.setHighRandomSugar(RANGE_RANDOM_HIGH);
        memberControlTargetBO.setLowRandomSugar(RANGE_RANDOM_LOW);
        //空腹血糖、非空腹
        //2型糖尿病、同时患高血压和2型糖尿病
        if((HAS.equals(baseInfo.getIsDiabetes()) && StringUtils.isBlank(baseInfo.getDiabetesType())) || DIABETES_TYPE_2.equals(baseInfo.getDiabetesType())){
            setBloodSugarValue(memberControlTargetBO ,4.4f ,7.0f ,RANGE_FKF_TNB_STANDARD_LOW ,RANGE_FKF_TNB_PERFECT);
        }
        //妊娠糖尿病、同时患高血压和妊娠糖尿病
        else if(DIABETES_TYPE_GESTATION.equals(baseInfo.getDiabetesType())){
            setBloodSugarValue(memberControlTargetBO ,4.0f ,5.3f ,LOW_AFTER_MEAL_RS ,HIGH_AFTER_MEAL_RS);
            //睡前
            memberControlTargetBO.setHighBeforeSleep(5.3f);
            memberControlTargetBO.setLowBeforeSleep(4.0f);
            //凌晨
            memberControlTargetBO.setHighBeforedawn(5.3f);
            memberControlTargetBO.setLowBeforedawn(3.3f);
            //餐前
            memberControlTargetBO.setHighBeforeMeal(5.3f);
            memberControlTargetBO.setLowBeforeMeal(4.0f);
        }
        //其他
        else{
            setBloodSugarValue(memberControlTargetBO ,2.8f ,6.0f ,2.8f ,7.7f);
        }
        //endregion 血糖

        //region 糖化血红蛋白
        //2型糖尿病、同时患高血压和2型糖尿病
        if((HAS.equals(baseInfo.getIsDiabetes()) && StringUtils.isBlank(baseInfo.getDiabetesType())) || DIABETES_TYPE_2.equals(baseInfo.getDiabetesType())){
            memberControlTargetBO.setHighGlycosylatedVal(RANGE_LOW_GLY_COSYLATED);
            memberControlTargetBO.setLowGlycosylatedVal(RANGE_LOW_GLY_COSYLATED);
        }
        //妊娠糖尿病、同时患高血压和妊娠糖尿病
        else if(DIABETES_TYPE_GESTATION.equals(baseInfo.getDiabetesType())){
            memberControlTargetBO.setHighGlycosylatedVal(RANGE_LOW_GLY_COSYLATED_RS);
            memberControlTargetBO.setLowGlycosylatedVal(RANGE_LOW_GLY_COSYLATED_RS);
        }
        //其他
        else{
            memberControlTargetBO.setHighGlycosylatedVal(RANGE_LOW_GLY_ONLY_GXY);
            memberControlTargetBO.setLowGlycosylatedVal(RANGE_LOW_GLY_ONLY_GXY);
        }
        //endregion

        //region 血压（收缩压、舒张压）
        //拥有高血压但没有糖尿病的患者
        if (HAS.equals(baseInfo.getEssentialHyp()) && (baseInfo.getIsDiabetes() == null || "".equals(baseInfo.getIsDiabetes()) || NOT_HAS.equals(baseInfo.getIsDiabetes()))){
            //确诊有、疑似、有症状
            boolean hypBfzYes = baseInfo.getHypBfz() != null && baseInfo.getHypBfz() == 1;
            //合并症（心、脑、肾、眼、外周、糖尿病疾病）-- 确诊无、未评估、无症状
            boolean hypBfzNo = baseInfo.getHypBfz() != null && baseInfo.getHypBfz() == 2;
            if(age >= 18 && age < 65){
                if(hypBfzYes){
                    memberControlTargetBO.setHighSystolicPress(RANGE_SYSTOLIC_PRESS_HIGH);
                    memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
                    //舒张压
                    memberControlTargetBO.setHighDiastolicPress(79f);
                    memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
                }else if(hypBfzNo){
                    // 收缩压
                    memberControlTargetBO.setHighSystolicPress(139F);
                    memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
                    //舒张压
                    memberControlTargetBO.setHighDiastolicPress(RANGE_DIASTOLIC_PRESS_HYP_HIGN);
                    memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
                }else{
                    // 收缩压
                    memberControlTargetBO.setHighSystolicPress(139f);
                    memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
                    //舒张压
                    memberControlTargetBO.setHighDiastolicPress(89f);
                    memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
                }
            }else if(age >= 65){
                //血压控制目标
                // 收缩压
                memberControlTargetBO.setHighSystolicPress(RANGE_SYSTOLIC_PRESS_HYP_HIGH);
                memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
                //舒张压
                memberControlTargetBO.setHighDiastolicPress(RANGE_DIASTOLIC_PRESS_HYP_HIGN);
                memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
            }else{
                memberControlTargetBO.setHighSystolicPress(119F);
                memberControlTargetBO.setLowSystolicPress(0f);
                //舒张压
                memberControlTargetBO.setHighDiastolicPress(RANGE_DIASTOLIC_PRESS_HIGN);
                memberControlTargetBO.setLowDiastolicPress(0f);
            }
        }
        //有糖尿病且有高血压且年龄 > 65
        else if(HAS.equals(baseInfo.getEssentialHyp()) && HAS.equals(baseInfo.getIsDiabetes()) && age >= 65){
            //收缩压
            memberControlTargetBO.setHighSystolicPress(149f);
            memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
            //舒张压
            memberControlTargetBO.setHighDiastolicPress(89f);
            memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
        }else {
            //血压控制目标//收缩压
            memberControlTargetBO.setHighSystolicPress(RANGE_SYSTOLIC_PRESS_HIGH);
            memberControlTargetBO.setLowSystolicPress(RANGE_SYSTOLIC_PRESS_LOW);
            //舒张压
            memberControlTargetBO.setHighDiastolicPress(RANGE_DIASTOLIC_PRESS_HIGN);
            memberControlTargetBO.setLowDiastolicPress(RANGE_DIASTOLIC_PRESS_LOW);
        }
        //endregion

        //region 高密度脂蛋白
        //2型糖尿病、高血压、同时患高血压和2型糖尿病
        if(DIABETES_TYPE_2.equals(baseInfo.getDiabetesType()) || HAS.equals(baseInfo.getEssentialHyp())){
            if(SEX_MAN == baseInfo.getSex()){
                memberControlTargetBO.setLowHDLCholesterol(LOW_HDL_CHOLESTEROL_MAN);
            }else if(SEX_WOMAN == baseInfo.getSex()){
                memberControlTargetBO.setLowHDLCholesterol(LOW_HDL_CHOLESTEROL_WOMAN);
            }else{
                memberControlTargetBO.setLowHDLCholesterol(LOW_HDL_CHOLESTEROL_MAN);
            }
        }
        //妊娠糖尿病、同时患高血压和妊娠糖尿病
        else if(DIABETES_TYPE_GESTATION.equals(baseInfo.getDiabetesType()) || HAS.equals(baseInfo.getEssentialHyp())){
            memberControlTargetBO.setLowHDLCholesterol(LOW_HDL_CHOLESTEROL_WOMAN);
        }
        //其他
        else{
            memberControlTargetBO.setLowHDLCholesterol(LOW_HDL_CHOLESTEROL_MAN);
        }
        //endregion 高密度脂蛋白

        //region 低密度脂蛋白
        //默认值
        memberControlTargetBO.setHighLDLCholesterol(4.0f);
        //仅高血压
        if(HAS.equals(baseInfo.getEssentialHyp()) && (baseInfo.getIsDiabetes() == null || NOT_HAS.equals(baseInfo.getIsDiabetes()))){
            //"1、合并有冠心病
            //2、CKD≥4期"
            if(CHD_HAS.equals(baseInfo.getChd()) || (baseInfo.getCkd() != null && baseInfo.getCkd() >= 4)){
                memberControlTargetBO.setHighLDLCholesterol(HIGH_LDL_CHOLESTEROL_CHD);
            }else if(baseInfo.getHypLayer() != null){
                //高危
                if(baseInfo.getHypLayer() == 3){
                    memberControlTargetBO.setHighLDLCholesterol(2.59f);
                }else{
                    memberControlTargetBO.setHighLDLCholesterol(3.39f);
                }
            }
            //其他情况
            else{
                memberControlTargetBO.setHighLDLCholesterol(3.39f);
            }
        }
        //同时患高血压和糖尿病
        else if(HAS.equals(baseInfo.getEssentialHyp()) && HAS.equals(baseInfo.getIsDiabetes())){
            //有糖尿病并发症
            if(baseInfo.getDiabetesBfz() != null && baseInfo.getDiabetesBfz() == 1){
                memberControlTargetBO.setHighLDLCholesterol(1.79f);
            }
            //冠心病
            else if(CHD_HAS.equals(baseInfo.getChd())){
                memberControlTargetBO.setHighLDLCholesterol(1.79f);
            }
            else{
                memberControlTargetBO.setHighLDLCholesterol(2.59f);
            }
        }else if(HAS.equals(baseInfo.getIsDiabetes())){
            if(CHD_HAS.equals(baseInfo.getChd())){
                memberControlTargetBO.setHighLDLCholesterol(1.79f);
            }
            else{
                memberControlTargetBO.setHighLDLCholesterol(2.59f);
            }
        }

        //endregion 低密度脂蛋白

        //region 总胆固醇
        //高血压、2型糖尿病、妊娠糖尿病、同时患高血压和糖尿病（2型、妊娠）
        if(HAS.equals(baseInfo.getIsDiabetes()) || HAS.equals(baseInfo.getEssentialHyp())){
            memberControlTargetBO.setLowTCholesterol(4.49f);
            memberControlTargetBO.setHighTCholesterol(4.49f);
        }else{
            memberControlTargetBO.setLowTCholesterol(6.1f);
            memberControlTargetBO.setHighTCholesterol(6.1f);
        }
        //endregion 总胆固醇

        //region 甘油三酯
        if(HAS.equals(baseInfo.getIsDiabetes()) || HAS.equals(baseInfo.getEssentialHyp())){
            memberControlTargetBO.setLowTriglyceride(1.69f);
            memberControlTargetBO.setHighTriglyceride(1.69f);
        }else{
            memberControlTargetBO.setLowTriglyceride(2.2f);
            memberControlTargetBO.setHighTriglyceride(2.2f);
        }
        //endregion 甘油三酯

        //region 小于18岁控制目标(仅患高血压)
        if (HAS.equals(baseInfo.getEssentialHyp()) && (baseInfo.getIsDiabetes() == null || NOT_HAS.equals(baseInfo.getIsDiabetes())) && !isBlank(baseInfo.getHeight()) && age < 18){
            double height = Double.parseDouble(baseInfo.getHeight());
            Map<String, Float> hypMap = EighteenRangeHelper.getEighteenRange(age, height, baseInfo.hypBfz,baseInfo.getSex());
            memberControlTargetBO.setLowSystolicPress(hypMap.get("sbpLow"));
            memberControlTargetBO.setHighSystolicPress(hypMap.get("sbpHigh"));

            memberControlTargetBO.setLowDiastolicPress(hypMap.get("dbpLow"));
            memberControlTargetBO.setHighDiastolicPress(hypMap.get("dbpHigh"));
        }
        //endregion 小于18岁控制目标(仅患高血压)

        //region 红细胞计数
        if(sex == 1){
            memberControlTargetBO.setRBCCount("(4.0-5.5)×10^12/L");
        } else if(sex == 2){
            memberControlTargetBO.setRBCCount("(3.5-5.0)×10^12/L");
        }
        //endregion

        //region 血红蛋白浓度
        if(sex == 1){
            memberControlTargetBO.setLowMCHC(120f);
            memberControlTargetBO.setHighMCHC(160f);
        } else if(sex == 2){
            memberControlTargetBO.setLowMCHC(110f);
            memberControlTargetBO.setHighMCHC(150f);
        }
        //endregion

        //region 肌酐
        if(sex == 1){
            memberControlTargetBO.setLowCr(54f);
            memberControlTargetBO.setHighCr(106f);
        } else if(sex == 2){
            memberControlTargetBO.setLowCr(44f);
            memberControlTargetBO.setHighCr(97f);
        }
        //endregion

        //region 尿酸
        if(sex == 1){
            memberControlTargetBO.setLowUA(149f);
            memberControlTargetBO.setHighUA(416f);
        } else if(sex == 2){
            memberControlTargetBO.setLowUA(89f);
            memberControlTargetBO.setHighUA(357f);
        }
        //endregion

        //region 血沉
        if(sex == 1){
            memberControlTargetBO.setLowESR(0f);
            memberControlTargetBO.setHighESR(15f);
        } else if(sex == 2){
            memberControlTargetBO.setLowESR(0f);
            memberControlTargetBO.setHighESR(20f);
        }
        //endregion

        //region 铁
        if(sex == 1){
            memberControlTargetBO.setLowFe(10.7f);
            memberControlTargetBO.setHighFe(29.5f);
        } else if(sex == 2){
            memberControlTargetBO.setLowFe(9.1f);
            memberControlTargetBO.setHighFe(26.5f);
        }
        //endregion
        memberControlTargetBO.setHighKetone("0.3");
        return memberControlTargetBO;
    }

    /**
     * 设置血糖控制目标值
     * @param memberControlTargetBO
     * @param kfLow
     * @param kfHigh
     * @param fkfLow
     * @param fkfHigh
     */
    private static void setBloodSugarValue(MemberControlTargetBO memberControlTargetBO ,float kfLow ,float kfHigh ,float fkfLow ,float fkfHigh){
        memberControlTargetBO.setHighBeforeBreakfast(kfHigh);
        memberControlTargetBO.setLowBeforeBreakfast(kfLow);
        memberControlTargetBO.setHighAfterMeal(fkfHigh);
        memberControlTargetBO.setLowAfterMeal(fkfLow);
        memberControlTargetBO.setHighBeforedawn(fkfHigh);
        memberControlTargetBO.setLowBeforedawn(fkfLow);
        memberControlTargetBO.setHighBeforeMeal(fkfHigh);
        memberControlTargetBO.setLowBeforeMeal(fkfLow);
        memberControlTargetBO.setHighBeforeSleep(fkfHigh);
        memberControlTargetBO.setLowBeforeSleep(fkfLow);
    }

    /**
     * 控制目标的基础信息
     */
    public class BaseInfo{
        private Integer sex;
        private String chd;
        private String diabetesType;
        private String essentialHyp; //是否有高血压
        private String birthday;

        /**
         * 高血压分级分层
         */
        private Integer hypLayer;
        /**
         * CKD分期
         */
        private Integer ckd;

        /**
         * 是否有高血压并发症  1 确诊有 2 确诊无 3 疑似 4 未评估（有症状） 5 未评估(无症状)
         * 是否有高血压并发症  1 确诊有、疑似、有症状   2 确诊无、未评估、无症状
         */
        private Integer hypBfz;

        /**
         * 是否有糖尿病并发症 1 确诊有 2 确诊无
         */
        private Integer diabetesBfz;

        /**
         * 是否有糖尿病 1 有 2 无
         */
        private String isDiabetes;
        /**
         * 身高
         */
        private String height;

        public Integer getHypLayer() {
            return hypLayer;
        }

        public void setHypLayer(Integer hypLayer) {
            this.hypLayer = hypLayer;
        }

        public Integer getCkd() {
            return ckd;
        }

        public void setCkd(Integer ckd) {
            this.ckd = ckd;
        }

        public Integer getHypBfz() {
            return hypBfz;
        }

        public void setHypBfz(Integer hypBfz) {
            this.hypBfz = hypBfz;
        }

        public Integer getDiabetesBfz() {
            return diabetesBfz;
        }

        public void setDiabetesBfz(Integer diabetesBfz) {
            this.diabetesBfz = diabetesBfz;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getChd() {
            return chd;
        }

        public void setChd(String chd) {
            this.chd = chd;
        }

        public String getDiabetesType() {
            return diabetesType;
        }

        public void setDiabetesType(String diabetesType) {
            this.diabetesType = diabetesType;
        }

        public String getEssentialHyp() {
            return essentialHyp;
        }

        public void setEssentialHyp(String essentialHyp) {
            this.essentialHyp = essentialHyp;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIsDiabetes() {
            return isDiabetes;
        }

        public void setIsDiabetes(String isDiabetes) {
            this.isDiabetes = isDiabetes;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }
    /**
     * 判断字符串是否为空
     * 功能说明:
     * author：Suyz
     * 创建时间：2015-5-26
     * @param s
     * @return true:空，false:非空
     */
    private   static boolean isBlank(String s){
        if(s == null){
            return true;
        }
        if("".equals(s)){
            return true;
        }
        if("".equals(s.trim())){
            return true;
        }
        return false;
    }

    /**
     * 根据生日记算年龄
     * @param dateStr  生日
     * @return
     */
    private static int getAge(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
//			throw new IllegalArgumentException("dateStr 参数异常");
            return 0;
        }
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = format1.parse(dateStr);
            Date sysdate = new Date();
            Long time = sysdate.getTime() - date1.getTime();
            Double d1 = time / new Double(1000 * 60 * 60 * 24) - 1;
            String i = new DecimalFormat("#0.00").format((d1 / 365f));
            String[] age = i.split("\\.");
            return Integer.parseInt(age[0].replace("", "").length() < 1 ? "0": age[0]);
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 性别 1 男 2 女
     */
    private final static int SEX_MAN = 1;
    private final static int SEX_WOMAN = 2;

    /**
     * QZ01 确诊有
     */
    private final static String CHD_HAS = "QZ01";

    /**
     * SUGAR_TYPE_003 妊娠糖尿病  SUGAR_TYPE_002 2型
     */
    private final static String DIABETES_TYPE_GESTATION = "SUGAR_TYPE_003";
    private final static String DIABETES_TYPE_2 = "SUGAR_TYPE_002";

    /**
     * 是否有 1 有  2没有
     */
    private final static String HAS = "1";
    private final static String NOT_HAS = "2";
}
