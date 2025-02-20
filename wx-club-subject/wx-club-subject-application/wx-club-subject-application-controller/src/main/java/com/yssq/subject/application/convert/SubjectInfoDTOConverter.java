package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectInfoDTO;
import com.yssq.subject.application.dto.SubjectLabelDTO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoDTOConverter {
    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);

    //DTO -> BO
    SubjectInfoBO ConvertDTO2BO(SubjectInfoDTO subjectInfoDTO);

    //BOList -> DTOList
    List<SubjectInfoDTO> ConvertBOList2DTOList(List<SubjectInfoBO> subjectInfoBOList);

    //BO -> DTO
    SubjectInfoDTO ConvertBO2DTO(SubjectInfoBO subjectInfoBO);

}
