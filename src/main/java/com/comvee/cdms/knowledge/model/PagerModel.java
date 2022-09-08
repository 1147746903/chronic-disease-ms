package com.comvee.cdms.knowledge.model;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class PagerModel implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
	private int total;
    /**
     * 每页条数
     */
	private int rows=10;
    /**
     * 当前页
     */
	private int page=1;
    /**
     * 总页数
     */
	private int totalPages;
	private int start;
	private int end;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    


	
}
