package com.yssq.subject.domain.convert;

import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.infra.basic.entity.SubjectInfo;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoBOConverter {
    SubjectInfoBOConverter INSTANCE = Mappers.getMapper(SubjectInfoBOConverter.class);

    //BO -> PO
    SubjectInfo ConvertBO2PO(SubjectInfoBO subjectInfoBO);

    //POList -> BOList
    List<SubjectInfoBO> ConvertPOList2BOList(List<SubjectInfo> subjectInfoList);

    SubjectInfoBO ConvertOptionAndInfoToBo(SubjectOptionBO subjectOptionBO, SubjectInfo subjectInfo);
}