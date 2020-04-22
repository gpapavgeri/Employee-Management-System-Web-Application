package com.company.myproject.model;

import org.springframework.stereotype.Component;

@Component
public class Pageable {

    private int pageNo;
    private int pageSize;
    private String sortBy;

    public Pageable() {
    }

    public Pageable(int pageNo, int pageSize, String sortBy) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
