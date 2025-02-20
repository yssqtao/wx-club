package com.yssq.subject.domain.convert.subjectInfoConvert;

import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.infra.basic.entity.SubjectJudge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JudgeBOConverter {
    JudgeBOConverter INSTANCE = Mappers.getMapper(JudgeBOConverter.class);

    //BOList -> POList
    List<SubjectAnswerBO> ConvertPOList2BOList(List<SubjectJudge> subjectJudgeList);

}
