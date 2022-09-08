package com.comvee.cdms.prescription.vo.eduplan;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/8/6 11:14.
 */
public class KnowledgeWeek {
    /**
     * 数字周：1
     */
    private Integer week;
    /**
     * 中文周：第一周
     */
    private String weekName;
    /**
     * 知识
     */
    private List<Knowledge> rows;


    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public List<Knowledge> getRows() {
        return rows;
    }

    public void setRows(List<Knowledge> rows) {
        this.rows = rows;
    }
}
