package com.yssq.subject.domain.convert;

import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryBOConverter {
    SubjectCategoryBOConverter INSTANCE = Mappers.getMapper(SubjectCategoryBOConverter.class);

    SubjectCategory ConvertBO2PO(SubjectCategoryBO subjectCategoryBO);

    // 将得到的SubjectCategoryList转换为SubjectCategoryBOList
    List<SubjectCategoryBO> ConvertPOList2BOList(List<SubjectCategory> subjectCategoryList);
}
