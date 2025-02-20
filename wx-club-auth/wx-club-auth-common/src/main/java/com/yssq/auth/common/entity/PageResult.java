package com.yssq.auth.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页返回实体
 */
@Data
public class PageResult<T> implements Serializable {
    /**
     * 新建PageResult实体类，定义分页查询的信息
     */

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer total = 0; //总的记录数

    private Integer totalPages = 0; //总的页数

    private List<T> result = Collections.emptyList();   //页面显示的结果

    private Integer start = 1;

    private Integer end = 0;

    public void setRecords(List<T> result) {
        this.result = result;
        if (result != null && result.size() > 0) {
            setTotal(result.size());
        }
    }

    public void setTotal(Integer total) {
        this.total = total;
        if (this.pageSize > 0) {
            /**
             * 分页结果的设计
             * 如果总记录数 total 能被每页大小 pageSize 整除，则总页数为 total / pageSize
             * 如果不能整除，则需要再加 1 页
             */
            this.totalPages = (total / this.pageSize) + (total % this.pageSize == 0 ? 0 : 1);
        } else {
            this.totalPages = 0;
        }
        this.start = (this.pageSize > 0 ? (this.pageNo - 1) * this.pageSize : 0) + 1;
        this.end = (this.start - 1 + this.pageSize * (this.pageNo > 0 ? 1 : 0));
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
