package com.yssq.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目分类关系表(SubjectMapping)实体类
 * TODO 题目映射表，是多对多的关系，包含题目id，题目的分类id，题目的标签id，当新增具体的题目时，可以挂载多个分类以及多个标签
 * TODO 当前端传入分类id后，1.从映射表可以得到题目id，从而得到题目详情 2.映射表可以得到标签id，从而查询标签的详情
 * @author makejava
 * @since 2025-02-18 11:54:13
 */
@Data
public class SubjectMapping implements Serializable {
    private static final long serialVersionUID = -96224231966233182L;
/**
     * 主键
     */
    private Long id;
/**
     * 题目id
     */
    private Long subjectId;
/**
     * 分类id
     */
    private Long categoryId;
/**
     * 标签id
     */
    private Long labelId;
/**
     * 创建人
     */
    private String createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 修改人
     */
    private String updateBy;
/**
     * 修改时间
     */
    private Date updateTime;

    private Integer isDeleted;

}

