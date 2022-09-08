/**
 * @File name:  RuleResult.java  drools规则反回的model
 * @Create on:  2016-02-01
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 **/
package com.comvee.cdms.knowledge.model;

import java.util.ArrayList;
import java.util.List;

public class RuleResultModel {
    /**
     *
     返回的题目组id
     */
    private String resTId;
    /**
     * 请求tid
     */
    private String tid;
    
    private List<Object> resList = new ArrayList<Object>();

    public List<Object> getResList() {
        return resList;
    }

    public void setResList(List<Object> resList) {
        this.resList = resList;
    }

    public String getTid() {
        return tid;
    }

    public void setTId(String tid) {
        this.tid = tid;
    }

    public String getResTId() {
        return resTId;
    }

    public void setResTId(String resTId) {
        this.resTId = resTId;
    }

    public void addResList(Object obj){
        this.resList.add(obj);
    }
}
