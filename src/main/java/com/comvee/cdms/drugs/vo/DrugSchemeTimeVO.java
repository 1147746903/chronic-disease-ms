package com.comvee.cdms.drugs.vo;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class DrugSchemeTimeVO {

    /**
     * 时间code :  breakfast 早餐 lunch  午餐 dinner 晚餐  sleep 睡觉
     */
    private String timeCode;
    /**
     * 时间节点 : 1  前  2 中 3 后
     */
    private String timeNodes;
    /**
     * 数量
     */
    private String num;

    public String getTimeCode() {
        return timeCode;
    }
    public void setTimeCode(String timeCode) {
        this.timeCode = timeCode;
    }
    public String getTimeNodes() {
        return timeNodes;
    }
    public void setTimeNodes(String timeNodes) {
        this.timeNodes = timeNodes;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
}