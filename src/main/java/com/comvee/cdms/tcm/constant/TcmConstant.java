package com.comvee.cdms.tcm.constant;

import com.comvee.cdms.tcm.model.vo.TcmHealthElementVO;

import java.util.HashMap;
import java.util.Map;

public class TcmConstant {
    public static final int TCM_COLLECT_TYPE_UN_START = 1;
    public static final int TCM_COLLECT_TYPE_START = 2;
    public static final int TCM_COLLECT_TYPE_FINISH = 3;

    //1：和平质:2：阴虚质、3：气郁质、4:湿热质、5:气虚质、6:阳虚质:7:痰湿质、8:血瘀质:9:特禀质
    public static final int TCM_CONSTITUTION_1 = 1;
    public static final int TCM_CONSTITUTION_2 = 2;
    public static final int TCM_CONSTITUTION_3 = 3;
    public static final int TCM_CONSTITUTION_4 = 4;
    public static final int TCM_CONSTITUTION_5 = 5;
    public static final int TCM_CONSTITUTION_6 = 6;
    public static final int TCM_CONSTITUTION_7 = 7;
    public static final int TCM_CONSTITUTION_8 = 8;
    public static final int TCM_CONSTITUTION_9 = 9;

    //1:火行人、2：金行人、3：土行人、4：水行人、5：木行人
    public static final int TCM_FEATURE_FIRE = 1;
    public static final int TCM_FEATURE_METAL = 2;
    public static final int TCM_FEATURE_EARTH = 3;
    public static final int TCM_FEATURE_WATER = 4;
    public static final int TCM_FEATURE_WOOD = 5;
    public static final String TCM_ELEMENT_FEATURE_METAL = "{'liver': 6,'largeIntestine': 10,'lung': 10}";//肝 +6 大肠 +10 肺 +10
    public static final String TCM_ELEMENT_FEATURE_WOOD = "{'liver':10,'spleen':6,'gallbladder':10}";//肝 +10 脾 +6 胆 +10
    public static final String TCM_ELEMENT_FEATURE_WATER = "{'kidney':10,'bladder':10,'heart':6}";//肾 +10 膀胱 +10 心 +6
    public static final String TCM_ELEMENT_FEATURE_FIRE = "{'smallIntestine':10,'lung':6,'heart':10}";//小肠 +10 肺 +6 心 +10;
    public static final String TCM_ELEMENT_FEATURE_EARTH = "{'stomach':10,'spleen':10,'kidney':6}";//胃 +10 脾 +10 肾 +6


    //舌色 1：黑舌、2：绛舌、3：暗舌、4：淡舌、5：紫舌、6：红舌、7：白舌、8：淡紫舌
    public static final int TCM_TONGUE_COLOR_BLACK = 1;
    public static final int TCM_TONGUE_COLOR_DEEP_RED = 2;
    public static final int TCM_TONGUE_COLOR_DARK = 3;
    public static final int TCM_TONGUE_COLOR_TINGE = 4;
    public static final int TCM_TONGUE_COLOR_PURPLE = 5;
    public static final int TCM_TONGUE_COLOR_RED = 6;
    public static final int TCM_TONGUE_COLOR_WHITE = 7;
    public static final int TCM_TONGUE_COLOR_PALE_PURPLE = 8;
    public static final String TCM_ELEMENT_TONGUE_COLOR_DEEP_RED = "{'thermal':9,'bloodHeat':5,'yinXu':5,'jinKui':4}";//热 +9 血热 +5 阴虚 +5  津亏 +4
    public static final String TCM_ELEMENT_TONGUE_COLOR_DARK = "{'bloodStasis':10, 'qiZhi':8, 'qiXu':8}";//血瘀 +10 气滞 +8 气虚 +8
    public static final String TCM_ELEMENT_TONGUE_COLOR_TINGE = "{'bloodDeficiency': 7, 'qiXu': 5, 'yangXu': 5}";//血虚 +7 气虚 +5 阳虚 +5
    public static final String TCM_ELEMENT_TONGUE_COLOR_PURPLE = "{'bloodStasis':6, 'qiZhi':2,'thermal':2,'stomach':2,'liver':2,'heart':2}";//血瘀 +6 气滞 +2 热 +2 胃 +2 肝 +2 心 +2
    public static final String TCM_ELEMENT_TONGUE_COLOR_RED = "{'thermal':7,'yinXu':4,'jinKui':3}";//热 +7 阴虚 +4 津亏 +3
    public static final String TCM_ELEMENT_TONGUE_COLOR_PALE_PURPLE = "{'yangXu':8}";//阳虚 +8

