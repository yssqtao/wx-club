package com.yssq.subject.domain.handler.subject;


import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.enums.SubjectInfoTypeEnum;
import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.domain.convert.subjectInfoConvert.MultipleBOConverter;
import com.yssq.subject.infra.basic.entity.SubjectMultiple;
import com.yssq.subject.infra.basic.service.SubjectMultipleService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多选题目的策略类
 */
@Component
public class MultipleTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectMultipleService subjectMultipleService;
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        Assert.notNull(subjectInfoBO.getOptionList(), "多选题目的答案不能为空");
        List<SubjectMultiple> subjectMultipleList = subjectInfoBO.getOptionList().stream()
                .map(option -> {
                    SubjectMultiple subjectMultiple = MultipleBOConverter.INSTANCE.ConvertBO2PO(option);
                    subjectMultiple.setSubjectId(subjectInfoBO.getId());
                    subjectMultiple.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                    return subjectMultiple;
                }).collect(Collectors.toList());
        subjectMultipleService.batchInsert(subjectMultipleList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectMultiple subjectMultiple = new SubjectMultiple();
        subjectMultiple.setSubjectId(Long.valueOf(subjectId));
        List<SubjectMultiple> result = subjectMultipleService.queryByCondition(subjectMultiple);
        List<SubjectAnswerBO> subjectAnswerBOList = MultipleBOConverter.INSTANCE.ConvertPOList2BOList(result);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }
}
