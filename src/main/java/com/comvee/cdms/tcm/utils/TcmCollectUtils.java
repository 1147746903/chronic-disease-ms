package com.comvee.cdms.tcm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.tcm.constant.TcmConstant;
import com.comvee.cdms.tcm.model.po.TcmCollectQueAnsPO;
import com.comvee.cdms.tcm.model.po.TcmCollectQuePO;
import com.comvee.cdms.tcm.model.vo.TcmHealthElementVO;

import java.util.*;

public class TcmCollectUtils {

    public static void calculateFeature(TcmHealthElementVO vo, int feature) {
        String element = null;
        switch (feature) {
            case TcmConstant.TCM_FEATURE_METAL:
                element = TcmConstant.TCM_ELEMENT_FEATURE_METAL;
                break;
            case TcmConstant.TCM_FEATURE_WOOD:
                element = TcmConstant.TCM_ELEMENT_FEATURE_WOOD;
                break;
            case TcmConstant.TCM_FEATURE_WATER:
                element = TcmConstant.TCM_ELEMENT_FEATURE_WATER;
                break;
            case TcmConstant.TCM_FEATURE_FIRE:
                element = TcmConstant.TCM_ELEMENT_FEATURE_FIRE;
                break;
            case TcmConstant.TCM_FEATURE_EARTH:
                element = TcmConstant.TCM_ELEMENT_FEATURE_EARTH;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }

    //舌色 1：黑舌、2：绛舌、3：暗舌、4：淡舌、5：紫舌、6：红舌、7：白舌、8：淡紫舌
    public static void calculateTongueColor(TcmHealthElementVO vo, int tongueColor) {
        String element = null;
        switch (tongueColor) {
            case TcmConstant.TCM_TONGUE_COLOR_BLACK:
                break;
            case TcmConstant.TCM_TONGUE_COLOR_DEEP_RED://热 +9 血热 +5 阴虚 +5  津亏 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_DEEP_RED;
                break;
            case TcmConstant.TCM_TONGUE_COLOR_DARK://血瘀 +10 气滞 +8 气虚 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_DARK;
                break;
            case TcmConstant.TCM_TONGUE_COLOR_TINGE://血虚 +7 气虚 +5 阳虚 +5
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_TINGE;
                break;
            case TcmConstant.TCM_TONGUE_COLOR_PURPLE://血瘀 +6 气滞 +2 热 +2 胃 +2 肝 +2 心 +2
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_PURPLE;
                break;
            case TcmConstant.TCM_TONGUE_COLOR_RED://热 +7 阴虚 +4 津亏 +3
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_RED;
                break;
            case TcmConstant.TCM_TONGUE_COLOR_WHITE:
                break;
            case TcmConstant.TCM_TONGUE_COLOR_PALE_PURPLE://阳虚 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_PALE_PURPLE;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }

    //舌质 1：适中、2：胖大、3：瘦薄、4：齿痕、5：裂纹、6：老、7：嫩、 8:肿胀、9：点刺
    public static void calculateTongueBody(TcmHealthElementVO vo, int tongueBody) {
        String element = null;
        switch (tongueBody) {
            case TcmConstant.TCM_TONGUE_BODY_MODERATION:
                break;
            case TcmConstant.TCM_TONGUE_BODY_FAT://血瘀 +3 热 +4 饮 +4 气虚 +4 湿 +4 阳虚 +4 脾 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_FAT;
                break;
            case TcmConstant.TCM_TONGUE_BODY_THIN://热 +4 阴虚 +5 血虚 +5 气虚 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_THIN;
                break;
            case TcmConstant.TCM_TONGUE_BODY_BITE://齿痕 热 -3 饮 +4 血虚 +4 气虚 +4 湿 +5 阳虚 +4 脾 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_BITE;
                break;
            case TcmConstant.TCM_TONGUE_BODY_CRACK://热 +3 阴虚 +2 血虚 +3 气虚 +2 湿 +3 精亏 +1 津亏 +3
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_CRACK;
                break;
            case TcmConstant.TCM_TONGUE_BODY_AGED://血瘀 +8 气滞 +8 痰 +8 湿 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_AGED;
                break;
            case TcmConstant.TCM_TONGUE_BODY_YOUNG://血瘀 -6 阴虚 +8  痰 -8 血虚 +8 气虚 +8 湿 -8 阳虚 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_YOUNG;
                break;
            case TcmConstant.TCM_TONGUE_BODY_SWELL://血瘀 +5 热 +5 湿 +5 脾 +4 心 +5
                element = TcmConstant.TCM_ELEMENT_TONGUE_BODY_SWELL;
                break;
            case TcmConstant.TCM_TONGUE_BODY_PIMPLE://血瘀 +2 气滞 +2 热 +3 血热 +4 胃 +2 肝 +1 脾 +1 胆 +1 大肠 +1 心神 +1 心 +2
                element = TcmConstant.TCM_ELEMENT_TONGUE_COLOR_PALE_PURPLE;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }

    //苔色 1：黑苔、2：黑白相间苔、3：燥苔、4：染苔、5：灰黑苔、6：润苔、7：焦黄苔、8：厚苔、9：薄苔、10：白苔、11：黄苔、12：淡黄苔、13：黄白相间苔
    public static void calculateCoatingColor(TcmHealthElementVO vo, int coatingColor) {
        String element = null;
        switch (coatingColor) {
            case TcmConstant.TCM_COATING_COLOR_GRAY_BLACK://热 +14 阴虚 +10 阳虚 +13 津亏 +7
                element = TcmConstant.TCM_ELEMENT_COATING_COLOR_GRAY_BLACK;
                break;
            case TcmConstant.TCM_COATING_COLOR_YELLOW://热 +8 阴虚 +5
                element = TcmConstant.TCM_ELEMENT_COATING_COLOR_YELLOW;
                break;
            case TcmConstant.TCM_COATING_COLOR_LIGHT_YELLOW://热 +5 阴虚 +3
                element = TcmConstant.TCM_ELEMENT_COATING_COLOR_LIGHT_YELLOW;
                break;
            case TcmConstant.TCM_COATING_COLOR_YELLOW_WHITE://热 +2
                element = TcmConstant.TCM_ELEMENT_COATING_COLOR_YELLOW_WHITE;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }

    //苔质 1；薄、2：厚、3：腐、4：腻、5：燥、6：焦、7：积粉、8：滑、9：剥、10：类剥
    public static void calculateTongueFur(TcmHealthElementVO vo, int tongueFur) {
        String element = null;
        switch (tongueFur) {
            case TcmConstant.TCM_TONGUE_FUR_THIN://痰 -6 湿 -6
                element = TcmConstant.TCM_ELEMENT_TONGUE_FUR_THIN;
                break;
            case TcmConstant.TCM_TONGUE_FUR_THICK://痰 +8 湿 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_FUR_THICK;
                break;
            case TcmConstant.TCM_TONGUE_FUR_SLIDE://饮 +4 湿 +4 津亏 -3
                element = TcmConstant.TCM_ELEMENT_TONGUE_FUR_SLIDE;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }


    //舌态 1：弄舌、2：舌痿、3；舌短、4：舌强、5：舌颤  6:吐舌  7:舌歪
    public static void calculateTongueCondition(TcmHealthElementVO vo, int tongueCondition) {
        String element = null;
        switch (tongueCondition) {
            case TcmConstant.TCM_TONGUE_CONDITION_WAGGLE://热 +4 动风 +4 精亏 +5 肝 +5 肾 +5
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_WAGGLE;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_FLACCID://热 +3 阴虚 +5 血虚 +3 气虚 +3 精亏 +3 肝 +3 肾 +3
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_FLACCID;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_SHORT://热 +2 痰 +2 动风 +2 血虚 +2 气虚 +2 津亏 +2
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_SHORT;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_STIFF://热 +5 痰 +8 动风 +9 津亏 +4 心神 +4 心 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_STIFF;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_TREMBLE://热 +5 阴虚 +2 阳亢 +2 动风 +8 血虚 +2 肝 +2
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_TREMBLE;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_STICK_OUT://热 +5 脾 +4 心 +4
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_STICK_OUT;
                break;
            case TcmConstant.TCM_TONGUE_CONDITION_WRY://血瘀 +5 痰 + 10 动风 +8
                element = TcmConstant.TCM_ELEMENT_TONGUE_CONDITION_WRY;
                break;
        }
        if (!StringUtils.isBlank(element)) {
            addHealthElement(vo, element);
        }
    }

    public static void calculateQue(TcmHealthElementVO vo, HashMap<String, String> ans) {
        for (String queId : ans.keySet()) {
            List<String> ansList = null;
            String ansStr = ans.get(queId);
            if (!StringUtils.isBlank(ansStr)) {
                ansList = JSON.parseArray(ansStr, String.class);
            }
            if (ansList == null) continue;
            switch (queId) {
                case "3":
                    if (ansList.contains("13")) {
                        String json = "{'qiXu':6,'yangXu':8}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "4":
                    if (ansList.contains("16")) {
                        String json = "{'qiZhi':12,'thermal':10,'phlegm':20,'wet':15,'spleen':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("19")) {
                        String json = "{'thermal':20,'yinXu':15,'stomach':15,'spleen':10,'largeIntestine':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("17")) {
                        String json = "{'qiZhi':15,'wet':10,'yangXu':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("20")) {
                        String json = "{'yangXu':15}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "5":
                    if (ansList.contains("27")) {
                        String json = "{'phlegm':25,'qiXu':18,'wet':30,'spleen':40,'mind':25}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("24")) {
                        String json = "{'thermal':16,'yinXu':18,'bloodDeficiency':22,'liver':12,'mind':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("25")) {
                        String json = "{'thermal':50,'yinXu':10,'phlegm':25,'bloodDeficiency':10,'stomach':18,'liver':15,'gallbladder':50,'kidney':6,'mind':55,'heart':35}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("22")) {
                        String json = "{'qiZhi':16,'yinXu':22,'phlegm':14,'bloodDeficiency':18,'qiXu':15,'spleen':20,'mind':14}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("26")) {
                        String json = "{'thermal':12,'yinXu':10,'phlegm':20,'qiXu':10,'wet':15,'jingKui':15,'yangXu':16,'stomach':18,'liver':15,'gallbladder':12,'kidney':18,'mind':30,'heart':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("23")) {
                        String json = "{'bloodStasis':15,'phlegm':25,'bloodDeficiency':16,'qiXu':25,'wet':20,'yangXu':16,'mind':30}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "6":
                    if (ansList.contains("30")) {
                        String json = "{'qiXu':24,'wet':30,'yangXu':32,'spleen':30,'kidney':12,'largeIntestine':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("31")) {
                        String json = "{'yinXu':30,'bloodDeficiency':24,'qiXu':12,'yangXu':12,'jinKui':50,'stomach':30,'spleen':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("33")) {
                        String json = "{'jinKui':50}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("34")) {
                        String json = "{'bloodStasis':14,'qiZhi':20,'thermal':30,'yinXu':18,'bloodDeficiency':10,'qiXu':12,'yangXu':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("29")) {
                        String json = "{'qiXu':18,'wet':30,'yangXu':18,'spleen':40}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("32")) {
                        String json = "{'qiZhi':30,'qiXu':18,'liver':30,'spleen':30}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("35")) {
                        String json = "{'bloodStasis':15,'qiZhi':10,'thermal':18,'qiXu':12,'wet':8,'stomach':10,'spleen':15,'largeIntestine':30}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("")) {
                        String json = "";
                        addHealthElement(vo, json);
                    }
                    break;
                case "7":
                    if (ansList.contains("41")) {
                        String json = "{'bloodStasis':12,'thermal':25,'bloodDeficiency':16,'qiXu':20,'wet':20,'yangXu':25,'spleen':16,'kidney':30,'bladder':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("40")) {
                        String json = "{'thermal':22,'qiXu':24,'wet':20,'yangXu':32,'kidney':48,'bladder':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("38")) {
                        String json = "{'thermal':40,'yinXu':20,'jinKui':25,'smallIntestine':20,'bladder':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("42")) {
                        String json = "{'bloodStasis':15,'qiZhi':10,'thermal':30,'wet':30,'bladder':70}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("39")) {
                        String json = "{'thermal':15,'qiXu':20,'wet':20,'spleen':20,'kidney':12,'bladder':31}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("37")) {
                        String json = "{'qiXu':30,'yangXu':35,'spleen':15,'kidney':40}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "8":
                    if (ansList.contains("44")) {
                        String json = "{'thermal':-20,'yinXu':-16,'qiXu':15,'yangXu':50,'spleen':15,'kidney':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("46")) {
                        String json = "{'qiXu':22,'yangXu':10,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("45")) {
                        String json = "{'thermal':30,'yinXu':10,'yangXu':-20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("43")) {
                        String json = "{'qiZhi':15,'thermal':12,'wet':15,'liver':15,'gallbladder':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("44") && ansList.contains("45")) {
                        String json = "{'qiXu':30,'spleen':12,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("48")) {
                        String json = "{'qiZhi':40,'thermal':30,'yinXu':20,'liver':20,'kidney':16}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("49")) {
                        String json = "{'qiZhi':16,'thermal':25,'yinXu':20,'wet':20,'stomach':12,'liver':10,'kidney':20,'largeIntestine':12}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "9":
                    if (ansList.contains("51")) {
                        String json = "{'qiZhi':30,'yinXu':-1,'phlegm':30,'qiXu':-30,'wet':30,'yangXu':-10,'spleen':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("")) {
                        String json = "{'qiXu':50,'yangXu':14,'spleen':24,'kidney':12,'lung':15,'heart':15}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "10":
                    if (ansList.contains("55")) {
                        String json = "{'bloodStasis':18,'thermal':10,'bloodHeat':18,'yin':18,'yinXu':12,'phlegm':18,'wet':18,'yangXu':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("54")) {
                        String json = "{'thermal':30,'yinXu':18,'jinKui':50}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("58")) {
                        String json = "{'thermal':20,'yinXu':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("56")) {
                        String json = "{'bloodStasis':30,'bloodHeat':6,'yin':20,'phlegm':20,'wet':20,'yangXu':25}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("59")) {
                        String json = "{'bloodStasis':10,'thermal':-15,'phlegm':12,'wet':10,'yangXu':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("53")) {
                        String json = "{'thermal':-15,'yinXu':-8,'wet':12,'yangXu':10,'jinKui':-30}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "11":
                    if (ansList.contains("64")) {
                        String json = "{'bloodStasis':8,'yangKang':12,'phlegm':8,'wind':6,'bloodDeficiency':7,'wet':10,'jingKui':7,'liver':10,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("61")) {
                        String json = "{'bloodStasis':12,'thermal':18,'yangKang':15,'phlegm':18,'wind':12,'liver':18,'gallbladder':25}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("62")) {
                        String json = "{'bloodStasis':15,'qiZhi':15,'thermal':15,'yinXu':10,'wind':15,'bloodDeficiency':10,'liver':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("65")) {
                        String json = "{'bloodStasis':18,'thermal':18,'phlegm':18,'bloodDeficiency':24,'qiXu':18,'wet':15,'jingKui':18,'liver':22,'gallbladder':18,'kidney':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("66")) {
                        String json = "{'bloodStasis':12,'yinXu':5,'bloodDeficiency':10,'wet':12,'yangXu':5,'kidney':8}";
                        addHealthElement(vo, json);
                    }
                    break;

                case "12":
                    if (ansList.contains("68")) {
                        String json = "{'thermal':14,'yinXu':23,'bloodDeficiency':15,'jingKui':18,'liver':20,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("70")) {
                        String json = "{'thermal':24,'yinXu':15,'yangKang':15,'liver':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("71")) {
                        String json = "{'yinXu':18,'bloodDeficiency':22,'jingKui':24,'liver':20,'kidney':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("72")) {
                        String json = "{'thermal':24,'liver':20,'lung':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("69")) {
                        String json = "{'thermal':18,'bloodDeficiency':18,'liver':20}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "13":
                    if (ansList.contains("74")) {
                        String json = "{'qiXu':15,'lung':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("75")) {
                        String json = "{'lung':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("76")) {
                        String json = "{'thermal':25,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("77")) {
                        String json = "{'thermal':18,'wet':14,'liver':15,'spleen':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("78")) {
                        String json = "{'qiXu':12,'yangXu':12,'spleen':10,'kidney':8,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("79")) {
                        String json = "{'thermal':28,'bloodHeat':14,'yinXu':12,'qiXu':10,'stomach':16,'liver':16,'spleen':10,'lung':30}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "14":
                    if (ansList.contains("81")) {
                        String json = "{'thermal':5,'yinXu':15,'bloodDeficiency':15,'qiXu':12,'jingKui':20,'liver':25,'spleen':18,'kidney':20,'heart':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("83")) {
                        String json = "{'thermal':3,'yinXu':20,'bloodDeficiency':24,'qiXu':18,'jingKui':30,'liver':35,'spleen':24,'kidney':30,'heart':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("85")) {
                        String json = "{'thermal':15,'wind':18,'wet':18,'liver':15,'gallbladder':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("86")) {
                        String json = "{'thermal':30,'wet':24,'liver':30,'gallbladder':50}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("82")) {
                        String json = "{'bloodStasis':15,'qiZhi':8,'thermal':24,'yangKang':20,'phlegm':15,'liver':15,'gallbladder':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("84")) {
                        String json = "{'bloodStasis':12,'qiZhi':15,'thermal':36,'yangKang':30,'phlegm':20,'liver':30,'gallbladder':48}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "15":
                    if (ansList.contains("97")) {
                        String json = "{'thermal':15,'yinXu':15,'jinKui':24,'spleen':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("98")) {
                        String json = "{'bloodStasis':18,'thermal':30,'yinXu':20,'bloodDeficiency':12,'qiXu':12,'stomach':30,'liver':10,'spleen':18,'kidney':8}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("99")) {
                        String json = "{'yinXu':18,'bloodDeficiency':16,'qiXu':18,'jingKui':30,'yangXu':18,'kidney':40}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("94")) {
                        String json = "{'thermal':24,'yinXu':18,'wet':18,'stomach':18,'spleen':30,'heart':30}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("88")) {
                        String json = "{'wet':15,'yangXu':18,'stomach':15,'spleen':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("89")) {
                        String json = "{'thermal':18,'yinXu':10,'liver':10,'gallbladder':15,'heart':14}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("90")) {
                        String json = "{'thermal':12,'qiXu':12,'wet':12,'spleen':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("91")) {
                        String json = "{'stomach':12,'liver':15,'spleen':8}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("93")) {
                        String json = "{'thermal':15,'jinKui':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("92")) {
                        String json = "{'yin':10,'jingKui':14,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("95")) {
                        String json = "{'thermal':18,'phlegm':15,'wet':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("96")) {
                        String json = "{'thermal':18,'bloodHeat':15,'stomach':15,'largeIntestine':15}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "16":
                    if (ansList.contains("101")) {
                        String json = "{'bloodStasis':18,'jinKui':24,'liver':12,'kidney':12,'lung':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("102")) {
                        String json = "{'thermal':24,'yinXu':18,'stomach':18,'lung':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("104")) {
                        String json = "{'bloodStasis':30,'phlegm':20,'stomach':30,'liver':22,'spleen':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("105")) {
                        String json = "{'thermal':24,'phlegm':15,'jinKui':20,'lung':25}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("106")) {
                        String json = "{'bloodStasis':16,'qiZhi':16,'yinXu':24,'phlegm':18,'qiXu':24,'liver':15,'kidney':20,'lung':25}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "17":
                    if (ansList.contains("111")) {
                        String json = "{'thermal':18,'phlegm':18,'liver':12,'lung':50}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("109")) {
                        String json = "{'phlegm':18,'wet':10,'spleen':10,'lung':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("110") && ansList.contains("118")) {
                        String json = "{'bloodStasis':12,'qiZhi':16,'thermal':16,'yinXu':10,'phlegm':18,'yangXu':16,'liver':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("106")) {
                        String json = "{'yinXu':18,'qiXu':18,'yangXu':18,'spleen':12,'kidney':12,'lung':18}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "18":
                    if (ansList.contains("113")) {
                        String json = "{'thermal':18,'yinXu':18,'lung':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("114")) {
                        String json = "{'yin':12,'phlegm':25,'wet':15,'yangXu':12,'spleen':18,'lung':35}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("115")) {
                        String json = "{'yin':12,'wet':10,'yangXu':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("116")) {
                        String json = "{'thermal':18,'yinXu':6}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("120")) {
                        String json = "{'yangXu':12,'lung':18,'heart':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("121")) {
                        String json = "{'thermal':18,'phlegm':18,'wet':10,'jinKui':15,'lung':24}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "19":
                    if (ansList.contains("128")) {
                        String json = "{'thermal':-15,'phlegm':16}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("127")) {
                        String json = "{'thermal':30,'phlegm':16}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("129")) {
                        String json = "{'thermal':20,'yinXu':12,'phlegm':16}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("132")) {
                        String json = "{'qiZhi':12,'thermal':18,'yinXu':24,'jinKui':18,'lung':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("123")) {
                        String json = "{'qiZhi':12,'thermal':12,'yinXu':12,'spleen':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("124")) {
                        String json = "{'yin':18,'phlegm':40,'wet':18,'spleen':30,'kidney':15,'lung':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("125")) {
                        String json = "{'qiXu':12,'yangXu':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("130")) {
                        String json = "{'qiXu':15,'yangXu':24,'heart':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("131")) {
                        String json = "{'thermal':18,'yinXu':24,'phlegm':18,'wet':10,'jinKui':18}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "20":
                    if (ansList.contains("134")) {
                        String json = "{'bloodStasis':18,'qiZhi':18,'yin':10,'yinXu':18,'phlegm':18,'bloodDeficiency':18,'qiXu':18,'yangXu':18,'spleen':18,'gallbladder':18,'kidney':18,'mind':40,'heart':50}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "21":
                    if (ansList.contains("138")) {
                        String json = "{'bloodStasis':15,'qiZhi':30,'yin':15,'phlegm':20,'qiXu':18,'wet':10,'yangXu':18,'kidney':18,'lung':18,'heart':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("139")) {
                        String json = "{'bloodStasis':35,'qiZhi':30,'yinXu':12,'phlegm':30,'bloodDeficiency':12,'qiXu':12,'yangXu':24,'liver':18,'spleen':12,'kidney':12,'lung':18,'heart':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("136")) {
                        String json = "{'thermal':12,'phlegm':30,'lung':40}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("137")) {
                        String json = "{'qiZhi':18,'yinXu':12,'phlegm':30,'qiXu':18,'jingKui':30,'yangXu':24,'liver':16,'spleen':18,'kidney':24,'lung':40,'heart':30}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("140")) {
                        String json = "{'bloodStasis':24,'thermal':18,'yinXu':18,'bloodDeficiency':15,'wet':18,'liver':30,'gallbladder':30}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "22":
                    if (ansList.contains("147")) {
                        String json = "{'qiZhi':18,'thermal':12,'bloodDeficiency':18,'wet':18,'yangXu':12,'spleen':18,'largeIntestine':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("144")) {
                        String json = "{'yinXu':18,'qiXu':18,'yangXu':18,'stomach':50,'spleen':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("145")) {
                        String json = "{'bloodStasis':18,'qiZhi':18,'thermal':12,'wet':18,'stomach':18,'liver':24,'spleen':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("143")) {
                        String json = "{'qiZhi':30,'qiXu':12,'yangXu':15,'stomach':50,'liver':30,'spleen':25}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("146")) {
                        String json = "{'bloodStasis':18,'qiZhi':18,'wet':12,'yangXu':18,'liver':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("142")) {
                        String json = "{'yinXu':30,'qiXu':30,'wet':30,'jingKui':30,'yangXu':30,'kidney':30}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "23":
                    if (ansList.contains("153")) {
                        String json = "{'bloodStasis':18,'thermal':18,'wet':18,'jingKui':12,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("152")) {
                        String json = "{'yin':18,'yangXu':18,'spleen':24,'kidney':20,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("149")) {
                        String json = "{'qiZhi':18,'yangXu':18,'liver':20,'kidney':20}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("150")) {
                        String json = "{'phlegm':18,'bloodDeficiency':18,'qiXu':18,'wet':18,'spleen':24,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("151")) {
                        String json = "{'bloodStasis':18,'qiZhi':18,'thermal':10,'phlegm':18,'bloodDeficiency':18,'qiXu':18,'wet':18,'liver':18,'spleen':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("154")) {
                        String json = "{'bloodStasis':24,'yinXu':18,'yangKang':10,'phlegm':20,'wind':10,'qiXu':18,'jingKui':18,'liver':10,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "24":
                    if (ansList.contains("157")) {
                        String json = "{'bloodStasis':12,'thermal':12,'yinXu':40,'wet':12,'kidney':18,'heart':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("158")) {
                        String json = "{'qiXu':40,'yangXu':30,'lung':24}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("156")) {
                        String json = "{'thermal':18,'yinXu':18,'wet':18,'stomach':10,'liver':6,'spleen':10,'kidney':10,'largeIntestine':10,'heart':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("160")) {
                        String json = "{'bloodStasis':18,'phlegm':30,'wind':12,'wet':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("159")) {
                        String json = "{'qiZhi':30,'thermal':18,'yinXu':20,'liver':25,'kidney':12}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "25":
                    if (ansList.contains("163")) {
                        String json = "{'yangKang':18,'wind':18,'liver':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("167")) {
                        String json = "{'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("166")) {
                        String json = "{'qiXu':18,'mind':15,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("164")) {
                        String json = "{'qiZhi':12,'qiXu':18,'spleen':15,'lung':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("162")) {
                        String json = "{'phlegm':18,'qiXu':18,'mind':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("168")) {
                        String json = "{'mind':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("165")) {
                        String json = "{'qiZhi':18,'spleen':15,'mind':15}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "26":
                    if (ansList.contains("172")) {
                        String json = "{'qiZhi':30,'qiXu':18,'yangXu':18,'liver':15,'spleen':15,'kidney':15,'heart':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("170")) {
                        String json = "{'thermal':32,'yinXu':18,'phlegm':18,'kidney':12,'mind':18,'heart':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("173")) {
                        String json = "{'qiZhi':18,'thermal':18,'yinXu':18,'phlegm':18,'qiXu':18,'spleen':12,'gallbladder':15,'mind':18,'heart':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("171")) {
                        String json = "{'bloodStasis':18,'thermal':24,'yinXu':15,'phlegm':15,'mind':18}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "27":
                    if (ansList.contains("175")) {
                        String json = "{'phlegm':15,'wet':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("176")) {
                        String json = "{'thermal':-15,'yinXu':-10,'yangXu':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("177")) {
                        String json = "{'wet':-15,'jinKui':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("178")) {
                        String json = "{'thermal':18,'yinXu':15,'yangXu':-10}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "28":
                    if (ansList.contains("180")) {
                        String json = "{'yinXu':10,'liver':6,'spleen':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("181")) {
                        String json = "{'thermal':12,'yangKang':10,'lung':10,'heart':6}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("182")) {
                        String json = "{'jinKui':10,'liver':10,'lung':6}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("183")) {
                        String json = "{'yangXu':10,'kidney':6,'heart':10}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "29":
                    if (ansList.contains("186")) {
                        String json = "{'thermal':18,'qiXu':18,'wet':18,'jingKui':18,'yangXu':18,'liver':25,'kidney':34}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("185")) {
                        String json = "{'thermal':18,'yinXu':20,'qiXu':-20,'yangXu':-20,'kidney':20}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "30":
                    if (ansList.contains("190")) {
                        String json = "{'qiZhi':18,'qiXu':18,'jingKui':18,'liver':15,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("191")) {
                        String json = "{'qiZhi':18,'bloodDeficiency':18,'jingKui':18,'yangXu':18,'kidney':18}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("192")) {
                        String json = "{'qiZhi':12,'thermal':18,'bloodHeat':18,'yinXu':18,'yangKang':12,'qiXu':24,'liver':15,'spleen':15,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("193")) {
                        String json = "{'bloodStasis':23,'thermal':18,'bloodHeat':18,'yinXu':8,'qiXu':18,'liver':10,'spleen':10,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("194")) {
                        String json = "{'bloodStasis':18,'phlegm':18,'qiXu':18,'jingKui':18,'liver':15,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("195")) {
                        String json = "{'bloodStasis':40,'qiZhi':18,'thermal':15,'qiXu':18,'wet':18,'yangXu':14,'liver':12,'spleen':10,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("196")) {
                        String json = "{'bloodStasis':40,'yangXu':18,'liver':13}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("197")) {
                        String json = "{'bloodDeficiency':40,'qiXu':18,'liver':15,'spleen':15,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("188")) {
                        String json = "{'bloodStasis':35,'yinXu':18,'phlegm':18,'bloodDeficiency':24,'qiXu':18,'wet':18,'liver':15,'kidney':15}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("189")) {
                        String json = "{'jingKui':12,'kidney':10}";
                        addHealthElement(vo, json);
                    }
                    break;
                case "32":
                    if (ansList.contains("203")) {
                        String json = "{'phlegm':6}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("204")) {
                        String json = "{'phlegm':12}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("205")) {
                        String json = "{'thermal':6,'wet':5}";
                        addHealthElement(vo, json);
                    }
                    if (ansList.contains("206")) {
                        String json = "{'thermal':12,'wet':12}";
                        addHealthElement(vo, json);
                    }
                    break;
            }
        }
    }

    public static void calculateFinishDt(TcmHealthElementVO elementVO, String finishDt) {
        Date date = DateHelper.getDate(finishDt, DateHelper.DATETIME_FORMAT);
        String year = DateHelper.getDate(date, "yyyy");
        if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-02-03 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-02-05 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':10,'yinXu':5,'gallbladder':5,'lung':5}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-02-18 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-02-20 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':6,'qiZhi':15,'yinXu':5,'phlegm':10,'qiXu':10,'stomach':5,'liver':10,'spleen':5,'lung':5,'heart':5}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-03-05 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-03-07 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':6,'qiZhi':15,'thermal':5,'yinXu':5,'phlegm':10,'qiXu':10,'stomach':5,'liver':10,'spleen':5,'mind':8,'lung':5,'heart':7}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-03-20 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-03-22 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':5,'qiZhi':15,'yinXu':8,'bloodDeficiency':7,'yangXu':10,'stomach':8,'liver':12,'spleen':12,'gallbladder':8,'largeIntestine':6}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-04-04 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-04-06 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':5,'qiZhi':15,'yinXu':10,'bloodDeficiency':7,'qiXu':15,'yangXu':8,'stomach':10,'liver':10,'spleen':15,'gallbladder':8,'largeIntestine':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-04-19 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-04-21 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':15,'thermal':6,'yinXu':10,'qiXu':5,'stomach':6,'liver':10,'smallIntestine':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-05-05 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-05-07 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':15,'thermal':8,'yinXu':12,'qiXu':5,'liver':10,'smallIntestine':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-05-20 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-05-22 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':10,'yinXu':10,'qiXu':10,'jinKui':10,'kidney':10,'largeIntestine':10,'heart':5}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-06-05 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-06-07 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':12,'yinXu':12,'qiXu':10,'jinKui':10,'kidney':10,'largeIntestine':10,'heart':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-06-21 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-06-22 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':15,'phlegm':8,'qiXu':5,'wet':5,'stomach':10,'liver':6,'spleen':10,'gallbladder':6}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-07-06 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-07-08 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':15,'thermal':15,'phlegm':8,'qiXu':5,'wet':5,'stomach':10,'liver':6,'spleen':10,'gallbladder':6,'mind':15,'heart':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-07-22 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-07-24 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':12,'thermal':18,'phlegm':12,'wet':10,'stomach':10,'liver':10,'smallIntestine':10,'gallbladder':10,'largeIntestine':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-08-07 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-08-09 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':15,'thermal':15,'phlegm':15,'wet':10,'stomach':8,'liver':15,'smallIntestine':6,'gallbladder':12,'largeIntestine':12}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-08-22 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-08-24 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':6,'qiZhi':15,'thermal':10,'phlegm':15,'qiXu':12,'wet':12,'jingKui':10,'jinKui':10,'liver':15,'spleen':12,'smallIntestine':8,'kidney':10,'largeIntestine':12,'bladder':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-09-07 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-09-09 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':8,'qiZhi':15,'thermal':6,'yinXu':10,'phlegm':12,'qiXu':12,'wet':10,'jingKui':10,'jinKui':12,'liver':12,'spleen':10,'smallIntestine':8,'kidney':10,'largeIntestine':10,'bladder':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-09-22 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-09-24 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':6,'yinXu':8,'bloodDeficiency':5,'qiXu':10,'yangXu':10,'jinKui':10,'liver':10,'smallIntestine':8,'gallbladder':10,'largeIntestine':10,'mind':12,'lung':15,'heart':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-10-08 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-10-09 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':4,'yinXu':10,'bloodDeficiency':5,'qiXu':12,'yangXu':10,'jinKui':10,'liver':10,'smallIntestine':8,'gallbladder':10,'largeIntestine':10,'mind':10,'lung':12,'heart':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-10-23 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-10-24 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':8,'yangXu':8,'jinKui':10,'smallIntestine':8,'largeIntestine':8,'lung':12}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-11-07 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-11-08 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':8,'qiXu':10,'yangXu':8,'smallIntestine':6,'largeIntestine':6,'mind':10,'lung':8,'heart':12}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-11-22 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-11-23 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'yin':6,'yinXu':8,'phlegm':8,'bloodDeficiency':6,'qiXu':8,'liver':10,'spleen':8,'gallbladder':10,'kidney':8,'bladder':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-12-06 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-12-08 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'yin':8,'yinXu':6,'phlegm':8,'bloodDeficiency':6,'qiXu':8,'yangXu':10,'liver':8,'spleen':10,'gallbladder':8,'kidney':8,'bladder':10}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-12-21 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-12-23 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'qiZhi':8,'yin':6,'yinXu':8,'phlegm':6,'bloodDeficiency':6,'qiXu':8,'yangXu':10,'stomach':8,'liver':10,'spleen':8,'smallIntestine':6,'largeIntestine':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-01-05 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-01-07 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'bloodStasis':8,'qiZhi':8,'yin':8,'yinXu':10,'phlegm':8,'bloodDeficiency':6,'qiXu':8,'yangXu':10,'stomach':8,'liver':8,'spleen':10,'smallIntestine':6,'largeIntestine':8}";
            addHealthElement(elementVO, json);
        } else if (Boolean.TRUE.equals(DateHelper.dateAfter(finishDt, DateHelper.DATETIME_FORMAT, year + "-01-20 00:00:00", DateHelper.DATETIME_FORMAT))
                && Boolean.TRUE.equals(DateHelper.dateAfter(year + "-01-21 23:59:59", DateHelper.DATETIME_FORMAT, finishDt, DateHelper.DATETIME_FORMAT))) {
            String json = "{'thermal':3,'yinXu':8,'qiXu':8,'jingKui':6,'yangXu':6,'gallbladder':5,'kidney':8,'lung':5}";
            addHealthElement(elementVO, json);
        }
    }

    public static void calculateBmi(TcmHealthElementVO elementVO, double bmi) {
        if (bmi >= 24 && bmi < 28) {
            String json = "{'phlegm':5,'wet':5}";
            addHealthElement(elementVO, json);
        } else if (bmi >= 28 && bmi < 35) {
            String json = "{'phlegm':15,'wet':15,'mind':10,'heart':10}";
            addHealthElement(elementVO, json);
        } else if (bmi >= 35) {
            String json = "{'phlegm':20,'wet':20,'mind':15,'heart':10}";
            addHealthElement(elementVO, json);
        }
    }

    public static void addHealthElement(TcmHealthElementVO a, String json) {
        TcmHealthElementVO b = JSON.parseObject(json, TcmHealthElementVO.class);
        a.setBloodStasis(a.getBloodStasis() + b.getBloodStasis());
        a.setQiZhi(a.getQiZhi() + b.getQiZhi());
        a.setThermal(a.getThermal() + b.getThermal());
        a.setBloodHeat(a.getBloodHeat() + b.getBloodHeat());
        a.setYin(a.getYin() + b.getYin());
        a.setYinXu(a.getYinXu() + b.getYinXu());
        a.setYangKang(a.getYangKang() + b.getYangKang());
        a.setPhlegm(a.getPhlegm() + b.getPhlegm());
        a.setWind(a.getWind() + b.getWind());
        a.setBloodDeficiency(a.getBloodDeficiency() + b.getBloodDeficiency());
        a.setQiXu(a.getQiXu() + b.getQiXu());
        a.setWet(a.getWet() + b.getWet());
        a.setJingKui(a.getJingKui() + b.getJingKui());
        a.setYangXu(a.getYangXu() + b.getYangXu());
        a.setJinKui(a.getJinKui() + b.getJinKui());
        a.setStomach(a.getStomach() + b.getStomach());
        a.setLiver(a.getLiver() + b.getLiver());
        a.setSpleen(a.getSpleen() + b.getSpleen());
        a.setSmallIntestine(a.getSmallIntestine() + b.getSmallIntestine());
        a.setGallbladder(a.getGallbladder() + b.getGallbladder());
        a.setKidney(a.getKidney() + b.getKidney());
        a.setLargeIntestine(a.getLargeIntestine() + b.getLargeIntestine());
        a.setMind(a.getMind() + b.getMind());
        a.setBladder(a.getBladder() + b.getBladder());
        a.setLung(a.getLung() + b.getLung());
        a.setHeart(a.getHeart() + b.getHeart());
    }


    public static String getFigureDescription(Double bmi) {
        if (bmi >= 35) {
            return "极重度肥胖";
        } else if (bmi >= 28) {
            return "过度肥胖";
        } else if (bmi >= 24) {
            return "偏胖";
        } else if (bmi >= 18.5) {
            return "正常";
        } else {
            return "偏瘦";
        }
    }

    public static String calculateRisk(Integer age, double bmi, String sex, String isDiabetes, HashMap<String, TcmCollectQuePO> queMap, HashMap<String, String> ans) {
        double cvdRisk = 0;
        double diabetesRisk = 0;
        if (age >= 40) {
            cvdRisk += (age - 35) / 5;
        }
        if (bmi >= 24 && bmi < 28) {
            cvdRisk += 1;
        }
        if (bmi >= 28) {
            cvdRisk += 2;
        }
        if (age < 25) {
            diabetesRisk += 0;
        } else if (age < 35) {
            diabetesRisk += 4;
        } else if (age < 40) {
            diabetesRisk += 8;
        } else if (age < 45) {
            diabetesRisk += 11;
        } else if (age < 50) {
            diabetesRisk += 12;
        } else if (age < 55) {
            diabetesRisk += 13;
        } else if (age < 60) {
            diabetesRisk += 15;
        } else if (age < 65) {
            diabetesRisk += 16;
        } else {
            diabetesRisk += 18;
        }
        if (bmi < 22) {
            diabetesRisk += 0;
        } else if (bmi < 24) {
            diabetesRisk += 1;
        } else if (bmi < 30) {
            diabetesRisk += 3;
        } else {
            diabetesRisk += 5;
        }
        for (TcmCollectQuePO q : queMap.values()) {
            List<String> ansList = null;
            String ansStr = ans.get(q.getSid());
            if (!StringUtils.isBlank(ansStr)) {
                ansList = JSON.parseArray(ansStr, String.class);
            }
            if(ansList == null){
                continue;
            }
            if (q.getQueIndex() == 32) {
                if (ans.containsKey(q.getSid()) && !StringUtils.isBlank(ans.get(q.getSid()))) {
                    boolean isSmoke = ansList.contains("203") || ansList.contains("204");

                    if (isSmoke) {
                        if ("1".equals(sex)) {
                            cvdRisk += 1;
                        } else if ("2".equals(sex)) {
                            cvdRisk += 2;
                        }
                    }
                }
            }
            if (q.getSid().equals("34")) {
                if (ansList.contains("213") ) {
                    cvdRisk += -2;
                    diabetesRisk += 0;
                }
                if (ansList.contains("214") ) {
                    cvdRisk += -2;
                    diabetesRisk += 1;
                }
                if (ansList.contains("215") ) {
                    diabetesRisk += 3;
                }
                if (ansList.contains("216") ) {
                    cvdRisk += 1;
                    diabetesRisk += 6;
                }
                if (ansList.contains("217") ) {
                    cvdRisk += 2;
                    diabetesRisk += 7;
                }
                if (ansList.contains("218") ) {
                    cvdRisk += 2;
                    diabetesRisk += 8;
                }
                if (ansList.contains("219") ) {
                    if ("1".equals(sex)) {
                        cvdRisk += 5;
                    } else if ("2".equals(sex)) {
                        cvdRisk += 3;
                    }
                    diabetesRisk += 10;
                }
                if (ansList.contains("220") ) {
                    if ("1".equals(sex)) {
                        cvdRisk += 8;
                    } else if ("2".equals(sex)) {
                        cvdRisk += 4;
                    }
                    diabetesRisk += 10;
                }
            }
            if (q.getSid().equals("35")) {
                if (ansList.contains("223") ) {
                    diabetesRisk += 3;
                }
                if (ansList.contains("224") ) {
                    diabetesRisk += 5;
                }
                if (ansList.contains("225") ) {
                    diabetesRisk += 7;
                }
                if (ansList.contains("226") ) {
                    diabetesRisk += 8;
                }
                if (ansList.contains("227") ) {
                    diabetesRisk += 10;
                }
            }
            if (q.getSid().equals("36")) {
                if (ansList.contains("230") ) {
                    diabetesRisk += 3;
                }
                if (ansList.contains("231") ) {
                    diabetesRisk += 5;
                }
                if (ansList.contains("232") ) {
                    diabetesRisk += 7;
                }
                if (ansList.contains("233") ) {
                    diabetesRisk += 8;
                }
                if (ansList.contains("234") ) {
                    diabetesRisk += 10;
                }
            }
            if (q.getSid().equals("37")) {
                if (ansList.contains("237") ) {
                    cvdRisk += 1;
                }
            }
            if (q.getSid().equals("37")) {
                if (ansList.contains("240") ) {
                    diabetesRisk += 6;
                }
            }
        }
        if ("1".equals(sex)) {
            if ("1".equals(isDiabetes)) {
                cvdRisk += 1;
            }
        } else if ("2".equals(sex)) {
            if ("1".equals(isDiabetes)) {
                cvdRisk += 2;
            }
        }

        if (cvdRisk < 10) {
            if (diabetesRisk < 25) {
                return "您发生糖尿病和心血管疾病的风险较低，请继续保持良好的生活习惯。";
            } else {
                return "您发生糖尿病的风险较高，发生心血管疾病的风险较低，建议您保持良好的生活习惯的同时，向专科医生咨询疾病预防，并做好针对性的筛查，配合医疗卫生单位组织的慢性病干预活动。";
            }
        } else {
            if (diabetesRisk < 25) {
                return "您发生糖尿病的风险较低，发生心血管疾病的风险较高，建议您保持良好的生活习惯的同时，向专科医生咨询疾病预防，并做好针对性的筛查，配合医疗卫生单位组织的慢性病干预活动。";
            } else {
                return "您发生糖尿病和心血管疾病的风险较高，为降低疾病发生风险，建议您及时向专科医生咨询疾病预防，并做好针对性的筛查，提倡健康的生活方式，配合医疗卫生单位组织的慢性病干预活动。";
            }
        }
    }


    public static String getTcmFeatureDesc(int feature) {
        String featureDesc = null;
        switch (feature) {
            case TcmConstant.TCM_FEATURE_METAL:
                featureDesc = "综合您的脸型和其他信息，您的体质按五行分类为金行人。注：由于燥属金，金行体质人多燥气，燥气通于肺，故金行人易患肺方面的疾病；由于燥热易伤阴津，所以金行人易患阴亏燥热之病如咳嗽、慢性支气管炎、糖尿病、便秘等病。";
                break;
            case TcmConstant.TCM_FEATURE_WOOD:
                featureDesc = "综合您的脸型和其他信息，您的体质按五行分类为木行人。注：由于风属木，木行体质人多风气，风气通于肝，故木行人易患肝胆方面的疾病；风性善动、善变，故木行体质人，又易肝风内动，患高血压、中风、神经系统失调的疾患及过敏性疾患。";
                break;
            case TcmConstant.TCM_FEATURE_WATER:
                featureDesc = "综合您的脸型和其他信息，您的体质按五行分类为水行人。注：由于寒属水,水行体质人多寒气，寒气通于肾，故水行人易患肾方面的疾病；寒性凝滞，寒性收引，故水行体质的人易气血不通而患经络痹阻的关节骨痛等症。";
                break;
            case TcmConstant.TCM_FEATURE_FIRE:
                featureDesc = "综合您的脸型和其他信息，您的体质按五行分类为火行人。注：由于热属火，火行体质人多热气，热气通于心，心主血脉，故火行人易患心方面疾病；火能动风、伤血，故火行人有中风、脑出血等潜在倾向。";
                break;
            case TcmConstant.TCM_FEATURE_EARTH:
                featureDesc = "综合您的脸型和其他信息，您的体质按五行分类为土行人。注：由于湿属土，土行体质人多湿气，湿气通于脾，故土行人易患脾胃方面疾病；由于湿性黏滞，易致气血运行缓慢而积湿生痰，所以多有痰饮、积聚、水肿等病。";
                break;
        }
        return featureDesc;
    }


    //1：和平质:2：阴虚质、3：气郁质、4:湿热质、5:气虚质、6:阳虚质:7:痰湿质、8:血瘀质:9:特禀质
    public static String getTcmConstitutionAssess(int constitution) {
        String assess = "";
        switch (constitution) {
            case TcmConstant.TCM_CONSTITUTION_1:
                assess = "您的体质按九种分类法为平和质。平和质先天禀赋良好，后天调养得当，脏腑功能状态强健壮实。";
                break;
            case TcmConstant.TCM_CONSTITUTION_2:
                assess = "您的体质按九种分类法为阴虚质。阴虚质表现为由于体内精、血、津、液等水份亏少，而出现阴虚内热和干燥等证。";
                break;
            case TcmConstant.TCM_CONSTITUTION_3:
                assess = "您的体质按九种分类法为气郁质。气郁质主要由于情志不调，或痰、湿积聚,致气机不通，脏腑或经络功能障碍。";
                break;
            case TcmConstant.TCM_CONSTITUTION_4:
                assess = "您的体质按九种分类法为湿热质。湿热质表现为湿热内蕴，常见面垢油光、容易长痘，口苦、口粘等症。";
                break;
            case TcmConstant.TCM_CONSTITUTION_5:
                assess = "您的体质按九种分类法为气虚质。气虚质最主要是反映在脏腑功能的低下。";
                break;
            case TcmConstant.TCM_CONSTITUTION_6:
                assess = "您的体质按九种分类法为阳虚质。阳虚质表现为脏腑功能活力不足，温煦功能减退，出现的恶寒喜暖证状，当受到病邪侵袭后多也化为寒证。";
                break;
            case TcmConstant.TCM_CONSTITUTION_7:
                assess = "您的体质按九种分类法为痰湿质。痰湿质表现为由于水液内停而痰湿凝聚，以形体肥胖、粘滞重浊为主要特征。";
                break;
            case TcmConstant.TCM_CONSTITUTION_8:
                assess = "您的体质按九种分类法为血瘀质。血瘀质表现为血液运行不畅或血液瘀滞不通的病理状态。";
                break;
            case TcmConstant.TCM_CONSTITUTION_9:
                assess = " 您的体质按九种分类法为特禀质。特禀质是由于先天禀赋不足和禀赋遗传等因素造成的一种特殊体质。包括先天性、遗传性的生理缺陷与疾病，过敏反应等。";
                break;
        }
        return assess;
    }

    public static String getJiaCalendar(int year) {
        String jia;
        year = year - 3;
        jia = TcmConstant.HEAVENLY[year % 10] + TcmConstant.EARTHLY[year % 12];
        return jia;
    }

    public static int getZhuKeIndex(String date) {
        Date d = DateHelper.getDate(date, DateHelper.DAY_FORMAT);
        date += " 00:00:01";
        if (d != null) {
            String y = DateHelper.getDate(d, "yyyy");
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-01-21", "yyyy-MM-dd"))
                    && Boolean.TRUE.equals(DateHelper.dateAfter(y + "-03-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 0;
            }
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-03-21", "yyyy-MM-dd"))
                    && Boolean.TRUE.equals(DateHelper.dateAfter(y + "-05-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 1;
            }
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-05-21", "yyyy-MM-dd"))
                    && Boolean.TRUE.equals(DateHelper.dateAfter(y + "-07-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 2;
            }
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-07-21", "yyyy-MM-dd"))
                    && Boolean.TRUE.equals(DateHelper.dateAfter(y + "-09-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 3;
            }
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-09-21", "yyyy-MM-dd"))
                    && Boolean.TRUE.equals(DateHelper.dateAfter(y + "-11-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 4;
            }
            if (Boolean.TRUE.equals(DateHelper.dateAfter(date,  DateHelper.DATETIME_FORMAT, y + "-11-21", "yyyy-MM-dd"))
                    || Boolean.TRUE.equals(DateHelper.dateAfter(y + "-01-20", "yyyy-MM-dd", date,  DateHelper.DATETIME_FORMAT))) {
                return 5;
            }
        }

        return -1;
    }

    public static int getJIAIndex(String jia) {
        for (int i = 0; i < TcmConstant.JIA.length; i++) {
            if (jia != null && jia.equals(TcmConstant.JIA[i])) {
                return i;
            }
        }
        return -1;
    }

    public static JSONObject getWuYunLiuQi(String date, boolean isBirth) {
        Date d = DateHelper.getDate(date, DateHelper.DAY_FORMAT);
        Calendar c = Calendar.getInstance();
        JSONObject obj = new JSONObject();
        if (d != null) {
            c.setTime(d);
            String jia = getJiaCalendar(c.get(Calendar.YEAR));
            int indexJia = getJIAIndex(jia);
            obj.put("jia", jia);
            obj.put("date", DateHelper.getDate(d, "yyyy年MM月dd日"));
            if (indexJia != -1) {
                obj.put("nian_yun", TcmConstant.NIAN_YUN[indexJia]);
                obj.put("si_tian", TcmConstant.SI_TIAN[indexJia]);
                obj.put("zai_quan", TcmConstant.ZAI_QUAN[indexJia]);
                if (isBirth) {
                    obj.put("desc", TcmConstant.BIRTH_TEXT[indexJia]);
                } else {
                    obj.put("desc", getCurrentYearDiseaseDesc(jia));
                }
            } else {
                obj.put("nian_yun", "");
                obj.put("si_tian", "");
                obj.put("zai_quan", "");
                obj.put("desc", "");
            }
            int indexZhuKe = getZhuKeIndex(date);
            if (indexZhuKe != -1) {
                obj.put("zhu_qi", TcmConstant.ZHU_QI[indexZhuKe]);
                if (indexJia != -1) {
                    obj.put("ke_qi", TcmConstant.KE_QI[indexZhuKe][indexJia]);
                } else {
                    obj.put("ke_qi", "");
                }
            } else {
                obj.put("zhu_qi", "");
                obj.put("ke_qi", "");
            }
        }
        return obj;
    }

    public static final String getCurrentYearDiseaseDesc(String jia) {
        if (!StringUtils.isBlank(jia)) {
            if (jia.startsWith("甲")) {
                return "土运太过，湿气流行，胃水受克，要注意生殖、肝胆、泌尿系统疾病。";
            }
            if (jia.startsWith("己")) {
                return "土运不足，风气流行，脾土受克，要注意肝胆、脾胃、神经方面疾病。";
            }
            if (jia.startsWith("庚")) {
                return "金运太过，燥气流行，肝木受克，要注意肝胆、心脏循环系统疾病。";
            }
            if (jia.startsWith("已")) {
                return "金运不足，火气流行，肺金受克，要注意心肺、呼吸系统疾病。";
            }
            if (jia.startsWith("丙")) {
                return "水运太过，寒气流行，心火受克，要注意血液循环、脾胃方面疾病。";
            }
            if (jia.startsWith("辛")) {
                return "水运不足，湿气流行，胃水受克，要注意肾和脾功能疾病。";
            }
            if (jia.startsWith("壬")) {
                return "木运太过，风气流行，脾土受克，要注意肠胃、呼吸系统疾病。";
            }
            if (jia.startsWith("丁")) {
                return "木运不足，燥气流行，肝木受克，要注意肝胆、呼吸及神经系统疾病。";
            }
            if (jia.startsWith("戊")) {
                return "火运太过，暑气流行，肺金受克，要注意呼吸泌尿、生殖方面疾病。";
            }
            if (jia.startsWith("癸")) {
                return "火运不足，寒气流行，心火受克，要注意心脏及泌尿方面疾病。";
            }
        }
        return null;
    }

    public static List<String> listDiseases(TcmHealthElementVO element) {
        TcmHealthElementVO diseasesElement = JSON.parseObject(TcmConstant.TCM_ELEMENT_DISEASE, TcmHealthElementVO.class);
        List<String> diseases = new ArrayList<>();
        if (element.getBloodStasis() >= diseasesElement.getBloodStasis()) {
            diseases.add("bloodStasis");
        }
        if (element.getQiZhi() >= diseasesElement.getQiZhi()) {
            diseases.add("qiZhi");
        }
        if (element.getThermal() >= diseasesElement.getThermal()) {
            diseases.add("thermal");
        }
        if (element.getBloodHeat() >= diseasesElement.getBloodHeat()) {
            diseases.add("bloodHeat");
        }
        if (element.getYin() >= diseasesElement.getYin()) {
            diseases.add("yin");
        }
        if (element.getYinXu() >= diseasesElement.getYinXu()) {
            diseases.add("yinXu");
        }
        if (element.getYangKang() >= diseasesElement.getYangKang()) {
            diseases.add("yangKang");
        }
        if (element.getPhlegm() >= diseasesElement.getPhlegm()) {
            diseases.add("phlegm");
        }
        if (element.getWind() >= diseasesElement.getWind()) {
            diseases.add("wind");
        }
        if (element.getBloodDeficiency() >= diseasesElement.getBloodDeficiency()) {
            diseases.add("bloodDeficiency");
        }
        if (element.getQiXu() >= diseasesElement.getQiXu()) {
            diseases.add("qiXu");
        }
        if (element.getWet() >= diseasesElement.getWet()) {
            diseases.add("wet");
        }
        if (element.getJingKui() >= diseasesElement.getJingKui()) {
            diseases.add("jingKui");
        }
        if (element.getYangXu() >= diseasesElement.getYangXu()) {
            diseases.add("yangXu");
        }
        if (element.getJinKui() >= diseasesElement.getJinKui()) {
            diseases.add("jinKui");
        }
        if (element.getStomach() >= diseasesElement.getStomach()) {
            diseases.add("stomach");
        }
        if (element.getLiver() >= diseasesElement.getLiver()) {
            diseases.add("liver");
        }
        if (element.getSpleen() >= diseasesElement.getSpleen()) {
            diseases.add("spleen");
        }
        if (element.getSmallIntestine() >= diseasesElement.getSmallIntestine()) {
            diseases.add("smallIntestine");
        }
        if (element.getGallbladder() >= diseasesElement.getGallbladder()) {
            diseases.add("gallbladder");
        }
        if (element.getKidney() >= diseasesElement.getKidney()) {
            diseases.add("kidney");
        }
        if (element.getLargeIntestine() >= diseasesElement.getLargeIntestine()) {
            diseases.add("largeIntestine");
        }
        if (element.getMind() >= diseasesElement.getMind()) {
            diseases.add("mind");
        }
        if (element.getBladder() >= diseasesElement.getBladder()) {
            diseases.add("bladder");
        }
        if (element.getLung() >= diseasesElement.getLung()) {
            diseases.add("lung");
        }
        if (element.getHeart() >= diseasesElement.getHeart()) {
            diseases.add("heart");
        }
        return diseases;
    }


    public static List<String> getComingDisease(TcmHealthElementVO element) {
        TcmHealthElementVO diseasesElement = JSON.parseObject(TcmConstant.TCM_ELEMENT_DISEASE, TcmHealthElementVO.class);
        TcmHealthElementVO proDiseasesElement = JSON.parseObject(TcmConstant.TCM_ELEMENT_PRO_DISEASE, TcmHealthElementVO.class);
        List<String> diseases = new ArrayList<>();
        if (element.getBloodStasis() > proDiseasesElement.getBloodStasis() && element.getBloodStasis() < diseasesElement.getBloodStasis()) {
            diseases.add("bloodStasis");
        }
        if (element.getQiZhi() > proDiseasesElement.getQiZhi() && element.getQiZhi() < diseasesElement.getQiZhi()) {
            diseases.add("qiZhi");
        }
        if (element.getThermal() > proDiseasesElement.getThermal() && element.getThermal() < diseasesElement.getThermal()) {
            diseases.add("thermal");
        }
        if (element.getBloodHeat() > proDiseasesElement.getBloodHeat() && element.getBloodHeat() < diseasesElement.getBloodHeat()) {
            diseases.add("bloodHeat");
        }
        if (element.getYin() > proDiseasesElement.getYin() && element.getYin() < diseasesElement.getYin()) {
            diseases.add("yin");
        }
        if (element.getYinXu() > proDiseasesElement.getYinXu() && element.getYinXu() < diseasesElement.getYinXu()) {
            diseases.add("yinXu");
        }
        if (element.getYangKang() > proDiseasesElement.getYangKang() && element.getYangKang() < diseasesElement.getYangKang()) {
            diseases.add("yangKang");
        }
        if (element.getPhlegm() > proDiseasesElement.getPhlegm() && element.getPhlegm() < diseasesElement.getPhlegm()) {
            diseases.add("phlegm");
        }
        if (element.getWind() > proDiseasesElement.getWind() && element.getWind() < diseasesElement.getWind()) {
            diseases.add("wind");
        }
        if (element.getBloodDeficiency() > proDiseasesElement.getBloodDeficiency() && element.getBloodDeficiency() < diseasesElement.getBloodDeficiency()) {
            diseases.add("bloodDeficiency");
        }
        if (element.getQiXu() > proDiseasesElement.getQiXu() && element.getQiXu() < diseasesElement.getQiXu()) {
            diseases.add("qiXu");
        }
        if (element.getWet() > proDiseasesElement.getWet() && element.getWet() < diseasesElement.getWet()) {
            diseases.add("wet");
        }
        if (element.getJingKui() > proDiseasesElement.getJingKui() && element.getJingKui() < diseasesElement.getJingKui()) {
            diseases.add("jingKui");
        }
        if (element.getYangXu() > proDiseasesElement.getYangXu() && element.getYangXu() < diseasesElement.getYangXu()) {
            diseases.add("yangXu");
        }
        if (element.getJinKui() > proDiseasesElement.getJinKui() && element.getJinKui() < diseasesElement.getJinKui()) {
            diseases.add("jinKui");
        }
        if (element.getStomach() > proDiseasesElement.getStomach() && element.getStomach() < diseasesElement.getStomach()) {
            diseases.add("stomach");
        }
        if (element.getLiver() > proDiseasesElement.getLiver() && element.getLiver() < diseasesElement.getLiver()) {
            diseases.add("liver");
        }
        if (element.getSpleen() > proDiseasesElement.getSpleen() && element.getSpleen() < diseasesElement.getSpleen()) {
            diseases.add("spleen");
        }
        if (element.getSmallIntestine() > proDiseasesElement.getSmallIntestine() && element.getSmallIntestine() < diseasesElement.getSmallIntestine()) {
            diseases.add("smallIntestine");
        }
        if (element.getGallbladder() > proDiseasesElement.getGallbladder() && element.getGallbladder() < diseasesElement.getGallbladder()) {
            diseases.add("gallbladder");
        }
        if (element.getKidney() > proDiseasesElement.getKidney() && element.getKidney() < diseasesElement.getKidney()) {
            diseases.add("kidney");
        }
        if (element.getLargeIntestine() > proDiseasesElement.getLargeIntestine() && element.getLargeIntestine() < diseasesElement.getLargeIntestine()) {
            diseases.add("largeIntestine");
        }
        if (element.getMind() > proDiseasesElement.getMind() && element.getMind() < diseasesElement.getMind()) {
            diseases.add("mind");
        }
        if (element.getBladder() > proDiseasesElement.getBladder() && element.getBladder() < diseasesElement.getBladder()) {
            diseases.add("bladder");
        }
        if (element.getLung() > proDiseasesElement.getLung() && element.getLung() < diseasesElement.getLung()) {
            diseases.add("lung");
        }
        if (element.getHeart() > proDiseasesElement.getHeart() && element.getHeart() < diseasesElement.getHeart()) {
            diseases.add("heart");
        }
        return diseases;
    }

    public static String getTeaNurse(List<String> diseaseList) {
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "乌龙茶/花茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("精亏"))) {
            return "青茶/红茶/黑茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("痰"))) {
            return "乌龙茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "乌龙茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("动风"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "乌龙茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "青茶/绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "乌龙茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("肝"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "青茶/绿茶/白茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "青茶/绿茶/白茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "青茶/绿茶/白茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "青茶/绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "花茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气逆"))) {
            return "花茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气逆"))) {
            return "乌龙茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳亢"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "青茶/红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "青茶/红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶/白茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "绿茶/白茶/黄茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "红茶/黑茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "青茶/红茶/黑茶";
        }
        return null;
    }

    public static String getPorridgeNurse(List<String> diseaseList) {
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "菖蒲米汤\n配方：石菖蒲3g，米适量。\n\n制作：石菖蒲先煎，后药渣，米洗净，倒入药汁，置武火上煮，水沸后，改用文火煮至米开花成汤即可。\n\n服法：闲时饮。\n";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "杏仁粳米粥\n" +
                    "材料：杏子10枚  粳米100g\n\n" +
                    "制作：杏子与粳米仪器入锅，加冰糖适量煮粥。\n\n" +
                    "服用：早晚食用。\n";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿豆粥 \n" +
                    "材料：绿豆30g  粳米100g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：先将绿豆浸泡一小时，粳米淘净，入沙锅同煮至米烂粥成。 \n\n" +
                    "服法：午餐时代饭食。 \n\n" +
                    "功效：清热解毒。 \n\n" +
                    "禁忌：脾胃虚寒不宜服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "赤豆茯苓薏苡粥《中医食疗》\n" +
                    "材料：赤小豆50g   茯苓20g（捣碎研粉）   薏苡仁100g\n\n" +
                    "制作：先将赤小豆用水浸泡半日，与薏苡仁仪一起煮烂后，再加如茯苓粉熬粥。\n\n" +
                    "服用：每周3～5次";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("肝"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "赤豆茯苓薏苡粥《中医食疗》\n" +
                    "材料：赤小豆50g   茯苓20g（捣碎研粉）   薏苡仁100g\n\n" +
                    "制作：先将赤小豆用水浸泡半日，与薏苡仁仪一起煮烂后，再加如茯苓粉熬粥。\n\n" +
                    "服用：每周3～5次";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "金钱瘦肉粥\n" +
                    "配方：金钱草15g  猪瘦肉50g，米适量。\n\n" +
                    "制作：金钱草先煎，取药汤。米洗净，与药汤、猪瘦肉一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即可。 \n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "益母草粥\n" +
                    "材料：益母草60  黄芪20g  粳米60  红糖少许 \n\n" +
                    "制法：水煎益母草、黄芪半小时，去渣取汁，同粳米煮至粥成，加入红糖（血糖高者去之）。 \n\n" +
                    "服法：每日2次，温服。 ";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "雪梨粥\n" +
                    "配方：雪梨一枚  荸荠3枚  陈皮3g，米适量。\n\n" +
                    "制作：米洗净，与雪梨、荸荠、陈皮一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即成。 ";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "麦冬粥\n" +
                    "材料：麦冬6g  粳米适量\n\n" +
                    "制法：麦冬、粳米入沙锅同煮至米烂粥成。 \n\n" +
                    "服法：午餐时代饭食。 ";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "莲子银耳粥\n" +
                    "配方：莲子（不去心）10g   银耳20g  米适量\n\n" +
                    "制作：米洗净，与莲子、银耳一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即成。 \n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "银耳粥\n" +
                    "材料：白木耳50g  冰糖适量（血糖高者去之） 米适量\n\n" +
                    "制法：银耳洗净后加米入锅，煮熟后加入冰糖，稍煮即可。 \n\n" +
                    "服法：每天早上服食一次。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "莲子粥\n" +
                    "配方：莲子（不去心）20g，米适量。\n\n" +
                    "制作：米洗净，与莲子一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即成。 \n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "生脉粥\n" +
                    "配方：红参6g  麦冬15g  五味子10g  粳米50g  冰糖15g\n\n" +
                    "制作：先将红参、麦冬、五味子水煎2次，取汁300m1，去药渣；用药液与粳米同煮粥，沸时放入冰糖，糖化粥熟即成。\n\n" +
                    "用法：每日1剂，每日早、晚分服。若出现休克，可1次1剂，根据病情可日吃2剂以上。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "大麦花生糯米粥《中医食疗》\n" +
                    "材料：大麦、花生、糯米适量\n\n" +
                    "制法：将大麦粒、花生、糯米煮粥食用。\n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "补虚正气粥《圣济总录》\n" +
                    "材料：黄芪15  人参5（或党参15）  粳米50（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：黄芪、人参切片，用冷水浸泡半小时，入沙锅煮沸，渣再煮一遍，取汁同粳米加水煮粥。 \n\n" +
                    "服法：每天早上空腹服食。 \n\n" +
                    "功效：健脾益气。适用于：劳倦内伤、五脏虚衰、年老体弱、久病羸瘦。\n\n" +
                    "症见：体虚自汗、慢性泄泻、食欲不振、心慌气短、气虚浮肿等。 \n\n" +
                    "禁忌：不宜与萝卜、茶叶同服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("寒"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "桂圆红枣粥\n" +
                    "材料：桂圆9g  红枣6枚 米适量\n\n" +
                    "制法：桂圆（去壳） 、红枣与米一同入锅，煮熟即成。\n\n" +
                    "服法：代饭食。 ";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "粳米粥《中医食疗》\n" +
                    "材料：生石膏100g、竹叶、麦冬各20g、粳米100g\n\n" +
                    "制作：生石膏洗净敲碎，注入清水1400ml，烧开后，加竹叶、麦冬各，同煎半个小时，去渣留汁于锅中，再将粳米淘净放入，慢熬成粥，下白糖后服。\n\n" +
                    "服用：分2次空腹食用。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "粳米粥《中医食疗》\n" +
                    "材料：生石膏100g、竹叶、麦冬各20g、粳米100g\n\n" +
                    "制作：生石膏洗净敲碎，注入清水1400ml，烧开后，加竹叶、麦冬各，同煎半个小时，去渣留汁于锅中，再将粳米淘净放入，慢熬成粥，下白糖后服。\n\n" +
                    "服用：分2次空腹食用。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "大麦花生糯米粥《中医食疗》\n" +
                    "材料：大麦、花生、糯米适量\n\n" +
                    "制法：将大麦粒、花生、糯米煮粥食用。\n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "补虚正气粥《圣济总录》\n" +
                    "材料：黄芪15  人参5（或党参15）  粳米50（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：黄芪、人参切片，用冷水浸泡半小时，入沙锅煮沸，渣再煮一遍，取汁同粳米加水煮粥。 \n\n" +
                    "服法：每天早上空腹服食。 \n\n" +
                    "功效：健脾益气。适用于：劳倦内伤、五脏虚衰、年老体弱、久病羸瘦。\n\n" +
                    "症见：体虚自汗、慢性泄泻、食欲不振、心慌气短、气虚浮肿等。 \n\n" +
                    "禁忌：不宜与萝卜、茶叶同服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "麦冬粥 \n" +
                    "材料：麦冬9g  粳米100g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：粳米淘净，将麦冬入沙锅同煮至米烂粥成。 \n\n" +
                    "服法：餐时代饭食。 ";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "甘松佛手粥 \n" +
                    "材料：甘松5g 佛手5g  粳米50g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：先将甘松、佛手洗净水煎，去渣留汁；将粳米淘净熬粥，粥将成时兑入药汁，再煮10分钟即可。 \n\n" +
                    "服法：5－7天，每天一次，顿服。 \n\n" +
                    "功效：行气疏肝，和胃止痛。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "粟米粥《中医食疗》\n" +
                    "材料：粟米适量\n\n" +
                    "制法：煮熟做饭，或者煮粥食用。\n\n" +
                    "服法：代餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "淡菜皮蛋粥\n" +
                    "配方：淡菜30个，皮蛋1个，粳米适量。\n\n" +
                    "制作：粳米洗净，与切成块的皮蛋、淡菜一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即成。\n\n" +
                    "用法：早晚分两次服食。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "蚝干虾皮粥\n" +
                    "材料：蚝干6枚  虾皮适量  米适量\n\n" +
                    "制法：蚝干与米入锅煮熟，食用前加入虾皮即可。\n\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "神仙粥《敦煌卷子》  \n" +
                    "材料：山药30g  芡实30g  韭菜30g  粳米100g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：将韭菜切成碎末；将芡实煮熟去壳，捣碎；山药捣碎。三味一同与粳米慢火煮成粥。 \n\n" +
                    "服法：每天早上空腹服食。 \n\n" +
                    "功效：壮阳补虚、益气健脾。\n\n" +
                    "适用于：脾肾阳虚之虚劳羸瘦。\n\n" +
                    "症见：精神萎靡，气短乏力，慢性泄泻等。  ";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "甘松佛手粥 \n" +
                    "材料：甘松5g 佛手5g  粳米50g（《饮食须知》：新米动风气，陈米下气。陈米为宜）\n\n" +
                    "制法：先将甘松、佛手洗净水煎，去渣留汁；将粳米淘净熬粥，粥将成时兑入药汁，再煮10分钟即可。\n\n" +
                    "服法：5－7天，每天一次，顿服。\n\n" +
                    "功效：行气疏肝，和胃止痛。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "人参莲肉粥《经验良方》\n" +
                    "材料：人参5  莲子（去芯）10枚  冰糖适量（血糖高者甘草5克代之）\n\n" +
                    "制法：先将人参、莲肉放入碗内加洁净水适量泡发，加入冰糖，再将碗置于蒸锅内隔水蒸炖一小时。\n\n" +
                    "服法：喝汤吃莲子，次日人参可重复使用。 \n\n" +
                    "功效：补脾益气。\n\n" +
                    "适用于：病后体虚。症见：倦怠乏力、食少泄泻、自汗等。\n\n" +
                    "禁忌：忌铁器，不宜与萝卜、茶叶同服。\n\n" +
                    "补虚正气粥《圣济总录》\n" +
                    "材料：黄芪15  人参5（或党参15）  粳米50（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：黄芪、人参切片，用冷水浸泡半小时，入沙锅煮沸，渣再煮一遍，取汁同粳米加水煮粥。\n\n" +
                    "服法：每天早上空腹服食。 \n\n" +
                    "功效：健脾益气。适用于：劳倦内伤、五脏虚衰、年老体弱、久病羸瘦。\n\n" +
                    "症见：体虚自汗、慢性泄泻、食欲不振、心慌气短、气虚浮肿等。 \n\n" +
                    "禁忌：不宜与萝卜、茶叶同服。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "阿胶粥《寿世青编》\n" +
                    "材料：阿胶15g  糯米100g制法：阿胶捣碎，将糯米加水煮粥候熟，加入阿胶稍煮，搅令烊化即成。 \n\n" +
                    "服法：每天早晚餐温热服食。 \n\n" +
                    "功效：养血止血，滋阴润肺，安胎。\n\n" +
                    "适用于：各种慢性出血及血虚证。\n\n" +
                    "症见：面色萎黄、心悸眩晕、虚劳咯血、吐血、尿血、便血等。\n\n" +
                    "桂圆大枣粥 \n" +
                    "材料：桂圆肉10枚  大枣5枚去核  黑米50  冰糖适量（血糖高者去之） \n\n" +
                    "制法：用清水将黑米稍加掏洗，加入5-7倍的温热水浸泡一晚，第二天就可以加桂圆、大枣、冰糖，文火熬煮一小时左右，即可。 \n\n" +
                    "服法：每天早上服食一次。\n\n" +
                    "菠菜粥《本草纲目》\n" +
                    "材料：菠菜250g  粳米250g  食盐 （味精）适量\n\n" +
                    "制法：先将菠菜洗净，在沸水中烫一下、切段，粳米淘净置锅内加水适量熬至粳米熟时将菠菜放入粥中，继续熬煮到成粥，停火，放入盐、味精即可。\n\n" +
                    "服法：当饭吃饱。 功效：补血止血，养阴润燥。\n\n" +
                    "适用于：久病血虚。\n\n" +
                    "症见：面色萎黄，消渴，大便涩滞，衄血，便血等。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "杞合山药粥 \n" +
                    "材料：百合30g  枸杞10g  生山药50g  粳米50g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：粳米淘净置锅内，加水适量熬至粳米汤初开时将百合、枸杞山药放入粥中，继续熬煮到成粥。\n\n" +
                    "服法：每天早上空腹服食。\n\n" +
                    "功效：滋补肝肾，养阴清热。\n\n" +
                    "银耳汤 \n" +
                    "材料：白木耳50g  冰糖适量（血糖高者去之）\n\n" +
                    "制法：银耳洗净后加水适量，煮熟后加入冰糖，稍煮即可。\n\n" +
                    "服法：每天早上服食一次。\n\n" +
                    "海参粥《老老恒言》\n" +
                    "材料：海参适量  粳米100  食盐 （味精）适量 \n\n" +
                    "制法：先将海参浸透，剖洗干净，切片，煮烂，粳米淘净置锅内，加水适量熬至粳米熟时将海参放入粥中，继续熬煮到成粥，停火，放入盐、味精即可。 \n\n" +
                    "服法：当饭吃饱。\n\n" +
                    "功效：补肾养阴。\n\n" +
                    "禁忌：感冒未愈，泄泻下利者不宜。\n\n" +
                    "鲍鱼粥（亦可用淡菜）\n" +
                    "材料：鲍鱼100g  枸杞10g  粳米100g  食盐 （味精）适量。\n\n" +
                    "制法：先将鲍鱼剖洗干净，切花，粳米淘净置锅内，加水适量熬至粳米熟时将鲍鱼放入粥中，继续熬煮到成粥，停火，放入盐、味精即可。\n\n" +
                    "服法：当饭吃饱。 \n\n" +
                    "功效：补肾养阴。 \n\n" +
                    "禁忌：感冒未愈，泄泻下利者不宜。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "干姜茯苓粥\n" +
                    "配方：干姜6g  茯苓15g  粳米50g\n\n" +
                    "制作：先将茯苓水煎1小时以上，连煎2次，去滓，取煎液与粳米煮成粥。\n\n" +
                    "用法：每日1剂，早、晚代餐食用。心衰基本控制后可每周吃2剂，以巩固疗效。\n\n" +
                    "虾米核桃粥 \n" +
                    "材料：虾米30  核桃肉50  粳米100（《饮食须知》：新米动风气，陈米下气。陈米为宜）\n\n" +
                    "制法：将虾米用温水浸泡30分钟；粳米、核桃淘净，与虾米同置沙锅内，放入清水，先用武火煮沸，再用文火煎熬，以米烂汤稠为度。\n\n" +
                    "服法：每天早或晚餐温热服食。\n\n" +
                    "功效：补肾兴阳，强精益气。\n\n" +
                    "枣皮羊肉粥 \n" +
                    "材料：山萸肉10g  桂叶6g  羊肉60g  粳米60g  葱白2条  生姜3片  细盐少许\n\n" +
                    "制法：精羊肉洗净后切细；先用沙锅加适量清水煎煮山萸肉、桂叶，取汁去渣；入羊肉、粳米同煮，煮沸后，再加入葱白、生姜、细盐，熬至粥成。 \n\n" +
                    "服法：当早、晚餐食用。 功效：补肾助阳，健脾益胃。\n\n" +
                    "适用于：肾阳不足，脾胃虚寒等证。 \n\n" +
                    "注：山萸肉、桂叶方可变通为锁阳羊肉粥、肉苁蓉羊肉粥等等。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("精亏"))) {
            return "淡菜皮蛋粥\n" +
                    "配方：淡菜30个，皮蛋1个，粳米适量。\n\n" +
                    "制作：粳米洗净，与切成块的皮蛋、淡菜一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮至米开花即成。\n\n" +
                    "用法：早晚分两次服食。\n\n" +
                    "首乌芹菜粥\n" +
                    "配方：制何首乌15g，芹菜50g（小芹菜），瘦猪肉50g，粳米100g，盐、味精适量。\n\n" +
                    "制作：首乌浓煎取汁，粳米同首乌汁同煮，粥将好时，下瘦肉末、芹菜末，煮至米烂，加盐、味精调味。\n\n" +
                    "用法：早晚分两次服食。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "萱花百合粥《圣济总录》\n" +
                    "材料：金针菜30g  百合30g  糯米50g制法：金针菜切段；百合剥皮洗净后切碎，两物与糯米同入沙锅，加水适量同煮，粥成即可。\n\n" +
                    "服法：每天晚餐空腹服食。 \n\n" +
                    "功效：舒肝解郁，清心安神。 \n\n" +
                    "禁忌：脾胃虚寒，慢性泄泻者慎服。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "益母草粥《食医心鉴》\n" +
                    "材料：益母草60g  粳米60g（《饮食须知》：新米动风气，陈米下气。陈米为宜）  红糖少许 。\n\n" +
                    "制法：水煎益母草半小时，去渣取汁，同粳米煮至粥成，加入红糖（血糖高者去之）。  \n\n" +
                    "服法：每日2次，温服。\n\n" +
                    "功效：活血调经。\n\n" +
                    "禁忌：孕妇禁用。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("痰"))) {
            return "薤白粥\n" +
                    "配方：薤白12g  瓜萎15g  法半夏5g  生姜10g  枳实10g  粳米30g。\n\n" +
                    "制作：先将瓜蒌、半夏、枳实水煎，取煎液60m1；将粳米加水煮沸后下煎液、薤白、生姜，小火煮粥熟即成。\n\n" +
                    "用法：早、晚各吃1剂。可连吃1周。\n\n" +
                    "楂梨膏《寿世保元》 \n" +
                    "材料：鲜山楂100g  甜梨150g  蜂蜜50g \n\n" +
                    "制法：山楂、甜梨洗净、去皮取肉，用水煮烂，加蜜收膏。 \n\n" +
                    "服法：每服30克，每日2-3次。\n\n" +
                    "功效：消食化痰止咳。\n\n" +
                    "川贝雪梨饮《中国药膳学》 \n" +
                    "材料：川贝2g  雪梨1个  冰糖适量（血糖高者甘草5克代之）\n\n" +
                    "制法：将梨洗净去皮，挖去梨核，放入川贝，加冰糖，上锅蒸10钟。\n\n" +
                    "服法：每日1次。 \n\n" +
                    "功效：清热化痰，润肺止咳。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "薏苡红豆粥《圣济总录》 \n" +
                    "材料：薏苡仁30g  红豆30g  大米30g \n\n" +
                    "制法：加水适量同煮，粥成即可。服法：每天1次。 \n\n" +
                    "功效：清热利湿，除痹缓急。 \n\n" +
                    "禁忌：脾胃虚寒，慢性泄泻者慎服。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "牛蒡粥《食医心鉴》 \n" +
                    "材料：牛蒡根30g  粳米50g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：粳米淘净；牛蒡根洗净放入锅中，加水适量，用武火烧沸后，转用文火煮10分钟，滤去渣，留药汁待用。牛蒡汁代水煮粳米，煮至米烂粥成。\n\n" +
                    "服法：每天早、晚餐可服食。\n\n" +
                    "功效：益肺清热，利咽散结。\n\n" +
                    "绿豆粥 \n" +
                    "材料：绿豆30g  粳米100g（《饮食须知》：新米动风气，陈米下气。陈米为宜） \n\n" +
                    "制法：先将绿豆浸泡一小时，粳米淘净，入沙锅同煮至米烂粥成。 \n\n" +
                    "服法：午餐时代饭食。 \n\n" +
                    "功效：清热解毒。 \n\n" +
                    "禁忌：脾胃虚寒不宜服。\n\n" +
                    "葛粉栀子粥 \n" +
                    "材料：葛根粉30g  栀子仁5g   粳米100g（《饮食须知》：新米动风气，陈米下气。陈米为宜）\n\n" +
                    "制法：粳米浸泡一宿，与葛根同入沙锅内，加清水1000毫升用文火煮至粥将成时，加入栀子仁末稍煮即可。 \n\n" +
                    "服法：代主食食用。\n\n" +
                    "功效：清热除烦，生津止渴。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("动风"))) {
            return "阿胶鸡子黄饮 \n" +
                    "材料：阿胶10g  鸡子黄（蛋黄）1枚 \n\n" +
                    "制法：阿胶烊化，加入鸡蛋子黄，搅匀。\n\n" +
                    "服法：温服。\n\n" +
                    "功效：滋阴熄风。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "人参龙眼汤丸\n" +
                    "配方：红参6g  龙眼肉12g  红糖10g  糯米粉100g\n\n" +
                    "制作：将红参切片，单独煎3次，取煎液50m1，龙眼肉与红糖剁成汤圆心子；糯米粉水调做成汤圆面，将心子放入其中，煮熟后冲入人参液即成。\n\n" +
                    "用法：早、晚当点心，1剂分次吃完，可连吃1周以上。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "龙眼枣仁粥\n" +
                    "配方：龙眼肉15g  酸枣仁15g  粳米50g\n\n" +
                    "制作：酸枣仁洗净，与龙眼肉、粳米煮成稀粥。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "桂术薏苡仁粥\n" +
                    "配方：桂枝15g  白术12g  甘草6g  薏苡仁50g\n\n" +
                    "制作：将前三味水煎2次，去渣，取两次煎液合并为200m1，与薏苡仁煮成稀粥。\n\n" +
                    "用法：早、晚各1剂，代餐食用。";
        }
        return null;
    }

    public static String getDietNurse(List<String> diseaseList) {
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "百合荸荠梨羹《药膳 汤膳 粥膳》\n" +
                    "配方：百合15g  荸荠30g  雪梨1个  冰糖适量\n" +
                    "\n" +
                    "制作：将荸荠洗净去皮捣烂，雪梨洗净去核切碎，百合洗净，与冰糖一同入锅，加水适量。用武火煮沸，再转用文火煮至汤稠即成。\n" +
                    "\n" +
                    "服用：不拘时饮用。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "苦瓜干贝羹\n" +
                    "材料：苦瓜100g   干贝20g   蛋清一枚\n" +
                    "\n" +
                    "制法：干贝泡发，苦瓜切丁，苦瓜、干贝下锅煮至八分熟，下蛋清，勾芡，煮熟即可。\n" +
                    "\n" +
                    "服用：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "麦冬水鸭汤\n" +
                    "材料：水鸭250g  西洋参2g  麦冬12g  五味子2g  生姜适量\n" +
                    "\n" +
                    "制法：水鸭、生姜、西洋参、麦冬和五味子入锅一起炖，炖至味浓肉熟为度。\n" +
                    "\n" +
                    "服法：随餐服。";
        }

        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "莲子赤豆排骨汤\n" +
                    "材料：莲子（不去芯）15g  赤豆15g  排骨250g\n" +
                    "\n" +
                    "制法：赤豆先泡发后，排骨、莲子、赤豆一起下锅炖，炖至肉熟为度。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "莲子赤豆水鸭汤\n" +
                    "材料：莲子（不去芯）15g  赤豆15g  水鸭250g\n" +
                    "\n" +
                    "制法：赤豆先泡发后，水鸭、莲子、赤豆一起下锅炖，炖至肉熟为度。\n" +
                    "\n" +
                    "服法：随餐服";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "菖蒲猪心汤\n" +
                    "材料：猪心150g  菖蒲6g  生姜适量\n" +
                    "\n" +
                    "制法：猪心焯水后，将猪心、生姜与菖蒲一同入锅煮，煮至猪心熟烂即可。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "莲子银耳汤\n" +
                    "配方：莲子（不去心）10g   银耳20g  \n" +
                    "\n" +
                    "制作：莲子、银耳一同放入锅内，倒入清水，置武火上煮，水沸后，改用文火煮熟即成。 \n" +
                    "\n" +
                    "服法：闲时服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "三七牛肉汤\n" +
                    "配方：三七粉1.5g  山药10g  牛肉100g（忌牛肉者可以猪排骨替代）\n" +
                    "\n" +
                    "制作：牛肉洗净切成小块，与三七、山药粉共炖成汤，加胡椒、姜、葱、酱油适量调味。\n" +
                    "\n" +
                    "用法：午、晚餐佐餐食用。每日1剂。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "金钱排骨汤\n" +
                    "材料：金钱草15g  排骨250g\n" +
                    "\n" +
                    "制法：排骨洗净，焯水后，加入金钱草炖。\n" +
                    "\n" +
                    "服法：喝汤吃肉。";
        }

        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("肝"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "薏苡赤豆汤\n" +
                    "材料：赤豆100g  薏苡仁50g\n" +
                    "\n" +
                    "制法：薏苡仁、赤豆一同入锅炖，炖至豆软烂为度。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "薏苡赤豆汤\n" +
                    "材料：赤豆100g  薏苡仁50g\n" +
                    "\n" +
                    "制法：薏苡仁、赤豆一同入锅炖，炖至豆软烂为度。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "黄花菜炒木耳\n" +
                    "材料：黄花菜250g  干木耳50g  瘦肉100g  盐适量\n" +
                    "\n" +
                    "制法：焯好的黄花菜捞出来泡在凉水里两小时。泡黄花菜的间隙，泡几朵木耳。泡好后清洗干净，撕成小块。瘦\n" +
                    "\n" +
                    "肉切丝，黄花菜捏干水分。锅内放油烧热，放入葱花爆香，把肉丝先下锅煸成白色，然后放木耳煸炒。下黄花菜、加入少许汤、精盐，炒至黄花菜入味，出锅即可。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "萝卜汤\n" +
                    "材料：白萝卜200g\n" +
                    "\n" +
                    "制法：白萝卜削皮洗净，入锅水煮至熟即可。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "黄芪煲鸡\n" +
                    "配方：黄芪15g  大红枣10枚  乌骨鸡1只（约500g）  食盐10g\n" +
                    "\n" +
                    "制作：将黄芪、甘草切片，大枣选无虫蛀者洗净，鸡去毛和内脏，一齐放入沙锅，加水适量，武火煮沸后打去浮沫，放盐和料酒，小火煨至鸡肉熟烂即成。\n" +
                    "\n" +
                    "用法：1剂分2～3日用完，吃肉喝汤。每周2剂，血压升起后再吃2剂巩固效果。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "黄芪煲鸡\n" +
                    "配方：黄芪15g  大红枣10枚  乌骨鸡1只（约500g）  食盐10g\n" +
                    "\n" +
                    "制作：将黄芪、甘草切片，大枣选无虫蛀者洗净，鸡去毛和内脏，一齐放入沙锅，加水适量，武火煮沸后打去浮沫，放盐和料酒，小火煨至鸡肉熟烂即成。\n" +
                    "\n" +
                    "用法：1剂分2～3日用完，吃肉喝汤。每周2剂，血压升起后再吃2剂巩固效果。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "薏仁猪脚汤\n" +
                    "材料：薏仁30g  干净猪脚一只约半斤\n" +
                    "\n" +
                    "制法：薏仁碾碎，猪脚洗净剁块与薏仁一同放入砂锅，加黄酒、姜及清水1500毫升，盖好。先用猛火煮滚，除去汤面浮沫，再用文火煨约2小时;待猪蹄烂熟后，依次加入盐、酱油、葱、胡椒粉。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "薏仁猪脚汤\n" +
                    "材料：薏仁30g，干净猪脚一只约半斤\n" +
                    "\n" +
                    "制法：薏仁碾碎，猪脚洗净剁块与薏仁一同放入砂锅，加黄酒、姜及清水1500毫升，盖好。先用猛火煮滚，除去汤面浮沫，再用文火煨约2小时;待猪蹄烂熟后，依次加入盐、酱油、葱、胡椒粉。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "燕芪肉汤\n" +
                    "材料：燕麦60  黄芪15  瘦肉30 \n" +
                    "\n" +
                    "制法：先煮黄芪取汁，再煮燕麦、瘦肉。 \n" +
                    "\n" +
                    "服法：连汤共食。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "燕芪肉汤\n" +
                    "材料：燕麦60  黄芪15  瘦肉30 \n" +
                    "\n" +
                    "制法：先煮黄芪取汁，再煮燕麦、瘦肉。 \n" +
                    "\n" +
                    "服法：连汤共食。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "沙参麦冬汤\n" +
                    "材料：北沙参10g  麦冬10g  排骨250g\n" +
                    "\n" +
                    "制法：将北沙参、麦冬与排骨洗净，同置锅中，加适量水煲汤，加调味料即成。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "佛手玫瑰汤\n" +
                    "材料：干佛手5g  玫瑰花10g\n" +
                    "\n" +
                    "制法：将玫瑰花与佛手加150ml，煎至100ml，去渣。加入适量红糖。\n" +
                    "\n" +
                    "服法：闲时服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "苦瓜干贝羹\n" +
                    "材料：苦瓜100g   干贝20g   蛋清一枚\n" +
                    "\n" +
                    "制法：干贝泡发，苦瓜切丁，苦瓜、干贝下锅煮至八分熟，下蛋清，勾芡，煮熟即可。\n" +
                    "\n" +
                    "服用：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "黑豆元肉大枣汤\n" +
                    "材料：黑豆50g  大枣50g  元肉（龙眼肉）15g  \n" +
                    "\n" +
                    "制法：水三碗同煎至一碗\n" +
                    "\n" +
                    "服法：早晚两次服用。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "黑豆元肉大枣汤\n" +
                    "材料：黑豆50g  大枣50g  元肉（龙眼肉）15g  \n" +
                    "\n" +
                    "制法：水三碗同煎至一碗\n" +
                    "\n" +
                    "服法：早晚两次服用。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "海蛎豆腐汤\n" +
                    "材料：海蛎100g  豆腐50g  姜适量\n" +
                    "\n" +
                    "制法：海蛎洗净裹地瓜粉，与豆腐一起下沸水煮熟即可。\n" +
                    "\n" +
                    "服法：随餐服。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五味子鱼汤\n" +
                    "配方：五味子10g  党参15g  太子参15g  黄芪30g  柏子仁10g  甘草6g   鲫鱼整条（约500）\n" +
                    "\n" +
                    "制作：先将五味子等中药水煎2次，去渣，取煎液500m1，与鲫鱼煮成汤，可在汤中加生姜粒、葱花少许。\n" +
                    "\n" +
                    "用法：每日1剂，午、晚代餐食用。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "养血调经老鸭汤\n" +
                    "原料：老鸭四分之一只，酒白芍15g  当归6g  川芎6g  熟地15g  虫草花15g  生姜15g  料酒  精盐  味精适量\n" +
                    "\n" +
                    "制作：\n" +
                    "\n" +
                    "(1)将老鸭洗净切块,于凉水中泡去血水，沥干；虫草花洗净，生姜切片。\n" +
                    "\n" +
                    "(2)老鸭块、酒芍、当归、熟地、川芎、虫草花置砂锅中加水适量武火煮开，纳姜片，文火炖至肉熟，起锅前加入精盐、料酒、味精即可。\n" +
                    "\n" +
                    "服法：佐餐服食。\n" +
                    "\n" +
                    "功效：养血补虚。适用于：病后气血不足及血虚证的各种表现及月经不调等。\n" +
                    "\n" +
                    "禁忌：脾胃虚寒者慎服。" +
                    "当归羊肉羹《济生方》\n" +
                    "原料：当归6g  黄芪15g  熟地黄20g  羊肉100g  葱  生姜  料酒  食盐（味精）各适量\n" +
                    "\n" +
                    "制作：先将羊肉洗净，当归、黄芪、熟地黄装入纱布袋内，扎好口，一同放入沙锅内，再加入葱、生姜、食盐、料酒和适量的水，然后将锅置武火上烧沸，再用文火煨炖，直到羊肉熟烂即成。\n" +
                    "\n" +
                    "服法：食用时加入味精，吃肉喝汤。\n" +
                    "\n" +
                    "功效：养血补虚。适用于：病后气血不足及血虚证的各种表现等。\n" +
                    "\n" +
                    "禁忌：外感发热，咽喉肿痛，牙痛者不宜食用。忌用铜器。忌食南瓜。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "枸杞子栗葛鸡\n" +
                    "配方：枸杞子30g  板栗200g  葛根100g   乌骨鸡1只（约1000g）\n" +
                    "\n" +
                    "制作：选宁夏大红杞洗净，板栗去外壳用肉，葛根磨成粉或切成片，鸡宰后去毛和内脏。一齐放入沙锅，加水足量，煮沸后打去浮沫，加盐、料酒适量，小火炖至鸡肉烂熟即成。\n" +
                    "\n" +
                    "用法：肉、药、汤分多次吃完，每周吃1剂，连吃3剂以上。" +
                    "养阴滋补老鸭汤\n" +
                    "原料：老鸭四分之一只，沙参15g  玉竹15g  石斛6g  生地15g  虫草花15g，料酒、精盐、味精适量。\n" +
                    "\n" +
                    "制作：\n" +
                    "\n" +
                    "(1)将老鸭洗净切块，于凉水中泡去血水，沥干；虫草花洗净。\n" +
                    "\n" +
                    "(2)老鸭块、沙参、玉竹、石斛、生地、虫草花置砂锅中加水适量武火煮开，文火炖至肉熟，起锅前加入精盐、料酒、味精即可。 \n" +
                    "\n" +
                    "服法：佐餐服食。功效：滋阴养肺。\n" +
                    "\n" +
                    "适用于：肺阴久虚咳喘；糖尿病之消渴；津亏肠燥之便秘等。\n" +
                    "\n" +
                    "禁忌：脾胃虚寒者慎服。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "灵桂羊肉汤\n" +
                    "配方：仙灵脾9g  肉桂2g  羊肉100g  食盐、姜、葱少许。\n" +
                    "\n" +
                    "制作：将仙灵脾、肉桂人沙锅，水煎2次，共取煎液1000ml；将羊肉切成条加药液同煮，沸后加入姜、葱、食盐煮熟即成。\n" +
                    "\n" +
                    "用法：吃肉喝汤，隔日1剂，直至症状明显改善。                                      \n" +
                    "\n" +
                    "禁忌：不宜与萝卜、茶叶同服。    \n" +
                    "姜烧羊肉\n" +
                    "配方：生姜60g  羊肉250g  大蒜、葱、料酒、酱油适量。\n" +
                    "\n" +
                    "制作：将切成块的羊肉、大蒜、生姜、料酒用小火煨至羊肉烂熟，加酱油或少许盐即成。\n" +
                    "\n" +
                    "用法：吃肉喝汤，隔日1剂，5剂为一疗程。         \n" +
                    "\n" +
                    "禁忌：不宜与萝卜、茶叶同服。" +
                    "益元羊羯子汤\n" +
                    "原料：羊脊1具，肉苁蓉20g  锁阳15g  杜仲6g  枸杞子6g  生山药90g  料酒及大蒜、生姜、精盐、味精适量。\n" +
                    "\n" +
                    "制作：\n" +
                    "\n" +
                    "(1)将羊脊骨剁成数节，于凉水中泡去血水，沥干；将苁蓉、锁阳、杜仲、枸杞装入纱布袋扎好，大蒜、生姜拍破。\n" +
                    "\n" +
                    "(2)将羊脊骨、中药袋置砂锅中加水适量武火煮开，抹去上沫，纳生山药文火炖至肉熟、加姜、蒜稍煮，起锅前加入精盐、料酒、味精即可。 \n" +
                    "\n" +
                    "服法：佐餐服食。\n" +
                    "\n" +
                    "功效：温补肾阳。适用于肾阳不足、肾精亏损之头晕眼花、腰膝酸软、阳痿早泄、不孕不育。\n" +
                    "\n" +
                    "禁忌：不宜与萝卜、茶叶同服。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "三七牛肉汤\n" +
                    "配方：三七粉1.5g  山药10g  牛肉100g（忌牛肉者可以猪排骨替代）\n" +
                    "\n" +
                    "制作：牛肉洗净切成小块，与三七、山药粉共炖成汤，加胡椒、姜、葱、酱油适量调味。\n" +
                    "\n" +
                    "用法：午、晚餐佐餐食用。每日1剂。" +
                    "益肾活血鸭汤\n" +
                    "材料：怀牛膝30g  川芎6g  川花椒（布包）  生姜5片  葱白3条  鸭1只  盐适量 \n" +
                    "\n" +
                    "制法：将鸭去毛及内脏；川芎、牛膝纳入鸭肚内，加入生姜、葱白、花椒，清水适量炖至鸭肉烂透，去牛膝、川芎，加盐。\n" +
                    "\n" +
                    "服法：食肉喝汤。 \n" +
                    "\n" +
                    "功效：补肾益精，活血通络。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("痰"))) {
            return "陈皮紫菜鸭汤 \n" +
                    "材料：陈皮10g  紫菜30g  生姜5片  葱白3条  鸭1只  盐适量 \n" +
                    "\n" +
                    "制法：将鸭去毛及内脏置沙锅内；陈皮切丝与生姜、葱白放入沙锅中，清水适量炖至鸭肉烂透，加紫菜和盐。 \n" +
                    "\n" +
                    "服法：食肉喝汤。\n" +
                    "\n" +
                    "功效：生津化痰。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "鲫鱼汤 \n" +
                    "材料：生姜10片  蒜头15瓣  鲫鱼1只  盐适量\n" +
                    "\n" +
                    "制法：先将鲫鱼去内脏，洗净，加入生姜、蒜头（拍扁），加水1000毫升，煮取500毫升。 \n" +
                    "\n" +
                    "服法：食鱼喝汤。 \n" +
                    "\n" +
                    "功效：利水渗湿。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("动风"))) {
            return "天麻地黄鸭汤\n" +
                    "材料：天麻20g   熟地黄20g  生姜5片  鸭1只  盐适量 \n" +
                    "\n" +
                    "制法：将鸭去毛及内脏置沙锅内；天麻捣碎与熟地、生姜放入沙锅中，清水适量炖至鸭肉烂透，加入盐即可。\n" +
                    "\n" +
                    "服法：食肉喝汤。\n" +
                    "\n" +
                    "功效：养阴熄风。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "清蒸人参鸡\n" +
                    "配方：红参10g  子母鸡1只  香菇15g  玉兰片10g  火腿10g  调料适量  \n" +
                    "\n" +
                    "制作：将鸡宰杀去内脏去毛洗净，将火腿、香菇、玉兰片、葱、姜均切成片；将人参切碎用温水泡，单上笼蒸30分钟；将鸡、配料、调料一齐放入盆中，加水适量，蒸后的人参连汤倒入；上笼，蒸至鸡肉烂熟即成。\n" +
                    "\n" +
                    "用法：可用以佐餐，1剂分多次吃完，每周可吃1～2剂。\n" +
                    "芪参烧活鱼\n" +
                    "配方：黄芪30g  党参30g  活鲤鱼1尾（约1000g）  葱、蒜、酱油等调料适量\n" +
                    "\n" +
                    "制作：将鲤鱼剖去内脏，去鳞、鳃、鳍，洗净，油炸成金黄色，捞出；将炸鱼放入锅中，加黄芪、党参片，加水适量同煮，沸后改小火煨至汤浓，去参、芪，加入调料烧开即成。\n" +
                    "\n" +
                    "用法：佐餐分次食之，隔两日一剂，直吃至症状消失。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "燕芪肉汤\n" +
                    "材料：燕麦60g  黄芪15g  瘦肉30g \n" +
                    "\n" +
                    "制法：先煮黄芪取汁，再煮燕麦、瘦肉。 \n" +
                    "\n" +
                    "服法：连汤共食。 \n" +
                    "\n" +
                    "功效：补虚止汗，益气通肠。\n" +
                    "\n" +
                    "适用于：久病体虚。\n" +
                    "\n" +
                    "症见：纳差、便秘、自汗等。\n" +
                    "\n" +
                    "用法：1剂分2～3日用完，吃肉喝汤。每周2剂，血压升起后再吃2剂巩固效果。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "蛋黄油\n" +
                    "配方：鸡蛋100个，蜂蜜适量。\n" +
                    "\n" +
                    "制作：用鲜鸡蛋，去蛋清，留蛋黄，在锅中熬煎，取蛋黄油瓶装备用。\n" +
                    "\n" +
                    "用法：每次吃1小茶匙，每日2次，连吃10天左右。吃时可调蜂蜜少许。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "麻黄牛肉汤\n" +
                    "配方：麻黄5g  麻黄根10g  牛肉50g  姜粒、葱花、酱油少许。\n" +
                    "\n" +
                    "制作：先将麻黄、麻黄根加水煎煮2次，取两次煎液合并，去渣；用煎液煮牛肉，至牛肉烂熟，加姜粒、葱花、酱油，煮沸即成。\n" +
                    "\n" +
                    "用法：早、晚佐餐食用，每次1剂，轻者可1剂分两次，趁热吃肉喝汤。" +
                    "茯桂鲤鱼汤\n" +
                    "配方：桂枝10g  茯苓10g  鲤鱼1尾，姜、葱适量。\n" +
                    "\n" +
                    "制作：桂枝，小火煎10分钟，滤去药渣，取药液100m1。鲤鱼500g左右，去鳞、鳃、肚肠，切成块；鱼块在油锅中略煎，加水煮沸后加入药液、姜、葱和其他调料（不用盐），煮鱼肉熟即成。\n" +
                    "\n" +
                    "用法：每日1剂，午、晚佐餐食用。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "生脉甲鱼汤\n" +
                    "配方：西洋参6g  麦冬15g  五味子10g  火麻仁20g  酸枣仁12g  炙甘草9g  甲鱼500g\n" +
                    "\n" +
                    "制作：将诸药水煎3次，将3次煎液混合，约500m1；甲鱼宰杀后去头、脚爪和内脏，连壳和肉与药液煨炖，先武火煮沸后打去浮沫，再小火炖至肉熟烂。一般可不加调味品，因药液有香甜味。若嫌味淡，龟肉可蘸少许酱油吃。\n" +
                    "\n" +
                    "用法：每日或隔日1剂，分多次佐餐，吃甲鱼肉喝汤。         \n" +
                    "\n" +
                    "禁忌：孕妇禁用。" +
                    "玉竹炖鱼\n" +
                    "配方：玉竹50g  红参6g  活龟500g\n" +
                    "\n" +
                    "制作：将玉竹、红参切片洗净；将活龟放入盆中，倒入热水，使排尽尿，宰去头，去内脏。将玉竹、红参，连龟甲、龟肉一齐放入沙锅，加水适量，武火烧开，打去浮沫，再文火煮至肉熟烂、龟甲溶化，即成。\n" +
                    "\n" +
                    "用法：1剂分多次吃完，吃肉喝汤，玉竹红参也可吃下。每周吃2剂，直至气阴充足。\n" +
                    "\n" +
                    "禁忌：孕妇禁用。\n";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("精亏"))) {
            return "淡菜汤\n" +
                    "配方：淡菜500g   生姜适量\n" +
                    "\n" +
                    "制作：淡菜洗净隔水炖，武火烧开，文火炖15分钟即成。\n" +
                    "\n" +
                    "服法：喝汤吃肉。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "陈皮茯苓糕\n" +
                    "材料：陈皮10g  茯苓粉20g  糯米粉300g  白砂糖50g  红糖20g\n" +
                    "\n" +
                    "做法：将洗净的陈皮切碎后，与茯苓粉、糯米粉、红糖、白砂糖同放人盆中，加清水适量，充分搅拌均匀，倒人浅方盘中，用大火隔水蒸熟，取下冷却后切成小块即可食用。";
        }

        return null;
    }

    public static String getDrinkNurse(List<String> diseaseList) {
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "龙胆3g  茵陈8g  栀子3g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "黄芪10g  防风9g  白术5g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "沙参9g  麦冬10g  五味子4g  西洋参4g";
        }

        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "党参10g  陈皮5g  大枣5g  山药粉15g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "党参10g  干姜5g  炙甘草5g  红糖5g";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("寒"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "藿香10g  苏梗10g  陈皮5g  砂仁5g";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "荷叶5g  佩兰10g  六一散10g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "党参10g  陈皮5g  鸡内金5g  神曲10g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "砂仁5g  党参10g  生姜5g  红糖5g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "陈皮5g  麦芽10g  佛手5g  枳壳5g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "生大黄1g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "桑椹6g  枸杞6g  沙苑子6g  怀牛膝6g ";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "枸杞6g  仙灵脾6g  杜仲6g  补骨脂6g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "小春花8g  菊花8 g 生甘草3g";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "荷叶5g  佩兰10g  六一散10g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "当归3g  川芎3g  郁金6g ";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("痰"))) {
            return "胖大海2枚  菊花9g   金银花9g   甘草3g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("动风"))) {
            return "天麻3g  钩藤4g  牛膝4g  益母草3g  栀子3g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "瓜蒌3g  薤白2g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "当归6g   黄芪9g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "沙参10g  石斛5g  麦冬10g  玉竹5g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "党参6g  黄芪6g  大枣6枚";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "枸杞8g  桑椹8g  白芍8g  当归3g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "枸杞8g  桑椹8g  麦冬8g  生地8g\n\n" +
                    "参斛养阴茶\n" +
                    "\n" +
                    "原料：洋参6g  石斛10g  枸杞10g  麦冬15g  冰糖适量（血糖高者甘草3克代之）\n" +
                    "\n" +
                    "制作：先将药材放入碗内加洁净水适量泡发，加入冰糖，再将碗置于蒸锅内隔水蒸炖一小时。\n" +
                    "\n" +
                    "服法：药渣滤出，喝汤代茶，药渣可与冰糖再炖一次。\n" +
                    "\n" +
                    "功效：养阴生津，滋补肝肾。\n" +
                    "\n" +
                    "禁忌：忌铁器，不宜与萝卜、茶叶同服。\n" +
                    "\n" +
                    "\n" +
                    "                              ";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "干姜5g  炙甘草5g  橘皮10g \n\n" +
                    "枸杞6g  仙灵脾6g  杜仲6g  补骨脂6g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("精亏"))) {
            return "熟地12g  山萸6g  山药12g  黄精12g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "三花如意茶\n" +
                    "\n" +
                    "材料：玫瑰花4g  茉莉花3g  菊花6g  佛手5g 冰糖适量（血糖高者甘草3克代之）\n" +
                    "\n" +
                    "制法：先将玫瑰花、茉莉花、佛手、菊花放入玻璃杯中，冲入热开水，静置3－5分钟，滤出汤液，加入冰糖搅拌即可。\n" +
                    "\n" +
                    "服法：代茶饮。\n" +
                    "\n" +
                    "功效：芳香理气。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "苍术5g  陈皮8g  茯苓10g  藿香5g";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "黄芩8g  生大黄3g  柴胡8g  郁金8g";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "桑叶9g  菊花9g  牛蒡子4g  甘草4g   \n" +
                    "\n" +
                    "制法：沸水冲泡。 \n" +
                    "\n" +
                    "服法：代茶频饮。" +
                    "银花茶 \n" +
                    "\n" +
                    "材料：银花15g  生甘草5g  \n" +
                    "\n" +
                    "制法：沸水冲泡。 \n" +
                    "\n" +
                    "服法：代茶频饮。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "红参6g 红景天9g 炙甘草2g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "红参6g 龙眼肉9g 丹参6g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "西洋参2g  麦冬12g  五味子2g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "桂枝10g  炙甘草6g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "益寿饮\n" +
                    "\n" +
                    "原料：生山楂片15g  决明子15g  菊花6g  绿茶5g \n" +
                    "\n" +
                    "制作：先将决明子打碎、同菊花、山楂水煎取汁。汤汁冲泡绿茶。\n" +
                    "\n" +
                    "服法：代茶饮。\n" +
                    "\n" +
                    "功效：平肝降压，润肠通便。适用于高血压兼有冠心病者；对阴虚阳亢、大便秘结等症有效。" +
                    "莲子芯2g  银杏叶2g  竹叶6g\n" +
                    "\n" +
                    "忌铁器,不宜与萝卜、茶叶同服。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "炒枣仁8g  柏子仁8g  远志3g";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "栀子3g  连翘3g  黄芩3g  竹茹丝3g";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("肝"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "绿茶";
        }


        return null;
    }

    public static String getSportNurse(List<String> diseaseList) {
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "五禽戏——鹿戏：手足着地，头向两侧后视，左三右二。然后伸左脚3次，伸右脚2次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("精亏"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——熊戏:仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "五禽戏——熊戏:仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "八段锦——双手攀足固肾腰：①过渡式,两腿挺膝伸直收左脚开步站立,两手经侧上举（吸）；两臂屈肘,两掌下按胸前落于体侧（呼）。②两臂上举（吸）；两臂屈肘,两掌下按经胸前（呼）；两掌心旋向上,掌指顺腋下向后插沿脊柱两侧向下摩运至臀部（吸）；上体前俯，两掌沿两腿后向下摩运经脚两侧置于脚面（呼）。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——鹿戏：手足着地，头向两侧后视，左三右二。然后伸左脚3次，伸右脚2次。\n" +
                    "八段锦——双手攀足固肾腰：①过渡式,两腿挺膝伸直收左脚开步站立,两手经侧上举（吸）；两臂屈肘,两掌下按胸前落于体侧（呼）。②两臂上举（吸）；两臂屈肘,两掌下按经胸前（呼）；两掌心旋向上,掌指顺腋下向后插沿脊柱两侧向下摩运至臀部（吸）；上体前俯，两掌沿两腿后向下摩运经脚两侧置于脚面（呼）。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——鹿戏：手足着地，头向两侧后视，左三右二。然后伸左脚3次，伸右脚2次。\n" +
                    "八段锦——双手攀足固肾腰：①过渡式,两腿挺膝伸直收左脚开步站立,两手经侧上举（吸）；两臂屈肘,两掌下按胸前落于体侧（呼）。②两臂上举（吸）；两臂屈肘,两掌下按经胸前（呼）；两掌心旋向上,掌指顺腋下向后插沿脊柱两侧向下摩运至臀部（吸）；上体前俯，两掌沿两腿后向下摩运经脚两侧置于脚面（呼）。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气逆"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("血虚"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("肝"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("胆"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))
        ) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("心"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "五禽戏——虎戏:手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("动风"))) {
            return "五禽戏——猿戏:如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("湿"))) {
            return "五禽戏——熊戏:仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("痰"))) {
            return "五禽戏——熊戏:仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("血瘀"))) {
            return "五禽戏——熊戏:仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——猿戏：如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "八段锦——左右开弓似射雕：身体重心右移，左脚向左开立；同时两掌向上交叉于胸前（吸），两腿徐缓屈膝成马步，两手开弓（呼）。收脚还原：身体重心右移,抬起左脚向右脚靠拢；同时右掌向上向右向下划弧（吸），左脚并步伸直；同时两掌分别由两侧下落捧于腹前（呼）。右开弓的呼吸方法同左开弓。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("痰"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏：如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。\n" +
                    "八段锦——摇头摆尾去心火：①过渡式，身体重心左移，左脚向左大开立；同时两掌上托至头上方（吸），两腿徐缓屈膝下蹲成马步；同时两臂向两侧下落两掌附于膝关节上方（呼）。此式呼吸以上肢动作为主。②身体重心稍升起（吸），而后右移，身体躯干向右倾，随之俯身（闭气）；身体重心左移，上体由右向前向左旋转（呼）；重心移至双腿，头向后旋摇（吸），上体下沉成马步（呼）。左摇头摆尾没有过渡式，呼吸方法同右式。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏：如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。\n" +
                    "八段锦——摇头摆尾去心火：①过渡式，身体重心左移，左脚向左大开立；同时两掌上托至头上方（吸），两腿徐缓屈膝下蹲成马步；同时两臂向两侧下落两掌附于膝关节上方（呼）。此式呼吸以上肢动作为主。②身体重心稍升起（吸），而后右移，身体躯干向右倾，随之俯身（闭气）；身体重心左移，上体由右向前向左旋转（呼）；重心移至双腿，头向后旋摇（吸），上体下沉成马步（呼）。左摇头摆尾没有过渡式，呼吸方法同右式。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏：如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。\n" +
                    "八段锦——摇头摆尾去心火：①过渡式，身体重心左移，左脚向左大开立；同时两掌上托至头上方（吸），两腿徐缓屈膝下蹲成马步；同时两臂向两侧下落两掌附于膝关节上方（呼）。此式呼吸以上肢动作为主。②身体重心稍升起（吸），而后右移，身体躯干向右倾，随之俯身（闭气）；身体重心左移，上体由右向前向左旋转（呼）；重心移至双腿，头向后旋摇（吸），上体下沉成马步（呼）。左摇头摆尾没有过渡式，呼吸方法同右式。每日可练2次，每次15～20分钟，3个月为一个疗程。  ";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——鸟戏：一足立地，另一足翘起，扬眉鼓力，两臂张开如欲飞状，两足交替各7次。然后坐下伸一脚，用手挽另一脚，左右交替各7次，再伸缩两臂各7次。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "八段锦——摇头摆尾去心火：①过渡式，身体重心左移，左脚向左大开立；同时两掌上托至头上方（吸），两腿徐缓屈膝下蹲成马步；同时两臂向两侧下落两掌附于膝关节上方（呼）。此式呼吸以上肢动作为主。②身体重心稍升起（吸），而后右移，身体躯干向右倾，随之俯身（闭气）；身体重心左移，上体由右向前向左旋转（呼）；重心移至双腿，头向后旋摇（吸），上体下沉成马步（呼）。左摇头摆尾没有过渡式，呼吸方法同右式。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("心"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——虎戏：手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。\n" +
                    "八段锦——攒拳怒目增力气：接上动，身体重心右移，左脚向左开步（吸）；两脚徐缓屈膝半蹲成马步，两手抱拳于腰间（呼）；身体重心微微提起（吸）；屈膝成马步的同时，左拳徐缓用力向前冲出（呼）；左拳变掌外缠绕（吸）；左掌变拳屈肘回收至腰间（呼）。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——猿戏：如猿攀物，使双脚悬空，上下伸缩身体7次，接着以双脚钩住物体，使身体倒悬，左右脚交替各7次。然后以手钩住物体，引体倒悬，头部向下各7次。\n" +
                    "八段锦——摇头摆尾去心火：①过渡式，身体重心左移，左脚向左大开立；同时两掌上托至头上方（吸），两腿徐缓屈膝下蹲成马步；同时两臂向两侧下落两掌附于膝关节上方（呼）。此式呼吸以上肢动作为主。②身体重心稍升起（吸），而后右移，身体躯干向右倾，随之俯身（闭气）；身体重心左移，上体由右向前向左旋转（呼）；重心移至双腿，头向后旋摇（吸），上体下沉成马步（呼）。左摇头摆尾没有过渡式，呼吸方法同右式。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——虎戏：手足着地，身躯前纵后退各3次，接着上肢向前下肢向后引腰。然后面部仰天，恢复起始动作，再如虎行般前进后退各7次。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气滞"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("胃"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("湿"))
                && diseaseList.stream().anyMatch(s -> s.equals("热"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。";
        }
        if (diseaseList.size() == 1 && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "八段锦——攒拳怒目增力气：接上动，身体重心右移，左脚向左开步（吸）；两脚徐缓屈膝半蹲成马步，两手抱拳于腰间（呼）；身体重心微微提起（吸）；屈膝成马步的同时，左拳徐缓用力向前冲出（呼）；左拳变掌外缠绕（吸）；左掌变拳屈肘回收至腰间（呼）。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("阳虚"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("脾"))
                && diseaseList.stream().anyMatch(s -> s.equals("气虚"))) {
            return "五禽戏——熊戏：仰卧，两手抱膝下，举头，左右侧分别着地各7次。然后蹲地，双手交替按地。\n" +
                    "八段锦——调理脾胃须单举：两腿徐缓挺膝伸直，左掌上托至头上，掌心朝上；同时右臂内旋，掌心朝下（吸）。左上撑，右下按，力达两掌根，舒胸展体，拔长左腰体（暂时闭气）；松腰沉髋，身体重心缓慢下落；左臂屈肘外旋左掌经面前落于腹前（呼）。右单举呼吸方法同左单举。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("肺"))
                && diseaseList.stream().anyMatch(s -> s.equals("阴虚"))) {
            return "五禽戏——鸟戏：一足立地，另一足翘起，扬眉鼓力，两臂张开如欲飞状，两足交替各7次。然后坐下伸一脚，用手挽另一脚，左右交替各7次，再伸缩两臂各7次。\n" +
                    "八段锦——左右开弓似射雕：身体重心右移，左脚向左开立；同时两掌向上交叉于胸前（吸），两腿徐缓屈膝成马步，两手开弓（呼）。收脚还原：身体重心右移，抬起左脚向右脚靠拢；同时右掌向上向右向下划弧（吸），左脚并步伸直；同时两掌分别由两侧下落捧于腹前（呼）。右开弓的呼吸方法同左开弓。每日可练2次，每次15～20分钟，3个月为一个疗程。";
        }
        if (diseaseList.size() == 3 && diseaseList.stream().anyMatch(s -> s.equals("gallbladder"))
                && diseaseList.stream().anyMatch(s -> s.equals("qiZhi"))
                && diseaseList.stream().anyMatch(s -> s.equals("thermal"))) {
            return "茶";
        }
        if (diseaseList.size() == 2 && diseaseList.stream().anyMatch(s -> s.equals("lung"))
                && diseaseList.stream().anyMatch(s -> s.equals("qiXu"))) {
            return "茶";
        }

        return null;
    }

    public static String convertZhDisease(String disease) {
        if (TcmConstant.healthElement.containsKey(disease)) {
            return TcmConstant.healthElement.get(disease);
        }
        return null;
    }

    public static Object getHealthElementDesc(String dis) {
        switch (dis) {
            case "bloodStasis":
                return "主要指血液瘀积，血行受阻。常见的症状为固定痛、刺痛，或胸腹肿块，出血色黯成块、痛经、月经紫黯夹块、面色晦黯、指端青紫、唇紫、蜘蛛痣、腹壁青筋显露等。";
            case "qiZhi":
                return "主要指气机阻滞。常见的症状为胁、腹、脘、胸等部位的胀闷甚至疼痛，且疼痛表现位胀痛、窜痛、绞痛，嗳气、呕逆、矢气多、排便不爽，月经错乱，腹痛欲泻，咽部异物感等。";
            case "thermal":
                return "主要指火热之邪侵袭肢体，或体内阳热之气过盛。常见的症状为发热、汗出、口渴、面红、目赤，烦躁、发狂，痰黄、小便短黄等。";
            case "bloodHeat":
                return "主要指火热炽盛，侵迫血脉，血液妄行。常见的症状为身热夜甚，吐血、衄血、斑疹、崩漏，神昏、谵语、躁扰不宁，月经提前、色深红，以及痈疖疮疡等。";
            case "yin":
                return "主要指水饮停聚于肺、心包、胸胁、胃肠等处。常见的症状为呕吐清水、胃部振水音，咳吐多量清稀、泡沫痰，心悸、胸闷，胸腔积液等。";
            case "yinXu":
                return "主要指阴液亏少，虚火偏旺，滋润、濡养等作用减退。常见的症状为盗汗、手足心热、咽干、颧红，长期低热、午后潮热，眼花、耳鸣、多梦、失眠、饥不欲食、胃脘嘈杂、大便干结等。";
            case "yangKang":
                return "主要指阳气旺盛，亢扰于上。常见的症状为头晕、头目胀痛、急躁易怒、失眠、多梦、面红目赤、血压升高等。";
            case "phlegm":
                return "主要指体内水液凝聚成痰。常见的症状为痰多，肢体出现圆滑包块，形体肥胖，咽部异物感，吞食梗塞，心胸闷痛等。";
            case "wind":
                return "主要指因热极、阳亢、阴血亏虚等内部病理变化。常见的症状为眼花、直视上窜、头摇、头晕、肢体抽搐、颤抖、半身不遂、口眼歪斜等。";
            case "bloodDeficiency":
                return "主要指血液亏虚，脏腑、经络、组织失却濡养。常见的症状为头晕、眼花、肢体肌肤麻木，脸面、眼睑、口唇的颜色淡白，妇女月经量少，心慌、多梦、健忘等。";
            case "qiXu":
                return "主要指元气亏虚，脏腑功能活动减退。常见的症状为神疲，倦怠乏力，活动后加重，容易感冒，经常腹泻，气短、声低、懒言等。";
            case "wet":
                return "主要指外界湿邪侵袭，或体内水液运化失常，以致湿浊停聚。常见的症状为身体酸重、头重、胸闷、口甜、口黏腻、厌油腻，腹泻、身黄、目黄，或为水痘、疱疹等。";
            case "jingKui":
                return "主要指精亏髓少形体失其充养。常见的症状为长期耳鸣、健忘、腰膝酸软、腰痛，生长发育迟缓、精少不育、闭经、性冷淡等。";
            case "yangXu":
                return "主要指阳气亏损，机体失却温煦。常见的症状为经常怕冷、四肢凉、脘腹腰背等处有寒冷感、喜温恶凉，水肿、小便清长、经常腹泻、完谷不化、经常便溏，气喘、自汗等。";
            case "jinKui":
                return "主要指津液不足，脏器组织官窍失却充盈、滋润。常见的症状为皮肤干燥、口渴、尿短黄、大便秘结、眼窝凹陷，鼻唇、舌干燥等。";
            case "stomach":
                return "主要表现为受纳、和降、腐熟功能障碍。常见的症状为胃脘胀满或疼痛、嗳气、恶心、呕吐、呃逆等。";
            case "liver":
                return "主要表现为疏泄与藏血功能失常。常见的症状为胸胁少腹胀痛或窜痛、情志抑郁或易怒、头晕胀痛、肢体震颤、手足抽搐，以及眼部症状、月经不调等。";
            case "spleen":
                return "主要表现为运化、升清、统血功能的失常。常见的症状为腹胀、便溏、食欲不振、浮肿、内脏下垂、慢性出血等。";
            case "smallIntestine":
                return "主要表现为泌别清浊功能和气机的失常。常见的症状为腹胀、腹痛、肠鸣、腹泻、小便涩痛、小便混浊等。";
            case "gallbladder":
                return "主要表现为贮藏和排泄胆汁功能失常。常见的症状为胆怯易惊、惊悸不宁、口苦、黄疸等。";
            case "kidney":
                return "主要表现为生长、发育、生殖功能障碍和水液代谢失常。常见的症状为腰膝酸软、眩晕耳鸣、发育迟缓、发白、脱发、牙齿动摇，男子阳痿遗精、精少不育，女子经少经闭、不孕，以及水肿、大小便异常等。";
            case "largeIntestine":
                return "主要表现为大肠传导功能失常。常见的症状为便秘、腹泻、腹痛等。";
            case "mind":
                return "要表现为意识思维等神经活动的功能失常。常见的症状为健忘、多梦、失眠、精神错乱、神志昏迷等。";
            case "bladder":
                return "主要表现为贮尿排尿功能失常。常见的症状为小便频急涩痛、尿闭以及遗尿、小便失禁等。";
            case "lung":
                return "主要表现为宣发、肃降及肺系功能失常。常见的症状为咳嗽、气喘、咯痰、胸闷胸痛、咽痛、声音嘶哑、鼻塞流涕等。";
            case "heart":
                return "主要表现为心脏及其主血脉运行的功能失常。常见的症状为心悸、心痛、心烦、口舌生疮、胸闷、气喘等。";

        }
        return null;
    }

    //1：和平质:2：阴虚质、3：气郁质、4:湿热质、5:气虚质、6:阳虚质:7:痰湿质、8:血瘀质:9:特禀质
    public static int convertConstitutionCode(String constitution) {
        if(constitution == null) return -1;
        switch (constitution.trim()){
            case "和平质":
                return TcmConstant.TCM_CONSTITUTION_1;
            case "阴虚质":
                return TcmConstant.TCM_CONSTITUTION_2;
            case "气郁质":
                return TcmConstant.TCM_CONSTITUTION_3;
            case "湿热质":
                return TcmConstant.TCM_CONSTITUTION_4;
            case "气虚质":
                return TcmConstant.TCM_CONSTITUTION_5;
            case "阳虚质":
                return TcmConstant.TCM_CONSTITUTION_6;
            case "痰湿质":
                return TcmConstant.TCM_CONSTITUTION_7;
            case "血瘀质":
                return TcmConstant.TCM_CONSTITUTION_8;
            case "特禀质":
                return TcmConstant.TCM_CONSTITUTION_9;
        }
        return -1;
    }
}
