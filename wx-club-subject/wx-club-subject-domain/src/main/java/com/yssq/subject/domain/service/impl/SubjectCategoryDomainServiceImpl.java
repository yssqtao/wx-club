package com.yssq.subject.domain.service.impl;

import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.enums.ResultCodeEnum;
import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.domain.convert.SubjectCategoryBOConverter;
import com.yssq.subject.domain.service.SubjectCategoryDomainService;
import com.yssq.subject.infra.basic.config.MyMetaObjectHandler;
import com.yssq.subject.infra.basic.entity.SubjectCategory;
import com.yssq.subject.infra.basic.mapper.SubjectCategoryDao;
import com.yssq.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类(SubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2025-02-17 15:48:23
 */
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private MyMetaObjectHandler myMetaObjectHandler;

    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.ConvertBO2PO(subjectCategoryBO);
        //TODO 由于接口中，没有设置是否删除选项，在这里单独设置
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectCategoryService.insert(subjectCategory);
    }

    @Override
    public Boolean ubpdate(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.ConvertBO2PO(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    @Override
    public void delete(Long id) {
        subjectCategoryService.deleteById(id);
    }

    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.ConvertBO2PO(subjectCategoryBO);
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryBOConverter.INSTANCE.ConvertPOList2BOList(subjectCategoryList);
        return subjectCategoryBOList;
    }
}
