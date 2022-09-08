package com.comvee.cdms.knowledge.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author 李左河
 *
 */
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> rows;
    private int total;
    /**
     * 当前页
     */
    private int page;
    
    public PageData() {
    }
    
    public PageData(List<T> rows, int total, int page) {
        this.rows = rows;
        this.total = total;
        this.page = page;
    }
    
    public List<T> getRows() {
        return rows;
    }
    
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    

}
