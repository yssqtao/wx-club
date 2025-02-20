package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthRolePermissionDTO;
import com.yssq.auth.domain.bo.AuthRolePermissionBO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T18:58:53+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_421 (Oracle Corporation)"
)
public class AuthRolePermissionDTOConverterImpl implements AuthRolePermissionDTOConverter {

    @Override
    public AuthRolePermissionBO ConvertDTO2BO(AuthRolePermissionDTO authRolePermissionDTO) {
        if ( authRolePermissionDTO == null ) {
            return null;
        }

        AuthRolePermissionBO authRolePermissionBO = new AuthRolePermissionBO();

        return authRolePermissionBO;
    }
}
