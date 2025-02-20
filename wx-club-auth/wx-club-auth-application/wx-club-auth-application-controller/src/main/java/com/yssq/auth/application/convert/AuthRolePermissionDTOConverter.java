package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthRolePermissionDTO;
import com.yssq.auth.domain.bo.AuthRolePermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 权限dto转换器
 */
@Mapper
public interface AuthRolePermissionDTOConverter {

    AuthRolePermissionDTOConverter INSTANCE = Mappers.getMapper(AuthRolePermissionDTOConverter.class);

    AuthRolePermissionBO ConvertDTO2BO(AuthRolePermissionDTO authRolePermissionDTO);

}
