package com.yssq.subject.domain.convert.subjectInfoConvert;

import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.infra.basic.entity.SubjectMultiple;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MultipleBOConverter {
    MultipleBOConverter INSTANCE = Mappers.getMapper(MultipleBOConverter.class);

    //BO -> PO
    SubjectMultiple ConvertBO2PO(SubjectAnswerBO subjectAnswerBO);

    //POList -> BOList
    List<SubjectAnswerBO> ConvertPOList2BOList(List<SubjectMultiple> subjectMultipleList);
}
