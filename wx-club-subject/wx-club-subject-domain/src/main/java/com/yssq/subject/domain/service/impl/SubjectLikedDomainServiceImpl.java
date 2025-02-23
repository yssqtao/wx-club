package com.yssq.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;

import com.yssq.subject.common.entity.PageResult;
import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.enums.SubjectLikedStatusEnum;
import com.yssq.subject.common.util.LoginUtil;
import com.yssq.subject.domain.bo.SubjectLikedBO;
import com.yssq.subject.domain.convert.SubjectLikedBOConverter;
import com.yssq.subject.domain.redis.RedisUtil;
import com.yssq.subject.domain.service.SubjectLikedDomainService;
import com.yssq.subject.infra.basic.entity.SubjectInfo;
import com.yssq.subject.infra.basic.entity.SubjectLiked;
import com.yssq.subject.infra.basic.service.SubjectInfoService;
import com.yssq.subject.infra.basic.service.SubjectLikedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 题目点赞表 领域service实现了
 *
 * @author jingdianjichi
 * @since 2024-01-07 23:08:45
 */
@Service
@Slf4j
public class SubjectLikedDomainServiceImpl implements SubjectLikedDomainService {

    @Resource
    private SubjectLikedService subjectLikedService;

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private RedisUtil redisUtil;

    private static final String SUBJECT_LIKED_KEY = "subject.liked";

    private static final String SUBJECT_LIKED_COUNT_KEY = "subject.liked.count";

    private static final String SUBJECT_LIKED_DETAIL_KEY = "subject.liked.detail";

    @Override
    public void add(SubjectLikedBO subjectLikedBO) {
        /**
         * TODO 需要在redisUtil中封装hash逻辑
         */
        Long subjectId = subjectLikedBO.getSubjectId();
        String likeUserId = subjectLikedBO.getLikeUserId();
        Integer status = subjectLikedBO.getStatus();
        String hashKey = buildSubjectLikedKey(subjectId.toString(), likeUserId);
        redisUtil.putHash(SUBJECT_LIKED_KEY, hashKey, status);

        /**
         * 只负责将点赞事件封装成消息并发送到 MQ 中
         * 而具体的业务处理（如同步数据库）由消费者（SubjectLikedConsumer）完成
         */
        /**
         * 原本redis实现的优点：
         * Redis 是内存数据库，读写性能非常高
         * 直接通过 Redis 操作数据，不需要额外的中间件
         */
        /**
         * 这么做的目的：
         * 1. 确保可靠性，MQ支持消息的持久化和消费确认机制
         * 高并发场景下可能存在数据丢失，例如，Redis 宕机或缓存被清理后，点赞状态可能无法同步到数据库
         * 点赞消息在生产者发送后不会丢失，即使消费者服务暂时不可用，消息也会保存在 MQ 中，待消费者恢复后继续处理
         * 2. 异步处理，降低响应时间（解耦操作，削峰填谷）
         * 所有操作都是同步完成的，包括点赞状态的更新和数据库的同步。在高并发场景下可能会导致响应时间变长
         * 只负责将点赞事件发送到 MQ，立即返回响应，具体的数据库同步由消费者异步完成，显著降低请求的响应时间
         * 3. 增强扩展性
         * 其他模块可以通过订阅 MQ 主题来扩展功能
         */

        /**
         * 1.用户发起点赞请求，add方法被调用，点赞信息封装为消息，通过MQ发送到主题subject-liked
         * 2.Redis 中的点赞状态（如计数和用户状态）会立即更新，但数据库的同步操作被延迟到消费者中完成
         * 3.SubjectLikedConsumer 订阅主题 subject-liked
         * 4.当有新的点赞消息时，消费者接收到消息，并将其解析为 SubjectLikedBO
         * 5.调用 syncLikedByMsg 方法，将点赞状态同步到数据库中（通过 batchInsertOrUpdate 方法）
         */

        String detailKey = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + likeUserId;
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        if (SubjectLikedStatusEnum.LIKED.getCode() == status) {
            redisUtil.increment(countKey, 1);
            redisUtil.set(detailKey, "1");
        } else {
            Integer count = redisUtil.getInt(countKey);
            if (Objects.isNull(count) || count <= 0) {
                return;
            }
            redisUtil.increment(countKey, -1);
            redisUtil.del(detailKey);
        }
    }

