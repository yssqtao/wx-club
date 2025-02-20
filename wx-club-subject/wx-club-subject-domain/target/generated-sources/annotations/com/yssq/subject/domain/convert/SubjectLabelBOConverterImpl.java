package com.yssq.subject.domain.convert;

import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.infra.basic.entity.SubjectLabel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T16:41:08+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class SubjectLabelBOConverterImpl implements SubjectLabelBOConverter {

    @Override
    public SubjectLabel ConvertBO2PO(SubjectLabelBO subjectLabelBO) {
        if ( subjectLabelBO == null ) {
            return null;
        }

        SubjectLabel subjectLabel = new SubjectLabel();

        subjectLabel.setId( subjectLabelBO.getId() );
        subjectLabel.setLabelName( subjectLabelBO.getLabelName() );
        subjectLabel.setSortNum( subjectLabelBO.getSortNum() );
        subjectLabel.setCategoryId( subjectLabelBO.getCategoryId() );
        subjectLabel.setCreatedBy( subjectLabelBO.getCreatedBy() );
        subjectLabel.setCreatedTime( subjectLabelBO.getCreatedTime() );
        subjectLabel.setUpdateBy( subjectLabelBO.getUpdateBy() );
        subjectLabel.setUpdateTime( subjectLabelBO.getUpdateTime() );
        subjectLabel.setIsDeleted( subjectLabelBO.getIsDeleted() );

        return subjectLabel;
    }

    @Override
    public List<SubjectLabelBO> ConvertPOList2BOList(List<SubjectLabel> subjectLabelList) {
        if ( subjectLabelList == null ) {
            return null;
        }

        List<SubjectLabelBO> list = new ArrayList<SubjectLabelBO>( subjectLabelList.size() );
        for ( SubjectLabel subjectLabel : subjectLabelList ) {
            list.add( subjectLabelToSubjectLabelBO( subjectLabel ) );
        }

        return list;
    }

    protected SubjectLabelBO subjectLabelToSubjectLabelBO(SubjectLabel subjectLabel) {
        if ( subjectLabel == null ) {
            return null;
        }

        SubjectLabelBO subjectLabelBO = new SubjectLabelBO();

        subjectLabelBO.setId( subjectLabel.getId() );
        subjectLabelBO.setLabelName( subjectLabel.getLabelName() );
        subjectLabelBO.setSortNum( subjectLabel.getSortNum() );
        subjectLabelBO.setCategoryId( subjectLabel.getCategoryId() );
        subjectLabelBO.setCreatedBy( subjectLabel.getCreatedBy() );
        subjectLabelBO.setCreatedTime( subjectLabel.getCreatedTime() );
        subjectLabelBO.setUpdateBy( subjectLabel.getUpdateBy() );
        subjectLabelBO.setUpdateTime( subjectLabel.getUpdateTime() );
        subjectLabelBO.setIsDeleted( subjectLabel.getIsDeleted() );

        return subjectLabelBO;
    }
}
