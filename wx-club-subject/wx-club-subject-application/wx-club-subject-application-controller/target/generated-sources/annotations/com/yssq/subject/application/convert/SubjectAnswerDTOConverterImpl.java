package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectAnswerDTO;
import com.yssq.subject.domain.bo.SubjectAnswerBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T16:41:09+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class SubjectAnswerDTOConverterImpl implements SubjectAnswerDTOConverter {

    @Override
    public SubjectAnswerBO ConvertDTO2BO(SubjectAnswerDTO subjectAnswerDTO) {
        if ( subjectAnswerDTO == null ) {
            return null;
        }

        SubjectAnswerBO subjectAnswerBO = new SubjectAnswerBO();

        subjectAnswerBO.setOptionType( subjectAnswerDTO.getOptionType() );
        subjectAnswerBO.setOptionContent( subjectAnswerDTO.getOptionContent() );
        subjectAnswerBO.setIsCorrect( subjectAnswerDTO.getIsCorrect() );

        return subjectAnswerBO;
    }

    @Override
    public List<SubjectAnswerBO> ConvertDTOList2BOList(List<SubjectAnswerDTO> subjectAnswerDTOList) {
        if ( subjectAnswerDTOList == null ) {
            return null;
        }

        List<SubjectAnswerBO> list = new ArrayList<SubjectAnswerBO>( subjectAnswerDTOList.size() );
        for ( SubjectAnswerDTO subjectAnswerDTO : subjectAnswerDTOList ) {
            list.add( ConvertDTO2BO( subjectAnswerDTO ) );
        }

        return list;
    }
}
