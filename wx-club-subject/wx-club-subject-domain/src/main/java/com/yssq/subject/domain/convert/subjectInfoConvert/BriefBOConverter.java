package com.yssq.subject.domain.convert.subjectInfoConvert;

import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.infra.basic.entity.SubjectBrief;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BriefBOConverter {
    BriefBOConverter INSTANCE = Mappers.getMapper(BriefBOConverter.class);

    //BO -> PO
    SubjectBrief ConvertBO2PO(SubjectInfoBO subjectInfoBO);

}
