package com.yssq.subject.domain.handler.subject;


import com.yssq.subject.common.enums.IsDeletedFlagEnum;
import com.yssq.subject.common.enums.SubjectInfoTypeEnum;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;
import com.yssq.subject.domain.convert.subjectInfoConvert.BriefBOConverter;
import com.yssq.subject.infra.basic.entity.SubjectBrief;
import com.yssq.subject.infra.basic.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 简答题目的策略类
 */
@Component
public class BriefTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectBriefService subjectBriefService;
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        /**
         * TODO 这里只是处理题目类型的特定信息，通用信息在使用工厂之前处理
         */
        SubjectBrief subjectBrief = BriefBOConverter.INSTANCE.ConvertBO2PO(subjectInfoBO);
        subjectBrief.setSubjectId(subjectInfoBO.getId().intValue());
        subjectBrief.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectBriefService.insert(subjectBrief);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectBrief subjectBrief = new SubjectBrief();
        subjectBrief.setSubjectId(subjectId);
        SubjectBrief result = subjectBriefService.queryByCondition(subjectBrief);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setSubjectAnswer(result.getSubjectAnswer());
        return subjectOptionBO;
    }
}
