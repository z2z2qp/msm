package com.will.framework.dao;

import com.will.framework.entity.IEntity;

import java.util.List;

/**
 * Created by Will on 2016/8/15 10:00.
 */
public class PageResult<T extends IEntity> {

    /**
     * 当前页
     */
    private int page;
    /**
     * 选取行
     */
    private int rows;
    /**
     * 总行数
     */
    private int total;

    /**
     * 总页数
     */
    private int pages;
    /**
     * 数据
     */
    private List<T> data;
    /**
     * 起始行
     */
    private int firstRow;
    /**
     * 结束行
     */
    private int maxRow;

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    public PageResult(int page, int rows) {
        this.page = page;
        this.rows = rows;

        this.firstRow = page > 0 ? (page - 1) * rows : 1;
        this.maxRow = page > 0 ? page * rows : 10;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPages() {
        if (pages == 0) {
            pages = total % rows == 0 ? total / rows : total / rows + 1;
        }
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
