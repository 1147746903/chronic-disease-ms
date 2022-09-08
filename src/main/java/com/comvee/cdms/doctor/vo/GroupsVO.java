package com.comvee.cdms.doctor.vo;

import java.io.Serializable;

/**
 * 分组列表视图
 * @author linyd
 * @date 2019/07/17
 */
public class GroupsVO<T> implements Serializable {
    private String name;
    private T items;
    private Integer orderIndex;
    /**
     * 列表类型
     */
    private Integer type;

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
