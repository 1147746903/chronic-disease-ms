/**
 * @File name:   EohFoodAllocationModel.java    饮食分配情况
 * @Create on:   2017年2月17日
 * @Author   :  占铃树
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.prescription.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 目前饮食情况
 * @author 占铃树
 */
public class FoodAllocationVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 一天总热量（kcal）
     */
    private String dailyHeat;
    /**
     * 交换总份数约（份）
     */
    private String num;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 主食和副食分配
     */
    private Map<String ,Object> foodAllocationMap = new HashMap<String, Object>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDailyHeat() {
        return dailyHeat;
    }
    public void setDailyHeat(String dailyHeat) {
        this.dailyHeat = dailyHeat;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }
    public String getModifyDt() {
        return modifyDt;
    }
    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }
    public Map<String ,Object> getFoodAllocationMap() {
        return foodAllocationMap;
    }
    public void setFoodAllocationMap(Map<String, Object> foodAllocationMap) {
        this.foodAllocationMap = foodAllocationMap;
    }

    @Override
    public String toString() {
        return "FoodAllocationVO{" +
                "id='" + id + '\'' +
                ", dailyHeat='" + dailyHeat + '\'' +
                ", num='" + num + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", foodAllocationMap=" + foodAllocationMap +
                '}';
    }
}