    //舌质 1：适中、2：胖大、3：瘦薄、4：齿痕、5：裂纹、6：老、7：嫩、8:肿胀、9：点刺
    public static final int TCM_TONGUE_BODY_MODERATION = 1;
    public static final int TCM_TONGUE_BODY_FAT = 2;
    public static final int TCM_TONGUE_BODY_THIN = 3;
    public static final int TCM_TONGUE_BODY_BITE = 4;
    public static final int TCM_TONGUE_BODY_CRACK = 5;
    public static final int TCM_TONGUE_BODY_AGED = 6;
    public static final int TCM_TONGUE_BODY_YOUNG = 7;
    public static final int TCM_TONGUE_BODY_SWELL = 8;
    public static final int TCM_TONGUE_BODY_PIMPLE = 9;
    public static final String TCM_ELEMENT_TONGUE_BODY_FAT = "{'bloodStasis':3,'thermal':4,'yin':4,'qiXu':4,'wet':4,'yangXu':4,'spleen':4}";//血瘀 +3 热 +4 饮 +4 气虚 +4 湿 +4 阳虚 +4 脾 +4
    public static final String TCM_ELEMENT_TONGUE_BODY_THIN = "{'thermal':4,'yinXu':5,'bloodDeficiency':5,'qiXu':4}";//热 +4 阴虚 +5 血虚 +5 气虚 +4
    public static final String TCM_ELEMENT_TONGUE_BODY_BITE = "{'thermal':-3,'yin':4,'bloodDeficiency':4,'qiXu':4,'wet':5,'yangXu':4,'spleen':4}";//齿痕 热 -3 饮 +4 血虚 +4 气虚 +4 湿 +5 阳虚 +4 脾 +4
    public static final String TCM_ELEMENT_TONGUE_BODY_CRACK = "{'thermal':3,'yinXu':2,'bloodDeficiency':3,'qiXu':2,'wet':3,'jingKui':1,'jinKui':3}";//热 +3 阴虚 +2 血虚 +3 气虚 +2 湿 +3 精亏 +1 津亏 +3
    public static final String TCM_ELEMENT_TONGUE_BODY_AGED = "{'bloodStasis':8,'qiZhi':8,'phlegm':8,'wet':8}";//血瘀 +8 气滞 +8 痰 +8 湿 +8
    public static final String TCM_ELEMENT_TONGUE_BODY_YOUNG = "{'bloodStasis':-6,'yinXu':8,'phlegm':-8,'bloodDeficiency':8,'qiXu':8,'wet':-8,'yangXu':8}";//血瘀 -6 阴虚 +8  痰 -8 血虚 +8 气虚 +8 湿 -8 阳虚 +8
    public static final String TCM_ELEMENT_TONGUE_BODY_SWELL = "{'bloodStasis':5,'thermal':5,'wet':5,'spleen':4,'heart':5}";//血瘀 +5 热 +5 湿 +5 脾 +4 心 +5
    public static final String TCM_ELEMENT_TCM_TONGUE_BODY_PIMPLE = "{'bloodStasis':2,'qiZhi':2,'thermal':3,'bloodHeat':4,'qiXu':1,'stomach':2,'liver':1,'spleen':1,'gallbladder':1,'largeIntestine':1,'mind':1,'heart':2}";//血瘀 +2 气滞 +2 热 +3 血热 +4 气虚 +1 胃 +2 肝 +1 脾 +1 胆 +1 大肠 +1 心神 +1 心 +2


