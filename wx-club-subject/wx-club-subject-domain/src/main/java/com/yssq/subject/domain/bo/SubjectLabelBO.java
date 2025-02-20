package com.yssq.subject.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 * @author makejava
 * @since 2025-02-17 19:00:05
 */
@Data
public class SubjectLabelBO implements Serializable {
    private static final long serialVersionUID = -20429181925913986L;
/**
     * 主键
     */
    private Long id;
/**
     * 标签分类
     */
    private String labelName;
/**
     * 排序
     */
    private Integer sortNum;

    private String categoryId;
/**
     * 创建人
     */
    private String createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 更新人
     */
    private String updateBy;
/**
     * 更新时间
     */
    private Date updateTime;

    private Integer isDeleted;

}

