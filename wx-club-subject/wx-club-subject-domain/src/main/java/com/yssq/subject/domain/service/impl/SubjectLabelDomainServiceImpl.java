package com.yssq.subject.domain.service.impl;

import com.yssq.subject.common.enums.CategoryTypeEnum;
import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.domain.convert.SubjectLabelBOConverter;
import com.yssq.subject.domain.service.SubjectLabelDomainService;
import com.yssq.subject.infra.basic.entity.SubjectCategory;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import com.yssq.subject.infra.basic.entity.SubjectMapping;
import com.yssq.subject.infra.basic.mapper.SubjectMappingDao;
import com.yssq.subject.infra.basic.service.SubjectCategoryService;
import com.yssq.subject.infra.basic.service.SubjectLabelService;
import com.yssq.subject.infra.basic.service.SubjectMappingService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目标签表(SubjectLabel)表服务实现类
 *
 * @author makejava
 * @since 2025-02-17 19:00:05
 */
@Service
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {
    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE.ConvertBO2PO(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count = subjectLabelService.insert(subjectLabel);
        return count >0;
    }

    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE.ConvertBO2PO(subjectLabelBO);
        int count = subjectLabelService.update(subjectLabel);
        return count >0;
    }

    @Override
    public void deleteById(Long id) {
        subjectLabelService.deleteById(id);
    }

    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        //TODO 此处后增业务：如果当前分类是 一级分类，则查询所有标签
        SubjectCategory subjectCategory = subjectCategoryService.queryById(Long.valueOf(subjectLabelBO.getCategoryId()));
        if(CategoryTypeEnum.PRIMARY.getCode() == subjectCategory.getCategoryType()){
            SubjectLabel subjectLabel = new SubjectLabel();
            subjectLabel.setCategoryId(subjectLabel.getCategoryId());
            List<SubjectLabel> subjectLabelList = subjectLabelService.queryLabelByCategoryId(subjectLabel);
            List<SubjectLabelBO> subjectLabelBOList = SubjectLabelBOConverter.INSTANCE.ConvertPOList2BOList(subjectLabelList);
            return subjectLabelBOList;
        }
        //TODO 此处先编写业务：如果不是 一级分类，需要通过映射关系查询标签ID，再根据标签ID查询标签详情
//        SubjectMapping subjectMapping = subjectMappingService.queryById(subjectLabelBO.getCategoryId());
        Long categoryId = Long.valueOf(subjectLabelBO.getCategoryId());
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryLabelId(subjectMapping);
        if (CollectionUtils.isEmpty(subjectMappingList)) {
            return Collections.emptyList();
        }
        List<Long> labelIdList = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabelList = subjectLabelService.batchQueryById(labelIdList);
        //TODO stream流处理
        List<SubjectLabelBO> boList = subjectLabelList.stream().map(subjectLabel -> {
            SubjectLabelBO bo = new SubjectLabelBO();
            bo.setId(subjectLabel.getId());
            bo.setLabelName(subjectLabel.getLabelName());
            bo.setCategoryId(String.valueOf(categoryId));
            bo.setSortNum(subjectLabel.getSortNum());
            return bo;
        }).collect(Collectors.toList());
        return boList;

        /*List<SubjectLabelBO> boList = new LinkedList<>();
        subjectLabelList.forEach(label -> {
            SubjectLabelBO bo = new SubjectLabelBO();
            bo.setId(label.getId());
            bo.setLabelName(label.getLabelName());
            bo.setCategoryId(String.valueOf(categoryId));
            bo.setSortNum(label.getSortNum());
            boList.add(bo);
        });
        return boList;*/
    }
}
