package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectLabelDTO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T16:41:09+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class SubjectLabelDTOConverterImpl implements SubjectLabelDTOConverter {

    @Override
    public SubjectLabelBO ConvertDTO2BO(SubjectLabelDTO subjectLabelDTO) {
        if ( subjectLabelDTO == null ) {
            return null;
        }

        SubjectLabelBO subjectLabelBO = new SubjectLabelBO();

        subjectLabelBO.setId( subjectLabelDTO.getId() );
        subjectLabelBO.setLabelName( subjectLabelDTO.getLabelName() );
        subjectLabelBO.setSortNum( subjectLabelDTO.getSortNum() );
        subjectLabelBO.setCategoryId( subjectLabelDTO.getCategoryId() );
        subjectLabelBO.setCreatedBy( subjectLabelDTO.getCreatedBy() );
        subjectLabelBO.setCreatedTime( subjectLabelDTO.getCreatedTime() );
        subjectLabelBO.setUpdateBy( subjectLabelDTO.getUpdateBy() );
        subjectLabelBO.setUpdateTime( subjectLabelDTO.getUpdateTime() );
        subjectLabelBO.setIsDeleted( subjectLabelDTO.getIsDeleted() );

        return subjectLabelBO;
    }

    @Override
    public List<SubjectLabelDTO> ConvertBOList2DTOList(List<SubjectLabelBO> subjectLabelBOList) {
        if ( subjectLabelBOList == null ) {
            return null;
        }

        List<SubjectLabelDTO> list = new ArrayList<SubjectLabelDTO>( subjectLabelBOList.size() );
        for ( SubjectLabelBO subjectLabelBO : subjectLabelBOList ) {
            list.add( subjectLabelBOToSubjectLabelDTO( subjectLabelBO ) );
        }

        return list;
    }

    protected SubjectLabelDTO subjectLabelBOToSubjectLabelDTO(SubjectLabelBO subjectLabelBO) {
        if ( subjectLabelBO == null ) {
            return null;
        }

        SubjectLabelDTO subjectLabelDTO = new SubjectLabelDTO();

        subjectLabelDTO.setId( subjectLabelBO.getId() );
        subjectLabelDTO.setLabelName( subjectLabelBO.getLabelName() );
        subjectLabelDTO.setSortNum( subjectLabelBO.getSortNum() );
        subjectLabelDTO.setCategoryId( subjectLabelBO.getCategoryId() );
        subjectLabelDTO.setCreatedBy( subjectLabelBO.getCreatedBy() );
        subjectLabelDTO.setCreatedTime( subjectLabelBO.getCreatedTime() );
        subjectLabelDTO.setUpdateBy( subjectLabelBO.getUpdateBy() );
        subjectLabelDTO.setUpdateTime( subjectLabelBO.getUpdateTime() );
        subjectLabelDTO.setIsDeleted( subjectLabelBO.getIsDeleted() );

        return subjectLabelDTO;
    }
}
