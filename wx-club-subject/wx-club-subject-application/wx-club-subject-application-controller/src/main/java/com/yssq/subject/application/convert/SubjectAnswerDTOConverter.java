package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectAnswerDTO;
import com.yssq.subject.application.dto.SubjectInfoDTO;
import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectAnswerDTOConverter {
    SubjectAnswerDTOConverter INSTANCE = Mappers.getMapper(SubjectAnswerDTOConverter.class);

    //DTO -> BO
    SubjectAnswerBO ConvertDTO2BO(SubjectAnswerDTO subjectAnswerDTO);

    //DTOList -> BOList
    List<SubjectAnswerBO> ConvertDTOList2BOList(List<SubjectAnswerDTO> subjectAnswerDTOList);

}
