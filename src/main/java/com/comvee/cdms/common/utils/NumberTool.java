/**
 * @File name:   NumberTool.java   数字处理相关工具类
 * @Create on:   2017年1月12日
 * @Author   :  占铃树
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/
package com.comvee.cdms.common.utils;

import java.util.HashMap;

/**
 * @author 占铃树
 */
public class NumberTool {

    /**
     * 中文转汉字
     * 
     * @param num
     * @return
     * @author 占铃树
     * @date 2017年1月12日
     */
    public static String numToChinese(int num) {
        int source = num;
        if (num == 0) {
            return "零";
        }
     // 节权位标识
        int unitPos = 0;
        String all = new String();
     // 中文数字字符串
        String chineseNum = new String();
     // 下一小结是否需要补零
        boolean needZero = false;
        String strIns = new String();
        while (num > 0) {
        	// 取最后面的那一个小节
            int section = num % 10000;
            // 判断上一小节千位是否为零，为零就要加上零
            if (needZero) {
                all = Tool.chnNumChar[0] + all;
            }
         // 处理当前小节的数字,然后用chineseNum记录当前小节数字
            chineseNum = sectionToChinese(section, chineseNum);
         // 此处用if else 选择语句来执行加节权位
            if (section != 0) {
            	// 当小节不为0，就加上节权位
                strIns = Tool.chnUnitSection[unitPos];
                chineseNum = chineseNum + strIns;
            }
            else {
            	// 否则不用加
                strIns = Tool.chnUnitSection[0];
                chineseNum = strIns + chineseNum;
            }
            all = chineseNum + all;
            chineseNum = "";
            needZero = (section < 1000) && (section > 0);
            num = num / 10000;
            unitPos++;
        }
        
        // 两位数 10~20之间的值 处理第一位
        int int10 = 10;
		int int20 = 20;
		if(int10 <= source && source < int20) {
            all = all.substring(1, all.length());
        }
        
        return all;
    }

    public static String sectionToChinese(int section, String chineseNum) {
    	// 小节部分用独立函数操作
        String setionChinese = new String();
     // 小节内部的权值计数器
        int unitPos = 0;
     // 小节内部的制零判断，每个小节内只能出现一个零
        boolean zero = true;
        while (section > 0) {
        	// 取当前最末位的值
            int v = section % 10;
            if (v == 0) {
                if (!zero) {
                	// 需要补零的操作，确保对连续多个零只是输出一个
                    zero = true;
                    chineseNum = Tool.chnNumChar[0] + chineseNum;
                }
            }
            else {
            	// 有非零的数字，就把制零开关打开
                zero = false;
             // 对应中文数字位
                setionChinese = Tool.chnNumChar[v];
             // 对应中文权位
                setionChinese = setionChinese + Tool.chnUnitChar[unitPos];
                chineseNum = setionChinese + chineseNum;
            }
            unitPos++;
            section = section / 10;
        }

        return chineseNum;
    }

    public static class Tool {
        /**
         * 数字位
         */
        public static String[] chnNumChar = { "零", "一", "二", "三", "四", "五",
                "六", "七", "八", "九" };
        public static char[] chnNumChinese = { '零', '一', '二', '三', '四', '五',
                '六', '七', '八', '九' };
        /**
         * 节权位
         */
        public static String[] chnUnitSection = { "", "万", "亿", "万亿" };
        /**
         * 权位
         */
        public static String[] chnUnitChar = { "", "十", "百", "千" };
        public static HashMap<Character, Integer> intList = new HashMap<Character, Integer>();

        static {
            for (int i = 0; i < chnNumChar.length; i++) {
                intList.put(chnNumChinese[i], i);
            }
            intList.put('十', 10);
            intList.put('百', 100);
            intList.put('千', 1000);
        }
    }
}
