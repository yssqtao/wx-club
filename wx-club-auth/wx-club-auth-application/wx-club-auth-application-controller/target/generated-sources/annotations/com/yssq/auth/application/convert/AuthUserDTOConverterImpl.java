package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthUserDTO;
import com.yssq.auth.domain.bo.AuthUserBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-22T15:13:38+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class AuthUserDTOConverterImpl implements AuthUserDTOConverter {

    @Override
    public AuthUserBO ConvertDTO2BO(AuthUserDTO authUserDTO) {
        if ( authUserDTO == null ) {
            return null;
        }

        AuthUserBO authUserBO = new AuthUserBO();

        return authUserBO;
    }

    @Override
    public List<AuthUserDTO> ConvertBOList2DTOList(List<AuthUserBO> authUserBOList) {
        if ( authUserBOList == null ) {
            return null;
        }

        List<AuthUserDTO> list = new ArrayList<AuthUserDTO>( authUserBOList.size() );
        for ( AuthUserBO authUserBO : authUserBOList ) {
            list.add( ConvertBO2DTO( authUserBO ) );
        }

        return list;
    }

    @Override
    public AuthUserDTO ConvertBO2DTO(AuthUserBO authUserBO) {
        if ( authUserBO == null ) {
            return null;
        }

        AuthUserDTO authUserDTO = new AuthUserDTO();

        return authUserDTO;
    }
}