    //苔色 1：黑苔、2：黑白相间苔、3：燥苔、4：染苔、5：灰黑苔、6：润苔、7：焦黄苔、8：厚苔、9：薄苔、10：白苔、11：黄苔、12：淡黄苔、13：黄白相间苔
    public static final int TCM_COATING_COLOR_BLACK = 1;
    public static final int TCM_COATING_COLOR_BLACK_WHITE = 2;
    public static final int TCM_COATING_COLOR_DRY = 3;
    public static final int TCM_COATING_COLOR_STAINED = 4;
    public static final int TCM_COATING_COLOR_GRAY_BLACK = 5;
    public static final int TCM_COATING_COLOR_MOIST = 6;
    public static final int TCM_COATING_COLOR_BROWN = 7;
    public static final int TCM_COATING_COLOR_THICK = 8;
    public static final int TCM_COATING_COLOR_THIN = 9;
    public static final int TCM_COATING_COLOR_WHITE = 10;
    public static final int TCM_COATING_COLOR_YELLOW = 11;
    public static final int TCM_COATING_COLOR_LIGHT_YELLOW = 12;
    public static final int TCM_COATING_COLOR_YELLOW_WHITE = 13;
    public static final String TCM_ELEMENT_COATING_COLOR_GRAY_BLACK = "{'thermal':14,'yinXu':10,'yangXu':13,'jinKui':7}";//热 +14 阴虚 +10 阳虚 +13 津亏 +7
    public static final String TCM_ELEMENT_COATING_COLOR_YELLOW = "{'thermal':8,'yinXu':5}";//热 +8 阴虚 +5
    public static final String TCM_ELEMENT_COATING_COLOR_LIGHT_YELLOW = "{'thermal':5, 'yinXu':3}";//热 +5 阴虚 +3
    public static final String TCM_ELEMENT_COATING_COLOR_YELLOW_WHITE = "{'thermal':2}";//热 +2

    //苔质 1；薄、2：厚、3：腐、4：腻、5：燥、6：焦、7：积粉、8：滑、9：剥、10：类剥
    public static final int TCM_TONGUE_FUR_THIN = 1;
    public static final int TCM_TONGUE_FUR_THICK = 2;
    public static final int TCM_TONGUE_FUR_ROTTEN = 3;
    public static final int TCM_TONGUE_FUR_GREASY = 4;
    public static final int TCM_TONGUE_FUR_DRY = 5;
    public static final int TCM_TONGUE_FUR_BURNT = 6;
    public static final int TCM_TONGUE_FUR_POWDER = 7;
    public static final int TCM_TONGUE_FUR_SLIDE = 8;
    public static final int TCM_TONGUE_FUR_PEEL = 9;
    public static final int TCM_TONGUE_FUR_PELL_LIKE = 10;
    public static final String TCM_ELEMENT_TONGUE_FUR_THIN = "{'phlegm':-6,'wet':-6}";//痰 -6 湿 -6
    public static final String TCM_ELEMENT_TONGUE_FUR_THICK = "{'phlegm':8, 'wet':8}";//痰 +8 湿 +8
    public static final String TCM_ELEMENT_TONGUE_FUR_SLIDE = "{'yin':4, 'wet':4, 'jinKui': -3}";//饮 +4 湿 +4 津亏 -3

    //舌态 1：弄舌、2：舌痿、3；舌短、4：舌强、5：舌颤 6:吐舌 7:舌歪
    public static final int TCM_TONGUE_CONDITION_WAGGLE = 1;
    public static final int TCM_TONGUE_CONDITION_FLACCID = 2;
    public static final int TCM_TONGUE_CONDITION_SHORT = 3;
    public static final int TCM_TONGUE_CONDITION_STIFF = 4;
    public static final int TCM_TONGUE_CONDITION_TREMBLE = 5;
    public static final int TCM_TONGUE_CONDITION_STICK_OUT = 6;
    public static final int TCM_TONGUE_CONDITION_WRY = 7;
    public static final String TCM_ELEMENT_TONGUE_CONDITION_WAGGLE = "{'thermal':4, 'wind':4, 'jingKui':5, 'liver':5, 'stomach':5}";//热 +4 动风 +4 精亏 +5 肝 +5 肾 +5
    public static final String TCM_ELEMENT_TONGUE_CONDITION_FLACCID = "{'thermal':3, 'yinXu':5, 'bloodDeficiency':3, 'qiXu':3, 'jingKui':3, 'liver':3, 'kidney':3}";//热 +3 阴虚 +5 血虚 +3 气虚 +3 精亏 +3 肝 +3 肾 +3
    public static final String TCM_ELEMENT_TONGUE_CONDITION_SHORT = "{'thermal':2, 'phlegm':2, 'wind':2, 'bloodDeficiency':2, 'qiXu':2, 'jinKui':2}";//热 +2 痰 +2 动风 +2 血虚 +2 气虚 +2 津亏 +2
    public static final String TCM_ELEMENT_TONGUE_CONDITION_STIFF = "{'thermal':5, 'phlegm':8, 'wind':9, 'jinKui':4, 'mind':4, 'heart':4}";//热 +5 痰 +8 动风 +9 津亏 +4 心神 +4 心 +4
    public static final String TCM_ELEMENT_TONGUE_CONDITION_TREMBLE = "{'thermal':5, 'yinXu':2,'yangKang':2,'wind':8, 'bloodDeficiency':2, 'liver':2}";//热 +5 阴虚 +2 阳亢 +2 动风 +8 血虚 +2 肝 +2
    public static final String TCM_ELEMENT_TONGUE_CONDITION_STICK_OUT = "{'thermal':5, 'spleen':4, 'heart':4}";//热 +5 脾 +4 心 +4
    public static final String TCM_ELEMENT_TONGUE_CONDITION_WRY = "{'bloodStasis':5,'phlegm':10,'wind':8}";//血瘀 +5 痰 + 10 动风 +8

