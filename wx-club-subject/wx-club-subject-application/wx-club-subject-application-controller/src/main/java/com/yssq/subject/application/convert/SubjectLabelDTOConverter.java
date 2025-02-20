package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectLabelDTO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelDTOConverter {
    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);

    //DTO -> BO
    SubjectLabelBO ConvertDTO2BO(SubjectLabelDTO subjectLabelDTO);

    //BOList -> DTOList
    List<SubjectLabelDTO> ConvertBOList2DTOList(List<SubjectLabelBO> subjectLabelBOList);

}
