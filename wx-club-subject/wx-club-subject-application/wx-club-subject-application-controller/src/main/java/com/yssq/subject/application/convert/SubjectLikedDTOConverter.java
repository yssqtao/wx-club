package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectLikedDTO;
import com.yssq.subject.domain.bo.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 题目点赞表 dto转换器
 */
@Mapper
public interface SubjectLikedDTOConverter {

    SubjectLikedDTOConverter INSTANCE = Mappers.getMapper(SubjectLikedDTOConverter.class);

    SubjectLikedBO ConvertDTO2BO(SubjectLikedDTO subjectLikedDTO);

}
