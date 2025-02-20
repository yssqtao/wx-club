package com.yssq.subject.domain.handler.subject;


import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.enums.SubjectInfoTypeEnum;
import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.domain.convert.subjectInfoConvert.RadioBOConverter;
import com.yssq.subject.infra.basic.entity.SubjectRadio;
import com.yssq.subject.infra.basic.service.SubjectRadioService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单选题目的策略类
 */
@Component
public class RadioTypeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    /**
     * 单选题目的add，返回的是list
     */
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        Assert.notNull(subjectInfoBO.getOptionList(), "单选题目的答案不能为空");
        List<SubjectRadio> subjectRadioList = subjectInfoBO.getOptionList().stream()
                .map(option -> {
                    SubjectRadio subjectRadio = RadioBOConverter.INSTANCE.ConvertBO2PO(option);
                    subjectRadio.setSubjectId(subjectInfoBO.getId());
                    subjectRadio.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                    return subjectRadio;
                })
                .collect(Collectors.toList());
        // 2. 批量插入数据库
        subjectRadioService.batchInsert(subjectRadioList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectRadio subjectRadio = new SubjectRadio();
        subjectRadio.setSubjectId(Long.valueOf(subjectId));
        List<SubjectRadio> result = subjectRadioService.queryByCondition(subjectRadio);
        List<SubjectAnswerBO> subjectAnswerBOList = RadioBOConverter.INSTANCE.ConvertPOList2BOList(result);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }
}
