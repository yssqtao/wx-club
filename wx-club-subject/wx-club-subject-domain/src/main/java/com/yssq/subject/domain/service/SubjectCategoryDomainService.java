package com.yssq.subject.domain.service;

import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.infra.basic.entity.SubjectCategory;

import java.util.List;

/**
 * 题目分类(SubjectCategory)表服务接口
 *
 * @author makejava
 * @since 2025-02-17 15:48:23
 */
public interface SubjectCategoryDomainService {

    void add(SubjectCategoryBO subjectCategoryBO);

    Boolean ubpdate(SubjectCategoryBO subjectCategoryBO);

    void delete(Long id);

    List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO);
}
