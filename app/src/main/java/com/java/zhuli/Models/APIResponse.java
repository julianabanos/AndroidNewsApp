package com.java.zhuli.Models;

import java.io.Serializable;
import java.util.List;

public class APIResponse implements Serializable {
    String pageSize;
    int total;
    List<Data> data;
    String currentPage;

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "total = '" + total + '\'' +
                        ",data = '" + data + '\'' +
                        ",pageSize = '" + pageSize + '\'' +
                        ",currentPage = '" + currentPage + '\'' +
                        "}";
    }
}
