package com.comvee.cdms.member.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 并发症种类
 * @author 林雨堆
 * v6.0.0
 */
public class ComplicationVO implements Serializable {
    /**
     * 种类计数
     */
    private Integer count;
    /**
     * 并发症信息列表
     */
    private List<ComplicationBO> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ComplicationBO> getList() {
        return list;
    }

    public void setList(List<ComplicationBO> list) {
        this.list = list;
    }

    public ComplicationBO newComplicationBO(){
        return new ComplicationBO();
    }

    public class ComplicationBO {
        /**
         * 并发症名称
         */
        private String name;
        /**
         * 确诊日期
         */
        private String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}


