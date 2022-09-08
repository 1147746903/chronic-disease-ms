package com.comvee.cdms.common.wrapper;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhiGe
 * @description
 * @date 2017/11/14 11:15 create
 */
public class PageResult<T> implements Serializable {

    /**    
     *
     */    
    private static final long serialVersionUID = 2984636422561292981L;
    
    private long totalRows = 0;
    private int totalPages = 0;
    private int pageNum = 1;
    private int pageSize = 0;
    private List<T> rows;

    /**
     * 构造函数
     * @param list
     */
    public PageResult(List<T> list){
        //分页插件的结果集
        if(list instanceof Page){
            Page<T> page = (Page<T>) list;
            this.totalPages = page.getPages();
            this.totalRows = page.getTotal();
            this.pageNum = (page.getPageNum()!=0)?page.getPageNum():1;
            this.pageSize = page.getPageSize();
            this.rows = page.getResult();
        }else{
            this.rows = list;
        }
    }

    /**
     * 生成空白rows的分页结果集对象
     * @return
     */
    public PageResult createEmptyPageResult(){
        PageResult pageResult = new PageResult();
        pageResult.setTotalRows(this.totalRows);
        pageResult.setTotalPages(this.totalPages);
        pageResult.setPageSize(this.getPageSize());
        pageResult.setPageNum(this.pageNum);
        return pageResult;
    }

    public PageResult(){
    }

    public PageResult(int pageNum, int pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
