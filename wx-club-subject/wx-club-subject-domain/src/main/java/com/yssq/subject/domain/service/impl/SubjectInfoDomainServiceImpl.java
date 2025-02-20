package com.yssq.subject.domain.service.impl;

import com.yssq.subject.common.entity.PageResult;
import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.domain.convert.SubjectInfoBOConverter;
import com.yssq.subject.domain.handler.subject.SubjectTypeHandler;
import com.yssq.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.yssq.subject.domain.service.SubjectInfoDomainService;
import com.yssq.subject.infra.basic.entity.SubjectInfo;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import com.yssq.subject.infra.basic.entity.SubjectMapping;
import com.yssq.subject.infra.basic.mapper.SubjectInfoDao;
import com.yssq.subject.infra.basic.service.SubjectInfoService;
import com.yssq.subject.infra.basic.service.SubjectLabelService;
import com.yssq.subject.infra.basic.service.SubjectMappingService;
import com.yssq.subject.infra.basic.service.SubjectMultipleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目信息表(SubjectInfo)表服务实现类
 *
 * @author makejava
 * @since 2025-02-18 14:51:07
 */
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Override
    @Transactional(rollbackFor = Exception.class) //对于多表操作，加上事务注解
    public void add(SubjectInfoBO subjectInfoBO) {
        //TODO 基于工厂加策略模式，实现题目的新增，因为题目类型有多种，如果每次新增都判断是哪种类型，导致if过多，业务逻辑冗余
        //题目有四种类型：设置一个工厂来包含这四种类型
        /**
         * TODO 这里是插入题目的通用信息
         */
        SubjectInfo subjectInfo = SubjectInfoBOConverter.INSTANCE.ConvertBO2PO(subjectInfoBO);
        subjectInfo.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        //TODO 在xml文件中添加select key 实现自动添加subject id
        subjectInfoService.insert(subjectInfo);
        /**
         * 1. 定义枚举类型，来识别题目类型
         * 2. 定义接口，让四种类型的题目来实现：a.枚举类型识别     b.add方法    c.query方法
         * 3. 定义工厂，与外界交互
         */
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfoBO.getSubjectType());
        subjectInfoBO.setId(subjectInfo.getId());
        handler.add(subjectInfoBO);

        /**
         * 对映射表的处理
         */
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> mappingList = categoryIds.stream()
                .flatMap(categoryId ->
                        labelIds.stream()
                                .map(labelId -> {
                                    SubjectMapping mapping = new SubjectMapping();
                                    mapping.setSubjectId(subjectInfo.getId());
                                    mapping.setCategoryId(Long.valueOf(categoryId));
                                    mapping.setLabelId(Long.valueOf(labelId));
                                    mapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                                    return mapping;
                                })
                )
                .collect(Collectors.toList());

        subjectMappingService.batchInsert(mappingList);
    }

    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        //TODO 分页查询第五步，创建分页返回对象，并将前端传入的页数和每页的记录数进行封装；同时计算起始位置
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectInfoBO.getPageNo());
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        int start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();

        SubjectInfo subjectInfo = SubjectInfoBOConverter.INSTANCE.ConvertBO2PO(subjectInfoBO);
        //TODO 分页查询第六步，查询 总记录数，需要提供查询所需的条件和分页信息
        //TODO 由于修改了实体类，添加了数据库表原本不存在的字段，因此参数需要修改，后续通过es优化
        int count = subjectInfoService.countByCondition(
                subjectInfo,
                subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId());
        if (count == 0) {
            return pageResult;
        }
        //TODO 分页查询第七步，查询 当前页的内容，需要提供查询所需的条件和分页信息
        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(
                subjectInfo,
                subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId(),
                start,
                subjectInfoBO.getPageSize());
        List<SubjectInfoBO> subjectInfoBOList = SubjectInfoBOConverter.INSTANCE.ConvertPOList2BOList(subjectInfoList);

        //TODO 后续添加
        List<SubjectInfoBO> updatedSubjectInfoBOList = subjectInfoBOList.stream()
                .map(info -> {
                    SubjectMapping subjectMapping = new SubjectMapping();
                    subjectMapping.setSubjectId(info.getId());

                    // 查询标签 ID 列表
                    List<Long> labelIds = subjectMappingService.queryLabelId(subjectMapping)
                            .stream()
                            .map(SubjectMapping::getLabelId)
                            .collect(Collectors.toList());

                    // 查询标签名称列表
                    List<String> labelNames = subjectLabelService.batchQueryById(labelIds)
                            .stream()
                            .map(SubjectLabel::getLabelName)
                            .collect(Collectors.toList());

                    // 设置标签名称到 SubjectInfoBO
                    info.setLabelName(labelNames);

                    // 返回修改后的对象
                    return info;
                })
                .collect(Collectors.toList());

        pageResult.setRecords(updatedSubjectInfoBOList);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = subjectInfoService.queryById(subjectInfoBO.getId());
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        SubjectOptionBO optionBO = handler.query(subjectInfo.getId().intValue());
        SubjectInfoBO bo = SubjectInfoBOConverter.INSTANCE.ConvertOptionAndInfoToBo(optionBO, subjectInfo);

        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<String> labelNameList = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        bo.setLabelName(labelNameList);
        return bo;
    }
}