    public static final String TCM_ELEMENT_PRO_DISEASE = "{'bloodStasis':119,'qiZhi':139,'thermal':155,'bloodHeat':30,'yin':30,'yinXu':120,'yangKang':54,'phlegm':163,'wind':32,'bloodDeficiency':90,'qiXu':160,'wet':140,'jingKui':98,'yangXu':150,'jinKui':106,'stomach':88,'liver':165,'spleen':118,'smallIntestine':13,'gallbladder':110,'kidney':153,'largeIntestine':52,'mind':88,'bladder':56,'lung':115,'heart':110}";
    public static final String TCM_ELEMENT_DISEASE = "{'bloodStasis':202,'qiZhi':238,'thermal':311,'bloodHeat':44,'yin':43,'yinXu':241,'yangKang':80,'phlegm':276,'wind':47,'bloodDeficiency':156,'qiXu':278,'wet':234,'jingKui':153,'yangXu':250,'jinKui':157,'stomach':148,'liver':281,'spleen':236,'smallIntestine':33,'gallbladder':155,'kidney':268,'largeIntestine':80,'mind':128,'bladder':76,'lung':195,'heart':162}";



    public static final String[] HEAVENLY =  {"癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬"};
    public static final String[] EARTHLY = {"亥", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌"};

    public static final String[] JIA = {"甲子","乙丑","丙寅","丁卯","戊辰","己巳","庚午","辛未","壬申","癸酉","甲戌","乙亥","丙子","丁丑","戊寅","己卯","庚辰","辛巳","壬午","癸未","甲申","乙酉","丙戌","丁亥","戊子","己丑","庚寅","辛卯","壬辰","癸巳","甲午","乙未","丙申","丁酉","戊戌","己亥","庚子","辛丑","壬寅","癸卯","甲辰","乙巳","丙午","丁未","戊申","己酉","庚戌","辛亥","壬子","癸丑","甲寅","乙卯","丙辰","丁巳","戊午","己未","庚申","辛酉","壬戌","癸亥"};
    public static final String[] NIAN_YUN = {"太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及","太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及","太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及","太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及","太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及","太阴湿土太过","阳明燥金不及","太阳寒水太过","厥阴风木不及","少阴君火太过","太阴湿土不及","阳明燥金太过","太阳寒水不及","厥阴风木太过","少阴君火不及"};
    public static final String[] SI_TIAN = {"少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木"};
    public static final String[] ZAI_QUAN = {"阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火","阳明燥金","太阳寒水","厥阴风木","少阴君火","太阴湿土","少阳相火"};
    public static final String[] ZHU_QI = {"厥阴风木", "少阴君火", "少阳相火", "太阴湿土", "阳明燥金", "太阳寒水"};

    public static final String[][] KE_QI = {
            {"太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金"},
            {"厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水"},
            {"少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木"},
            {"太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火"},
            {"少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土"},
            {"阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金", "太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火"},
    };

    public static final String[] BIRTH_TEXT = {"心火燔热，肝风肺燥偏胜，脾肾寒湿体质，脾肾肝肺心易受邪感病，发病后易出现上风火燥中湿下寒的疾病病机特点，其中脾湿偏胜是病机关键",
            "心肺燥热，肝风偏胜，脾肾阳虚寒湿体质，肺肝心肾脾五脏易受邪感病，发病后易出现上燥火风中湿下寒的疾病病机特点，其中燥金不及是病机关键",
            "肝肺风燥偏胜，心阳被郁，脾肾寒湿体质。肾脾心三焦胆肝肺易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点。其中阳虚寒凝是病机关键",
            "心火燔热、肺燥偏胜、肝郁脾湿体质，肝肺心脾四脏易受邪感病，发病后易出现上燥火中湿郁的疾病病机特点",
            "心火燔热，肺金燥热，脾肾阳虚寒湿体质，心肺肾脾四脏易受邪感病，发病后易出现上燥火中湿下寒的疾病病机特点",
            "风火燥盛，脾肾阳虚湿寒体质。脾肝肾肺三焦胆易受邪感病，发病后上半年易出现上风火燥中湿下寒的疾病病机特点。",
            "心肺燥热，肝失疏泄体质，肺心肝三脏易受邪感病，发病后易出现上燥热中郁的疾病病机特点，其中肺燥偏胜是病机关键。",
            "心火肝风偏胜，脾肾阳虚寒湿体质，肾脾心肝易受邪感病，发病后易出现上风热中湿下寒的疾病病机特点，其中脾肾阳虚寒凝是病机关键，",
            "肝风肺燥内热偏胜，脾虚湿盛体质，肝脾肺三焦胆易受邪感病，发病后易出现上风火燥中湿的疾病病机特点，其中木旺乘脾是病机关键",
            "心阳不足，肺燥偏胜，脾肾阳虚寒湿体质，心肺脾肾肝易受邪感病，发病后易出现上燥寒中湿郁下寒的疾病病机特点，其中心阳不足、肺燥偏胜是病机关键",
            "肝风偏胜，心阳被郁，脾肾寒湿体质，脾肝肾心四脏易受邪感病，发病后易出现上风寒中湿下寒的疾病病机特点，其中脾湿偏胜是病机关键",
            "肝风肺燥，内热炽盛，脾肾寒湿体质，肺肝心三焦胆肾脾易受邪感病，发病后易出现肝风肺燥，内热炽盛，脾肾寒湿的疾病病机特点",
            "心阳被抑，肺金燥热，肝郁脾湿，肾阳虚寒体质。肾心脾肺肝易受邪感病，发病后易出现上燥寒中湿郁下寒的疾病病机特点",
            "肺燥心火偏胜，木郁土壅肾寒体质。肝脾肺心肾易受邪感病，发病后易出现上燥火中湿下寒的疾病病机特点",
            "心三焦胆火偏胜、肝风肺燥，脾肾湿寒体质。心三焦胆肺肾肝脾易受邪感病，发病后易出现上火风燥中湿下寒的疾病病机特点",
            "肝风肺燥，心火燔热，脾肾阳虚寒湿体质。脾肝肺肾心五脏易受邪感病，发病易出现上风火燥中湿下寒的疾病病机特点，",
            "心肺燥热，脾湿肝郁，肾阳虚寒凝体质，肺肝心肾脾易受邪感病，发病后易出现上燥热中湿郁下寒的疾病病机特点，其中肺燥偏胜是病机关键，",
            "心三焦胆火热淫胜，肝风肺燥偏胜，脾肾寒湿体质，肾肝心三焦胆脾肺易受邪感病，发病后易出现上热风燥中湿下寒的疾病病机特点，其中肾水不足是病机关键，",
            "心火燔热，肝风肺燥偏胜，脾虚湿盛体质，肝肺脾心易受邪感病，发病后易出现上风火燥中湿的疾病病机特点，其中肝风太过是病机关键，",
            "心阳不足，肺燥偏胜，脾虚湿盛，肾阳虚寒，体质，心肺脾肾肝易受邪感病，发病后易出现上燥寒中湿下寒的疾病病机特点，其中心阳不足、脾虚湿盛是病机关键",
            "三焦胆火热淫胜，肝风肺燥偏胜，脾肾阳虚寒湿体质，脾肝肾肺三焦胆易受邪感病，发病后易出现上风火燥中湿下寒的疾病病机特点，脾湿偏胜是病机关键",
            "心肺燥热，肝风偏胜，肾阳虚寒体质，肺心肾肝四脏易受邪感病，发病后易出现上燥火风下寒的疾病病机特点，其中心肺燥热是病机关键",
            "心阳被郁，脾肾寒湿体质。肾心脾三脏易受邪感病，发病后容易出现上寒中湿下寒的疾病病机特点。",
            "火热肺燥偏胜，肝郁脾虚体质，肝心三焦胆肺脾易受邪感病，发病后易出现上燥火，中湿郁的疾病病机特点。",
            "心肺燥热，肝气郁滞，肾阳虚寒体质。心肺肾肝四脏易受邪感病，发病后易出现上燥火中郁下寒的疾病病机特点，其中心火燔热是病机关键，",
            "肝风肺燥偏胜，心火被抑，脾肾阳虚湿寒大行。脾肝肺肾心易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点。",
            "肺燥三焦胆火旺、木郁土壅体质，肺心三焦胆肝脾易受邪感病，发病后易出现上燥热中湿郁的疾病病机特点，其中肺燥偏胜与内热燔胜是病机关键",
            "心火燔热，肝风肺燥偏胜，脾肾寒湿体质，肾心脾肝肺易受邪感病，发病后易出现上火风燥中湿下寒的疾病病机特点，其中肾水不足是病机关键",
            "心阳被抑，肝风肺燥偏胜，脾肾阳虚寒湿体质，肝肺脾肾心易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点，其中肝风太过是病机关键",
            "心火不足，肝风肺燥偏胜，脾肾寒湿体质，心三焦胆肾肺脾肝易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点，其中心火不足是病机关键",
            "心火燔热，肝风肺燥偏胜，脾肾寒湿体质，脾肾肝肺心易受邪感病，发病后易出现上风火燥中湿下寒的疾病病机特点，其中脾湿偏胜是病机关键",
            "心肺燥热，肝风偏胜，脾肾阳虚寒湿体质，肺肝心肾脾五脏易受邪感病，发病后易出现上燥火风中湿下寒的疾病病机特点，其中燥金不及是病机关键",
            "肝肺风燥偏胜，心阳被郁，脾肾寒湿体质。肾脾心三焦胆肝肺易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点。其中阳虚寒凝是病机关键",
            "心火燔热、肺燥偏胜、肝郁脾湿体质，肝肺心脾四脏易受邪感病，发病后易出现上燥火中湿郁的疾病病机特点",
            "心火燔热，肺金燥热，脾肾阳虚寒湿体质，心肺肾脾四脏易受邪感病，发病后易出现上燥火中湿下寒的疾病病机特点。",
            "风火燥盛，脾肾阳虚湿寒体质。脾肝肾肺三焦胆易受邪感病，发病后上半年易出现上风火燥中湿下寒的疾病病机特点。",
            "心肺燥热，肝失疏泄体质，肺心肝三脏易受邪感病，发病后易出现上燥热中郁的疾病病机特点，其中肺燥偏胜是病机关键。",
            "心火肝风偏胜，脾肾阳虚寒湿体质，肾脾心肝易受邪感病，发病后易出现上风热中湿下寒的疾病病机特点，其中脾肾阳虚寒凝是病机关键，",
            "肝风肺燥内热偏胜，脾虚湿盛体质，肝脾肺三焦胆易受邪感病，发病后易出现上风火燥中湿的疾病病机特点，其中木旺乘脾是病机关键",
            "心阳不足，肺燥偏胜，脾肾阳虚寒湿体质，心肺脾肾肝易受邪感病，发病后易出现上燥寒中湿郁下寒的疾病病机特点，其中心阳不足、肺燥偏胜是病机关键",
            "肝风偏胜，心阳被郁，脾肾寒湿体质，脾肝肾心四脏易受邪感病，发病后易出现上风寒中湿下寒的疾病病机特点，其中脾湿偏胜是病机关键",
            "肝风肺燥，内热炽盛，脾肾寒湿体质，肺肝心三焦胆肾脾易受邪感病，发病后易出现肝风肺燥，内热炽盛，脾肾寒湿的疾病病机特点。",
            "心阳被抑，肺金燥热，肝郁脾湿，肾阳虚寒体质。肾心脾肺肝易受邪感病，发病后易出现上燥寒中湿郁下寒的疾病病机特点。",
            "肺燥心火偏胜，木郁土壅肾寒体质。肝脾肺心肾易受邪感病，发病后易出现上燥火中湿下寒的疾病病机特点",
            "心三焦胆火偏胜、肝风肺燥，脾肾湿寒体质。心三焦胆肺肾肝脾易受邪感病，发病后易出现上火风燥中湿下寒的疾病病机特点。",
            "肝风肺燥，心火燔热，脾肾阳虚寒湿体质。脾肝肺肾心五脏易受邪感病，发病易出现上风火燥中湿下寒的疾病病机特点，",
            "心肺燥热，脾湿肝郁，肾阳虚寒凝体质，肺肝心肾脾易受邪感病，发病后易出现上燥热中湿郁下寒的疾病病机特点，其中肺燥偏胜是病机关键，",
            "心三焦胆火热淫胜，肝风肺燥偏胜，脾肾寒湿体质，肾肝心三焦胆脾肺易受邪感病，发病后易出现上热风燥中湿下寒的疾病病机特点，其中肾水不足是病机关键，",
            "心火燔热，肝风肺燥偏胜，脾虚湿盛体质，肝肺脾心易受邪感病，发病后易出现上风火燥中湿的疾病病机特点，其中肝风太过是病机关键，",
            "心阳不足，肺燥偏胜，脾虚湿盛，肾阳虚寒，体质，心肺脾肾肝易受邪感病，发病后易出现上燥寒中湿下寒的疾病病机特点，其中心阳不足、脾虚湿盛是病机关键",
            "三焦胆火热淫胜，肝风肺燥偏胜，脾肾阳虚寒湿体质，脾肝肾肺三焦胆易受邪感病，发病后易出现上风火燥中湿下寒的疾病病机特点，脾湿偏胜是病机关键",
            "心肺燥热，肝风偏胜，肾阳虚寒体质，肺心肾肝四脏易受邪感病，发病后易出现上燥火风下寒的疾病病机特点，其中心肺燥热是病机关键",
            "心阳被郁，脾肾寒湿体质。肾心脾三脏易受邪感病，发病后容易出现上寒中湿下寒的疾病病机特点。",
            "火热肺燥偏胜，肝郁脾虚体质，肝心三焦胆肺脾易受邪感病，发病后易出现上燥火，中湿郁的疾病病机特点。",
            "心肺燥热，肝气郁滞，肾阳虚寒体质。心肺肾肝四脏易受邪感病，发病后易出现上燥火中郁下寒的疾病病机特点，其中心火燔热是病机关键，",
            "肝风肺燥偏胜，心火被抑，脾肾阳虚湿寒大行。脾肝肺肾心易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点。",
            "肺燥三焦胆火旺、木郁土壅体质，肺心三焦胆肝脾易受邪感病，发病后易出现上燥热中湿郁的疾病病机特点，其中肺燥偏胜与内热燔胜是病机关键",
            "心火燔热，肝风肺燥偏胜，脾肾寒湿体质，肾心脾肝肺易受邪感病，发病后易出现上火风燥中湿下寒的疾病病机特点，其中肾水不足是病机关键",
            "心阳被抑，肝风肺燥偏胜，脾肾阳虚寒湿体质，肝肺脾肾心易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点，其中肝风太过是病机关键",
            "心火不足，肝风肺燥偏胜，脾肾寒湿体质，心三焦胆肾肺脾肝易受邪感病，发病后易出现上风燥寒中湿下寒的疾病病机特点，其中心火不足是病机关键"};

    public static final Map<String, String> healthElement = new HashMap<String, String>(){
        {
            put("bloodStasis","血瘀");
            put("qiZhi","气滞");
            put("thermal","热");
            put("bloodHeat","血热");
            put("yin","饮");
            put("yinXu","阴虚");
            put("yangKang","阳亢");
            put("phlegm","痰");
            put("wind","动风");
            put("bloodDeficiency","血虚");
            put("qiXu","气虚");
            put("wet","湿");
            put("jingKui","精亏");
            put("yangXu","阳虚");
            put("jinKui","津亏");
            put("stomach","胃");
            put("liver","肝");
            put("spleen","脾");
            put("smallIntestine","小肠");
            put("gallbladder","胆");
            put("kidney","肾");
            put("largeIntestine","大肠");
            put("mind","心神");
            put("bladder","膀胱");
            put("lung","肺");
            put("heart","心");
        }
    };

}
