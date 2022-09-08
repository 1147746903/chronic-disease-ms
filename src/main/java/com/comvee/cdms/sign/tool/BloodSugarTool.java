package com.comvee.cdms.sign.tool;

import com.comvee.cdms.sign.constant.SignConstant;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public class BloodSugarTool {

    /**
     * HIGH_MAX 高血糖高危值  LOW_MIN 低血糖高危值
     */
    private final static float HIGH_MAX = 16.7f;
    private final static float LOW_MIN = 3.9f;

    /**
     * 获取智能血糖建议
     * @param paramCode
     * @param paramLevel
     * @return
     */
    public static String getIntelligentSuggestion(String paramCode,int paramLevel, Float paramValue){
        String suggestion = "";
        if(paramValue >= HIGH_MAX){
            suggestion = "分析血糖高的原因，若反复或持续性严重高血糖应到医院复诊并调整治疗方案。";
        }else if(paramValue <= LOW_MIN){
            suggestion = "您刚刚发生低血糖，现感觉有好点吗？发生低血糖时应进食15克含糖食物，15分钟后要记得监测血糖，看血糖恢复情况，从而采取进一步干预措施，注意当有意识障碍或低血糖进食2次含糖食物仍无改善情况建议您就近就医。";
        }else if(paramLevel == SignConstant.SIGN_LEVEL_LOW){
            suggestion = getLowSuggestion(paramCode);
        }else if(paramLevel == SignConstant.SIGN_LEVEL_HIGH){
            suggestion = getHighSuggestion(paramCode);
        }else{
            suggestion = "您的血糖正常，请继续保持";
        }
        return suggestion;
    }

    /**
     * 获取偏低建议
     * @param paramCode
     * @return
     */
    private static String getLowSuggestion(String paramCode){
        String suggestion = "";
        //空腹
        if(SignConstant.PARAM_CODE_BEFORE_BREAKFAST.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_KF_LOW);
            //餐前
        }else if(SignConstant.PARAM_CODE_BEFORE_DINNER.equals(paramCode)
                || SignConstant.PARAM_CODE_BEFORE_LUNCH.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_CQ_LOW);
            //餐后
        }else if(SignConstant.PARAM_CODE_AFTER_BREAKFAST.equals(paramCode)
                || SignConstant.PARAM_CODE_AFTER_DINNER.equals(paramCode)
                || SignConstant.PARAM_CODE_AFTER_LUNCH.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_CH_LOW);
            //睡前
        }else if(SignConstant.PARAM_CODE_BEFORE_SLEEP.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_SQ_LOW);
            //随机 || 凌晨
        }else if(SignConstant.PARAM_CODE_RANDOM_TIME.equals(paramCode)
                || SignConstant.PARAM_CODE_3AM.equals(paramCode)
                || SignConstant.PARAM_CODE_BEFORE_DAWN.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_LC_LOW);
        }
        return suggestion;
    }

    /**
     * 获取偏高建议
     * @param paramCode
     * @return
     */
    private static String getHighSuggestion(String paramCode){
        String suggestion = "";
        //空腹
        if(SignConstant.PARAM_CODE_BEFORE_BREAKFAST.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_KF_HIGH);
            //餐前
        }else if(SignConstant.PARAM_CODE_BEFORE_DINNER.equals(paramCode)
                || SignConstant.PARAM_CODE_BEFORE_LUNCH.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_CQ_HIGH);
            //餐后
        }else if(SignConstant.PARAM_CODE_AFTER_BREAKFAST.equals(paramCode)
                || SignConstant.PARAM_CODE_AFTER_DINNER.equals(paramCode)
                || SignConstant.PARAM_CODE_AFTER_LUNCH.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_CH_HIGH);
            //睡前
        }else if(SignConstant.PARAM_CODE_BEFORE_SLEEP.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_SQ_HIGH);
            //随机 || 凌晨
        }else if(SignConstant.PARAM_CODE_RANDOM_TIME.equals(paramCode)
                || SignConstant.PARAM_CODE_3AM.equals(paramCode)
                || SignConstant.PARAM_CODE_BEFORE_DAWN.equals(paramCode)){
            suggestion = getSuggesion(SUGAR_ERR_LC_HIGH);
        }
        return suggestion;
    }

    /**
     * 获取建议
     * @param strings
     * @return
     */
    private static String getSuggesion(String[] strings){
        return strings[getRandomIndex(strings.length)];
    }

    /**
     * 获取随机下标
     * @param length
     * @return
     */
    private static int getRandomIndex(int length){
        return ThreadLocalRandom.current().nextInt(length);
    }

    public static String[] SUGAR_ERR_KF_LOW={
            "您好，从您监测的血糖来看，您的空腹血糖偏低，建议您测下睡前血糖，如果睡前血糖低于5.6mmol/L，建议您要喝杯牛奶或吃点饼干或水果，避免夜间低血糖。",
            "您有出现空腹低血糖，是晚餐控制太严格了？空腹运动？或您没有遵医嘱用药？都会引起低血糖，建议您要监测睡前血糖，若睡前血糖低于5.6mmool/l，建议您适当加餐，防止您出现夜间乃至空腹低血糖现象",
            "早上您的血糖有出现偏低的情况。您是否有空腹运动的情况呢？建议您最好要避免空腹运动，加强对空腹血糖的监测，一有出现血糖偏低的情况下立刻吃一些含糖量比较高的食物，15分钟过后，继续测下血糖",
            "您好，发生低血糖时应及时进食15g糖类食品，15分钟后再次监测，若血糖仍未恢复，可再服用等量糖水直到血糖恢复，若此时血糖仍无恢复到3.9mmol/l以上，就需要到附近医院就医，以防发生危险。",
            "您空腹出现了低血糖，一次的低血糖对脑细胞造成不可逆转的损害，经常出现无症状性低血糖的话就要在您之前经常发生低血糖的时段加强血糖监测，以及时捕捉到低血糖，并进行加餐，另外，三餐定时定量，规律适量运动，规律用药，都能预防低血糖再次发生。",
    };
    public static String[] SUGAR_ERR_CQ_LOW={
            "您好，您出现餐前低血糖了，您当时会有不舒服的症状吗？您平常在控制高血糖的同时要注意预防低血糖。加强餐前血糖的监测，以及时捕捉到低血糖，并进行加餐，另外，三餐定时定量，规律适量运动，规律用药，都能预防低血糖再次发生。",
            "您好，您餐前血糖偏低，现在恢复正常了吗？您下次出现低血糖时，如临近用餐时间可以早点就餐，或及时进食含15g糖的食物，并过15分钟监测一下血糖，直到恢复正常。",
            "您好，您有出现餐前低血糖，延误吃饭导致的吗？低血糖相对于高血糖更危险，会对人体造成不可逆的损伤，尤其是脑细胞，希望您要重视。若三餐无法定时定量进餐，建议您要先吃点点心（全麦面包、牛奶、水果等），预防低血糖。",
            "您好，您之前餐前血糖偏低，出现低血糖的原因您知道吗？是因为前一餐吃得比较少或者运动量增大呢？如果排查不出原因，建议您接下来注意监测餐前血糖，预防低血糖的发生。",
            "您餐前会低血糖，如果在饮食、运动等正常情况下，还是一直低血糖，建议您及时就医，与您的主治医生商讨治疗方案；饮食不必太过严格控制，记得加餐，可以吃三片的苏打饼干、一片全麦吐司或一个西红柿、黄瓜。",

    };

    public static String[] SUGAR_ERR_CH_LOW={
            "您餐后低血糖，是您饮食不规律或控制太严格了，或用药或注射胰岛素剂量没有遵医嘱？建议您合理安排饮食，可分三餐两点心进食，按医嘱用药/注射胰岛素，身边常备糖果，密切观察血糖情况，预防低血糖",
            "您的餐后血糖偏低了，是不是没有按时进餐或吃的较少呢？建议您提前做好食物补充的的准备，预防低血糖，饮食定时定量，少食多餐，密切观察血糖情况可以避免血糖大范围的波动",
            "您好，您有出现餐后低血糖，延误吃饭导致的吗？运动量过大或药物调整了剂量引起的餐后血糖低呢？建议您排查好低血糖的原因，及时调整不当的生活方式，避免出现低血糖的症状，如不是这些原因引起的，可与主治医生咨询调整下用药方案。",
            "您刚刚发生低血糖，现感觉有好点吗？发生低血糖时应进食15克含糖食物，15分钟后要记得监测血糖，看血糖恢复情况，从而采取进一步干预措施，当低血糖进食2次含糖食物仍无改善情况建议您就近就医",
            "您好，关注到您有餐后低血糖的情况，是不是没在医生的指导下调整治疗方案或是正餐吃得少了呢？建议您遵医嘱，避免过分控制当餐饮食，低血糖的危害性远大于高血糖，血糖控制宁高勿低，祝您健康。",

    };

    public static String[] SUGAR_ERR_SQ_LOW={
            "您睡前血糖有点低，是因为晚餐吃太少了还是运动量增加或药物用多了而引起的呢？建议您遵医嘱用药，餐后血糖低于控制目标时，要预防睡前低血糖了，可以适当的加餐，加餐食品可以选择一个西红柿或黄瓜或半个苹果，低血糖的危害性远大于高血糖哦，要引起重视哦。",
            "您好，关注您睡前血糖有些偏低，为了预防出现夜间低血糖，建议可适当进食些睡前点心，比如西红柿、猕猴桃、低脂无糖牛奶等低升糖指数的食物。日常预防高低血糖：可每天多吃几顿，主餐少吃一点儿，在两餐之间适当的加餐，基本上要做到一天不少于三餐，一餐不多于2两，避免一顿吃得太多。",
            "您好！您刚刚出现睡前低血糖了，有感觉不适吗（心慌手抖出冷汗）？出现低血糖时按照“吃15等15”的原则，立即进食15克含糖食品（3-4片葡萄糖片、100ml含糖饮料、1大汤勺蜂蜜等），15分钟后复测血糖，观察是否恢复到正常水平。",
            "关注您的睡前血糖有些偏低，可能跟您晚餐吃的过少有关，预防低血糖建议餐次安排要科学，可以每天多吃几顿，每顿少吃一点儿，避免加重胰岛的负担，基本上要做到一天不少于三餐，一餐主食不多于2两（生重），避免一顿吃得太多。",
    };

    public static String[] SUGAR_ERR_LC_LOW={
            "您有出现凌晨低血糖的情况，建议您在进食15g含糖食品，15分钟后要监测血糖是否恢复，若是血糖恢复后，可进食点碳水化合物如面包、糕点等预防再次发生低血糖",
            "您发生低血糖时应及时进食15克含糖食物，15分钟后在监测，若血糖无恢复，可再进食15克含糖食物，15分钟后再监测血糖，若此时血糖仍无恢复到3.9mmol/l以上，就需要到附近医院就医，以防发生危险",
            "您好，糖尿病患者低血糖对心血管系统和中枢神经系统有不利影响，一般低血糖常见于摄入量不足、未按医嘱用药、或者运动不科学、有时空腹饮酒也会引起低血糖，如果经常低血糖可能危及健康，则需要及时就医调整。",
    };


    public static String[] SUGAR_ERR_KF_HIGH={
            "您的空腹血糖偏高，建议您严格监测早餐后、晚餐前后及睡前的血糖，并把睡前的血糖控制在控制目标内再观察空腹血糖水平，并加强饮食、运动控制，多与主治医生沟通，制定适合自己的治疗方案。",
            "您的空腹血糖偏高，引起空腹血糖偏高的原因有很多，如：晚餐吃得太晚、晚餐有饮酒习惯、夜宵习惯、没有遵医嘱用药、运动不科学，您可以对照自查下，针对相关原因做相应调整。",
            "您的空腹血糖偏高，如近期持续血糖高，建议就医复诊调整治疗方案，就诊前三天建议连续监测全天5-7个点血糖，包括三餐前后及睡前，有助于医生评估整体血糖和调整治疗方案。",
            "您的空腹血糖偏高，建议您遵医嘱规律用药同时，加强血糖监测，尤其是睡前、凌晨3点，分析血糖开始偏高的时间点，调整日常饮食，控制全天总热量，若不能改善，请监测全天七个点血糖，三餐前后及睡前血糖。",
            "空腹血糖受晚餐、运动量、压力、天气、睡眠等因素的影响，建议您定期监测血糖，配合血糖管理，有助于改善空腹血糖。",

    };
    public static String[] SUGAR_ERR_CQ_HIGH={
            "您好，您的餐前血糖偏高了，是因为前一餐吃的太饱，还是加餐了？建议您餐后1小时适当运动，可以从走路开始，循序渐进。",
            "餐前血糖偏高了，是否是前一餐吃得太丰盛了呢？还是有加餐？建议饮食宜清淡，控制总摄入量，每餐主食不宜超过2两（生重），辛辣油腻、煎炸的食品少吃，多吃新鲜蔬菜，如有加餐建议要把上一顿的主食匀出来1/4用来加餐，加餐的食物选择低GI的食物。",
            "您好，您今天餐前血糖高，引起血糖偏高的原因有很多，如：前一餐吃得太晚、有饮酒习惯、没有遵医嘱用药、运动不科学，您可以对照自查下，针对相关原因做相应调整，祝您健康。",
    };

    public static String[] SUGAR_ERR_CH_HIGH={
            "您好。您餐后血糖偏高了，可能跟您餐前血糖偏高、正餐摄入太多有关系，推荐您时间允许的情况下适当安排饭后散步或者快走30分钟帮助消耗血糖，维持血糖稳定，祝您健康。",
            "您餐后血糖偏高，是不是饮食没有控制啊？建议您饮食定时定量，少食多餐，主食以您的拳头大小的份额为一份，青菜每天1斤左右，鱼和肉一天1.5两左右.祝您健康。",
            "您今天餐后血糖偏高了哦，是否是吃得太丰盛了点呢？建议饮食宜清淡，控制总摄入量，每餐主食不宜超过2两（生重），辛辣油腻、煎炸的食品少吃，多吃新鲜蔬菜。",
            "关注到您餐后血糖偏高，建议您遵循医生的治疗方案，不要擅自调整，必要时可就医复诊，长期高血糖容易引起糖尿病的急慢性并发症，要引起重视哦，祝您健康。",
    };

    public static String[] SUGAR_ERR_SQ_HIGH={
            "关注到您今天睡前血糖偏高，建议您保持规律饮食，定时定量，严格控制每日的总量，尽量避免吃夜宵、点心、零食等习惯，适量增加运动，有助于降低血糖并提高胰岛素的敏感性。",
            "您睡前血糖有些高了，不知是不是晚餐量不小心吃多了？建议您饮食少油炸，少甜腻，餐后合理运动,减轻体重,对控制血糖是有帮助的。坚持锻炼,每周至少5次,每次30分钟。如快走、慢跑、骑自行车、登楼梯、爬山坡等。",
            "您好，您的睡前血糖偏高，引起睡前血糖偏高的原因有很多，如：晚餐吃得太晚、晚餐有饮酒习惯、夜宵习惯、没有遵医嘱用药、运动不科学，您可以对照自查下，针对相关原因做相应调整。祝您健康。",
            "您今天睡前血糖偏高，建议您遵循医生的治疗方案，不要擅自调整，必要时可就医复诊；另外，保持良好的饮食习惯，适当运动，配合血糖监测也有助于改善血糖情况。",
    };

    public static String[] SUGAR_ERR_LC_HIGH={
            "您有出现凌晨高血糖的情况，建议监测晚餐后及睡前血糖。如果测得的血糖偏高则可以先从晚餐进餐量，饮食结构，晚餐后运动时长，运动方式等方面自我调整配合血糖监测观察变化，若无改善，及时就医复查。",
            "您凌晨血糖偏高了，原因：1、较高的睡前血糖一直持续到凌晨血糖都是偏高的；2、睡前血糖正常，但凌晨期间机体分泌升糖激素导致血糖升高，即“黎明现象”。建议：您加测睡前血糖，观察3-4天情况如何。若睡前血糖偏高，跟您晚餐有较大关系；若睡前血糖正常，则需要咨询医生必要时调整治疗方案",
    };
}
