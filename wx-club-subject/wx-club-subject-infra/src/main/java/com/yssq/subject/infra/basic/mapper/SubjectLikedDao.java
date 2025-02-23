package com.yssq.subject.infra.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yssq.subject.infra.basic.entity.SubjectLiked;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 题目点赞表(SubjectLiked)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-23 16:38:05
 */
@Repository
public interface SubjectLikedDao extends BaseMapper<SubjectLiked> {
    int insertBatch(@Param("entities") List<SubjectLiked> entities);

    int countByCondition(SubjectLiked subjectLiked);

    List<SubjectLiked> queryPage(@Param("entity") SubjectLiked subjectLiked,
                                 @Param("start") int start,
                                 @Param("pageSize") Integer pageSize);

    void batchInsertOrUpdate(@Param("entities") List<SubjectLiked> subjectLikedList);
}

