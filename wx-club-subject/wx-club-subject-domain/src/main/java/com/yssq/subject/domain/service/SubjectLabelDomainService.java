package com.yssq.subject.domain.service;

import com.yssq.subject.domain.bo.SubjectLabelBO;

import java.util.List;

/**
 * 题目标签表(SubjectLabel)表服务接口
 *
 * @author makejava
 * @since 2025-02-17 19:00:05
 */
public interface SubjectLabelDomainService {
    Boolean add(SubjectLabelBO subjectLabelBO);

    Boolean update(SubjectLabelBO subjectLabelBO);

    void deleteById(Long id);

    List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO);
}
