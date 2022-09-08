package com.comvee.cdms.glu.model;
/**
 * 
 * @author 李左河
 *
 */
public class PageRequestModel implements java.io.Serializable  {
	/**
	 * 分页请求参数
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  当前页
	 */
	private int page = 1;
	/**
	 *  每页显示记录数
	 */
	private int rows = 10;
	/**
	 *  数据总记录数
	 */
	private int totalRows = 0;
	/**
	 *  排序字段名
	 */
	private String sort = null;
	/**
	 *  按什么排序(asc,desc)
	 */
	private String order = "asc";
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
    public int getTotalRows() {
        return totalRows;
    }
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
