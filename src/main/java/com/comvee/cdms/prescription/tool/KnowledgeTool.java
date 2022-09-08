package com.comvee.cdms.prescription.tool;

import com.alibaba.fastjson.JSONArray;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 管理处方教育工具类
 */
public class KnowledgeTool {

    /**
     * 知识学习列表处理
     * @param knowledgeListJson
     * @return
     */
    public static List<PrescriptionKnowledgePO> knowledgeListHandler(String knowledgeListJson){
        if(StringUtils.isBlank(knowledgeListJson)){
            return null;
        }
        List<PrescriptionKnowledgePO> list = JSONArray.parseArray(knowledgeListJson, PrescriptionKnowledgePO.class);
        MultiValueMap<Integer ,PrescriptionKnowledgePO> multiValueMap = new LinkedMultiValueMap<>();
        for(PrescriptionKnowledgePO p : list){
            multiValueMap.add(p.getWeek() ,p);
        }
        Integer[] weekDays;
        PrescriptionKnowledgePO prescriptionKnowledge;
        String date;
        List<PrescriptionKnowledgePO> knowledgeList;
        for(Map.Entry<Integer ,List<PrescriptionKnowledgePO>> entry : multiValueMap.entrySet()){
            knowledgeList = entry.getValue();
            weekDays = getWeekDays(knowledgeList.size());
            for(int i = 0 ,max = knowledgeList.size(); i < max ; i ++){
                prescriptionKnowledge = knowledgeList.get(i);
                date = LocalDateTime.now()
                        .plusWeeks(entry.getKey() - 1)
                        .plusDays(weekDays[i])
                        .withHour(10)
                        .withMinute(0)
                        .withSecond(0)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                prescriptionKnowledge.setPlanPushDt(date);
                prescriptionKnowledge.setLearnDt(date);
            }
        }
        //第一篇立马下发
        if(list != null && !list.isEmpty()){
            prescriptionKnowledge = list.get(0);
            prescriptionKnowledge.setPlanPushDt(DateHelper.getNowDate());
            prescriptionKnowledge.setLearnDt(DateHelper.getNowDate());
        }
        return list;
    }

    /**
     * 根据每周篇数获取接下来一周内的推送日期时间点
     * @param weekTimes 每周篇数
     * @return
     */
    private static Integer[] getWeekDays(Integer weekTimes){
        Integer[] integers = null;
        switch (weekTimes){
            case 1:
                integers = new Integer[]{0};
                break;
            case 2:
                integers = new Integer[]{0 ,4};
                break;
            case 3:
                integers = new Integer[]{0 ,2 ,4};
                break;
            case 4:
                integers = new Integer[]{0 ,2 ,4 ,6};
                break;
            case 5:
                integers = new Integer[]{0 ,1 ,2 ,4 ,6};
                break;
            case 6:
                integers = new Integer[]{0 ,1 ,2 ,4 ,5 ,6};
                break;
            case 7:
                integers = new Integer[]{1 ,2 ,3 ,4 ,5 ,6 ,7};
                break;
            default:
                throw new BusinessException("");
        }
        return integers;
    }
}
