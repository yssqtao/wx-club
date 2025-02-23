package com.yssq.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.yssq.subject.common.entity.PageResult;
import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.util.IdWorkerUtil;
import com.yssq.subject.common.util.LoginUtil;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.domain.convert.SubjectInfoBOConverter;
import com.yssq.subject.domain.handler.subject.SubjectTypeHandler;
import com.yssq.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.yssq.subject.domain.redis.RedisUtil;
import com.yssq.subject.domain.service.SubjectInfoDomainService;
import com.yssq.subject.domain.service.SubjectLikedDomainService;
import com.yssq.subject.infra.basic.entity.SubjectInfo;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import com.yssq.subject.infra.basic.entity.SubjectMapping;
import com.yssq.subject.infra.basic.es.entity.SubjectInfoEs;
import com.yssq.subject.infra.basic.mapper.SubjectInfoDao;
import com.yssq.subject.infra.basic.service.*;
import com.yssq.subject.infra.rpc.UserInfo;
import com.yssq.subject.infra.rpc.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 题目信息表(SubjectInfo)表服务实现类
 */
@Service
@Slf4j
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectEsService subjectEsService;
    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    @Resource
    private RedisUtil redisUtil;

    private static final String RANK_KEY = "subject_rank";

    @Resource
    private UserRpc userRpc;

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
        //TODO 插入数据库之后，同步到es，此业务插入到es不成功
        SubjectInfoEs subjectInfoEs = new SubjectInfoEs();
        subjectInfoEs.setDocId(new IdWorkerUtil(1, 1, 1).nextId());
        subjectInfoEs.setSubjectId(subjectInfo.getId());
        subjectInfoEs.setSubjectAnswer(subjectInfoBO.getSubjectAnswer());
        subjectInfoEs.setCreateTime(new Date().getTime());
        subjectInfoEs.setCreateUser("鸡翅");
        subjectInfoEs.setSubjectName(subjectInfo.getSubjectName());
        subjectInfoEs.setSubjectType(subjectInfo.getSubjectType());
        subjectEsService.insert(subjectInfoEs);
        //TODO 实现排行榜时，这里需要通过redis放入zadd计入排行榜
        redisUtil.addScore(RANK_KEY, LoginUtil.getLoginId(), 1);
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
        //查询题目是否被点赞过
        bo.setLiked(subjectLikedDomainService.isLiked(subjectInfoBO.getId().toString(), LoginUtil.getLoginId()));
        bo.setLikedCount(subjectLikedDomainService.getLikedCount(subjectInfoBO.getId().toString()));
        assembleSubjectCursor(subjectInfoBO, bo);
        return bo;
    }

    private void assembleSubjectCursor(SubjectInfoBO subjectInfoBO, SubjectInfoBO bo) {
        Long categoryId = subjectInfoBO.getCategoryId();
        Long labelId = subjectInfoBO.getLabelId();
        Long subjectId = subjectInfoBO.getId();
        if (Objects.isNull(categoryId) || Objects.isNull(labelId)) {
            return;
        }
        Long nextSubjectId = subjectInfoService.querySubjectIdCursor(subjectId, categoryId, labelId, 1);
        bo.setNextSubjectId(nextSubjectId);
        Long lastSubjectId = subjectInfoService.querySubjectIdCursor(subjectId, categoryId, labelId, 0);
        bo.setLastSubjectId(lastSubjectId);
    }

    @Override
    public PageResult<SubjectInfoEs> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO) {
        SubjectInfoEs subjectInfoEs = new SubjectInfoEs();
        subjectInfoEs.setPageNo(subjectInfoBO.getPageNo());
        subjectInfoEs.setPageSize(subjectInfoBO.getPageSize());
        subjectInfoEs.setKeyWord(subjectInfoBO.getKeyWord());
        return subjectEsService.querySubjectList(subjectInfoEs);
    }

    @Override
    public List<SubjectInfoBO> getContributeList() {
//        传统的实现
//        List<SubjectInfo> subjectInfoList = subjectInfoService.getContributeCount();

        //基于redis的zset实现
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil.rankWithScore(RANK_KEY, 0, 5);
        if (log.isInfoEnabled()) {
            log.info("getContributeList.typedTuples:{}", JSON.toJSONString(typedTuples));
        }
        if (CollectionUtils.isEmpty(typedTuples)) {
            return Collections.emptyList();
        }
        List<SubjectInfoBO> subjectInfoBOList = new LinkedList<>();
        typedTuples.forEach((rank -> {
            SubjectInfoBO subjectInfoBO = new SubjectInfoBO();
            subjectInfoBO.setSubjectCount(rank.getScore().intValue());
            UserInfo userInfo = userRpc.getUserInfo(rank.getValue());
            subjectInfoBO.setCreateUser(userInfo.getNickName());
            subjectInfoBO.setCreateUserAvatar(userInfo.getAvatar());
            subjectInfoBOList.add(subjectInfoBO);
        }));
        return subjectInfoBOList;
    }
}
