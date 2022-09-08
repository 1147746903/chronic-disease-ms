package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.po.MemberPO;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Wxy on 2018/11/7.
 */
public class FollowQuesHelper {

    //行为问卷 生成建议逻辑
    public static Map<String,Object> outQuesFollow(List<Map<String, Object>> followBodyList, MemberPO member, String type, Map<String, Object> followInfoMap){
        Map<String,Object> reMap=new HashMap<>();

        Double ys =0.0;//饮食: 1~3题的平均分
        Double yd =0.0;//运动: 4~ 5题的平均分
        Double yy =0.0;//用药：6题的得分
        Double xtjc =0.0;//血糖测试: 7~ 8题的平均分
        Double zbjc =0.0;//足部检查: 9~ 12题的平均分

        //order 题目顺序
        //type  题目类型 1饮食  2运动  3用药  4血糖监测   5足部检查
        //title 题目标题
        //item  题目选项 1 2 3
        //score 题目得分 1分 2分 3分
        //code  是否必填 1必填 0非必填
        for (int i = 0; i <followBodyList.size() ; i++) {
            Map<String, Object> fMap = followBodyList.get(i);

            String order = fMap.get("order").toString();
            Double score =StringUtils.isBlank(fMap.get("score").toString())?0.0:Double.parseDouble(fMap.get("score").toString());

            if("1".equals(order) || "2".equals(order) || "3".equals(order)){
                ys += score;
            }else if("4".equals(order) || "5".equals(order)){
                yd += score;
            }else if("6".equals(order)){
                yy += score;
            }else if("7".equals(order) || "8".equals(order)){
                xtjc += score;
            }else if("9".equals(order) || "10".equals(order) || "11".equals(order) || "12".equals(order)){
                zbjc += score;
            }

        }
        ys = (double) Math.round(ys/3 * 100) / 100;
        yd = (double) Math.round(yd/2 * 100) / 100;
        xtjc = (double) Math.round(xtjc/2 * 100) / 100;
        zbjc = (double) Math.round(zbjc/4 * 100) / 100;

        //DecimalFormat df = new DecimalFormat("#.00");
        //String str = df.format(d);
        List<String> wtList1 =new ArrayList<>();//低问题集合
        List<String> wtList2 =new ArrayList<>();//中等问题集合

        String nic="";
        if(null!=member){
            String memberName = member.getMemberName();
            nic = memberName;
            Integer sex = member.getSex();
            if(1==sex){
                nic+="先生，";
            }else{
                nic+="女士，";
            }
        }

        Double num =ys + yd + yy + xtjc + zbjc;//总得分
        reMap.put("score",num);
        reMap.put("type","0");//类型  1有做上一次行为问卷 0没有
        String level="";
        String wt1="";
        String wt2="";
        String wt="";

        if(num > 29){
            level="1";//好
        }else if(num < 21){
            level="3";//差
        }else if(num <= 29 && num >= 21){
            level="2";//中等
        }
        reMap.put("level",level);

        if(ys < 4.2){
            wtList1.add("饮食控制不合理");
        }else if(ys<=5.6  && ys >=4.2){
            wtList2.add("饮食控制");
        }

        if(yd < 4.2){
            wtList1.add("运动锻炼不达标");
        }else if(yd<=5.6  && yd >=4.2){
            wtList2.add("运动锻炼");
        }

        if(yy < 4.2){
            wtList1.add("用药依从性差");
        }else if(yy<=5.6  && yy >=4.2){
            wtList2.add("用药依从性");
        }

        if(xtjc < 4.2){
            wtList1.add("血糖监测不规范");
        }else if(xtjc<=5.6  && xtjc >=4.2){
            wtList2.add("血糖监测");
        }

        if(zbjc < 4.2){
            wtList1.add("足部护理差");
        }else if(zbjc<=5.6  && zbjc >=4.2){
            wtList2.add("足部护理");
        }

        if(wtList1.size()>0){
            for (int i = 0; i < wtList1.size(); i++) {
                wt1+= wtList1.get(i)+"、";
            }
        }
        if(wt1.length()>0){
            wt1=wt1.substring(0,wt1.length()-1);
        }

        if(wtList2.size()>0){
            for (int i = 0; i < wtList2.size(); i++) {
                wt2+= wtList2.get(i)+"、";
            }
        }
        if(wt2.length()>0){
            if(wtList2.size()==1){
                wt2=wt2.substring(0,wt2.length()-1)+"一般";
            }else{
                wt2=wt2.substring(0,wt2.length()-1)+"的管理一般";
            }
        }


        if(wtList1.size()>0 && wtList2.size()>0){
            wt = wt1+"、"+wt2;
        }else{
            wt = wt1 + wt2;
        }

        if("1".equals(type)){
            String  levelOld = followInfoMap.get("level").toString();
            if("1".equals(levelOld) && "1".equals(level)){
                //总分评估为 好，但单项分＜4.2时
                if(wtList1.size()>0){
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您日常糖尿病总体自我管理行为一直都保持良好，这非常棒！希望您继续关注相对薄弱的地方，如"+ wt1 +"，继续加强，相信通过持之以恒良好生活方式的保持，您的各项代谢指标都会得到持续有效的控制。");
                }else{
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您日常糖尿病总体自我管理行为一直都保持良好，这非常棒！据研究表现，糖尿病患者生活上自我管理水平与病情控制呈正相关，有效的自我管理又有利于代谢指标的控制，有效促进糖尿病病情的良好转归，预防延缓并发症的发生和发展。所以希望您继续保持下去哦！");
                }
                reMap.put("type","上次（好）~ 末次（好）");
            }else if("1".equals(levelOld) && "2".equals(level)){
                reMap.put("text","您好，"+nic+"发现您糖尿病控制中自我管理行为干预，开始有所下降，比如"+ wt +"，希望您和家人能与您的糖尿病教育者或专科医生沟通，根据具体原因针对性的进行适宜调整，来有效的控制血糖，预防和延缓相关并发症的发生和发展。");
                reMap.put("type","上次（好）~ 末次（一般）");
            }else if("1".equals(levelOld) && "3".equals(level)){
                reMap.put("text","您好，"+nic+"发现您在糖尿病控制中的自我管理行为干预，开始有所下降，比如"+ wt1 +"，这里希望您和家人能与您的糖尿病教育者或专科医生沟通，根据具体原因针对性的进行适宜调整，来有效的控制血糖，预防和延缓相关并发症的发生和发展。");
                reMap.put("type","上次（好）~ 末次（差）");
            }

            else if("2".equals(levelOld) && "1".equals(level)){
                //总分评估为 好，但单项分＜4.2时
                if(wtList1.size()>0){
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您的糖尿病日常自我管理效能在不断的增强，这非常棒！希望您继续关注相对薄弱的地方，如："+ wt1 +"，继续加强，相信通过持之以恒良好生活方式的保持，您的各项代谢指标都会得到持续有效的控制。");
                }else{
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您的糖尿病日常自我管理效能在不断的增强，这非常棒！相信您能持续良好的保持下去，它会让您在整体血糖控制中受益终生哦！");
                }
                reMap.put("type","上次（一般）~ 末次（好） ");
            }else if("2".equals(levelOld) && "2".equals(level)){
                reMap.put("text","您好，"+nic+"经过这一阶段的管理，您在日常生活上的综合管控上一直保持不错，不过还有一些相对薄弱的地方，如："+ wt +"，这需要您继续加强哦！相信通过您的不懈努力，积极有效的自我综合管理，您各项代谢指标的控制会越来越好。");
                reMap.put("type","上次（一般）~ 末次（一般） ");
            }else if("2".equals(levelOld) && "3".equals(level)){
                reMap.put("text","您好，"+nic+"发现您糖尿病控制过程中，在日常生活上的综合管控上，不能配合有效的干预，比如"+ wt1 +"，希望您重视起来。因研究表明，糖尿病患者生活上自我管理水平与病情控制呈正相关，有效的自我管理有利于各项代谢指标的控制，可以有效促进糖尿病病情的良好转归，预防延缓并发症的发生和发展。");
                reMap.put("type","上次（一般 ）~ 末次（差）");
            }

            else if("3".equals(levelOld) && "1".equals(level)){
                //总分评估为 好，但单项分＜4.2时
                if(wtList1.size()>0){
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您的糖尿病日常自我管理效能在不断的增强，这非常棒！希望您继续关注相对薄弱的地方（"+ wt1 +"），继续加强，相信通过持之以恒良好生活方式的保持，您的各项代谢指标都会得到持续有效的控制。");
                }else{
                    reMap.put("text","您好，"+nic+"经过这段时间的管理，您的糖尿病日常自我管理效能在不断的增强，这非常棒！相信您能持续良好的保持下去，它将让您在整体血糖控制和健康方面受益终生哦！");
                }
                reMap.put("type","上次（差）~ 末次（好） ");
            }else if("3".equals(levelOld) && "2".equals(level)){
                reMap.put("text","您好，"+nic+"经过这段时间的管理，您的糖尿病日常自我管理效能在不断的增强，很棒，这次评估，发现还有一些需加强的地方，如："+ wt +"，希望继续努力，相信通过您的持续改善熟练掌握个体化的血糖管控方法后，它将为您在糖尿病控制过程中保驾护航！");
                reMap.put("type","上次（差）~ 末次（一般）");
            }else if("3".equals(levelOld) && "3".equals(level)){
                reMap.put("text","您好，"+nic+"发现您糖尿病控制过程中，在日常生活上的综合管控上，一直不能配合有效的干预，比如"+ wt1 +"，希望您及时重视起来。因研究表明，糖尿病患者生活上自我管理水平与病情控制呈正相关，有效的自我管理有利于各项代谢指标的控制，可以有效促进糖尿病病情的良好转归，预防延缓并发症的发生和发展。");
                reMap.put("type","上次（差）~ 末次(差）");
            }

        }else{
            if(num > 29){
                //总分评估为 好，但单项分＜4.2时
                if(wtList1.size()>0){
                    reMap.put("text","您好，"+nic+"您的行为量表的评估分为良好，说明您日常对自身的病情比较了解，懂得通过综合的调理来控制血糖，这非常好，希望您继续坚持下去，另外其中有一些薄弱的地方，如"+ wt1 +"，需继续加强哦，加油，相信您的血糖会得到持续有效的控制。");
                }else{
                    reMap.put("text","您好，"+nic+"您的行为量表的评估分为良好，说明您日常对自身的病情比较了解，也比较关注，懂得通过综合的调理来控制血糖，这非常好，希望您继续坚持下去哦，相信您的血糖会得到持续有效的控制。");
                }
            }
            if(num < 21){
                reMap.put("text","您好，"+nic+"您本次行为量表评估分为差（"+ wt +"），说明您在日常不大关注糖尿病的综合管理哦！这对病情的控制非常不利，希望您及时在糖尿病教育者或专科医生指导下，提高糖尿病的认知和管理，使血糖持续控制平稳，积极预防和延缓糖尿病相关并发症的发生、发展。");
            }
            if(num <= 29 && num >= 21){
                reMap.put("text","您好，"+nic+"您的行为量表的评估分为中等水平，说明您日常有去了解糖尿病，懂得通过综合的调理来控制血糖，这很好，不过您目前还有些薄弱的地方,需要加强，如："+ wt +"，继续努力哦！坚持下去，相信您的血糖会得到有效控制。");
            }
        }

        return  reMap;
    }
}
