package com.yssq.subject.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求实体
 */
@Data
public class PageInfo implements Serializable {

    private Integer pageNo = 1;

    private Integer pageSize = 20;
    
    /**
     * TODO 分页查询第一步:新建PageInfo实体类，校验两个参数的合法性
     */
    
    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1) {
            return 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE) {
            return 20;
        }
        return pageSize;
    }
}
