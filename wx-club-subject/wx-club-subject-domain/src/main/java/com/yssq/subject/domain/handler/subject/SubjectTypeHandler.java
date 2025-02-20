package com.yssq.subject.domain.handler.subject;

import com.yssq.subject.common.enums.SubjectInfoTypeEnum;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectOptionBO;


/**
 * TODO 这是策略接口，让不同的题型来实现接口
 * @author yssqtao
 * @date 2025/2/14
 */

public interface SubjectTypeHandler {

    /**
     * 枚举身份的识别
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 实际的题目的插入
     */
    void add(SubjectInfoBO subjectInfoBO);


    SubjectOptionBO query(int i);
}
