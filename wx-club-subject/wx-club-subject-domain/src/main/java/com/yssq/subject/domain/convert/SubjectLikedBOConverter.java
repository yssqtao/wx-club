package com.yssq.subject.domain.convert;

import com.yssq.subject.domain.bo.SubjectLikedBO;
import com.yssq.subject.infra.basic.entity.SubjectLiked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 题目点赞表 bo转换器
 */
@Mapper
public interface SubjectLikedBOConverter {

    SubjectLikedBOConverter INSTANCE = Mappers.getMapper(SubjectLikedBOConverter.class);

    SubjectLiked ConvertBO2PO(SubjectLikedBO subjectLikedBO);

    List<SubjectLikedBO> ConvertPOList2BOList(List<SubjectLiked> subjectLikedList);

}
