package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.*;

/** 糖尿病足随访表智能建议
 * @author wyc
 * @date 2019/9/29 10:38
 */
public class FollowTnbzHelper {
    public static Map<String,Object> outFollowTnbz(Map<String, Object> followBodyMap){
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






        //创面
        String cmWt = "";  //创面存在问题
        String cmLeft = "";  //左脚创面位置
        String cmRght = ""; //右脚创面位置
        if (null != followBodyMap.get("cmwz_l") && !StringUtils.isBlank(followBodyMap.get("cmwz_l").toString())){
            String cmwzL = followBodyMap.get("cmwz_l").toString();
            cmLeft = cmwzL.replace("2", "足趾").replace("3", "足体")
                    .replace("4", "足跟").replace("5", "踝").replace("6", "小腿");
        }
        if (null != followBodyMap.get("cmwz_r") && !StringUtils.isBlank(followBodyMap.get("cmwz_r").toString())){
            String cmwzR = followBodyMap.get("cmwz_r").toString();
            cmRght = cmwzR.replace("2", "足趾").replace("3", "足体")
                    .replace("4", "足跟").replace("5", "踝").replace("6", "小腿");

        }
        if (!"".equals(cmLeft) && !"".equals(cmRght)){
            cmWt = "左足：在“"+cmLeft+"”位置存在创面。右足：在“"+cmRght+"”位置存在创面；\r\n";
        }else {
            if (!"".equals(cmLeft) && "".equals(cmRght)){
                cmWt = "左足：在“"+cmLeft+"”位置存在创面；\r\n";
            }else if ("".equals(cmLeft) && !"".equals(cmRght)){
                cmWt += "右足：在“"+cmRght+"”位置存在创面；\r\n";
            }
        }
//        if (!"".equals(cmWt)){
//            wtList.add(cmWt);
//            gjSet.add(1);
//            mbSet.add(12);
//        }

        //缺血性溃疡
        String qxxky = "";
        if (null != followBodyMap.get("qxxky") && !StringUtils.isBlank(followBodyMap.get("qxxky").toString())){
            qxxky = followBodyMap.get("qxxky").toString();
            //1:无 2:皮肤或皮下组织  3:肌肉及结缔组织
            if ("2".equals(qxxky)){
                String wt = "缺血性溃疡（皮肤或皮下组织）；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                mbSet.add(12);
            }else if ("3".equals(qxxky)){
                String wt = "缺血性溃疡（肌肉及结缔组织）；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                mbSet.add(12);
            }
        }

        boolean flag = checkCmAndKy(cmLeft, cmRght, qxxky); //判断是否有创面或溃疡
        //感染症状
        String grzz = "";
        if (null != followBodyMap.get("hb") && !StringUtils.isBlank(followBodyMap.get("hb").toString())){
            String hb = followBodyMap.get("hb").toString();
            if ("2".equals(hb)){
                String wt = "有感染症状-红斑；\r\n";
                wtList.add(wt);
                grzz += "红、";
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }

            }
        }
        if (null != followBodyMap.get("zz") && !StringUtils.isBlank(followBodyMap.get("zz").toString())){
            String zz = followBodyMap.get("zz").toString();
            if ("2".equals(zz)){
                String wt = "有感染症状-肿胀；\r\n";
                wtList.add(wt);
                grzz += "肿、";
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        if (null != followBodyMap.get("rg") && !StringUtils.isBlank(followBodyMap.get("rg").toString())){
            String rg = followBodyMap.get("rg").toString();
            if ("2".equals(rg)){
                String wt = "有感染症状-热感；\r\n";
                wtList.add(wt);
                grzz += "热、";
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        if (null != followBodyMap.get("tt") && !StringUtils.isBlank(followBodyMap.get("tt").toString())){
            String tt = followBodyMap.get("tt").toString();
            if ("2".equals(tt)){
                String wt = "有感染症状-疼痛；\r\n";
                wtList.add(wt);
                grzz += "痛、";
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        String gjGrzz = "";
        if (!"".equals(grzz)){
//            String gj = grzz.substring(0,grzz.length()-1)+" 是身体组织局部有炎症的症状表现之一，提示可能存在感染的情况，建议立即专科诊治；\r\n";
//            gjList.add(gj);
            gjGrzz = grzz.substring(0,grzz.length()-1);
            gjSet.add(112);
        }
        //缺血性坏疽
        if (null != followBodyMap.get("qxxhz") && !StringUtils.isBlank(followBodyMap.get("qxxhz").toString())){
            String qxxhz = followBodyMap.get("qxxhz").toString();
            //1:无 2:局部坏疽  3:全足坏疽
            if ("2".equals(qxxhz)){
                String wt = "局部坏疽；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(qxxhz)){
                String wt = "全足坏疽；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //缺血性静息痛
        if (null != followBodyMap.get("qxxjxt") && !StringUtils.isBlank(followBodyMap.get("qxxjxt").toString())){
            String qxxjxt = followBodyMap.get("qxxjxt").toString();
            //1:无 2:有
            if ("2".equals(qxxjxt)){
                String wt = "有缺血性静息痛；\r\n";
                wtList.add(wt);
                gjSet.add(3);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        //间歇性跛行
        if (null != followBodyMap.get("jxxbx") && !StringUtils.isBlank(followBodyMap.get("jxxbx").toString())){
            String jxxbx = followBodyMap.get("jxxbx").toString();
            // 1 : 无  2 : 轻度 3 : 中度  4 : 重度
            if ("2".equals(jxxbx)){
                String wt = "（轻度）间歇性跛行；\r\n";
                wtList.add(wt);
                gjSet.add(4);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jxxbx)){
                String wt = "（中度）间歇性跛行；\r\n";
                wtList.add(wt);
                gjSet.add(4);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("4".equals(jxxbx)){
                String wt = "（重度）间歇性跛行；\r\n";
                wtList.add(wt);
                gjSet.add(4);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        //经皮氧分压 1 : 正常 2 : 轻度异常 3 : 重度异常  4 : 未查
        String jpyLeft = "";
        String jpyRight = "";
        if (null != followBodyMap.get("jpyfy") && !StringUtils.isBlank(followBodyMap.get("jpyfy").toString())){
            jpyLeft = followBodyMap.get("jpyfy").toString();
        }
        if (null != followBodyMap.get("jpyfy_r") && !StringUtils.isBlank(followBodyMap.get("jpyfy_r").toString())){
            jpyRight = followBodyMap.get("jpyfy_r").toString();
        }
        if ("".equals(jpyLeft)){
            if ("2".equals(jpyRight)){
                String wt = "右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(42);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jpyRight)){
                String wt = "右足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(43);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("1".equals(jpyLeft)){
            if ("2".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(38);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(39);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("2".equals(jpyLeft)){
            if ("".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(28);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-正常；\r\n";
                wtList.add(wt);
                gjSet.add(30);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(31);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(32);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("4".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-未查；\r\n";
                wtList.add(wt);
                gjSet.add(33);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("3".equals(jpyLeft)){
            if ("".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(29);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-正常；\r\n";
                wtList.add(wt);
                gjSet.add(34);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(35);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(36);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("4".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-未查；\r\n";
                wtList.add(wt);
                gjSet.add(37);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("4".equals(jpyLeft)){
            if ("2".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-未查。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
                wtList.add(wt);
                gjSet.add(40);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(jpyRight)){
                String wt = "左足：经皮氧分压（TCPO2）-未查。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
                wtList.add(wt);
                gjSet.add(41);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }


        //足动脉搏动减弱  1:无 2:减弱  3:正常
        String dmJr = ""; //足动脉搏动减弱类型
        String dmXs = ""; //足动脉搏动消失类型
        //髂动脉
        if (null != followBodyMap.get("kdm") && !StringUtils.isBlank(followBodyMap.get("kdm").toString())){
            String kdm = followBodyMap.get("kdm").toString();
            if ("1".equals(kdm)){
                dmXs += "髂动脉，";
                String wt = "足动脉搏动：髂动脉消失；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(kdm)){
                dmJr += "髂动脉，";
                String wt = "足动脉搏动：髂动脉减弱；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        //腘动脉
        if (null != followBodyMap.get("gdm") && !StringUtils.isBlank(followBodyMap.get("gdm").toString())){
            String gdm = followBodyMap.get("gdm").toString();
            if ("1".equals(gdm)){
                dmXs += "腘动脉，";
                String wt = "足动脉搏动：腘动脉消失；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(gdm)){
                dmJr += "腘动脉，";
                String wt = "足动脉搏动：腘动脉减弱；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        //足背动脉
        if (null != followBodyMap.get("zbdm") && !StringUtils.isBlank(followBodyMap.get("zbdm").toString())){
            String zbdm = followBodyMap.get("zbdm").toString();
            if ("1".equals(zbdm)){
                dmXs += "足背动脉，";
                String wt = "足动脉搏动：足背动脉消失；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(zbdm)){
                dmJr += "足背动脉，";
                String wt = "足动脉搏动：足背动脉减弱；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        //胫后动脉
        if (null != followBodyMap.get("jhdm") && !StringUtils.isBlank(followBodyMap.get("jhdm").toString())){
            String jhdm = followBodyMap.get("jhdm").toString();
            if ("1".equals(jhdm)){
                dmXs += "胫后动脉，";
                String wt = "足动脉搏动：胫后动脉消失；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(jhdm)){
                dmJr += "胫后动脉，";
                String wt = "足动脉搏动：胫后动脉减弱；\r\n";
                wtList.add(wt);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }
        String gjDmJr = "";
        String gjDmXs = "";
        if (!"".equals(dmJr)){
//            String gj = "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足动脉检查（"+dmJr.substring(0,dmJr.length()-1)+"）有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n";
//            gjList.add(gj);
            gjDmJr = dmJr.substring(0,dmJr.length()-1);
            gjSet.add(113);
        }
        if (!"".equals(dmXs)){
//            String gj = "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足动脉检查（"+dmXs.substring(0,dmXs.length()-1)+"）搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n";
//            gjList.add(gj);
            gjDmXs = dmXs.substring(0,dmXs.length()-1);
            gjSet.add(114);
        }

        //皮温下降 1:无 2:有
        if (null != followBodyMap.get("pwxj") && !StringUtils.isBlank(followBodyMap.get("pwxj").toString())){
            String pwxj = followBodyMap.get("pwxj").toString();
            if ("2".equals(pwxj)){
                String wt = "皮温下降；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //10克尼龙丝触觉检查  1:正常 2:缺失 3:消失
        String nlsLeft = "";
        String nlsRight = "";
        if (null != followBodyMap.get("nlscjjc") && !StringUtils.isBlank(followBodyMap.get("nlscjjc").toString())){
            nlsLeft = followBodyMap.get("nlscjjc").toString();
        }
        if (null != followBodyMap.get("nlscjjc_r") && !StringUtils.isBlank(followBodyMap.get("nlscjjc_r").toString())){
            nlsRight = followBodyMap.get("nlscjjc_r").toString();
        }
        if ("".equals(nlsLeft)){
            if ("2".equals(nlsRight)){
                String wt = "右足：10克尼龙丝触觉检查缺失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(nlsRight)){
                String wt = "右足：10克尼龙丝触觉检查消失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("1".equals(nlsLeft)){
            if ("2".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查正常。右足：10克尼龙丝触觉检查缺失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查正常。右足：10克尼龙丝触觉检查消失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("2".equals(nlsLeft)){
            if ("".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查缺失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查正常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查缺失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查消失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("3".equals(nlsLeft)){
            if ("".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查消失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查正常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查缺失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("3".equals(nlsRight)){
                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查消失；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //踝反射检查：1:正常 2:异常
        String hfsLeft = "";
        String hfsRight = "";
        if (null != followBodyMap.get("hfsjc") && !StringUtils.isBlank(followBodyMap.get("hfsjc").toString())){
            hfsLeft = followBodyMap.get("hfsjc").toString();
        }
        if (null != followBodyMap.get("hfsjc_r") && !StringUtils.isBlank(followBodyMap.get("hfsjc_r").toString())){
            hfsRight = followBodyMap.get("hfsjc_r").toString();
        }
        if ("".equals(hfsLeft)){
            if ("2".equals(hfsRight)){
                String wt = "右足：踝反射检查异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("1".equals(hfsLeft)){
            if ("2".equals(hfsRight)){
                String wt = "左足：踝反射检查正常。右足：踝反射检查异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("2".equals(hfsLeft)){
            if ("".equals(hfsRight)){
                String wt = "左足：踝反射检查异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(hfsRight)){
                String wt = "左足：踝反射检查异常。右足：踝反射检查正常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(hfsRight)){
                String wt = "左足：踝反射检查异常。右足：踝反射检查异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //凉、温度觉  1:正常 2:异常
        String lwdjLeft = "";
        String lwdjRight = "";
        if (null != followBodyMap.get("lwdj") && !StringUtils.isBlank(followBodyMap.get("lwdj").toString())){
            lwdjLeft = followBodyMap.get("lwdj").toString();
        }
        if (null != followBodyMap.get("lwdj_r") && !StringUtils.isBlank(followBodyMap.get("lwdj_r").toString())){
            lwdjRight = followBodyMap.get("lwdj_r").toString();
        }
        if ("".equals(lwdjLeft)){
            if ("2".equals(lwdjRight)){
                String wt = "右足：凉、温度觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("1".equals(lwdjLeft)){
            if ("2".equals(lwdjRight)){
                String wt = "左足：凉、温度觉正常。右足：凉、温度觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("2".equals(lwdjLeft)){
            if ("".equals(lwdjRight)){
                String wt = "左足：凉、温度觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(lwdjRight)){
                String wt = "左足：凉、温度觉异常。右足：凉、温度觉正常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(lwdjRight)){
                String wt = "左足：凉、温度觉异常。右足：凉、温度觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //痛觉 1:正常 2:异常
        String tjLeft = "";
        String tjRight = "";
        if (null != followBodyMap.get("tj") && !StringUtils.isBlank(followBodyMap.get("tj").toString())){
            tjLeft = followBodyMap.get("tj").toString();
        }
        if (null != followBodyMap.get("tj_r") && !StringUtils.isBlank(followBodyMap.get("tj_r").toString())){
            tjRight = followBodyMap.get("tj_r").toString();
        }
        if ("".equals(tjLeft)){
            if ("2".equals(tjRight)){
                String wt = "右足：痛觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("1".equals(tjLeft)){
            if ("2".equals(tjRight)){
                String wt = "左足：痛觉正常。右足：痛觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }else if ("2".equals(tjLeft)){
            if ("".equals(tjRight)){
                String wt = "左足：痛觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("1".equals(tjRight)){
                String wt = "左足：痛觉异常。右足：痛觉正常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }else if ("2".equals(tjRight)){
                String wt = "左足：痛觉异常。右足：痛觉异常；\r\n";
                wtList.add(wt);
                gjSet.add(7);
                if (flag){
                    mbSet.add(12);
                }else {
                    mbSet.add(1);
                }
            }
        }

        //创面问题,由于排序问题放在此处
        if (!"".equals(cmWt)){
            wtList.add(cmWt);
            gjSet.add(1);
            mbSet.add(12);
        }

        //白细胞计数
        if (null != followBodyMap.get("bxb") && !StringUtils.isBlank(followBodyMap.get("bxb").toString())){
            double bxb = Double.parseDouble(followBodyMap.get("bxb").toString());
            if (bxb > 10){
                String wt = "白细胞计数增高；\r\n";
                wtList.add(wt);
                gjSet.add(8);
                mbSet.add(2);
            }else if (bxb < 4){
                String wt = "白细胞计数降低；\r\n";
                wtList.add(wt);
                gjSet.add(9);
                mbSet.add(2);
            }
        }

        //降钙素原
        if (null != followBodyMap.get("jgsy") && !StringUtils.isBlank(followBodyMap.get("jgsy").toString())){
            double jgsy = Double.parseDouble(followBodyMap.get("jgsy").toString());
            if (jgsy > 0.05){
                String wt = "降钙素原升高；\r\n";
                wtList.add(wt);
                gjSet.add(10);
                mbSet.add(3);
            }
        }

        //C反应蛋白
        if (null != followBodyMap.get("cfydb") && !StringUtils.isBlank(followBodyMap.get("cfydb").toString())){
            double cfydb = Double.parseDouble(followBodyMap.get("cfydb").toString());
            if (cfydb > 0.8){
                String wt = "C反应蛋白升高；\r\n";
                wtList.add(wt);
                gjSet.add(11);
                mbSet.add(4);
            }
        }

        //血沉
        if (null != followBodyMap.get("xc") && !StringUtils.isBlank(followBodyMap.get("xc").toString())){
            double xc = Double.parseDouble(followBodyMap.get("xc").toString());
            if ("男".equals(sex)){
                if (xc > 15){
                    String wt = "血沉加快；\r\n";
                    wtList.add(wt);
                    gjSet.add(12);
                    mbSet.add(5);
                }
            }else if ("女".equals(sex)){
                if (xc > 20){
                    String wt = "血沉加快；\r\n";
                    wtList.add(wt);
                    gjSet.add(13);
                    mbSet.add(6);
                }
            }
        }

        //血红蛋白浓度
        if (null != followBodyMap.get("xhdb") && !StringUtils.isBlank(followBodyMap.get("xhdb").toString())){
            double xhdb = Double.parseDouble(followBodyMap.get("xhdb").toString());
            if ("男".equals(sex)){
                if (xhdb > 160){
                    String wt = "血红蛋白浓度升高；\r\n";
                    wtList.add(wt);
                    gjSet.add(14);
                    mbSet.add(7);
                }else if (xhdb < 120){
                    String wt = "血红蛋白浓度降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(15);
                    mbSet.add(7);
                }
            }else if ("女".equals(sex)){
                if (xhdb > 150){
                    String wt = "血红蛋白浓度升高；\r\n";
                    wtList.add(wt);
                    gjSet.add(14);
                    mbSet.add(8);
                }else if (xhdb < 110){
                    String wt = "血红蛋白浓度降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(15);
                    mbSet.add(8);
                }
            }
        }

        //白蛋白（ALB）
        if (null != followBodyMap.get("xqbdb") && !StringUtils.isBlank(followBodyMap.get("xqbdb").toString())){
            double xqbdb = Double.parseDouble(followBodyMap.get("xqbdb").toString());
            if (xqbdb > 54){
                String wt = "白蛋白升高；\r\n";
                wtList.add(wt);
                gjSet.add(17);
                mbSet.add(9);
            }else if (xqbdb < 34){
                String wt = "白蛋白降低；\r\n";
                wtList.add(wt);
                gjSet.add(16);
                mbSet.add(9);
            }
        }

        //ABI
        Double abiLeft = null;
        Double abiRight = null;
        if (null != followBodyMap.get("abi_l") && !StringUtils.isBlank(followBodyMap.get("abi_l").toString())){
            abiLeft = Double.parseDouble(followBodyMap.get("abi_l").toString());
        }
        if (null != followBodyMap.get("abi_r") && !StringUtils.isBlank(followBodyMap.get("abi_r").toString())){
            abiRight = Double.parseDouble(followBodyMap.get("abi_r").toString());
        }
        if (null != abiLeft){
            if (abiLeft > 1.3){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(44);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(18);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足动脉钙化,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(45);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足动脉钙化,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(46);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足动脉钙化,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(47);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足动脉钙化,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(48);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足动脉钙化,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(49);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }

            }else if (abiLeft >= 1 && abiLeft <= 1.3) {
                if (null != abiRight) {
                    if (abiRight > 1.3) {
                        String wt = "ABI检查提示：左足正常,右足动脉钙化；\r\n";
                        wtList.add(wt);
                        gjSet.add(50);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    } else if (abiRight >= 0.91 && abiRight <= 0.99) {
                        String wt = "ABI检查提示：左足正常,右足临界值；\r\n";
                        wtList.add(wt);
                        gjSet.add(51);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    } else if (abiRight >= 0.71 && abiRight <= 0.9) {
                        String wt = "ABI检查提示：左足正常,右足轻度动脉狭窄；\r\n";
                        wtList.add(wt);
                        gjSet.add(52);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    } else if (abiRight >= 0.41 && abiRight <= 0.7) {
                        String wt = "ABI检查提示：左足正常,右足中度动脉狭窄；\r\n";
                        wtList.add(wt);
                        gjSet.add(53);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    } else if (abiRight >= 0 && abiRight <= 0.4) {
                        String wt = "ABI检查提示：左足正常,右足重度病变；\r\n";
                        wtList.add(wt);
                        gjSet.add(54);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    }
                }
            }else if (abiLeft >= 0.91 && abiLeft <= 0.99){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(55);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足临界值,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(56);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足临界值,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(57);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(19);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足临界值,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(58);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足临界值,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(59);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足临界值,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(60);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }else if (abiLeft >= 0.71 && abiLeft <= 0.9){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(61);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(62);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(63);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(64);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(20);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(65);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(66);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }else if (abiLeft >= 0.41 && abiLeft <= 0.7){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(67);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(68);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(69);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(70);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(71);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(21);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：左足中度动脉狭窄,右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(72);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }else if (abiLeft >= 0 && abiLeft <=0.4){
                if (null == abiRight){
                    String wt = "ABI检查提示：左足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(73);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight > 1.3){
                    String wt = "ABI检查提示：左足重度病变,右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(74);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 1 && abiRight <= 1.3){
                    String wt = "ABI检查提示：左足重度病变,右足正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(75);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.91 && abiRight <= 0.99){
                    String wt = "ABI检查提示：左足重度病变,右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(76);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.71 && abiRight <= 0.9){
                    String wt = "ABI检查提示：左足重度病变,右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(77);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0.41 && abiRight <= 0.7){
                    String wt = "ABI检查提示：左足重度病变,右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(78);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (abiRight >= 0 && abiRight <= 0.4){
                    String wt = "ABI检查提示：重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(22);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }
        }else {
            if (null != abiRight) {
                if (abiRight > 1.3) {
                    String wt = "ABI检查提示：右足动脉钙化；\r\n";
                    wtList.add(wt);
                    gjSet.add(79);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                } else if (abiRight >= 0.91 && abiRight <= 0.99) {
                    String wt = "ABI检查提示：右足临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(80);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                } else if (abiRight >= 0.71 && abiRight <= 0.9) {
                    String wt = "ABI检查提示：右足轻度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(81);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                } else if (abiRight >= 0.41 && abiRight <= 0.7) {
                    String wt = "ABI检查提示：右足中度动脉狭窄；\r\n";
                    wtList.add(wt);
                    gjSet.add(82);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                } else if (abiRight >= 0 && abiRight <= 0.4) {
                    String wt = "ABI检查提示：右足重度病变；\r\n";
                    wtList.add(wt);
                    gjSet.add(83);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }

        }

        //vpt 0 : <10V(正常)  1 : 10-15V(轻度受损) 2 : 16-25V(中度受损)  3 : >25V(重度受损)
        String vptLeft = "";
        String vptRight = "";
        if(null != followBodyMap.get("zdgjjc") && !StringUtils.isBlank(followBodyMap.get("zdgjjc").toString())){
            vptLeft = followBodyMap.get("zdgjjc").toString();
        }
        if(null != followBodyMap.get("zdgjjc_r") && !StringUtils.isBlank(followBodyMap.get("zdgjjc_r").toString())){
            vptRight = followBodyMap.get("zdgjjc_r").toString();
        }
        if ("".equals(vptLeft)){
            if ("1".equals(vptRight)){
                String wt = "VPT检查提示：右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(84);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(85);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(86);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }
        }else if ("0".equals(vptLeft)){
            if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(106);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(107);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足正常，右足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(108);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }
        }else if ("1".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值；\r\n";
                wtList.add(wt);
                gjSet.add(87);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("0".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足：正常；\r\n";
                wtList.add(wt);
                gjSet.add(109);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：临界值；\r\n";
                wtList.add(wt);
                gjSet.add(23);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足：感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(88);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足临界值，右足：感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(89);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }
        }else if ("2".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(90);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("0".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足：正常；\r\n";
                wtList.add(wt);
                gjSet.add(110);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足：临界值；\r\n";
                wtList.add(wt);
                gjSet.add(91);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(24);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉减退，右足：感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(92);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }
        }else if ("3".equals(vptLeft)){
            if ("".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(93);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("0".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足：正常；\r\n";
                wtList.add(wt);
                gjSet.add(111);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("1".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足：临界值；\r\n";
                wtList.add(wt);
                gjSet.add(94);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("2".equals(vptRight)){
                String wt = "VPT检查提示：左足感觉缺失，右足：感觉减退；\r\n";
                wtList.add(wt);
                gjSet.add(95);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }else if ("3".equals(vptRight)){
                String wt = "VPT检查提示：感觉缺失；\r\n";
                wtList.add(wt);
                gjSet.add(25);
                if (flag){
                    mbSet.add(11);
                }else {
                    mbSet.add(10);
                }
            }
        }

        //TBI >0.7(正常)  0.65-0.7(临界值)  ＜0.65(异常)
        Double tbiLeft = null;
        Double tbiRight = null;
        if (null != followBodyMap.get("tbi_l") && !StringUtils.isBlank(followBodyMap.get("tbi_l").toString())){
            tbiLeft = Double.parseDouble(followBodyMap.get("tbi_l").toString());
        }
        if (null != followBodyMap.get("tbi_r") && !StringUtils.isBlank(followBodyMap.get("tbi_r").toString())){
            tbiRight = Double.parseDouble(followBodyMap.get("tbi_r").toString());
        }
        if (null != tbiLeft){
            if (tbiLeft > 0.7){
                if (null != tbiRight){
                    if (tbiRight >= 0.65 && tbiRight <= 0.7){
                        String wt = "TBI检查提示: 左足：正常，右足：临界值；\r\n";
                        wtList.add(wt);
                        gjSet.add(96);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    }else if (tbiRight < 0.65){
                        String wt = "TBI检查提示: 左足：正常，右足：异常；\r\n";
                        wtList.add(wt);
                        gjSet.add(97);
                        if (flag){
                            mbSet.add(11);
                        }else {
                            mbSet.add(10);
                        }
                    }
                }
            }else if (tbiLeft >= 0.65 && tbiLeft <= 0.7){
                if (null == tbiRight){
                    String wt = "TBI检查提示: 左足：临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(98);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight > 0.7){
                    String wt = "TBI检查提示: 左足：临界值，右足：正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(99);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
                    String wt = "TBI检查提示: 临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(26);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight < 0.65){
                    String wt = "TBI检查提示: 左足：临界值，右足：异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(100);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }else if (tbiLeft < 0.65){
                if (null == tbiRight){
                    String wt = "TBI检查提示: 左足：异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(101);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight > 0.7){
                    String wt = "TBI检查提示: 左足：异常，右足：正常；\r\n";
                    wtList.add(wt);
                    gjSet.add(102);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
                    String wt = "TBI检查提示: 左足：异常，右足：临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(103);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight < 0.65){
                    String wt = "TBI检查提示: 异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(27);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }
        }else {
            if (null != tbiRight){
                if (tbiRight >= 0.65 && tbiRight <= 0.7){
                    String wt = "TBI检查提示: 右足：临界值；\r\n";
                    wtList.add(wt);
                    gjSet.add(104);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
                    String wt = "TBI检查提示: 右足：异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(105);
                    if (flag){
                        mbSet.add(11);
                    }else {
                        mbSet.add(10);
                    }
                }
            }
        }
        //去重//////////////////////////
        Map<Integer, String> gjMapping = getGjMapping(gjGrzz,gjDmJr,gjDmXs);
        for (Integer code : gjSet) {
            gjList.add(gjMapping.get(code));
        }
        Map<Integer, String> mbMapping = getMbMapping();
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
    private static Map<Integer,String> getGjMapping(String gjGrzz,String gjDmJr,String gjDmXs){
        Map<Integer, String> outMap = new HashMap<>();
        //创面
        outMap.put(1,"糖尿病足溃疡、受伤后，易形成慢性难愈合性创面，因此，需要积极配合医护人员进行创面规范处理，并控制好血糖、血压、血脂，及抗感染，改善微循环等局部和全身的综合系统治疗，有助于创面的愈合；\r\n");
        //缺血
        outMap.put(3,"缺血性坏疽，缺血性溃疡以及缺血性静息痛是糖尿病足下肢缺血的严重阶段，需要及时就医，积极配合专科医生进行系统的治疗；\r\n");
        outMap.put(4,"间歇性跛行是糖尿病足的早期的临床表现之一，建议最好及时到专科诊治，进一步检查明确病因，然后进行针对性系统治疗；\r\n");
        //足动脉搏动
        outMap.put(5,"足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足背动脉检查有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
        outMap.put(6,"足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足背动脉检查搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
        //皮温下降,10克尼龙丝触觉检查,,踝反射检查,凉、温度觉,痛觉
        outMap.put(7,"有感觉障碍，有可能存在下肢神经病变，导致保护功能减退，发生下肢神经性溃疡，及时专科诊治，遵医嘱进行系统治疗，并定期复查观察变化；\r\n");
        //白细胞计数
        outMap.put(8,"白细胞计数增高 一般主要见于细菌性感染、组织损伤等，另外也可以见于剧烈运动、恐惧、孕期等情况，建议就医诊治，明确病因，一般炎症引起的，经治疗好转后白细胞数可下降；\r\n");
        outMap.put(9,"白细胞计数降低 一般主要见于病毒感染，也可见于其它情况，比如脾功能亢进、自身免疫疾病等，建议就医诊治，明确病因后进行针对性的治疗；\r\n");
        //降钙素原
        outMap.put(10,"降钙素原是严重细菌性炎症和真菌感染的特异性指标，而且也是脓毒症和炎症活动有关的多脏器衰竭的可靠指标。建议专科就诊，明确具体病因；\r\n");
        //C反应蛋白
        outMap.put(11,"C反应蛋白增高常见于组织损伤，各种细菌性感染，风湿热活动期，动脉粥样硬化等，也可出现生理性增高。建议动态观察，如持续增高，请专科诊治；\r\n");
        //血沉
        outMap.put(12,"生理性血沉加快可见于50岁以上的年长者，病理性血沉增快绝大多数为急性或慢性感染。如结核等免疫性疾病、高胆固醇血症等，建议复查，如结果仍异常，建议专科诊治；\r\n");
        outMap.put(13,"生理性血沉加快可见于51岁以上的年长者；病理性血沉增快绝大多数为急性或慢性感染，如结核等免疫性疾病、贫血、高胆固醇血症等，建议复查，如结果仍异常，建议专科诊治；\r\n");
        //血红蛋白浓度
        outMap.put(14,"血红蛋白浓度升高可见于脱水、饮酒、长期抽烟、高血压等情况，建议请临床医生评估分析，定期复查观察变化；\r\n");
        outMap.put(15,"血红蛋白量减低的程度一般反映贫血的程度，建议请临床医生评估分析具体病因 ，并定期复查观察变化；\r\n");
        //白蛋白
        outMap.put(16,"白蛋白降低，建议到专科就诊，明确病因后进行针对性的治疗，并注意复查；\r\n");
        outMap.put(17,"白蛋白增高，建议适当的补充水分，饮食方面适当减少蛋白的摄入，定期复查观察变化；\r\n");
        //ABI
        outMap.put(18,"存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(19,"有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(20,"下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(21,"下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(22,"足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(44,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(45,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(46,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(47,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(48,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(49,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(50,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(51,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(52,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(53,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(54,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(55,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(56,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(57,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(58,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(59,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(60,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(61,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(62,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(63,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(64,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(65,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(66,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(67,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(68,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(69,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(70,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(71,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(72,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(73,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        outMap.put(74,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(75,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(76,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(77,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(78,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(79,"右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
        outMap.put(80,"右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
        outMap.put(81,"右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
        outMap.put(82,"右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
        outMap.put(83,"右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
        //VPT
        outMap.put(23,"轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(24,"中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(25,"有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(84,"右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(85,"右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(86,"右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(87,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(88,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(89,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(90,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(91,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(92,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(93,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(94,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(95,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(106,"左足：感觉良好，发生神经性溃疡为低风险。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(107,"左足：感觉良好，发生神经性溃疡为低风险。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
        outMap.put(108,"左足：感觉良好，发生神经性溃疡为低风险。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
        outMap.put(109,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        outMap.put(110,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        outMap.put(111,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
        //TBI
        outMap.put(26,"有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(27,"下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
        outMap.put(96,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(97,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
        outMap.put(98,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(99,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(100,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
        outMap.put(101,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
        outMap.put(102,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
        outMap.put(103,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(104,"右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
        outMap.put(105,"右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");

        //经皮氧分压
        outMap.put(28,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左足：轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(29,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左足：重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(30,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/正常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(31,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(32,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(33,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/未查），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(34,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/正常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(35,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(36,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(37,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/未查），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(38,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：正常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(39,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：正常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(40,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：未查/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(41,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：未查/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(42,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压右足：轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
        outMap.put(43,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压右足：重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");

        outMap.put(112,gjGrzz + "是身体组织局部有炎症的症状表现之一，提示可能存在感染的情况，建议立即专科诊治；\r\n");
        outMap.put(113,"足动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足动脉检查（"+gjDmJr+"）有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
        outMap.put(114, "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足动脉检查（"+gjDmXs+"）搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
        // outMap  key 114
        return outMap;
    }

    /**
     * 预期达到目标文案
     * @param range  控制目标
     * @return
     */
    private static Map<Integer, String> getMbMapping() {
        Map<Integer, String> outMap = new HashMap<>();
        //创面,感染症状,缺血,经皮氧分压,足动脉搏动,皮温下降,10克尼龙丝触觉检查,,踝反射检查,凉、温度觉,痛觉
        outMap.put(1,"肢体血管功能、供血逐渐改善，症状逐渐好转；\r\n");
        outMap.put(12,"肢体血管功能、供血逐渐改善，症状逐渐好转，创面逐渐愈合；\r\n");
        //白细胞计数
        outMap.put(2,"控制白细胞计数，达到(4-10)×10^9/L；\r\n");
        //降钙素原
        outMap.put(3,"控制降钙素原，达到0.000-0.050ng/ml；\r\n");
        //C反应蛋白
        outMap.put(4,"控制C反应蛋白，达到0-0.8mg/dL；\r\n");
        //血沉
        outMap.put(5,"控制血沉，达到0-15mm/h；\r\n");  //男
        outMap.put(6,"控制血沉，达到0-20mm/h；\r\n");  //女
        //血红蛋白浓度
        outMap.put(7,"控制血红蛋白浓度，达到120～160g/L；\r\n");  //男
        outMap.put(8,"控制血红蛋白浓度，达到110～150g/L；\r\n");  //女
        //白蛋白
        outMap.put(9,"控制白蛋白，达到34-54g/L；\r\n");
        //ABI,VPT,TBI
        outMap.put(10,"积极治疗，使肢体血管和神经功能、供血状态逐渐改善，症状好转；\r\n");
        outMap.put(11,"积极治疗，使肢体血管和神经功能、供血状态逐渐改善，症状好转，创面逐渐愈合；\r\n");
        return outMap;
    }

    private static boolean checkCmAndKy(String cmLeft,String cmRght,String qxxky){
        boolean flag = false;
        if (!StringUtils.isBlank(cmLeft) || !StringUtils.isBlank(cmRght) || (!StringUtils.isBlank(qxxky) && !"1".equals(qxxky)) ){
            flag = true;
        }
        return flag;
    }
}

//package com.comvee.cdms.follow.helper;
//
//import com.comvee.cdms.common.utils.StringUtils;
//import com.comvee.cdms.member.bo.RangeBO;
//
//import java.util.*;
//
///** 糖尿病足随访表智能建议
// * @author wyc
// * @date 2019/9/29 10:38
// */
//public class FollowTnbzHelper {
//    public static Map<String,Object> outFollowTnbz(Map<String, Object> followBodyMap){
//        Map<String, Object> reMap = new HashMap<>();
//
//        List<String> wtList = new ArrayList<>();//问题集合
//        List<String> gjList = new ArrayList<>();//改进集合
//        List<String> mbList = new ArrayList<>();//目标集合
//
//        Set<Integer> gjSet = new LinkedHashSet<>();//改进去重
//        Set<Integer> mbSet = new LinkedHashSet<>();//目标去重
//        String sex = "" ;  //性别  男 女
//        if (null != followBodyMap.get("sex") && !StringUtils.isBlank(followBodyMap.get("sex").toString())){
//            sex = followBodyMap.get("sex").toString();
//        }
//        //创面
//        String cmWt = "";  //创面存在问题
//        String cmLeft = "";  //左脚创面位置
//        String cmRght = ""; //右脚创面位置
//        if (null != followBodyMap.get("cmwz_l") && !StringUtils.isBlank(followBodyMap.get("cmwz_l").toString())){
//            String cmwzL = followBodyMap.get("cmwz_l").toString();
//            cmLeft = cmwzL.replace("2", "足趾").replace("3", "足体")
//                    .replace("4", "足跟").replace("5", "踝").replace("6", "小腿");
//        }
//        if (null != followBodyMap.get("cmwz_r") && !StringUtils.isBlank(followBodyMap.get("cmwz_r").toString())){
//            String cmwzR = followBodyMap.get("cmwz_r").toString();
//            cmRght = cmwzR.replace("2", "足趾").replace("3", "足体")
//                    .replace("4", "足跟").replace("5", "踝").replace("6", "小腿");
//
//        }
//        if (!"".equals(cmLeft) && !"".equals(cmRght)){
//            cmWt = "左足：在“"+cmLeft+"”位置存在创面。右足：在“"+cmRght+"”位置存在创面；\r\n";
//        }else {
//            if (!"".equals(cmLeft) && "".equals(cmRght)){
//                cmWt = "左足：在“"+cmLeft+"”位置存在创面；\r\n";
//            }else if ("".equals(cmLeft) && !"".equals(cmRght)){
//                cmWt += "右足：在“"+cmRght+"”位置存在创面；\r\n";
//            }
//        }
////        if (!"".equals(cmWt)){
////            wtList.add(cmWt);
////            gjSet.add(1);
////            mbSet.add(12);
////        }
//
//        //缺血性溃疡
//        String qxxky = "";
//        if (null != followBodyMap.get("qxxky") && !StringUtils.isBlank(followBodyMap.get("qxxky").toString())){
//            qxxky = followBodyMap.get("qxxky").toString();
//            //1:无 2:皮肤或皮下组织  3:肌肉及结缔组织
//            if ("2".equals(qxxky)){
//                String wt = "缺血性溃疡（皮肤或皮下组织）；\r\n";
//                wtList.add(wt);
//                gjSet.add(3);
//                mbSet.add(12);
//            }else if ("3".equals(qxxky)){
//                String wt = "缺血性溃疡（肌肉及结缔组织）；\r\n";
//                wtList.add(wt);
//                gjSet.add(3);
//                mbSet.add(12);
//            }
//        }
//
//        boolean flag = checkCmAndKy(cmLeft, cmRght, qxxky); //判断是否有创面或溃疡
//        //感染症状
//        String grzz = "";
//        if (null != followBodyMap.get("hb") && !StringUtils.isBlank(followBodyMap.get("hb").toString())){
//            String hb = followBodyMap.get("hb").toString();
//            if ("2".equals(hb)){
//                String wt = "有感染症状-红斑；\r\n";
//                wtList.add(wt);
//                grzz += "红、";
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//
//            }
//        }
//        if (null != followBodyMap.get("zz") && !StringUtils.isBlank(followBodyMap.get("zz").toString())){
//            String zz = followBodyMap.get("zz").toString();
//            if ("2".equals(zz)){
//                String wt = "有感染症状-肿胀；\r\n";
//                wtList.add(wt);
//                grzz += "肿、";
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        if (null != followBodyMap.get("rg") && !StringUtils.isBlank(followBodyMap.get("rg").toString())){
//            String rg = followBodyMap.get("rg").toString();
//            if ("2".equals(rg)){
//                String wt = "有感染症状-热感；\r\n";
//                wtList.add(wt);
//                grzz += "热、";
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        if (null != followBodyMap.get("tt") && !StringUtils.isBlank(followBodyMap.get("tt").toString())){
//            String tt = followBodyMap.get("tt").toString();
//            if ("2".equals(tt)){
//                String wt = "有感染症状-疼痛；\r\n";
//                wtList.add(wt);
//                grzz += "痛、";
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        String gjGrzz = "";
//        if (!"".equals(grzz)){
////            String gj = grzz.substring(0,grzz.length()-1)+" 是身体组织局部有炎症的症状表现之一，提示可能存在感染的情况，建议立即专科诊治；\r\n";
////            gjList.add(gj);
//            gjGrzz = grzz.substring(0,grzz.length()-1);
//            gjSet.add(112);
//        }
//        //缺血性坏疽
//        if (null != followBodyMap.get("qxxhz") && !StringUtils.isBlank(followBodyMap.get("qxxhz").toString())){
//            String qxxhz = followBodyMap.get("qxxhz").toString();
//            //1:无 2:局部坏疽  3:全足坏疽
//            if ("2".equals(qxxhz)){
//                String wt = "局部坏疽；\r\n";
//                wtList.add(wt);
//                gjSet.add(3);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(qxxhz)){
//                String wt = "全足坏疽；\r\n";
//                wtList.add(wt);
//                gjSet.add(3);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //缺血性静息痛
//        if (null != followBodyMap.get("qxxjxt") && !StringUtils.isBlank(followBodyMap.get("qxxjxt").toString())){
//            String qxxjxt = followBodyMap.get("qxxjxt").toString();
//            //1:无 2:有
//            if ("2".equals(qxxjxt)){
//                String wt = "有缺血性静息痛；\r\n";
//                wtList.add(wt);
//                gjSet.add(3);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        //间歇性跛行
//        if (null != followBodyMap.get("jxxbx") && !StringUtils.isBlank(followBodyMap.get("jxxbx").toString())){
//            String jxxbx = followBodyMap.get("jxxbx").toString();
//            // 1 : 无  2 : 轻度 3 : 中度  4 : 重度
//            if ("2".equals(jxxbx)){
//                String wt = "（轻度）间歇性跛行；\r\n";
//                wtList.add(wt);
//                gjSet.add(4);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jxxbx)){
//                String wt = "（中度）间歇性跛行；\r\n";
//                wtList.add(wt);
//                gjSet.add(4);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("4".equals(jxxbx)){
//                String wt = "（重度）间歇性跛行；\r\n";
//                wtList.add(wt);
//                gjSet.add(4);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        //经皮氧分压 1 : 正常 2 : 轻度异常 3 : 重度异常  4 : 未查
//        String jpyLeft = "";
//        String jpyRight = "";
//        if (null != followBodyMap.get("jpyfy") && !StringUtils.isBlank(followBodyMap.get("jpyfy").toString())){
//            jpyLeft = followBodyMap.get("jpyfy").toString();
//        }
//        if (null != followBodyMap.get("jpyfy_r") && !StringUtils.isBlank(followBodyMap.get("jpyfy_r").toString())){
//            jpyRight = followBodyMap.get("jpyfy_r").toString();
//        }
//        if ("".equals(jpyLeft)){
//            if ("2".equals(jpyRight)){
//                String wt = "右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(42);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jpyRight)){
//                String wt = "右足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(43);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("1".equals(jpyLeft)){
//            if ("2".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(38);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(39);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("2".equals(jpyLeft)){
//            if ("".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(28);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(30);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(31);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(32);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("4".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-轻度异常。右足：经皮氧分压（TCPO2）-未查；\r\n";
//                wtList.add(wt);
//                gjSet.add(33);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("3".equals(jpyLeft)){
//            if ("".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(29);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(34);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(35);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(36);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("4".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-重度异常。右足：经皮氧分压（TCPO2）-未查；\r\n";
//                wtList.add(wt);
//                gjSet.add(37);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("4".equals(jpyLeft)){
//            if ("2".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-未查。右足：经皮氧分压（TCPO2）-轻度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(40);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(jpyRight)){
//                String wt = "左足：经皮氧分压（TCPO2）-未查。右足：经皮氧分压（TCPO2）-重度异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(41);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//
//        //足动脉搏动减弱  1:无 2:减弱  3:正常
//        String dmJr = ""; //足动脉搏动减弱类型
//        String dmXs = ""; //足动脉搏动消失类型
//        //髂动脉
//        if (null != followBodyMap.get("kdm") && !StringUtils.isBlank(followBodyMap.get("kdm").toString())){
//            String kdm = followBodyMap.get("kdm").toString();
//            if ("1".equals(kdm)){
//                dmXs += "髂动脉，";
//                String wt = "足动脉搏动：髂动脉消失；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(kdm)){
//                dmJr += "髂动脉，";
//                String wt = "足动脉搏动：髂动脉减弱；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        //腘动脉
//        if (null != followBodyMap.get("gdm") && !StringUtils.isBlank(followBodyMap.get("gdm").toString())){
//            String gdm = followBodyMap.get("gdm").toString();
//            if ("1".equals(gdm)){
//                dmXs += "腘动脉，";
//                String wt = "足动脉搏动：腘动脉消失；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(gdm)){
//                dmJr += "腘动脉，";
//                String wt = "足动脉搏动：腘动脉减弱；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        //足背动脉
//        if (null != followBodyMap.get("zbdm") && !StringUtils.isBlank(followBodyMap.get("zbdm").toString())){
//            String zbdm = followBodyMap.get("zbdm").toString();
//            if ("1".equals(zbdm)){
//                dmXs += "足背动脉，";
//                String wt = "足动脉搏动：足背动脉消失；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(zbdm)){
//                dmJr += "足背动脉，";
//                String wt = "足动脉搏动：足背动脉减弱；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        //胫后动脉
//        if (null != followBodyMap.get("jhdm") && !StringUtils.isBlank(followBodyMap.get("jhdm").toString())){
//            String jhdm = followBodyMap.get("jhdm").toString();
//            if ("1".equals(jhdm)){
//                dmXs += "胫后动脉，";
//                String wt = "足动脉搏动：胫后动脉消失；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(jhdm)){
//                dmJr += "胫后动脉，";
//                String wt = "足动脉搏动：胫后动脉减弱；\r\n";
//                wtList.add(wt);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//        String gjDmJr = "";
//        String gjDmXs = "";
//        if (!"".equals(dmJr)){
////            String gj = "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足动脉检查（"+dmJr.substring(0,dmJr.length()-1)+"）有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n";
////            gjList.add(gj);
//            gjSet.add(113);
//        }
//        if (!"".equals(dmXs)){
////            String gj = "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足动脉检查（"+dmXs.substring(0,dmXs.length()-1)+"）搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n";
////            gjList.add(gj);
//            gjSet.add(114);
//        }
//
//        //皮温下降 1:无 2:有
//        if (null != followBodyMap.get("pwxj") && !StringUtils.isBlank(followBodyMap.get("pwxj").toString())){
//            String pwxj = followBodyMap.get("pwxj").toString();
//            if ("2".equals(pwxj)){
//                String wt = "皮温下降；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //10克尼龙丝触觉检查  1:正常 2:缺失 3:消失
//        String nlsLeft = "";
//        String nlsRight = "";
//        if (null != followBodyMap.get("nlscjjc") && !StringUtils.isBlank(followBodyMap.get("nlscjjc").toString())){
//            nlsLeft = followBodyMap.get("nlscjjc").toString();
//        }
//        if (null != followBodyMap.get("nlscjjc_r") && !StringUtils.isBlank(followBodyMap.get("nlscjjc_r").toString())){
//            nlsRight = followBodyMap.get("nlscjjc_r").toString();
//        }
//        if ("".equals(nlsLeft)){
//            if ("2".equals(nlsRight)){
//                String wt = "右足：10克尼龙丝触觉检查缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(nlsRight)){
//                String wt = "右足：10克尼龙丝触觉检查消失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("1".equals(nlsLeft)){
//            if ("2".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查正常。右足：10克尼龙丝触觉检查缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查正常。右足：10克尼龙丝触觉检查消失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("2".equals(nlsLeft)){
//            if ("".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查缺失。右足：10克尼龙丝触觉检查消失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("3".equals(nlsLeft)){
//            if ("".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查消失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("3".equals(nlsRight)){
//                String wt = "左足：10克尼龙丝触觉检查消失。右足：10克尼龙丝触觉检查消失；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //踝反射检查：1:正常 2:异常
//        String hfsLeft = "";
//        String hfsRight = "";
//        if (null != followBodyMap.get("hfsjc") && !StringUtils.isBlank(followBodyMap.get("hfsjc").toString())){
//            hfsLeft = followBodyMap.get("hfsjc").toString();
//        }
//        if (null != followBodyMap.get("hfsjc_r") && !StringUtils.isBlank(followBodyMap.get("hfsjc_r").toString())){
//            hfsRight = followBodyMap.get("hfsjc_r").toString();
//        }
//        if ("".equals(hfsLeft)){
//            if ("2".equals(hfsRight)){
//                String wt = "右足：踝反射检查异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("1".equals(hfsLeft)){
//            if ("2".equals(hfsRight)){
//                String wt = "左足：踝反射检查正常。右足：踝反射检查异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("2".equals(hfsLeft)){
//            if ("".equals(hfsRight)){
//                String wt = "左足：踝反射检查异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(hfsRight)){
//                String wt = "左足：踝反射检查异常。右足：踝反射检查正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(hfsRight)){
//                String wt = "左足：踝反射检查异常。右足：踝反射检查异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //凉、温度觉  1:正常 2:异常
//        String lwdjLeft = "";
//        String lwdjRight = "";
//        if (null != followBodyMap.get("lwdj") && !StringUtils.isBlank(followBodyMap.get("lwdj").toString())){
//            lwdjLeft = followBodyMap.get("lwdj").toString();
//        }
//        if (null != followBodyMap.get("lwdj_r") && !StringUtils.isBlank(followBodyMap.get("lwdj_r").toString())){
//            lwdjRight = followBodyMap.get("lwdj_r").toString();
//        }
//        if ("".equals(lwdjLeft)){
//            if ("2".equals(lwdjRight)){
//                String wt = "右足：凉、温度觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("1".equals(lwdjLeft)){
//            if ("2".equals(lwdjRight)){
//                String wt = "左足：凉、温度觉正常。右足：凉、温度觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("2".equals(lwdjLeft)){
//            if ("".equals(lwdjRight)){
//                String wt = "左足：凉、温度觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(lwdjRight)){
//                String wt = "左足：凉、温度觉异常。右足：凉、温度觉正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(lwdjRight)){
//                String wt = "左足：凉、温度觉异常。右足：凉、温度觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //痛觉 1:正常 2:异常
//        String tjLeft = "";
//        String tjRight = "";
//        if (null != followBodyMap.get("tj") && !StringUtils.isBlank(followBodyMap.get("tj").toString())){
//            tjLeft = followBodyMap.get("tj").toString();
//        }
//        if (null != followBodyMap.get("tj_r") && !StringUtils.isBlank(followBodyMap.get("tj_r").toString())){
//            tjRight = followBodyMap.get("tj_r").toString();
//        }
//        if ("".equals(tjLeft)){
//            if ("2".equals(tjRight)){
//                String wt = "右足：痛觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("1".equals(tjLeft)){
//            if ("2".equals(tjRight)){
//                String wt = "左足：痛觉正常。右足：痛觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }else if ("2".equals(tjLeft)){
//            if ("".equals(tjRight)){
//                String wt = "左足：痛觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("1".equals(tjRight)){
//                String wt = "左足：痛觉异常。右足：痛觉正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }else if ("2".equals(tjRight)){
//                String wt = "左足：痛觉异常。右足：痛觉异常；\r\n";
//                wtList.add(wt);
//                gjSet.add(7);
//                if (flag){
//                    mbSet.add(12);
//                }else {
//                    mbSet.add(1);
//                }
//            }
//        }
//
//        //创面问题,由于排序问题放在此处
//        if (!"".equals(cmWt)){
//            wtList.add(cmWt);
//            gjSet.add(1);
//            mbSet.add(12);
//        }
//
//        //白细胞计数
//        if (null != followBodyMap.get("bxb") && !StringUtils.isBlank(followBodyMap.get("bxb").toString())){
//            double bxb = Double.parseDouble(followBodyMap.get("bxb").toString());
//            if (bxb > 10){
//                String wt = "白细胞计数增高；\r\n";
//                wtList.add(wt);
//                gjSet.add(8);
//                mbSet.add(2);
//            }else if (bxb < 4){
//                String wt = "白细胞计数降低；\r\n";
//                wtList.add(wt);
//                gjSet.add(9);
//                mbSet.add(2);
//            }
//        }
//
//        //降钙素原
//        if (null != followBodyMap.get("jgsy") && !StringUtils.isBlank(followBodyMap.get("jgsy").toString())){
//            double jgsy = Double.parseDouble(followBodyMap.get("jgsy").toString());
//            if (jgsy > 0.05){
//                String wt = "降钙素原升高；\r\n";
//                wtList.add(wt);
//                gjSet.add(10);
//                mbSet.add(3);
//            }
//        }
//
//        //C反应蛋白
//        if (null != followBodyMap.get("cfydb") && !StringUtils.isBlank(followBodyMap.get("cfydb").toString())){
//            double cfydb = Double.parseDouble(followBodyMap.get("cfydb").toString());
//            if (cfydb > 0.8){
//                String wt = "C反应蛋白升高；\r\n";
//                wtList.add(wt);
//                gjSet.add(11);
//                mbSet.add(4);
//            }
//        }
//
//        //血沉
//        if (null != followBodyMap.get("xc") && !StringUtils.isBlank(followBodyMap.get("xc").toString())){
//            double xc = Double.parseDouble(followBodyMap.get("xc").toString());
//            if ("男".equals(sex)){
//                if (xc > 15){
//                    String wt = "血沉加快；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(12);
//                    mbSet.add(5);
//                }
//            }else if ("女".equals(sex)){
//                if (xc > 20){
//                    String wt = "血沉加快；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(13);
//                    mbSet.add(6);
//                }
//            }
//        }
//
//        //血红蛋白浓度
//        if (null != followBodyMap.get("xhdb") && !StringUtils.isBlank(followBodyMap.get("xhdb").toString())){
//            double xhdb = Double.parseDouble(followBodyMap.get("xhdb").toString());
//            if ("男".equals(sex)){
//                if (xhdb > 160){
//                    String wt = "血红蛋白浓度升高；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(14);
//                    mbSet.add(7);
//                }else if (xhdb < 120){
//                    String wt = "血红蛋白浓度降低；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(15);
//                    mbSet.add(7);
//                }
//            }else if ("女".equals(sex)){
//                if (xhdb > 150){
//                    String wt = "血红蛋白浓度升高；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(14);
//                    mbSet.add(8);
//                }else if (xhdb < 110){
//                    String wt = "血红蛋白浓度降低；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(15);
//                    mbSet.add(8);
//                }
//            }
//        }
//
//        //白蛋白（ALB）
//        if (null != followBodyMap.get("xqbdb") && !StringUtils.isBlank(followBodyMap.get("xqbdb").toString())){
//            double xqbdb = Double.parseDouble(followBodyMap.get("xqbdb").toString());
//            if (xqbdb > 54){
//                String wt = "白蛋白升高；\r\n";
//                wtList.add(wt);
//                gjSet.add(17);
//                mbSet.add(9);
//            }else if (xqbdb < 34){
//                String wt = "白蛋白降低；\r\n";
//                wtList.add(wt);
//                gjSet.add(16);
//                mbSet.add(9);
//            }
//        }
//
//        //ABI
//        Double abiLeft = null;
//        Double abiRight = null;
//        if (null != followBodyMap.get("abi_l") && !StringUtils.isBlank(followBodyMap.get("abi_l").toString())){
//            abiLeft = Double.parseDouble(followBodyMap.get("abi_l").toString());
//        }
//        if (null != followBodyMap.get("abi_r") && !StringUtils.isBlank(followBodyMap.get("abi_r").toString())){
//            abiRight = Double.parseDouble(followBodyMap.get("abi_r").toString());
//        }
//        if (null != abiLeft){
//            if (abiLeft > 1.3){
//                if (null == abiRight){
//                    String wt = "ABI检查提示：左足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(44);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight > 1.3){
//                    String wt = "ABI检查提示：动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(18);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 1 && abiRight <= 1.3){
//                    String wt = "ABI检查提示：左足动脉钙化,右足正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(45);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.91 && abiRight <= 0.99){
//                    String wt = "ABI检查提示：左足动脉钙化,右足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(46);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.71 && abiRight <= 0.9){
//                    String wt = "ABI检查提示：左足动脉钙化,右足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(47);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.41 && abiRight <= 0.7){
//                    String wt = "ABI检查提示：左足动脉钙化,右足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(48);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0 && abiRight <= 0.4){
//                    String wt = "ABI检查提示：左足动脉钙化,右足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(49);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//
//            }else if (abiLeft >= 1 && abiLeft <= 1.3) {
//                if (null != abiRight) {
//                    if (abiRight > 1.3) {
//                        String wt = "ABI检查提示：左足正常,右足动脉钙化；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(50);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    } else if (abiRight >= 0.91 && abiRight <= 0.99) {
//                        String wt = "ABI检查提示：左足正常,右足临界值；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(51);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    } else if (abiRight >= 0.71 && abiRight <= 0.9) {
//                        String wt = "ABI检查提示：左足正常,右足轻度动脉狭窄；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(52);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    } else if (abiRight >= 0.41 && abiRight <= 0.7) {
//                        String wt = "ABI检查提示：左足正常,右足中度动脉狭窄；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(53);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    } else if (abiRight >= 0 && abiRight <= 0.4) {
//                        String wt = "ABI检查提示：左足正常,右足重度病变；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(54);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    }
//                }
//            }else if (abiLeft >= 0.91 && abiLeft <= 0.99){
//                if (null == abiRight){
//                    String wt = "ABI检查提示：左足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(55);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight > 1.3){
//                    String wt = "ABI检查提示：左足临界值,右足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(56);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 1 && abiRight <= 1.3){
//                    String wt = "ABI检查提示：左足临界值,右足正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(57);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.91 && abiRight <= 0.99){
//                    String wt = "ABI检查提示：临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(19);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.71 && abiRight <= 0.9){
//                    String wt = "ABI检查提示：左足临界值,右足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(58);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.41 && abiRight <= 0.7){
//                    String wt = "ABI检查提示：左足临界值,右足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(59);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0 && abiRight <= 0.4){
//                    String wt = "ABI检查提示：左足临界值,右足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(60);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }else if (abiLeft >= 0.71 && abiLeft <= 0.9){
//                if (null == abiRight){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(61);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight > 1.3){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(62);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 1 && abiRight <= 1.3){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(63);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.91 && abiRight <= 0.99){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(64);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.71 && abiRight <= 0.9){
//                    String wt = "ABI检查提示：轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(20);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.41 && abiRight <= 0.7){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(65);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0 && abiRight <= 0.4){
//                    String wt = "ABI检查提示：左足轻度动脉狭窄,右足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(66);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }else if (abiLeft >= 0.41 && abiLeft <= 0.7){
//                if (null == abiRight){
//                    String wt = "ABI检查提示：左足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(67);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight > 1.3){
//                    String wt = "ABI检查提示：左足中度动脉狭窄,右足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(68);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 1 && abiRight <= 1.3){
//                    String wt = "ABI检查提示：左足中度动脉狭窄,右足正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(69);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.91 && abiRight <= 0.99){
//                    String wt = "ABI检查提示：左足中度动脉狭窄,右足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(70);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.71 && abiRight <= 0.9){
//                    String wt = "ABI检查提示：左足中度动脉狭窄,右足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(71);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.41 && abiRight <= 0.7){
//                    String wt = "ABI检查提示：中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(21);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0 && abiRight <= 0.4){
//                    String wt = "ABI检查提示：左足中度动脉狭窄,右足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(72);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }else if (abiLeft >= 0 && abiLeft <=0.4){
//                if (null == abiRight){
//                    String wt = "ABI检查提示：左足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(73);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight > 1.3){
//                    String wt = "ABI检查提示：左足重度病变,右足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(74);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 1 && abiRight <= 1.3){
//                    String wt = "ABI检查提示：左足重度病变,右足正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(75);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.91 && abiRight <= 0.99){
//                    String wt = "ABI检查提示：左足重度病变,右足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(76);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.71 && abiRight <= 0.9){
//                    String wt = "ABI检查提示：左足重度病变,右足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(77);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0.41 && abiRight <= 0.7){
//                    String wt = "ABI检查提示：左足重度病变,右足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(78);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (abiRight >= 0 && abiRight <= 0.4){
//                    String wt = "ABI检查提示：重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(22);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }
//        }else {
//            if (null != abiRight) {
//                if (abiRight > 1.3) {
//                    String wt = "ABI检查提示：右足动脉钙化；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(79);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                } else if (abiRight >= 0.91 && abiRight <= 0.99) {
//                    String wt = "ABI检查提示：右足临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(80);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                } else if (abiRight >= 0.71 && abiRight <= 0.9) {
//                    String wt = "ABI检查提示：右足轻度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(81);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                } else if (abiRight >= 0.41 && abiRight <= 0.7) {
//                    String wt = "ABI检查提示：右足中度动脉狭窄；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(82);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                } else if (abiRight >= 0 && abiRight <= 0.4) {
//                    String wt = "ABI检查提示：右足重度病变；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(83);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }
//
//        }
//
//        //vpt 0 : <10V(正常)  1 : 10-15V(轻度受损) 2 : 16-25V(中度受损)  3 : >25V(重度受损)
//        String vptLeft = "";
//        String vptRight = "";
//        if(null != followBodyMap.get("zdgjjc") && !StringUtils.isBlank(followBodyMap.get("zdgjjc").toString())){
//            vptLeft = followBodyMap.get("zdgjjc").toString();
//        }
//        if(null != followBodyMap.get("zdgjjc_r") && !StringUtils.isBlank(followBodyMap.get("zdgjjc_r").toString())){
//            vptRight = followBodyMap.get("zdgjjc_r").toString();
//        }
//        if ("".equals(vptLeft)){
//            if ("1".equals(vptRight)){
//                String wt = "VPT检查提示：右足临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(84);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("2".equals(vptRight)){
//                String wt = "VPT检查提示：右足感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(85);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("3".equals(vptRight)){
//                String wt = "VPT检查提示：右足感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(86);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }
//        }else if ("0".equals(vptLeft)){
//            if ("1".equals(vptRight)){
//                String wt = "VPT检查提示：左足正常，右足临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(106);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("2".equals(vptRight)){
//                String wt = "VPT检查提示：左足正常，右足感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(107);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("3".equals(vptRight)){
//                String wt = "VPT检查提示：左足正常，右足感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(108);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }
//        }else if ("1".equals(vptLeft)){
//            if ("".equals(vptRight)){
//                String wt = "VPT检查提示：左足临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(87);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("0".equals(vptRight)){
//                String wt = "VPT检查提示：左足临界值，右足：正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(109);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("1".equals(vptRight)){
//                String wt = "VPT检查提示：临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(23);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("2".equals(vptRight)){
//                String wt = "VPT检查提示：左足临界值，右足：感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(88);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("3".equals(vptRight)){
//                String wt = "VPT检查提示：左足临界值，右足：感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(89);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }
//        }else if ("2".equals(vptLeft)){
//            if ("".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(90);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("0".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉减退，右足：正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(110);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("1".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉减退，右足：临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(91);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("2".equals(vptRight)){
//                String wt = "VPT检查提示：感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(24);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("3".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉减退，右足：感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(92);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }
//        }else if ("3".equals(vptLeft)){
//            if ("".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(93);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("0".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉缺失，右足：正常；\r\n";
//                wtList.add(wt);
//                gjSet.add(111);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("1".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉缺失，右足：临界值；\r\n";
//                wtList.add(wt);
//                gjSet.add(94);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("2".equals(vptRight)){
//                String wt = "VPT检查提示：左足感觉缺失，右足：感觉减退；\r\n";
//                wtList.add(wt);
//                gjSet.add(95);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }else if ("3".equals(vptRight)){
//                String wt = "VPT检查提示：感觉缺失；\r\n";
//                wtList.add(wt);
//                gjSet.add(25);
//                if (flag){
//                    mbSet.add(11);
//                }else {
//                    mbSet.add(10);
//                }
//            }
//        }
//
//        //TBI >0.7(正常)  0.65-0.7(临界值)  ＜0.65(异常)
//        Double tbiLeft = null;
//        Double tbiRight = null;
//        if (null != followBodyMap.get("tbi_l") && !StringUtils.isBlank(followBodyMap.get("tbi_l").toString())){
//            tbiLeft = Double.parseDouble(followBodyMap.get("tbi_l").toString());
//        }
//        if (null != followBodyMap.get("tbi_r") && !StringUtils.isBlank(followBodyMap.get("tbi_r").toString())){
//            tbiRight = Double.parseDouble(followBodyMap.get("tbi_r").toString());
//        }
//        if (null != tbiLeft){
//            if (tbiLeft > 0.7){
//                if (null != tbiRight){
//                    if (tbiRight >= 0.65 && tbiRight <= 0.7){
//                        String wt = "TBI检查提示: 左足：正常，右足：临界值；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(96);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    }else if (tbiRight < 0.65){
//                        String wt = "TBI检查提示: 左足：正常，右足：异常；\r\n";
//                        wtList.add(wt);
//                        gjSet.add(97);
//                        if (flag){
//                            mbSet.add(11);
//                        }else {
//                            mbSet.add(10);
//                        }
//                    }
//                }
//            }else if (tbiLeft >= 0.65 && tbiLeft <= 0.7){
//                if (null == tbiRight){
//                    String wt = "TBI检查提示: 左足：临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(98);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight > 0.7){
//                    String wt = "TBI检查提示: 左足：临界值，右足：正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(99);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
//                    String wt = "TBI检查提示: 临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(26);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight < 0.65){
//                    String wt = "TBI检查提示: 左足：临界值，右足：异常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(100);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }else if (tbiLeft < 0.65){
//                if (null == tbiRight){
//                    String wt = "TBI检查提示: 左足：异常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(101);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight > 0.7){
//                    String wt = "TBI检查提示: 左足：异常，右足：正常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(102);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
//                    String wt = "TBI检查提示: 左足：异常，右足：临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(103);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight < 0.65){
//                    String wt = "TBI检查提示: 异常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(27);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }
//        }else {
//            if (null != tbiRight){
//                if (tbiRight >= 0.65 && tbiRight <= 0.7){
//                    String wt = "TBI检查提示: 右足：临界值；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(104);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }else if (tbiRight >= 0.65 && tbiRight <= 0.7){
//                    String wt = "TBI检查提示: 右足：异常；\r\n";
//                    wtList.add(wt);
//                    gjSet.add(105);
//                    if (flag){
//                        mbSet.add(11);
//                    }else {
//                        mbSet.add(10);
//                    }
//                }
//            }
//        }
//        //去重//////////////////////////
//        Map<Integer, String> gjMapping = getGjMapping(gjGrzz,gjDmJr,gjDmXs);
//        for (Integer code : gjSet) {
//            gjList.add(gjMapping.get(code));
//        }
//        Map<Integer, String> mbMapping = getMbMapping();
//        for (Integer code : mbSet) {
//            mbList.add(mbMapping.get(code));
//        }
//
//        reMap.put("wtList", wtList);
//        reMap.put("gjList", gjList);
//        reMap.put("mbList", mbList);
//        return  reMap;
//    }
//
//    /**
//     * 主要改进措施文案
//     * @param range  控制目标
//     * @return
//     */
//    private static Map<Integer,String> getGjMapping(String gjGrzz,String gjDmJr,String gjDmXs){
//        Map<Integer, String> outMap = new HashMap<>();
//        //创面
//        outMap.put(1,"糖尿病足溃疡、受伤后，易形成慢性难愈合性创面，因此，需要积极配合医护人员进行创面规范处理，并控制好血糖、血压、血脂，及抗感染，改善微循环等局部和全身的综合系统治疗，有助于创面的愈合；\r\n");
//        //缺血
//        outMap.put(3,"缺血性坏疽，缺血性溃疡以及缺血性静息痛是糖尿病足下肢缺血的严重阶段，需要及时就医，积极配合专科医生进行系统的治疗；\r\n");
//        outMap.put(4,"是糖尿病足的早期的临床表现之一，建议最好及时到专科诊治，进一步检查明确病因，然后进行针对性系统治疗；\r\n");
//        //足动脉搏动
//        outMap.put(5,"足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足背动脉检查有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
//        outMap.put(6,"足背动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足背动脉检查搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
//        //皮温下降,10克尼龙丝触觉检查,,踝反射检查,凉、温度觉,痛觉
//        outMap.put(7,"有感觉障碍，有可能存在下肢神经病变，导致保护功能减退，发生下肢神经性溃疡，及时专科诊治，遵医嘱进行系统治疗，并定期复查观察变化；\r\n");
//        //白细胞计数
//        outMap.put(8,"白细胞计数增高 一般主要见于细菌性感染、组织损伤等，另外也可以见于剧烈运动、恐惧、孕期等情况，建议就医诊治，明确病因，一般炎症引起的，经治疗好转后白细胞数可下降；\r\n");
//        outMap.put(9,"白细胞计数降低 一般主要见于病毒感染，也可见于其它情况，比如脾功能亢进、自身免疫疾病等，建议就医诊治，明确病因后进行针对性的治疗；\r\n");
//        //降钙素原
//        outMap.put(10,"降钙素原是严重细菌性炎症和真菌感染的特异性指标，而且也是脓毒症和炎症活动有关的多脏器衰竭的可靠指标。建议专科就诊，明确具体病因；\r\n");
//        //C反应蛋白
//        outMap.put(11,"C反应蛋白增高常见于组织损伤，各种细菌性感染，风湿热活动期，动脉粥样硬化等，也可出现生理性增高。建议动态观察，如持续增高，请专科诊治；\r\n");
//        //血沉
//        outMap.put(12,"生理性血沉加快可见于50岁以上的年长者，病理性血沉增快绝大多数为急性或慢性感染。如结核等免疫性疾病、高胆固醇血症等，建议复查，如结果仍异常，建议专科诊治；\r\n");
//        outMap.put(13,"生理性血沉加快可见于51岁以上的年长者；病理性血沉增快绝大多数为急性或慢性感染，如结核等免疫性疾病、贫血、高胆固醇血症等，建议复查，如结果仍异常，建议专科诊治；\r\n");
//        //血红蛋白浓度
//        outMap.put(14,"血红蛋白浓度升高可见于脱水、饮酒、长期抽烟、高血压等情况，建议请临床医生评估分析，定期复查观察变化；\r\n");
//        outMap.put(15,"血红蛋白量减低的程度一般反映贫血的程度，建议请临床医生评估分析具体病因 ，并定期复查观察变化；\r\n");
//        //白蛋白
//        outMap.put(16,"白蛋白降低，建议到专科就诊，明确病因后进行针对性的治疗，并注意复查；\r\n");
//        outMap.put(17,"白蛋白增高，建议适当的补充水分，饮食方面适当减少蛋白的摄入，定期复查观察变化；\r\n");
//        //ABI
//        outMap.put(18,"存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(19,"有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(20,"下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(21,"下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(22,"足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(44,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(45,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(46,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(47,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(48,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(49,"左足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(50,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(51,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(52,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(53,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(54,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(55,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(56,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(57,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(58,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(59,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(60,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(61,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(62,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(63,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(64,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(65,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(66,"左足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(67,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(68,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(69,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(70,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(71,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(72,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊。右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(73,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        outMap.put(74,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(75,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(76,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(77,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(78,"左足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(79,"右足：存在下肢动脉中层钙化，血管壁僵硬性增加，顺应性降低，是高血压、糖尿病等疾病引发血管病变的表现之一。建议专科就诊；\r\n");
//        outMap.put(80,"右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊，请医生结合临床进一步判断；\r\n");
//        outMap.put(81,"右足：下肢动脉轻度闭塞性供血不足，使足部大血管、微血管供血减少，有引发糖尿病足溃疡的风险。建议专科就诊；\r\n");
//        outMap.put(82,"右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。建议专科就诊；\r\n");
//        outMap.put(83,"右足：足部大血管、微血管严重病变，使足部血管狭窄或阻塞，造成供血灌注不足，诱发足部溃疡和坏疽的风险几率很高。建议专科就诊；\r\n");
//        //VPT
//        outMap.put(23,"轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(24,"中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(25,"有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(84,"右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(85,"右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(86,"右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(87,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(88,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(89,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(90,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(91,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(92,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(93,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(94,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(95,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(106,"左足：感觉良好，发生神经性溃疡为低风险。右足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(107,"左足：感觉良好，发生神经性溃疡为低风险。右足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊；\r\n");
//        outMap.put(108,"左足：感觉良好，发生神经性溃疡为低风险。右足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊；\r\n");
//        outMap.put(109,"左足：轻度深感觉障碍，发生神经性溃疡为低风险。建议专科就诊,请医生结合临床进一步判断。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
//        outMap.put(110,"左足：中度度深感觉障碍，存在下肢神经病变，降导致您下肢保护功能减退，发生神经性溃疡为中度风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
//        outMap.put(111,"左足：有重度深感觉障碍，存在下肢神经病变，降导致下肢保护功能减退，发生神经性溃疡为高风险。建议专科就诊。右足：感觉良好，发生神经性溃疡为低风险；\r\n");
//        //TBI
//        outMap.put(26,"有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(27,"下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
//        outMap.put(96,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(97,"左足：足部供血正常，平时继续保持良好的血糖、血脂和血压。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
//        outMap.put(98,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(99,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(100,"左足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断。右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
//        outMap.put(101,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
//        outMap.put(102,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。右足：足部供血正常，平时继续保持良好的血糖、血脂和血压；\r\n");
//        outMap.put(103,"左足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡。右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(104,"右足：有足部供血异常可能，有发生足部溃疡的危险，建议专科就诊,请医生结合临床进一步判断；\r\n");
//        outMap.put(105,"右足：下肢动脉中度闭塞性供血不足，使足部大血管、微血管供血减少，易引发糖尿病足溃疡；\r\n");
//
//        //经皮氧分压
//        outMap.put(28,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左足：轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(29,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左足：重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(30,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/正常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(31,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(32,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(33,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：轻度异常/未查），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(34,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/正常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(35,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(36,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(37,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：重度异常/未查），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(38,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：正常/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(39,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：正常/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(40,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：未查/轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(41,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压左/右足：未查/重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(42,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压右足：轻度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//        outMap.put(43,"经皮氧分压（TCPO2）测定能对肢体供血做出定量评估，且可直接反应微血管功能状态，直接反映血管向组织供氧情况，您本次检查为（经皮氧分压右足：重度异常），建议及时请专科医生结合临床综合评估，明确病情，然后制定适宜系统的治疗方案；\r\n");
//
//        outMap.put(112,gjGrzz + "是身体组织局部有炎症的症状表现之一，提示可能存在感染的情况，建议立即专科诊治；\r\n");
//        outMap.put(113,"足动脉搏动是判定下肢动脉闭塞性硬化的指标之一。您本次足动脉检查（"+gjDmJr+"）有减弱的现象，说明可能存在下肢供血功能的改变，建议及时到医院进一步针对性的检查，比如足ABI、vpt等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
//        outMap.put(114, "足动脉搏动是判定下肢动脉闭塞性硬化的指标之一，您本次足动脉检查（"+gjDmXs+"）搏动消失，说明下肢血管管腔有可能狭窄或闭塞，存在下肢供血不足的情况，建议及时到医院进一步针对性的检查，比如足ABI、vpt检查等等，及时明确病因，然后在医生指导下进行针对性的干预；\r\n");
//        // outMap  key 114
//        return outMap;
//    }
//
//    /**
//     * 预期达到目标文案
//     * @param range  控制目标
//     * @return
//     */
//    private static Map<Integer, String> getMbMapping() {
//        Map<Integer, String> outMap = new HashMap<>();
//        //创面,感染症状,缺血,经皮氧分压,足动脉搏动,皮温下降,10克尼龙丝触觉检查,,踝反射检查,凉、温度觉,痛觉
//        outMap.put(1,"肢体血管功能、供血逐渐改善，症状逐渐好转；\r\n");
//        outMap.put(12,"肢体血管功能、供血逐渐改善，症状逐渐好转，创面逐渐愈合；\r\n");
//        //白细胞计数
//        outMap.put(2,"控制白细胞计数，达到(4-10)×10^9/L；\r\n");
//        //降钙素原
//        outMap.put(3,"控制降钙素原，达到0.000-0.050ng/ml；\r\n");
//        //C反应蛋白
//        outMap.put(4,"控制C反应蛋白，达到0-0.8mg/dL；\r\n");
//        //血沉
//        outMap.put(5,"控制血沉，达到0-15mm/h；\r\n");  //男
//        outMap.put(6,"控制血沉，达到0-20mm/h；\r\n");  //女
//        //血红蛋白浓度
//        outMap.put(7,"控制血红蛋白浓度，达到120～160g/L；\r\n");  //男
//        outMap.put(8,"控制血红蛋白浓度，达到110～150g/L；\r\n");  //女
//        //白蛋白
//        outMap.put(9,"控制白蛋白，达到34-54g/L；\r\n");
//        //ABI,VPT,TBI
//        outMap.put(10,"积极治疗，使肢体血管和神经功能、供血状态逐渐改善，症状好转；\r\n");
//        outMap.put(11,"积极治疗，使肢体血管和神经功能、供血状态逐渐改善，症状好转，创面逐渐愈合；\r\n");
//        return outMap;
//    }
//
//    private static boolean checkCmAndKy(String cmLeft,String cmRght,String qxxky){
//        boolean flag = false;
//        if (!StringUtils.isBlank(cmLeft) || !StringUtils.isBlank(cmRght) || (!StringUtils.isBlank(qxxky) && !"1".equals(qxxky)) ){
//            flag = true;
//        }
//        return flag;
//    }
//}
