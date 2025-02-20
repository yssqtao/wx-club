package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectCategoryDTO;
import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConverter {
    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO ConvertDTO2BO(SubjectCategoryDTO subjectCategoryDTO);

    // 将得到的SubjectCategoryBOList转换为SubjectCategoryDTOList
    List<SubjectCategoryDTO> ConvertBOList2DTOList(List<SubjectCategoryBO> subjectCategoryBOList);

}
