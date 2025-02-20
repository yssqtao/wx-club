package com.yssq.subject.domain.convert.subjectInfoConvert;

import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.infra.basic.entity.SubjectBrief;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T16:41:08+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class BriefBOConverterImpl implements BriefBOConverter {

    @Override
    public SubjectBrief ConvertBO2PO(SubjectInfoBO subjectInfoBO) {
        if ( subjectInfoBO == null ) {
            return null;
        }

        SubjectBrief subjectBrief = new SubjectBrief();

        subjectBrief.setId( subjectInfoBO.getId() );

        return subjectBrief;
    }
}
