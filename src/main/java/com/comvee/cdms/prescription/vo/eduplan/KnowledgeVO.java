package com.comvee.cdms.prescription.vo.eduplan;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/8/6 9:38.
 */
public class KnowledgeVO {
    @Override
    public String toString() {
        return "KnowledgeVO{}";
    }

    /**
     * 知识周
     */
    private List<KnowledgeWeek> knowledgeWeekList;
    /**
     * 知识标签
     */
    private List<String> knowledgeTagList;

    public List<KnowledgeWeek> getKnowledgeWeekList() {
        return knowledgeWeekList;
    }

    public void setKnowledgeWeekList(List<KnowledgeWeek> knowledgeWeekList) {
        this.knowledgeWeekList = knowledgeWeekList;
    }

    public List<String> getKnowledgeTagList() {
        return knowledgeTagList;
    }

    public void setKnowledgeTagList(List<String> knowledgeTagList) {
        this.knowledgeTagList = knowledgeTagList;
    }
}
