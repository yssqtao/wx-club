package com.yssq.subject.application.convert;

import com.yssq.subject.application.dto.SubjectCategoryDTO;
import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-22T15:06:38+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class SubjectCategoryDTOConverterImpl implements SubjectCategoryDTOConverter {

    @Override
    public SubjectCategoryBO ConvertDTO2BO(SubjectCategoryDTO subjectCategoryDTO) {
        if ( subjectCategoryDTO == null ) {
            return null;
        }

        SubjectCategoryBO subjectCategoryBO = new SubjectCategoryBO();

        subjectCategoryBO.setId( subjectCategoryDTO.getId() );
        subjectCategoryBO.setCategoryName( subjectCategoryDTO.getCategoryName() );
        subjectCategoryBO.setCategoryType( subjectCategoryDTO.getCategoryType() );
        subjectCategoryBO.setImageUrl( subjectCategoryDTO.getImageUrl() );
        subjectCategoryBO.setParentId( subjectCategoryDTO.getParentId() );
        subjectCategoryBO.setCount( subjectCategoryDTO.getCount() );
        subjectCategoryBO.setLabelBOList( subjectCategoryDTOListToSubjectLabelBOList( subjectCategoryDTO.getLabelBOList() ) );
        subjectCategoryBO.setCreatedBy( subjectCategoryDTO.getCreatedBy() );
        subjectCategoryBO.setCreatedTime( subjectCategoryDTO.getCreatedTime() );
        subjectCategoryBO.setUpdateBy( subjectCategoryDTO.getUpdateBy() );
        subjectCategoryBO.setUpdateTime( subjectCategoryDTO.getUpdateTime() );
        subjectCategoryBO.setIsDeleted( subjectCategoryDTO.getIsDeleted() );

        return subjectCategoryBO;
    }

    @Override
    public List<SubjectCategoryDTO> ConvertBOList2DTOList(List<SubjectCategoryBO> subjectCategoryBOList) {
        if ( subjectCategoryBOList == null ) {
            return null;
        }

        List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>( subjectCategoryBOList.size() );
        for ( SubjectCategoryBO subjectCategoryBO : subjectCategoryBOList ) {
            list.add( subjectCategoryBOToSubjectCategoryDTO( subjectCategoryBO ) );
        }

        return list;
    }

    protected SubjectLabelBO subjectCategoryDTOToSubjectLabelBO(SubjectCategoryDTO subjectCategoryDTO) {
        if ( subjectCategoryDTO == null ) {
            return null;
        }

        SubjectLabelBO subjectLabelBO = new SubjectLabelBO();

        subjectLabelBO.setId( subjectCategoryDTO.getId() );
        subjectLabelBO.setCreatedBy( subjectCategoryDTO.getCreatedBy() );
        subjectLabelBO.setCreatedTime( subjectCategoryDTO.getCreatedTime() );
        subjectLabelBO.setUpdateBy( subjectCategoryDTO.getUpdateBy() );
        subjectLabelBO.setUpdateTime( subjectCategoryDTO.getUpdateTime() );
        subjectLabelBO.setIsDeleted( subjectCategoryDTO.getIsDeleted() );

        return subjectLabelBO;
    }

    protected List<SubjectLabelBO> subjectCategoryDTOListToSubjectLabelBOList(List<SubjectCategoryDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SubjectLabelBO> list1 = new ArrayList<SubjectLabelBO>( list.size() );
        for ( SubjectCategoryDTO subjectCategoryDTO : list ) {
            list1.add( subjectCategoryDTOToSubjectLabelBO( subjectCategoryDTO ) );
        }

        return list1;
    }

    protected SubjectCategoryDTO subjectLabelBOToSubjectCategoryDTO(SubjectLabelBO subjectLabelBO) {
        if ( subjectLabelBO == null ) {
            return null;
        }

        SubjectCategoryDTO subjectCategoryDTO = new SubjectCategoryDTO();

        subjectCategoryDTO.setId( subjectLabelBO.getId() );
        subjectCategoryDTO.setCreatedBy( subjectLabelBO.getCreatedBy() );
        subjectCategoryDTO.setCreatedTime( subjectLabelBO.getCreatedTime() );
        subjectCategoryDTO.setUpdateBy( subjectLabelBO.getUpdateBy() );
        subjectCategoryDTO.setUpdateTime( subjectLabelBO.getUpdateTime() );
        subjectCategoryDTO.setIsDeleted( subjectLabelBO.getIsDeleted() );

        return subjectCategoryDTO;
    }

    protected List<SubjectCategoryDTO> subjectLabelBOListToSubjectCategoryDTOList(List<SubjectLabelBO> list) {
        if ( list == null ) {
            return null;
        }

        List<SubjectCategoryDTO> list1 = new ArrayList<SubjectCategoryDTO>( list.size() );
        for ( SubjectLabelBO subjectLabelBO : list ) {
            list1.add( subjectLabelBOToSubjectCategoryDTO( subjectLabelBO ) );
        }

        return list1;
    }

    protected SubjectCategoryDTO subjectCategoryBOToSubjectCategoryDTO(SubjectCategoryBO subjectCategoryBO) {
        if ( subjectCategoryBO == null ) {
            return null;
        }

        SubjectCategoryDTO subjectCategoryDTO = new SubjectCategoryDTO();

        subjectCategoryDTO.setId( subjectCategoryBO.getId() );
        subjectCategoryDTO.setCategoryName( subjectCategoryBO.getCategoryName() );
        subjectCategoryDTO.setCategoryType( subjectCategoryBO.getCategoryType() );
        subjectCategoryDTO.setImageUrl( subjectCategoryBO.getImageUrl() );
        subjectCategoryDTO.setParentId( subjectCategoryBO.getParentId() );
        subjectCategoryDTO.setCount( subjectCategoryBO.getCount() );
        subjectCategoryDTO.setLabelBOList( subjectLabelBOListToSubjectCategoryDTOList( subjectCategoryBO.getLabelBOList() ) );
        subjectCategoryDTO.setCreatedBy( subjectCategoryBO.getCreatedBy() );
        subjectCategoryDTO.setCreatedTime( subjectCategoryBO.getCreatedTime() );
        subjectCategoryDTO.setUpdateBy( subjectCategoryBO.getUpdateBy() );
        subjectCategoryDTO.setUpdateTime( subjectCategoryBO.getUpdateTime() );
        subjectCategoryDTO.setIsDeleted( subjectCategoryBO.getIsDeleted() );

        return subjectCategoryDTO;
    }
}