    private String buildSubjectLikedKey(String subjectId, String userId) {
        return subjectId + ":" + userId;
    }

    @Override
    public Boolean isLiked(String subjectId, String userId) {
        String detailKey = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + userId;
        return redisUtil.exist(detailKey);
    }

    @Override
    public Integer getLikedCount(String subjectId) {
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        Integer count = redisUtil.getInt(countKey);
        if (Objects.isNull(count) || count <= 0) {
            return 0;
        }
        return redisUtil.getInt(countKey);
    }



    @Override
    public Boolean update(SubjectLikedBO subjectLikedBO) {
        SubjectLiked subjectLiked = SubjectLikedBOConverter.INSTANCE.ConvertBO2PO(subjectLikedBO);
        return subjectLikedService.update(subjectLiked) > 0;
    }

    @Override
    public Boolean delete(SubjectLikedBO subjectLikedBO) {
        SubjectLiked subjectLiked = new SubjectLiked();
        subjectLiked.setId(subjectLikedBO.getId());
        subjectLiked.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        return subjectLikedService.update(subjectLiked) > 0;
    }

    @Override
    public void syncLiked() {
        Map<Object, Object> subjectLikedMap = redisUtil.getHashAndDelete(SUBJECT_LIKED_KEY);
        if (log.isInfoEnabled()) {
            log.info("syncLiked.subjectLikedMap:{}", JSON.toJSONString(subjectLikedMap));
        }
        if (MapUtils.isEmpty(subjectLikedMap)) {
            return;
        }
        //批量同步到数据库
        List<SubjectLiked> subjectLikedList = new LinkedList<>();
        subjectLikedMap.forEach((key, val) -> {
            SubjectLiked subjectLiked = new SubjectLiked();
            String[] keyArr = key.toString().split(":");
            String subjectId = keyArr[0];
            String likedUser = keyArr[1];
            subjectLiked.setSubjectId(Long.valueOf(subjectId));
            subjectLiked.setLikeUserId(likedUser);
            subjectLiked.setStatus(Integer.valueOf(val.toString()));
            subjectLiked.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            subjectLikedList.add(subjectLiked);
        });
        subjectLikedService.batchInsertOrUpdate(subjectLikedList);
    }

    @Override
    public PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        PageResult<SubjectLikedBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectLikedBO.getPageNo());
        pageResult.setPageSize(subjectLikedBO.getPageSize());
        int start = (subjectLikedBO.getPageNo() - 1) * subjectLikedBO.getPageSize();
        SubjectLiked subjectLiked = SubjectLikedBOConverter.INSTANCE.ConvertBO2PO(subjectLikedBO);
        subjectLiked.setLikeUserId(LoginUtil.getLoginId());
        int count = subjectLikedService.countByCondition(subjectLiked);
        if (count == 0) {
            return pageResult;
        }
        List<SubjectLiked> subjectLikedList = subjectLikedService.queryPage(subjectLiked, start,
                subjectLikedBO.getPageSize());
        List<SubjectLikedBO> subjectInfoBOS = SubjectLikedBOConverter.INSTANCE.ConvertPOList2BOList(subjectLikedList);
        subjectInfoBOS.forEach(info -> {
            SubjectInfo subjectInfo = subjectInfoService.queryById(info.getSubjectId());
            info.setSubjectName(subjectInfo.getSubjectName());
        });
        pageResult.setRecords(subjectInfoBOS);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    public void syncLikedByMsg(SubjectLikedBO subjectLikedBO) {
        List<SubjectLiked> subjectLikedList = new LinkedList<>();
        SubjectLiked subjectLiked = new SubjectLiked();
        subjectLiked.setSubjectId(Long.valueOf(subjectLikedBO.getSubjectId()));
        subjectLiked.setLikeUserId(subjectLikedBO.getLikeUserId());
        subjectLiked.setStatus(subjectLikedBO.getStatus());
        subjectLiked.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectLikedList.add(subjectLiked);
        subjectLikedService.batchInsertOrUpdate(subjectLikedList);
    }

}
