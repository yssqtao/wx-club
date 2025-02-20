package com.yssq.subject.domain.convert;

import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.infra.basic.entity.SubjectCategory;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelBOConverter {
    SubjectLabelBOConverter INSTANCE = Mappers.getMapper(SubjectLabelBOConverter.class);

    //BO -> PO
    SubjectLabel ConvertBO2PO(SubjectLabelBO subjectLabelBO);

    //POList -> BOList
    List<SubjectLabelBO> ConvertPOList2BOList(List<SubjectLabel> subjectLabelList);
}
