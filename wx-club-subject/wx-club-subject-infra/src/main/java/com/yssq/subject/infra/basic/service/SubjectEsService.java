package com.yssq.subject.infra.basic.service;


import com.yssq.subject.common.entity.PageResult;
import com.yssq.subject.infra.basic.es.entity.SubjectInfoEs;

public interface SubjectEsService {
    boolean insert(SubjectInfoEs subjectInfoEs);

    PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs);
}
