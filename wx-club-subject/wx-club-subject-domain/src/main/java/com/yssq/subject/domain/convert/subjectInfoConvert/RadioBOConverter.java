package com.yssq.subject.domain.convert.subjectInfoConvert;

import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.infra.basic.entity.SubjectMultiple;
import com.yssq.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RadioBOConverter {
    RadioBOConverter INSTANCE = Mappers.getMapper(RadioBOConverter.class);

    //BO -> PO
    SubjectRadio ConvertBO2PO(SubjectAnswerBO subjectAnswerBO);

    //POList -> BOList
    List<SubjectAnswerBO> ConvertPOList2BOList(List<SubjectRadio> subjectRadioList);
}
