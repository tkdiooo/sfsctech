//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.dto;

import java.util.List;

public class PageInfo<T> {
    private int pageNum;
    private int pageCount;
    private List<T> items;
    private int perPageSize;

    public PageInfo() {
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPerPageSize() {
        return this.perPageSize;
    }

    public void setPerPageSize(int perPageSize) {
        this.perPageSize = perPageSize;
    }
}
