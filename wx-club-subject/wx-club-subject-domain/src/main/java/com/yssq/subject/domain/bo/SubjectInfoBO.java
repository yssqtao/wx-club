package com.yssq.subject.domain.bo;

import com.yssq.subject.common.entity.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目信息表(SubjectInfo)实体类
 *
 * @author makejava
 * @since 2025-02-18 14:51:07
 */
@Data
public class SubjectInfoBO extends PageInfo implements Serializable {
    private static final long serialVersionUID = 931951092638353474L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目名称
     */
    private String subjectName;
    /**
     * 题目难度
     */
    private Integer subjectDifficult;
    /**
     * 出题人名
     */
    private String settleName;
    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;
    /**
     * 题目分数
     */
    private Integer subjectScore;
    /**
     * 题目解析
     */
    private String subjectParse;
    /**
     * TODO 后增分类id
     */
    private List<Integer> categoryIds;

    /**
     * TODO 后增标签id
     */
    private List<Integer> labelIds;

    /**
     * TODO 后增答案选项
     */
    private List<SubjectAnswerBO> optionList;

    /**
     * TODO 后增标签name
     */
    private List<String> labelName;

    private Long categoryId;

    private Long labelId;

}

